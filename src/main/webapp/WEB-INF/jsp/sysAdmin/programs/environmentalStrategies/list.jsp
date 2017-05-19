<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<div class="main clearfix" role="main">
    <div class="row-fluid">
        <div class="col-md-12">
            <c:choose>
                <c:when test="${not empty param.msg}" >
                    <div class="alert alert-success">
                        <strong>Success!</strong> 
                        <c:choose>
                            <c:when test="${param.msg == 'created'}">The crosswalk has been successfully added!</c:when>
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
                    <h3 class="panel-title">Environmental Strategies</h3>
                </div>
                <div class="panel-body">
                    <div class="form-container scrollable"><br />
                        <table class="table table-striped table-hover table-default" <c:if test="${not empty environmentalStrategies}">id="dataTable"</c:if>>
                                <thead>
                                    <tr>
                                        <th scope="col">Environmental Strategy</th>
                                        <th scope="col"></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <c:choose>
                                    <c:when test="${not empty environmentalStrategies}">
                                        <c:forEach items="${environmentalStrategies}" var="environmentalStrategy">
                                            <c:set var="code" value="${fn:split(environmentalStrategy, ' -- ')}" />
                                            <tr>
                                                <td scope="row">
                                                    ${environmentalStrategy}
                                                </td>
                                                <td class="center-text">
                                                    <a href="environmentalStrategies/${code[0]}" data-toggle="modal" class="btn btn-link"  title="View Questions">
                                                        <span class="glyphicon glyphicon-edit"></span>
                                                        View Questions
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <tr><td colspan="7" class="center-text">There are currently no environmental strategies set up for this registry.</td></tr>
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
