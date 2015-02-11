
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h3 class="panel-title">${modalTitle} ${success}</h3>
        </div>
        <div class="modal-body">
            <form:form id="importTypeForm" modelAttribute="importTypeDetails" method="post" role="form">
                <input type="hidden" id="action" name="action" value="save" />
                <input type="hidden" id="progamNameURL" value="${programName}" />
                <form:hidden path="id" id="id" /> 
                <form:hidden path="programId" />
                <form:hidden path="dateCreated" />
                 <div class="form-group">
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
                    <spring:bind path="useMCI">
                        <div class="form-group ${status.error ? 'has-error' : '' }">
                            <label class="control-label" for="useMCI">Use MCI *</label>
                            <div>
                                <label class="radio-inline">
                                    <form:radiobutton id="useMCI" path="useMCI" value="true" /> Yes
                                </label>
                                <label class="radio-inline">
                                    <form:radiobutton id="useMCI" path="useMCI" value="false" /> No, overwrite matching records
                                </label>
                            </div>
                        </div>
                    </spring:bind>  
                    <spring:bind path="name">
                        <div class="form-group ${status.error ? 'has-error' : '' }">
                            <label class="control-label" for="name">Name *</label>
                            <form:input path="name" id="name" class="form-control" type="text" maxLength="255" />
                            <form:errors path="name" cssClass="control-label" element="label" />
                        </div>
                    </spring:bind>
                </div>
                <div class="form-group">
                    <input type="button" id="submitImportType" role="button" class="btn btn-primary" value="Save"/>
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