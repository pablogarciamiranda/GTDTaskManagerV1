<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="comprobarNavegacion.jsp"%>
<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate value="${today}" pattern="MM.dd.yyyy" />
<!DOCTYPE html>
<html>
<head>
<title>TaskManager - Mostrar tarea</title>
<link rel="stylesheet" type="text/css"
	href="https://bootswatch.com/flatly/bootstrap.css">
<!-- Google Fonts -->
<link href='https://fonts.googleapis.com/css?family=Passion+One'
	rel='stylesheet' type='text/css'>
<link href='https://fonts.googleapis.com/css?family=Oxygen'
	rel='stylesheet' type='text/css'>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<div class="container">
	<jsp:include page="messages.jsp"/>
	<form action="editarTarea" method="POST" class="form-horizontal">
		<table border="1" class="table table-striped table-hover ">
			<tr>
				<th>Nombre</th>
				<td id="title">
						<input type="text" name="newTitle" size="15"
							value="${task.title}" class="form-control"> 
				</td>
			</tr>

			<tr>
				<th>Comentarios</th>
			<td id="comment">
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
								<td class="text-danger"><c:out value="${task.planned}"></c:out></td>
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
						<select name="newCategoryId" class="form-control">
							<option value=-1>Sin categoría</option>
							<c:forEach items="${categories}" var="category">
							    <option value="${category.id}" ${category.id == selectedCategory ? 'selected="selected"' : ''}">${category.name}</option>
							</c:forEach>
						</select>
				</td>
			</tr>
		</table>
		
		<input type="hidden" value="${task.id}" name="taskId" />
		<input type="submit" class="btn btn-primary" value="Editar tarea" />
	</form>
</div>
	<%@ include file="pieDePagina.jsp"%>
</body>