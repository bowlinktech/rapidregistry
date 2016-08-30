
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h3 class="panel-title">${modalTitle} ${success}</h3>
        </div>
        <div class="modal-body">
            <form:form id="importTypeForm" modelAttribute="importTypeDetails" method="post" role="form">
                <input type="hidden" id="action" name="action" value="save" />
                <input type="hidden" id="progamNameURL" value="${programName}" />
                <form:hidden path="id" id="id" /> 
                <form:hidden path="programId" />
                <form:hidden path="dateCreated" />
                 <div class="form-group">
                    <spring:bind path="status">
                        <div class="form-group ${status.error ? 'has-error' : '' }">
                            <label class="control-label" for="status">Status *</label>
                            <div>
                                <label class="radio-inline">
                                    <form:radiobutton id="status" path="status" value="true" /> Active
                                </label>
                                <label class="radio-inline">
                                    <form:radiobutton id="status" path="status" value="false" /> Inactive
                                </label>
                            </div>
                        </div>
                    </spring:bind> 
                    <!--  if it is a parent -->
                    <div class="form-group ${status.error ? 'has-error' : '' }">
                            <label class="control-label" for="useHEL">Is Parent Config *</label>
                            <div>
                                <label class="radio-inline">
                                    <input type="radio" id="isParent" value="true" onClick="isParent(true);" <c:if test="${importTypeDetails.isParent}">checked</c:if>/> Yes
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" id="isParent" path="useHEL" value="false" onClick="isParent(false);" <c:if test="${!importTypeDetails.isParent}">checked</c:if>/> No
                                </label>
                            </div>
                            </div>
                    <%-- if yes display paths --%>
                    <spring:bind path="name">
                        <div class="form-group ${status.error ? 'has-error' : '' }">
                            <label class="control-label" for="name">Name *</label>
                            <form:input path="name" id="name" class="form-control" type="text" maxLength="255" />
                            <form:errors path="name" cssClass="control-label" element="label" />
                        </div>
                    </spring:bind>
                    <spring:bind path="useHEL">
                        <div class="form-group ${status.error ? 'has-error' : '' }">
                            <label class="control-label" for="useHEL">Use Health-e-link *</label>
                            <div>
                                <label class="radio-inline">
                                    <form:radiobutton id="useHEL1" path="useHEL" value="true" onClick="helPaths(true);"/> Yes
                                </label>
                                <label class="radio-inline">
                                    <form:radiobutton id="useHEL2" path="useHEL" value="false" onClick="helPaths(false);" /> No
                                </label>
                            </div>
                            </div>
                      </spring:bind>
                      <div id="helPaths" <c:if test="${not importTypeDetails.useHEL}">style="display:none"</c:if>>
	                      <spring:bind path="helConfigId">
	                        	<div class="form-group ${status.error ? 'has-error' : '' }" id="helDropPathDiv">
		                            <label class="control-label" for="name">Health-e-link Config Id *</label>
		                            <form:input path="helConfigId" id="helConfigId" class="form-control" type="text" maxLength="6" />
		                            <span id="helConfigIdMsg" class="form-group control-label"></span>
	                        	</div>
	                   		</spring:bind>
                      
                      
                        <spring:bind path="helDropPath">
                        	<div class="form-group ${status.error ? 'has-error' : '' }" id="helDropPathDiv">
	                            <label class="control-label" for="name">Health-e-link Input Path *</label>
	                            <form:input path="helDropPath" id="helDropPath" class="form-control" type="text" maxLength="100" />
	                            <span id="helDropPathMsg" class="form-group control-label"></span>
                        	</div>
                   		</spring:bind>
	                    <spring:bind path="helPickUpPath">
	                        <div class="form-group ${status.error ? 'has-error' : '' }" id="helPickUpPathDiv">
	                            <label class="control-label" for="name">Health-e-link Output Path *</label>
	                            <form:input path="helPickUpPath" id="helPickUpPath" class="form-control" type="text" maxLength="100" />
	                             <span id="helPickUpPathMsg" class="form-group control-label"></span>
	                        </div>
	                    </spring:bind>
	                    <spring:bind path="outFileTypeId">
		                            <div class="form-group ${status.error ? 'has-error' : '' }" id="outFileTypeIdDiv">
		                                <label class="control-label" for="outFileTypeId">Output File Type</label>
		                                <form:select path="outFileTypeId" id="outFileTypeId" class="form-control half outFileTypeId">
                                <c:forEach items="${fileTypesList}" var="fileType">
                                    <option value="${fileType.id}" <c:if test="${fileType.id == importTypeDetails.outFileTypeId}">selected</c:if>>${fileType.fileType}</option>
                                </c:forEach>
                            </form:select>
                            <span id="outFileTypeIdMsg" class="form-group control-label"></span>
		                    </div>
		                </spring:bind> 
	                     </div>
	                      <spring:bind path="containsHeaderRow">
                        <div class="form-group ${status.error ? 'has-error' : '' }">
                            <label class="control-label" for="containsHeaderRow">Contain Header Row *</label>
                            <div>
                                <label class="radio-inline">
                                    <form:radiobutton id="containsHeaderRow1" path="containsHeaderRow" value="true" /> Yes
                                </label>
                                <label class="radio-inline">
                                    <form:radiobutton id="containsHeaderRow2" path="containsHeaderRow" value="false"/> No
                                </label>
                            </div>
                            </div>
                      </spring:bind>
	                    <spring:bind path="inFileTypeId">
		                            <div class="form-group ${status.error ? 'has-error' : '' }" >
		                                <label class="control-label" for="inFileTypeId">File Type*</label>
		                                <form:select path="inFileTypeId" id="inFileTypeId" class="form-control half inFileTypeId">
                                <c:forEach items="${fileTypesList}" var="fileType">
                                    <option value="${fileType.id}" <c:if test="${fileType.id == importTypeDetails.inFileTypeId}">selected</c:if>>${fileType.fileType}</option>
                                </c:forEach>
                            </form:select>
		                    </div>
		                </spring:bind> 
		                <spring:bind path="fileDelimId">
		                            <div class="form-group ${status.error ? 'has-error' : '' }">
		                                <label class="control-label" for="fileDelimId">File Delimiter*</label>
		                                <form:select path="fileDelimId" id="fileDelimId" class="form-control half fileDelimId">
                               <c:forEach items="${delimiters}" var="fileDelim" varStatus="dStatus">
                                        <option value="${delimiters[dStatus.index][0]}" <c:if test="${delimiters[dStatus.index][0] == importTypeDetails.fileDelimId}">selected</c:if>>${delimiters[dStatus.index][1]}</option>
                                    </c:forEach>
                            </form:select>
		                    </div>
		                </spring:bind> 
	                   <spring:bind path="maxFileSize">
                                    <div id="maxFileSizeDiv" class="form-group ${status.error ? 'has-error' : '' }">
                                        <label class="control-label" for="maxFileSize">Max File Size (mb) *</label>
                                        <form:input path="maxFileSize" id="maxFileSize" class="form-control" type="text" maxLength="11"/>
                                        <form:errors path="maxFileSize" cssClass="control-label" element="label" />
                                        <span id="maxFileSizeMsg" class="control-label"></span>                                    
                                    </div>
                        </spring:bind>
                        
                       
                    </div>
               
                <div class="form-group">
                    <input type="button" id="submitImportType" role="button" class="btn btn-primary" value="Save"/>
                </div>
            </form:form>
        </div>
    </div>
</div>

<script type="text/javascript">

    $(document).ready(function() {
        $("input:text,form").attr("autocomplete", "off");
    });

</script>