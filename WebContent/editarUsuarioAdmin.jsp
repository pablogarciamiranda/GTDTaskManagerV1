<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="comprobarNavegacion.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>TaskManager - Editar usuario</title>
<link rel="stylesheet" href="https://bootswatch.com/flatly/bootstrap.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
</head>
<body>
	<br/><br/>
	<jsp:useBean id="user" class="uo.sdi.dto.User" scope="session" />
	<form action="editarUsuario" method="POST">
		<table>
			<tr>
				<td>Id:</td><td id="id"><jsp:getProperty property="id" name="user" /></td>
			</tr>
			<tr>
				<td>Email:</td>
				<td id="email">
						<input type="text" name="newEmail" size="15"
							value="<jsp:getProperty property="email" name="user"/>"> 
				</td>
			</tr>
			<tr>
				<td>Password:</td>
				<td id="password">
						<input type="password" name="password" size="15"> 
				</td>
			</tr>
			<tr>
				<td>New password:</td>
				<td id="password">
						<input type="password" name="newPassword" size="15"> 
				</td>
			</tr>
			<tr>
				<td>Repeat New password:</td>
				<td id="password">
						<input type="password" name="newPassword2" size="15"> 
				</td>
			</tr>
			<tr>
				<td>Es administrador:</td><td id="isAdmin"><jsp:getProperty property="isAdmin" name="user" /></td>
			</tr>
			<tr>
				<td>Login:</td>
					<td id="login">
						<input type="text" name="newLogin" size="15"
							value="<jsp:getProperty property="login" name="user"/>">
					</td>
			</tr>
			<tr>
				<td>Estado:</td><td id="status"><jsp:getProperty property="status" name="user" /></td>
			</tr>
		</table>
		<input type="hidden" name="login" value=<jsp:getProperty property="login" name="user"/>>
		<input type="submit" value="Modificar">
	</form>
	<br/>	
	<a id="cerrarSesion" href="cerrarSesion">Cerrar sesión</a>
	
	<%@ include file="pieDePagina.jsp" %>
</body>
</html>
