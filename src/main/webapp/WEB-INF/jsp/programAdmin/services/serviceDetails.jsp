<%-- 
    Document   : serviceDetails
    Created on : Feb 18, 2015, 1:49:42 PM
    Author     : chadmccue
--%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="main clearfix" role="main">

    <div class="col-md-12">
        <c:if test="${not empty savedStatus}" >
            <div class="alert alert-success" role="alert">
                <strong>Success!</strong> 
                The service has been successfully updated!
            </div>
        </c:if>
        <c:if test="${not empty param.msg}" >
            <div class="alert alert-success">
                <strong>Success!</strong> 
                <c:choose>
                    <c:when test="${param.msg == 'categoryAdded'}">The service has been added to the selected category!</c:when>
                    <c:when test="${param.msg == 'categoryRemoved'}">The service has been successfully removed from the selected category!</c:when>
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
                    <form:form id="servicedetailsform" commandName="serviceDetails" modelAttribute="serviceDetails"  method="post" role="form">
                        <input type="hidden" id="action" name="action" value="save" />
                        <form:hidden path="id" id="id" />
                        <form:hidden path="programId" />
                        <form:hidden path="dateCreated" />
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
                            <spring:bind path="serviceName">
                                <div class="form-group ${status.error ? 'has-error' : '' }">
                                    <label class="control-label" for="serviceName">Service Name *</label>
                                    <form:input path="serviceName" id="serviceName" class="form-control" type="text" maxLength="55" />
                                    <form:errors path="serviceName" cssClass="control-label" element="label" />
                                </div>
                            </spring:bind>
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
                    <a href="#categoryModal" data-toggle="modal" class="btn btn-primary btn-xs btn-action" id="addCategory" title="Assign to another Category">Assigned Categories</a>
                </div>
                <h3 class="panel-title">Assigned Categories</h3>
            </div>
            <div class="panel-body">
                <div class="form-container scrollable" id="assignedCategories"></div>
            </div>
        </section>

    </div>
</div>
<div class="modal fade" id="categoryModal" role="dialog" tabindex="-1" aria-labeledby="" aria-hidden="true" aria-describedby=""></div>
