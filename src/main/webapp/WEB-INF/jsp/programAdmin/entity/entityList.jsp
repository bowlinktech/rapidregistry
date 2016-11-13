<%-- 
    Document   : entityList
    Created on : Apr 22, 2015, 12:44:11 PM
    Author     : chadmccue
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="panel-heading">
    <div class="pull-right">
        <a href="#entityModal" data-toggle="modal" class="btn btn-primary btn-xs btn-action entityItemDetails" rel="${entityId}" dspPos="${entityDsp}" itemId="0" title="Create New ${entityName}">Create New ${entityName}</a>
    </div>
    <h3 class="panel-title">${entityName}</h3>
</div>
<div class="panel-body">
    <div class="form-container scrollable">
        <table class="table table-striped table-hover table-default" <c:if test="${not empty entityItems}">id="dataTable"</c:if>>
            <thead>
                <tr>
                    <th scope="col">Display Id - Name</th>
                    <th scope="col" class="center-text">Status</th>
                    <th scope="col" class="center-text">Date Created</th>
                    <th scope="col">Associated With</th>
                    <th scope="col" class="center-text"></th>
                </tr>
            </thead>
           <tbody>
            <c:choose>
                <c:when test="${not empty entityItems}">
                    <c:forEach var="entityItem" items="${entityItems}">
                        <tr id="${entityItem.id}">
                            <td>
                                <c:if test="${fn:length(entityItem.displayId) > 0}">${entityItem.displayId} - </c:if>${entityItem.name}
                            </td>
                            <td class="center-text">
                                <c:choose><c:when test="${entityItem.status == true}">Active</c:when><c:otherwise>Inactive</c:otherwise></c:choose>
                            </td>
                            <td class="center-text"><fmt:formatDate value="${entityItem.dateCreated}" type="date" pattern="M/dd/yyyy h:mm a" /></td>
                            <td>
                                <c:choose>
                                    <c:when test="${entityDsp == 1}">
                                        N/A
                                    </c:when>
                                    <c:otherwise>
                                        ${entityItem.associatedWith}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="actions-col">
                                <c:choose>
                                    <c:when test="${entityDsp == 1}">
                                        <a href="#entityModal" data-toggle="modal" rel="${entityId}" dspPos="${entityDsp}" itemId="${entityItem.id}" class="btn btn-link entityItemDetails" title="Edit this ${entityName}" role="button">
                                            Edit
                                            <span class="glyphicon glyphicon-edit"></span>
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="entity/details?i=${entityItem.encryptedId}&v=${entityItem.encryptedSecret}" class="btn btn-link" title="Edit this ${entityName}" role="button">
                                            Edit
                                            <span class="glyphicon glyphicon-edit"></span>
                                        </a>
                                    </c:otherwise>
                                </c:choose>
                                <a href="javascript:void(0);" class="btn btn-link deleteEntity" rel="${entityId}" dspPos="${entityDsp}" itemId="${entityItem.id}" title="Delete this ${entityName}" role="button">
                                    Delete
                                    <span class="glyphicon glyphicon-remove-circle"></span>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr><td colspan="8" class="center-text">There are currently no items set up for this ${entityName}.</td></tr>
                </c:otherwise>
            </c:choose>
            </tbody>
        </table>
    </div>
</div>

<script>

    $('#dataTable').dataTable({
    "sPaginationType": "bootstrap",
    "oLanguage": {
        "sSearch": "_INPUT_",
        "sLengthMenu": '<select class="form-control" style="width:150px">' +
                '<option value="10">10 Records</option>' +
                '<option value="20">20 Records</option>' +
                '<option value="30">30 Records</option>' +
                '<option value="40">40 Records</option>' +
                '<option value="50">50 Records</option>' +
                '<option value="-1">All</option>' +
                '</select>'
    }
});
</script>


