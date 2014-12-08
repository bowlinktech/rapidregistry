<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
                        <div class="form-group">
                          <input type="submit" id="submitButton"  role="button" class="btn btn-primary" value="Some Page"/>
                       </div>
                    </div>
                </form:form>
            </div>
            
        </section>

        
    </div>
</div>

<!-- Activity Code Details modal -->
<div class="modal fade" id="staffMemberModal" role="dialog" tabindex="-1" aria-labeledby="Add Staff Member" aria-hidden="true" aria-describedby="Add Staff Member"></div>
