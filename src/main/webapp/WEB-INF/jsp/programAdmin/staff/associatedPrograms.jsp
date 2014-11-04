<%-- 
    Document   : associatedPrograms
    Created on : Nov 4, 2014, 2:56:04 PM
    Author     : chadmccue
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<table class="table table-striped table-hover responsive">
    <thead>
        <tr>
            <th scope="col">Program</th>
            <th scope="col">Modules</th>
            <th scope="col" class="center-text">Departments</th>
            <th scope="col" class="center-text">Date Created</th>
            <th scope="col"></th>
        </tr>
    </thead>
    <tbody>
        <c:choose>
            <c:when test="${not empty programs}">
                <c:forEach var="program" items="${programs}">
                    <tr>
                        <td>
                            ${program.programName}
                        </td>
                        <td class="center-text">
                            <a href="javascript:void(0);" class="btn-link viewModules" rel="${program.id}" title="View Program Modules" role="button">
                                View
                            </a>
                        </td>
                        <td class="center-text">
                            <a href="javascript:void(0);" class="btn-link viewDepartments" rel="${program.id}" title="View Program Modules" role="button">
                                View
                            </a>
                        </td>
                        <td class="center-text">
                            <fmt:formatDate value="${program.dateCreated}" type="date" pattern="M/dd/yyyy h:mm a" />
                        </td>
                    </tr>
                </c:forEach>
            </c:when>
        </c:choose>
    </tbody>
</table>
