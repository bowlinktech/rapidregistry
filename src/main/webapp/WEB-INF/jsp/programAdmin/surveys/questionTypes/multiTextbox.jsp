<%-- 
    Document   : singleTextBox
    Created on : Feb 21, 2015, 9:38:39 AM
    Author     : chadmccue
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<div class="container pull-left" style="margin-left: 20px; width:98%;">
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
                    <label class="control-label" for="pageTitle">Question</label>
                    <form:input path="question" placeholder="Enter your Question" id="question" class="form-control" type="text"/>
                </div>
                <div class="form-group">
                    <label class="control-label" for="reportText">Report Text (will not appear on survey)</label>
                    <form:input path="reportText" placeholder="Enter your report text" id="reportText" class="form-control" type="text"  maxLength="255" />
                </div>
                <div class="form-group">
                    <label class="control-label" for="questionTag">Question Tag</label>
                    <form:input path="questionTag" placeholder="Enter a question tag" id="questionTag" class="form-control" type="text"  maxLength="75" />
                </div>
                <c:if test="${not empty availableTables || not empty availableCW}">
                    <div class="well well-xsm" style="background-color:#ffffff; margin-bottom: 2px">
                        <input type="checkbox" id="populate" <c:if test="${not empty surveyQuestion.populateFromTable || surveyQuestion.populateFromCW > 0}">checked="checked"</c:if> />&nbsp;<label class="control-label" for="populateFromTable">Populate choices from an existing table</label>
                    </div>
                    <div id="autoPopulateDiv">
                        <div class="well well-xsm"  style="background-color:#ffffff; margin-bottom: 2px; height: 50px; ${not empty surveyQuestion.populateFromTable || surveyQuestion.populateFromCW > 0 ? 'display:block;' : 'display:none;'}">
                            <c:choose>
                                <c:when test="${surveyQuestion.id > 0}">
                                    <form:select path="populateFromTable" id="populateFromTable" class="form-control half sm-input">
                                        <option value="">- Select Table -</option>
                                        <c:forEach items="${availableTables}" var="table" varStatus="ftype">
                                            <option value="${table.tableName}" <c:if test="${surveyQuestion.populateFromTable == table.tableName}">selected</c:if>>${table.tableName}</option>
                                        </c:forEach>
                                        <c:forEach items="${availableCW}" var="cw" varStatus="cwtype">
                                            <option value="${cw.id}" <c:if test="${surveyQuestion.populateFromCW == cw.id}">selected</c:if>>${cw.name}</option>
                                        </c:forEach>    
                                    </form:select>
                                </c:when>
                                <c:otherwise>
                                    <span>The question must be saved before this option is available.</span>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="well well-xsm"  style="background-color:#ffffff; margin-bottom: 2px; height: 75px; ${not empty surveyQuestion.populateFromTable || surveyQuestion.populateFromCW > 0 ? 'display:block;' : 'display:none;'}">
                            <label class="control-label" for="reportText">Column to use for the above selected table</label>
                            <form:input path="populateFromTableCol" placeholder="Enter the column to use with the selected table above" id="populateFromTableCol" class="form-control" type="text"  maxLength="55" />
                        </div>
                    </div>
                </c:if>
            </div>
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
                    <input type="checkbox" id="validateField" <c:if test="${surveyQuestion.validationId > 1}">checked="checked"</c:if> />&nbsp;<label class="control-label" for="validationId">Validate Answer</label>
                </div>
                <div class="panel" id="validationDiv" style="${surveyQuestion.validationId > 1 ? 'display:block;' : 'display:none;'}">
                    <div class="panel-body">
                        <div class="form-group">
                            <label class="control-label" for="fieldValidation">Select the field validation</label>
                            <form:select path="validationId" id="fieldValidation" class="form-control half">
                                <c:forEach items="${validationTypes}"  var="fieldvalidationtypes" varStatus="vtype">
                                    <option value="${validationTypes[vtype.index][0]}" <c:if test="${surveyQuestion.validationId == validationTypes[vtype.index][0]}">selected</c:if>>${validationTypes[vtype.index][1]}</option>
                                </c:forEach>
                            </form:select>
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
                            <form:checkbox path="autoPopulateFromField" id="autoPopulateFromField" value="true" />&nbsp;<label class="control-label" for="autoPopulateFromField">Auto populate question with this field</label>
                        </div>
                    </div>
                </div>    
            </div> 
            <div class="tab-pane tab-pane-question fade movePane" id="move">
                <div class="well well-xsm" style="background-color:#ffffff; margin-bottom: 2px">
                    <label class="control-label" for="move">Move this question to..</label>
                </div>
                <div class="panel" id="saveToDiv">
                    <div class="panel-body">
                        <div class="form-inline">
                            <div class="form-group">
                                <label class="control-label" for="moveToPage">Page</label><br />
                                <select id="movemoveToPage" class="form-control moveToPage">
                                    <option value="">- Select A Page -</option>
                                    <c:forEach items="${pages}" var="page">
                                        <option value="${page.id}">${page.pageNum}. ${page.pageTitle}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group movePositionDiv" style="display:none;">
                                <label class="control-label" for="position">Position</label><br />
                                <select id="moveposition" class="form-control">
                                    <option value="after">After</option>
                                    <option value="before">Before</option>
                                </select>
                            </div>
                            <div class="form-group moveToQuestionDiv" style="display:none;">
                                <label class="control-label" for="moveToQuestion">Question</label><br />
                                <select id="movemoveToQuestion" class="form-control moveToQuestion"></select>
                            </div>
                        </div>
                    </div>
                </div>   
            </div> 
            <div class="tab-pane tab-pane-question fade copyPane" id="copy">
                <div class="well well-xsm" style="background-color:#ffffff; margin-bottom: 2px">
                    <label class="control-label" for="move">Duplicate this question and put it on...</label>
                </div>
                <div class="panel" id="saveToDiv">
                    <div class="panel-body">
                        <div class="form-inline">
                            <div class="form-group">
                                <label class="control-label" for="moveToPage">Page</label><br />
                                <select id="copymoveToPage" class="form-control moveToPage">
                                    <option value="">- Select A Page -</option>
                                    <c:forEach items="${pages}" var="page">
                                        <option value="${page.id}">${page.pageNum}. ${page.pageTitle}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group movePositionDiv" style="display:none;">
                                <label class="control-label" for="position">Position</label><br />
                                <select id="copyposition" class="form-control">
                                    <option value="after">After</option>
                                    <option value="before">Before</option>
                                </select>
                            </div>
                            <div class="form-group moveToQuestionDiv" style="display:none;">
                                <label class="control-label" for="moveToQuestion">Question</label><br />
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