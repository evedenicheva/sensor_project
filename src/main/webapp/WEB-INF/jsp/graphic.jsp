<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <title>sensor-device</title>
</head>
<body class="container-fluid wrapper">

<a class="nav-link" href="${pageContext.request.contextPath}/mainPage/${deviceId}">go back</a>

<hr>
<link rel="stylesheet" href="http://bootstraptema.ru/plugins/2015/bootstrap3/bootstrap.min.css" />
<link rel="stylesheet" href="http://bootstraptema.ru/plugins/2016/shieldui/style.css" />
<script src="http://bootstraptema.ru/plugins/jquery/jquery-1.11.3.min.js"></script>
<script src="http://bootstraptema.ru/plugins/2016/shieldui/script.js"></script>

<div class="container">
<div class="row">
<div class="col-md-8 col-md-offset-2">

<div id="chart">

<script>
 $(document).ready(function () {
 $("#chart").shieldChart({
 theme: "bootstrap",
 primaryHeader: {
 text: "${sensorName}"
 },
 exportOptions: {
 image: false,
 print: false
 },
 axisX: {
 categoricalValues: [
 <c:forEach items="${sensorInfosTime}" var="sensorInfoTime">
 "${sensorInfoTime}",
 </c:forEach>
 ]
 },
 tooltipSettings: {
 chartBound: true,
 axisMarkers: {
 enabled: true,
 mode: 'xy'
 }
 },
 dataSeries: [{
 seriesType: 'area',
 collectionAlias: "${sensorName}",
 data: [
 <c:forEach items="${sensorInfos}" var="sensorInfo">
 ${sensorInfo.value},
 </c:forEach>
 ]
 }]
 });
 });
</script><!-- /.График -->

</div>
</div>
</div>
</body>
</html>