<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h3 class="panel-title">Edit Question ${questionNum} for Page ${pageNum} ${updated}</h3>
         </div>
         <div class="modal-body">
            <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%-- edit answers and questions --%>
<c:choose>
               <c:when test="${not empty question}">
            	<form:form id="questionForm" commandName="question" method="post" role="form">
            		<div class="form-container">
                    	<form:hidden path="id" />
                    	<form:hidden path="surveyPageId" />            	
                        <form:hidden path="dateCreated" />  
                        <form:hidden path="questionNum"/>
                         <%-- 
                         `id`,
`hide`,
`required`,
`dspQuestionId`,
`question`,

`answerTypeId`,
`columnsDisplayed`,
`allowMultipleAns`,
`saveToTableName`,
`saveToFieldId`,
`autoPopulateFromField`,
`dateCreated`,
                         
                         --%>
                                       		 		                
                    	<div class="form-group">
                    	   <spring:bind path="pageTitle">
                        	<div id="pageTitleDiv" class="form-group ${status.error ? 'has-error' : '' }">
                    		<label class="control-label" for="pageTitle">Page Title*</label>
                   			 <form:input path="pageTitle" id="pageTitle" class="form-control" type="text"  maxLength="255" />
                   			 <form:errors path="pageTitle" cssClass="control-label" element="label" />
                    		 <span id="pageTitleMsg" class="control-label"></span>
                    		</div> 
               		 		</spring:bind> 
               		 	</div>
               		 	
               		 	<spring:bind path="pageDesc">
                        	<div id="pageDescDiv" class="form-group ${status.error ? 'has-error' : '' }">
                    		<label class="control-label" for="pageDesc">Page Description</label>
                   			<form:textarea path="pageDesc" class="form-control"  rows="10" />
                   			<form:errors path="pageDesc" cssClass="control-label" element="label" />
                    		</div> 
               		 	</spring:bind>                        
               		 	<div class="form-group">
	                   				 <input type="button" id="submitPageButton" role="button" class="btn btn-primary" value="Save" relPage="${page.pageNum}"/>
	                	</div>
	                	
                    	
                                                        
                    </div>
                </form:form>
                </c:when>
                <c:otherwise>
                	This page does not exist or you do not have permission to access it.
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

