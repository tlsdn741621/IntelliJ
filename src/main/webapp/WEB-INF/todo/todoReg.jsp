<%--
  Created by IntelliJ IDEA.
  User: it
  Date: 25. 6. 23.
  Time: 오전 11:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>등록 임시 화면 </h1>
<form action="/todo/register2 " method="post">
    <div>
    <input type="text" name="title" placeholder="todo의 제목을 입력 해주세요">
    </div>
    <div>
    <input type="date" name="dueDate">
    </div>
    <div>
    <button type="reset">리셋</button>
    <button type="submit">등록처리</button>
    </div>

</form>
</body>
</html>
