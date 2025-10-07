package model;

import static config.AppConstants.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import config.AppConstants;
import util.Debug;

/**
 * ユーザーの入力情報
 */
public class User implements Serializable {

	/**	デバッグ出力用	*/
	private static final String FQCN = User.class.getName();

	/**	姓	*/
	private String lastName;
	/**	名	*/
	private String firstName;

	/**	姓（フリガナ）	*/
	private String lastFurigana;
	/**	名（フリガナ）	*/
	private String firstFurigana;

	/**	性別	*/
	private String gender;
	/**	生年月日	*/
	private String birth;

	/**	電話番号1	*/
	private String phone1;
	/**	電話番号2	*/
	private String phone2;
	/**	電話番号3	*/
	private String phone3;

	/**	郵便番号1	*/
	private String zip1;
	/**	郵便番号2	*/
	private String zip2;
	/**	住所	*/
	private String address;
	/**	メールアドレス	*/
	private String email;
	/**	パスワード	*/
	private String password;

	//----------------------------------------------
	/**	各パラメータが入力間違いか否かのMAP	*/
	private Map<String, Boolean> validateMap = new HashMap<>();

	//----------------------------------------------
	//
	/**
	 * バリデーションチェック
	 * 各項目内容のチェックして正否結果をMapに格納
	 */
	public void allValidete() {

		//各パラメータが入力間違いか否かのMAP初期化
		Map<String, Boolean> validateMap = new HashMap<>();
		validateMap = AppConstants.PARAM_LIST.stream()
				.collect(Collectors.toMap(
						Function.identity(), //キーマッパー: リストの要素そのもの (String) をキーにする
						key -> true //値マッパー: すべてのキーに対して固定の値を設定する
				));
		//出力の確認
		Debug.print(FQCN, "バリデーションチェック:"+validateMap);

		//姓のチェック
		boolean isLastName = isValidName(this.lastName);
		validateMap.put(LAST_NAME, isLastName);

		//名のチェック
		boolean isFirstName = isValidName(this.firstName);
		validateMap.put(FIRST_NAME, isFirstName);

		//姓（カナ）のチェック
		boolean isLastNameKana = isValidKanaName(this.lastFurigana);
		validateMap.put(LAST_FURIGANA, isLastNameKana);

		//名（カナ）のチェック
		boolean isFirstNameKana = isValidKanaName(this.firstFurigana);
		validateMap.put(FIRST_FURIGANA, isFirstNameKana);

		//誕生日のチェック
		boolean isBirth = isValidBirth(this.birth);
		validateMap.put(BIRTH, isBirth);

		//電話番号のチェック(入力は3分割されたものなので合体。keyは1つだけでいいのでPHONE1使用）
		String phone = this.phone1+this.phone2+this.phone3;
		boolean isPhone = isValidPhone(phone);
		validateMap.put(PHONE1, isPhone);
		
		//郵便番号のチェック(入力は2分割されたものなので合体。keyは1つだけでいいのでZIP1使用）
		String zip = this.zip1+this.zip2;
		boolean isZip = isValidZip(zip);
		validateMap.put(ZIP1, isZip);
		
		//住所のチェック
		boolean isAddress = isValidAddress(this.address);
		validateMap.put(ADDRESS, isAddress);

		//メールアドレスのチェック
		boolean isEmail = isValidEmail(this.email);
		validateMap.put(EMAIL, isEmail);

		//パスワードのチェック
		boolean isPassword = isValidPassword(this.password);
		validateMap.put(PASSWORD, isPassword);
		
		//バリデーションの結果をMapに格納
		setValidateMap(validateMap);

	}

	/**
	 * 氏名
	 * 「英数字・ひらがな・漢字・カタカナのみ許可」
	 */
	public boolean isValidName(String param) {

		if (param == null || "".equals(param)) {
			Debug.print(FQCN, "名前が入力されていません");
			return false;
		} else if (!param.matches("^[a-zA-Z0-9ぁ-んァ-ヶ一-龯々ー]+$")) {
			Debug.print(FQCN, "名前は英数字・ひらがな・漢字・カタカナのみ有効です");
			return false;
		}
		return true;
	}

	/**
	 * 氏名（カタカナ）
	 */
	public boolean isValidKanaName(String param) {
		if (param == null || "".equals(param)) {
			Debug.print(FQCN, "名（カナ）が入力されていません");
			return false;
		} else if (!param.matches("^[ァ-ヺー・]+$")) {
			Debug.print(FQCN, "名（カナ）はカタカナのみ有効");
			return false;
		}
		return true;
	}

	/**
	 * 誕生日
	 */
	public boolean isValidBirth(String param) {
		// 最大年齢（150歳）
		final int MAX_AGE = 150;

		if (param == null || "".equals(param)) {
			Debug.print(FQCN, "誕生日が入力されていません");
			return false;
		} else if (!param.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
			;
			Debug.print(FQCN, "誕生日は半角数字のみ有効");
			return false;
		}

		try {

			// 入力形式を定義（例：yyyy-MM-dd）
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			// 文字列をLocalDateに変換（存在しない日付なら例外）
			LocalDate birthday = LocalDate.parse(param, formatter);

			// 今日の日付
			LocalDate today = LocalDate.now();

			// 未来の日付はNG
			if (birthday.isAfter(today)) {
				return false;
			}

			// 年齢を計算
			int age = Period.between(birthday, today).getYears();

			// 150歳以上はNG
			if (age > MAX_AGE) {
				return false;
			}

			// すべてOK
			return true;

		} catch (DateTimeParseException e) {
			Debug.print(FQCN, "存在しない日付");
			return false;
		}
	}

	
	/**
	 * 電話番号
	 * 固定電話0から始まる10桁。携帯電話11桁で070, 080, 090で始まる数字です
	 */
	public boolean isValidPhone(String param) {
		if (param == null || "".equals(param)) {
			Debug.print(FQCN, "電話番号が入力されていません");
			return false;
		} else if (!param.matches("^0\\d{9,10}$")) {
			Debug.print(FQCN, "無効な電話番号です");
			return false;
		}
		return true;
	}

	/**
	 * 郵便番号
	 * 7桁の数字のみ有効。
	 */
	public boolean isValidZip(String param) {
		if (param == null || "".equals(param)) {
			Debug.print(FQCN, "郵便番号が入力されていません");
			return false;
		} else if (!param.matches("^\\d{7}$")) {
			Debug.print(FQCN, "無効な郵便番号です");
			return false;
		}
		return true;
	}

	/**
	 * 住所
	 * 特殊文字を除く
	 */
	public boolean isValidAddress(String param) {
		if (param == null || "".equals(param)) {
			Debug.print(FQCN, "住所が入力されていません");
			return false;
		} else if (!param.matches("^[一-龠ぁ-ゔァ-ヴー0-9０-９a-zA-ZＡ-Ｚａ-ｚ\\s\\-\\.\\/,\\(\\)\\&・]*$")) {
			Debug.print(FQCN, "無効な記号が入力されています");
			return false;
		}
		return true;
	}

	/**
	 * メールアドレス
	 */
	public boolean isValidEmail(String param) {
		if (param == null || "".equals(param)) {
			Debug.print(FQCN, "メールアドレスが入力されていません");
			return false;
		} else if (!param.matches("^[a-zA-Z0-9!#$%&'*+\\/=?^_`{|}~-]+(\\.[a-zA-Z0-9!#$%&'*+\\/=?^_`{|}~-]+)*@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$")) {
			Debug.print(FQCN, "無効な記号が入力されています");
			return false;
		}
		return true;
	}
	
	/**
	 * パスワード
	 * 8以上16文字以内。大小英文字と記号を1文字以上含む
	 */
	public boolean isValidPassword(String param) {
		if (param == null || "".equals(param)) {
			Debug.print(FQCN, "パスワードが入力されていません");
			return false;
		} else if (!param.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,16}$")) {
			Debug.print(FQCN, "無効なパスワードです");
			return false;
		}
		return true;
	}
	
	//------------------------------------------------
	public User() {
	}

	public User(String lastName, String firstName, String lastFurigana, String firstFurigana, String gender,
			String birth, String phone1, String phone2, String phone3, String zip1, String zip2, String address,
			String email, String password) {
		this.lastName = lastName;
		this.firstName = firstName;
		this.lastFurigana = lastFurigana;
		this.firstFurigana = firstFurigana;
		this.gender = gender;
		this.birth = birth;
		this.phone1 = phone1;
		this.phone2 = phone2;
		this.phone3 = phone3;
		this.zip1 = zip1;
		this.zip2 = zip2;
		this.address = address;
		this.email = email;
		this.password = password;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastFurigana() {
		return lastFurigana;
	}

	public void setLastFurigana(String lastFurigana) {
		this.lastFurigana = lastFurigana;
	}

	public String getFirstFurigana() {
		return firstFurigana;
	}

	public void setFirstFurigana(String firstFurigana) {
		this.firstFurigana = firstFurigana;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getPhone3() {
		return phone3;
	}

	public void setPhone3(String phone3) {
		this.phone3 = phone3;
	}

	public String getZip1() {
		return zip1;
	}

	public void setZip1(String zip1) {
		this.zip1 = zip1;
	}

	public String getZip2() {
		return zip2;
	}

	public void setZip2(String zip2) {
		this.zip2 = zip2;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Map<String, Boolean> getValidateMap() {
		return validateMap;
	}

	public void setValidateMap(Map<String, Boolean> validateMap) {
		this.validateMap = validateMap;
	}

}
