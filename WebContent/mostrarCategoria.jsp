<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="comprobarNavegacion.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>TaskManager - Mostrar categoria</title>
<link rel="stylesheet" href="https://bootswatch.com/flatly/bootstrap.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
</head>

<body>
	<table border="1" align="center">
			<tr>
				<th>ID</th>
				<td>${category.id}</td>
			</tr>
			<tr>
				<th>Name</th>
				<td>${category.name}</td>
			</tr>

	</table>
			<form method="POST" action="modificarCategoria">
				<input type="hidden" value="${category.id}" name="categoryId" /> 
				<input type="submit" class="button" value="Editar categoria" />
			</form>
	<%@ include file="pieDePagina.jsp" %>
</body>
