<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ include file="comprobarNavegacion.jsp"%>
<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate value="${today}" pattern="MM.dd.yyyy" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>TaskManager - Listado de tareas del día</title>
<!-- Website CSS style -->
<link rel="stylesheet" type="text/css"
	href="https://bootswatch.com/flatly/bootstrap.min.css">

<!-- Website Font style -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.1/css/font-awesome.min.css">

<!-- Google Fonts -->
<link href='https://fonts.googleapis.com/css?family=Passion+One'
	rel='stylesheet' type='text/css'>
<link href='https://fonts.googleapis.com/css?family=Oxygen'
	rel='stylesheet' type='text/css'>
<link rel="stylesheet"
	href="https://bootswatch.com/flatly/bootstrap.min.css">
<script>

	function setCheckBoxFalse(checkbox){
		checbox.checked = false;
	}

	function toggleFinishedTasks(checkbox) {
		var terminadasDiv = document.getElementById('terminadasDiv');
		if (checkbox.checked == true) {
			terminadasDiv.style.display = 'block';
		} else {
			terminadasDiv.style.display = 'none';
		}
	}
</script>
</head>

<body>
	<div class="container">
	<table>
		<tr>
		<td><h2>${category.name}</h2></td>
		<c:if test="${category != null}">
		<td>
		<form action="editarCategoria">
			<input type="hidden" name="categoryId" value="${category.id}">
			<button type="submit" class="btn btn-primary">Editar</button>	
		</form>
		</td>
		<td>
		<form action="eliminarCategoria">	
			<input type="hidden" name="categoryId" value="${category.id}">
			<button type="submit" class="btn btn-primary">Eliminar</button>
		</form>
		</td>
		<td>
		<form action="duplicarCategoria">
			<input type="hidden" name="categoryId" value="${category.id}">
			<button type="submit" class="btn btn-primary">Duplicar</button>
		</form>
		</td>
		</c:if>
		</tr>
	</table>
		<form action="añadirTarea">
			<table class="table table-striped table-hover">
				<tbody>
					<tr>
						<td><input type="hidden" name="categoryId"
							value="${category.id}"></td>
						<td><input class="form-control input-sm" type="text"
							id="inputSmall" name="taskName"></td>
						<td><button type="submit" class="btn btn-primary">Add</button></td>
					</tr>
					<tr>
				</tbody>
			</table>
		</form>
		<table class="table table-striped table-hover">
			<tbody>
				<tr>
				<form>
					<input type="checkbox" id="checkbox" onchange="toggleFinishedTasks(this)">
					Mostrar tareas terminadas
				</form>
				<h3>Tareas sin terminar</h3>
				</tr>
				<c:forEach var="task" items="${listaTareas}" varStatus="i">
					<c:choose>
						<c:when test="${empty task.planned}">
							<tr id="item_${i.index}">
								<td><a href="mostrarTarea?taskId=${task.id}">${task.title}</a></td>
								<td><c:out value="No hay fecha planeada"></c:out></td>
								<td>
									<form action="terminarTarea">
										<input type="hidden" name="categoryId" value="${param.id}">
										<input type="hidden" name="taskId" value="${task.id}">
										<button type="submit" class="btn btn-primary">Finished</button>
									</form>
								</td>
							</tr>
						</c:when>
						<c:when test="${not empty task.planned}">
							<c:choose>
								<c:when test="${task.planned lt now}">
									<tr id="item_${i.index}">
										<td><a align="center"
											href="mostrarTarea?taskId=${task.id}">${task.title}</a></td>
										</td>
										<td class="danger"><c:out value="${task.planned}"></c:out></td>
										<td>
											<form action="terminarTarea">
												<input type="hidden" name="categoryId" value="${param.id}">
												<input type="hidden" name="taskId" value="${task.id}">
												<button type="submit" class="btn btn-primary">Finished</button>
											</form>
										</td>
									</tr>
								</c:when>
								<c:when test="${task.planned ge now}">
									<tr id="item_${i.index}">
										<td><a href="mostrarTarea?taskId=${task.id}">${task.title}</a></td>
										<td><c:out value="${task.planned}"></c:out></td>
										<td>
											<form action="terminarTarea">
												<input type="hidden" name="categoryId" value="${param.id}">
												<input type="hidden" name="taskId" value="${task.id}">
												<button type="submit" class="btn btn-primary">Finished</button>
											</form>
										</td>
									</tr>
								</c:when>
							</c:choose>
						</c:when>
					</c:choose>
				</c:forEach>
			</tbody>
		</table>

		<div class="container" id="terminadasDiv" style="display: none;">
			<h3>Tareas terminadas</h3>
			<table>
				<tbody>
					<c:forEach var="task" items="${listaTareasTerminadas}"
						varStatus="i">
						<tr>
						<td><a href="mostrarTarea?taskId=${task.id}">${task.title}</a></td>
						<td><c:out value="${task.planned}"></c:out></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>

	</div>
	<%@ include file="pieDePagina.jsp"%>
</body>
</html>