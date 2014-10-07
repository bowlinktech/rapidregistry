<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="main clearfix" role="main">

    <div class="col-md-12">

        <c:if test="${not empty savedStatus}" >
            <div class="alert alert-success" role="alert">
                <strong>Success!</strong> 
                <c:choose><c:when test="${savedStatus == 'updated'}">The program has been successfully updated!</c:when><c:otherwise>The program has been successfully created!</c:otherwise></c:choose>
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
                                <th scope="col">Display Position</th>
                                <th scope="col"></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${providerdetails.providerAddresses.size() > 0}">
                                    <c:forEach items="${providerdetails.providerAddresses}" var="address" varStatus="pStatus">
                                        <tr>
                                            <td scope="row">
                                                <c:if test="${not empty providerdetails.providerAddresses[pStatus.index].type}"><span style="font-style:italic">${providerdetails.providerAddresses[pStatus.index].type}</span><br /></c:if>
                                                ${providerdetails.providerAddresses[pStatus.index].line1} 
                                                <c:if test="${not empty providerdetails.providerAddresses[pStatus.index].line2}"><br />${providerdetails.providerAddresses[pStatus.index].line2}</c:if>
                                                    <br />
                                                ${providerdetails.providerAddresses[pStatus.index].city} ${providerdetails.providerAddresses[pStatus.index].state} ${providerdetails.providerAddresses[pStatus.index].postalCode}
                                            </td>
                                            <td>
                                                (o) ${providerdetails.providerAddresses[pStatus.index].phone1}
                                                <c:if test="${not empty providerdetails.providerAddresses[pStatus.index].phone2}"><br />(toll free) ${providerdetails.providerAddresses[pStatus.index].phone2}</c:if>
                                                <c:if test="${not empty providerdetails.providerAddresses[pStatus.index].fax}"><br />((f) ${providerdetails.providerAddresses[pStatus.index].fax}</c:if>
                                            </td>
                                            <td>
                                                <div class="pull-right">
                                                    <a href="#systemAddressModal" data-toggle="modal" class="btn btn-link editAddress" rel="address?i=${providerdetails.providerAddresses[pStatus.index].id}" title="Edit this address">
                                                    <span class="glyphicon glyphicon-edit"></span>
                                                    Edit
                                                </a>
                                                <a href="javascript:void(0);"  class="btn btn-link deleteAddress" rel="${providerdetails.providerAddresses[pStatus.index].id}" title="Delete this address">
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
                                <c:when test="${providerdetails.providerAddresses.size() > 0}">
                                    <c:forEach items="${providerdetails.providerAddresses}" var="address" varStatus="pStatus">
                                        <tr>
                                            <td scope="row">
                                                <c:if test="${not empty providerdetails.providerAddresses[pStatus.index].type}"><span style="font-style:italic">${providerdetails.providerAddresses[pStatus.index].type}</span><br /></c:if>
                                                ${providerdetails.providerAddresses[pStatus.index].line1} 
                                                <c:if test="${not empty providerdetails.providerAddresses[pStatus.index].line2}"><br />${providerdetails.providerAddresses[pStatus.index].line2}</c:if>
                                                    <br />
                                                ${providerdetails.providerAddresses[pStatus.index].city} ${providerdetails.providerAddresses[pStatus.index].state} ${providerdetails.providerAddresses[pStatus.index].postalCode}
                                            </td>
                                            <td>
                                                (o) ${providerdetails.providerAddresses[pStatus.index].phone1}
                                                <c:if test="${not empty providerdetails.providerAddresses[pStatus.index].phone2}"><br />(toll free) ${providerdetails.providerAddresses[pStatus.index].phone2}</c:if>
                                                <c:if test="${not empty providerdetails.providerAddresses[pStatus.index].fax}"><br />((f) ${providerdetails.providerAddresses[pStatus.index].fax}</c:if>
                                            </td>
                                            <td>
                                                <div class="pull-right">
                                                    <a href="#systemAddressModal" data-toggle="modal" class="btn btn-link editAddress" rel="address?i=${providerdetails.providerAddresses[pStatus.index].id}" title="Edit this address">
                                                    <span class="glyphicon glyphicon-edit"></span>
                                                    Edit
                                                </a>
                                                <a href="javascript:void(0);"  class="btn btn-link deleteAddress" rel="${providerdetails.providerAddresses[pStatus.index].id}" title="Delete this address">
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

