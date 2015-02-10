<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="main clearfix" role="main">
    <div class="col-md-10">
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
        <section class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">Page ${page}</h3>
            </div>
            <div class="panel-body">
                <form:form id="pageForm" method="post" role="form">
                    <div class="form-container">
                        <div id="titleDiv" class="form-group">
                            <label class="control-label" for="confirmPassword">Everything to do with page</label>
                            <input id="title" name="title" class="form-control" maxLength="255" autocomplete="off" type="text" value="${title}" />
                            <span id="titleMsg" class="control-label"></span>
                        </div>  
                     </div>  
                            
                    <section class="panel panel-default">
                        <div class="panel-heading clearfix" style="height:46px; background-color: rgba(0,0,0, 0.3);">
                            <div class="btn-group pull-left">
                                <ul class="nav panel-tabs">
                                    <li class="active"><a href="#edit" data-toggle="tab"><strong>Edit</strong></a></li>
                                    <li><a href="#options" data-toggle="tab"><strong>Options</strong></a></li>
                                    <li><a href="#logic" data-toggle="tab"><strong>Logic</strong></a></li>
                                    <li><a href="#move" data-toggle="tab"><strong>Move</strong></a></li>
                                    <li><a href="#copy" data-toggle="tab"><strong>Copy</strong></a></li>
                                </ul>
                            </div>
                        </div>
                        <div class="panel-body">
                            <div class="tab-content">
                                <div class="tab-pane active" id="edit">
                                    Edit Section
                                </div> 
                                <div class="tab-pane" id="options">
                                    Options Section
                                </div>
                                <div class="tab-pane" id="logic">
                                    Logic Section
                                </div>
                                <div class="tab-pane" id="move">
                                    Move Section
                                </div>
                                <div class="tab-pane" id="copy">
                                    Copy Section
                                </div>
                            </div>   
                        </div>
                    </section>
                </form:form>
            </div>
        </section>
        
    </div>
</div>

<!-- Activity Code Details modal -->
<div class="modal fade" id="staffMemberModal" role="dialog" tabindex="-1" aria-labeledby="Add Staff Member" aria-hidden="true" aria-describedby="Add Staff Member"></div>
