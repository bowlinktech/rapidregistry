<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="main clearfix" role="main">

    <div class="col-md-12">
        <c:if test="${not empty savedStatus}" >
            <div class="alert alert-success" role="alert">
                <strong>Success!</strong> 
                <c:choose><c:when test="${savedStatus == 'updated'}">The program has been successfully updated!</c:when><c:otherwise>The program has been successfully created!</c:otherwise></c:choose>
            </div>
        </c:if>
        <c:if test="${not empty param.msg}" >
            <div class="alert alert-success">
                <strong>Success!</strong> 
                <c:choose>
                    <c:when test="${param.msg == 'tablesaved'}">The table has been associated to this program!</c:when>
                    <c:when test="${param.msg == 'tabledeleted'}">The table association has been removed for this program!</c:when>
                    <c:when test="${param.msg == 'entrysaved'}">The patient entry method has been associated to this program!</c:when>
                    <c:when test="${param.msg == 'entrydeleted'}">The patient entry method has been removed for this program!</c:when>
                    <c:when test="${param.msg == 'fieldsaved'}">The patient search field has been associated to this program!</c:when>
                    <c:when test="${param.msg == 'fielddeleted'}">The patient search field has been removed for this program!</c:when>
                </c:choose>
            </div>
        </c:if>
    </div>

    <div class="col-md-6">
        <section class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">Details</h3>
            </div>
            <div class="panel-body">
                <div class="form-container">
                    <form:form id="staffdetails" commandName="staffdetails"  method="post" role="form">
                        <input type="hidden" id="action" name="action" value="save" />
                        <input type="hidden" name="userId" value="${userId}" />
                        <input type="hidden" name="v" value="${v}" />
                        <form:hidden path="roleId" />
                        <form:hidden path="createdBy" />
                        <form:hidden path="dateCreated" />
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
                        <spring:bind path="typeId">
                            <div id="typeIdDiv" class="form-group ${status.error ? 'has-error' : '' }">
                                <label class="control-label" for="typeId">Staff Type *</label>
                                <form:select path="typeId" id="typeId" class="form-control half">
                                    <option value="" label=" - Select - " >- Select -</option>
                                    <c:forEach items="${userTypes}"  varStatus="uname">
                                        <option value="${userTypes[uname.index][0]}" <c:if test="${staffdetails.typeId == userTypes[uname.index][0]}">selected</c:if>>${userTypes[uname.index][1]}</option>
                                    </c:forEach>
                                </form:select>
                               <form:errors path="typeId" cssClass="control-label" element="label" />      
                            </div>
                        </spring:bind>
                        <spring:bind path="firstName">
                            <div class="form-group ${status.error ? 'has-error' : '' }">
                                <label class="control-label" for="firstName">First Name *</label>
                                <form:input path="firstName" id="firstName" class="form-control" type="text" maxLength="55" />
                                <form:errors path="firstName" cssClass="control-label" element="label" />
                            </div>
                        </spring:bind>
                        <spring:bind path="lastName">
                            <div class="form-group ${status.error ? 'has-error' : '' }">
                                <label class="control-label" for="lastName">Last Name *</label>
                                <form:input path="lastName" id="lastName" class="form-control" type="text" maxLength="55" />
                                <form:errors path="lastName" cssClass="control-label" element="label" />
                            </div>
                        </spring:bind>
                        <spring:bind path="email">
                            <div class="form-group ${status.error ? 'has-error' : '' }">
                                <label class="control-label" for="email">Email</label>
                                <form:input path="email" id="email" class="form-control" type="text"  maxLength="255" />
                                <form:errors path="email" cssClass="control-label" element="label" />
                            </div>
                        </spring:bind>
                        <spring:bind path="password">
                            <div id="passwordDiv" class="form-group ${status.error ? 'has-error' : '' }">
                                <label class="control-label" for="password">Password *</label>
                                <form:input path="password" id="password" class="form-control" type="password" maxLength="15" autocomplete="off"  />
                                <form:errors path="password" cssClass="control-label" element="label" />
                            </div>
                        </spring:bind>
                        <div id="confirmPasswordDiv" class="form-group">
                            <label class="control-label" for="confirmPassword">Confirm Password *</label>
                            <input id="confirmPassword" name="confirmpassword" class="form-control" maxLength="15" autocomplete="off" type="password" value="${staffdetails.getPassword()}" />
                            <span id="confimPasswordMsg" class="control-label"></span>
                        </div>
                    </form:form>
                </div>
            </div>
        </section>
    </div>

    <div class="col-md-6">
        <section class="panel panel-default">
            <div class="panel-heading">
                <div class="pull-right">
                    <a href="#registryModal" data-toggle="modal" class="btn btn-primary btn-xs btn-action" id="addRegistry" title="Add A Registry">Add A Registry</a>
                </div>
                <h3 class="panel-title">Associated Registries</h3>
            </div>
            <div class="panel-body">
                <div class="form-container scrollable">
                    <table class="table table-striped table-hover responsive">
                        <thead>
                            <tr>
                                <th scope="col">Registry</th>
                                <th scope="col">Modules</th>
                                <th scope="col" class="center-text">Departments</th>
                                <th scope="col" class="center-text">Date Created</th>
                                <th scope="col"></th>
                            </tr>
                        </thead>
                        <tbody>
                            
                        </tbody>
                    </table>

                </div>
            </div>
        </section>

    </div>
</div>

<!-- Program Entry Method modal -->
<div class="modal fade" id="entryMethodModal" role="dialog" tabindex="-1" aria-labeledby="Add New Patient Entry Method" aria-hidden="true" aria-describedby="Add New Patient Entry Method"></div>
<div class="modal fade" id="surveyTableModal" role="dialog" tabindex="-1" aria-labeledby="Add New Table" aria-hidden="true" aria-describedby="Add New Table"></div>
<div class="modal fade" id="searchFieldsModal" role="dialog" tabindex="-1" aria-labeledby="Add New Search Field" aria-hidden="true" aria-describedby="Add New Search Field"></div>
<div class="modal fade" id="summaryFieldsModal" role="dialog" tabindex="-1" aria-labeledby="Add New Search Field" aria-hidden="true" aria-describedby="Add New Summary Field"></div>