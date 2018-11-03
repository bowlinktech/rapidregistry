<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:if test="${fn:length(userList) > 0}">
<select id="loginAsUser" name="loginAsUser" class="form-control loginAsUser">
		<option value="">Please select the user you wish to login as</option>
            <c:forEach items="${userList}" var="user" varStatus="userCount">
				<option value="${user.encryptedUserName}">&#160;${user.firstName}&#160;${user.lastName}&#160;(${user.username})</option>
		</c:forEach>	
</select>
<input type="hidden" value="https://${programURL}/login" name="programURL" id="programURL"/>	
</c:if>
