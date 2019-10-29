<jsp:include page="header.jsp"/>

<form method="POST" action="${pageContext.request.contextPath}/addDevice">
  <h4 class="top-name center-block text-center">Device</h4>
  <h5 class="top-name center-block text-center text-danger">${unsuccessful}</h5>
    <div class="form-group row">
      <label for="device_name" class="col-sm-2 col-form-label">Name</label>
      <div class="col-sm-4">
        <input type="text" name= "name" class="form-control" id="device_name">
      </div>
    </div>
    <div class="form-group row">
      <label for="device_ip" class="col-sm-2 col-form-label">IP-address</label>
      <div class="col-sm-4">
        <input type="text" name= "ipAddress" class="form-control" id="device_ip">
      </div>
    </div>
    <div class="form-group row">
      <label for="device_location" class="col-sm-2 col-form-label">Location</label>
      <div class="col-sm-4">
        <input type="text" name= "location" class="form-control" id="device_location">
      <div>
     </div>
    <div class="form-group">
      <button type="submit" class="btn btn-dark">Submit</button>
    <div>
</form>


<jsp:include page="footer.jsp"/>