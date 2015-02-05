<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:choose>
               <c:when test="${not empty survey}">
            	<form:form id="surveyForm" commandName="survey" method="post" role="form">
            		<div class="form-container">
                    	<form:hidden path="id" />
                    	<form:hidden path="programId" />            	
                        <form:hidden path="dateModified" />
                        <form:hidden path="dateCreated" />                      
                    	<div class="form-group">
                    	   <spring:bind path="title">
                        	<div id="titleDiv" class="form-group ${status.error ? 'has-error' : '' }">
                    		<label class="control-label" for="title">Survey Title*</label>
                   			 <form:input path="title" id="title" class="form-control" type="text"  maxLength="255" />
                   			 <form:errors path="title" cssClass="control-label" element="label" />
                    		 <c:if test="${not empty existingTitle}"><span class="control-label has-error">${existingTitle}</span></c:if>
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
                          <div class="form-group col-md-4">
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
                          <div class="form-group col-md-8">
                          </div>
                        </div> 
                        
                        <div class="row">                           
               		 	<div class="form-group col-md-4">
                            <label for="status">Required Engagement *</label>
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
                            <label for="status">Allow Engagement *</label>
                            <div>
                                <label class="radio-inline">
                                    <form:radiobutton id="allowEngagement" path="allowEngagement" value="true" /> Yes
                                </label>
                                <label class="radio-inline">
                                    <form:radiobutton id="allowEngagement" path="allowEngagement" value="false" /> No
                                </label>
                            </div>
                        </div>
                         <div class="form-group col-md-8">
                         </div>
                        </div>
                        <div class="form-group">
                            <label for="status">Duplicates Allowed *</label>
                            <div>
                                <label class="radio-inline">
                                    <form:radiobutton id="duplicatesAllowed" path="duplicatesAllowed" value="true" /> Yes
                                </label>
                                <label class="radio-inline">
                                    <form:radiobutton id="duplicatesAllowed" path="duplicatesAllowed" value="false" /> No
                                </label>
                            </div>
                        </div>
               		 	<spring:bind path="activityCodeId">
                            <div id="activityCodeIdDiv" class="form-group ${status.error ? 'has-error' : '' }">
                                <label class="control-label" for="typeId">Activity Code</label>
                                <form:select path="activityCodeId" id="activityCodeId" class="form-control half">
                                    <option value="" label=" - Select - " >- Select -</option>
                                    <c:forEach items="${activityCodes}"  varStatus="code">
                                        <option value="${activityCodes[code.index].id}" <c:if test="${survey.activityCodeId == activityCodes[code.index].id}">selected</c:if>>${activityCodes[code.index].codeDesc}</option>
                                    </c:forEach>
                                </form:select>
                               <form:errors path="activityCodeId" cssClass="control-label" element="label" />  
                               <span id="activityCodeIdMsg" class="control-label"></span>    
                            </div>
                        </spring:bind> 
               		 	<spring:bind path="reminderText">
                        	<div id="reminderTextDiv" class="form-group ${status.error ? 'has-error' : '' }">
                    		<label class="control-label" for="reminderText">Reminder Text</label>
                   			<form:input path="reminderText" id="title" class="form-control" type="text"  maxLength="255" />
                   			<form:errors path="reminderText" cssClass="control-label" element="label" />
                    		</div> 
               		 	</spring:bind>                        
               		 	
                        <div class="row">                           
               		 	<div class="form-group col-md-4">
               		 	<spring:bind path="prevButtonText">
                        	<div id="prevButtonTexttDiv" class="form-group ${status.error ? 'has-error' : '' }">
                    		<label class="control-label" for="prevButtonText">Previous Button Text</label>
                   			<form:input path="prevButtonText" id="title" class="form-control" type="text"  maxLength="30" />
                   			<form:errors path="prevButtonText" cssClass="control-label" element="label" />                   		
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
               		 	</div>
               		 	 <div class="form-group col-md-4">
               		 	<spring:bind path="doneButtonText">
                        	<div id="doneButtonTextDiv" class="form-group ${status.error ? 'has-error' : '' }">
                    		<label class="control-label" for="doneButtonText">Done Button Text</label>
                   			<form:input path="doneButtonText" id="title" class="form-control" type="text"  maxLength="30" />
                   			<form:errors path="doneButtonText" cssClass="control-label" element="label" />
               		 	</div> 
               		 	</spring:bind> 
               		 	</div>
               		 	</div> 
               		 	<div>
               		 	<c:choose>
	               		 	<c:when test="${not empty create}">
	               		 	<input type="hidden" id="action" name="action" value="save" />
	                    	</c:when>
	                    	<c:otherwise>
	               		 		<div class="form-group">
	                   				 <input type="button" id="submitSurveyButton" role="button" class="btn btn-primary" value="Save"/>
	                			</div>
	                		</c:otherwise>          	
                    	</c:choose>
                    	
                    </div>                                     
                    </div>
                </form:form>
                </c:when>
                <c:otherwise>
                This survey does not exist or you do not have permission to access it.
                </c:otherwise>
                </c:choose>