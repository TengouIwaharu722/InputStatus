<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="css/register.css">
<title>ユーザー登録</title>
<style>
</style>
</head>
<body>
	<div class="container">
		<div
			style="display: flex; justify-content: center; align-items: center; gap: 16px;">
			<p style="margin: 0;">登録完了しました</p>
			<img src="<%=request.getContextPath()%>/images/ozigi.png"
				alt="ありがとう" width="50" height="50">
		</div>

		<a href="login.jsp" class="back-button">戻る</a>
	</div>
</body>
</html>