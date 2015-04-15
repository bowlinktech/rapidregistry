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
        	<c:choose>
            	<c:when test="${not empty savedStatus}" >
            	<div class="alert alert-success">
                    <c:choose>
                        <c:when test="${savedStatus == 'updatedprogrammodules'}">The program modules have been successfully updated.</c:when>
                        <c:when test="${savedStatus == 'algorithmUpdated'}">The algorithm has been successfully updated.</c:when>
                    </c:choose>
                </div>
                </c:when>
                
                <c:when test="${not empty param.msg}" >
                    <div class="alert alert-success">
                        <strong>Success!</strong> 
                        <c:choose>
                            <c:when test="${param.msg == 'updated'}">The algorithm has been successfully updated!</c:when>
                            <c:when test="${param.msg == 'created'}">The algorithm has been successfully added!</c:when>
                            <c:when test="${param.msg == 'deleted'}">The algorithm has been successfully removed!</c:when>
                        </c:choose>
                    </div>
                </c:when>
            </c:choose>
            <section class="panel panel-default">
                <div class="panel-body">
                    <dt>
                    <dd><strong>Program Summary:</strong></dd>
                    <dd><strong>Program Name:</strong> ${programDetails.programName}</dd>
                    <dd><strong>Import Name:</strong> ${importType.name}</dd>
                    </dt>
                </div>
            </section>  
        </div>
    </div>
   <c:forEach var="category" items="${algorithmByCatList}">
    <div class="row-fluid">
        <div class="col-md-12">
            <section class="panel panel-default">
                <div class="panel-heading">
                    <div class="pull-right">
                        <a href="#algorithmDetailsModal" data-toggle="modal" class="btn btn-primary btn-xs btn-action createNewAlgorithm" id="createNewAlgorithm${category.id}" title="Create New Algorithm" rel="${importType.id}" rel2="${category.id}">Create New Algorithm</a>
                    </div>
                    <h3 class="panel-title">Algorithms for ${category.displayText}</h3>
                </div>
                <div class="panel-body">
                <div id="processOrderMsgDiv${category.id}" class="alert alert-success" style="display:none;">
                    <strong>The process order change is saved.</strong>
                </div>
                    <div class="form-container scrollable" id="algorithm${category.id}"><br />
                       	<%@ include file="alByCat.jsp" %>
                    </div>
                </div>
            </section>
          
        </div>
	</div>
      </c:forEach>
      <c:if test="${empty algorithmByCatList}">
      		<div class="col-md-12">
      		<section class="panel panel-default">
                <div class="panel-body">
               There are no algorithms found. Please contract program administrator if you believe that this is an error.
             </div>
             </section>
             </div>
      </c:if>
</div>



<!-- Algorithm Details modal -->
<div class="modal fade" id="algorithmDetailsModal" role="dialog" tabindex="-1" aria-labeledby="Add algorithm" aria-hidden="true" aria-describedby="Add Algorithm"></div>

