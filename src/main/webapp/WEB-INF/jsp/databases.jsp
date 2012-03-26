<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.jmolly.com/datomic-browser/functions" %>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="/styles/styles.css"/>
        <script type="text/javascript" src="/scripts/jquery.js"></script>
        <script type="text/javascript">
            var doSubmit = function() {
                $('#newDatabaseButton').attr('disabled', 'true');
                $('form').attr('action', '/browser/database/' + $('#newDatabaseText').val());
                $('form').submit();
            };
            $(document).ready(function () {
                $('#newDatabaseText').keypress(function (e) {
                    $('#newDatabaseButton').removeAttr('disabled');
                    if (e.keyCode == 13) {
                        doSubmit();
                        e.preventDefault(); // prevent natural form submission
                    }
                });
                $('#newDatabaseButton').click(doSubmit);
            });
        </script>
    </head>
    <body>
        <h2>All Databases</h2>
        <ul>
            <c:if test="${empty dbnames}">
                There are currently no databases.
            </c:if>
            <c:forEach var="dbname" items="${dbnames}">
                <li><a href="/browser/database/${f:urlEncode(dbname)}">datomic:mem://<c:out value="${dbname}"/></a></li>
            </c:forEach>
        </ul>
        <h2>Create a New Database</h2>
        <form method="post">
            datomic:mem://<input id="newDatabaseText" type="text"/>
            <input disabled id="newDatabaseButton" type="button" value="Create"/>
        </form>
    </body>
</html>
