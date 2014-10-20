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
                            <c:when test="${param.msg == 'hierarchysaved'}">The program organization hierarchy has been successfully updated!</c:when>
                        </c:choose>
                    </div>
                </c:when>
            </c:choose>
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
                    <div class="pull-right">
                        <a href="#hierarchyDetailsModal" data-toggle="modal" class="btn btn-primary btn-xs btn-action" id="createNewHierarchy" title="Create New Program Organization Hierarchy">Create New Hierarchy</a>
                    </div>
                    <h3 class="panel-title">Organization Hierarchy</h3>
                </div>
                <div class="panel-body">
                    <div class="form-container scrollable"><br />
                        <table class="table table-striped table-hover table-default" <c:if test="${not empty orgHierarchyList}">id="dataTable"</c:if>>
                                <thead>
                                    <tr>
                                        <th scope="col">Name</th>
                                        <th scope="col" class="center-text">Display Position</th>
                                        <th scope="col" class="center-text">Date Created</th>
                                        <th scope="col"></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <c:choose>
                                    <c:when test="${not empty orgHierarchyList}">
                                        <c:forEach var="hierarchy" items="${orgHierarchyList}">
                                            <tr>
                                                <td>
                                                    ${hierarchy.name}
                                                </td>
                                                <td class="center-text">
                                                     ${hierarchy.dspPos}
                                                </td>
                                                <td class="center-text"><fmt:formatDate value="${hierarchy.dateCreated}" type="date" pattern="M/dd/yyyy" /></td>
                                                <td class="actions-col">
                                                    <a href="#hierarchyDetailsModal" data-toggle="modal" rel="${hierarchy.id}" class="btn btn-link editHierarchy" title="Edit this Program Organization Hierarchy" role="button">
                                                        <span class="glyphicon glyphicon-edit"></span>
                                                        Edit
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <tr><td colspan="7" class="center-text">There are currently no program organization hierarchy set up.</td></tr>
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



<!-- Algorithm Details modal -->
<div class="modal fade" id="hierarchyDetailsModal" role="dialog" tabindex="-1" aria-labeledby="Add Program Organization Hierarchy" aria-hidden="true" aria-describedby="Add Program Organization Hierarchy"></div>

