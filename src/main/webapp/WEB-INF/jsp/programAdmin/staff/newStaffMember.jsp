<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h3 class="panel-title">Create New User ${encryptedURL}</h3>
         </div>
         <div class="modal-body">
             <div id="detailForm">
                 <form:form id="staffmemberdetailsform" commandName="staffdetails" modelAttribute="staffdetails"  method="post" role="form">
                    <input type="hidden" id="encryptedURL" value="${encryptedURL}" />
                    <form:hidden path="id" id="id" />
                    <form:hidden path="roleId" />
                    <form:hidden path="createdBy" />
                    <form:hidden path="dateCreated" />
                    <div class="form-container">
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
                            <div class="form-group ${status.error || not empty existingUser ? 'has-error' : '' }">
                                <label class="control-label" for="email">Email *</label>
                                <form:input path="email" id="email" class="form-control" type="text"  maxLength="255" />
                                <form:errors path="email" cssClass="control-label" element="label" />
                                <c:if test="${not empty existingUser}"><span class="control-label has-error">${existingUser}</span></c:if>
                            </div>
                        </spring:bind>
                         <spring:bind path="username">
                            <div id="usernameDiv" class="form-group ${status.error ? 'has-error' : '' }">
                                <label class="control-label" for="password">Username *</label>
                                <form:input path="username" id="username" class="form-control" type="text" maxLength="2555" autocomplete="off"  />
                                <form:errors path="username" cssClass="control-label" element="label" />
                                <span id="usernameMsg" class="control-label"></span>
                            </div>
                        </spring:bind>        
                        <spring:bind path="password">
                            <div id="passwordDiv" class="form-group ${status.error ? 'has-error' : '' }">
                                <label class="control-label" for="password">Password *</label>
                                <form:input path="password" id="password" class="form-control" type="password" maxLength="15" autocomplete="off"  />
                                <form:errors path="password" cssClass="control-label" element="label" />
                                <span id="passwordMsg" class="control-label"></span>
                            </div>
                        </spring:bind>
                        <div id="confirmPasswordDiv" class="form-group">
                            <label class="control-label" for="confirmPassword">Confirm Password *</label>
                            <input id="confirmPassword" name="confirmpassword" class="form-control" maxLength="15" autocomplete="off" type="password" value="" />
                            <span id="confirmPasswordMsg" class="control-label"></span>
                        </div>
                        <div class="form-group">
                            <input type="button" id="submitButton" rel="${btnValue}" role="button" class="btn btn-primary" value="${btnValue}"/>
                        </div>
                    </div>
                </form:form>
             </div>
             
        </div>
    </div>
</div>

<script type="text/javascript">

    $(document).ready(function() {
        $("input:text,form").attr("autocomplete", "off");
    });
</script>
