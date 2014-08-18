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
                        <c:when test="${param.msg == 'updated'}">The system administrator successfully updated!</c:when>
                        <c:when test="${param.msg == 'created'}">The system administrator has been successfully added!</c:when>
                    </c:choose>
                </div>
            </c:when>
        </c:choose>

        <section class="panel panel-default">
            <div class="panel-heading">
                <div class="pull-right">
                    <a href="#sysAdminModal" data-toggle="modal" class="btn btn-primary btn-xs btn-action" id="createNewAdmin" title="Create New System Administrator">Create New System Administrator</a>
                </div>
                <h3 class="panel-title">System Administrators</h3>
            </div>
            <div class="panel-body">

                <div class="form-container scrollable"><br />
                    <table class="table table-striped table-hover table-default" <c:if test="${not empty sysAdmins}">id="dataTable"</c:if>>
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
                                <c:when test="${not empty sysAdmins}">
                                    <c:forEach var="admin" items="${sysAdmins}">
                                        <tr>
                                            <td>
                                                <a href="#sysAdminModal" data-toggle="modal" rel="${admin.id}" class="btn btn-link editSysAdmin" title="Edit this admin" role="button">${admin.firstName}&nbsp;${admin.lastName}</a>
                                            </td>
                                            <td class="center-text">
                                                <c:choose><c:when test="${admin.status == true}">Active</c:when><c:otherwise>Inactive</c:otherwise></c:choose>
                                                    </td>
                                                    <td class="center-text">
                                                <c:choose>
                                                    <c:when test="${admin.timesloggedIn > 0}">
                                                        <fmt:formatNumber value="${admin.timesloggedIn}" />
                                                    </c:when>
                                                    <c:otherwise>0</c:otherwise>
                                                </c:choose>    
                                            </td>
                                            <td class="center-text"><fmt:formatDate value="${admin.dateCreated}" type="date" pattern="M/dd/yyyy" /></td>
                                            <td class="actions-col">
                                                <a href="#sysAdminModal" data-toggle="modal" rel="${admin.id}" class="btn btn-link editSysAdmin" title="Edit this admin" role="button">
                                                    <span class="glyphicon glyphicon-edit"></span>
                                                    Edit
                                                </a>
                                                <a href="javascript:void(0);" rel="${admin.id}" class="btn btn-link deleteSysAdmin" title="Remove this system administrator." role="button">
                                                    <span class="glyphicon glyphicon-remove"></span>
                                                    Delete
                                                </a>    
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr><td colspan="7" class="center-text">There are currently no system administrators set up.</td></tr>
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
<div class="modal fade" id="sysAdminModal" role="dialog" tabindex="-1" aria-labeledby="Add system administrator" aria-hidden="true" aria-describedby="Add system administrator"></div>
