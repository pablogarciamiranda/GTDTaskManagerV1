<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>TaskManager - Editar Tarea</title>
</head>
<body>
							
	<form action="editarTarea" method="POST">
		<input type="hidden" name="taskId" value="${task.id}"/>
		<table>
			<tr>
				<td>Id:</td><td id="id" value = "${task.id}"></td>
			</tr>
			<tr>
				<td>Name:</td>
				<td id="title">
						<input type="text" name="newTitle" size="15"
							value="${task.title}"> 
				</td>
			</tr>
			<tr>
				<td>Planned Date:</td>
				<td id="plannedDate">
						<input type="text" name="newPlannedDate" size="15"
							value="${task.planned}"> 
				</td>
			</tr>
			<tr>
				<td>Comment:</td>
				<td id="comment">
						<textarea rows="4" cols="50" name="newComment">${task.comments}</textarea>
				</td>
			</tr>
			<tr>
				<td>Category:</td>
				<td id="category">
						<select name="newCategoryId">
							<c:forEach items="${categories}" var="id">
							    <option value="${id.id}">${id.name}</option>
							</c:forEach>
						</select>
				</td>
			</tr>
		</table>
		<input type="submit" value="Modificar">
	</form>
	<br/>	
</body>
</html>