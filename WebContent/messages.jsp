<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${not empty errors}">

		<div class="alert alert-dismissible alert-danger">
		<c:forEach var="error" items="${errors}">
			<c:out value="${error}" />
		</c:forEach>
		</div>
</c:if>