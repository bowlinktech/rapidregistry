<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h3 class="panel-title"><c:choose><c:when test="${btnValue == 'Update'}">Update</c:when><c:when test="${btnValue == 'Create'}">Add</c:when></c:choose> Algorithm ${success}</h3>
        </div>
        <div class="modal-body">
            <form:form id="mcidetailsform" commandName="mcidetails" modelAttribute="mcidetails"  method="post" role="form">
                <form:hidden path="id" id="id" />
                <form:hidden path="programUploadTypeId" id="programUploadTypeId" />
                <form:hidden path="dateCreated" />
                <form:hidden path="processOrder" />
                <div class="form-container">
                    <div class="form-group">
                            <spring:bind path="categoryId">
                            <div id="categoryIdDiv" class="form-group ${status.error ? 'has-error' : '' }">
                                <label class="control-label" for="categoryId">Category *</label>
                                <form:select path="categoryId" id="categoryId" class="form-control half">
                                    <option value="" label=" - Select - " >- Select -</option>
                                    <c:forEach items="${categoryList}"  var="category">
                                        <option value="${category.id}" <c:if test="${category.id == mcidetails.categoryId}">selected</c:if>>${category.displayText}</option>
                                    </c:forEach>
                                </form:select>
                               <form:errors path="categoryId" cssClass="control-label" element="label" />  
                               <span id="categoryIdMsg" class="control-label"></span>    
                            </div>
                        </spring:bind>
                    </div>   
                   <div class="form-group">
                      <spring:bind path="action">
                            <div id="actionDiv" class="form-group ${status.error ? 'has-error' : '' }">
                                <label class="control-label" for="actionId">Action *</label>
                                <form:select path="action" id="action" class="form-control half">
                                    <option value="" label=" - Select - " >- Select -</option>
                                    <c:forEach items="${actionList}"  var="actionItem">
                                        <option value="${actionItem.id}" <c:if test="${actionItem.id == mcidetails.action}">selected</c:if>>${actionItem.displayText}</option>
                                    </c:forEach>
                                </form:select>
                               <form:errors path="action" cssClass="control-label" element="label" />  
                               <span id="actionMsg" class="control-label"></span>    
                            </div>
                        </spring:bind>   
                        <div class="form-group">
                        <div>
                            <label for="status">Status *</label>
                            <div>
                                <label class="radio-inline">
                                    <form:radiobutton id="status" path="status" value="true"/> Active
                                </label>
                                <label class="radio-inline">
                                    <form:radiobutton id="status" path="status" value="false"/> Inactive
                                </label>
                            </div>
                        </div>
                    </div>  
                                             
                   </div>   
                    <c:if test="${not empty selFields}">
                       <div class="form-group">
                        <section class="panel panel-default">
                             <div class="panel-heading">
                                 <h3 class="panel-title">Existing Fields</h3>
                             </div>
                             <div class="panel-body">
                                 <div class="form-container scrollable">
                                     <table class="table table-striped table-hover table-default" id="fieldTableExisting">
                                         <tbody>
                                             <c:forEach var="selField" items="${selFields}">
                                                 <tr id="row_${selField.id}">
                                                     <td>
                                                         ${selField.fieldName}
                                                     </td>
                                                     <td>
                                                         ${selField.action}
                                                     </td>
                                                     <td>
                                                        <a href="javascript:void(0);" rel="${selField.id}" class="btn btn-link removeAlgorithmField pull-right" title="Remove this field" role="button">
                                                          <span class="glyphicon glyphicon-remove"></span>
                                                          Delete
                                                        </a> 
                                                     </td>
                                                 </tr>
                                             </c:forEach>
                                         </tbody>
                                     </table>
                                 </div>
                             </div>
                        </section>
                    </div>   

                    </c:if>             
                   <div class="form-group">
                       <section class="panel panel-default">
                            <div class="panel-heading">
                                <div class="pull-right">
                                    <a href="#algorithmDetailsModal" data-toggle="modal" class="btn btn-primary btn-xs btn-action" id="addNewField" title="Add Another Field">Add Another Field</a>
                                </div>
                                <h3 class="panel-title"><c:if test="${btnValue == 'Update'}">Add New </c:if>Fields</h3>
                            </div>
                            <div class="panel-body">
                                <div class="form-container scrollable">
                                    <table class="table table-striped table-hover table-default" id="fieldTable">
                                        <tbody>
                                           <tr class="tr_clone">
                                                <td>
                                                    <select name="fieldIds" class="form-control xs-input" style="width:150px;">
                                                        <option value="">- Choose a Field -</option>
                                                        <c:forEach var="field"  items="${availableFields}">
                                                            <option value="${field.fieldId}">${field.fieldName}</option>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td>
                                                    <select name="fieldAction" class="form-control xs-input" style="width:150px;">
                                                        <option value="equals">Equals</option>
                                                        <option value="does not equal">Does not Equal</option>
                                                    </select>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                       </section>
                   </div>  
                    <div class="form-group">
                        <input type="button" id="submitButton" rel="${btnValue}" rel2="${mcidetails.programUploadTypeId}" role="button" class="btn btn-primary" value="${btnValue}"/>
                    </div>
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
