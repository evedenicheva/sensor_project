<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp"/>

<h5 class="top-name center-block text-center">${unsuccessful}</h5>
<table class="table">
  <c:forEach var="sensor" items="${sensors}">
  <form class="form-horizontal" action="${pageContext.request.contextPath}/deleteSensor/${sensor.id}" method="post">
    <tr>
      <td><input type="hidden" name= "deviceId" value="${currentDevice.id}"></td>
      <td>${sensor.name}</td>
      <c:forEach var="sensorInfo" items="${sensorInfos}">
      <c:if test="${sensorInfo.name==sensor.name}">
      <td>${sensorInfo.value}</td>
      </c:if>
      </c:forEach>
      <td><a href="${pageContext.request.contextPath}/graphic/${currentDevice.id}/${sensor.name}" class="link">graphic</a></td>
      <td><button id="delete" name="delete" class="btn btn-dark">Delete</button></td>
    </tr>
   </form>
  </c:forEach>
</table>

<form method="POST" action="${pageContext.request.contextPath}/mainPage/${currentDevice.id}">
  <div class="form-group row">
    <div class="col-sm-5">
      <input type="text" name= "name" class="form-control" id="name" placeholder="Name" required="">
    </div>
    <div class="col-sm-5">
      <input type="number" name= "value" class="form-control" id="value" placeholder="Value" required="">
    </div>
    <div class="col-sm-2">
      <button type="login" class="btn btn-dark">Add</button>
    </div>
  </div>
</form>

<a href="${pageContext.request.contextPath}/deleteDevice/${currentDevice.id}" class="link">Delete device</a>


<jsp:include page="footer.jsp"/>