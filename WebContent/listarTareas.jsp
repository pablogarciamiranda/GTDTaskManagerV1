<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ include file="comprobarNavegacion.jsp" %>
<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate value="${today}" pattern="MM.dd.yyyy" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>TaskManager - Listado de tareas del día</title>
<!-- Website CSS style -->
		<link rel="stylesheet" type="text/css" href="https://bootswatch.com/flatly/bootstrap.min.css">

		<!-- Website Font style -->
	    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.1/css/font-awesome.min.css">
		
		<!-- Google Fonts -->
		<link href='https://fonts.googleapis.com/css?family=Passion+One' rel='stylesheet' type='text/css'>
		<link href='https://fonts.googleapis.com/css?family=Oxygen' rel='stylesheet' type='text/css'>
<link rel="stylesheet" href="https://bootswatch.com/flatly/bootstrap.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
</head>
<body>
	<form action="añadirTarea">
	Nueva Tarea: <input type="text" name="fname"><br>
	<input type="submit" value="Añadir">
	</form>
	<table border="1" align="center">
			<tr>
				<th>ID</th>
				<th>Title</th>
				<th>Planned</th>
				<th>Options</th>
			</tr>
		<c:forEach var="task" items="${listaTareas}" varStatus="i">
			<tr id="item_${i.index}">
				<td>${task.id}</td>
				<td>${task.title}</td>
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
				<td><a href="mostrarTarea?taskId=${task.id}">Ver</a>
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="pieDePagina.jsp" %>
</body>
</html>