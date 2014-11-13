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
            <h3 class="panel-title">New Program Association</h3>
         </div>
         <div class="modal-body">
             <form id="newProgramForm" method="post" role="form">
                 <input type="hidden" name="i" value="${userId}" />
                 <input type="hidden" name="v" value="${v}" />
                 <input type="hidden" id="encryptedURL" value="${encryptedURL}" />
                 <input type="hidden" name="hierarchyValues" id="hierarchyValues" value="" />
                 <div class="form-group">
                    <div id="programDiv" class="form-group ${status.error ? 'has-error' : '' }">
                        <label class="control-label" for="program">Program *</label>
                        <select name="program" id="program" class="form-control program">
                            <option value="0">- Select Program -</option>
                            <c:forEach var="programs" items="${programs}">
                                <option value="${programs.id}">${programs.programName}</option>
                            </c:forEach>
                        </select>     
                    </div>
                </div>        
                <div class="form-group">        
                    <div id="programModulesDiv" style="display:none">
                        <label class="control-label" for="progamModules">Modules *</label>
                        <select id="progamModules" name="programModules" class="form-control" multiple="true"></select>
                    </div>
                </div>
                <div id="orgHierarchyDiv"></div>
                <div class="form-group">
                    <input type="button" id="submitProgramButton" role="button" class="btn btn-primary" value="Save"/>
                </div>
            </form>
         </div>
    </div>
</div>
