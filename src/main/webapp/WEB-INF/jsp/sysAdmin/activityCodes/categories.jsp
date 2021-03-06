<%-- 
    Document   : providerAssocItems
    Created on : Dec 30, 2014, 1:28:03 PM
    Author     : chadmccue
--%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
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
                <c:forEach items="${categories}" var="category" varStatus="iStatus">
                    <tr>
                        <td scope="row">
                            ${category.category}
                        </td>
                        <td class="center-text">
                            <a href="javascript:void(0)" class="btn btn-link deleteCategory" rel="${category.id}" title="Remove this Category">
                                <span class="glyphicon glyphicon-trash"></span>
                                Delete
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise><tr><td scope="row" colspan="2" style="text-align:center">There are no categories associated to this activity code.</td></c:otherwise>
            </c:choose>
    </tbody>
</table>
