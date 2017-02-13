<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="comprobarNavegacion.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>TaskManager - Página de administracion</title>
</head>
<body>
<form action="modificarUsuario" method="POST">
	<table>
			<tr>
				<td>Nombre de usuario:</td>
				<td id="login">
						<input type="text" name="login" size="15"> 
				</td>
			</tr>
	</table>
	<input type="submit" value="Modificar">
</form>
</body>