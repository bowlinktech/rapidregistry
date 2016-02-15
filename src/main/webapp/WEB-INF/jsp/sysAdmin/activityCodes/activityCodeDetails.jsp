<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="main clearfix" role="main">

    <div class="col-md-12">
        <c:if test="${not empty savedStatus}" >
            <div class="alert alert-success" role="alert">
                <strong>Success!</strong> 
                The activity code has been successfully updated!
            </div>
        </c:if>
        <div class="alert alert-success assocSuccess" style="display:none"></div>
    </div>

    <div class="col-md-6">
         <form:form id="codedetails" commandName="codedetails" method="post" role="form">
            <input type="hidden" id="action" name="action" value="save" />
            <form:hidden path="id" id="codeId" />
            <form:hidden path="dateCreated" />
            <section class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Activity Code Details</h3>
                </div>
                <div class="panel-body">
                    <div class="form-container">
                        <spring:bind path="code">
                            <div class="form-group ${status.error ? 'has-error' : '' }">
                                <label class="control-label" for="code">Code *</label>
                                <form:input path="code" id="code" class="form-control" type="text" maxLength="255" />
                                <form:errors path="code" cssClass="control-label" element="label" />
                            </div>
                        </spring:bind>
                        <spring:bind path="codeDesc">
                            <div class="form-group ${status.error ? 'has-error' : '' }">
                                <label class="control-label" for="codeDesc">Code Description *</label>
                                <form:textarea path="codeDesc" id="codeDesc" class="form-control" type="text"  maxLength="500" />
                                <form:errors path="codeDesc" cssClass="control-label" element="label" />
                            </div>
                        </spring:bind>   
                        <spring:bind path="displayText">
                            <div class="form-group ${status.error ? 'has-error' : '' }">
                                <label class="control-label" for="displayText">Display Text</label>
                                <form:textarea path="displayText" id="displayText" class="form-control" type="text"  maxLength="255" />
                            </div>
                        </spring:bind>   
                    </div>
                </div>
            </section>
       </form:form>
    </div>
    <div class="col-md-6">
        <section class="panel panel-default">
            <div class="panel-heading">
                <div class="pull-right">
                    <a href="#newAssocModal" data-toggle="modal" class="btn btn-primary btn-xs btn-action" id="associateNewCategory" rel="${codedetails.id}" title="Associate Category">Associate to a Category</a>
                </div>
                <h3 class="panel-title">Associated Categories</h3>
            </div>
            <div class="panel-body" id="activityCodeCategories"></div>
        </section>
    </div>
</div>

<!-- Program Entry Method modal -->
<div class="modal fade" id="newAssocModal" role="dialog" tabindex="-1" aria-labeledby="Modified By" aria-hidden="true" aria-describedby="Modified By"></div>
