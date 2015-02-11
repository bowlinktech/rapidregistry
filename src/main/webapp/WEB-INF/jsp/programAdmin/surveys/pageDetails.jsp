<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h3 class="panel-title">Edit Page Information ${updated}</h3>
         </div>
         <div class="modal-body">
            <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:choose>
               <c:when test="${not empty page}">
            	<form:form id="pageForm" commandName="surveyPages" method="post" role="form">
            		<div class="form-container">
                    	<form:hidden path="id" />
                    	<form:hidden path="surveyId" />            	
                        <form:hidden path="pageNum" />
                        <form:hidden path="dateCreated" />                      
                    	<div class="form-group">
                    	   <spring:bind path="pageTitle">
                        	<div id="pageTitleDiv" class="form-group ${status.error ? 'has-error' : '' }">
                    		<label class="control-label" for="pageTitle">Page Title*</label>
                   			 <form:input path="pageTitle" id="pageTitle" class="form-control" type="text"  maxLength="255" />
                   			 <form:errors path="pageTitle" cssClass="control-label" element="label" />
                    		</div> 
               		 		</spring:bind> 
               		 	</div>
               		 	
               		 	<spring:bind path="pageDesc">
                        	<div id="pageDescDiv" class="form-group ${status.error ? 'has-error' : '' }">
                    		<label class="control-label" for="pageDesc">Page Description</label>
                   			<form:input path="pageDesc" id="pageDesc" class="form-control" />
                   			<form:errors path="pageDesc" cssClass="control-label" element="label" />
                    		</div> 
               		 	</spring:bind>                        
               		 	<div class="form-group">
	                   				 <input type="button" id="submitSurveyButton" role="button" class="btn btn-primary" value="Save"/>
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

