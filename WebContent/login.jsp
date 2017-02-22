<!DOCTYPE html>
<html lang="en">
    <head> 
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" type="text/css" href="WEB-INF/css/register.css">

		<!-- Website CSS style -->
		<link rel="stylesheet" type="text/css" href="https://bootswatch.com/flatly/bootstrap.min.css">

		<!-- Website Font style -->
	    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.1/css/font-awesome.min.css">
		
		<!-- Google Fonts -->
		<link href='https://fonts.googleapis.com/css?family=Passion+One' rel='stylesheet' type='text/css'>
		<link href='https://fonts.googleapis.com/css?family=Oxygen' rel='stylesheet' type='text/css'>

		<title>GTDTaskManager - Login</title>
	</head>
	<body>
		<div class="container">
			<div class="row main">
				<div class="panel-heading">
	               <div class="panel-title text-center">
	               		<h1 class="title">Sign In</h1>
	               		<hr />
	               	</div>
	            </div> 
	            <jsp:include page="messages.jsp"></jsp:include>
	            
	            <div class="main-login main-center">
					<form class="form-horizontal" action="validarse" method="post" >
						
						<div class="form-group">
							<label for="username" class="cols-sm-2 control-label">Username:</label>
							<div class="cols-sm-10">
								<div class="input-group">
									<span class="input-group-addon"><i class="fa fa-users fa" aria-hidden="true"></i></span>
									<input type="text" class="form-control" name="nombreUsuario" id="username"  placeholder="Enter your Username"/>
								</div>
							</div>
						</div>
						
						<div class="form-group">
							<label for="password" class="cols-sm-2 control-label">Password</label>
							<div class="cols-sm-10">
								<div class="input-group">
									<span class="input-group-addon"><i class="fa fa-lock fa-lg" aria-hidden="true"></i></span>
									<input type="password" class="form-control" name="passwordUsuario" id="password"  placeholder="Enter your Password"/>
								</div>
							</div>
						</div>

					<div class="form-group ">
							<button id="submit" value="Send" class="btn btn-primary btn-lg btn-block login-button" type="submit">Login</button>
						</div>
					</form>
				</div>
			</div>
		</div>
		
		<script type="text/javascript" src="assets/js/bootstrap.js"></script>
		<%@ include file="pieDePagina.jsp" %>
	</body>
</html>
			