<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h3 class="panel-title"><c:choose><c:when test="${btnValue == 'Update'}">Update</c:when><c:when test="${btnValue == 'Create'}">Add</c:when></c:choose> Activity Code Category ${success}</h3>
         </div>
         <div class="modal-body">
                <form:form id="categorydetailsform" commandName="categorydetails" modelAttribute="categorydetails"  method="post" role="form">
                   <form:hidden path="id" id="id" />
                   <div class="form-container">
                       <spring:bind path="category">
                           <div class="form-group ${status.error ? 'has-error' : '' }">
                               <label class="control-label" for="category">Category *</label>
                               <form:input path="category" id="category" class="form-control" type="text" maxLength="55" />
                               <form:errors path="category" cssClass="control-label" element="label" />
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
