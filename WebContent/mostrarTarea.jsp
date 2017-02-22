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
<fmt:formatDate value="${task.planned}" var="plannedDate"
                                      pattern="yyyy-MM-dd"/>
<fmt:formatDate value="${task.created}" var="createdDate"
                                      pattern="EEEEEEEEEE dd/MM/yyyy"/>
                             
	<jsp:include page="navbar.jsp" />
	<div class="container">
		<jsp:include page="messages.jsp" />
		<form action="editarTarea" method="POST" class="form-horizontal">
			<table border="1" class="table table-striped table-hover ">
				<tr>
					<th>Nombre</th>
					<td id="title"><input type="text" name="newTitle" size="15"
						value="${task.title}" class="form-control"></td>
				</tr>

				<tr>
					<th>Comentarios</th>
					<td id="comment"><textarea rows="4" cols="50"
							name="newComment">${task.comments}</textarea></td>

				</tr>

				<tr>
					<th>Creada</th>
					<td>${createdDate}</td>
				</tr>

				<tr>
					<c:if test="${task.finished == null}">
						<th>Planeada</th>
						<c:choose>
							<c:when test="${empty task.planned}">
							<td>
								<input value="${plannedDate}" type="date"
									name="newPlannedDate"></td>
							</c:when>
							<c:when test="${not empty task.planned}">
								<c:choose>
									
									<c:when test="${task.planned lt now}">
										<td class="text-danger">
											<input value="${plannedDate}"
											type="date" name="newPlannedDate">
										</td>
									</c:when>
									<c:when test="${task.planned ge now}">
										<td><input value="${plannedDate}" type="date" name="newPlannedDate">
										</td>
									</c:when>
								</c:choose>
							</c:when>
						</c:choose>
					</c:if>
				</tr>

				<c:if test="${task.finished != null}">
					<tr>
						<th>Finalizada</th>
						<td><c:out value="${task.finished}"></c:out></td>
					</tr>
				</c:if>
				<tr>
					<th>Categoría</th>
					<td id="category"><select name="newCategoryId"
						class="form-control">
							<option value=-1>Sin categoría</option>
							<c:forEach items="${categories}" var="category">
								<option value="${category.id}"${category.id == selectedCategory ? 'selected="selected"' : ''}">${category.name}</option>
							</c:forEach>
					</select></td>
				</tr>
			</table>

			<input type="hidden" value="${task.id}" name="taskId" /> <input
				type="submit" class="btn btn-primary" value="Editar tarea" />
		</form>
	</div>
	<%@ include file="pieDePagina.jsp"%>
</body>