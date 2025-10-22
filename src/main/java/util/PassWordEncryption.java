package util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * パスワードの暗号化（bcypt）
 * ※SHA-256は高速すぎるため、辞書攻撃に弱いので
 * bcryptを使用します。
 */
public class PassWordEncryption {
	/**
	 * 文字のハッシュ化
	 * @param plainPassword 入力されたパスワード
	 * @return ハッシュ化した文字列
	 */
	public static String hash(String plainPassword) {
		return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
	}

	/**
	 * 入力値が正しいかチェック
	 * @param1 plainPassword 入力値
	 * @param2 hashedPassword DBから取得したハッシュ値
	 * @return
	 */
	public static boolean verify(String plainPassword, String hashedPassword) {
		return BCrypt.checkpw(plainPassword, hashedPassword);

	}
}
