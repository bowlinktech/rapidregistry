<%-- 
    Document   : hierarchyAssocForm
    Created on : Dec 30, 2014, 11:59:10 AM
    Author     : chadmccue
--%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h3 class="panel-title">Available Categories</h3>
        </div>
        <div class="modal-body">
            <form id="assocItemForm" method="post" role="form">
                <div class="form-container">
                     <div class="form-group ${status.error ? 'has-error' : '' }">
                        <c:choose>
                            <c:when test="${not empty categories}">
                                <select name="selItems" id="selItems" class="form-control" multiple="true">
                                    <c:forEach items="${categories}" var="category">
                                        <option value="${category.id}">${category.category}</option>
                                    </c:forEach>
                                </select>
                            </c:when>
                            <c:otherwise>
                                <p>There are no available categories to select</p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <c:if test="${not empty categories}">
                    <div class="form-group">
                        <input type="button" id="submitItemAssoc" role="button" class="btn btn-primary" value="Save"/>
                    </div>  
                </c:if>
            </form>
        </div>
    </div>
</div>
