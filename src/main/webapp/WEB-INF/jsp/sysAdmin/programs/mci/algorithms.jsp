<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<div class="main clearfix" role="main">
    <div class="row-fluid">
        <div class="col-md-12">
        		<div id="processOrderMsgDiv" class="alert alert-success" style="display:none;">
                    <strong>The process order change is saved.</strong>
                </div>
            <c:choose>
            	<c:when test="${not empty savedStatus}" >
            	<div class="alert alert-success">
                    <c:choose>
                        <c:when test="${savedStatus == 'updatedprogrammodules'}">The program modules have been successfully updated.</c:when>
                        <c:when test="${savedStatus == 'algorithmUpdated'}">The program MCI Algorithm has been successfully updated.</c:when>
                    </c:choose>
                </div>
                </c:when>
                
                <c:when test="${not empty param.msg}" >
                    <div class="alert alert-success">
                        <strong>Success!</strong> 
                        <c:choose>
                            <c:when test="${param.msg == 'updated'}">The program MCI Algorithm has been successfully updated!</c:when>
                            <c:when test="${param.msg == 'created'}">The program MCI Algorithm has been successfully added!</c:when>
                            <c:when test="${param.msg == 'deleted'}">The program MCI Algorithm has been successfully removed!</c:when>
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
    <c:forEach var="programEngagementSection" items="${programEngagementSections}">
    <div class="row-fluid">
        <div class="col-md-12">
            <section class="panel panel-default">
                <div class="panel-heading">
                    <div class="pull-right">
                        <a href="#algorithmDetailsModal" data-toggle="modal" class="btn btn-primary btn-xs btn-action" id="createNewAlgorithm" title="Create New Algorithm" rel="${programEngagementSection.id}">Create New Algorithm</a>
                    </div>
                    <h3 class="panel-title">MCI Algorithms for ${programEngagementSection.sectionName}</h3>
                </div>
                <div class="panel-body">
                    <div class="form-container scrollable"><br />
                       <table class="table table-striped table-hover table-default" <c:if test="${not empty programEngagementSection.mciAlgorithms}">id="NotInUseDataTable"</c:if>>
                                <thead>
                                    <tr>
                                        
                                        <th scope="col" class="center-text">Process Order</th>
                                        <th scope="col">Selected Fields</th>
                                        <th scope="col" class="center-text">Selected Action</th>                                        
                                        <th scope="col" class="center-text">Status</th>                                        
                                        <th scope="col" class="center-text">Date Created</th>
                                        
                                        <th scope="col"></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <c:choose>
                                    <c:when test="${not empty programEngagementSection.mciAlgorithms}">
                                        <c:forEach var="algorithm" items="${programEngagementSection.mciAlgorithms}">
                                            <tr>
                                            	<td class="center-text">
						                            <select rel="${algorithm.processOrder}" rel2="${programEngagementSection.id}" name="processOrder" class="processOrder">
						                                <option value="">- Select -</option>
						                                <c:forEach begin="1" end="${programEngagementSection.mciAlgorithms.size()}" var="i">
						                                    <option value="${i}" <c:if test="${algorithm.processOrder  == i}">selected</c:if>>${i}</option>
						                                </c:forEach>
						                            </select>
                        						</td>
                                                <td>
                                                    <c:forEach items="${algorithm.fields}" var="field" varStatus="fIndex">
                                                        ${fIndex.index+1}. <strong><c:choose><c:when test="${fIndex.index+1 == 1}">If</c:when><c:otherwise>And </c:otherwise></c:choose></strong> ${field.fieldName}&nbsp;<strong>${field.action}</strong><br />
                                                    </c:forEach>
                                                </td>
                                                <td class="center-text">
                                                    <c:choose>
                                                        <c:when test="${algorithm.action == 1}">Match</c:when>
                                                        <c:when test="${algorithm.action == 2}">No Match</c:when>
                                                        <c:when test="${algorithm.action == 3}">Review</c:when>
                                                    </c:choose>
                                                </td>
                                                <td class="center-text"><c:if test="${algorithm.status}">Active</c:if><c:if test="${!algorithm.status}">Inactive</c:if>
                                                </td>
                                                <td class="center-text"><fmt:formatDate value="${algorithm.dateCreated}" type="date" pattern="M/dd/yyyy" /></td>
                                                <td class="actions-col">
                                                    <a href="#algorithmDetailsModal" data-toggle="modal" rel="${algorithm.id}" class="btn btn-link editAlgorithm" title="Edit this algorithm" role="button">
                                                        <span class="glyphicon glyphicon-edit"></span>
                                                        Edit
                                                    </a>
                                                    <a href="javascript:void(0);" rel="${algorithm.id}" rel2="${programEngagementSection.id}" class="btn btn-link deleteAlgorithm" title="Delete this algorithm" role="button">
                                                        <span class="glyphicon glyphicon-remove"></span>
                                                        Delete
                                                    </a>    
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <tr><td colspan="7" class="center-text">There are currently no MCI Algorithms set up.</td></tr>
                                    </c:otherwise>
                                </c:choose>
                            </tbody>
                        </table>
                    </div>
                </div>
            </section>
          
        </div>
	</div>
      </c:forEach>
      <c:if test="${empty programEngagementSections}">
      		<div class="col-md-12">
      		<section class="panel panel-default">
                <div class="panel-body">
               There are currently no sections set up.  Please add an engagement section first.
             </div>
             </section>
             </div>
      </c:if>
</div>



<!-- Algorithm Details modal -->
<div class="modal fade" id="algorithmDetailsModal" role="dialog" tabindex="-1" aria-labeledby="Add MCI Algorithm" aria-hidden="true" aria-describedby="Add MCI Algorithm"></div>

