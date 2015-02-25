
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<form>
    <input type="hidden" id="s" value="${encryptedId}" />
    <input type="hidden" id="v" value="${encryptedSecret}" />
</form>

<div class="main clearfix" role="main">
    <div class="col-md-12">
        <c:choose>
            <c:when test="${not empty msg}" >
                <div class="alert alert-success">
                    <strong>Success!</strong> 
                    <c:choose>
                        <c:when test="${msg == 'surveyUpdated'}">The survey has been successfully updated!</c:when>
                        <c:when test="${msg == 'surveyCreated'}">The survey has been successfully added!</c:when>
                        <c:when test="${msg == 'pageUpdated'}">The page has been successfully updated!</c:when>
                        <c:when test="${msg == 'pageCreated'}">The page has been successfully added!</c:when>
                    </c:choose>
                </div>
            </c:when>
        </c:choose>
    </div>
    <div class="col-md-10" style="padding-left:40px;">
        <div class="row" id="surveyPages"></div>
    </div>
</div>
<!-- Edit Survey modal -->
<div class="modal fade" id="surveyModal" role="dialog" tabindex="-1" aria-labeledby="Modify Survey" aria-hidden="true" aria-describedby="Modify Survey"></div>
