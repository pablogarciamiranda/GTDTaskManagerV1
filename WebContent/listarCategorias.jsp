<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="comprobarNavegacion.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>TaskManager - Listado de categorías</title>
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
</head>

<body>
	<h2>Lists</h2>
	<table class="table table-striped table-hover" id="listaDeCategorias">
		<tbody>
			<tr id="inbox">
				<td colspan=2><a href="listarTareasInbox">Inbox</a></td>
			</tr>
			<tr id="hoy">
				<td colspan=2><a href="listarTareasHoy">Hoy</a></td>
			</tr>
			<tr id="semana">
				<td colspan=2><a href="listarTareasSemana">Esta semana</a></td>
			</tr>
			<c:forEach var="entry" items="${listaCategorias}" varStatus="i">
				<tr id="item_${i.index}">
					<td><a href="listarTareas?categoryId=${entry.id}">${entry.name}</a></td>
				</tr>
			</c:forEach>
			<tr id="añadirCategoria">
				<td>
					<form action="añadirCategoria" id="add_category_form">
						<table class="table table-striped table-hover">
							<tbody>
								<tr>
									<td><input class="form-control input-sm" type="text"
										id="categoryName" name="categoryName"
										placeholder="Name of the category"></td>
									<td><button type="submit" id="add_category"
											class="btn btn-primary">Add</button></td>
								</tr>
								<tr>
							</tbody>
						</table>
					</form>
				</td>
			</tr>

		</tbody>
	</table>
</body>
</html>