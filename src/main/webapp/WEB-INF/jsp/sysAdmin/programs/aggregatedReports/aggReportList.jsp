
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
                <%-- 
                <div class="pull-right">
                    <a href="#importModal" data-toggle="modal" class="btn btn-primary btn-xs btn-action" id="createNewReportType"  title="Add New Report Type">Add New Report Type</a>
                </div>
                --%>
                    <h3 class="panel-title">Narrow Reports by Report Type</h3>
                </div>
                <div class="panel-body">
                    <select class="form-control half changeRepType" rel="${sessionScope.programName}">
                        <option value="0">- Show All -</option>
                        <c:forEach var="reportType" items="${allAggRepTypes}">
                            <option value="${reportType.id}" <c:if test="${selRepType == reportType.id}">selected</c:if>>${reportType.reportType}</option>
                        </c:forEach>
                    </select>
              </div>
            </section>
        </div>
    </div>
<div class="row-fluid">
      <div class="col-md-12">
          <section class="panel panel-default">
              <div class="panel-heading">
              <%--
                <div class="pull-right">
                    <a href="#reportModal" data-toggle="modal" class="btn btn-primary btn-xs btn-action" id="createNewReport"  title="Add New Aggregated Report">Add New Aggregated Report</a>
                </div>
                 --%>
                <h3 class="panel-title" rel="${selRepType}" id="reportTypeTitle"><c:if test="${not empty rtDetails}">${rtDetails.reportType}</c:if><c:if test="${empty rtDetails}">All Aggregated Reports</c:if></h3>
            </div>
              <div class="panel-body">
                <div class="form-container scrollable">
                    <table class="table table-striped table-hover table-default" <c:if test="${not empty repList}">id="dataTable"</c:if>>
                        <thead>
                            <tr>
                                <th scope="col">Name</th>                                
                                <th scope="col" class="center-text">Status</th>
                                <th scope="col" class="center-text">Date Created</th>
                                <th scope="col" class="center-text"></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty repList}">
                                    <c:forEach var="report" items="${repList}">
                                        <tr>
                                            <td scope="row">
                                                ${report.reportName}
                                            </td>
                                            <td class="center-text">
                                                <c:choose><c:when test="${report.status == true}">Active</c:when><c:otherwise>Inactive</c:otherwise></c:choose>
                                            </td>
                                            <td class="center-text"><fmt:formatDate value="${report.dateCreated}" type="date" pattern="M/dd/yyyy" /></td>
                                            <td class="actions-col">
                                                <a href="aggregated-reports/reportDetails?r=${report.id}" class="btn btn-link editDetails" title="Edit this report">
                                                    <span class="glyphicon glyphicon-edit"></span>
                                                    Edit
                                                </a>                                               
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr><td colspan="4" class="center-text">There are currently no aggregated reports set up for this report type.</td></tr>
                                </c:otherwise>
                            </c:choose>
                       </tbody>
                 </table>
              </div>
          </div>
       </section>
    </div>
 </div>
</div>
