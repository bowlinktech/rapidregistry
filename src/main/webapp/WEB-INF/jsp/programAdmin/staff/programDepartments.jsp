<%-- 
    Document   : programDepartments
    Created on : Nov 4, 2014, 4:38:41 PM
    Author     : chadmccue
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="outerLoop" value="${userDepartments.size()/hierarchyHeadings.size()}" />
<c:set var="counter" value="0" />

<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h3 class="panel-title">Selected Departments</h3>
         </div>
         <div class="modal-body">
            <table class="table table-striped table-hover responsive">
                <thead>
                    <tr>
                        <c:forEach var="hierarchy" items="${hierarchyHeadings}">
                            <th scope="col">${hierarchy.name}</th>
                        </c:forEach>
                        <th scope="col" class="center-text">Date Created</th>
                        <th scope="col"></th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty userDepartments}">
                            <c:forEach begin="1" end="${outerLoop}">
                                <tr>
                                    <c:forEach var="hierarchy" items="${hierarchyHeadings}">
                                        <td>
                                            ${userDepartments[counter].hierarchyName}
                                            <c:set var="counter" value="${counter+1}" />
                                        </td>
                                    </c:forEach>
                                        <td class="center-text"><fmt:formatDate value="${userDepartments[counter-1].dateCreated}" type="Both" pattern="M/dd/yyyy h:mm a" /></td>
                                        <td class="center-text">
                                            <a href="javascript:void(0)" class="btn-link removeProgram" rel="${program.programId}">Delete</a>
                                        </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                    </c:choose>
                </tbody>
            </table>
            <div class="modal-header"></div>
            <div><h3 class="panel-title" style="padding-top:20px">Add a New Department</h3></div>
            <div class="otherorgHierarchyDiv" style="padding-top:20px"></div>  
         </div>
    </div>
</div>
