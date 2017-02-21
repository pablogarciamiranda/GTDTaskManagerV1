<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ include file="comprobarNavegacion.jsp"%>
<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate value="${today}" pattern="MM.dd.yyyy" />
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
	<jsp:include page="navbar.jsp"/>
		<div class="container-fluid">
			<div class="col-md-2">
				<jsp:include page="listarCategorias.jsp" /></div>
			<div class="col-md-10">
				<div class="row">
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
							<tr>
						</tbody>
					</table>
				</div>
				<form action="añadirTarea?pseudolistaNombre=${pseudolistaNombre}">
					<table class="table table-striped table-hover">
						<tbody>
							<tr>
								<c:if test="${category.id != null}">
									<td><input type="hidden" name="categoryId"
										value="${category.id}"></td>
								</c:if>
								<td><input class="form-control input-sm" type="text"
									id="inputSmall" name="taskName"
									placeholder="Insert your task here"></td>
								<td><button type="submit" class="btn btn-primary">Add</button></td>
							</tr>
							<tr>
						</tbody>
					</table>
				</form>
				<table class="table table-striped table-hover">
					<caption>Tareas sin terminar</caption>
					<tbody>
						<c:forEach var="task" items="${listaTareas}" varStatus="i">
							<c:choose>
								<c:when test="${empty task.planned}">
									<tr id="item_${i.index}">
										<td><a href="mostrarTarea?taskId=${task.id}">${task.title}</a></td>
										<td><c:out value="No hay fecha planeada"></c:out></td>
										<td>
											<form action="terminarTarea">
												<c:choose>
													<c:when test="${category.id != null}">
														<input type="hidden" name="categoryId"
															value="${category.id}">
													</c:when>
													<c:otherwise>
														<input type="hidden" name="pseudolistaNombre"
															value="${pseudolistaNombre}">
													</c:otherwise>
												</c:choose>
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
												<td><a href="mostrarTarea?taskId=${task.id}">${task.title}</a></td>
												<td class="danger"><c:out value="${task.planned}"></c:out></td>
												<td>
													<form action="terminarTarea">
														<c:choose>
															<c:when test="${category.id != null}">
																<input type="hidden" name="categoryId"
																	value="${category.id}">
															</c:when>
															<c:otherwise>
																<input type="hidden" name="pseudolistaNombre"
																	value="${pseudolistaNombre}">
															</c:otherwise>
														</c:choose>
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
														<c:choose>
															<c:when test="${category.id != null}">
																<input type="hidden" name="categoryId"
																	value="${category.id}">
															</c:when>
															<c:otherwise>
																<input type="hidden" name="pseudolistaNombre"
																	value="${pseudolistaNombre}">
															</c:otherwise>
														</c:choose>
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
				<c:if test="${sePuedeMostrarTerminadas == true}">
					<form>
						<input type="checkbox" id="checkbox"
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
								<tr class="success">
									<td><a href="mostrarTarea?taskId=${task.id}">${task.title}</a></td>
									<td><c:out value="${task.planned}"></c:out></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<%@ include file="pieDePagina.jsp"%></body>
</html>