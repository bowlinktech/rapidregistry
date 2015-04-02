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
                    <label class="control-label" for="pageTitle"><!--<span class="qNum">Q${qnum}</span>:--> Date / Time</label>
                    <form:input path="question" placeholder="Enter your Question" id="question" class="form-control" type="text"  maxLength="255" />
                </div>
                <c:if test="${surveyQuestion.dateQuestionRows.size() > 0}">
                    <div class="panel" id="dateQuestionRowDiv" style="margin-bottom: 2px">
                        <div class="panel-body">
                            <table class="table dateQuestionRowTable" border="0">
                                <thead>
                                    <tr>
                                        <th>Rows</th>
                                        <th class="center-text"></th>
                                        <th class="center-text"></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${surveyQuestion.dateQuestionRows}" var="rowDetails" varStatus="row">
                                        <input type="hidden" id="id_${row.index}" name="dateQuestionRows[${row.index}].id" value="${rowDetails.id}" />
                                        <input type="hidden" id="questionId_${row.index}" name="dateQuestionRows[${row.index}].questionId" value="${rowDetails.questionId}" />
                                        <tr rel="${row.index}">
                                            <td style="vertical-align: middle; width:75px;"> Label ${row.index+1}</td>
                                            <td>
                                                <input type="text" name="dateQuestionRows[${row.index}].label"  value="${rowDetails.label}" rel="${row.index}" class="form-control fieldLabel formField" />
                                            </td>
                                            <td style="vertical-align:top;">
                                                <i class="glyphicon glyphicon-plus-sign addRow" style="font-size:1.7em; cursor: pointer"></i>&nbsp;
                                                <i class="glyphicon glyphicon-minus-sign removeRow" style="font-size:1.7em; cursor: pointer"></i>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </c:if>
                <div class="well well-xsm" style="background-color:#ffffff;">
                    <div class="row">
                        <div class="col-md-2" style="width:150px;">
                            <label class="control-label">What to Collect:</label>
                        </div>
                        <div class="col-md-5 pull-left">
                            <form:checkbox path="collectDateInfo" id="collectDateInfo" />&nbsp;Date Info
                            <form:checkbox path="collectTimeInfo" id="collectTimeInfo" style="margin-left:10px;" />&nbsp;Time Info
                        </div>
                        <div class="col-md-5">
                            <div class="pull-right">
                                <form:radiobutton path="dateFormatType" id="dateFormatType" value="1" />&nbsp;MM / DD / YYYY
                                <form:radiobutton path="dateFormatType" id="dateFormatType" value="2" style="margin-left:10px;" />&nbsp;DD / MM / YYYY
                            </div>
                        </div>
                    </div>
                    
                </div>
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
                    <input type="checkbox" id="choiceLayout" <c:if test="${surveyQuestion.dateDspType > 0}">checked="checked"</c:if> />&nbsp;<label class="control-label" for="choiceLayout">Change the layout of how the date rows are displayed</label>
                </div>
                <div class="panel" id="choiceLayoutDiv" style="${surveyQuestion.dateDspType > 0 ? 'display:block;' : 'display:none;'}">
                    <div class="panel-body">
                        <div class="form-group">
                            <label class="radio-inline control-label">
                                <form:radiobutton path="dateDspType" class="radio" value="1" /> <strong>Underneath</strong>
                            </label>
                        </div>
                        <div class="form-group">
                            <label class="radio-inline control-label">
                                <form:radiobutton path="dateDspType" class="radio" value="2" /> <strong>Side by Side</strong>
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
                            <form:checkbox path="autoPopulateFromField" id="autoPopulateFromField" value="true" />&nbsp;<label class="control-label" for="autoPopulateFromField">Auto populate question with this field</label>
                        </div>
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
            <%-- Question Copy Div --%>
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
