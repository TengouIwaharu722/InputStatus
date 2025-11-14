package util;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;
import servlet.Tarminal;


/**
 * コネクションプール（Hikari）を使った 接続マネージャ
 * HikariはMavenで設定。
 * C:/Users/7Java5/Desktop/サーブレットJSP/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/InputStatus/data/persondb
 * ueda@gmail.com
 * 1234aSc@
 * 
 */
@Slf4j
public class DBManager {

	final static String DB_USER = "sa";		//DBの接続ID
	final static String DB_PASS = "";			//DBの接続パスワード
	private static HikariDataSource dataSource;

	static {
		
				try {
						log.info("jdbc:h2:file:"+	Tarminal.dataFolderPath+"/persondb");
						HikariConfig config = new HikariConfig();
						config.setJdbcUrl("jdbc:h2:file:" + Tarminal.dataFolderPath + 
								"/persondb");
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
