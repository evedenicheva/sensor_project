<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <title>sensor-device</title>
</head>
<body class="container-fluid wrapper">

<h3 class="top-name center-block text-center">Registration</h4>
<h5 class="top-name center-block text-center text-danger">${unsuccessful}</h5>

<form method="POST" action="${pageContext.request.contextPath}/addUser">

  <div class="form-group row">
    <label for="email" class="col-sm-2 col-form-label">Email</label>
    <div class="col-sm-4">
      <input type="email" name= "email" value="Katya@mail.ru" class="form-control" id="email">
    </div>
  </div>
  <div class="form-group row">
    <label for="login" class="col-sm-2 col-form-label">Login</label>
    <div class="col-sm-4">
      <input type="text" name= "login" value="Katya" class="form-control" id="login">
    </div>
  </div>
  <div class="form-group row">
    <label for="pwd" class="col-sm-2 col-form-label">Password</label>
    <div class="col-sm-4">
      <input type="password" name="password" value="katya" class="form-control" id="pwd">
    </div>
  </div>
  <div class="form-group row">
      <label for="pwd_conf" class="col-sm-2 col-form-label">Confirm password</label>
      <div class="col-sm-4">
        <input type="password" name="password_conf" value="katya" class="form-control" id="pwd_conf">
      </div>
    </div>

  <hr>

  <h4 class="top-name center-block text-center">Device</h4>
  <div class="form-group row">
    <label for="device_name" class="col-sm-2 col-form-label">Name</label>
    <div class="col-sm-4">
      <input type="text" name= "name" value="pool_device" class="form-control" id="name">
    </div>
  </div>
  <div class="form-group row">
    <label for="device_ip" class="col-sm-2 col-form-label">IP-address</label>
    <div class="col-sm-4">
      <input type="text" name= "ipAddress" value="wSe15uio92k-kdk110" class="form-control" id="ipAddress">
    </div>
  </div>
  <div class="form-group row">
    <label for="device_location" class="col-sm-2 col-form-label">Location</label>
    <div class="col-sm-4">
      <input type="text" name= "location" value="Nezavisimosti 55" class="form-control" id="location">
    <div>
   </div>

  <div class="alert alert-light" role="alert">
    If you have not any devices yet, you can skip this part.
  </div>
  <div class="form-group">
    <button type="submit" class="btn btn-dark">Submit</button>
  <div>


</form>


</body>
</html>