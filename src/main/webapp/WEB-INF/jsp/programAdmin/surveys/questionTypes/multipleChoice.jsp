<%-- 
    Document   : singleTextBox
    Created on : Feb 21, 2015, 9:38:39 AM
    Author     : chadmccue
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<div class="container pull-left" style="margin-left: 20px;">
    <!-- Nav tabs -->
    <ul class="nav nav-tabs nav-tabs-question" role="tablist">
      <li class="paneTabLi editPaneTab active" rel="edit">
          <a href="#edit" role="tab" data-toggle="tab" class="paneTab">
              <icon class="glyphicon glyphicon-edit"></icon> Edit
          </a>
      </li>
      <li class="paneTabLi optionsPaneTab" rel="options">
          <a href="#options" role="tab" data-toggle="tab" class="paneTab">
            <icon class="glyphicon glyphicon-cog"></icon> Options
          </a>
      </li>
      <c:if test="${surveyQuestion.id > 0}">
         <li class="paneTabLi logicPaneTab" rel="logic">
            <a href="#logic" role="tab" data-toggle="tab" class="paneTab">
                <icon class="glyphicon glyphicon-random"></icon> Logic
            </a>
        </li>
         <li class="paneTabLi movePaneTab" rel="move">
            <a href="#move" role="tab" data-toggle="tab" class="paneTab">
                <icon class="glyphicon glyphicon-move"></icon> Move
            </a>
        </li>
        <li class="paneTabLi copyPaneTab" rel="copy">
            <a href="#copy" role="tab" data-toggle="tab" class="paneTab">
                <icon class="glyphicon glyphicon-tag"></icon> Copy
            </a>
        </li>
      </c:if>
    </ul>
    
    <form:form id="surveyquestion" commandName="surveyQuestion" method="post" role="form">
        <form:hidden path="id" />
        <form:hidden path="surveyId" />
        <form:hidden path="surveyPageId" />
        <form:hidden path="answerTypeId" />
        <form:hidden path="questionNum" />
        <form:hidden path="dateCreated" />
        <!-- Tab panes -->
        <div class="tab-content">
            <div class="tab-pane tab-pane-question fade active in editPane" id="edit">
                <div class="form-group">
                    <label class="control-label" for="pageTitle">Q${qnum}: Multiple Choice</label>
                    <form:input path="question" placeholder="Enter your Question" id="question" class="form-control" type="text"  maxLength="255" />
                </div>
                <c:if test="${not empty availableTables}">
                    <div class="well well-xsm" style="background-color:#ffffff; margin-bottom: 2px">
                        <input type="checkbox" id="populate" <c:if test="${not empty surveyQuestion.populateFromTable}">checked="checked"</c:if> />&nbsp;<label class="control-label" for="populateFromTable">Populate choices from an existing table</label>
                    </div>
                    <div class="well well-xsm" id="autoPopulateDiv" style="background-color:#ffffff; margin-bottom: 2px; height: 50px; ${not empty surveyQuestion.populateFromTable ? 'display:block;' : 'display:none;'}">
                        <c:choose>
                            <c:when test="${surveyQuestion.id > 0}">
                                <form:select path="populateFromTable" id="populateFromTable" class="form-control half sm-input">
                                    <option value="">- Select Table -</option>
                                    <c:forEach items="${availableTables}" var="table" varStatus="ftype">
                                        <option value="${table.tableName}" <c:if test="${surveyQuestion.populateFromTable == table.tableName}">selected</c:if>>${table.tableName}</option>
                                    </c:forEach>
                                </form:select>
                            </c:when>
                            <c:otherwise>
                                <span>The question must be saved before this option is available.</span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:if>
                <div class="well well-xsm" style="background-color:#ffffff; margin-bottom: 2px">
                   <input type="checkbox" id="manual" <c:if test="${empty surveyQuestion.populateFromTable && not empty surveyQuestion.questionChoices}">checked="checked"</c:if> />&nbsp;<label class="control-label" for="populateFromTable">Manually enter choices</label>
                </div>
                    <c:if test="${surveyQuestion.questionChoices.size() > 0}">
                    <div class="panel" id="questionChoiceDiv">
                        <div class="panel-body">
                            <table class="table choiceTable" border="0">
                                <thead>
                                    <tr>
                                        <th>Answers</th>
                                        <th class="center-text"></th>
                                        <th class="center-text">Default</th>
                                        <th>Associated Activity Code</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${surveyQuestion.questionChoices}" var="choiceDetails" varStatus="choice">
                                        <input type="hidden" id="id_${choice.index}" name="questionChoices[${choice.index}].id" value="${choiceDetails.id}" />
                                        <input type="hidden" id="questionId_${choice.index}" name="questionChoices[${choice.index}].questionId" value="${choiceDetails.questionId}" />
                                        <input type="hidden" id="choiceValue_${choice.index}" name="questionChoices[${choice.index}].choiceValue" value="${choiceDetails.choiceValue}" />
                                        <input type="hidden" id="skipToPageId_${choice.index}" name="questionChoices[${choice.index}].skipToPageId" value="${choiceDetails.skipToPageId}" />
                                        <input type="hidden" id="skipToQuestionId_${choice.index}" name="questionChoices[${choice.index}].skipToQuestionId" value="${choiceDetails.skipToQuestionId}" />
                                        <tr rel="${choice.index}">
                                            <td>
                                                <input type="text" name="questionChoices[${choice.index}].choiceText"  value="${choiceDetails.choiceText}" rel="${choice.index}" class="form-control fieldLabel formField" />
                                            </td>
                                            <td style="vertical-align:top;">
                                                <c:if test="${empty surveyQuestion.populateFromTable}"><i class="glyphicon glyphicon-plus-sign addChoice" style="font-size:1.7em; cursor: pointer"></i>&nbsp;</c:if>
                                                <i class="glyphicon glyphicon-minus-sign removeChoice" style="font-size:1.7em; cursor: pointer"></i>
                                            </td>
                                            <td class="center-text">
                                                <input type="radio" class="defAnswer" name="questionChoices[${choice.index}].defAnswer" value="1" <c:if test="${choiceDetails.defAnswer == true}">checked</c:if> />
                                            </td>
                                            <td>
                                               <select name="questionChoices[${choice.index}].activityCodeId" class="form-control">
                                                    <option value="0" label=" - Select - " >- Select -</option>
                                                    <c:forEach items="${activityCodes}" varStatus="code">
                                                        <option value="${activityCodes[code.index].id}" <c:if test="${choiceDetails.activityCodeId == activityCodes[code.index].id}">selected</c:if>>${activityCodes[code.index].codeDesc}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </c:if>
            </div>
            <%-- Question Options Div --%>
            <div class="tab-pane tab-pane-question fade optionsPane" id="options">
                <div class="well well-xsm" style="background-color:#ffffff; margin-bottom: 2px">
                    <form:checkbox path="required" id="required" />&nbsp;<label class="control-label" for="pageTitle">Make this question required</label>
                </div>
                <div class="panel" id="requiredResponseDiv" style="${surveyQuestion.required == true ? 'display:block;' : 'display:none;'}">
                    <div class="panel-body">
                        <div class="form-group">
                            <label class="control-label" for="requiredResponse">Display this message when this question is not answered.</label>
                            <form:input path="requiredResponse" placeholder="Required Error Message" id="requiredResponse" class="form-control" type="text"  maxLength="255" />
                        </div>
                    </div>
                </div>
                <div class="well well-xsm" style="background-color:#ffffff; margin-bottom: 2px">
                    <form:checkbox path="alphabeticallySort" id="alphabeticallySort" />&nbsp;<label class="control-label" for="alphabeticallySort">Alphabetically Sort Choices</label>
                </div>
                <div class="well well-xsm" style="background-color:#ffffff; margin-bottom: 2px">
                    <input type="checkbox" id="choiceLayout" <c:if test="${not empty surveyQuestion.choiceLayout}">checked="checked"</c:if> />&nbsp;<label class="control-label" for="choiceLayout">Change the layout for how choices are displayed</label>
                </div>
                <div class="panel" id="choiceLayoutDiv" style="${not empty surveyQuestion.choiceLayout ? 'display:block;' : 'display:none;'}">
                    <div class="panel-body">
                        <div class="form-group">
                            <label class="radio-inline control-label">
                                <form:radiobutton path="choiceLayout" class="radio" value="1 Column" /> <strong>1 Column</strong>
                            </label>
                        </div>
                        <div class="form-group">
                            <label class="radio-inline control-label">
                                <form:radiobutton path="choiceLayout" class="radio" value="2 Columns" /> <strong>2 Columns</strong>
                            </label>
                        </div>
                        <div class="form-group">
                            <label class="radio-inline control-label">
                                <form:radiobutton path="choiceLayout" class="radio" value="3 Columns" /> <strong>3 Columns</strong>
                            </label>
                        </div>
                        <div class="form-group">
                            <label class="radio-inline control-label">
                                <form:radiobutton path="choiceLayout" class="radio" value="Horizontal" /> <strong>Horizontal</strong>
                            </label>
                        </div>
                    </div>
                </div>
                <div class="well well-xsm" style="background-color:#ffffff; margin-bottom: 2px">
                    <input type="checkbox" id="saveTo" <c:if test="${surveyQuestion.saveToFieldId > 0}">checked="checked"</c:if> />&nbsp;<label class="control-label" for="saveTo">Save the answer to an additional field</label>
                </div>
                <div class="panel" id="saveToDiv" style="${surveyQuestion.saveToFieldId > 0 ? 'display:block;' : 'display:none;'}">
                    <div class="panel-body">
                        <div class="form-group form-inline">
                            <form:select path="saveToFieldId" id="saveToField" class="form-control half sm-input">
                                <option value="0">- Select Field -</option>
                                <c:forEach items="${fields}" var="field" varStatus="ftype">
                                    <option value="${fields[ftype.index][0]}" <c:if test="${surveyQuestion.saveToFieldId == fields[ftype.index][0]}">selected</c:if>>${fields[ftype.index][1]}</option>
                                </c:forEach>
                            </form:select>&nbsp;&nbsp;&nbsp;
                            <form:checkbox path="autoPopulateFromField" id="autoPopulateFromField" value="true" />&nbsp;<label class="control-label" for=autoPopulateFromField">Auto populate question with this field</label>
                        </div>
                    </div>
                </div>    
            </div> 
            <%-- Question Logic Div --%>
            <div class="tab-pane tab-pane-question fade logicPane" id="logic">
                <div class="panel" id="questionChoiceDiv">
                    <div class="panel-body">
                        <table class="table choiceTable" border="0">
                            <thead>
                                <tr>
                                    <th>if answer is...</th>
                                    <th>Then skip to...</th>
                                    <th class="center-text">Clear All</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${surveyQuestion.questionChoices}" var="choiceDetails" varStatus="choice">
                                    <tr>
                                        <td>
                                            <input type="text" value="${choiceDetails.choiceText}" class="form-control" disabled="disabled" style="width:300px;" />
                                        </td>
                                        <td>
                                            <div class="form-inline">
                                                <div class="form-group">
                                                    <select style="width:250px;" id="logicskipToPage_${choice.index}" rel="${choice.index}" class="form-control logicskipToPage">
                                                        <option value="0">- Select A Page -</option>
                                                        <c:forEach items="${pages}" var="page">
                                                            <c:if test="${page.pageNum > surveyQuestion.pageNum}">
                                                                <option value="${page.id}" <c:if test="${page.id == choiceDetails.skipToPageId}">selected</c:if>>${page.pageNum}. ${page.pageTitle}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                                <div class="form-group">
                                                    <select style="width:250px;" id="logicskipToQuestion_${choice.index}" rel="${choice.index}" rel2="${choiceDetails.skipToQuestionId}" class="form-control logicskipQuestion" disabled="true">
                                                        <option value=""></option>
                                                    </select>
                                                </div>
                                            </div>
                                        </td>
                                        <td class="center-text">
                                           Clear
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>      
            <%-- Question Move Div --%>
            <div class="tab-pane tab-pane-question fade movePane" id="move">
                <div class="well well-xsm" style="background-color:#ffffff; margin-bottom: 2px">
                    <label class="control-label" for="move">Move this question to..</label>
                </div>
                <div class="panel" id="saveToDiv">
                    <div class="panel-body">
                        <div class="form-inline">
                            <div class="form-group">
                                <label class="control-label" for=moveToPage">Page</label><br />
                                <select id="movemoveToPage" class="form-control moveToPage">
                                    <option value="">- Select A Page -</option>
                                    <c:forEach items="${pages}" var="page">
                                        <option value="${page.id}">${page.pageNum}. ${page.pageTitle}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group movePositionDiv" style="display:none;">
                                <label class="control-label" for=position">Position</label><br />
                                <select id="moveposition" class="form-control">
                                    <option value="after">After</option>
                                    <option value="before">Before</option>
                                </select>
                            </div>
                            <div class="form-group moveToQuestionDiv" style="display:none;">
                                <label class="control-label" for=moveToQuestion">Question</label><br />
                                <select id="movemoveToQuestion" class="form-control moveToQuestion"></select>
                            </div>
                        </div>
                    </div>
                </div>   
            </div> 
            <%-- Question Copy Div --%>
            <div class="tab-pane tab-pane-question fade copyPane" id="copy">
                <div class="well well-xsm" style="background-color:#ffffff; margin-bottom: 2px">
                    <label class="control-label" for="move">Duplicate this question and put it on...</label>
                </div>
                <div class="panel" id="saveToDiv">
                    <div class="panel-body">
                        <div class="form-inline">
                            <div class="form-group">
                                <label class="control-label" for=moveToPage">Page</label><br />
                                <select id="copymoveToPage" class="form-control moveToPage">
                                    <option value="">- Select A Page -</option>
                                    <c:forEach items="${pages}" var="page">
                                        <option value="${page.id}">${page.pageNum}. ${page.pageTitle}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group movePositionDiv" style="display:none;">
                                <label class="control-label" for=position">Position</label><br />
                                <select id="copyposition" class="form-control">
                                    <option value="after">After</option>
                                    <option value="before">Before</option>
                                </select>
                            </div>
                            <div class="form-group moveToQuestionDiv" style="display:none;">
                                <label class="control-label" for=moveToQuestion">Question</label><br />
                                <select id="copymoveToQuestion" class="form-control moveToQuestion"></select>
                            </div>
                        </div>
                    </div>
                </div>   
            </div>
            <div class="form-group" style="margin-top: 20px;">
                <input type="button" id="submitQuestion" role="button" class="btn btn-primary saveQuestionBtn" value="Save"/>
                <input type="button" id="cancelQuestion" role="button" rel="${qnum}" rel2="${surveyQuestion.id}" rel3="${surveyQuestion.surveyPageId}" class="btn btn-danger" value="Cancel"/>
            </div>     
        </div> 
   </form:form>            
</div>
