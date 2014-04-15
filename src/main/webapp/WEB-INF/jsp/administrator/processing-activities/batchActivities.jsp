<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="main clearfix" role="main">
    <div class="row-fluid">
        <div class="col-md-12">
            <section class="panel panel-default">
                <div class="panel-body">
                    <dt>
                        <dt>Batch Summary:</dt>
                        <dd><strong>Batch ID:</strong> ${batchDetails.utBatchName}</dd>
                        <dd><strong>Source Organization:</strong> ${batchDetails.orgName}</dd>
                        <dd><strong>Date Submitted:</strong> <fmt:formatDate value="${batchDetails.dateSubmitted}" type="date" pattern="M/dd/yyyy" />&nbsp;&nbsp;<fmt:formatDate value="${batchDetails.dateSubmitted}" type="time" pattern="h:mm:ss a" /></dd>
                    </dt>
                </div>
            </section>
        </div>
    </div>
    <div class="col-md-12">
         <section class="panel panel-default">
            <div class="panel-body">
                
                <div class="form-container scrollable">
                    <table class="table table-striped table-hover table-default" <c:if test="${not empty userActivities}">id="dataTable"</c:if>>
                        <thead>
                            <tr>
                                <th scope="col">User Name</th>
                  				<th scope="col">Transaction(s) accessed</th>
                  				<th scope="col">User Activity</th>
                  				<th scope="col">Additional Information</th>
                                <th scope="col">Date / Time</th>
                            	<th scope="col"></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty userActivities}">
                                    <c:forEach var="ua" items="${userActivities}">
                                        <tr>
                                            <td scope="row">${ua.userFirstName} ${ua.userLastName}<br/>
                                            ${ua.orgName}
                                            </td>
                                            <td>
                                                ${ua.activity}
                                            </td>
                                            <td>
                                            ${ua.activityDesc}
                                            </td>
                                            <td>
                                           <%-- 
                                            <c:forEach var="transactionInId" items="ua.transactionInIds" varStatus="counter">
                                            	<a href="javascript:'alert(\" I open a window\");'">${ua.transactionInIds.[counter]}</a>
                                           	</c:forEach>
                                           	--%>
                                              ${ua.transactionInIds}
                                            </td> 
                                            <td>
                                            
                                               <fmt:formatDate value="${batchDetails.dateSubmitted}" type="date" pattern="M/dd/yyyy" />&nbsp;&nbsp;<fmt:formatDate value="${ua.dateCreated}" type="time" pattern="h:mm:ss a" />
                                            </td>                                         
                                      </tr>
                                   </c:forEach>     
                                 </c:when>  
                                 <c:when test="${not empty toomany}">
                                    <tr>
                                        <td colspan="7">
                                            There were a total of ${size} user activities found, please enter a parameter to narrow down the results.
                                            <br />
                                        </td>
                                    </tr>
                                 </c:when>
                                 <c:when test="${not empty stilltoomany}">
                                    <tr>
                                        <td colspan="7">T
                                           Your search produced ${size} results, please narrow down further.
                                        </td>
                                    </tr>
                                 </c:when>   
                                 <c:otherwise>
                                    <tr><td colspan="7" class="center-text">There are currently no user activities for this batch.</td></tr>
                                </c:otherwise>
                             </c:choose>           
                        </tbody>
                    </table>
                </div>
            </div>
        </section>
    </div>
</div>
<div class="modal fade" id="statusModal" role="dialog" tabindex="-1" aria-labeledby="Status Details" aria-hidden="true" aria-describedby="Status Details"></div>
<div class="modal fade" id="messageDetailsModal" role="dialog" tabindex="-1" aria-labeledby="Message Details" aria-hidden="true" aria-describedby="Message Details"></div>