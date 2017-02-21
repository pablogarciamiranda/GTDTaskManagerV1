<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="comprobarNavegacion.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<title>TaskManager - Panel de administracion</title>
<link rel="stylesheet" href="https://bootswatch.com/flatly/bootstrap.min.css" />
</head>
<body>
<jsp:include page="navbar.jsp"/>
<div class="container">
	<jsp:include page="messages.jsp"></jsp:include>
	<table border="1" class="table table-striped table-hover">
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
					<td><a href="mostrarUsuario?id=${user.id}">Editar</a>
					</td>
				</tr>
			</c:forEach>
	</table>
</div>
</body>