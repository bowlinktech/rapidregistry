<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<link rel="stylesheet" href="/dspResources/css/scroll.css" type="text/css" media="screen">

<%-- make sure there is a survey --%>
<c:choose>
<c:when test="${empty surveys}">
<div class="main clearfix" role="main">
You do not have permission to access this page.
</div>
</c:when>
<c:otherwise>



<div class="main clearfix" role="main">
    <div class="col-md-12">
        <c:choose>
            <c:when test="${not empty msg}" >
                <div class="alert alert-success">
                    <strong>Success!</strong> 
                    <c:choose>
                        <c:when test="${msg == 'surveyUpdated'}">The survey has been successfully updated!</c:when>
                        <c:when test="${msg == 'surveyCreated'}">The survey has been successfully added!</c:when>
                        <c:when test="${msg == 'pageUpdated'}">The page has been successfully updated!</c:when>
                        <c:when test="${msg == 'pageCreated'}">The page has been successfully added!</c:when>

                    </c:choose>
                </div>
            </c:when>
        </c:choose>
        
        <%--- we set up the page so that the menu bar will always stay with the question --%>
        
<div class="main clearfix" role="main">

    <div class="col-md-12">

<div id="wrapper"  class="col-md-12">

	<div id="left">

		<div id="sidebar">
			<%@ include file="menu.jsp" %>
		</div>

	</div>

	<div id="right">
	
 <!--  we start to loop pages here -->
  		<!-- in case there are no survey pages -->
 		<c:if test="${fn:length(survey.surveyPages) == 0}">
 		<section class="panel panel-default">
        
            <div id="divSurTitle${page.id}" class="panel-heading" style="height:46px; background-color: rgba(0,0,0, 0.3);">
            	<h3 class="panel-title" class="main clearfix">
            	<a href="#surveyModal" data-toggle="modal" id="surveyTitle${page.id}" title="${surveyTitle}" relPage="${page.id}" relS="${surveyId}" class="editSurveyTitle btn-link-lg" role="button">${surveyTitle}</a></h3>
            	</div>           	
 			</section>
			<section class="panel panel-default">
            <div class="panel-body text-center">
            <%-- from page id, we can look up all info --%>
            <a href="javascript:alert('this adds a page to the page after');" rel="${page.id}">Add a Page</a>
         	</div>
         </section>
		</c:if>
 
 		<!-- survey pages, there should be at least one page -->
        <c:forEach var="page" items="${survey.surveyPages}">
        <section class="panel panel-default">
        	<div class="row" id="page${page.id}">
		                <div class="col-md-2">
		                	<h4>Page ${page.pageNum}</h4>
		                </div>
		                <div class="scrollable post" relPage="${page.id}">
			                <div class="pull-right">
				                <div id="answerDiv" class="form-group">
					                     <select  id="pageDD${page.id}" name="answer" class="form-control ddForPage">
					                       <c:forEach var="pageNum" items="${surveyPages}">
							           			<option value="page${pageNum.id}" <c:if test="${pageNum.id == page.id}"> selected </c:if>>P. ${pageNum.pageNum} - ${pageNum.pageTitle}</option>
							           	 </c:forEach>
					                     </select>
					                     <span id="errorMsg_dropDown" class="control-label"></span>  
				                 </div>
			            	</div>
		            	</div>
	        	</div>
        </section>
        <section class="panel panel-default">
        
            <div id="divSurTitle${page.id}" class="panel-heading" style="height:46px; background-color: rgba(0,0,0, 0.3);">
            	<h3 class="panel-title" class="main clearfix">
            	<a href="#surveyModal" data-toggle="modal" id="surveyTitle${page.id}" title="${surveyTitle}" relPage="${page.id}" relS="${surveyId}" class="editSurveyTitle btn-link-lg" role="button">${surveyTitle}</a></h3>
            	</div>
            	<div class="panel-heading">
            	<h4>
            		<span class="editTitle"><a href="#surveyModal" data-toggle="modal" class="btn-link-lg editPageTitle" relPage="${page.id}" title="Edit Page Title" role="button" id="pageTitle${page.id}">${page.pageTitle}
            		<c:if test="${fn:length(page.pageTitle) == 0}">-- Add Page Title --</c:if>
	               	</a>
	               	</span>
	            </h4>
     			</div>
            <!--  each question is displayed here -->
           <c:forEach var="question" items="${page.surveyQuestions}">
			 <div class="panel-body" id="questionDiv${question.id}" rel="${questionId}">
                <form:form id="pageForm" method="post" role="form">
                    <section class="panel panel-default">
                        <div class="panel-body">
					             <c:set var="displayQ" value="Q. ${question.questionNum}"/>
					             <c:if test="${fn:length(question.dspQuestionId) > 0}">
					            	<c:set var="displayQ" value="${question.dspQuestionId}"/>
					             </c:if>
					             <label class="control-label" for="question">${displayQ} - ${question.question}</label>
                                    <c:choose>
	            <c:when test="${question.answerTypeId == 1}">
		            <div class="form-group">
	                           <c:forEach var="answer" items="${question.surveyAnswers}">
	                  				<label class="radio-inline">
	                       				<input type="radio" name="answerMC" value="1" <c:if test="${answer.defAnswer}">checked</c:if> /> ${answer.answer}
					                 </label>
					                 <br/>
	                   			</c:forEach>
	                       </div>          
	            </c:when>
	            <c:when test="${question.answerTypeId == 2}"> <%--dropdown--%>
	            	<div id="answerDiv" class="form-group">
	                     <select  id="ddAns" name="answer" class="form-control ">
	                       <c:forEach var="answer" items="${question.surveyAnswers}">
			           			<option <c:if test="${answer.defAnswer}"> selected </c:if>>Answer is ${answer.answer}</option>
			           	 </c:forEach>
	                     </select>
	                     <span id="errorMsg_dropDown" class="control-label"></span>  
                      </div>
                </c:when>
	            <c:when test="${question.answerTypeId == 3}"><!--  single text box -->
	            	<div class="form-group">
			               ${question.surveyAnswers[0].label}	<input type="text" name="answer" value='<c:if test="${answer.defAnswer}"> ${question.surveyAnswers[0].answer} </c:if>' class="form-control" type="text" maxLength="${question.surveyAnswers[0].maxLength}" />	               
	                </div>
	            </c:when>
	            <c:when test="${question.answerTypeId == 4}"><%-- multiple textbox --%>
	            <div class="form-group" >
		            <c:forEach var="answer" items="${question.surveyAnswers}">
			            ${answer.label}
						<input class="form-control" type="text" max="${answer.maxLength}" value="<c:if test='${answer.defAnswer}'>${answer.answer}</c:if>">
			            <br/>
			         </c:forEach>
		         </div>
	            </c:when>
	            <c:when test="${question.answerTypeId == 5}"><%--comment box --%>
	            	<div class="form-group">
			            	${answer.label}
				            <!--  check where to pull out cols and rows -->
							<textarea rows="5" maxlength="${question.surveyAnswers[0].maxLength}" id="a${question.surveyAnswers[0].id}" name="a${question.surveyAnswers[0].id}" class="form-control"><c:if test="${question.surveyAnswers[0].defAnswer} == true">${question.surveyAnswers[0].answer}</c:if></textarea> 
		         	</div>
	            </c:when>
	            <c:when test="${question.answerTypeId == 6}"><%-- 'Date/Time' --%>
		            ${question.answerTypeId} is the date / time
		            Where should we track how we restrict
		      	</c:when>
	            <c:when test="${question.answerTypeId == 7}"><%--'7','Display Text' --%>
		            <div class="form-group">
		            	${question.surveyAnswers[0].answer}
		            </div>
	            </c:when>
	            <c:when test="${question.answerTypeId == 8}"><%-- '8' Checkbox' --%>
	            <div class="form-group">
	            	<c:forEach var="answer" items="${question.surveyAnswers}">
                        <div class="checkbox">
                            <label>
                            <input type="checkbox" id="q_${question.id}"  
                              value="${answer.answer}" <c:if test="${answer.defAnswer}">checked</c:if> />${answer.answer}
                           </label>
                        </div>
                    </c:forEach>     
                    </div>  
	            </c:when>
           
            </c:choose>
           <div class="form-group" id="editButtons${question.id}" style="display:none">
           This should appear when cursor is mouse over the question, clicking edit would bring up tab modal
           	<br/>
           	<input type="button" id="submitButton"  role="button" class="btn btn-primary" value="Edit Question" rel="${question.id}" onClick="javascript:alert('open edit/option tab');"/>
            </div>    
                                </div> 

                    </section>
                </form:form>
</div>
			 

            </c:forEach>
            
         </section>
         
         <section class="panel panel-default">
            <div class="panel-body text-center">
            <%-- from page id, we can look up all info --%>
            <a href="javascript:alert('this adds a page to the page after');" rel="${page.id}">Add a Page</a>
         	</div>
         </section>
        </c:forEach>	
        <%-- end of questions, need padding --%>

<div class="marginBottom" style="visibility:hidden;">

</div>
	</div>
<%-- padding --%>
	<div class="clear"></div>

</div>
</div>
</div>
    </div>
</div>
</c:otherwise>
</c:choose>
<!-- Edit Survey modal -->
<div class="modal fade" id="surveyModal" role="dialog" tabindex="-1" aria-labeledby="Modify Survey" aria-hidden="true" aria-describedby="Modify Survey"></div>
