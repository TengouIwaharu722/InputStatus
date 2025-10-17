package config;

import java.util.Arrays;
import java.util.List;

/**
 * 定数クラス
 */
final public class AppConstants {

	private AppConstants() {};

	/**	セッションに保持する変数名	*/
	public static final String SESSION_NAME = "user";

	/**	JavaBeansで設定した個人情報を取り出すkey	*/
	public static final String LAST_NAME = "lastName";
	public static final String FIRST_NAME = "firstName";
	public static final String LAST_FURIGANA = "lastFurigana";
	public static final String FIRST_FURIGANA = "firstFurigana";
	public static final String GENDER = "gender";
	public static final String BIRTH = "birth";
	public static final String PHONE1 = "phone1";
	public static final String PHONE2 = "phone2";
	public static final String PHONE3 = "phone3";
	public static final String ZIP1 = "zip1";
	public static final String ZIP2 = "zip2";
	public static final String ADDRESS = "address";
	public static final String EMAIL = "email";
	public static final String PASSWORD = "password";
	
	/**	ユーザ入力画面	*/
	public static final String INPUT_FORM_URL = "WEB-INF/jsp/inputStatus.jsp";
	
	/**	登録リザルトの画面	*/
	public static final String INPUT_RESULT_URL = "WEB-INF/jsp/inputResult.jsp";

	
	/**	登録完了の画面	*/
	public static final String RESISTER_PAGE_URL = "WEB-INF/jsp/registerDone.jsp";

	/**	入力値のkeyのリスト	*/	
	public static final List<String> PARAM_LIST;
	
	//----------------------------------------------
    //入力値の正否MAPを作成する第一段階、keyの初期化
    static {
        PARAM_LIST = Arrays.asList(LAST_NAME,FIRST_NAME,LAST_FURIGANA,FIRST_FURIGANA,
        				GENDER,BIRTH,PHONE1,PHONE2,PHONE3,ZIP1,ZIP2,
        				ADDRESS,EMAIL,PASSWORD);
        
    }
		
}
