<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="main clearfix" role="main">
    <div class="row-fluid">
        <div class="col-md-12">
            <c:choose>
                <c:when test="${not empty param.msg}" >
                    <div class="alert alert-success">
                        <strong>Success!</strong> 
                        <c:choose>
                            <c:when test="${param.msg == 'importTypesaved'}">The import type has been successfully saved!</c:when>
                            <c:when test="${param.msg == 'fieldssaved'}">The import type fields have been successfully saved!</c:when>
                        </c:choose>
                    </div>
                    
                </c:when>
                <c:when test="${not empty param.deleted}">
	                            <c:if test="${param.deleted=='failed'}">
	                            <div class="alert alert-danger">The import type is in use, it has been set to inactive.
	                            </div></c:if>
	                            <c:if test="${param.deleted=='success'}">
	                             <div class="alert alert-success"><strong>Success!</strong> The import type has been successfully removed!</div></c:if>
                 </c:when>
            </c:choose>
            <section class="panel panel-default">
                <div class="panel-body">
                    <dt>
                    <dd><strong>Program Summary:</strong></dd>
                        <dd><strong>Program Name:</strong> ${programDetails.programName}</dd>
                    </dt>
                </div>
            </section>
      </div>
  </div>
  <div class="row-fluid">
      <div class="col-md-12">
          <section class="panel panel-default">
              <div class="panel-heading">
                <div class="pull-right">
                    <a href="#importModal" data-toggle="modal" class="btn btn-primary btn-xs btn-action" id="createNewImportType"  title="Add New Import Type">Add New Import Type</a>
                </div>
                <h3 class="panel-title">Program Import Types</h3>
            </div>
              <div class="panel-body">
                <div class="form-container scrollable">
                    <table class="table table-striped table-hover table-default">
                        <thead>
                            <tr>
                                <th scope="col">Name</th>
                                <th scope="col" class="center-text">Status</th>
                                <th scope="col" class="center-text">Use Health-e-link</th>
                                <th scope="col" class="center-text">Date Created</th>
                                <th scope="col" class="center-text"></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty importTypes}">
                                    <c:forEach var="importType" items="${importTypes}">
                                        <tr>
                                            <td scope="row">
                                                ${importType.name}
                                            </td>
                                            <td class="center-text">
                                                <c:choose><c:when test="${importType.status == true}">Active</c:when><c:otherwise>Inactive</c:otherwise></c:choose>
                                            </td>
                                            <td class="center-text">
                                                <c:choose><c:when test="${importType.useHEL == true}">Yes</c:when><c:otherwise>No</c:otherwise></c:choose>
                                            </td>
                                            <td class="center-text"><fmt:formatDate value="${importType.dateCreated}" type="date" pattern="M/dd/yyyy" /></td>
                                            <td class="actions-col">
                                                <a href="#importModal" data-toggle="modal" class="btn btn-link editImportType" rel="${importType.id}" title="Edit this import type">
                                                    <span class="glyphicon glyphicon-edit"></span>
                                                    Edit
                                                </a>
                                                <a href="/sysAdmin/programs/${programName}/imports/fields?s=${importType.id}"  class="btn btn-link" title="View importable fields for this import type.">
                                                    <span class="glyphicon glyphicon-align-justify"></span>
                                                    Fields
                                                </a>
                                                <a href="/sysAdmin/programs/${programName}/mci-algorithms?s=${importType.id}"  class="btn btn-link" title="View rules on how to process this import type." rel="${importType.id}">
                                                    <span class="glyphicon glyphicon-saved"></span>
                                                    Rules
                                                </a>
                                                <a href="javascript:void(0);"  class="btn btn-link deleteImportType" rel="${importType.id}"  title="Delete this import type">
                                                    <span class="glyphicon glyphicon-remove"></span>
                                                    Delete
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr><td colspan="5" class="center-text">There are currently no imports set up for this program.</td></tr>
                                </c:otherwise>
                            </c:choose>
                       </tbody>
                 </table>
              </div>
          </div>
       </section>
    </div>
 </div>
</div>
<div class="modal fade" id="importModal" role="dialog" tabindex="-1" aria-labeledby="" aria-hidden="true" aria-describedby=""></div>
