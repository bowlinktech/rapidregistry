<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<div class="main clearfix" role="main">
    <div class="col-md-12">

        <section class="panel panel-default">
            <div class="panel-heading">
                <div class="pull-right">
                    <a href="#activityCodeCategoryModal" data-toggle="modal" class="btn btn-primary btn-xs btn-action" id="createNewCategory" title="Create New Category">Create New Category</a>
                </div>
                <h3 class="panel-title">Import File as User</h3>
            </div>
            <div class="panel-body">
			<div class="form-container">
			 <div class="form-group">
                    <div id="userDiv" class="form-group">
                            <label class="control-label" for="saveToTableCol">Select a user</label>
                            <select  id="user" class="form-control half" rel="${users}">
                            <option value="" label=" - Select - " ></option>
                                <c:forEach var="user" items="${users}">
                                <option value="" label="${user.firstName} ${user.lastName} - ${user.email}" ></option>
                                </c:forEach>
                            </select>
                            <span id="userMsg" cssClass="control-label" element="label"></span>     
                    </div>
					<div id="programIdDiv" class="form-group">
                            <label class="control-label" for="saveToTableCol">Select a Program</label>
                            <select  id="user" class="form-control half" disabled>
                            <option value="" label=" - Select - " ></option>
                                <c:forEach var="user" items="${programs}">
                                <option value="" label="" ></option>
                                </c:forEach>
                            </select>
                            <span id="programMsg" cssClass="control-label" element="label"></span>     
                    </div>
                    <div id="brochureFileDiv" class="form-group">
                            <label class="control-label" for="brochureFile">File *</label>
                            <input id="importFile" type="file"  />
                            <span id="importFileMsg" class="control-label"></span>
                        </div>
                    
                </div>
			
			</div>
            </div>
        </section>
    </div>
</div>
                                    
<!-- admin functions modal -->
<div class="modal fade" id="adminFnsModal" role="dialog" tabindex="-1" aria-labeledby="Add Activity Code" aria-hidden="true" aria-describedby="Add Activity Code"></div>
