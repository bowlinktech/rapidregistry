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
                        <c:choose>
                            <c:when test="${code == 1}">- The file uploaded was empty.</c:when>
                            <c:when test="${code == 2}">- The file uploaded exceeded the max size.</c:when>
                            <c:when test="${code == 3}">- The file uploaded was not the correct file type associated to your selected message type.</c:when>
                            <c:when test="${code == 4}">- The file uploaded did not contain the correct delimiter.</c:when>
                            <c:when test="${code == 5}">- You tried to upload a file with multiple message types but your system is not configured for that.</c:when>
                        </c:choose>
                        <br />
                    </c:forEach>
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
                            <option value="${user.id}">${user.firstName} ${user.lastName} - ${user.email}</option>
                        </c:forEach>                        
                    </select>
                    <span id="userIdMsg" class="control-label"></span>
                </div>
                <div id="programUploadTypeIdDiv" class="form-group ${status.error ? 'has-error' : '' }" style="display:none">
                    <label for="programUploadTypeId">Select the program you are uploading for *</label>
                    <select id="programUploadTypeId" name="programUplaodTypeId" class="form-control half">
                        <option  value="">- Program Upload Types - </option>
                        <c:forEach items="${programUploadTypes}" var="programUploadType">
                            <option value="${programUploadType.id}">${programUploadType.name}</option>
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
