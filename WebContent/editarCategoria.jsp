<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>TaskManager - Editar Tarea</title>
</head>
<body>
							
	<form action="editarTarea" method="POST">
		<input type="hidden" name="taskId" value="${category.id}"/>
		<table>
			<tr>
				<td>Id:</td><td id="id" value = "${category.id}"></td>
			</tr>
			<tr>
				<td>Name:</td>
				<td id="name">
						<input type="text" name="newName" size="15"
							value="${category.name}"> 
				</td>
			</tr>
		</table>
		<input type="submit" value="Modificar">
	</form>
	<br/>	
</body>
</html>