<%--
  Created by IntelliJ IDEA.
  User: it
  Date: 25. 6. 26.
  Time: 오후 12:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>임시 수정폼 화면, tno로 데이터 조회해서, 일단 불러오기, 그리고 수정할 내용 변경해서 다시 post 로 전달하기</h1>
<form id="form1" action="/todo/update2" method="post">
    <div>
        <input type="text" name="tno" value="${dto.tno}" readonly>
    </div>

    <div>
        <input type="text" name="title" value="${dto.title}">
    </div>

    <div>
        <input type="date" name="dueDate" value="${dto.dueDate}">
    </div>

    <div>
        <input type="checkbox" name="finished" ${dto.finished ? "checked":""}>
    </div>
    <div>
        <button type="submit">수정하기</button>
        <a href="/todo/list2">목록가기</a>
    </div>
</form>


<form id="form2" action="/todo/delete2" method="post">
    <input type="hidden" name="tno" value="${dto.tno}" readonly>
    <div>
        <button type="submit">삭제</button>
    </div>
</form>

</body>
</html>
