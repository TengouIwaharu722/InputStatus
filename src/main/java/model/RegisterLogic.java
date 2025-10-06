package model;

import util.Debug;

/**
 * ユーザー登録を行うモデル
 * データベースにUserモデルを送る予定
 */
public class RegisterLogic {

	/**	デバッグ出力用	*/
	private static final String FQCN = RegisterLogic .class.getName(); 

	public boolean execute(User user) {
		Debug.print(FQCN,"ユーザーデータ登録終了");
		Debug.print(FQCN,user.getFirstName()+" "+user.getLastName()+"のデータ");

		return true;
	}
}
