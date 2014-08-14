<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="main clearfix full-width" role="main">
    <div class="col-md-12">
        <c:choose>
            <c:when test="${not empty savedStatus}" >
                <div class="alert alert-success" role="alert">
                    <strong>Success!</strong> 
                    <c:choose>
                        <c:when test="${savedStatus == 'updated'}">The program has been successfully updated!</c:when>
                        <c:when test="${savedStatus == 'created'}">The program has been successfully added!</c:when>
                        <c:when test="${savedStatus == 'deleted'}">The program has been successfully removed!</c:when>
                        <c:when test="${savedStatus == 'updatedpatientsharing'}">The patient sharing has been successfully updated.</c:when>
                        <c:when test="${savedStatus == 'updatedprogrammodules'}">The program modules have been successfully updated.</c:when>
                        <c:when test="${savedStatus == 'codesupdated'}">The activity codes have been successfully added!</c:when>
                    </c:choose>
                </div>    
            </c:when>
            <c:when test="${not empty param.msg}" >
                <div class="alert alert-success">
                    <strong>Success!</strong> 
                    <c:choose>
                        <c:when test="${param.msg == 'updated'}">The program fields have been successfully updated!</c:when>
                        <c:when test="${param.msg == 'created'}">The crosswalk has been successfully added!</c:when>
                    </c:choose>
                </div>
            </c:when>
        </c:choose>

        <section class="panel panel-default">
            <div class="panel-body">

                <div class="form-container scrollable"><br />
                    <table class="table table-striped table-hover table-default" <c:if test="${not empty programList}">id="dataTable"</c:if>>
                            <thead>
                                <tr>
                                    <th scope="col">Program Name ${result}</th>
                                    <th scope="col" class="center-text"># of Program Admins</th>
                                    <th scope="col" class="center-text">Date Created</th>
                                    <th scope="col"></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty programList}">
                                    <c:forEach var="program" items="${programList}">
                                        <tr>
                                            <td scope="row">
                                                <a href="${fn:toLowerCase(fn:replace(program.programName, ' ', '-'))}/" title="Edit this program">${program.programName}</a>
                                            </td>
                                            <td class="center-text">
                                                ${program.totalProgramAdmins}
                                            </td>
                                            <td class="center-text"><fmt:formatDate value="${program.dateCreated}" type="date" pattern="M/dd/yyyy" /></td>
                                            <td class="actions-col">
                                                <a href="${fn:toLowerCase(fn:replace(program.programName, ' ', '-'))}/" class="btn btn-link" title="Edit this program" role="button">
                                                    <span class="glyphicon glyphicon-edit"></span>
                                                    Edit
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr><td colspan="7" class="center-text">There are currently no programs set up.</td></tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>
            </div>
        </section>
    </div>
</div>