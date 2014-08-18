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
                            <c:when test="${param.msg == 'updated'}">The program admin user has been successfully updated!</c:when>
                            <c:when test="${param.msg == 'created'}">The program admin user has been successfully added!</c:when>
                            <c:when test="${param.msg == 'associated'}">The selected admin is now associated to this program!</c:when>
                            <c:when test="${param.msg == 'removed'}">The selected admin is now removed from this program!</c:when>
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
                        <a href="#fieldModal" data-toggle="modal" class="btn btn-primary btn-xs btn-action" id="createNewField" title="Create New Field">Create New Field</a>
                    </div>
                    <h3 class="panel-title">Data Elements</h3>
                </div>
                <div class="panel-body">
                    <div class="form-container scrollable"><br />
                        <table class="table table-striped table-hover table-default" <c:if test="${not empty availableFields}">id="dataTable"</c:if>>
                                <thead>
                                    <tr>
                                        <th scope="col">Field Name</th>
                                        <th scope="col" class="center-text">Status</th>
                                        <th scope="col">Table Name</th>
                                        <th scope="col">Table Column</th>
                                        <th scope="col"></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <c:choose>
                                    <c:when test="${not empty availableFields}">
                                        <c:forEach var="field" items="${availableFields}">
                                            <tr>
                                                <td>
                                                    <a href="#fieldModal" data-toggle="modal" rel="${field.id}" class="btn btn-link editField" title="Edit this field" role="button">${field.elementName}</a>
                                                </td>
                                                <td class="center-text">
                                                    <c:choose><c:when test="${field.status == true}">Active</c:when><c:otherwise>Inactive</c:otherwise></c:choose>
                                                </td>
                                                <td>
                                                    ${field.saveToTableName}
                                                </td>
                                                <td>
                                                    ${field.saveToTableCol}
                                                </td>
                                                <td class="actions-col">
                                                    <a href="#fieldModal" data-toggle="modal" rel="${field.id}" class="btn btn-link editField" title="Edit this field" role="button">
                                                        <span class="glyphicon glyphicon-edit"></span>
                                                        Edit
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <tr><td colspan="7" class="center-text">There are currently no fields set up.</td></tr>
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
<div class="modal fade" id="fieldModal" role="dialog" tabindex="-1" aria-labeledby="Add Field Element" aria-hidden="true" aria-describedby="Add Field Element"></div>

