package util;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

/**
 * コネクションプール（Hikari）から接続を取得するマネージャ
 * 初期化処理は listener.DBInitializerListener で行われます。
 */
@Slf4j
public class DBManager {
    
    // DBManagerは接続情報や初期化処理を持たなくなります。
    // 静的変数のdataSourceも不要です。

	// 接続
	public static Connection getConnection() throws SQLException {
        
        // 【重要】リスナーから初期化済みのDataSourceを取得します
        HikariDataSource dataSource = DBInitializerListener.getDataSource(); 
        
        if (dataSource == null) {
            // 初期化に失敗している場合は、リスナー側で既にRuntimeExceptionが投げられているはずですが、
            // 念のためこのチェックを残します。
            log.error("HikariCPの初期化に失敗しています。リスナーのログを確認してください。");
            throw new IllegalStateException("HikariCPの初期化に失敗しています。dataSourceがnullです。");
        }
        
        // 接続ログを出したい場合はここでlog.debug("DB接続を取得します")などを追加
		return dataSource.getConnection();
	}

}

