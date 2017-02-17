<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<c:if test="${not empty error}">
  		  <div class="alert alert-danger alert-dismissable">
		    <a href="#" class="close" data-dismiss="alert" aria-label="close">�</a>
		    <strong>Error: </strong>${error}
  		  </div>
</c:if>