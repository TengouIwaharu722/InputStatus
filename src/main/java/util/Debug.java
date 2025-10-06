package util;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Map;

/**
 * デバッグ出力を管理する
 * ※アプリケーション提出時にSystem.out.printLnがあれば
 * セキュリティ的に不適当
 */
public class Debug {
	//アプリケーション提出時にfalseに変更
	private static final boolean IS_DEBUG_MODE = true;

	/**
	 * あらゆる型のオブジェクトをコンソールに出力します
	 */
	public static void print(String callerClassName,Object object) {
		if (!IS_DEBUG_MODE) {
			return; // デバッグモードOFFなら何もしない
		}
		Class<?> callerClass = null;

		try {
			// 文字列から Class オブジェクトを取得
			callerClass = Class.forName(callerClassName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			// callerClass が null のままになる
		}

		// ------------------------------------------------
		// 1. 呼び出し元の情報を取得
		// ------------------------------------------------
		String callerInfo = "";
		if (callerClass != null) {
			String fullClassName = callerClass.getName();

			// パッケージ名とクラス名に分割
			int lastDot = fullClassName.lastIndexOf('.');
			String className = fullClassName.substring(lastDot + 1);
			String packageName = (lastDot > 0) ? fullClassName.substring(0, lastDot) : "(default)";

			// 例: [config.AppConstants] の形式
			callerInfo = String.format("[%s.%s] ", packageName, className);
		} else {
			// Classオブジェクトが取得できなかった場合は、受け取った文字列を使用
			callerInfo = String.format("[Caller:%s] ", callerClassName);
		}

		// ------------------------------------------------
		// 2. オブジェクトの内容を処理 (前回と同様)
		// ------------------------------------------------
		String outputMessage;

		if (object == null) {
			outputMessage = "null";
		} else if (object.getClass().isArray()) {
			//オブジェクト配列 (String[], Integer[], MyClass[]) の場合
			if (object instanceof Object[]) {
				outputMessage = Arrays.deepToString((Object[]) object);
			} else {
				// プリミティブ配列 (int[], char[], long[]など) の場合
				StringBuilder sb = new StringBuilder();
				sb.append("[");
				int length = Array.getLength(object); // リフレクションで配列の長さを取得

				for (int i = 0; i < length; i++) {
					// リフレクションで i 番目の要素を取得し、文字列に変換
					Object element = Array.get(object, i);
					sb.append(element);

					if (i < length - 1) {
						sb.append(", ");
					}
				}
				sb.append("]");
				outputMessage = sb.toString();
			}
		} else if (object instanceof Map) {
			// Mapの場合：内容をそのまま表示（toStringで十分展開される）
			outputMessage = object.toString();
		} else {
			// その他のオブジェクト、String、Listなどの場合
			outputMessage = object.toString();
		}

		System.out.println("[DEBUG] " + callerInfo + outputMessage);
	}

	/**
	 * クラス名がthisの場合用
	 */
	public static void print(Object callerInstance,Object object) {
		if (callerInstance != null) {
			// インスタンスから完全修飾クラス名を取得してメソッドを呼び出す
			print(object, callerInstance.getClass().getName());
		} else {
			print(object);
		}
	}

	/**
	 * 引数が1津の場合（クラス名の出力不要）
	 */
	public static void print(Object object) {
		if (IS_DEBUG_MODE) {
			System.out.println("[DEBUG] " + object.toString());
		}
	}
}
