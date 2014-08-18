<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h3 class="panel-title"><c:choose><c:when test="${btnValue == 'Update'}">Update</c:when><c:when test="${btnValue == 'Create'}">Add</c:when></c:choose> Report ${success}</h3>
         </div>
         <div class="modal-body">
                <form:form id="reportform" commandName="reportdetails" modelAttribute="reportdetails"  method="post" role="form">
                   <form:hidden path="id" id="id" />
                   <form:hidden path="dateCreated" />
                   <div class="form-container">
                       <spring:bind path="reportName">
                           <div class="form-group ${status.error ? 'has-error' : '' }">
                               <label class="control-label" for="reportName">Report Name *</label>
                               <form:input path="reportName" id="reportName" class="form-control" type="text" maxLength="255" />
                               <form:errors path="reportName" cssClass="control-label" element="label" />
                           </div>
                       </spring:bind>
                       <spring:bind path="jspPage">
                           <div class="form-group ${status.error ? 'has-error' : '' }">
                               <label class="control-label" for="jspPage">JSP Page *</label>
                               <form:input path="jspPage" id="jspPage" class="form-control" type="text" maxLength="255" />
                               <form:errors path="jspPage" cssClass="control-label" element="label" />
                           </div>
                       </spring:bind>
                       <spring:bind path="reportDesc">
                           <div class="form-group ${status.error ? 'has-error' : '' }">
                               <label class="control-label" for="reportDesc">Report Description *</label>
                               <form:textarea path="reportDesc" class="form-control"  rows="10" />
                               <form:errors path="reportDesc" cssClass="control-label" element="label" />
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

<script type="text/javascript">

    $(document).ready(function() {
        $("input:text,form").attr("autocomplete", "off");
    });
</script>
