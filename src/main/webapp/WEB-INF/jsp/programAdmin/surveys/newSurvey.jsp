<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="main clearfix" role="main">
    <div class="col-md-12">
        <c:if test="${not empty create}">   
            <section class="panel panel-default">
                <div class="panel-body">   
                    <a href="#surveyCopyModal" data-toggle="modal" class="btn btn-primary btn-xs btn-action" id="copySurvey" title="Copy from an Existing Survey">Copy from an Existing Survey</a>
                </div>
            </section>
        </c:if>
        <%@ include file="include/surveyForm.jsp" %>
        </section>
    </div>
</div>

<!-- survey copy modal -->
<div class="modal fade" id="surveyCopyModal" role="dialog" tabindex="-1" aria-labeledby="Edit or Copy Existing Survey" aria-hidden="true" aria-describedby="Edit or Copy Existing Survey"></div>
