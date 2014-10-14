
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
            <form:form id="entryMethod" commandName="programPatientEntryMethods"  method="post" role="form">
                <input type="hidden" id="action" name="action" value="save" />
                <input type="hidden" id="progamNameURL" value="${programName}" />
                <form:hidden path="id" id="id" />
                <form:hidden path="programId" />
                 <div class="form-group">
                    <spring:bind path="surveyId">
                        <div id="surveyDiv" class="form-group ${status.error ? 'has-error' : '' }">
                            <label class="control-label" for="surveyId">Survey </label>
                            <form:select path="surveyId" id="surveyId" class="form-control half">
                                <option value="0" label=" - Select - " >- Select Survey -</option>
                                <c:forEach items="${surveys}" varStatus="sname">
                                    <option value="${surveys.id}" <c:if test="${programPatientEntryMethods.surveyId == surveys.id}">selected</c:if>>${surveys.title}</option>
                                </c:forEach>
                            </form:select>  
                        </div>
                    </spring:bind>
                    <spring:bind path="btnValue">
                        <div class="form-group ${status.error ? 'has-error' : '' }">
                            <label class="control-label" for="btnValue">Button Text *</label>
                            <form:input path="btnValue" id="btnValue" class="form-control" type="text" maxLength="15" />
                            <form:errors path="btnValue" cssClass="control-label" element="label" />
                        </div>
                    </spring:bind> 
                    <spring:bind path="dspPos">
                        <div id="displayPosDiv" class="form-group ${status.error ? 'has-error' : '' }">
                            <label class="control-label" for="dspPos">Display Position * </label>
                            <form:select path="dspPos" id="dspPos" class="form-control half">
                                <option value="0" label=" - Select - " >- Select Display Position -</option>
                                <c:forEach varStatus="lIndex" begin="1" end="${maxDspPos}">
                                    <option value="${lIndex.index}" <c:if test="${programPatientEntryMethods.dspPos == lIndex.index}">selected</c:if>>${lIndex.index}</option>
                                </c:forEach>
                            </form:select>
                            <form:errors path="dspPos" cssClass="control-label" element="label" />
                        </div>
                    </spring:bind> 
                     
                </div>
                <div class="form-group">
                    <input type="button" id="submitEntryButton" role="button" class="btn btn-primary" value="Save"/>
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