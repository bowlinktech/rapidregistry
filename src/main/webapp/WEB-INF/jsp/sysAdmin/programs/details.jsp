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
                    <form:form id="program" commandName="program"  method="post" role="form">
                        <input type="hidden" id="action" name="action" value="save" />
                        <form:hidden path="id" id="id" />
                        <form:hidden path="dateCreated" />
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
                        <spring:bind path="programName">
                            <div class="form-group ${status.error ? 'has-error' : '' } ${not empty existingProgram ? 'has-error' : ''}">
                                <label class="control-label" for="programName">Program Name *</label>
                                <form:input path="programName" id="programName" class="form-control" type="text" maxLength="55" />
                                <form:errors path="programName" cssClass="control-label" element="label" />
                                <c:if test="${not empty existingProgram}">${existingProgram}</c:if>
                                </div>
                        </spring:bind>
                        <spring:bind path="programDesc">
                            <div class="form-group ${status.error ? 'has-error' : '' }">
                                <label class="control-label" for="programDesc">Program Description</label>
                                <form:textarea path="programDesc" id="programDesc" class="form-control" rows="15" />
                            </div>
                        </spring:bind>  
                        <spring:bind path="emailAddress">
                            <div class="form-group ${status.error ? 'has-error' : '' }">
                                <label class="control-label" for="emailAddress">Program Email Address *</label>
                                <form:input path="emailAddress" id="emailAddress" class="form-control" type="text" maxLength="255" />
                                <form:errors path="emailAddress" cssClass="control-label" element="label" />
                                <h6>(This email address will be used in the FROM field when emails are sent from this program)</h6>
                            </div>
                        </spring:bind>
                        <spring:bind path="url">
                            <div class="form-group ${status.error ? 'has-error' : '' }">
                                <label class="control-label" for="url">Program URL</label>
                                <form:input path="url" id="url" class="form-control" type="text" maxLength="255" />
                                <form:errors path="url" cssClass="control-label" element="label" />
                                <h6>(This will be the URL to connect to this program)</h6>
                            </div>
                        </spring:bind>
                    </form:form>
                </div>
            </div>
        </section>
    </div>

    <div class="col-md-6">
        <section class="panel panel-default">
            <div class="panel-heading">
                <div class="pull-right">
                    <a href="#entryMethodModal" data-toggle="modal" class="btn btn-primary btn-xs btn-action" id="createNewEntryMethod" title="Add New Patient Entry Method">Add New Patient Entry Method</a>
                </div>
                <h3 class="panel-title">Patient Entry Methods</h3>
            </div>
            <div class="panel-body">
                <div class="form-container scrollable">
                    <table class="table table-striped table-hover responsive">
                        <thead>
                            <tr>
                                <th scope="col">Button Value</th>
                                <th scope="col">Selected Survey</th>
                                <th scope="col" class="center-text">Use Engagement Form</th>
                                <th scope="col" class="center-text">Display Position</th>
                                <th scope="col"></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${entryMethods.size() > 0}">
                                    <c:forEach items="${entryMethods}" var="entryMethod">
                                        <tr>
                                            <td scope="row">
                                                ${entryMethod.btnValue}
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${entryMethod.surveyTitle != ''}">
                                                        ${entryMethod.surveyTitle}
                                                    </c:when>
                                                    <c:otherwise>N/A</c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td class="center-text">
                                                <c:choose>
                                                    <c:when test="${entryMethod.useEngagementForm == true}">
                                                        Yes
                                                    </c:when>
                                                    <c:otherwise>NO</c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td class="center-text">
                                                ${entryMethod.dspPos}
                                            </td>
                                            <td>
                                                <div class="pull-right">
                                                    <a href="#entryMethodModal" data-toggle="modal" class="btn btn-link editEntryMethod" rel="${entryMethod.id}" title="Edit this entry method">
                                                        <span class="glyphicon glyphicon-edit"></span>
                                                        Edit
                                                    </a>
                                                    <a href="javascript:void(0);"  class="btn btn-link deleteEntryMethod" rel="${entryMethod.id}" title="Delete this entry method">
                                                        <span class="glyphicon glyphicon-remove"></span>
                                                        Delete
                                                    </a>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise><tr><td scope="row" colspan="4" style="text-align:center">No Entry Methods Found</td></c:otherwise>
                                </c:choose>
                        </tbody>
                    </table>

                </div>
            </div>
        </section>
        
        <section class="panel panel-default">
            <div class="panel-heading">
                <div class="pull-right">
                    <a href="#surveyTableModal" data-toggle="modal" class="btn btn-primary btn-xs btn-action" id="createNewSurveyTable" title="Add New Table">Add New Table</a>
                </div>
                <h3 class="panel-title">Available Tables for Survey Drop Down Questions</h3>
            </div>
            <div class="panel-body">
                <div class="form-container scrollable">
                    <table class="table table-striped table-hover responsive">
                        <thead>
                            <tr>
                                <th scope="col">Table Name</th>
                                <th scope="col"></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${availableTables.size() > 0}">
                                    <c:forEach items="${availableTables}" var="availableTable">
                                        <tr>
                                            <td scope="row">
                                                ${availableTable.tableName}
                                            </td>
                                            <td>
                                                <div class="pull-right">
                                                    <a href="javascript:void(0);"  class="btn btn-link deleteTable" rel="${availableTable.id}" title="Delete">
                                                        <span class="glyphicon glyphicon-remove"></span>
                                                        Delete
                                                    </a>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise><tr><td scope="row" colspan="2" style="text-align:center">No Associated Tables Found</td></c:otherwise>
                                </c:choose>
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