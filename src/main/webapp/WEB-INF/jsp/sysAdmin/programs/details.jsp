<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="main clearfix" role="main">

    <div class="col-md-12">

        <c:if test="${not empty savedStatus}" >
            <div class="alert alert-success" role="alert">
                <strong>Success!</strong> 
                <c:choose><c:when test="${savedStatus == 'updated'}">The program has been successfully updated!</c:when><c:otherwise>The program has been successfully created!</c:otherwise></c:choose>
                    </div>
        </c:if>

        <form:form id="program" commandName="program"  method="post" role="form">
            <input type="hidden" id="action" name="action" value="save" />
            <form:hidden path="id" id="id" />
            <form:hidden path="dateCreated" />

            <section class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Details</h3>
                </div>
                <div class="panel-body">
                    <div class="form-container">
                        <spring:bind path="programName">
                            <div class="form-group ${status.error ? 'has-error' : '' } ${not empty existingProgram ? 'has-error' : ''}">
                                <label class="control-label" for="programName">Program Name *</label>
                                <form:input path="programName" id="programName" class="form-control" type="text" maxLength="55" />
                                <form:errors path="programName" cssClass="control-label" element="label" />
                                <c:if test="${not empty existingProgram}">${existingProgram}</c:if>
                            </div>
                        </spring:bind>
                        <spring:bind path="programDesc">
                            <div class="form-group ${status.error ? 'has-error' : '' }">
                                <label class="control-label" for="programDesc">Program Desc</label>
                                <form:textarea path="programDesc" id="programDesc" class="form-control" rows="15" />
                           </div>
                        </spring:bind>  
                        <spring:bind path="emailAddress">
                            <div class="form-group ${status.error ? 'has-error' : '' }">
                                <label class="control-label" for="emailAddress">Program Email Address *</label>
                                <form:input path="emailAddress" id="emailAddress" class="form-control" type="text" maxLength="255" />
                                <form:errors path="emailAddress" cssClass="control-label" element="label" />
                                <h6>(This email address will be used in the FROM field when emails are sent from this program)</h6>
                            </div>
                        </spring:bind>
                    </div>
                </div>
            </section>
        </form:form>
    </div>
</div>


