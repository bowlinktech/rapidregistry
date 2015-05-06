<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="main clearfix" role="main">

    <div class="col-md-12">
        <c:if test="${not empty savedStatus}" >
            <div class="alert alert-success" role="alert">
                <strong>Success!</strong> 
                The staff member has been successfully updated!
            </div>
        </c:if>
        <c:if test="${not empty param.msg}" >
            <div class="alert alert-success">
                <strong>Success!</strong> 
                <c:choose>
                    <c:when test="${param.msg == 'moduleAdded'}">The module(s) have been saved to the selected program!</c:when>
                    <c:when test="${param.msg == 'programAdded'}">The user has been successfully associated to the selected program!</c:when>
                    <c:when test="${param.msg == 'programRemoved'}">The user has been successfully removed from the selected program!</c:when>
                    <c:when test="${param.msg == 'entityAdded'}">The entity has been saved to the selected program!</c:when>
                    <c:when test="${param.msg == 'entityRemoved'}">The entity has been successfully removed from the selected program!</c:when>
                </c:choose>
            </div>
        </c:if>
    </div>

    <div class="col-md-6">
        <section class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">Details</h3>
            </div>
            <div class="panel-body">
                <div class="form-container">
                    <form:form id="staffdetails" commandName="staffdetails"  method="post" role="form">
                        <input type="hidden" id="action" name="action" value="save" />
                        <form:hidden path="roleId" />
                        <form:hidden path="id" />
                        <form:hidden path="createdBy" />
                        <form:hidden path="dateCreated" />
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
                        <spring:bind path="password">
                        	
                            <div id="passwordDiv" class="form-group ${status.error ? 'has-error' : '' }">
                                <label class="control-label" for="password">Password</label><br/><i>Leave blank if not changing</i>
                                <form:input path="password" id="password" class="form-control" type="password" maxLength="15" autocomplete="off"  />
                                <form:errors path="password" cssClass="control-label" element="label" />
                                <span id="passwordMsg" class="control-label"></span>
                            </div>
                        </spring:bind>
                        <div id="confirmPasswordDiv" class="form-group">
                            <label class="control-label" for="confirmPassword">Confirm Password</label>
                            <input id="confirmPassword" name="confirmpassword" class="form-control" maxLength="15" autocomplete="off" type="password" value="${staffdetails.getPassword()}" />
                            <span id="confirmPasswordMsg" class="control-label"></span>
                        </div>
                    </form:form>
                </div>
            </div>
        </section>
    </div>

    <div class="col-md-6">
        <section class="panel panel-default">
            <div class="panel-heading">
                <div class="pull-right">
                    <a href="#programModal" data-toggle="modal" class="btn btn-primary btn-xs btn-action" id="addProgram" title="Associate another Program">Associate another Program</a>
                </div>
                <h3 class="panel-title">Associated Programs</h3>
            </div>
            <div class="panel-body">
                <div class="form-container scrollable" id="associatedPrograms"></div>
            </div>
        </section>

    </div>
</div>

<!-- Program Entry Method modal -->
<div class="modal fade" id="programModal" role="dialog" tabindex="-1" aria-labeledby="Associate another Program" aria-hidden="true" aria-describedby="Associate another Program"></div>
<div class="modal fade" id="programModulesModal" role="dialog" tabindex="-1" aria-labeledby="Program Modules" aria-hidden="true" aria-describedby="Program Modules"></div>
<div class="modal fade" id="programEntityModal" role="dialog" tabindex="-1" aria-labeledby="Program Departments" aria-hidden="true" aria-describedby="Program Departments"></div>
