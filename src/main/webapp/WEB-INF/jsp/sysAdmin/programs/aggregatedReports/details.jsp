
<link rel="stylesheet" href="/dspResources/css/bootstrap-duallistbox.css" type="text/css" media="screen">
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<div class="main clearfix" role="main">
    <div class="row-fluid">
        <div class="col-md-12">
            <c:if test="${not empty savedStatus}" >
                <div class="alert alert-success" role="alert">
                    <strong>Success!</strong> 
                    <c:choose>
                        <c:when test="${savedStatus == 'aggReportUpdated'}">The report has been successfully added!</c:when>
                        <c:when test="${savedStatus == 'created'}">The program has been successfully added!</c:when>
                    </c:choose>
                </div>
            </c:if>
            <section class="panel panel-default">
                <div class="panel-body">
                    <dt>
                    <dd><strong>Program Summary:</strong></dd>
                    <dd><strong>Program Name:</strong> ${programDetails.programName}</dd>
                    </dt>
                </div>
            </section>
        </div>
    </div>

<div class="row-fluid">
      <div class="col-md-12">
          <section class="panel panel-default">
              <div class="panel-heading">
                <h3 class="panel-title" rel="${selRepType}" id="reportTypeTitle">Select ${hierarchyList[1].name} for ${reportDetail.reportName}</h3>
            </div>
            <div class="panel-body">
			                <select multiple="" class="form-control" id="form-field-select-4">
			                    <c:forEach items="${entities}" var="entity">
			                        <option value="${entity.id}" <c:if test="${entity.isSelected == 1}">selected</c:if>>${entity.id} - ${entity.name}</option>
			                    </c:forEach>
			                </select>
            			</div>

       </section>
    </div>
     <c:forEach items="${reportDetail.reportCrossTabs}" var="crossTab">
  <div class="row-fluid">
      <div class="col-md-12">
          <section class="panel panel-default">
              <div class="panel-heading"  style="background-color: #4B7C88">
                <h3 class="panel-title" id="reportTypeTitle">${crossTab.tableTitle}</h3>
            </div>
              <div class="panel-body">
                <div class="form-container">

                           
                    </div>

          </div>
       </section>
    </div>  
 </div>
 </c:forEach>
</div>