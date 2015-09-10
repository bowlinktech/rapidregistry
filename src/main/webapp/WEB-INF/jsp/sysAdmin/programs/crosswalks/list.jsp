<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<div class="main clearfix" role="main">
    <div class="row-fluid">
        <div class="col-md-12">
            <c:choose>
                <c:when test="${not empty param.msg}" >
                    <div class="alert alert-success">
                        <strong>Success!</strong> 
                        <c:choose>
                            <c:when test="${param.msg == 'created'}">The crosswalk has been successfully added!</c:when>
                        </c:choose>
                    </div>
                </c:when>
            </c:choose>
        </div>
    </div>
    <div class="row-fluid">
        <div class="col-md-12">
            <section class="panel panel-default">
                <div class="panel-heading">
                    <div class="pull-right">
                        <a href="#crosswalkModal" data-toggle="modal" class="btn btn-primary btn-xs btn-action" id="createNewCrosswalk" title="Add New Crosswalk">Add New Crosswalk</a>
                    </div>
                    <h3 class="panel-title">Crosswalks</h3>
                </div>
                <div class="panel-body">
                    <div class="form-container scrollable"><br />
                        <table class="table table-striped table-hover table-default" <c:if test="${not empty crosswalks}">id="dataTable"</c:if>>
                                <thead>
                                    <tr>
                                        <th scope="col">Name</th>
                                        <th scope="col" class="center-text">Date Created</th>
                                        <th scope="col"></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <c:choose>
                                    <c:when test="${not empty crosswalks}">
                                        <c:forEach items="${crosswalks}" var="crosswalk" varStatus="pStatus">
                                            <tr>
                                                <td scope="row">
                                                    ${crosswalks[pStatus.index].name} <c:choose><c:when test="${crosswalks[pStatus.index].programId == 0}"> (generic)</c:when><c:otherwise> (Program Specific)</c:otherwise></c:choose>
                                                </td>
                                                <td class="center-text"><fmt:formatDate value="${crosswalks[pStatus.index].dateCreated}" type="date" pattern="M/dd/yyyy" /></td>
                                                <td class="center-text">
                                                    <a href="#crosswalkModal" data-toggle="modal" class="btn btn-link viewCrosswalk" rel="?i=${crosswalks[pStatus.index].id}" title="View this Crosswalk">
                                                        <span class="glyphicon glyphicon-edit"></span>
                                                        View
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <tr><td colspan="7" class="center-text">There are currently no crosswalks set up for this registry.</td></tr>
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
<div class="modal fade" id="crosswalkModal" role="dialog" tabindex="-1" aria-labeledby="Message Crosswalks" aria-hidden="true" aria-describedby="Message Crosswalks"></div>

