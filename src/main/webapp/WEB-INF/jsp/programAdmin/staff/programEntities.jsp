<%-- 
    Document   : programDepartments
    Created on : Nov 4, 2014, 4:38:41 PM
    Author     : chadmccue
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="outerLoop" value="${userEntities.size()/hierarchyHeadings.size()}" />
<c:set var="counter" value="0" />

<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h3 class="panel-title">${heading} Association</h3>
         </div>
         <div class="modal-body">
            <form id="newProgramEntityForm" method="post" role="form">
                 <input type="hidden" name="i" value="${userId}" />
                 <input type="hidden" name="v" value="${v}" />
                 <input type="hidden" name="program" value="${programId}" />
                 <input type="hidden" id="encryptedURL" value="${encryptedURL}" />
                 <input type="hidden" name="nextEntityId" value="${nextEntityId}" />
                 <input type="hidden" name="entityId" value="${entityId}" />
                 <input type="hidden" id="completed" value="${completed}" />
                 
                <div class="orgHierarchyDiv" style="padding-bottom:20px">
                    <select name="selectedEntityItems" class="form-control" multiple style="height:200px;">
                        <c:forEach var="entityItem" items="${entityItems}">
                            <option value="${entityItem.id}" <c:if test="${fn:contains(userEntityItems, entityItem.id)}">selected</c:if>>${entityItem.name}</option>
                        </c:forEach>
                    </select>
                </div>  
                <div class="form-group">
                    <c:choose>
                        <c:when test="${nextEntityId > 0}">
                            <input type="button" id="submitEntityButton" role="button" class="btn btn-primary" value="Next [${nextEntityName}]"/>
                        </c:when>
                        <c:otherwise>
                            <input type="button" id="submitEntityButton" role="button" class="btn btn-primary" value="Done"/>
                        </c:otherwise>
                    </c:choose>
                </div>
            </form>    
         </div>
    </div>
</div>
