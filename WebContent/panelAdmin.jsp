<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>

<title>TaskManager - Panel de administracion</title>

<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/v/bs/jqc-1.12.4/dt-1.10.13/datatables.min.css" />
<script type="text/javascript"
	src="https://cdn.datatables.net/v/bs/jqc-1.12.4/dt-1.10.13/datatables.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="https://bootswatch.com/flatly/bootstrap.css">

<script type="text/javascript">
	$(document).ready(function() {
		$('#example').DataTable();
	});
</script>
</head>
<body>
	<jsp:include page="navbar.jsp" />
	<div class = container-fluid>
	<table id="example" class="table table-striped table-hover" cellpadding="0" cellspacing="0" width="100%">
		<thead>
			<tr>
				<th align="center">Id</th>
				<th align="center">Login</th>
				<th align="center">email</th>
				<th align="center">Is Admin</th>
				<th align="center" >Status</th>
				<th align="center">Editar</th>

			</tr>
		</thead>
		<tbody>
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
								type="submit" class="btn btn-primary" id="editar_${user.id}"
								value="Editar">
						</form></td>
					<c:if test="${!user.isAdmin}">
						<td><form id="cambiarEstado" action="cambiarEstado"
								method="POST">
								<input type="hidden" name="id" value="${user.id}">
								<c:choose>
									<c:when test="${user.status == 'ENABLED'}">
										<input type="submit" class="btn btn-primary"
											id="desactivar_${user.id}" value="Desactivar">
									</c:when>
									<c:otherwise>
										<input type="submit" class="btn btn-primary"
											id="activar_${user.id}" value="Activar">
									</c:otherwise>
								</c:choose>
							</form></td>
						<td><form id="eliminarUsuario" action="eliminarUsuario"
								method="POST"
								onsubmit="return confirm('Do you really want' +
								' to delete the user?\n If you accept every task from this user'
													+ ' will be removed');">
								<input type="hidden" name="id" value="${user.id}"> <input
									type="submit" class="btn btn-primary" id="eliminar_${user.id}"
									value="Eliminar">
							</form></td>

					</c:if>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
</body>
</html>

