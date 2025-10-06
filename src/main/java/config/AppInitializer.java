package config;

/**
 * 初期化を一元管理するクラス
 */
public class AppInitializer {
	/**定数を定義したクラス*/
	private static final String DEFINE_URL ="config.AppConstants";
	private static final String USER_URL ="model.User";

	public static void initiaLize() {
		try {
			Class.forName(DEFINE_URL); 
			//Class.forName(USER_URL); 
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
}
