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
    <div class="modal-content"   style="min-width:800px;">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h3 class="panel-title">Available Program Modules</h3>
         </div>
         <div class="modal-body">
             <form id="moduleForm" method="post" role="form">
                 <input type="hidden" name="i" value="${userId}" />
                 <input type="hidden" name="v" value="${v}" />
                 <input type="hidden" id="encryptedURL" value="${encryptedURL}" />
                 <input type="hidden" name="selProgramModules" id="selProgramModules" value="" />
                 <div class="form-group">
                    <table class="table table-striped table-hover responsive">
                        <thead>
                            <tr>
                                <th scope="col"></th>
                                <th scope="col" class="center-text">Allow Access</th>
                                <th scope="col" class="center-text">Create</th>
                                <th scope="col" class="center-text">Edit</th>
                                <th scope="col" class="center-text">Delete</th>
                                <th scope="col" class="center-text">Level 1</th>
                                <th scope="col" class="center-text">Level 2</th>
                                <th scope="col" class="center-text">Level 3</th>
                                <th scope="col" class="center-text">Reconcile</th>
                                <th scope="col" class="center-text">Import</th>
                                <th scope="col" class="center-text">Export</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="module" items="${programModules}">
                                <tr>
                                    <td>${module.displayName}</td>
                                    <td class="center-text"><input class="programModules" name="programModules" type="checkbox" value="${module.moduleId}" <c:if test="${module.useModule == true}">checked="checked"</c:if> /></td>
                                    <td class="center-text"><input name="create_${module.moduleId}" type="checkbox" value="1" <c:if test="${module.allowCreate == true}">checked="checked"</c:if> ${module.moduleId == 3 || module.moduleId == 2 || module.moduleId == 4 ? 'disabled="disabled"' : ''} /></td>
                                    <td class="center-text"><input name="edit_${module.moduleId}" type="checkbox" value="1" <c:if test="${module.allowEdit == true}">checked="checked"</c:if> ${module.moduleId == 3 || module.moduleId == 2 || module.moduleId == 4 ? 'disabled="disabled"' : ''} /></td>
                                    <td class="center-text"><input name="delete_${module.moduleId}" type="checkbox" value="1" <c:if test="${module.allowDelete == true}">checked="checked"</c:if> ${module.moduleId == 3 || module.moduleId == 2 || module.moduleId == 4 ? 'disabled="disabled"' : ''} /></td>
                                    <td class="center-text"><input name="level1_${module.moduleId}" type="checkbox" value="1" <c:if test="${module.allowLevel1 == true}">checked="checked"</c:if> ${module.moduleId == 3 ? '' : 'disabled="disabled"'} /></td>
                                    <td class="center-text"><input name="level2_${module.moduleId}" type="checkbox" value="1" <c:if test="${module.allowLevel2 == true}">checked="checked"</c:if> ${module.moduleId == 3 ? '' : 'disabled="disabled"'} /></td>
                                    <td class="center-text"><input name="level3_${module.moduleId}" type="checkbox" value="1" <c:if test="${module.allowLevel3 == true}">checked="checked"</c:if> ${module.moduleId == 3 ? '' : 'disabled="disabled"'} /></td>
                                    <td class="center-text"><input name="reconcile_${module.moduleId}" type="checkbox" value="1" <c:if test="${module.allowReconcile == true}">checked="checked"</c:if> ${module.moduleId == 2 ? '' : 'disabled="disabled"'} /></td>
                                    <td class="center-text"><input name="import_${module.moduleId}" type="checkbox" value="1" <c:if test="${module.allowImport == true}">checked="checked"</c:if> ${module.moduleId == 4 ? '' : 'disabled="disabled"'} /></td>
                                    <td class="center-text"><input name="export_${module.moduleId}" type="checkbox" value="1" <c:if test="${module.allowExport == true}">checked="checked"</c:if> ${module.moduleId == 4 ? '' : 'disabled="disabled"'} /></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="form-group">
                    <input type="button" id="submitModuleButton" role="button" class="btn btn-primary" value="Save"/>
                </div>
            </form>
         </div>
    </div>
</div>
