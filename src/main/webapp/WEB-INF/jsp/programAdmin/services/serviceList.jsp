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
                        <c:when test="${param.msg == 'updated'}">The service was successfully updated!</c:when>
                        <c:when test="${param.msg == 'created'}">The service has been successfully added!</c:when>
                    </c:choose>
                </div>
            </c:when>
        </c:choose>
        
        
        <section class="panel panel-default">
            <div class="panel-heading">
                <div class="pull-right">
                    <a href="#serviceModal" data-toggle="modal" class="btn btn-primary btn-xs btn-action" id="createNewService" title="Create New Service">Create New Service</a>
                </div>
                <h3 class="panel-title">Services</h3>
            </div>
            <div class="panel-body">

                <div class="form-container scrollable"><br />
                    <table class="table table-striped table-hover table-default" <c:if test="${not empty categories}">id="dataTable"</c:if>>
                            <thead>
                                <tr>
                                    <th scope="col">Name</th>
                                    <th scope="col" class="center-text">Date Created</th>
                                    <th scope="col"></th>
                                </tr>
                            </thead>
                            <tbody>
                            <c:choose>
                                <c:when test="${not empty services}">
                                    <c:forEach var="service" items="${services}">
                                        <tr>
                                            <td>
                                                <a href="services/details?i=${service.encryptedId}&v=${service.encryptedSecret}" class="btn-link" title="Edit this Service" role="button">${service.serviceName}
                                                <br />
                                                (<c:choose><c:when test="${service.status == true}">Active</c:when><c:otherwise>Inactive</c:otherwise></c:choose>)</a>
                                            </td>
                                            <td class="center-text"><fmt:formatDate value="${service.dateCreated}" type="date" pattern="M/dd/yyyy" /></td>
                                            <td class="actions-col">
                                                <a href="services/details?i=${service.encryptedId}&v=${service.encryptedSecret}" class="btn btn-link" title="Edit this Service" role="button">
                                                    <span class="glyphicon glyphicon-edit"></span>
                                                    Edit
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr><td colspan="3" class="center-text">There are currently no services set up.</td></tr>
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
<div class="modal fade" id="serviceModal" role="dialog" tabindex="-1" aria-labeledby="" aria-hidden="true" aria-describedby=""></div>
