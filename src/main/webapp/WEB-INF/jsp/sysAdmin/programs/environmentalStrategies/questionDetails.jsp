<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h3 class="panel-title"><c:choose><c:when test="${question.id > 0}">Edit</c:when><c:when test="${btnValue == 'Create'}">Add</c:when></c:choose> Question ${success}</h3>
                </div>
                <div class="modal-body">

                <form:form id="questionform" commandName="question" modelAttribute="question" action="/sysAdmin/programs/${programName}/environmentalStrategies/environmentalStrategyQuestion" enctype="multipart/form-data" method="post" role="form">
                <form:hidden path="id" id="id" />
                <form:hidden path="programId" />
                <form:hidden path="environmentalStrategy" />
                <form:hidden path="dspPos" />
                <div class="form-container">
                    <spring:bind path="header">
                        <div id="headerDiv" class="form-group ${status.error ? 'has-error' : '' }">
                            <label class="control-label" for="header">Header</label>
                            <form:input path="header" id="header" class="form-control"  maxlength="100" />
                            <span id="headerMsg" class="control-label"></span>
                        </div>
                    </spring:bind>
                    <spring:bind path="question">
                        <div id="questionDiv" class="form-group ${status.error ? 'has-error' : '' }">
                            <label class="control-label" for="question">Question *</label>
                            <form:textarea path="question" id="question" class="form-control" />
                            <span id="questionMsg" class="control-label"></span>
                        </div>
                    </spring:bind>
                    <spring:bind path="required">
                        <div id="requiredDiv" class="form-group ${status.error ? 'has-error' : '' }">
                            <label class="control-label" for="required">Required Field *</label>
                            <form:select path="required" id="required" class="form-control half">
                                <option value="false" <c:if test="${question.required == false}">selected</c:if>>False</option>
                                <option value="true" <c:if test="${question.required == true}">selected</c:if>>True</option>
                            </form:select>
                            <span id="requiredMsg" class="control-label"></span>
                        </div>
                    </spring:bind>
                    <spring:bind path="validationId">
                        <div id="validationDiv" class="form-group ${status.error ? 'has-error' : '' }">
                            <label class="control-label" for="validation">Field Validation</label>
                            <form:select path="validationId" id="validation" class="form-control half">
                                <option value="1">- Select -</option>
                                <c:forEach items="${validations}" varStatus="vStatus">
                                    <option value="${validations[vStatus.index][0]}" <c:if test="${question.validationId == validations[vStatus.index][0]}">selected</c:if>>${validations[vStatus.index][1]} </option>
                                </c:forEach>
                            </form:select>
                            <span id="validationMsg" class="control-label"></span>
                        </div>
                    </spring:bind>
                    <spring:bind path="defaultValue">
                        <div id="defaultValueDiv" class="form-group ${status.error ? 'has-error' : '' }">
                            <label class="control-label" for="defaultValue">Default Value (enter 0 if there is no default value) *</label>
                            <form:input path="defaultValue" id="defaultValue" class="form-control half"  maxlength="4" />
                            <span id="defaultValueMsg" class="control-label"></span>
                        </div>
                    </spring:bind>
                    <spring:bind path="qTag">
                        <div id="qTagDiv" class="form-group ${status.error ? 'has-error' : '' }">
                            <label class="control-label" for="qTag">Question Tag *</label>
                            <form:input path="qTag" id="qTag" class="form-control half" />
                            <span id="qTagMsg" class="control-label"></span>
                        </div>
                    </spring:bind>
                    <spring:bind path="copyToReach">
                        <div id="copyToReachDiv" class="form-group ${status.error ? 'has-error' : '' }">
                            <label class="control-label" for="copyToReach">Copy this value to Total Reached *</label>
                            <form:select path="copyToReach" id="copyToReach" class="form-control half">
                                <option value="false" <c:if test="${question.copyToReach == false}">selected</c:if>>False</option>
                                <option value="true" <c:if test="${question.copyToReach == true}">selected</c:if>>True</option>
                            </form:select>
                            <span id="copyToReachMsg" class="control-label"></span>
                        </div>
                    </spring:bind>
                    <spring:bind path="sumForReach">
                        <div id="sumForReachDiv" class="form-group ${status.error ? 'has-error' : '' }">
                            <label class="control-label" for="sumForReach">Add this value to Total Reached *</label>
                            <form:select path="sumForReach" id="sumForReach" class="form-control half">
                                <option value="false" <c:if test="${question.sumForReach == false}">selected</c:if>>False</option>
                                <option value="true" <c:if test="${question.sumForReach == true}">selected</c:if>>True</option>
                            </form:select>
                            <span id="sumForReachMsg" class="control-label"></span>
                        </div>
                    </spring:bind>
                    <spring:bind path="copyToCount">
                        <div id="copyToCountDiv" class="form-group ${status.error ? 'has-error' : '' }">
                            <label class="control-label" for="copyToCount">Copy this value to Total Count *</label>
                            <form:select path="copyToCount" id="copyToCount" class="form-control half">
                                <option value="false" <c:if test="${question.copyToCount == false}">selected</c:if>>False</option>
                                <option value="true" <c:if test="${question.copyToCount == true}">selected</c:if>>True</option>
                            </form:select>
                            <span id="copyToCountMsg" class="control-label"></span>
                        </div>
                    </spring:bind>
                    <spring:bind path="sumForCount">
                        <div id="sumForCountDiv" class="form-group ${status.error ? 'has-error' : '' }">
                            <label class="control-label" for="sumForCount">Add this value to Total Count *</label>
                            <form:select path="sumForCount" id="sumForCount" class="form-control half">
                                <option value="false" <c:if test="${question.sumForCount == false}">selected</c:if>>False</option>
                                <option value="true" <c:if test="${question.sumForCount == true}">selected</c:if>>True</option>
                            </form:select>
                            <span id="sumForCountMsg" class="control-label"></span>
                        </div>
                    </spring:bind>
                    <spring:bind path="maxFieldValue">
                        <div id="maxValueDiv" class="form-group ${status.error ? 'has-error' : '' }">
                            <label class="control-label" for="maxFieldValue">Max Value for field (enter 0 if there is no max) *</label>
                            <form:input path="maxFieldValue" id="maxFieldValue" class="form-control half" maxlength="4" />
                            <span id="maxValueMsg" class="control-label"></span>
                        </div>
                    </spring:bind>
                    <spring:bind path="maxCharactersAllowed">
                        <div id="maxCharactersAllowedDiv" class="form-group ${status.error ? 'has-error' : '' }">
                            <label class="control-label" for="maxCharactersAllowed">Max Characters Allowed *</label>
                            <form:input path="maxCharactersAllowed" id="maxCharactersAllowed" class="form-control half" maxlength="4" />
                            <span id="maxCharactersAllowedMsg" class="control-label"></span>
                        </div>
                    </spring:bind>
                    <div class="form-group">
                        <input type="button" id="submitQuestionButton" rel="${actionValue}" class="btn btn-primary" value="${btnValue}"/>
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