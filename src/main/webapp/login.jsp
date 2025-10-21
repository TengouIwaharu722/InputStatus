

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="Content-Language" content="ja">
  <title>ログイン</title>
  <link rel="stylesheet" type="text/css" href="css/login.css">
  
</head>
<body>
  <div class="login-container">
    <h2>ログイン</h2>

    <% if (request.getAttribute("error") != null) { %>
      <div class="error-message"><%= request.getAttribute("error") %></div>
    <% } %>

    <form action="LogInServlet" method="post">
      <input type="text" name="mailaddress" placeholder="メールアドレス" required />
      <input type="password" name="password" placeholder="パスワード" required />
      <input type="submit" value="ログイン" />
    </form>

    <a href="Tarminal">新規登録はこちら</a>
  </div>
</body>
</html>
