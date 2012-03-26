<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.jmolly.com/datomic-browser/functions" %>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="/styles/styles.css"/>
        <script type="text/javascript" src="/scripts/jquery.js"></script>
    </head>
    <body>
        <h2><a href="/browser/database/${f:urlEncode(dbname)}">${uri}</a></h2>
        <h3>Upload</h3>
        <form method="post" action="/browser/database/${dbname}/upload" enctype="multipart/form-data">
            <input type="file" name="file"/> <input type="submit"/>
        </form>
        <h3>Search</h3>
        <form method="get" action="/browser/database/${dbname}/search">
            <input type="text" name="terms"/> <input type="submit"/>
        </form>
        <h3>Attributes</h3>
        <ul>
            <c:forEach var="attribute" items="${attributes}">
                <li><a href="/browser/database/${dbname}/entity/${f:urlEncode(f:eget(attribute, ":db/id"))}">${f:eget(attribute, ":db/ident")}</a></li>
            </c:forEach>
        </ul>
    </body>
</html>
