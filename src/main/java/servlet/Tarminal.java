package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bo.PostUserLogic;
import config.AppConstants;
import lombok.extern.slf4j.Slf4j;
import model.User;
import util.DBManager;

/**
 * サーブレット
 * スタート及びSubmit後
 */
@Slf4j
@WebServlet("/Tarminal")
public class Tarminal extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//アプリケーションのルートパス
	public static String dataFolderPath;
	
	@Override
	public void init() throws ServletException {
		super.init();
	
	// 配布用にDBを配置させるためアプリケーションのルートパス取得
			ServletContext context = getServletContext();
			// dataフォルダをつけてパス作成
			dataFolderPath = context.getRealPath("/data");

			//DB接続オブジェクトconnでSQL実行オブジェクトstmlを作成
			try (Connection conn = DBManager.getConnection(); Statement stmt = conn.createStatement()) {

				String sql = "CREATE TABLE IF NOT EXISTS users ("
	           + "id INT PRIMARY KEY AUTO_INCREMENT, "
	           + "LAST_NAME VARCHAR(100), "
	           + "FIRST_NAME VARCHAR(100), "
	           + "LAST_FURIGANA VARCHAR(100), "
	           + "FIRST_FURIGANA VARCHAR(100), "
	           + "GENDER VARCHAR(10), "
	           + "BIRTH DATE, "
	           + "PHONE01 VARCHAR(20), "
	           + "PHONE02 VARCHAR(20), "
	           + "PHONE03 VARCHAR(20), "
	           + "ZIP01 VARCHAR(10), "
	           + "ZIP02 VARCHAR(10), "
	           + "ADRESS VARCHAR(255), "
	           + "EMAIL VARCHAR(255), "
	           + "PASSWORD VARCHAR(255)"
	           + ");";
						
				stmt.executeUpdate(sql);
				log.info("初期化完了");

			} catch (SQLException e) {
				throw new ServletException("DB初期化失敗", e);
			}

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//フォワード先
		String forwardPath = null;

		//サーブレットクラスの動作を決定するactionの値を取得
		String action = request.getParameter("action");

		//登録の開始（登録フォームへ）
		if (action == null) {
			forwardPath = AppConstants.INPUT_FORM_URL;
		}
		//登録確認画面から「登録」をリクエストされたときの処理
		else if (action.equals("done")) {
			//セッションスコープに保存された登録ユーザー情報を取得
			HttpSession session = request.getSession();
			User usar = (User) session.getAttribute(AppConstants.SESSION_NAME);

			//登録処理の呼び出し
			PostUserLogic logic = new PostUserLogic();
			logic.execute(usar);
			

			//不要となったセッションスコープ内のインスタンス削除
			session.removeAttribute(AppConstants.SESSION_NAME);

			//登録後のフォワード指定
			forwardPath = AppConstants.RESISTER_PAGE_URL;

		}

		//フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//フォワード先
		String forwardPath = null;

		String lastName = request.getParameter(AppConstants.LAST_NAME);
		String firstName = request.getParameter(AppConstants.FIRST_NAME);
		String lastFurigana = request.getParameter(AppConstants.LAST_FURIGANA);
		String firstFurigana = request.getParameter(AppConstants.FIRST_FURIGANA);
		String gender = request.getParameter(AppConstants.GENDER);
		String birth = request.getParameter(AppConstants.BIRTH);
		String phone1 = request.getParameter(AppConstants.PHONE1);
		String phone2 = request.getParameter(AppConstants.PHONE2);
		String phone3 = request.getParameter(AppConstants.PHONE3);
		String zip1 = request.getParameter(AppConstants.ZIP1);
		String zip2 = request.getParameter(AppConstants.ZIP2);
		String address = request.getParameter(AppConstants.ADDRESS);
		String email = request.getParameter(AppConstants.EMAIL);
		String password = request.getParameter(AppConstants.PASSWORD);

		User user = new User(lastName, firstName, lastFurigana,
				firstFurigana, gender, birth, phone1, phone2,
				phone3, zip1, zip2, address, email, password);

		//バリデーションチェック
		user.allValidete();

		//Mapから値のコレクションを取得
		boolean hasAnyFalse = user.getValidateMap().values().stream()
				//Streamの要素を一つずつチェック
				.anyMatch(value ->
				//値が false であるかどうかを判定
				value.equals(false));

		//Map内の入力値に1つでも入力不正があれば登録画面に戻します
		if (hasAnyFalse) {
			forwardPath = AppConstants.INPUT_FORM_URL;
		} else {
			//入力値が正常なら登録確認画面に移行
			forwardPath = AppConstants.INPUT_RESULT_URL;
		}
		//セッションスコープに登録ユーザを保存
		HttpSession session = request.getSession();
		session.setAttribute(AppConstants.SESSION_NAME, user);

		// フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
		dispatcher.forward(request, response);

	}

}
