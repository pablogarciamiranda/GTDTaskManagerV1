<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="comprobarNavegacion.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>TaskManager - Mostrar tarea</title>
</head>

<body>
	<table border="1" align="center">
			<tr>
				<th>ID</th>
				<td>${task.id}</td>
			</tr>
			<tr>
				<th>Title</th>
				<td>${task.title}</td>
			</tr>
			<tr>
				<th>Comments</th>
				<td>${task.comments}</td>
			</tr>
			<tr>
				<th>Created</th>
				<td>${task.created}</td>
			</tr>
			<tr>
				<th>Planned</th>
				<td>${task.planned}</td>
			</tr>
			<tr>
				<th>Finished</th>
				<td>${task.finished}</td>
			</tr>
			<tr>
				<th>Category</th>
				
				<c:if test="${category.name != null}">
					<td>${category.name}</td>
				</c:if>
			</tr>

	</table>
			<form method="POST" action="editarTarea" class="formDark">
			<input type="hidden" value="${task.id}" name="taskId" /> 
			<input type="submit" class="button" value="Editar tarea" />
			</form>
	<%@ include file="pieDePagina.jsp" %>
</body>
