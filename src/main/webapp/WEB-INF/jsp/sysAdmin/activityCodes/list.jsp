<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="main clearfix full-width" role="main">
    <div class="col-md-12">
        <c:choose>
            <c:when test="${not empty param.msg}" >
                <div class="alert alert-success">
                    <strong>Success!</strong> 
                    <c:choose>
                        <c:when test="${param.msg == 'updated'}">The activity code has been successfully updated!</c:when>
                        <c:when test="${param.msg == 'created'}">The activity code has been successfully added!</c:when>
                    </c:choose>
                </div>
            </c:when>
        </c:choose>

        <section class="panel panel-default">
            <div class="panel-heading">
                <div class="pull-right">
                    <a href="#activityCodeModal" data-toggle="modal" class="btn btn-primary btn-xs btn-action" id="createNewCode" title="Create New Activity Code">Create New Activity Code</a>
                </div>
                <h3 class="panel-title">Activity Codes</h3>
            </div>
            <div class="panel-body">

                <div class="form-container scrollable"><br />
                    <table class="table table-striped table-hover table-default" <c:if test="${not empty activityCodes}">id="dataTable"</c:if>>
                        <thead>
                            <tr>
                                <th scope="col">Code ${result}</th>
                                <th scope="col">Code Desc</th>
                                <th scope="col" class="center-text">Date Created</th>
                                <th scope="col"></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty activityCodes}">
                                    <c:forEach var="code" items="${activityCodes}">
                                        <tr>
                                            <td scope="row">
                                                <a href="#activityCodeModal" data-toggle="modal" rel="${code.id}" title="Edit this activity code" class="editCode">${code.code}</a>
                                            </td>
                                            <td>
                                               ${code.codeDesc}
                                            </td>
                                            <td class="center-text"><fmt:formatDate value="${code.dateCreated}" type="date" pattern="M/dd/yyyy" /></td>
                                            <td class="actions-col">
                                                <a href="#activityCodeModal" data-toggle="modal" rel="${code.id}" class="btn btn-link editCode" title="Edit this activity code" role="button">
                                                    <span class="glyphicon glyphicon-edit"></span>
                                                    Edit
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr><td colspan="7" class="center-text">There are currently no activity codes set up.</td></tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>
            </div>
        </section>
    </div>
</div>
                                    
<!-- Activity Code Details modal -->
<div class="modal fade" id="activityCodeModal" role="dialog" tabindex="-1" aria-labeledby="Add Activity Code" aria-hidden="true" aria-describedby="Add Activity Code"></div>
