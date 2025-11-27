package util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j; // Slf4jをインポート

// Slf4jアノテーションを追加
@Slf4j
@WebListener
public class DBInitializerListener implements ServletContextListener {

    private static HikariDataSource dataSource;
    
    // DB接続情報
    private final String DB_USER = "sa";
    private final String DB_PASS = "";
    
    // データベースファイルパスを保持する変数（log.infoで使用）
    private static String dataFolderPath; 
    
    // DBManagerからdataSourceを取得するためのGetter
    public static HikariDataSource getDataSource() {
        return dataSource;
    }
    
    /**
     * Webアプリケーションが起動されたときに実行されます。
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("--- DBInitializerListener: アプリケーション起動（データベース接続初期化開始） ---");

        // 【１】アプリケーションのルートパスを取得し、dataFolderPathを設定
        ServletContext context = sce.getServletContext();
        dataFolderPath = context.getRealPath("/data");
        
        if (dataFolderPath == null) {
            log.error("コンテキストパスの取得に失敗しました。アプリケーションを停止します。");
            throw new RuntimeException("アプリケーションの'/data'パスを取得できませんでした。");
        }
        
        log.info("データファイルパス: {}", dataFolderPath);
        
        // 【２】HikariCPの初期化
        try {
            HikariConfig config = new HikariConfig();
            
            // 取得した絶対パスを使用してJDBC URLを設定
            String jdbcUrl = "jdbc:h2:file:" + dataFolderPath + "/persondb";
            log.info("JDBC URL: {}", jdbcUrl);
            
            config.setJdbcUrl(jdbcUrl);
            config.setUsername(DB_USER);
            config.setPassword(DB_PASS);
            config.setDriverClassName("org.h2.Driver");
            
            config.setMaximumPoolSize(10);
            config.setConnectionTimeout(10000); 

            dataSource = new HikariDataSource(config);
            log.info("--- HikariCPの初期化に成功しました。 ---");
            
        } catch (Exception e) {
            // 初期化失敗時はスタックトレースを含めてログ出力
            log.error("HikariCPの初期化に致命的なエラーが発生しました。アプリケーションを停止します。", e);
            throw new RuntimeException("データベース接続プールの初期化に失敗しました。", e);
        }

        // 【３】DB接続の確認とテーブルの初期化
        try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {

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
            log.info("初期化完了: 'users' テーブルの存在確認または作成が完了しました。");

        } catch (SQLException e) {
            log.error("データベースのテーブル初期化に失敗しました。アプリケーションを停止します。", e);
            throw new RuntimeException("DBテーブル初期化失敗", e);
        }
    }

    /**
     * Webアプリケーションが停止されたときに実行されます。
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (dataSource != null) {
            dataSource.close();
            log.info("--- DBInitializerListener: アプリケーション停止（HikariCP接続プールをクローズ） ---");
        }
    }
}