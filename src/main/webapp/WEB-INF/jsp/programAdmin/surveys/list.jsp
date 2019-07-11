<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="main clearfix" role="main">
    <div class="col-md-12">
        <c:choose>
            <c:when test="${not empty param.msg}" >
                <div class="alert alert-success">
                    <strong>Success!</strong> 
                    <c:choose>
                        <c:when test="${param.msg == 'updated'}">The system administrator successfully updated!</c:when>
                        <c:when test="${param.msg == 'created'}">The system administrator has been successfully added!</c:when>
			<c:when test="${param.msg == 'catcreated'}">The survey category has been successfully updated!</c:when>
			<c:when test="${param.msg == 'catdeleted'}">The survey category has been successfully deleted!</c:when>
                    </c:choose>
                </div>
            </c:when>
        </c:choose>
	
	<section class="panel panel-default">
             <div class="panel-heading">
                <div class="pull-right">
		   <a href="#surveyCategoryModal" data-toggle="modal" class="btn btn-primary btn-xs btn-action" id="createNewSurveyCategory" title="Create New Survey Category">Create New Category</a>
                </div>
                <h3 class="panel-title">Survey Categories</h3>
            </div>
            <div class="panel-body">
                <div class="form-container scrollable"><br />
                    <table class="table table-striped table-hover table-default">
                            <thead>
                                <tr>
                                    <th scope="col">Category Name</th>
                                    <th scope="col" class="center-text">Date Created</th>
                                    <th scope="col" class="center-text"></th>
                                </tr>
                            </thead>
                            <tbody>
                            <c:choose>
                                <c:when test="${not empty surveyCategories}">
                                    <c:forEach var="surveycategory" items="${surveyCategories}">
                                        <tr>
                                            <td>
                                                ${surveycategory.categoryName}
                                            </td>
                                            <td class="center-text"><fmt:formatDate value="${surveycategory.dateCreated}" type="Both" pattern="M/dd/yyyy h:mm a" /></td>
                                            <td class="actions-col">
                                                <a href="#surveyCategoryModal" data-toggle="modal" class="btn btn-primary btn-xs btn-action" id="editSurveyCategory" rel="${surveycategory.id}" title="Edit this category" role="button">
                                                    <span class="glyphicon glyphicon-edit"></span>
                                                    Edit
                                                </a>
						<a href="#" class="btn btn-primary btn-xs btn-danger deleteSurveyCategory" rel="${surveyCategory.id}" title="Delete this category" role="button">
                                                    <span class="glyphicon glyphicon-remove"></span>
                                                    Delete
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr><td colspan="3" class="center-text">There are currently no survey categories set up.</td></tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>
            </div>
        </section>
	
        <section class="panel panel-default">
             <div class="panel-heading">
                <div class="pull-right">
		    <a href="/programAdmin/surveys/create" id="createSurvey" title="Create New Survey" role="button" class="btn btn-primary btn-xs btn-action">Create New Survey </a>
                </div>
                <h3 class="panel-title">Surveys</h3>
            </div>
            <div class="panel-body">

                <div class="form-container scrollable"><br />
                    <table class="table table-striped table-hover table-default" <c:if test="${not empty surveys}">id="dataTable"</c:if>>
                            <thead>
                                <tr>
                                    <th scope="col">Survey Title</th>
                                    <th scope="col" class="center-text">Date Created</th>
                                    <th scope="col" class="center-text">Last Modified</th>
                                    <th scope="col" class="center-text"></th>
                                    <th scope="col" class="center-text"></th>
                                </tr>
                            </thead>
                            <tbody>
                            <c:choose>
                                <c:when test="${not empty surveys}">
                                    <c:forEach var="survey" items="${surveys}">
                                        <tr>
                                            <td>
                                                <a href="surveys/details?s=${survey.encryptedId}&v=${survey.encryptedSecret}" class="btn-link" title="Edit this survey" role="button">${survey.title}&nbsp;                                               
                                                <br />
                                                (<c:choose><c:when test="${survey.status == true}">Active</c:when><c:otherwise>Inactive</c:otherwise></c:choose>)</a>
                                            </td>
                                            <td class="center-text"><fmt:formatDate value="${survey.dateCreated}" type="Both" pattern="M/dd/yyyy h:mm a" /></td>
                                            <td class="center-text"><fmt:formatDate value="${survey.dateModified}" type="Both" pattern="M/dd/yyyy h:mm a" /></td>
                                            <td class="actions-col">
                                                <a href="#changeLogModal" data-toggle="modal" id="viewChangeLogs" title="View" role="button" class="btn-link" rel="${survey.id}">
                                                    View Change Log
                                                </a>
                                            </td>
                                            <td class="actions-col">
                                                <a href="surveys/details?s=${survey.encryptedId}&v=${survey.encryptedSecret}" class="btn btn-link editSysAdmin" title="Edit" role="button">
                                                    <span class="glyphicon glyphicon-edit"></span>
                                                    Edit
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr><td colspan="6" class="center-text">There are currently no surveys set up.</td></tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>
            </div>
        </section>
    </div>
</div>

<!-- Change Log modal -->
<div class="modal fade" id="changeLogModal" role="dialog" tabindex="-1" aria-labeledby="View Change Log" aria-hidden="true" aria-describedby="View Change Log"></div>
<div class="modal fade" id="surveyCategoryModal" role="dialog" tabindex="-1" aria-labeledby="Survey Category" aria-hidden="true" aria-describedby="Survey Category"></div>