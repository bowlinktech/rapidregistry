<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="main clearfix" role="main">
    <div class="col-md-12">
	<c:if test="${not empty errorCodes}" >
                <div class="alert alert-danger">
                    <strong>The last file uploaded failed our validation!</strong> 
                    <br />
                    <c:forEach items="${errorCodes}" var="code">
                        ${code.errorDisplayText}
                        <br />
                    </c:forEach>
                </div>
        </c:if>
        <c:if test="${not empty savedStatus}" >
                     <div class="alert alert-success">
                        <strong>Success!</strong> 
                        Your file is successfully uploaded.
                       </div>             
         </c:if>
        <section class="panel panel-default">
            <div class="panel-heading">
                <div class="pull-right">
                    <a href="#activityCodeCategoryModal" data-toggle="modal" class="btn btn-primary btn-xs btn-action" id="createNewCategory" title="Create New Category">Create New Category</a>
                </div>
                <h3 class="panel-title">Import File as User</h3>
            </div>
            <div class="panel-body">
			<div class="form-container">
			<form:form id="fileUploadForm" action="submitImportFile" enctype="multipart/form-data" method="post" role="form">
                <div id="userIdDiv" class="form-group ${status.error ? 'has-error' : '' }">
                    <label for="userId">Select the user you are uploading for *</label>
                    <select id="userId" name="userId" class="form-control half userId">
                        <option  value="">- Users - </option>
                        <c:forEach items="${users}" var="user">
                            <option value="${user.id}" <c:if test="${puUserId == user.id}">selected</c:if>>${user.firstName} ${user.lastName} - ${user.email}</option>
                        </c:forEach>                        
                    </select>
                    <span id="userIdMsg" class="control-label"></span>
                </div>
                
                <div id="programUploadTypeIdDiv" class="form-group ${status.error ? 'has-error' : '' }" <c:if test="${empty programUploadTypeId}">style="display:none"</c:if>>
                    <label for="programUploadTypeId">Select the program you are uploading for *</label>
                    <select id="programUploadTypeId" name="programUplaodTypeId" class="form-control half">
                        <option  value="">- Program Upload Types - </option>
                        <c:forEach items="${programUploadTypes}" var="programUploadType">
                            <option value="${programUploadType.id}" <c:if test="${programUploadTypeId == programUploadType.id}">selected</c:if>>${programUploadType.name}</option>
                        </c:forEach>                        
                    </select>
                    <span id="programUploadTypeIdMsg" class="control-label"></span>
                </div>
                
                <div id="uploadedFileDiv" class="form-group ${status.error ? 'has-error' : '' }">
                    <label for="uploadedFile">File *</label>
                    <input id="uploadedFile" name="uploadedFile" class="form-control" type="file" />
                    <span id="uploadedFileMsg" class="control-label"></span>
                </div>

                <div class="form-group">
                    <input class="btn btn-primary btn-sm" id="submitButton" type="submit" value="Upload"/>
                    <a data-dismiss="modal" class="btn btn-secondary btn-sm" data-toggle="tab">
                        Cancel
                    </a>
                </div>
           </form:form>			
			</div>
            </div>
        </section>
    </div>
</div>
                                    
<!-- admin functions modal -->
<div class="modal fade" id="adminFnsModal" role="dialog" tabindex="-1" aria-labeledby="Add Activity Code" aria-hidden="true" aria-describedby="Add Activity Code"></div>
