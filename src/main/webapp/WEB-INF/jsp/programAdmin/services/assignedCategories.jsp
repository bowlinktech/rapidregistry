<%-- 
    Document   : associatedPrograms
    Created on : Nov 4, 2014, 2:56:04 PM
    Author     : chadmccue
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<table class="table table-striped table-hover responsive">
    <thead>
        <tr>
            <th scope="col">Category</th>
            <th scope="col"></th>
        </tr>
    </thead>
    <tbody>
        <c:choose>
            <c:when test="${not empty categories}">
                <c:forEach var="category" items="${categories}">
                    <tr>
                        <td>
                            ${category.categoryName}
                        </td>
                        <td class="center-text">
                            <a href="javascript:void(0)" class="btn-link removeCategory" rel="${category.serviceCategoryId}">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr><td colspan="2">There are no assigned categories for this service.</td></tr>
            </c:otherwise>
        </c:choose>
    </tbody>
</table>
