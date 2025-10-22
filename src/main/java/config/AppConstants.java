package config;

import java.util.Arrays;
import java.util.List;

/**
 * 定数クラス
 */
final public class AppConstants {

	private AppConstants() {
	};

	/** セッションに保持する変数名 */
	public static final String SESSION_NAME = "user";

	/** JavaBeansで設定した個人情報を取り出すkey */
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
	public static final String EMAIL_ORIZIN = "emailOrizin";
	public static final String PASSWORD = "password";

	/** ユーザ入力画面 */
	public static final String INPUT_FORM_URL = "WEB-INF/jsp/inputStatus.jsp";

	/** 登録リザルトの画面 */
	public static final String INPUT_RESULT_URL = "WEB-INF/jsp/inputResult.jsp";

	/** 登録完了の画面 */
	public static final String RESISTER_PAGE_URL = "WEB-INF/jsp/registerDone.jsp";

	/** ログイン成功の画面 */
	public static final String LOGIN_SUCCESS_URL = "WEB-INF/jsp/loginSuccess.jsp";
	
	
	/** 入力値のkeyのリスト */
	public static final List<String> PARAM_LIST;

	// ---------------------------------------------------------------------
	// 入力値の正否MAPを作成する第一段階、keyの初期化
	static {
		PARAM_LIST = Arrays.asList(LAST_NAME, FIRST_NAME, LAST_FURIGANA, FIRST_FURIGANA, GENDER, BIRTH, PHONE1, PHONE2,
				PHONE3, ZIP1, ZIP2, ADDRESS, EMAIL, PASSWORD, EMAIL_ORIZIN);

	}
	// ---------------------------------------------------------------------
	// エラー文言
	public static final String ERROR_DEFAULT_INFO = "無効な値が入力されています";
	public static final String ERROR_NAME_NULL = "名前が入力されていません";
	public static final String ERROR_NAME_INFO = "名前は英数字・ひらがな・漢字・カタカナのみ有効です";
	public static final String ERROR_NAME_KANA_NULL = "名（カナ）が入力されていません";
	public static final String ERROR_NAME_KANA_INFO = "名（カナ）はカタカナのみ有効";
	public static final String ERROR_BIRTH_NULL = "生年月日が入力されていません";
	public static final String ERROR_BIRTH_INFO = "生年月日は半角数字のみ有効";
	public static final String ERROR_BIRTH_INFO2 = "無効な生年月日が入力されています";
	public static final String ERROR_TELL_NULL = "電話番号が入力されていません";
	public static final String ERROR_TELL_INFO = "無効な電話番号です";
	public static final String ERROR_TELL_INFO2 = "固定電話0から始まる10桁。携帯電話11桁で070, 080, 090で始まる数字です";
	public static final String ERROR_ZIP_NULL = "郵便番号が入力されていません";
	public static final String ERROR_ZIP_INFO = "無効な郵便番号です";
	public static final String ERROR_ZIP_INFO2 = "3桁-4桁の数字を入力してください";
	public static final String ERROR_ADRESS_NULL = "住所が入力されていません";
	public static final String ERROR_ADRESS_INFO = "無効な記号が入力されています";
	public static final String ERROR_MAIL_NULL = "メールアドレスが入力されていません";
	public static final String ERROR_MAIL_INFO = "無効な記号が入力されています";
	public static final String ERROR_MAIL_USED = "そのメールアドレスは使用されています";
	public static final String ERROR_PASSWORD_NULL = "パスワードが入力されていません";
	public static final String ERROR_PASSWORD_INFO = "無効なパスワードです";
	public static final String ERROR_PASSWORD_INFO2 = "8以上16文字以内。大小英文字と記号を1文字以上含むものを入力してください";


	
	
	
	
}
