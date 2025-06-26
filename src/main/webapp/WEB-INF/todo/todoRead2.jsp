<%--
  Created by IntelliJ IDEA.
  User: it
  Date: 25. 6. 26.
  Time: 오전 10:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>임시 하나 조회 화면: 상세화면, 현재 데이터 별로 없어서 효과는 없는편. 연습용.</h1>
<div>
    <input type="text" name="tno" value="${dto.tno}" readonly>
</div>

<div>
    <input type="text" name="title" value="${dto.title}" readonly>
</div>

<div>
    <input type="date" name="dueDate" value="${dto.dueDate}">
</div>

<div>
    <input type="checkbox" name="finished" ${dto.finished ? "checked":""} readonly>
</div>
<div>
    <a href="/todo/update2?tno=${dto.tno}">수정폼</a>
    <a href="/todo/list2">목록가기</a>
</div>

<form id="form2" action="/todo/delete2" method="post">
    <input type="hidden" name="tno" value="${dto.tno}" readonly>
    <div>
        <button type="submit">삭제</button>
    </div>
</form>
</body>
</html>
