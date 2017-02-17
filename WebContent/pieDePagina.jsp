	<%@ page contentType="text/html" pageEncoding="UTF-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<br/>
	<br/>
	${requestScope.mensajeParaElUsuario!=null ? '<i>' += requestScope.mensajeParaElUsuario += '</i>' : ''}
	<c:out value="${dateValue }"></c:out>