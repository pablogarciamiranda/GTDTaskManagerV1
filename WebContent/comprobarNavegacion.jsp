<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${requestScope.jspSiguiente==null}">
	<jsp:forward page="../login.jsp" />
</c:if>
