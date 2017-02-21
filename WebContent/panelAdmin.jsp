<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="comprobarNavegacion.jsp"%>
<%@ page import="uo.sdi.dto.types.UserStatus"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>TaskManager - Panel de administracion</title>
<link rel="stylesheet"
	href="https://bootswatch.com/flatly/bootstrap.min.css" />
</head>
<body>
	<jsp:include page="navbar.jsp" />
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
				<th>Cambiar status</th>
				<th>Eliminar</th>
			</tr>
			<c:forEach var="user" items="${listOfUsers}" varStatus="i">
				<tr id="item_${i.index}">
					<td>${user.id}</td>
					<td>${user.login}</td>
					<td>${user.email}</td>
					<td>${user.isAdmin}</td>
					<td>${user.status}</td>
					<td><form id="mostrarUsuario" action="mostrarUsuario"
							method="POST">
							<input type="hidden" name="id" value="${user.id}"> <input
								type="submit" class="btn btn-primary" value="Editar">
						</form></td>
					<c:if test="${!user.isAdmin}">
						<td><form id="cambiarEstado" action="cambiarEstado"
								method="POST">
								<input type="hidden" name="id" value="${user.id}">
								<c:choose>
									<c:when test="${user.status == 'ENABLED'}">
 										<input type="submit" class="btn btn-primary" value="Desactivar">
									</c:when>
									<c:otherwise>
										<input type="submit" class="btn btn-primary" value="Activar">
									</c:otherwise>
								</c:choose>
							</form></td>
						<td><form id="eliminarUsuario" action="eliminarUsuario"
								method="POST"
								onsubmit="return confirm('Do you really want' +
								' to delete the user?\n If you accept every task from this user'
													+ ' will be removed');">
								<input type="hidden" name="id" value="${user.id}"> <input
									type="submit" class="btn btn-primary" value="Eliminar">
							</form></td>

					</c:if>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>