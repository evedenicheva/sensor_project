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

<nav class="navbar navbar-expand-lg navbar-light bg-light">

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          Choose other device
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
        <c:forEach var="dev" items="${devices}">
          <a class="dropdown-item" href="${pageContext.request.contextPath}/mainPage/${dev.id}">${dev.name}</a>
         </c:forEach>
        </div>
      </li>
      <li class="nav-item">
         <a class="nav-link" href="${pageContext.request.contextPath}/addDevice">Add device</a>
      </li>
      <li class="nav-item">
         <a class="nav-link" href="${pageContext.request.contextPath}/logout">Log out</a>
      </li>
      <li class="nav-item">
          <a class="nav-link text-danger" href="${pageContext.request.contextPath}/mainPage/${currentDevice.id}">${currentDevice.name}</a>
      </li>
    </ul>

    <form class="form-inline my-2 my-lg-0" action="${pageContext.request.contextPath}/search">
      <input type="hidden" name="currentDeviceId" value="${currentDevice.id}">
      <input class="form-control mr-sm-2" name="search-str" type="search" placeholder="Search by sensor name" aria-label="Search">
      <button class="btn btn-light" type="search">Search</button>
    </form>
  </div>
</nav>