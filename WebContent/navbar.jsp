<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<nav class="navbar navbar-default">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<c:choose>
					<c:when test="${sessionScope.user.isAdmin}">
 						<a class="navbar-brand" href="">Task Manager</a>
					</c:when>
					<c:otherwise>
						<a class="navbar-brand" href="listarTareasInbox">Task Manager</a>
					</c:otherwise>
				</c:choose>
			</div>

			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<jsp:useBean id="user" class="uo.sdi.dto.User" scope="session" />
					<li class="active"><a
						href="mostrarUsuario?login=${sessionScope.user.login}">${sessionScope.user.login}<span
							class="sr-only">(current)</span></a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="cerrarSesion">Logout</a></li>
				</ul>
			</div>
		</div>
	</nav>