<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h3 class="panel-title">${modalTitle} ${success}</h3>
         </div>
         <div class="modal-body">
             <div id="detailForm">
                 <form:form id="servicedetailsform" commandName="serviceDetails" modelAttribute="serviceDetails"  method="post" role="form">
                    <input type="hidden" id="encryptedURL" value="${encryptedURL}" />
                    <form:hidden path="id" id="id" />
                    <form:hidden path="programId" />
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
                        <spring:bind path="serviceName">
                            <div class="form-group ${status.error ? 'has-error' : '' }">
                                <label class="control-label" for="serviceName">Service Name *</label>
                                <form:input path="serviceName" id="serviceName" class="form-control" type="text" maxLength="55" />
                                <form:errors path="serviceName" cssClass="control-label" element="label" />
                            </div>
                        </spring:bind>
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
