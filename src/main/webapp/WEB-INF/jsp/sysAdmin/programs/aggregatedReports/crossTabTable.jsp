<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


      <div class="col-md-12">
          <section class="panel panel-default">
          	  
              <div class="panel-heading" <c:if test="${crossTab.statusId == 1}">style="background-color: #4B7C88"</c:if> <c:if test="${crossTab.statusId == 2}">style="background-color: #efefef"</c:if>>
              <div class="pull-right">
       				 <a href="#reportModal" data-toggle="modal" class="btn btn-primary btn-xs btn-action removeCrossTabTable" rel="${crossTab.id}" rel1="${crossTab.reportId}" title="Delete this Table">Delete this Table</a>
    			</div>
              <a href="#reportModal" data-toggle="modal" rel="${crossTab.id}" title="Table Details" class="panel-title unstyled-link crossTabTitle" id="crossTabTitleHref_${crossTab.id}" <c:if test="${crossTab.statusId == 1}">style="color:#ffffff"</c:if> <c:if test="${crossTab.statusId == 2}">style="color:#000000"</c:if>>
                <span id="ctTableSpan_${crossTab.id}">${crossTab.tableTitle}</span><span class="glyphicon glyphicon-edit" style="padding-left:5px; cursor: pointer;"></span>
              </a>
              <c:if test="${crossTab.statusId == 2}">&nbsp;&nbsp;(INACTIVE)</c:if>
              <span id="crossTabId" class="${newCrossTab}" style="display:none;">${crossTab.id}</span>
            </div>
              <div class="panel-body">
              ${crossTab.cwIdRow} & ${crossTab.cwIdCol}
					<div class="panel-body">
	                    <div class="form-container">
	                    	<%-- cross table tab form --%>
	                    	<form:form id="cwDataForm" method="post">
	                    	
	                    	
	                    	
	                    	<div class="form-group">
                            <input type="button" id="submitButton" rel1="${details.id}" rel="${btnValue}" role="button" class="btn btn-primary" value="${btnValue}"/>
                        </div>
	                    	</form:form>
	                    
	                    </div>
                    </div>
                </div>
       </section>
    </div>  
