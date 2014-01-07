<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Page variable to hold what dsp pos value to show -->
<c:set var="endDspPostLoop" value="1" />

<!-- Need to calculate how many fields for current section -->
<c:set var="totalFieldsBucket1, totalFieldsBucket2, totalFieldsBucket3, totalFieldsBucket4, totalFieldsBucket5, totalFieldsBucket6" value="0" />
<c:forEach items="${transportDetails.fields}" var="fieldDetails" varStatus="field">
    <c:if test="${fieldDetails.bucketNo == 1}">
        <c:set var="totalFieldsBucket1" value="${totalFieldsBucket1+1}" />
    </c:if>
    <c:if test="${fieldDetails.bucketNo == 2}">
        <c:set var="totalFieldsBucket2" value="${totalFieldsBucket2+1}" />
    </c:if>
    <c:if test="${fieldDetails.bucketNo == 3}">
        <c:set var="totalFieldsBucket3" value="${totalFieldsBucket3+1}" />
    </c:if>
    <c:if test="${fieldDetails.bucketNo == 4}">
        <c:set var="totalFieldsBucket4" value="${totalFieldsBucket4+1}" />
    </c:if>
    <c:if test="${fieldDetails.bucketNo == 5}">
        <c:set var="totalFieldsBucket5" value="${totalFieldsBucket5+1}" />
    </c:if>
    <c:if test="${fieldDetails.bucketNo == 6}">
        <c:set var="totalFieldsBucket6" value="${totalFieldsBucket6+1}" />
    </c:if>
</c:forEach>

<div class="main clearfix" role="main">
    <div class="row-fluid">
        <div class="col-md-12">
            <c:choose>
                <c:when test="${not empty savedStatus}" >
                    <div class="alert alert-success">
                        <strong>Success!</strong> 
                        <c:choose>
                            <c:when test="${savedStatus == 'updated'}">The ERG Customization have been successfully updated!</c:when>
                        </c:choose>
                    </div>
                </c:when>
            </c:choose>
            <div id="saveMsgDiv" class="alert alert-danger" style="display:none;">
                <strong>You must click SAVE above to submit the ERG changes!</strong>
            </div>
            <section class="panel panel-default">
                <div class="panel-body">
                    <dt>
                        <dt>Configuration Summary:</dt>
                        <dd><strong>Organization:</strong> ${configurationDetails.orgName}</dd>
                        <dd><strong>Message Type:</strong> ${configurationDetails.messageTypeName}</dd>
                        <dd><strong>Transport Method:</strong> ${configurationDetails.transportMethod}</dd>
                    </dt>
                </div>
            </section>
        </div>
    </div>
    <div class="row-fluid">
        <div class="col-md-12">		
            <section class="panel panel-default">
                <div class="panel-heading">
                    <div class="pull-right">
                        <a class="btn btn-primary btn-xs  btn-action" id="selectAllFields" data-toggle="tooltip" title="Toggle Fields to use"><span class="glyphicon glyphicon-ok"></span> Toggle Used Fields</a>
                    </div>
                    <h3 class="panel-title">ERG Form Fields</h3>
                </div>
                <div class="panel-body">
                    <div class="form-container scrollable">		
                        <form:form id="formFields" modelAttribute="transportDetails" method="post" role="form">
                            <input type="hidden" id="action" name="action" value="save" />
                            <input type="hidden" name="transportMethod" value="2" />
                            <c:forEach var="i" begin="1" end="6">	
                                <section class="panel panel-default">
                                    <div class="panel-heading">
                                        <dt>
                                        <dd>
                                            <strong><c:choose><c:when test="${i==1}"> (Sender Organization Information)</c:when><c:when test="${i==2}"> (Sender Provider Information)</c:when><c:when test="${i==3}"> (Recipient Organization Information)</c:when><c:when test="${i==4}"> (Recipient Provider Information)</c:when><c:when test="${i==5}"> (Patient Information)</c:when><c:when test="${i==6}"> (Details)</c:when></c:choose></strong>
                                                </dd>
                                                </dt>
                                            </div>
                                            <div class="panel-body">
                                                <div class="form-container scrollable">
                                                <table class="table table-striped table-hover bucketTable_${i}">
                                                <thead>
                                                    <tr>
                                                        <th scope="col" style="width:100px; min-width:100px" class="center-text">Use This Field</th>
                                                        <th scope="col" style="width:150px; min-width:150px">Display POS</th>
                                                        <th scope="col" style="width:200px; min-width:200px;">Field Name</th>
                                                        <th scope="col" style="width:300px; min-width:300px;">Field Label *</th>
                                                        <th scope="col" style="width:100px; min-width:100px;" class="center-text">Required</th>
                                                        <th scope="col" style="width:150px; min-width:150px;">Validation Type</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach items="${transportDetails.fields}" var="fieldDetails" varStatus="field">
                                                        <c:if test="${fieldDetails.bucketNo == i}">
                                                            <tr>
                                                                <td scope="row" class="center-text">
                                                                    <input type="hidden" name="fields[${field.index}].id" value="${fieldDetails.id}" />
                                                                    <input type="hidden" class="configId" name="fields[${field.index}].configId" value="${fieldDetails.configId}" />
                                                                    <input type="hidden" name="fields[${field.index}].transportDetailId" value="${fieldDetails.transportDetailId}" />
                                                                    <input type="hidden" class="fieldNo" name="fields[${field.index}].fieldNo" value="${fieldDetails.fieldNo}" />
                                                                    <input type="hidden" name="fields[${field.index}].bucketNo" value="${fieldDetails.bucketNo}" />
                                                                    <input type="hidden" name="fields[${field.index}].messageTypeFieldId" value="${fieldDetails.messageTypeFieldId}" />
                                                                    <input type="checkbox" class="useFields" name="fields[${field.index}].useField" value="true" <c:if test="${fieldDetails.useField == true}">checked="checked"</c:if> />
                                                                    <input type="hidden" name="fields[${field.index}].saveToTableName" value="${fieldDetails.saveToTableName}" />
                                                                    <input type="hidden" name="fields[${field.index}].saveToTableCol" value="${fieldDetails.saveToTableCol}" />
                                                                    <input type="hidden" name="fields[${field.index}].autoPopulateTableName" value="${fieldDetails.autoPopulateTableName}" />
                                                                    <input type="hidden" name="fields[${field.index}].autoPopulateTableCol" value="${fieldDetails.autoPopulateTableCol}" />
                                                                </td>
                                                                <td >
                                                                    <c:if test="${fieldDetails.bucketNo == 1}">
                                                                        <c:set var="endDspPostLoop" value="${totalFieldsBucket1}" />
                                                                    </c:if>
                                                                    <c:if test="${fieldDetails.bucketNo == 2}">
                                                                        <c:set var="endDspPostLoop" value="${totalFieldsBucket2}" />
                                                                    </c:if>
                                                                    <c:if test="${fieldDetails.bucketNo == 3}">
                                                                        <c:set var="endDspPostLoop" value="${totalFieldsBucket3}" />
                                                                    </c:if>
                                                                    <c:if test="${fieldDetails.bucketNo == 4}">
                                                                        <c:set var="endDspPostLoop" value="${totalFieldsBucket4}" />
                                                                    </c:if>
                                                                    <c:if test="${fieldDetails.bucketNo == 5}">
                                                                        <c:set var="endDspPostLoop" value="${totalFieldsBucket5}" />
                                                                    </c:if>
                                                                    <c:if test="${fieldDetails.bucketNo == 6}">
                                                                        <c:set var="endDspPostLoop" value="${totalFieldsBucket6}" />
                                                                    </c:if>
                                                                    <input type="hidden" id="endDspPostLoop_${fieldDetails.bucketNo}" value="${endDspPostLoop}" />
                                                                    <select rel="${fieldDetails.bucketNo}" rel2="${fieldDetails.bucketDspPos}" name="fields[${field.index}].bucketDspPos" class="form-control half dspPos dspPos_${fieldDetails.bucketNo} formField">
                                                                        <option value="" label=" - Select - " ></option>
                                                                        <c:forEach begin="1" end="${endDspPostLoop}" var="t">
                                                                            <option value="${t}" <c:if test="${fieldDetails.bucketDspPos == t}">selected</c:if>>${t}</option>
                                                                        </c:forEach>
                                                                    </select>
                                                                </td>
                                                                <td>
                                                                    <input type="hidden" name="fields[${field.index}].fieldDesc" value="${fieldDetails.fieldDesc}" />
                                                                    ${fieldDetails.fieldDesc}
                                                                </td>
                                                                <td>
                                                                    <div id="fieldLabel_${field.index}" class="form-group ${status.error ? 'has-error' : '' }">
                                                                        <input type="text" name="fields[${field.index}].fieldLabel"  value="${fieldDetails.fieldLabel}" rel="${field.index}" class="form-control fieldLabel formField" />
                                                                        <span id="fieldLabelMsg_${field.index}" class="control-label"></span>
                                                                    </div>
                                                                </td>
                                                                <td class="center-text">
                                                                    <input type="checkbox" name="fields[${field.index}].required" value="true" <c:if test="${fieldDetails.required == true}">checked="checked"</c:if> class="formField"  />
                                                                </td>
                                                                <td class="validationTypes">
                                                                    <select name="fields[${field.index}].validationType" class="form-control half formField">
                                                                        <c:forEach items="${validationTypes}"  var="fieldvalidationtypes" varStatus="vtype">
                                                                            <option value="${validationTypes[vtype.index][0]}" <c:if test="${fieldDetails.validationType == validationTypes[vtype.index][0]}">selected</c:if>>${validationTypes[vtype.index][1]}</option>
                                                                        </c:forEach>
                                                                    </select>
                                                                </td>
                                                            </tr>
                                                        </c:if>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </section>
                            </c:forEach>
                        </form:form>
                    </div>
                </div>	
            </section>
        </div>
    </div>
</div>