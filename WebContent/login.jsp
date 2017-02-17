<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>TaskManager - Inicie sesión</title>
<!-- Website CSS style -->
		<link rel="stylesheet" type="text/css" href="https://bootswatch.com/flatly/bootstrap.min.css">

		<!-- Website Font style -->
	    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.1/css/font-awesome.min.css">
		
		<!-- Google Fonts -->
		<link href='https://fonts.googleapis.com/css?family=Passion+One' rel='stylesheet' type='text/css'>
		<link href='https://fonts.googleapis.com/css?family=Oxygen' rel='stylesheet' type='text/css'>
</head>
<body>
	<div class="container">
		<div class="wrapper">
			<form action="validarse" method="post" name="validarse_form_name">
				<h3 class="form-signin-heading">Welcome Back! Please Sign In</h3>
				<hr class="colorgraph">
				<br> 
				<input type="text" class="form-control" name="nombreUsuario"
					placeholder="Username" required="" autofocus="" size="15" />
				 <input type="password" class="form-control" name="passwordUsuario"
					placeholder="Password" required="" size="15"/>

				<button class="btn btn-lg btn-primary btn-block" name="Submit"
					value="Login" type="submit">Login</button>
			</form>
		</div>
	</div>
	<jsp:include page="pieDePagina.jsp"/>
</body>
</html>