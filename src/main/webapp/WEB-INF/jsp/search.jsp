<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.jmolly.com/datomic-browser/functions" %>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="/styles/styles.css"/>
        <script type="text/javascript" src="/scripts/jquery.js"></script>
    </head>
    <body>
        <h2><a href="/browser/database/${f:urlEncode(dbname)}">${uri}</a></h2>
        <h3>${f:size(results)} Search results for '${terms}'</h3>
        <ul>
        <c:forEach var="result" items="${results}">
            <li><a href="/browser/database/${dbname}/entity/${f:urlEncode(f:eget(result, ":db/id"))}">#db/id[:db.part/db ${f:eget(result, ":db/id")}]</a></li>
        </c:forEach>
        </ul>
    </body>
</html>
