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
                        <c:when test="${param.msg == 'updated'}">The activity code category has been successfully updated!</c:when>
                        <c:when test="${param.msg == 'created'}">The activity code category has been successfully added!</c:when>
                    </c:choose>
                </div>
            </c:when>
        </c:choose>

        <section class="panel panel-default">
            <div class="panel-heading">
                <div class="pull-right">
                    <a href="#activityCodeCategoryModal" data-toggle="modal" class="btn btn-primary btn-xs btn-action" id="createNewCategory" title="Create New Category">Create New Category</a>
                </div>
                <h3 class="panel-title">Activity Code Categories</h3>
            </div>
            <div class="panel-body">

                <div class="form-container scrollable"><br />
                    <table class="table table-striped table-hover table-default" <c:if test="${not empty categories}">id="dataTable"</c:if>>
                        <thead>
                            <tr>
                                <th scope="col">Category ${result}</th>
                                <th scope="col"></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty categories}">
                                    <c:forEach var="category" items="${categories}">
                                        <tr>
                                            <td scope="row">
                                                <a href="#activityCodeCategoryModal" data-toggle="modal" rel="${category.id}" title="Edit this activity code category" class="editCategory">${category.category}</a>
                                            </td>
                                            <td class="actions-col">
                                                <a href="#activityCodeCategoryModal" data-toggle="modal" rel="${category.id}" class="btn btn-link editCategory" title="Edit this activity code category" role="button">
                                                    <span class="glyphicon glyphicon-edit"></span>
                                                    Edit
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr><td colspan="7" class="center-text">There are currently no activity code categories set up.</td></tr>
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
<div class="modal fade" id="activityCodeCategoryModal" role="dialog" tabindex="-1" aria-labeledby="Add Activity Code" aria-hidden="true" aria-describedby="Add Activity Code"></div>
