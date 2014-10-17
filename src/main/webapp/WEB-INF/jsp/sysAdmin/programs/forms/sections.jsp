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
                            <c:when test="${param.msg == 'sectionsaved'}">The section has been successfully saved!</c:when>
                            <c:when test="${param.msg == 'sectiondeleted'}">The section has been successfully removed!</c:when>
                            <c:when test="${param.msg == 'fieldssaved'}">The fields have been successfully saved!</c:when>
                        </c:choose>
                    </div>
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
                    <a href="#sectionModal" data-toggle="modal" class="btn btn-primary btn-xs btn-action" id="createNewsection" rel="${sectionName}" title="Add New Section">Add New Section</a>
                </div>
                <h3 class="panel-title">${pageTitle}</h3>
            </div>
              <div class="panel-body">
                <div class="form-container scrollable">
                   <table class="table table-striped table-hover table-default">
                        <thead>
                            <tr>
                                <th scope="col">Section Name</th>
                                <th scope="col" class="center-text">Status</th>
                                <th scope="col" class="center-text">Display Position</th>
                                <th scope="col" class="center-text">Date Created</th>
                                <th scope="col"></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty sections}">
                                    <c:forEach var="section" items="${sections}">
                                        <tr>
                                            <td scope="row">
                                                ${section.sectionName}
                                            </td>
                                            <td class="center-text">
                                                <c:choose><c:when test="${section.status == true}">Active</c:when><c:otherwise>Inactive</c:otherwise></c:choose>
                                            </td>
                                            <td class="center-text">
                                                ${section.dspPos}
                                            </td>
                                            <td class="center-text"><fmt:formatDate value="${section.dateCreated}" type="date" pattern="M/dd/yyyy" /></td>
                                            <td class="actions-col">
                                                <a href="#sectionModal" data-toggle="modal" class="btn btn-link editsection" rel="${section.id}" rel2="${sectionName}" title="Edit this section">
                                                    <span class="glyphicon glyphicon-edit"></span>
                                                    Edit
                                                </a>
                                                <a href="${sectionName}/fields?s=${section.id}"  class="btn btn-link" title="View fields for this section.">
                                                    <span class="glyphicon glyphicon-align-justify"></span>
                                                    Fields
                                                </a>
                                                <%--<a href="javascript:void(0);"  class="btn btn-link deleteSection" rel="${entryMethod.id}" rel2="${sectionName}" title="Delete this entry method">
                                                    <span class="glyphicon glyphicon-remove"></span>
                                                    Delete
                                                </a>--%>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr><td colspan="7" class="center-text">There are currently no sections set up.</td></tr>
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
<!-- Provider Address modal -->
<div class="modal fade" id="sectionModal" role="dialog" tabindex="-1" aria-labeledby="Add New Section" aria-hidden="true" aria-describedby="Add New Section"></div>
