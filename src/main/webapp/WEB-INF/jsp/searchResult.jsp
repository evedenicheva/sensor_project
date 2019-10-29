<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp"/>

<h5 class="top-name center-block text-center">${unsuccessful}</h5>
  <c:forEach var="sensor" items="${sensorsFromSearch}">
      <div class="row">
        <div class="col-sm-5">
          <p>${sensor.name}</p>
        </div>
        <div class="col-sm-5">
        <c:forEach var="device" items="${devicesFromSearch}">
          <c:if test="${sensor.device.id==device.id}">
            <a class="link" href="${pageContext.request.contextPath}/mainPage/${device.id}">${device.name}</a>
          </c:if>
        </c:forEach>
        </div>
      </div>
  </c:forEach>


<jsp:include page="footer.jsp"/>