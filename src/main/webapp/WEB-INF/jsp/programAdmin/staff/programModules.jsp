 <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%-- 
    Document   : programModules
    Created on : Nov 4, 2014, 4:38:34 PM
    Author     : chadmccue
--%>

<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h3 class="panel-title">Selected Modules</h3>
         </div>
         <div class="modal-body">
             <form id="moduleForm" method="post" role="form">
                 <input type="hidden" name="i" value="${userId}" />
                 <input type="hidden" name="v" value="${v}" />
                 <input type="hidden" id="encryptedURL" value="${encryptedURL}" />
                 <div class="form-group">
                    <div id="tableNameDiv" class="form-group ${status.error ? 'has-error' : '' }">
                        <label class="control-label" for="tableName">Available Program Modules</label>
                        <select name="programModules" class="form-control" multiple="true">
                            <c:forEach var="module" items="${programModules}">
                                <option value="${module.moduleId}" <c:if test="${module.useModule == true}">selected</c:if>>${module.displayName}</option>
                            </c:forEach>
                        </select>     
                    </div>
                </div>
                <div class="form-group">
                    <input type="button" id="submitModuleButton" role="button" class="btn btn-primary" value="Save"/>
                </div>
            </form>
         </div>
    </div>
</div>
