package model;


import lombok.extern.slf4j.Slf4j;

/**
 * ユーザー登録を行うモデル
 * データベースにUserモデルを送る予定
 */
@Slf4j
public class RegisterLogic {

	/**	デバッグ出力用	*/
	private static final String FQCN = RegisterLogic .class.getName(); 

	public boolean execute(User user) {
		
		log.debug("ユーザーデータ登録終了");
		log.debug(user.getFirstName()+" "+user.getLastName()+"のデータ");
	
		return true;
	}
}
