<%-- 
    Document   : fieldForm
    Created on : Jan 15, 2015, 9:59:09 AM
    Author     : chadmccue
--%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h3 class="panel-title">Edit Selected Field ${success}</h3>
        </div>
        <div class="modal-body">
            <form:form id="fieldForm" modelAttribute="fieldDetails" method="post" role="form">
                <input type="hidden" id="action" name="action" value="save" />
                <input type="hidden" id="progamNameURL" value="${programName}" />
                <form:hidden path="id" id="id" /> 
                <form:hidden path="programUploadTypeId" />
                <form:hidden path="fieldId" />
                <form:hidden path="dateCreated" />
                <form:hidden path="dspPos" />
                <div class="form-container">
                    <div class="form-group">
                        <label class="control-label" for="fieldValidation">Field Validation</label>
                        <form:select path="validationId" id="fieldValidation" class="form-control half">
                            <c:forEach items="${validationTypes}"  var="fieldvalidationtypes" varStatus="vtype">
                                <option value="${validationTypes[vtype.index][0]}" <c:if test="${fieldDetails.validationId == validationTypes[vtype.index][0]}">selected</c:if>>${validationTypes[vtype.index][1]}</option>
                            </c:forEach>
                        </form:select>
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="requiredField">Required Field *</label>
                        <form:select path="requiredField" id="requiredField" class="form-control half">
                            <option value="false" <c:if test="${fieldDetails.requiredField == false}">selected</c:if>>False</option>
                            <option value="true" <c:if test="${fieldDetails.requiredField == true}">selected</c:if>>True</option>
                        </form:select>
                    </div> 
                    <div class="form-group">
                        <label class="control-label" for="useField">Use Field *</label>
                        <form:select path="useField" id="useField" class="form-control half">
                            <option value="false" <c:if test="${fieldDetails.useField == false}">selected</c:if>>False</option>
                            <option value="true" <c:if test="${fieldDetails.useField == true}">selected</c:if>>True</option>
                        </form:select>
                    </div>                     
                    <div class="form-group">
                        <input type="button" id="submitFieldEditButton"  class="btn btn-primary" value="Update Field"/>
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
