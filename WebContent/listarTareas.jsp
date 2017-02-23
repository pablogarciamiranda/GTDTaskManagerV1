<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="comprobarNavegacion.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="alb.util.date.DateUtil"%>
<%@ include file="comprobarNavegacion.jsp"%>
<c:set var="today" value="<%=DateUtil.today()%>" />
<!DOCTYPE html>
<html>
<head>
<title>TaskManager - Listado de tareas del día</title>
<!-- Website CSS style -->
<link rel="stylesheet" type="text/css"
	href="https://bootswatch.com/flatly/bootstrap.css">

<!-- Google Fonts -->
<link href='https://fonts.googleapis.com/css?family=Passion+One'
	rel='stylesheet' type='text/css'>
<link href='https://fonts.googleapis.com/css?family=Oxygen'
	rel='stylesheet' type='text/css'>
<script>
	function setCheckBoxFalse() {
		checkbox.checked = false;
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
<body onload="setCheckBoxFalse()">
	<jsp:include page="navbar.jsp" />
	<div class="container-fluid">
		<div class="col-md-2">
			<jsp:include page="listarCategorias.jsp" /></div>
		<div class="col-md-10">
			<div class="row">
				<jsp:include page="messages.jsp" />
				<div class="col-md-8">
					<c:if test="${category != null}">
						<div class="form-group">
							<form action="editarCategoria">
								<div class="input-group">
									<span class="input-group-addon">Category</span> <input
										type="text" class="form-control" name="newName"
										placeholder="${category.name}"> <input type="hidden"
										name="categoryId" value="${category.id}"> <span
										class="input-group-btn">
										<button class="btn btn-default" type="submit">Editar</button>
									</span>
								</div>
							</form>

						</div>

					</c:if>
					<c:if test="${category == null}">
						<h3>${listaMostrada}</h3>
					</c:if>
				</div>
				<table>
					<tbody>
						<tr>
							<td></td>
							<c:if test="${category != null}">
								<td>
									<form action="eliminarCategoria"
										onsubmit="return confirm('Do you really want' +
											'to delete the category?\n If you accept every task from this category'
													+ ' will be removed');">
										<input type="hidden" name="categoryId" value="${category.id}">
										<button type="submit" id="eliminar_categoria"
											class="btn btn-primary">Eliminar</button>
									</form>
								</td>

								<td>
									<form action="duplicarCategoria">
										<input type="hidden" name="categoryId" value="${category.id}">
										<button type="submit" id="duplicar_categoria"
											class="btn btn-primary">Duplicar</button>
									</form>
								</td>
							</c:if>
						<tr>
					</tbody>
				</table>
			</div>
			<form action="añadirTarea" id="add_task_form">
				<input type="hidden" name="listaMostrada" value="${listaMostrada}">
				<table class="table table-striped table-hover">
					<tbody>
						<tr>
							<c:if test="${category.id != null}">
								<td><input type="hidden" name="categoryId"
									value="${category.id}"></td>

							</c:if>
							<td><input class="form-control input-sm" type="text"
								id="input_Task" name="taskName"
								placeholder="Insert your task here"></td>
							<td><button type="submit" id="add_task"
									class="btn btn-primary">Add</button></td>
						</tr>
						<tr>
					</tbody>
				</table>
			</form>
			<table class="table table-striped table-hover">
				<caption>Tareas sin terminar</caption>
				<tbody>
					<c:forEach var="task" items="${listaTareas}" varStatus="i">
						<fmt:formatDate value="${task.planned}" var="plannedDate"
							pattern="EEEEEEEEEE dd/MM/yyyy" />
						<tr id="item_${i.index}">
							<td><a href="mostrarTarea?taskId=${task.id}">${task.title}</a></td>
							<c:if
								test="${listaMostrada eq 'Hoy' or listaMostrada eq 'Semana' }">
								<!-- Aqui iria el nombre de la categoria -->
								<c:forEach var="category" items="${listaCategorias}" varStatus="i">
									<c:if test= "${category.id == task.categoryId}">
										<td>${category.name}</td>
									</c:if>
									
									
									
								</c:forEach>
								
							</c:if>
							<c:choose>
								<c:when test="${empty task.planned}">
									<td>No hay fecha planeada</td>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${task.planned lt today}">
											<td class="danger"><c:out value="${plannedDate}"></c:out></td>
										</c:when>
										<c:otherwise>
											<td><c:out value="${plannedDate}"></c:out></td>
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
							<td>
								<form action="terminarTarea">
									<c:choose>
										<c:when test="${category.id != null}">
											<input type="hidden" name="categoryId" value="${category.id}">
										</c:when>
										<c:otherwise>
											<input type="hidden" name="listaMostrada"
												value="${listaMostrada}">
										</c:otherwise>
									</c:choose>
									<input type="hidden" name="taskId" value="${task.id}">
									<button id="terminar_tarea_${task.title}" type="submit" class="btn btn-primary">Finished</button>
								</form>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<c:if test="${sePuedeMostrarTerminadas == true}">
				<form id="listar_terminadas_form">
					<input type="checkbox" name="checkbox" id="checkbox"
						onchange="toggleFinishedTasks(this)"> Mostrar tareas
					terminadas
				</form>
			</c:if>

			<div id="terminadasDiv" style="display: none;">
				<table class="table table-striped table-hover ">
					<caption>Tareas terminadas</caption>
					<tbody>
						<c:forEach var="task" items="${listaTareasTerminadas}"
							varStatus="i">
							<fmt:formatDate value="${task.created}" var="plannedDate"
								pattern="EEEEEEEEEE dd/MM/yyyy" />
							<tr class="success">
								<td><a href="mostrarTarea?taskId=${task.id}">${task.title}</a></td>
								<td><c:out value="${plannedDate}"></c:out></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<%@ include file="pieDePagina.jsp"%></body>
</html>