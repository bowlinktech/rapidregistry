<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="main clearfix" role="main">
    <div class="row-fluid">
        <div class="col-md-12">
            <c:if test="${not empty savedStatus}" >
                <div class="alert alert-success" role="alert">
                    <strong>Success!</strong> 
                    <c:choose>
                        <c:when test="${savedStatus == 'updated'}">The program has been successfully updated!</c:when>
                        <c:when test="${savedStatus == 'created'}">The program has been successfully added!</c:when>
                    </c:choose>
                </div>
            </c:if>
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
  <form:form id="patientSharing"  method="post" role="form">
       <input type="hidden" id="action" name="action" value="save" />
       <input type="hidden" id="programIds" name="programIds" />
  </form:form>
  <div class="row-fluid">
      <div class="col-md-12">
          <section class="panel panel-default">
              <div class="panel-body">
                <div class="form-container scrollable">
                    <p>
                        Below is a list of available programs you can share patient data with. Mark off the programs in which <strong>${programDetails.programName}</strong> can share data with.
                    </p>
                   <table class="table table-striped table-hover table-default">
                        <thead>
                            <tr>
                                <th scope="col" class="center-text" style="width:10%">Share</th>
                                <th scope="col">Program Name</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty availPrograms}">
                                    <c:forEach var="program" items="${availPrograms}">
                                        <tr>
                                            <td scope="row" class="center-text">
                                                <input type="checkbox" name="programId" value="${program.id}" ${program.sharing == true ? 'checked="true"' : ''} />
                                            </td>
                                            <td>
                                                ${program.programName}
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr><td colspan="7" class="center-text">There are currently no other programs set up to share patient data with.</td></tr>
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