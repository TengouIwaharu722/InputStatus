package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import config.AppConstants;
import dao.UserDAO;
import lombok.extern.slf4j.Slf4j;
import model.User;
import util.PassWordEncryption;

/**
 * ログイン
 */
@Slf4j
@WebServlet("/LogInServlet")
public class LogInServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 入力値の取得
		String mailaddress = request.getParameter("mailaddress");
		String password = request.getParameter("password");

		UserDAO userDAO = new UserDAO();
		User user = userDAO.findUserByEmail(mailaddress);
		
		//暗号化したパスワードの比較
		if (user == null || !PassWordEncryption.verify(password, user.getPassword())) {
			request.setAttribute("error", "入力された情報が正しくありません。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request, response);
			return;
		}else {
			//ログイン成功画面に遷移
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			RequestDispatcher dispatcher = request.getRequestDispatcher(AppConstants.LOGIN_SUCCESS_URL);
			dispatcher.forward(request, response);
			
		}

	}

}
