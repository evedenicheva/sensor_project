<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <title>sensor-device</title>
</head>
<body class="container-fluid wrapper">

<form name='f' action="login" method='POST'>


<h3 class="top-name center-block text-center">Authorization</h4>

<h5 class="top-name center-block text-center text-danger">${unsuccessful}</h5>
  <div class="form-group row">
    <label for="text" class="col-sm-2 col-form-label">Login</label>
    <div class="col-sm-4">
      <input type="text" name= "username" class="form-control" id="login">
    </div>
  </div>
  <div class="form-group row">
    <label for="pwd" class="col-sm-2 col-form-label">Password</label>
    <div class="col-sm-4">
      <input type="password" name="password" class="form-control" id="pwd">
    </div>
    <jsp:text/></input>
    <button type="submit" class="btn btn-dark">Login</button>
  </div>
  <hr>
</form>

  <a class="nav-link" href="addUser">Have not account yet? Register</a>



</body>
</html>