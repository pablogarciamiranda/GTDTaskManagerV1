
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<footer>
<div class="container text-center">
	<small>© Copyright 2016, <a href="mailto:${initParam.email1}">${initParam.autor1}</a> y
	<a href="mailto:${initParam.email2}">${initParam.autor2}</a></small>
	<c:out value="${dateValue }"></c:out>
	${requestScope.mensajeParaElUsuario!=null ? '<i>' += requestScope.mensajeParaElUsuario += '</i>' : ''}
	</div>
</footer>

