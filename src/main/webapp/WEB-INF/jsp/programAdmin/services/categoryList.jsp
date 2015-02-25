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
                        <c:when test="${param.msg == 'updated'}">The service category was successfully updated!</c:when>
                        <c:when test="${param.msg == 'created'}">The service category has been successfully added!</c:when>
                    </c:choose>
                </div>
            </c:when>
        </c:choose>
        
        
        <section class="panel panel-default">
            <div class="panel-heading">
                <div class="pull-right">
                    <a href="#serviceCategoryModal" data-toggle="modal" class="btn btn-primary btn-xs btn-action" id="createNewCategory" title="Create New Service Category">Create New Service Category</a>
                </div>
                <h3 class="panel-title">Service Categories</h3>
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
                                <c:when test="${not empty categories}">
                                    <c:forEach var="category" items="${categories}">
                                        <tr>
                                            <td>
                                                <a href="#serviceCategoryModal" data-toggle="modal" class="btn-link editCategory" rel="${category.id}" title="Edit this Category" role="button">${category.categoryName}
                                                <br />
                                                (<c:choose><c:when test="${category.status == true}">Active</c:when><c:otherwise>Inactive</c:otherwise></c:choose>)</a>
                                            </td>
                                            <td class="center-text"><fmt:formatDate value="${category.dateCreated}" type="date" pattern="M/dd/yyyy" /></td>
                                            <td class="actions-col">
                                                <a href="#serviceCategoryModal" data-toggle="modal" class="btn btn-link editCategory" rel="${category.id}" title="Edit this Category" role="button">
                                                    <span class="glyphicon glyphicon-edit"></span>
                                                    Edit
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr><td colspan="3" class="center-text">There are currently no service categories set up.</td></tr>
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
<div class="modal fade" id="serviceCategoryModal" role="dialog" tabindex="-1" aria-labeledby="" aria-hidden="true" aria-describedby=""></div>
