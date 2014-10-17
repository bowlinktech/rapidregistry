
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h3 class="panel-title">Create New Program ${success}</h3>
        </div>
        <div class="modal-body">
            <form:form id="program" commandName="program"  method="post" role="form">
                <input type="hidden" id="action" name="action" value="save" />
                <form:hidden path="id" id="id" />
                <form:hidden path="dateCreated" />
                <spring:bind path="status">
                    <div class="form-group ${status.error ? 'has-error' : '' }">
                        <label class="control-label" for="status">Status *</label>
                        <div>
                            <label class="radio-inline">
                                <form:radiobutton id="status" path="status" value="true" /> Active
                            </label>
                            <label class="radio-inline">
                                <form:radiobutton id="status" path="status" value="false" /> Inactive
                            </label>
                        </div>
                    </div>
                </spring:bind> 
                <spring:bind path="programName">
                    <div class="form-group ${status.error ? 'has-error' : '' } ${not empty existingProgram ? 'has-error' : ''}">
                        <label class="control-label" for="programName">Program Name *</label>
                        <form:input path="programName" id="programName" class="form-control" type="text" maxLength="55" />
                        <form:errors path="programName" cssClass="control-label" element="label" />
                        <c:if test="${not empty existingProgram}"><span class="control-label has-error">${existingProgram}</span></c:if>
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
                <div class="form-group">
                    <input type="button" id="submitButton" role="button" class="btn btn-primary" value="Save"/>
                </div>
            </form:form>
        </div>
    </div>
</div>

<script type="text/javascript">

    $(document).ready(function() {
        $("input:text,form").attr("autocomplete", "off");
    });

</script>