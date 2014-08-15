<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<div class="main clearfix" role="main">
    <div class="row-fluid">
        <div class="col-md-12">
            <c:choose>
                <c:when test="${not empty param.msg}" >
                    <div class="alert alert-success">
                        <strong>Success!</strong> 
                        <c:choose>
                            <c:when test="${param.msg == 'updated'}">The program admin user has been successfully updated!</c:when>
                            <c:when test="${param.msg == 'created'}">The program admin user has been successfully added!</c:when>
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
                        <a href="#adminDetailsModal" data-toggle="modal" class="btn btn-primary btn-xs btn-action" id="createNewAdmin" title="Create New Algorithm">Create New Admin</a>
                    </div>
                    <h3 class="panel-title">Admins</h3>
                </div>
                <div class="panel-body">
                    <div class="form-container scrollable"><br />
                        <table class="table table-striped table-hover table-default" <c:if test="${not empty programAdministrators}">id="dataTable"</c:if>>
                                <thead>
                                    <tr>
                                        <th scope="col">Name</th>
                                        <th scope="col" class="center-text">Status</th>
                                        <th scope="col" class="center-text">Times logged into Program</th>
                                        <th scope="col" class="center-text">Date Created</th>
                                        <th scope="col"></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <c:choose>
                                    <c:when test="${not empty programAdministrators}">
                                        <c:forEach var="admin" items="${programAdministrators}">
                                            <tr>
                                                <td>
                                                    ${admin.firstName}&nbsp;${admin.lastName}
                                                </td>
                                                <td class="center-text">
                                                    <c:choose><c:when test="${admin.status == true}">Active</c:when><c:otherwise>Inactive</c:otherwise></c:choose>
                                                </td>
                                                <td class="center-text">
                                                   10
                                                </td>
                                                <td class="center-text"><fmt:formatDate value="${admin.dateCreated}" type="date" pattern="M/dd/yyyy" /></td>
                                                <td class="actions-col">
                                                    <a href="#adminDetailsModal" data-toggle="modal" rel="${admin.id}" class="btn btn-link editAdmin" title="Edit this admin" role="button">
                                                        <span class="glyphicon glyphicon-edit"></span>
                                                        Edit
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <tr><td colspan="7" class="center-text">There are currently no program admins set up.</td></tr>
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
<div class="modal fade" id="adminDetailsModal" role="dialog" tabindex="-1" aria-labeledby="Add Program Admin" aria-hidden="true" aria-describedby="Add Program Admin"></div>

