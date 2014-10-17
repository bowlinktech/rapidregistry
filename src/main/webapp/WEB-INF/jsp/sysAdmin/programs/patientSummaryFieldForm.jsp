
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
            <c:choose>
                <c:when test="${availablePatientFields.size() > 0}">
                    <form:form id="summaryField" commandName="programPatientSummaryFields"  method="post" role="form">
                        <input type="hidden" id="action" name="action" value="save" />
                        <input type="hidden" id="progamNameURL" value="${programName}" />
                        <form:hidden path="id" id="id" />
                        <form:hidden path="programId" />
                        <form:hidden path="dateCreated" />
                         <div class="form-group">
                            <spring:bind path="sectionFieldId">
                                <div id="sectionFieldDiv" class="form-group ${status.error ? 'has-error' : '' }">
                                    <label class="control-label" for="sectionFieldId">Field * </label>
                                    <form:select path="sectionFieldId" id="sectionFieldId" class="form-control half">
                                        <option value="0" label=" - Select - " >- Select Field -</option>
                                        <c:forEach var="field" items="${availablePatientFields}">
                                            <option value="${field.id}" <c:if test="${programPatientSearchFields.sectionFieldId == field.id}">selected</c:if>>${field.fieldDisplayname}</option>
                                        </c:forEach>
                                    </form:select>  
                                    <span id="sectionFieldMsg" class="control-label" ></span>         
                                </div>
                            </spring:bind>
                            <spring:bind path="dspPos">
                                <div id="dspPosDiv" class="form-group ${status.error ? 'has-error' : '' }">
                                    <label class="control-label" for="dspPos">Display Position * </label>
                                    <form:select path="dspPos" id="dspPos" class="form-control half">
                                        <option value="0" label=" - Select - " >- Select Display Position -</option>
                                        <c:forEach varStatus="lIndex" begin="1" end="${maxDspPos}">
                                            <option value="${lIndex.index}" <c:if test="${programPatientEntryMethods.dspPos == lIndex.index}">selected</c:if>>${lIndex.index}</option>
                                        </c:forEach>
                                    </form:select>
                                    <span id="dspPosMsg" class="control-label" ></span> 
                                </div>
                            </spring:bind> 

                        </div>
                        <div class="form-group">
                            <input type="button" id="submitSummaryButton" role="button" class="btn btn-primary" value="Save"/>
                        </div>
                    </form:form>
                </c:when>
                <c:otherwise>
                    <p>There are no patient fields associated to this program.</p>
                </c:otherwise>
            </c:choose>
            
        </div>
    </div>
</div>

<script type="text/javascript">

    $(document).ready(function() {
        $("input:text,form").attr("autocomplete", "off");
    });

</script>