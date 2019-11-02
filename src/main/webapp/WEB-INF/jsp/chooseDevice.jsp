<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <title>sensor-device</title>
</head>
<body class="container-fluid wrapper">

<h3 class="top-name center-block text-center">Please select your device:</h4>

<br>

<c:forEach var="dev" items="${devices}">
        <div class="col-sm-12">
            <div class="row justify-content-center">
                <div class="col-4 text-center">
                <div class="h2">
                      <a  class="badge badge-light" href="${pageContext.request.contextPath}/mainPage/${dev.id}">${dev.name}</a>
                </div>
                <br>
                <br>
                </div>
              </div>
          </div>
</div>
</c:forEach>

</body>
</html>