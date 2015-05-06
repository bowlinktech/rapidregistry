 <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%-- 
    Document   : programModules
    Created on : Nov 4, 2014, 4:38:34 PM
    Author     : chadmccue
--%>

<form>
   <input type="hidden" id="encryptedURL" value="${encryptedURL}" />
</form>

<div class="modal-dialog">
    <div class="modal-content"   style="min-width:800px;">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h3 class="panel-title">Available Surveys to Copy from</h3>
         </div>
         <div class="modal-body">
             <c:choose>
                 <c:when test="${not empty surveys}">
                     
                     <div class="form-group">
                        <select name="availSurveys" id="availSurveys" class="form-control">
                            <option value="0">- Choose a Survey -</option>
                            <c:forEach items="${surveys}" var="survey">
                                <option value="${survey.id}">${survey.title}</option>
                            </c:forEach>
                        </select>
                     </div>
                     <div class="form-group">
                        <input type="button" id="copySurveySubmit" role="button" class="btn btn-primary" value="Copy"/>
                    </div>
                 </c:when>
                 <c:otherwise>
                     <p class="text-info">You current do not have any surveys created to copy from.</p>
                 </c:otherwise>
             </c:choose>
         </div>
    </div>
</div>
