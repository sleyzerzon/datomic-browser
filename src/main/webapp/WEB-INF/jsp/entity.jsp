<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.jmolly.com/datomic-browser/functions" %>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="/styles/styles.css"/>
        <script type="text/javascript" src="/scripts/jquery.js"></script>
    </head>
    <body>
        <h2><a href="/browser/database/${f:urlEncode(dbname)}">${uri}</a></h2>
        <h3>#db/id[:db.part/db ${f:eget(entity, ":db/id")}]</h3>
        <ul>
            <c:forEach var="key" items="${f:keySet(entity)}">
                <c:set var="value" value="${f:eget(entity, key)}"/>
                <c:choose>
                    <c:when test="${f:isEntityMap(value)}">
                        <li>${key} =
                            <a href="/browser/database/${dbname}/entity/${f:urlEncode(f:eget(value, ":db/id"))}">#db/id[:db.part/db ${f:eget(value, ":db/id")}]</a>
                                <c:if test="${f:hasDbIdent(value)}">(${f:eget(value, ":db/ident")})</c:if>
                        </li>
                    </c:when>
                    <c:when test="${f:isCollection(value)}">
                        <li>${key} =
                        <ul>
                        <c:forEach var="entry" items="${value}">
                            <li>
                            <c:choose>
                                <c:when test="${f:isEntityMap(entry)}"><a href="/browser/database/${dbname}/entity/${f:urlEncode(f:eget(entry, ":db/id"))}">#db/id[:db.part/db ${f:eget(entry, ":db/id")}]</a>
                                    <c:if test="${f:hasDbIdent(entry)}">(${f:eget(entry, ":db/ident")})</c:if></c:when>
                                <c:otherwise>${entry}</c:otherwise>
                            </c:choose>
                            </li>
                        </c:forEach>
                        </ul>
                        </li>
                    </c:when>
                    <c:otherwise><li>${key} = ${f:eget(entity, key)}</li></c:otherwise>
                </c:choose>
            </c:forEach>
        </ul>
        <c:if test="${not empty instances}">
            <h3>Instances</h3>
            <ul>
            <c:forEach var="referer" items="${instances}">
                <li>
                    <a href="/browser/database/${dbname}/entity/${f:urlEncode(f:eget(referer, ":db/id"))}">#db/id[:db.part/db ${f:eget(referer, ":db/id")}]</a>
                    <c:if test="${f:hasDbIdent(referer)}">(${f:eget(referer, ":db/ident")})</c:if>
                </li>
            </c:forEach>
            </ul>
        </c:if>
        <c:if test="${not empty referers}">
            <h3>Referers</h3>
            <ul>
            <c:forEach var="referer" items="${referers}">
                <li>
                    <a href="/browser/database/${dbname}/entity/${f:urlEncode(f:eget(referer, ":db/id"))}">#db/id[:db.part/db ${f:eget(referer, ":db/id")}]</a>
                    <c:if test="${f:hasDbIdent(referer)}">(${f:eget(referer, ":db/ident")})</c:if>
                </li>
            </c:forEach>
            </ul>
        </c:if>
    </body>
</html>
