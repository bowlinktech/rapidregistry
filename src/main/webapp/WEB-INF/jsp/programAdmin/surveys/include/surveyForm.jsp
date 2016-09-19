<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:choose>
    <c:when test="${not empty survey}">
        <form:form id="surveyForm" commandName="survey" method="post" role="form">
            <input type="hidden" id="action" name="action" value="save" />
            <form:hidden path="id" />
            <form:hidden path="programId" />            	
            <form:hidden path="dateModified" />
            <form:hidden path="dateCreated" /> 
            
            <section class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">General Survey Information</h3>
                </div>
                <div class="panel-body">
                    <div class="form-container">
                        <div class="form-group">
                            <spring:bind path="title">
                                <div id="titleDiv" class="form-group ${status.error ? 'has-error' : '' } ${not empty existingTitle ? 'has-error' : ''}">
                                    <label class="control-label" for="title">Survey Title*</label>
                                    <form:input path="title" id="title" class="form-control" type="text"  maxLength="255" />
                                    <form:errors path="title" cssClass="control-label" element="label" />
                                    <c:if test="${not empty existingTitle}"><label id="title.errors" class="control-label">${existingTitle}</label></c:if>
                                    </div> 
                            </spring:bind> 
                        </div>
                        <div class="row">                           
                            <div class="form-group col-md-4">
                                <label for="status">Status *</label>
                                <div>
                                    <label class="radio-inline">
                                        <form:radiobutton id="status" path="status" value="true" /> Active
                                    </label>
                                    <label class="radio-inline">
                                        <form:radiobutton id="status" path="status" value="false" /> Inactive
                                    </label>
                                </div>
                            </div>  
                            <div class="form-group  col-md-4">
                                <label for="status">Can duplicate surveys be added to an engagement? *</label>
                                <div>
                                    <label class="radio-inline">
                                        <form:radiobutton id="duplicatesAllowed" path="duplicatesAllowed" value="true" /> Yes
                                    </label>
                                    <label class="radio-inline">
                                        <form:radiobutton id="duplicatesAllowed" path="duplicatesAllowed" value="false" /> No
                                    </label>
                                </div>
                            </div>
                        </div> 

                        <div class="row">                           
                            <div class="form-group col-md-4">
                                <label for="status">Does this survey require an engagement? *</label>
                                <div>
                                    <label class="radio-inline">
                                        <form:radiobutton id="requireEngagement" path="requireEngagement" value="true" /> Yes
                                    </label>
                                    <label class="radio-inline">
                                        <form:radiobutton id="requireEngagement" path="requireEngagement" value="false" /> No
                                    </label>
                                </div>
                            </div>
                            <div class="form-group col-md-4">
                                <label for="status">Can this survey be associated to an engagement? *</label>
                                <div>
                                    <label class="radio-inline">
                                        <form:radiobutton id="allowEngagement" path="allowEngagement" value="true" /> Yes
                                    </label>
                                    <label class="radio-inline">
                                        <form:radiobutton id="allowEngagement" path="allowEngagement" value="false" /> No
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group  col-md-4">
                                <label for="status">Reminder Status*</label>
                                <div>
                                    <label class="radio-inline">
                                        <form:radiobutton id="reminderStatus" path="reminderStatus" value="true" /> Active
                                    </label>
                                    <label class="radio-inline">
                                        <form:radiobutton id="reminderStatus" path="reminderStatus" value="false" /> Inactive
                                    </label>
                                </div>
                            </div> 
                            <div class="form-group col-md-4">
                                <label for="status">Can this survey be associated to Programs/Content Criteria? *</label>
                                <div>
                                    <label class="radio-inline">
                                        <form:radiobutton id="associateToProgram" path="associateToProgram" value="true" /> Yes
                                    </label>
                                    <label class="radio-inline">
                                        <form:radiobutton id="associateToProgram" path="associateToProgram" value="false" /> No
                                    </label>
                                </div>
                            </div>
                        </div>
                        <spring:bind path="reminderText">
                            <div id="reminderTextDiv" class="form-group ${status.error ? 'has-error' : '' }">
                                <label class="control-label" for="reminderText">Reminder Message</label>
                                <p><mark>This reminder will appear when the survey is completed and the final submit button is pressed.</mark></p>
                                <form:textarea path="reminderText" id="reminderText" class="form-control" maxLength="500" />
                                <form:errors path="reminderText" cssClass="control-label" element="label" />
                            </div> 
                        </spring:bind> 
                    </div>
                </div>
            </section>

            <section class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Survey Buttons</h3>
                </div>
                <div class="panel-body">
                    <div class="form-container">
                        <div class="row">                           
                            <div class="form-group col-md-4">
                                <spring:bind path="prevButtonText">
                                    <div id="prevButtonTexttDiv" class="form-group ${status.error ? 'has-error' : '' }">
                                        <label class="control-label" for="prevButtonText">Previous Button Text</label>
                                        <form:input path="prevButtonText" id="title" class="form-control" type="text"  maxLength="30" />
                                        <form:errors path="prevButtonText" cssClass="control-label" element="label" />                   		
                                    </div> 
                                </spring:bind> 
                                <spring:bind path="prevBtnActivityCodeId">
                                    <div class="form-group">
                                        <label class="control-label" for="prevBtnActivityCodeId">Associated Activity Code</label>
                                        <form:select path="prevBtnActivityCodeId" id="prevBtnActivityCodeId" class="form-control half">
                                            <option value="0" label=" - Select - " >- Select -</option>
                                            <c:forEach items="${activityCodes}"  varStatus="code">
                                                <option value="${activityCodes[code.index].id}" <c:if test="${survey.prevBtnActivityCodeId == activityCodes[code.index].id}">selected</c:if>>${activityCodes[code.index].codeDesc}</option>
                                            </c:forEach>
                                        </form:select>
                                    </div>
                                </spring:bind> 
                            </div>
                            <div class="form-group col-md-4">
                                <spring:bind path="nextButtonText">
                                    <div id="prevButtonTexttDiv" class="form-group ${status.error ? 'has-error' : '' }">
                                        <label class="control-label" for="nextButtonText">Next Button Text</label>
                                        <form:input path="nextButtonText" id="title" class="form-control" type="text"  maxLength="30" />
                                        <form:errors path="nextButtonText" cssClass="control-label" element="label" />                   		
                                    </div> 
                                </spring:bind> 
                                <spring:bind path="nextBtnActivityCodeId">
                                    <div class="form-group ">
                                        <label class="control-label" for="nextBtnActivityCodeId">Associated Activity Code</label>
                                        <form:select path="nextBtnActivityCodeId" id="nextBtnActivityCodeId" class="form-control half">
                                            <option value="0" label=" - Select - " >- Select -</option>
                                            <c:forEach items="${activityCodes}"  varStatus="code">
                                                <option value="${activityCodes[code.index].id}" <c:if test="${survey.nextBtnActivityCodeId == activityCodes[code.index].id}">selected</c:if>>${activityCodes[code.index].codeDesc}</option>
                                            </c:forEach>
                                        </form:select>
                                    </div>
                                </spring:bind> 
                            </div>
                            <div class="form-group col-md-4">
                                <spring:bind path="doneButtonText">
                                    <div id="doneButtonTextDiv" class="form-group ${status.error ? 'has-error' : '' }">
                                        <label class="control-label" for="doneButtonText">Done Button Text</label>
                                        <form:input path="doneButtonText" id="title" class="form-control" type="text"  maxLength="30" />
                                        <form:errors path="doneButtonText" cssClass="control-label" element="label" />
                                    </div> 
                                </spring:bind> 
                                <spring:bind path="doneBtnActivityCodeId">
                                    <div class="form-group">
                                        <label class="control-label" for="doneBtnActivityCodeId">Associated Activity Code</label>
                                        <form:select path="doneBtnActivityCodeId" id="doneBtnActivityCodeId" class="form-control half">
                                            <option value="0" label=" - Select - " >- Select -</option>
                                            <c:forEach items="${activityCodes}"  varStatus="code">
                                                <option value="${activityCodes[code.index].id}" <c:if test="${survey.doneBtnActivityCodeId == activityCodes[code.index].id}">selected</c:if>>${activityCodes[code.index].codeDesc}</option>
                                            </c:forEach>
                                        </form:select>
                                    </div>
                                </spring:bind> 
                            </div>
                        </div> 
                    </div>
                </div>
            </section>
            <c:if test="${empty create}">
                 <div class="form-group">
                    <input type="button" id="submitSurveyButton" role="button" class="btn btn-primary" value="Save"/>
                </div>
            </c:if>  
        </form:form>
    </c:when>
    <c:otherwise>
        This survey does not exist or you do not have permission to access it.
    </c:otherwise>
</c:choose>