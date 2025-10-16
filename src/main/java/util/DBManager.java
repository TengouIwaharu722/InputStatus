package util;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * コネクションプール（Hikari）を使った 接続マネージャ
 */
public class DBManager {

	// 学校jsp
	final static String JDBC_URL = "jdbc:h2:file:C:/Users/7Java5/Desktop/H2DB/input";
	// 自宅
	// final static String JDBC_URL ="jdbc:h2:file:C:/Users/uedak/Desktop/H2/data/dokotubu";
	final static String DB_USER = "sa";		//DBの接続ID
	final static String DB_PASS = "";			//DBの接続パスワード
	private static HikariDataSource dataSource;

	static {
		
				try {
						HikariConfig config = new HikariConfig();
						config.setJdbcUrl(JDBC_URL);
						config.setUsername(DB_USER);
						config.setPassword(DB_PASS);
						config.setDriverClassName("org.h2.Driver");
						config.setMaximumPoolSize(10); 				//最大接続数
						config.setConnectionTimeout(10000);		//待機時間（デフォルトは30秒）
						
						dataSource = new HikariDataSource(config);
				}catch(Exception e) {
					e.printStackTrace();
				}
	}

	// 接続
	public static Connection getConnection() throws SQLException {
		if (dataSource == null) {
      throw new IllegalStateException("HikariCPの初期化に失敗しています。dataSourceがnullです。");
		}
		return dataSource.getConnection();
	}

}
