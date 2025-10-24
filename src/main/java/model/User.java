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
import dao.UserDAO;
import lombok.extern.slf4j.Slf4j;

/**
 * ユーザーの入力情報
 * ※Serializableはセッションに保存時に必要
 */
@Slf4j
public class User implements Serializable {

	
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
	private String phone01;
	/**	電話番号2	*/
	private String phone02;
	/**	電話番号3	*/
	private String phone03;

	/**	郵便番号1	*/
	private String zip01;
	/**	郵便番号2	*/
	private String zip02;
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
		log.debug("バリデーションチェック:"+validateMap);

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
		String phone = this.phone01+this.phone02+this.phone03;
		boolean isPhone = isValidPhone(phone);
		validateMap.put(PHONE1, isPhone);
		
		//郵便番号のチェック(入力は2分割されたものなので合体。keyは1つだけでいいのでZIP1使用）
		String zip = this.zip01+this.zip02;
		boolean isZip = isValidZip(zip);
		validateMap.put(ZIP1, isZip);
		
		//住所のチェック
		boolean isAddress = isValidAddress(this.address);
		validateMap.put(ADDRESS, isAddress);

		//メールアドレスのチェック
		//boolean isEmail = isValidEmail(this.email);
		//validateMap.put(EMAIL, isEmail);

		String isEmail = isValidEmail(this.email);
		
		switch(isEmail) {
		case "":		//email問題なし
			validateMap.put(EMAIL, true);
			break;
		case ERROR_MAIL_NULL:	//email入力なし
			validateMap.put(EMAIL, false);
			break;
		case ERROR_MAIL_INFO:	//無効な値が入っている
			validateMap.put(EMAIL, false);
			break;
		case ERROR_MAIL_USED:	//DB上に存在している
			validateMap.put(EMAIL, true);	//記述は問題なし
			validateMap.put(EMAIL_ORIZIN, false);	//DB上に存在
			break;
		}
				
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
			log.warn(AppConstants.ERROR_NAME_NULL);
			return false;
		} else if (!param.matches("^[a-zA-Z0-9ぁ-んァ-ヶ一-龯々ー]+$")) {
			log.warn(AppConstants.ERROR_NAME_INFO);
			return false;
		}
		return true;
	}

	/**
	 * 氏名（カタカナ）
	 */
	public boolean isValidKanaName(String param) {
		if (param == null || "".equals(param)) {
			log.warn(AppConstants.ERROR_NAME_KANA_NULL);
			return false;
		} else if (!param.matches("^[ァ-ヺー・]+$")) {
			log.warn(AppConstants.ERROR_NAME_KANA_INFO);			
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
			log.warn(AppConstants.ERROR_BIRTH_NULL);
			return false;
		} else if (!param.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
			log.warn(AppConstants.ERROR_BIRTH_INFO);
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
			log.warn(AppConstants.ERROR_BIRTH_INFO2);
			return false;
		}
	}

	
	/**
	 * 電話番号
	 * 固定電話0から始まる10桁。携帯電話11桁で070, 080, 090で始まる数字です
	 */
	public boolean isValidPhone(String param) {
		if (param == null || "".equals(param)) {
			log.warn(AppConstants.ERROR_TELL_NULL);
			return false;
		} else if (!param.matches("^0\\d{9,10}$")) {
			log.warn(AppConstants.ERROR_TELL_INFO);
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
			log.warn(AppConstants.ERROR_ZIP_NULL);
			return false;
		} else if (!param.matches("^\\d{7}$")) {
			log.warn(AppConstants.ERROR_ZIP_NULL);
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
			log.warn(AppConstants.ERROR_ADRESS_NULL);
			return false;
		} else if (!param.matches("^[一-龠ぁ-ゔァ-ヴー0-9０-９a-zA-ZＡ-Ｚａ-ｚ\\s\\-\\.\\/,\\(\\)\\&・]*$")) {
			log.warn(AppConstants.ERROR_ADRESS_INFO);
			return false;
		}
		return true;
	}
	
	/**
	 * メールアドレス2
	 */
	public String isValidEmail(String param) {
		String ress="";
		if (param == null || "".equals(param)) {
			ress = AppConstants.ERROR_MAIL_NULL;
		} else if (!param.matches("^[a-zA-Z0-9!#$%&'*+\\/=?^_`{|}~-]+(\\.[a-zA-Z0-9!#$%&'*+\\/=?^_`{|}~-]+)*@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$")) {
			ress = AppConstants.ERROR_MAIL_INFO;
		} else if(new UserDAO().checkUserEmail(param)) {
			ress = AppConstants.ERROR_MAIL_USED;
		}
		//メールアドレスに不具合があった場合ログを出す
		if(!("".equals(ress))) {
			log.warn(ress);	
		}
		return ress;
	}
	
	
	/**
	 * パスワード
	 * 8以上16文字以内。大小英文字と記号を1文字以上含む
	 */
	public boolean isValidPassword(String param) {
		if (param == null || "".equals(param)) {
			log.warn(AppConstants.ERROR_PASSWORD_NULL);
			return false;
		} else if (!param.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,16}$")) {
			log.warn(AppConstants.ERROR_PASSWORD_INFO);
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
		this.phone01 = phone1;
		this.phone02 = phone2;
		this.phone03 = phone3;
		this.zip01 = zip1;
		this.zip02 = zip2;
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
		return phone01;
	}

	public void setPhone1(String phone1) {
		this.phone01 = phone1;
	}

	public String getPhone2() {
		return phone02;
	}

	public void setPhone2(String phone2) {
		this.phone02 = phone2;
	}

	public String getPhone3() {
		return phone03;
	}

	public void setPhone3(String phone3) {
		this.phone03 = phone3;
	}

	public String getZip1() {
		return zip01;
	}

	public void setZip1(String zip1) {
		this.zip01 = zip1;
	}

	public String getZip2() {
		return zip02;
	}

	public void setZip2(String zip2) {
		this.zip02 = zip2;
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
