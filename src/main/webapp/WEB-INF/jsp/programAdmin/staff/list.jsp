<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="main clearfix" role="main">
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
                <h3 class="panel-title">Search Users</h3>
            </div>
            <div class="panel-body">
                <form id="searchForm" action="" method="get">
                    <input type="hidden" id="clear" name="clear" value="" />
                    <div class="form-container">
                        <div class="row">
                            <div class="form-group col-md-6">
                                <div>
                                    <label class="control-label" for="firstName">First Name</label>
                                    <input type="text" name="firstname" value="${firstNameSF}" class="form-control" type="text" maxLength="55" />
                                </div>
                            </div>
                            <div class="form-group col-md-6">
                                <label for="status">Status</label>
                                <div class="form-group">
                                    <label class="radio-inline">
                                        <input type="radio" name="status" value="1" <c:if test="${statusSF == 1}">checked</c:if> /> Active
                                    </label>
                                    <label class="radio-inline">
                                        <input type="radio" name="status" value="0" <c:if test="${statusSF == 0}">checked</c:if> /> Inactive
                                    </label>
                                    <label class="radio-inline">
                                        <input type="radio" name="status" value="2" <c:if test="${statusSF == 2}">checked</c:if> /> Both
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group col-md-6">
                               <div>
                                    <label class="control-label" for="lastName">Last Name</label>
                                    <input type="text" name="lastName" value="${lastNameSF}" class="form-control" type="text" maxLength="55" />
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                          <input type="submit" id="submitButton"  role="button" class="btn btn-primary" value="Search"/>
                          <input type="button" id="clearButton"  role="button" class="btn btn-primary" value="Clear"/>
                       </div>
                    </div>
                </form>
            </div>
        </section>

        <section class="panel panel-default">
            <div class="panel-heading">
                <div class="pull-right">
                    <a href="#staffMemberModal" data-toggle="modal" class="btn btn-primary btn-xs btn-action" id="createNewStaffMember" title="Create New Staff Member">Create New User</a>
                </div>
                <h3 class="panel-title">Users</h3>
            </div>
            <div class="panel-body">

                <div class="form-container scrollable"><br />
                    <table class="table table-striped table-hover table-default" <c:if test="${not empty staffMembers}">id="dataTable"</c:if>>
                            <thead>
                                <tr>
                                    <th scope="col">Name</th>
                                    <th scope="col" class="center-text">Staff Type</th>
                                    <th scope="col" class="center-text">Date Created</th>
                                    <th scope="col" class="center-text">Last Logged In</th>
                                    <th scope="col">Programs</th>
                                    <th scope="col"></th>
                                </tr>
                            </thead>
                            <tbody>
                            <c:choose>
                                <c:when test="${not empty staffMembers}">
                                    <c:forEach var="staff" items="${staffMembers}">
                                        <tr>
                                            <td>
                                                <a href="staff/details?i=${staff.encryptedId}&v=${staff.encryptedSecret}" class="btn-link" title="Edit this Staff Member" role="button">${staff.firstName}&nbsp;${staff.lastName}
                                                <c:if test="${staff.email != ''}"><br />${staff.email}</c:if>
                                                <br />
                                                (<c:choose><c:when test="${staff.status == true}">Active</c:when><c:otherwise>Inactive</c:otherwise></c:choose>)</a>
                                            </td>
                                            <td class="center-text">
                                                ${staff.staffType}
                                            </td>
                                            <td class="center-text"><fmt:formatDate value="${staff.dateCreated}" type="date" pattern="M/dd/yyyy" /></td>
                                            <td class="center-text"><fmt:formatDate value="${staff.lastloggedIn}" type="Both" pattern="M/dd/yyyy h:mm a" /></td>
                                            <td>
                                                
                                            </td>
                                            <td class="actions-col">
                                                <a href="staff/details?i=${staff.encryptedId}&v=${staff.encryptedSecret}" class="btn btn-link editSysAdmin" title="Edit this Staff Member" role="button">
                                                    <span class="glyphicon glyphicon-edit"></span>
                                                    Edit
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr><td colspan="6" class="center-text">There are currently no staff members set up.</td></tr>
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
<div class="modal fade" id="staffMemberModal" role="dialog" tabindex="-1" aria-labeledby="Add Staff Member" aria-hidden="true" aria-describedby="Add Staff Member"></div>
