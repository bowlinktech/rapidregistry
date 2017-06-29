<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<div class="main clearfix" role="main">
    <div class="row-fluid">
        <div class="col-md-12">
            <c:choose>
                <c:when test="${not empty savedStatus}" >
                    <div class="alert alert-success">
                        <strong>Success!</strong> 
                        <c:choose>
                            <c:when test="${savedStatus == 'created'}">The question has been successfully added!</c:when>
                            <c:when test="${savedStatus == 'updated'}">The question has been successfully updated!</c:when>
                        </c:choose>
                    </div>
                </c:when>
            </c:choose>
        </div>
    </div>
    <div class="row-fluid">
        <div class="col-md-12">
            <section class="panel panel-default">
                <div class="panel-heading">
                    <div class="pull-right">
                        <a href="#questionModal" data-toggle="modal" rel="${code}" id="0" class="btn btn-primary btn-xs btn-action questionDetails" title="Add New Question">Add New Question</a>
                    </div>
                    <h3 class="panel-title">Environmental Strategy Questions</h3>
                </div>
                <div class="panel-body">
                    <div class="form-container scrollable"><br />
                        <table class="table table-striped table-hover table-default" <c:if test="${not empty environmentalstrategyquestions}">id="dataTable"</c:if>>
                                <thead>
                                    <tr>
                                        <th scope="col">Question</th>
                                        <th scope="col"></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <c:choose>
                                    <c:when test="${not empty environmentalstrategyquestions}">
                                        <c:forEach items="${environmentalstrategyquestions}" var="environmentalstrategyquestion">
                                            <tr>
                                                <td scope="row">
                                                    ${environmentalstrategyquestion.question}
                                                </td>
                                                <td class="center-text">
                                                    <a href="#questionModal" data-toggle="modal" rel="${code}" id="${environmentalstrategyquestion.id}" class="btn btn-link questionDetails" title="View this Question">
                                                        <span class="glyphicon glyphicon-edit"></span>
                                                        Edit
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <tr><td colspan="7" class="center-text">There are currently no questions set up for this environmental strategy.</td></tr>
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
<div class="modal fade" id="questionModal" role="dialog" tabindex="-1" aria-labeledby="Environmental Strategy Question" aria-hidden="true" aria-describedby="Environmental Strategy Question"></div>

