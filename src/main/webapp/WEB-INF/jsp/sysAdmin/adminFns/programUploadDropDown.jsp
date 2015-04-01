<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<option  value="">- Program Upload Types - </option>
    <c:forEach items="${putList}" var="programUploadType">
    	<option value="${programUploadType.id}">${programUploadType.name}</option>
    </c:forEach>                        
                   
                    