<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.User" %>
<%
    model.User user = (model.User) session.getAttribute("user");
%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="Content-Language" content="ja">
<link rel="stylesheet" type="text/css" href="css/input_result.css">
<title>入力内容確認</title>
</head>
<body>
  <div class="confirm-container">
    <h1>入力内容の確認</h1>

    <div class="confirm-item">
      <div class="confirm-label">氏名</div>
      <div class="confirm-value"><%= user.getLastName() %> <%= user.getFirstName() %></div>
    </div>

    <div class="confirm-item">
      <div class="confirm-label">フリガナ</div>
      <div class="confirm-value"><%= user.getLastFurigana() %> <%= user.getFirstFurigana() %></div>
    </div>

    <div class="confirm-item">
      <div class="confirm-label">性別</div>
      <div class="confirm-value"><%= user.getGender() %></div>
    </div>

    <div class="confirm-item">
      <div class="confirm-label">生年月日</div>
      <div class="confirm-value"><%= user.getBirth() %></div>
    </div>

    <div class="confirm-item">
      <div class="confirm-label">電話番号</div>
      <div class="confirm-value">
        <%= user.getPhone1() %>-<%= user.getPhone2() %>-<%= user.getPhone3() %>
      </div>
    </div>

    <div class="confirm-item">
      <div class="confirm-label">郵便番号</div>
      <div class="confirm-value">
        <%= user.getZip1() %>-<%= user.getZip2() %>
      </div>
    </div>

    <div class="confirm-item">
      <div class="confirm-label">住所</div>
      <div class="confirm-value"><%= user.getAddress() %></div>
    </div>

    <div class="confirm-item">
      <div class="confirm-label">メールアドレス</div>
      <div class="confirm-value"><%= user.getEmail() %></div>
    </div>

    <div class="confirm-item">
      <div class="confirm-label">パスワード</div>
      <div class="confirm-value" id="masked-password"></div>
       <script>
    		const password = "<%= user != null ? user.getPassword() : "" %>";
    		const masked = "●".repeat(password.length);
    		document.getElementById("masked-password").textContent = masked + "（非表示）";
  		</script>      
    </div>

    <div class="button-group">
      <form action="Tarminal" method="get" style="display:inline;">
        <button type="submit" class="back">戻る</button>
      </form>
      <form action="Tarminal" method="get" style="display:inline;">
        <input type="hidden" name="action" value="done">
        <button type="submit" class="submit">登録する</button>
      </form>
    </div>
  </div>
</body>
</html>

