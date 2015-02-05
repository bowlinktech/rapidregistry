<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="main clearfix" role="main">
    <div class="col-md-12">
        <c:choose>
            <c:when test="${not empty param.msg}" >
                <div class="alert alert-success">
                    <strong>Success!</strong> 
                    <c:choose>
                        <c:when test="${param.msg == 'updated'}">The system administrator successfully updated!</c:when>
                        <c:when test="${param.msg == 'created'}">The system administrator has been successfully added!</c:when>
                    </c:choose>
                </div>
            </c:when>
        </c:choose>
        <section class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">
                <c:if test="${not empty create}">Build a New Survey from Scratch</c:if>
                <c:if test="${not empty edit}">Modify ${surveyTitle}</c:if>
                </h3>
            </div>
            <div class="panel-body">
           	<%@ include file="include/surveyForm.jsp" %>
            </div>
        </section>
     <c:if test="${not empty create}">   
        <section class="panel panel-default">
            <div class="panel-body">           
					<a href="javascript:alert('code this');" title="Surveys" class="btn-link">Edit or Copy an Existing Survey</a>
            </div>
            </section>
       </c:if>
    </div>
</div>

<!-- survey copy modal -->
<div class="modal fade" id="surveyCopyModal" role="dialog" tabindex="-1" aria-labeledby="Edit or Copy Existing Survey" aria-hidden="true" aria-describedby="Edit or Copy Existing Survey"></div>
