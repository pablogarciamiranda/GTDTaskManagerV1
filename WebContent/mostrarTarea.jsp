<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="comprobarNavegacion.jsp"%>
<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate value="${today}" pattern="MM.dd.yyyy" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>TaskManager - Mostrar tarea</title>
<link rel="stylesheet"
	href="https://bootswatch.com/flatly/bootstrap.min.css"
	integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp"
	crossorigin="anonymous">
</head>
<style>
.late {
	color: rgb(255, 0, 0);
}
</style>
</head>

<body>
	<jsp:include page="messages.jsp"></jsp:include>
	<form action="editarTarea" method="POST">
		<table border="1" align="center">

			<tr>
				<th>Nombre</th>
				<td id="title">
						<input type="text" name="newTitle" size="15"
							value="${task.title}"> 
				</td>
			</tr>

			<tr>
				<th>Comentarios</th>
			<td id="comment">
						<textarea rows="4" cols="50" name="newComment">${task.comments}</textarea>
				</td>
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
								<td class="late"><c:out value="${task.planned}"></c:out></td>
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
				<td id="category">
						<select name="newCategoryId">
							<option value=-1>Sin categoría</option>
							<c:forEach items="${categories}" var="category">
							    <option value="${category.id}" ${category.id == selectedCategory ? 'selected="selected"' : ''}">${category.name}</option>
							</c:forEach>
						</select>
				</td>
			</tr>
		</table>
		
		<input type="hidden" value="${task.id}" name="taskId" />
		<input type="submit" class="button" value="Editar tarea" />
	</form>
	<%@ include file="pieDePagina.jsp"%>
</body>