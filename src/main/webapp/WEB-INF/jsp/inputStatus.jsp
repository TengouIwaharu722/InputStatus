<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
				<label>氏名 <span class="required">必須</span>
				<% if (user != null && (!user.getValidateMap().get(LAST_NAME)||!user.getValidateMap().get(FIRST_NAME))) { %>
                    <span class="error">：<%= ERROR_NAME_INFO %></span>
                <% } %>
				</label>
				<div class="split-input">
					<input type="text" id="lastName" name="lastName" placeholder="（例）山田" required 
					value="<%= user != null ? user.getLastName() : "" %>">
					
					<input type="text" id="firstName" name="firstName" placeholder="（例）太郎" required
					value="<%= user != null ? user.getFirstName() : "" %>">
				</div>
			</div>

			<div class="form-group">
				<label>フリガナ(カタカナ)<span class="required">必須</span>
				<% if (user != null && (!user.getValidateMap().get(LAST_FURIGANA)||!user.getValidateMap().get(FIRST_FURIGANA))) { %>
                    <span class="error">：<%= ERROR_NAME_KANA_INFO %></span>
                <% } %>
                </label>
				<div class="split-input">
					<input type="text" id="lastFurigana" name="lastFurigana" placeholder="（例）ヤマダ" required
					value="<%= user != null ? user.getLastFurigana(): "" %>">
					
					<input type="text"id="firstFurigana" name="firstFurigana" placeholder="（例）タロウ" required
					value="<%= user != null ? user.getFirstFurigana(): "" %>">
				</div>
			</div>

			<div class="form-group">
				<label>性別<span class="required">必須</span>
				</label>
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
				<label for="birth">生年月日<span class="required">必須</span>
				<% if (user != null && (!user.getValidateMap().get(BIRTH))) { %>
                    <span class="error">：<%= ERROR_BIRTH_INFO2 %></span>
                <% } %>
				</label> 
				<input type="date" id="birth" name="birth" required
				value="<%= user != null ? user.getBirth(): "" %>">
			</div>

			<div class="form-group">
				<label for="phone1">電話番号<span class="required">必須</span>
				<% if (user != null && (!user.getValidateMap().get(PHONE1))) { %>
                    <span class="error">：<%= ERROR_TELL_INFO2 %></span>
                <% } %>
				</label>
				<div class="split-input">
					<input type="text" id="phone1" name="phone1" maxlength="4" placeholder="090" required 
					value="<%= user != null ? user.getPhone1(): "" %>">
					- 
					<input type="text" id="phone2" name="phone2" maxlength="4" placeholder="1234" required
					value="<%= user != null ? user.getPhone2(): "" %>">
					- 
					<input type="text" id="phone3" name="phone3" maxlength="4" placeholder="5678" required
					value="<%= user != null ? user.getPhone3(): "" %>">					
				</div>
			</div>


			<div class="form-group">
				<label for="zip1">郵便番号<span class="required">必須</span>
				<% if (user != null && (!user.getValidateMap().get(ZIP1))) { %>
                    <span class="error">：<%= ERROR_ZIP_INFO2 %></span>
                <% } %>				
				</label>
				<div class="split-input">
					<input type="text" id="zip1" name="zip1" maxlength="3" placeholder="123" required
					value="<%= user != null ? user.getZip1(): "" %>">
					- 
					<input type="text" id="zip2" name="zip2" maxlength="4" placeholder="4567" required
					value="<%= user != null ? user.getZip2(): "" %>">
				</div>
			</div>


			<div class="form-group">
				<label for="address">住所<span class="required">必須</span>
				<% if (user != null && (!user.getValidateMap().get(ADDRESS))) { %>
                    <span class="error">：<%= ERROR_DEFAULT_INFO %></span>
                <% } %>								
				</label> 
				<input type="text" id="address" name="address" placeholder="大阪市中央区×××一丁目" required
					value="<%= user != null ? user.getAddress(): "" %>">				
			</div>

			<div class="form-group">
				<label for="email">メールアドレス(ログインID)<span class="required">必須</span>
				<% 
				if (user != null && (!user.getValidateMap().get(EMAIL))) { 
				%>
       <span class="error">：<%= ERROR_DEFAULT_INFO %></span>
    <% 
    } else if(user != null &&(!user.getValidateMap().get(EMAIL_ORIZIN))){
    %>
    			<span class="error">：<%= ERROR_MAIL_USED %></span>
    <% 
    } 
    %>
				</label> 
				<input type="email" id="email" name="email" placeholder="testmail@test.co.jp" required
					value="<%= user != null ? user.getEmail(): "" %>">
			</div>


			<div class="form-group">
				<label for="password">パスワード<span class="required">必須</span>
				<% if (user != null && (!user.getValidateMap().get(PASSWORD))) { %>
                    <span class="error">：<%= ERROR_PASSWORD_INFO2 %></span>
                <% } %>																
				</label>
				<div class="password-wrapper">
					<input type="password" id="password" name="password" placeholder="1234Ty@Gp" required
					value="<%= user != null ? user.getPassword(): "" %>">					
					<span class="toggle-password" onclick="togglePassword()">👁️</span>
				</div>
				<small class="form-text text-muted">
					※<%= ERROR_PASSWORD_INFO2 %></small>
			</div>


			<div class="form-group">
				<button type="submit">決定</button>
			</div>
		</form>
	</div>
	<jsp:include page = "footer.jsp"/>
</body>
</html>
