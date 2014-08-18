<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="main clearfix full-width" role="main">
    <div class="col-md-12">
        <c:choose>
            <c:when test="${not empty param.msg}" >
                <div class="alert alert-success">
                    <strong>Success!</strong> 
                    <c:choose>
                        <c:when test="${param.msg == 'updated'}">The report has been successfully updated!</c:when>
                        <c:when test="${param.msg == 'created'}">The report has been successfully added!</c:when>
                    </c:choose>
                </div>
            </c:when>
        </c:choose>

        <section class="panel panel-default">
            <div class="panel-heading">
                <div class="pull-right">
                    <a href="#reportModal" data-toggle="modal" class="btn btn-primary btn-xs btn-action" id="createNewReport" title="Create New Report">Create New Report</a>
                </div>
                <h3 class="panel-title">Canned Reports</h3>
            </div>
            <div class="panel-body">

                <div class="form-container scrollable"><br />
                    <table class="table table-striped table-hover table-default" <c:if test="${not empty reports}">id="dataTable"</c:if>>
                        <thead>
                            <tr>
                                <th scope="col">Report ${result}</th>
                                <th scope="col">Report Desc</th>
                                <th scope="col" class="center-text">Date Created</th>
                                <th scope="col"></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty reports}">
                                    <c:forEach var="report" items="${reports}">
                                        <tr>
                                            <td scope="row">
                                                <a href="#reportModal" data-toggle="modal" rel="${report.id}" title="Edit this report" class="editReport">${report.reportName}</a>
                                            </td>
                                            <td>
                                               ${report.reportDesc}
                                            </td>
                                            <td class="center-text"><fmt:formatDate value="${report.dateCreated}" type="date" pattern="M/dd/yyyy" /></td>
                                            <td class="actions-col">
                                                <a href="#reportModal" data-toggle="modal" rel="${report.id}" class="btn btn-link editReport" title="Edit this report" role="button">
                                                    <span class="glyphicon glyphicon-edit"></span>
                                                    Edit
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr><td colspan="7" class="center-text">There are currently no activity codes set up.</td></tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>
            </div>
        </section>
    </div>
</div>
                                    
<!-- Activity Code Details modal -->
<div class="modal fade" id="reportModal" role="dialog" tabindex="-1" aria-labeledby="Add Report" aria-hidden="true" aria-describedby="Add Report"></div>
