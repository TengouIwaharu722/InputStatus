package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import model.User;
import util.DBManager;
import util.PassWordEncryption;

/**
 * 個人データの取得とセット
 */
@Slf4j
public class UserDAO {

	/**
	 * 入力されたEmailがDB上に複数ないかチェック
	 */
	public boolean checkUserEmail(String inputEmail) {

		// データベースへの接続
		try (Connection conn = DBManager.getConnection()) {

			String sql = "SELECT COUNT(*) FROM USER_DATA WHERE EMAIL = ?";

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, inputEmail); // inputEmail は検索対象のメールアドレス
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				int count = rs.getInt(1);
				return count >= 1; // 登録済ならtrue
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 入力されたEmailからDB上で個人データを取得する
	 */
	public User findUserByEmail(String inputEmail) {
		User user = null;

		// データベースへの接続
		try (Connection conn = DBManager.getConnection()) {
			//個人のデータを取得
			String sql = "SELECT * FROM USER_DATA WHERE EMAIL = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, inputEmail);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {				
        user = new User();
        user = setUserData(rs);        
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user; // 見つからなければnull
	}

	/**
	 * 全個人データの取得
	 */
	public List<User> findAll() {

		List<User> list = new ArrayList<User>();

		// データベースへの接続
		try (Connection conn = DBManager.getConnection()) {

			// SELECT文を準備(IDの大きい順)
			String sql = "SELECT LAST_NAME, FIRST_NAME, LAST_FURIGANA, FIRST_FURIGANA, "
					+ "GENDER, BIRTH, PHONE01, PHONE02, PHONE03, ZIP01, ZIP02, ADRESS, EMAIL,PASSWORD " + "FROM USER_DATA";

			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SELECT文を実行し、結果表を取得
			ResultSet rs = pStmt.executeQuery();

			// レコード内容を取り出す
			while (rs.next()) {
				String lastName = rs.getString("LAST_NAME");
				String firstName = rs.getString("FIRST_NAME");
				String lastFurigana = rs.getString("LAST_FURIGANA");
				String firstFurigana = rs.getString("FIRST_FURIGANA");
				String gender = rs.getString("GENDER");

				Date birthDate = rs.getDate("BIRTH");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String birth = sdf.format(birthDate);

				String phone1 = rs.getString("PHONE01");
				String phone2 = rs.getString("PHONE02");
				String phone3 = rs.getString("PHONE03");
				String zip1 = rs.getString("ZIP01");
				String zip2 = rs.getString("ZIP02");
				String address = rs.getString("ADRESS");
				String email = rs.getString("EMAIL");
				String password = rs.getString("PASSWORD");

				User user = new User(lastName, firstName, lastFurigana, firstFurigana, gender, birth, phone1, phone2, phone3,
						zip1, zip2, address, email, password);

				list.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}

	/**
	 * 個人データをDBに送る
	 */
	public boolean create(Connection conn, User user) throws SQLException {

		String sql = "INSERT INTO USER_DATA(" + "LAST_NAME, FIRST_NAME, LAST_FURIGANA, FIRST_FURIGANA, GENDER, BIRTH, "
				+ "PHONE01, PHONE02, PHONE03, ZIP01, ZIP02, ADRESS, EMAIL, PASSWORD"
				+ ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement pStmt = conn.prepareStatement(sql);
		pStmt.setString(1, user.getLastName());
		pStmt.setString(2, user.getFirstName());
		pStmt.setString(3, user.getLastFurigana());
		pStmt.setString(4, user.getFirstFurigana());
		pStmt.setString(5, user.getGender());
		pStmt.setDate(6, java.sql.Date.valueOf(user.getBirth()));
		pStmt.setString(7, user.getPhone1());
		pStmt.setString(8, user.getPhone2());
		pStmt.setString(9, user.getPhone3());
		pStmt.setString(10, user.getZip1());
		pStmt.setString(11, user.getZip2());
		pStmt.setString(12, user.getAddress());
		pStmt.setString(13, user.getEmail());

		// 暗証番号をハッシュ化
		String hashPassword = PassWordEncryption.hash(user.getPassword());
		log.info("ハッシュ化：" + hashPassword);
		pStmt.setString(14, hashPassword);

		int result = pStmt.executeUpdate();
		return result == 1;
	}

	/**
	 * メールアドレスをキーにしてDBからデータを取り出す
	 */
	public User setUserData(ResultSet rs) throws SQLException {

		User user = null;
		String lastName = rs.getString("LAST_NAME");
		String firstName = rs.getString("FIRST_NAME");
		String lastFurigana = rs.getString("LAST_FURIGANA");
		String firstFurigana = rs.getString("FIRST_FURIGANA");
		String gender = rs.getString("GENDER");

		Date birthDate = rs.getDate("BIRTH");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String birth = sdf.format(birthDate);

		String phone1 = rs.getString("PHONE01");
		String phone2 = rs.getString("PHONE02");
		String phone3 = rs.getString("PHONE03");
		String zip1 = rs.getString("ZIP01");
		String zip2 = rs.getString("ZIP02");
		String address = rs.getString("ADRESS");
		String email = rs.getString("EMAIL");
		String password = rs.getString("PASSWORD");

		user = new User(lastName, firstName, lastFurigana, firstFurigana, gender, birth, phone1, phone2, phone3, zip1, zip2,
				address, email, password);

		return user;
	}

}
