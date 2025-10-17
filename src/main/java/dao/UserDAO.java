package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.User;


/**
 * 個人データの取得とセット
 */
public class UserDAO {
	/**
	 * 個人データをDBに送る
	 */
	public boolean create(Connection conn, User user) throws SQLException {

		String sql = "INSERT INTO USERDATA("
		    + "last_name, first_name, last_furigana, first_furigana, gender, birth, "
		    + "phone1, phone2, phone3, zip1, zip2, address, email, password"
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
		pStmt.setString(14, user.getPassword());


		int result = pStmt.executeUpdate();
		return result == 1;
	}
}
