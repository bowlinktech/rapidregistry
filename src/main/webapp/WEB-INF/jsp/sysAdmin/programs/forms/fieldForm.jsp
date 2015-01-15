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
                <input type="hidden" id="section" name="section" value="${section}" />
                <form:hidden path="id" id="id" /> 
                <form:hidden path="programId" />
                <form:hidden path="sectionId" />
                <form:hidden path="fieldId" />
                <form:hidden path="dateCreated" />
                <form:hidden path="searchDspPos" />
                <form:hidden path="dspPos" />
                <form:hidden path="dataGridColumn" />
                <div class="form-container">
                    <div id="fieldDisplayDiv" class="form-group ${status.error ? 'has-error' : '' }">
                        <label class="control-label" for="fieldDisplayName">Field Display Name</label>
                         <form:input path="fieldDisplayname" id="fieldDisplayName" class="form-control"  maxLength="55" />
                        <span id="fieldDisplayMsg" class="control-label"></span>
                    </div>
                    <div id="crosswalkDiv" class="form-group">
                        <label class="control-label" for="Crosswalk">Field Crosswalk</label>
                        <form:select path="crosswalkId" id="crosswalk" class="form-control half">
                            <option value="0">- Select -</option>
                            <c:forEach items="${crosswalks}" var="cwalk" varStatus="cStatus">
                                <option value="${crosswalks[cStatus.index].id}" <c:if test="${fieldDetails.crosswalkId == crosswalks[cStatus.index].id}">selected</c:if>>${crosswalks[cStatus.index].name} <c:choose><c:when test="${crosswalks[cStatus.index].programId == 0}"> (generic)</c:when><c:otherwise> (Program Specific)</c:otherwise></c:choose></option>
                            </c:forEach>
                        </form:select>
                    </div>
                    <div class="form-group">
                        <label class="control-label" for=fieldValidation">Field Validation</label>
                        <form:select path="validationId" id="fieldValidation" class="form-control half">
                            <c:forEach items="${validationTypes}"  var="fieldvalidationtypes" varStatus="vtype">
                                <option value="${validationTypes[vtype.index][0]}" <c:if test="${fieldDetails.validationId == validationTypes[vtype.index][0]}">selected</c:if>>${validationTypes[vtype.index][1]}</option>
                            </c:forEach>
                        </form:select>
                    </div>
                    <div class="form-group">
                        <label class="control-label" for=fieldValidation">Required Field *</label>
                        <form:select path="requiredField" id="requiredField" class="form-control half">
                            <option value="false" <c:if test="${fieldDetails.requiredField == false}">selected</c:if>>False</option>
                            <option value="true" <c:if test="${fieldDetails.requiredField == true}">selected</c:if>>True</option>
                        </form:select>
                    </div> 
                    <div class="form-group">
                        <label class="control-label" for=dataGridColumn">Show field in: *</label>
                        <div class="checkbox">
                            <label>
                              <form:checkbox path="searchField"  id="searchColumn"  value="true" /> Client Search
                            </label>
                        </div>
                        <div class="checkbox">
                            <label>
                              <form:checkbox path="summaryField" id="summaryColumn" value="true" /> Client Summary
                            </label>
                        </div>  
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
