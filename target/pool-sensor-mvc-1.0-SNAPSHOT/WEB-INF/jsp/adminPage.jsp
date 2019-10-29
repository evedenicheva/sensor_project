<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <title>sensor-device</title>
</head>
<body>
<div class="container">
<a class="nav-link" href="${pageContext.request.contextPath}/logout">Log out</a>

<hr>
<h5 class="top-name center-block text-center text-danger">${result}</h5>
    <c:forEach items="${myUsers}" var="myUser">
    <h2 class="top-name center-block text-center">User ${myUser.id}</h2>
    <div class="row">
        <div class=col-md-3>Login</div>
        <div class=col-md-2>Email</div>
        <div class=col-md-3>Role</div>
    </div>
        <form class="form-horizontal" action="${pageContext.request.contextPath}/adminPage/updateUser/${myUser.id}" method="post">
            <div class="row">
                <div class=col-md-3>
                    <input id="login" name="login" type="text" class="form-control input-md"
                           value="${myUser.login}">
                </div>

                <div class=col-md-2>
                     <input id="email" name="email" type="text" class="form-control input-md"
                           value="${myUser.email}">
                </div>


                <div class="form-check form-check-inline">
                    <c:forEach items="${myRoles}" var="myRole">
                        <c:forEach items="${myUser.myRoles}" var="userRole">
                            <c:if test="${myRole.id==userRole.id}">
                                <div class="form-check form-check-inline">
                                     <input class="form-check-input" name="role" type="checkbox" id="myRole" value="${myRole.roleType}" checked>
                                     <label class="form-check-label" for="myRole">${myRole.roleType}</label>
                                </div>
                            </c:if>
                            <c:if test="${myRole.id!=userRole.id}">
                                 <div class="form-check form-check-inline">
                                      <input class="form-check-input" name="role" type="checkbox" id="myRole" value="${myRole.roleType}">
                                      <label class="form-check-label" for="myRole">${myRole.roleType}</label>
                                 </div>
                            </c:if>
                        </c:forEach>
                    </c:forEach>
                </div>


                <div class=col-md-3>
                    <button id="update" name="update" class="btn btn-light">Update</button>
                    <button id="delete" name="delete" class="btn btn-dark">Delete</button>
                </div>
            </div>
        </form>
        <h2 class="top-name center-block text-center">Devices</h5>
        <div class="row">
            <div class=col-md-2>Name</div>
            <div class=col-md-3>Location</div>
            <div class=col-md-2>IP-Address</div>
            <div class=col-md-2>Sensors</div>
        </div>
        <c:forEach items="${devices}" var="device">
        <c:if test="${myUser.id==device.myUser.id}">
        <form class="form-horizontal" action="${pageContext.request.contextPath}/adminPage/updateDevice/${device.id}" method="post">
             <div class="row">
                 <div class=col-md-2>
                     <input id="name" name="name" type="text"class="form-control input-md" value="${device.name}">
                 </div>

                 <div class=col-md-3>
                     <input id="location" name="location" type="text"class="form-control input-md" value="${device.location}">
                 </div>

                 <div class=col-md-2>
                      <input id="ipAddress" name="ipAddress" type="text" class="form-control   input-md" value="${device.ipAddress}">
                 </div>

                 <div class=col-md-2>
                 <c:forEach items="${sensors}" var="sensor">
                 <c:if test="${device.id==sensor.device.id}">
                 <div class="form-check">
                         <input class="form-check-input" name="sensor" type="checkbox" id="sensorName"                                               value="${sensor.name}" checked>
                         <label class="form-check-label" for="sensorName">${sensor.name}</label>
                 </div>
                 </c:if>
                 </c:forEach>
                 </div>

                 <div class=col-md-3>
                     <button id="update" name="update" class="btn btn-light">Update</button>
                     <button id="delete" name="delete" class="btn btn-dark">Delete</button>
                 </div>
             </div>
         </form>
          </c:if>
         </c:forEach>
         <hr>
    </c:forEach>


</div>
</body>
</html>