<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!-- Bootstrap theme -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html><head>
<title>TaskManager - Registrarse</title>
<link rel="stylesheet" href="https://bootswatch.com/flatly/bootstrap.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
</head>
<body>
  <form action="registrarse" method="post" name="registrarse_form_name">

 	<center><h1>Regístrate</h1></center>
 	<hr><br>
 	<table align="center">
    	<tr> 
    		<td align="left">Nombre de usuario:</td>
	    	<td><input type="text" name="loginUsuario" align="left" size="15"></td>
      	</tr>
      	<tr> 
    		<td align="left">Email:</td>
	    	<td><input type="text" name="emailUsuario" align="left" size="15"></td>
      	</tr>
      	<tr> 
    		<td align="left">Password:</td>
	    	<td><input type="password" name="passwordUsuario" align="left" size="15"></td>
      	</tr>
      	      	<tr> 
    		<td align="left">Repetir password:</td>
	    	<td><input type="password" name="confirmPasswordUsuario" align="left" size="15"></td>
      	</tr>
      	<tr>
    	    <td align="left"></td><td><input type="submit" value="Enviar"/></td>
      	</tr>
      </table>
   </form>
   <%@ include file="pieDePagina.jsp" %>
</body>
</html>