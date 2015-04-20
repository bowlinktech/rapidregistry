<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<form>
    <input type="hidden" id="sectionName" value="${sectionName}" />
    <input type="hidden" id="sectionId" value="${sectionId}" />
    <input type="hidden" id="progamNameURL" value="${programName}" />
</form>

<div class="main clearfix" role="main">
    <div class="row-fluid">
        <div class="col-md-12">
            <c:choose>
                <c:when test="${not empty param.msg}" >
                    <div class="alert alert-success">
                        <strong>Success!</strong> 
                        <c:choose>
                            <c:when test="${param.msg == 'fieldssaved'}">The fields have been successfully saved!</c:when>
                            <c:when test="${param.msg == 'fieldsaved'}">The field has been successfully saved!</c:when>
                            <c:when test="${param.msg == 'created'}">The crosswalk has been successfully added!</c:when>
                            <c:when test="${param.msg == 'fieldValuesSaved'}">The field values have been successfully saved!</c:when>
                        </c:choose>
                    </div>
                </c:when>
            </c:choose>
            <section class="panel panel-default">
                <div class="panel-body">
                    <dt>
                    <dd><strong>Program Summary:</strong></dd>
                        <dd><strong>Program Name:</strong> ${programDetails.programName}</dd>
                        <dd><strong>Section Name:</strong> ${sectionDetails.sectionName}</dd>
                    </dt>
                </div>
            </section>  
        </div>
    </div>       
    <div class="row-fluid">
        <div class="col-md-6">
            <section class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">New Field</h3>
                </div>
                <div class="panel-body">
                    <div class="form-container">
                        <div id="fieldDiv" class="form-group ${status.error ? 'has-error' : '' }">
                            <label class="control-label" for="fieldNumber">Field</label>
                            <select id="field" class="form-control half">
                                <option value="">- Select -</option>
                                <c:forEach items="${availableFields}" var="field" varStatus="fStatus">
                                    <option value="${availableFields[fStatus.index].id}" rel="${availableFields[fStatus.index].elementName}">${availableFields[fStatus.index].elementName} (${availableFields[fStatus.index].saveToTableName})</option>
                                </c:forEach>
                            </select>
                            <span id="fieldMsg" class="control-label"></span>
                        </div>
                            <div id="fieldDisplayDiv" class="form-group ${status.error ? 'has-error' : '' }">
                            <label class="control-label" for="fieldDisplayName">Field Display Name</label>
                            <input type="text" id="fieldDisplayName" name="fieldDisplayName" class="form-control half" />
                            <span id="ffieldDisplayMsg" class="control-label"></span>
                        </div>
                        <div id="crosswalkDiv" class="form-group">
                            <label class="control-label" for="Crosswalk">Field  Crosswalk</label>
                            <select id="crosswalk" class="form-control half">
                                <option value="">- Select -</option>
                                <c:forEach items="${crosswalks}" var="cwalk" varStatus="cStatus">
                                    <option value="${crosswalks[cStatus.index].id}">${crosswalks[cStatus.index].name} <c:choose><c:when test="${crosswalks[cStatus.index].programId == 0}"> (generic)</c:when><c:otherwise> (Program Specific)</c:otherwise></c:choose></option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label class="control-label" for="fieldValidation">Field Validation</label>
                            <select id="fieldValidation" class="form-control half">
                                <c:forEach items="${validationTypes}"  var="fieldvalidationtypes" varStatus="vtype">
                                    <option value="${validationTypes[vtype.index][0]}">${validationTypes[vtype.index][1]}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label class="control-label" for="fieldValidation">Required Field *</label>
                            <select id="requiredField" class="form-control half">
                                <option value="false">False</option>
                                <option value="true">True</option>
                            </select>
                        </div> 
                        <div class="form-group">
                            <label class="control-label" for=hideField">Hidden Field *</label>
                            <select id="hideField" class="form-control half">
                                <option value="false">No</option>
                                <option value="true">Yes</option>
                            </select>
                        </div>     
                        <div class="form-group">
                            <label class="control-label" for=dataGridColumn">Show field in: *</label>
                            <%--<div class="checkbox">
                                <label>
                                  <input type="checkbox" id="dataGridColumn" name="search" value="true" />  Data Grid
                                </label>
                            </div>--%>
                            <div class="checkbox">
                                <label>
                                  <input type="checkbox" id="searchColumn" name="search" value="true" />  Client Search
                                </label>
                            </div>
                            <div class="checkbox">
                                <label>
                                  <input type="checkbox" id="summaryColumn" name="summary" value="true" />  Client Summary
                                </label>
                            </div>  
                        </div>  
                        <div class="form-group">
                            <input type="button" id="submitFieldButton"  class="btn btn-primary" value="Add Field"/>
                        </div>
                    </div>
                </div>
            </section>
        </div>

        <%-- Existing Crosswalks --%>
        <div class="col-md-6">
            <section class="panel panel-default">
                <div class="panel-heading">
                    <div class="pull-right">
                        <a href="#crosswalkModal" data-toggle="modal" class="btn btn-primary btn-xs btn-action" id="createNewCrosswalk" title="Add New Crosswalk">Add New Crosswalk</a>
                    </div>
                    <h3 class="panel-title">Available Crosswalks</h3>
                </div>
                <div class="panel-body">
                    <div class="form-container scrollable">
                        <div id="crosswalksTable"></div>
                    </div>
                </div>
            </section>
        </div>
    </div>
</div>

<div class="main clearfix" role="main">	
    <%-- Existing Translations --%>
    <div class="col-md-12">
        <section class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">Selected Fields</h3>
            </div>
            <div class="panel-body">
                <div id="fieldMsgDiv"  rel="${id}" class="alert alert-danger" style="display:none;">
                    <strong>You must click SAVE above to submit the selected fields listed below!</strong>
                </div>
                <div class="form-container scrollable" id="existingFields"></div>
            </div>
        </section>
    </div>
</div>

<%-- Crosswalks Address modal --%>
<div class="modal fade" id="crosswalkModal" role="dialog" tabindex="-1" aria-labeledby="Message Crosswalks" aria-hidden="true" aria-describedby="Message Crosswalks"></div>
<div class="modal fade" id="fieldModal" role="dialog" tabindex="-1" aria-labeledby="Edit Field" aria-hidden="true" aria-describedby="Edit Field"></div>
<div class="modal fade" id="selectValuesModal" role="dialog" tabindex="-1" aria-labeledby="Select Field Values" aria-hidden="true" aria-describedby="Select Field Values"></div>