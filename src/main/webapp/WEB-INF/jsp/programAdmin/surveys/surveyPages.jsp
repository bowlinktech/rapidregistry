<%-- 
    Document   : surveyPages.jsp
    Created on : Feb 20, 2015, 10:59:59 AM
    Author     : chadmccue
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<link rel="stylesheet" href="/dspResources/css/scroll.css" type="text/css" media="screen">

<!-- survey pages, there should be at least one page -->
<c:forEach var="page" items="${surveyPages}">
    <div class="row" id="page${page.id}" style="height: 50px;">
        <div class="pull-left">
            <h4>Page ${page.pageNum}</h4>
        </div>
        <div class="pull-right pageSection" relPage="${page.id}">
            <div class="pull-right">
                <div id="answerDiv" class="form-group">
                    <select id="pageDD${page.id}" name="answer" class="form-control ddForPage">
                        <c:forEach var="p" items="${surveyPages}">
                            <option value="${p.pageNum}" <c:if test="${p.id == page.id}"> selected </c:if>>P${p.pageNum}: ${p.pageTitle}</option>
                        </c:forEach>
                    </select>
                    <span id="errorMsg_dropDown" class="control-label"></span>  
                </div>
            </div>
        </div>
    </div>
    <div class="row">               
        <div class="panel panel-default">
            <div class="panel-heading" style="background-color: #4B7C88">
                <h4 id="pageTitleHeading_${page.id}">
                    <span class="editTitle">
                        <a href="javascript:void(0);" class="btn-link-lg editPageTitle" rel="${page.id}" title="Edit Page Title" role="button">
                            <span id="pageTitleSpan_${page.id}" style="color:#ffffff">${page.pageTitle}</span> <span class="glyphicon glyphicon-edit" style="color: #ffffff; cursor: pointer;"></span>
                        </a>
                    </span>
                </h4>
                <div id="pageTitleEdit_${page.id}" style="display:none;">
                    <form class="form-inline">
                      <div id="pageTitleDiv_${page.id}" class="form-group">
                        <input type="text" id="pageTitle_${page.id}" class="form-control" value="${page.pageTitle}" />
                      </div>
                      <button type="button" rel="${page.id}" class="btn btn-default submitPageTitleChanges">Save</button>
                    </form>
                </div>
            </div>
             <div class="panel-body pageQuestionsPanel" id="pagePanel_${page.id}" rel="${page.id}" style="background-color: #EFEFEF">
                 <section>
                <c:choose>
                    <c:when test="${not empty page.surveyQuestions}">
                        <c:forEach var="question" items="${page.surveyQuestions}">
                            <div class="questionDiv row" id="questionDiv${question.id}" rel="${question.id}" style="width: 98%; padding: 5px; margin-left: 5px; margin-bottom: 30px;">
                                <div class="pull-left">
                                    <span><h4><c:if test="${question.required == true}">*&nbsp;</c:if>${question.questionNum}. ${question.question}</h4></span>
                                    <c:choose>
                                        <c:when test="${question.answerTypeId == 3}">
                                            <div class="form-group">
                                                <input type="text" placeholder="Enter your Question" class="form-control" type="text" maxLength="255" disabled style="background-color:#ffffff; width:500px;" />
                                            </div>
                                        </c:when>
                                    </c:choose>
                                </div>
                                <div class="pull-right" id="questionBtns${question.id}" style="display:none;">
                                    <button type="button" class="btn btn-primary btn-sm questionButton" pane="editPane" rel="${question.id}">
                                        <span class="glyphicon glyphicon-edit" aria-hidden="true"></span> Edit
                                    </button>
                                    <button type="button" class="btn btn-default btn-sm questionButton" pane="optionsPane" rel="${question.id}">
                                        <span class="glyphicon glyphicon-cog" aria-hidden="true"></span> Options
                                    </button>  
                                    <button type="button" class="btn btn-default btn-sm questionButton" pane="movePane" rel="${question.id}">
                                        <span class="glyphicon glyphicon-move" aria-hidden="true"></span> Move
                                    </button>  
                                    <button type="button" class="btn btn-default btn-sm questionButton" pane="copyPane" rel="${question.id}">
                                        <span class="glyphicon glyphicon-tag" aria-hidden="true"></span> Copy
                                    </button>   
                                    <button type="button" class="btn btn-danger btn-sm deleteQuestion" rel="${question.id}">
                                        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span> Delete
                                    </button>         
                                </div>
                            </div>
                            <div id="editQuestionDiv_${question.id}" class="row" style="display:none;"></div>            
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div class="center-text row" id="emptyPageDiv_${page.id}" style="padding-top:75px; padding-bottom:75px; height:350px;">
                            <h2 style="color:grey">[ Empty Page ]</h2>
                            <br />
                            <h4 style="color:grey">Add a question by clicking it from the left menu</h4>
                        </div>
                    </c:otherwise>
                </c:choose>   
                <div id="newQuestionDiv_${page.id}" class="row" style="display:none;"></div>
                 </section>
            </div>
        </div>
    </div>
    <div class="row" id="newPageDiv_${page.pageNum}" style="padding-bottom: 20px; ">
        <div class="well well-sm text-center"  style="background-color:#ffffff; border-style: dashed">
            <button type="button" class="btn btn-primary btn-sm newPage" rel="${page.id}">
                <span class="glyphicon glyphicon-file" aria-hidden="true"></span> + Add New Page
            </button>
        </div>
    </div>
</c:forEach>

