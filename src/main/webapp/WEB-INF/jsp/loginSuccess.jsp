<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="model.User" %>
<%
  User user = (User) session.getAttribute("user");
  String userName = user != null ? user.getLastName()+user.getFirstName() : "ゲスト";
%>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title>ログイン成功</title>
  <link rel="stylesheet" type="text/css" href="css/loginsuccess.css">
</head>
<body>
  <div class="success-container">
    <h1>ログイン成功</h1>
    <p>ようこそ <span class="username"><%= userName %></span> さん</p>
  </div>
</body>
</html>