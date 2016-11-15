<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="main clearfix" role="main">
    <div class="row-fluid">
        <div class="col-md-12">
            <c:if test="${not empty param.msg}" >
            <div class="alert alert-success">
                <strong>Success!</strong> 
                <c:choose>
                    <c:when test="${param.msg == 'ctAdded'}">The cross tab table has been added to this report!</c:when>
                    <c:when test="${param.msg == 'ctRemoved'}">The cross tab table has been successfully removed from this report!</c:when>
                </c:choose>
                <span>${hierarchyList[1].name}</span>
            </div>
        </c:if>
            <section class="panel panel-default">
                <div class="panel-body">
                   <dl>
                    <dt>
	                    <dd><strong>Program Summary:</strong></dd>
	                    <dd><strong>Program Name:</strong> ${programDetails.programName}</dd>
                    </dt>
                   </dl>
                </div>
            </section>
        </div>
    </div>

<div class="row-fluid">
      <div class="col-md-12">
          <section class="panel panel-default">
              <div class="panel-heading">
               <div class="pull-right">
       				 <a href="#reportModal" data-toggle="modal" class="btn btn-primary btn-xs btn-action newCrossTabReportForm" rel="${report.id}" title="Add New Cross Tab Table">Add New Cross Tab Table</a>
    			</div>
                <h3 class="panel-title" id="reportTypeTitle">Select ${hierarchyList[1].name} for Report</h3>
            </div>
            <div class="panel-body">
            <form name="ctEntityChange" id="">
            <div class="form-group">
                <select multiple class="form-control" id="form-field-select-4">
				                    <c:forEach items="${entities}" var="entity">
				                        <option value="${entity.id}" <c:if test="${entity.isSelected == 1}">selected</c:if>>${entity.id} - ${entity.name}</option>
				                    </c:forEach>
				</select>
            </div>
			<div class="form-group">
                 <input type="button" id="submitEntitiesButton" rel1="${details.id}" rel="${btnValue}" role="button" class="btn btn-primary" value="Save ${hierarchyList[1].name}"/>
            </div>             
			</form>            
            </div>

       </section>
       
    </div>
     <c:forEach items="${report.reportCrossTabs}" var="crossTab"><div class="row-fluid" id="tableDiv${crossTab.id}"><%@ include file="crossTabTable.jsp" %></div>
 	</c:forEach>
</div>
</div>
<!-- Activity Code Details modal -->
<div class="modal fade" id="reportModal" role="dialog" tabindex="-1" aria-labeledby="Aggregated Reports" aria-hidden="true" aria-describedby="Aggregated Reports"></div>

