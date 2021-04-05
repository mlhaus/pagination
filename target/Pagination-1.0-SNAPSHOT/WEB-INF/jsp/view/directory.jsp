<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Directory</title>
        <link href="styles/main.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <h2>Directory</h2>
            <div class="pagination">
                <c:forEach var="i" begin="1" end="${maxPages}">
                    <a <c:if test="${currentPage == i}">class="active"</c:if> href="<c:url value="/directory"><c:param name="page" value="${i}" /></c:url>">${i}</a>
                </c:forEach>
            </div>
            <div class="people">
                <c:forEach items="${people}" var="person" begin="${begin}" end="${end}">
                    <div class="person">
                        <img src="${person.picture}" alt="<c:out value="${person.firstName}" />&nbsp;<c:out value="${person.lastName}" />">
                        <p><c:out value="${person.firstName}" />&nbsp;<c:out value="${person.lastName}" /></p>
                    </div>
                </c:forEach>
            </div>
        </div>
    </body>
</html>
