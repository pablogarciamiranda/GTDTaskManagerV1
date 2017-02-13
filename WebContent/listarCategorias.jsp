<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="comprobarNavegacion.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>TaskManager - Listado de categorías</title>
</head>
<body>
	<table border="1" align="center">
			<tr>
				<th>Lista</th>
				<th>Acciones</th>
			</tr>	
			<tr id="inbox">
				<td><a href="listarTareasInbox">Inbox</a></td>
			</tr>
			<tr id="hoy">
				<td><a href="listarTareasHoy">Hoy</a></td>
			</tr>
			<tr id="semana">
				<td><a href="listarTareasSemana">Esta semana</a></td>
			</tr>
		<c:forEach var="entry" items="${listaCategorias}" varStatus="i">
			<tr id="item_${i.index}">	
				<td><a href="listarTareas?id=${entry.id}">${entry.name}</a></td>
				<td><a href="mostrarCategoria?id=${entry.id}">Editar</a></td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="pieDePagina.jsp" %>
</body>
</html>