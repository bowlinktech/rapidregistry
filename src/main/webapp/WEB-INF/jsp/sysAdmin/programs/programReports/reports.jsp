<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="main clearfix" role="main">
    <div class="row-fluid">
        <div class="col-md-12">
            <c:if test="${not empty savedStatus}" >
                <div class="alert alert-success" role="alert">
                    <strong>Success!</strong> 
                    <c:choose>
                        <c:when test="${savedStatus == 'updatedpatientsharing'}">The patient sharing has been successfully updated.</c:when>
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
  <form:form id="programReports"  method="post" role="form">
       <input type="hidden" id="action" name="action" value="save" />
       <input type="hidden" id="reportIds" name="reportIds" />
  </form:form>
  <div class="row-fluid">
      <div class="col-md-12">
          <section class="panel panel-default">
              <div class="panel-body">
                <div class="form-container scrollable">
                    <p>
                        Below is a list of available canned reports that can be associated to this program. Mark off the reports in which <strong>${programDetails.programName}</strong> users have access to.
                    </p>
                   <table class="table table-striped table-hover table-default">
                        <thead>
                            <tr>
                                <th scope="col" class="center-text" style="width:10%">Use</th>
                                <th scope="col">Report Name</th>
                                <th scope="col">Report Desc</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty availReports}">
                                    <c:forEach var="report" items="${availReports}">
                                        <tr>
                                            <td scope="row" class="center-text">
                                                <input type="checkbox" name="reportId" value="${report.id}" ${report.useReport == true ? 'checked="true"' : ''} />
                                            </td>
                                            <td>
                                                ${report.reportName}
                                            </td>
                                            <td>
                                                ${report.reportDesc}
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr><td colspan="7" class="center-text">There are currently no reports set up to associate this program with.</td></tr>
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