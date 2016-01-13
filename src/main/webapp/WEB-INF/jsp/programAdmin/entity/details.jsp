<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="main clearfix" role="main">

    <div class="col-md-12">
        <c:if test="${not empty savedStatus}" >
            <div class="alert alert-success" role="alert">
                <strong>Success!</strong> 
                The entity item has been successfully updated!
            </div>
        </c:if>
        <div class="alert alert-success assocSuccess" style="display:none"></div>
    </div>

    <div class="col-md-6">
        <section class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">Entity Item Details</h3>
            </div>
            <div class="panel-body">
                <form:form id="hierarchyItemdetailsform" commandName="hierarchyDetails" method="post" role="form">
                    <input type="hidden" name="action" id="action"  />
                    <form:hidden path="id" id="id" />
                    <form:hidden path="programHierarchyId" />
                    <form:hidden path="dateCreated" />
                    <input type="hidden" name="selActivityCodes" id="selActivityCodes" value="" />
                    <div class="form-container">
                        <div class="form-group">
                            <label for="status">Status *</label>
                            <div>
                                <label class="radio-inline">
                                    <form:radiobutton id="status" path="status" value="true" /> Active
                                </label>
                                <label class="radio-inline">
                                    <form:radiobutton id="status" path="status" value="false" /> Inactive
                                </label>
                            </div>
                        </div>
                        <spring:bind path="name">
                            <div class="form-group ${status.error ? 'has-error' : '' }">
                                <label class="control-label" for="name">Name *</label>
                                <form:input path="name" id="name" class="form-control" type="text" maxLength="255" />
                                <form:errors path="name" cssClass="control-label" element="label" />
                            </div>
                        </spring:bind>
                        <spring:bind path="address">
                            <div class="form-group ${status.error ? 'has-error' : '' }">
                                <label class="control-label" for="address">Address</label>
                                <form:input path="address" id="address" class="form-control" type="text" maxLength="255" />
                                <form:errors path="address" cssClass="control-label" element="label" />
                            </div>
                        </spring:bind>
                        <spring:bind path="address2">
                            <div class="form-group ${status.error ? 'has-error' : '' }">
                                <label class="control-label" for="address2">Address 2</label>
                                <form:input path="address2" id="address2" class="form-control" type="text"  maxLength="255" />
                                <form:errors path="address2" cssClass="control-label" element="label" />
                            </div>
                        </spring:bind>
                        <spring:bind path="city">
                            <div class="form-group ${status.error ? 'has-error' : '' }">
                                <label class="control-label" for="city">City</label>
                                <form:input path="city" id="city" class="form-control" type="text"  maxLength="255" />
                                <form:errors path="city" cssClass="control-label" element="label" />
                            </div>
                        </spring:bind>
                        <spring:bind path="state">
                            <div class="form-group ${status.error ? 'has-error' : '' }">
                                <label class="control-label" for="state">State</label>
                                <form:select id="state" path="state" cssClass="form-control half">
                                    <option value="" label=" - Select - " ></option>
                                    <form:options items="${stateList}"/>
                                </form:select>
                                <form:errors path="state" cssClass="control-label" element="label" />
                            </div>
                        </spring:bind>
                        <spring:bind path="zipCode">
                            <div class="form-group ${status.error ? 'has-error' : '' }">
                                <label class="control-label" for="zipcode">Zip Code</label>
                                <form:input path="zipCode" id="zipcode" class="form-control" type="text"  maxLength="15" />
                                <form:errors path="zipCode" cssClass="control-label" element="label" />
                            </div>
                        </spring:bind>      
                        <spring:bind path="phoneNumber">
                            <div class="form-group ${status.error ? 'has-error' : '' }">
                                <label class="control-label" for="phoneNumber">Phone Number</label>
                                <form:input path="phoneNumber" id="phoneNumber" class="form-control" type="text"  maxLength="255" />
                                <form:errors path="phoneNumber" cssClass="control-label" element="label" />
                            </div>
                        </spring:bind>
                       <spring:bind path="displayId">
                        <div class="form-group ${status.error ? 'has-error' : '' }">
                            <label class="control-label" for="displayId">Display Id *</label>
                            <form:input path="displayId" id="displayId" class="form-control" type="text"  maxLength="45" />
                            <form:errors path="displayId" cssClass="control-label" element="label" />
                        </div>
                    </spring:bind>         
                    </div>
                </form:form>
            </div>
        </section>
    </div>
    <div class="col-md-6">
        <section class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">Associated To</h3>
            </div>
            <div class="panel-body">
                <div style="height:250px; overflow: auto">
                    <ul class="list-group">
                        <c:if test="${not empty entityItems}">
                            <c:forEach var="entityItem" items="${entityItems}">
                                <li class="list-group-item">
                                    <div class="input-group">
                                        <span class="input-group-addon">
                                            <input type="radio" name="entityItem" class="entitySelect" entityId="${entityItem.id}" <c:if test="${entityItem.isAssociated == true}">checked="checked"</c:if> />
                                        </span>
                                        <input type="text" class="form-control" value="${entityItem.name}" readonly="true" style="font-weight:bold">
                                    </div>
                                </li>
                            </c:forEach> 
                        </c:if>
                    </ul>
                </div>
            </div>
        </section>    
        <section class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">Associated Code Sets</h3>
            </div>
            <div class="panel-body">
                <select multiple="" class="form-control" id="form-field-select-4">
                    <c:forEach items="${availActivityCodes}" var="activityCode">
                        <option value="${activityCode.id}" <c:if test="${activityCode.selected == true}">selected</c:if>>${activityCode.code} - ${activityCode.codeDesc}</option>
                    </c:forEach>
                </select>
            </div>
        </section>
    </div>
</div>

<!-- Program Entry Method modal -->
<div class="modal fade" id="newAssocModal" role="dialog" tabindex="-1" aria-labeledby="Modified By" aria-hidden="true" aria-describedby="Modified By"></div>
