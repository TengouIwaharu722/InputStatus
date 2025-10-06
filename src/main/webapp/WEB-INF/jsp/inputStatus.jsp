<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.User" %>
<%@ page import="model.User" %>
<%@ page import="static config.AppConstants.*" %>
<%
	Object obj = request.getSession().getAttribute("user");
	User user = null;
	if(obj!=null){
		user = (User)obj;
	}
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="Content-Language" content="ja">
<link rel="stylesheet" type="text/css" href="css/input_status.css">

<script>
	function togglePassword() {
		const pwField = document.getElementById("password");
		const toggleIcon = document.querySelector(".toggle-password");
		if (pwField.type === "password") {
			pwField.type = "text";
			toggleIcon.textContent = "🙈"; // 表示中のアイコン
		} else {
			pwField.type = "password";
			toggleIcon.textContent = "👁️"; // 非表示のアイコン
		}
	}
</script>

<title>ユーザー情報入力</title>
<body>
	<div class="form-container">
		<h1>ユーザー情報入力フォーム</h1>
		<form action="Tarminal" method="post">
			<div class="form-group">
				<label>氏名
				<% if (user != null && (!user.getValidateMap().get(LAST_NAME)||!user.getValidateMap().get(FIRST_NAME))) { %>
                    <span class="error">：名前は英数字・ひらがな・漢字・カタカナのみ有効です</span>
                <% } %>
				</label>
				<div class="split-input">
					<input type="text" id="lastName" name="lastName" placeholder="姓" required 
					value="<%= user != null ? user.getLastName() : "" %>">
					
					<input type="text" id="firstName" name="firstName" placeholder="名" required
					value="<%= user != null ? user.getFirstName() : "" %>">
				</div>
			</div>

			<div class="form-group">
				<label>フリガナ
				<% if (user != null && (!user.getValidateMap().get(LAST_FURIGANA)||!user.getValidateMap().get(FIRST_FURIGANA))) { %>
                    <span class="error">：カタカナのみ有効です</span>
                <% } %>
                </label>
				<div class="split-input">
					<input type="text" id="lastFurigana" name="lastFurigana" placeholder="セイ" required
					value="<%= user != null ? user.getLastFurigana(): "" %>">
					
					<input type="text"id="firstFurigana" name="firstFurigana" placeholder="メイ" required
					value="<%= user != null ? user.getFirstFurigana(): "" %>">
				</div>
			</div>


			<div class="form-group">
				<label>性別</label>
				<div class="radio-group">
					<label>
						<input type="radio" name="gender" value="男" 
						<%= user != null && "男".equals(user.getGender()) ? "checked" : "" %> required>男
					</label>
					<label>
						<input type="radio" name="gender" value="女"
						<%= user != null && "女".equals(user.getGender()) ? "checked" : "" %>>女
					</label> 
					<label>
						<input type="radio" name="gender" value="その他"
					 	<%= user != null && "その他".equals(user.getGender()) ? "checked" : "" %>>その他
					</label>
				</div>
			</div>

			<div class="form-group">
				<label for="birth">生年月日
				<% if (user != null && (!user.getValidateMap().get(BIRTH))) { %>
                    <span class="error">：無効な生年月日が入力されています</span>
                <% } %>
				</label> 
				<input type="date" id="birth" name="birth" required
				value="<%= user != null ? user.getBirth(): "" %>">
			</div>

			<div class="form-group">
				<label for="phone1">電話番号
				<% if (user != null && (!user.getValidateMap().get(PHONE1))) { %>
                    <span class="error">：固定電話0から始まる10桁。携帯電話11桁で070, 080, 090で始まる数字です</span>
                <% } %>
				</label>
				<div class="split-input">
					<input type="text" id="phone1" name="phone1" maxlength="4" required
					value="<%= user != null ? user.getPhone1(): "" %>">
					- 
					<input type="text" id="phone2" name="phone2" maxlength="4" required
					value="<%= user != null ? user.getPhone2(): "" %>">
					- 
					<input type="text" id="phone3" name="phone3" maxlength="4" required
					value="<%= user != null ? user.getPhone3(): "" %>">					
				</div>
			</div>


			<div class="form-group">
				<label for="zip1">郵便番号
				<% if (user != null && (!user.getValidateMap().get(ZIP1))) { %>
                    <span class="error">：3桁-4桁の数字を入力してください</span>
                <% } %>				
				</label>
				<div class="split-input">
					<input type="text" id="zip1" name="zip1" maxlength="3" required
					value="<%= user != null ? user.getZip1(): "" %>">
					- 
					<input type="text" id="zip2" name="zip2" maxlength="4" required
					value="<%= user != null ? user.getZip2(): "" %>">
				</div>
			</div>


			<div class="form-group">
				<label for="address">住所
				<% if (user != null && (!user.getValidateMap().get(ADDRESS))) { %>
                    <span class="error">：無効な値が入力されています</span>
                <% } %>								
				</label> 
				<input type="text" id="address" name="address" required
					value="<%= user != null ? user.getAddress(): "" %>">				
			</div>

			<div class="form-group">
				<label for="email">メールアドレス(ログインID)
				<% if (user != null && (!user.getValidateMap().get(EMAIL))) { %>
                    <span class="error">：無効な値が入力されています</span>
                <% } %>												
				</label> 
				<input type="email" id="email" name="email" required
					value="<%= user != null ? user.getEmail(): "" %>">
			</div>


			<div class="form-group">
				<label for="password">パスワード
				<% if (user != null && (!user.getValidateMap().get(PASSWORD))) { %>
                    <span class="error">：8以上16文字以内。大小英文字と記号を1文字以上含むものを入力してください</span>
                <% } %>																
				</label>
				<div class="password-wrapper">
					<input type="password" id="password" name="password" required
					value="<%= user != null ? user.getPassword(): "" %>">					
					<span class="toggle-password" onclick="togglePassword()">👁️</span>
				</div>
				<small class="form-text text-muted">
					※8以上16文字以内。大小英文字と記号を1文字以上含むものを入力してください</small>
			</div>


			<div class="form-group">
				<button type="submit">送信</button>
			</div>
		</form>
	</div>
</body>
</html>
