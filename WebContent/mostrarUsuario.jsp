<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="uo.sdi.dto.types.UserStatus" %>
<%@ include file="comprobarNavegacion.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>TaskManager - Página principal del usuario</title>
<link rel="stylesheet" href="https://bootswatch.com/flatly/bootstrap.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
</head>
<body>
<i>Iniciaste sesión el <fmt:formatDate pattern="dd-MM-yyyy' a las 'HH:mm" 
								value="${sessionScope.fechaInicioSesion}"/>
								(usuario número ${contador})</i>
	<br/><br/>
	<jsp:useBean id="userToEdit" class="uo.sdi.dto.User" scope="session" />
	<jsp:include page="messages.jsp"></jsp:include>
	<form id = "editarUsuario" action="editarUsuario" method="POST">
	<div>
		<table>
			<c:if test="${sessionScope.user.isAdmin}">
				<tr>
					<td>Id:</td><td id="id"><jsp:getProperty property="id" name="userToEdit" /></td>
				</tr>
			</c:if>
			<tr>
				<td>Email:</td>
				<td id="email">
						<input type="text" name="newEmail" size="15"
							value="<jsp:getProperty property="email" name="userToEdit"/>"> 
				</td>
			</tr>
			<tr>
				<td>Password:</td>
				<td id="password">
						<input type="password" name="password" size="15"> 
				</td>
			</tr>
			<tr>
				<td>New password:</td>
				<td id="password">
						<input type="password" name="newPassword" size="15"> 
				</td>
			</tr>
			<tr>
				<td>Repeat New password:</td>
				<td id="password">
						<input type="password" name="newPassword2" size="15"> 
				</td>
			</tr>
			<tr>
				<td>Es administrador:</td><td id="isAdmin"><jsp:getProperty property="isAdmin" name="userToEdit" /></td>
			</tr>
			<tr>
				<td>Login:</td><td id="login"><input type="text" name="newLogin" size="15"
							value="<jsp:getProperty property="login" name="userToEdit" />"> </td>
			</tr>
			<c:if test="${sessionScope.user.isAdmin}">
				<tr>
					<td>Estado:</td>
					<td id="status">	
						 <c:if test="${userToEdit.status eq UserStatus.ENABLED}">
	                    	<jsp:getProperty property="status" name="userToEdit" />
	                     </c:if>
	                     <c:if test="${userToEdit.status eq UserStatus.DISABLED}">
	                    	<jsp:getProperty property="status" name="userToEdit" />
	                     </c:if>
                    </td>
				</tr>
			</c:if>
		</table>
		<input type="hidden" name="login" value="<jsp:getProperty property="login" name="userToEdit" />">
		<input type="submit" name="editarUsuario" value="Editar" form="editarUsuario">
	</div>
	</form>	
	
	<c:if test="${sessionScope.user.isAdmin}">
		<form id = "eliminarUsuario" action="eliminarUsuario" method="POST">
			<input type="hidden" name="id" value="<jsp:getProperty property="id" name="userToEdit" />">
			<input type="submit" value="Eliminar" >
		</form>
		<form id = "cambiarEstado" action="cambiarEstado" method="POST">
			<input type="hidden" name="id" value="<jsp:getProperty property="id" name="userToEdit" />">
			<input type="submit" value="Cambiar Estado" >
		</form>
	</c:if>
	<br/>	
	<a id="cerrarSesion" href="cerrarSesion">Cerrar sesión</a>
	
	<%@ include file="pieDePagina.jsp" %>
</body>
</html>
