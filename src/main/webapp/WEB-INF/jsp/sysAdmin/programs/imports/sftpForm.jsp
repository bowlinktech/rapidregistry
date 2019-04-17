
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
            <form:form id="sftpForm" modelAttribute="sftpDetails" method="post" role="form">
                <input type="hidden" id="action" name="action" value="save" />
                <input type="hidden" id="progamNameURL" value="${programName}" />
               
                <form:hidden path="id" id="id" /> 
                <form:hidden path="programUploadTypeId"/> 
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
                   
                    
                    <spring:bind path="sftpPath">
                        <div class="form-group ${status.error ? 'has-error' : '' }">
                            <label class="control-label" for="sftpPath">SFTP Folder for RR *</label>
                            <form:input path="sftpPath" id="sftpPath" class="form-control" type="text" maxLength="255" />
                            <form:errors path="sftpPath" cssClass="control-label" element="label" />
                        </div>
                    </spring:bind>
                    <spring:bind path="username">
                        <div class="form-group ${status.error ? 'has-error' : '' }">
                            <label class="control-label" for="username">User Name *</label>
                            <form:input path="username" id="username" class="form-control" type="text" maxLength="45" />
                            <form:errors path="username" cssClass="control-label" element="label" />
                        </div>
                    </spring:bind>
                    <spring:bind path="password">
                        <div class="form-group ${status.error ? 'has-error' : '' }">
                            <label class="control-label" for="password">Password *</label>
                            <form:input path="password" id="password" class="form-control password" type="text" maxLength="45" />
                            <form:errors path="password" cssClass="control-label" element="label" />
                        </div>
                    </spring:bind>
                                           
                    </div>
                <div class="form-group">
                    <input type="button" id="submitSFTPForm" role="button" class="btn btn-primary" value="Save"/>
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