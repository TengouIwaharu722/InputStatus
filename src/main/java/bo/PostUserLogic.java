package bo;

import java.sql.Connection;
import java.sql.SQLException;

import dao.UserDAO;
import model.User;
import util.DBManager;

/**
 * 個人データをDBに入れる
 */
public class PostUserLogic {
	public boolean execute(User user) {
		try (Connection conn = DBManager.getConnection()) {
			conn.setAutoCommit(false); // トランザクション開始

			UserDAO dao = new UserDAO();
			boolean result = dao.create(conn, user);

			if (result) {
				conn.commit(); // 成功時コミット
			} else {
				conn.rollback(); // 失敗時ロールバック
			}
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
