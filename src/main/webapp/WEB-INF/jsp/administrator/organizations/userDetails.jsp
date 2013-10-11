
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="modal-dialog">
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			<h3 class="panel-title">Add System Users</h3>
		</div>
		<div class="modal-body">
			<form:form id="userdetailsform" commandName="userdetails" modelAttribute="userdetails"  method="post" role="form">
				<form:hidden path="id" id="id" />
				<form:hidden path="orgId" id="orgId" />
				<form:hidden path="dateCreated" />
				<div class="form-container">
					<div class="form-group">
						<div>
							<label class="radio-inline" for="mainContact1">
								<form:radiobutton id="mainContact1" path="mainContact" value="1" /> Primary Contact
							</label>
							<label class="radio-inline" for="mainContact2">
								<form:radiobutton id="mainContact" path="mainContact" value="2" /> Secondary Contact
							</label>
							<label class="radio-inline" for="mainContact2">
								<form:radiobutton id="mainContact" path="mainContact" value="0" /> Not a Main Contact
							</label>
						</div>
					</div>
					<div class="form-group">
						<label class="checkbox-inline" for="sendEmailAlert">
							<form:checkbox id="sendEmailAlert" path="sendEmailAlert" value="true" /> Alert me via email on any new message my organization receives.
						</label>
					</div>
					<div class="form-group">
						<label for="status">Status *</label>
						<div>
							<label class="radio-inline">
								<form:radiobutton id="status" path="status" value="true" /> Active
							</label>
							<label class="radio-inline">
								<form:radiobutton id="status" path="status" value="false" /> Inactive
							</label>
						</div>
					</div>
					<spring:bind path="firstName">
						<div class="form-group ${status.error ? 'has-error' : '' }">
							<label class="control-label" for="firstName">First Name *</label>
							<form:input path="firstName" id="firstName" class="form-control" type="text" maxLength="55" />
							<form:errors path="firstName" cssClass="control-label" element="label" />
						</div>
					</spring:bind>
					<spring:bind path="lastName">
						<div class="form-group ${status.error ? 'has-error' : '' }">
							<label class="control-label" for="lastName">Last Name *</label>
							<form:input path="lastName" id="lastName" class="form-control" type="text" maxLength="55" />
							<form:errors path="lastName" cssClass="control-label" element="label" />
						</div>
					</spring:bind>
					<spring:bind path="email">
						<div class="form-group ${status.error ? 'has-error' : '' }">
							<label class="control-label" for="email">Email</label>
							<form:input path="email" id="email" class="form-control" type="text"  maxLength="255" />
							<form:errors path="email" cssClass="control-label" element="label" />
						</div>
					</spring:bind>
					<spring:bind path="username">
						<div class="form-group ${status.error ? 'has-error' : '' } ${not empty existingUsername ? 'has-error' : ''}">
							<label class="control-label" for="username">Username *</label>
							<form:input path="username" id="username" class="form-control" type="text" maxLength="15" />
							<form:errors path="username" cssClass="control-label" element="label" />
							<c:if test="${not empty existingUsername}"><span class="control-label">${existingUsername}</span></c:if>
						</div>
					</spring:bind>
					<spring:bind path="password">
						<div id="passwordDiv" class="form-group ${status.error ? 'has-error' : '' }">
							<label class="control-label" for="password">Password *</label>
							<form:input path="password" id="password" class="form-control" type="password" maxLength="15"  />
							<form:errors path="password" cssClass="control-label" element="label" />
						</div>
					</spring:bind>
					<div id="confirmPasswordDiv" class="form-group">
						<label class="control-label" for="confirmPassword">Confirm Password *</label>
						<input id="confirmPassword" name="confirmpassword" class="form-control" maxLength="15"  type="password" value=${userdetails.getPassword()} />
						<span id="confimPasswordMsg" class="control-label"></span>
					</div>
					<div class="form-group">
						<label class="control-label" for="section">Available Sections</label>
						<c:forEach items="${sections}" var="section" varStatus="loopStatus">  
							 <div class="row">
								<div class="col-md-6">
									<label class="checkbox-inline">
										 <form:checkbox id="sectionList" path="sectionList" value="${section.id}" />${section.featureName}
									</label>
								</div>
							</div>
						</c:forEach>
					</div>
					<div class="form-group">
						<input type="button" id="submitButton" rel="${btnValue}" class="btn btn-primary" value="${btnValue}"/>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>

<script type="text/javascript">

	$(document).ready(function() {
	    $("input:text,form").attr("autocomplete","off");
	});

	

</script>