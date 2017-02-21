<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="uo.sdi.dto.types.UserStatus"%>
<%@ include file="comprobarNavegacion.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>TaskManager - Página principal del usuario</title>
<link rel="stylesheet" type="text/css"
	href="https://bootswatch.com/flatly/bootstrap.css">
<!-- Google Fonts -->
<link href='https://fonts.googleapis.com/css?family=Passion+One'
	rel='stylesheet' type='text/css'>
<link href='https://fonts.googleapis.com/css?family=Oxygen'
	rel='stylesheet' type='text/css'>
</head>
<body>
	<jsp:include page="navbar.jsp" />
	<div class="container">
		<i>Iniciaste sesión el <fmt:formatDate
				pattern="dd-MM-yyyy' a las 'HH:mm"
				value="${sessionScope.fechaInicioSesion}" /> (usuario número
			${contador})
		</i> <br /> <br />
		<jsp:useBean id="userToEdit" class="uo.sdi.dto.User" scope="session" />
		<jsp:include page="messages.jsp"></jsp:include>
		<form class="form-horizontal" id="editarUsuario"
			action="editarUsuario" method="POST">
			<table class="table table-striped table-hover">
				<c:if test="${sessionScope.user.isAdmin}">
					<tr>
						<td>Id:</td>
						<td id="id"><jsp:getProperty property="id" name="userToEdit" /></td>
					</tr>
				</c:if>
				<tr>
					<td>Email:</td>
					<td id="email"><input type="text" name="newEmail" size="15"
						value="<jsp:getProperty property="email" name="userToEdit"/>">
					</td>
				</tr>
				<tr>
					<td>Password:</td>
					<td id="password"><input type="password" name="password"
						size="15"></td>
				</tr>
				<tr>
					<td>New password:</td>
					<td id="password"><input type="password" name="newPassword"
						size="15"></td>
				</tr>
				<tr>
					<td>Repeat New password:</td>
					<td id="password"><input type="password" name="newPassword2"
						size="15"></td>
				</tr>
				<tr>
					<td>Es administrador:</td>
					<td id="isAdmin"><jsp:getProperty property="isAdmin"
							name="userToEdit" /></td>
				</tr>
				<tr>
					<td>Login:</td>
					<td id="login"><input type="text" name="newLogin" size="15"
						value="<jsp:getProperty property="login" name="userToEdit" />">
					</td>
				</tr>
				<c:if test="${sessionScope.user.isAdmin}">
					<tr>
						<td>Estado:</td>
						<td id="status">
							<c:choose>
								<c:when test="${userToEdit.status == 'ENABLED'}">
 									<span class="glyphicon glyphicon-ok-circle"></span>
								</c:when>
								<c:otherwise>
									<span class="glyphicon glyphicon-remove-circle"></span>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:if>
			</table>
			<input type="hidden" name="login"
				value="<jsp:getProperty property="login" name="userToEdit" />">
			<input type="submit" class="btn btn-primary" name="editarUsuario"
				value="Editar" form="editarUsuario">
		</form>
		<br/>
	</div>
	<%@ include file="pieDePagina.jsp"%>
</body>
</html>
