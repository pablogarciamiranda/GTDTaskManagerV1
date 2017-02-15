<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="comprobarNavegacion.jsp" %>
<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate value="${today}" pattern="MM.dd.yyyy" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>TaskManager - Mostrar tarea</title>
<style>
	.late{
		color:rgb(255,0,0);
		}
</style>
</head>

<body>
	<table border="1" align="center">
			<tr>
				<th>ID</th>
				<td>${task.id}</td>
			</tr>
			<tr>
				<th>Nombre</th>
				<td>${task.title}</td>
			</tr>
			<tr>
				<th>Comentarios</th>
				<td>${task.comments}</td>
			</tr>
			<tr>
				<th>Creada</th>
				<td>${task.created}</td>
			</tr>
			<tr>
				<th>Planeada</th>
				<c:choose>
					<c:when test="${empty task.planned}">
						<td><c:out value="No hay fecha planeada"></c:out></td>
					</c:when>
					<c:when test="${not empty task.planned}">
						<c:choose>
							<c:when test="${task.planned lt now}">
								<td class="late"><c:out  value="${task.planned}"></c:out></td>
							</c:when>
							<c:when test="${task.planned ge now}">
								<td><c:out value="${task.planned}"></c:out></td>
							</c:when>
						</c:choose>	
					</c:when>
				</c:choose>
			</tr>
			<tr>
				<th>Estado</th>
				<c:choose>
					<c:when test="${task.finished}">
						<td><c:out value="Finalizada"></c:out></td>
					</c:when>
					<c:when test="${not task.finished}">
						<td><c:out value="No finalizada aún"></c:out></td>
					</c:when>
				</c:choose>
			</tr>
			<tr>
				<th>Categoría</th>	
				<c:choose>
					<c:when test="${empty category}">
						<td><c:out value="Sin categoría"></c:out></td>
					</c:when>
					<c:when test="${not empty category}">
						<td><c:out value="${category.name}"></c:out></td>
					</c:when>
				</c:choose>
			</tr>

	</table>
			<form method="POST" action="modificarTarea">
				<input type="hidden" value="${task.id}" name="taskId" /> 
				<input type="submit" class="button" value="Editar tarea" />
			</form>
	<%@ include file="pieDePagina.jsp" %>
</body>
