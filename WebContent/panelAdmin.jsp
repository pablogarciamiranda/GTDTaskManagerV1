<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="comprobarNavegacion.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>TaskManager - Panel de administracion</title>
</head>
<body>
	<table border="1" align="center">
			<tr>
				<th>Id</th>
				<th>Login</th>
				<th>Email</th>
				<th>isAdmin</th>
				<th>Status</th>
				<th>Editar</th>
			</tr>	
			<c:forEach var="user" items="${listOfUsers}" varStatus="i">
				<tr id="item_${i.index}">
					<td>${user.id}</td>
					<td>${user.login}</td>
					<td>${user.email}</td>
					<td>${user.isAdmin}</td>
					<td>${user.status}</td>
					<td><a href="modificarUsuario?login=${user.login}">Editar</a>
					</td>
				</tr>
			</c:forEach>
	</table>
</form>
</body>