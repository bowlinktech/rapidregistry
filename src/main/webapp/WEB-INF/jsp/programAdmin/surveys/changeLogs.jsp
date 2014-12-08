<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h3 class="panel-title">Enter Save Note</h3>
         </div>
         <div class="modal-body">
             <div class="form-container scrollable"><br />
                    <table class="table table-striped table-hover table-default" <c:if test="${not empty surveys}">id="dataTable"</c:if>>
                            <thead>
                                <tr>
                                    <th scope="col">Entered By</th>
                                    <th scope="col" class="center-text">Change Date / Time</th>
                                    <th scope="col">Notes</th>        
                                </tr>
                            </thead>
                            <tbody>
                            <c:choose>
                                <c:when test="${not empty changeLogs}">
                                    <c:forEach var="changeLog" items="${changeLogs}">
                                        <tr>
                                            <td>
                                               ${changeLog.userFirstName} ${changeLog.userLastName}
                                            </td>
                                            <td class="center-text"><fmt:formatDate value="${changeLog.dateCreated}" type="Both" pattern="M/dd/yyyy h:mm a" /></td>
                                            <td>
                                                ${changeLog.notes}
                                            </td>                                            
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:when test="${not empty notValid}">
                                        <tr>
                                            <td colspan="3">
                                              ${notValid}
                                            </td>                                             
                                        </tr>                     
                                </c:when>
                                <c:otherwise>
                                    <tr><td colspan="3" class="center-text">There are not changes for this survey.</td></tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>
             
        </div>
    </div>
</div>

<script type="text/javascript">

    $(document).ready(function() {
        $("input:text,form").attr("autocomplete", "off");
    });
</script>
