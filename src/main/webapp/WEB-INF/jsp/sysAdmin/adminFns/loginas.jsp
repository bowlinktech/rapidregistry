<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="main clearfix" role="main">
    <div class="col-md-12">
	<section class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Login As ${msg}</h3>
                    </div>
                    <div class="panel-body">
                    <form role="form" id="form-admin-login" name='loginAsForm' action="" method='POST' target="_blank">
            <input type="hidden" id="realUsername" name="realUsername" value="${pageContext.request.userPrincipal.name}" />
            <fieldset name="login-fields" form="form-admin-login" class="basic-clearfix">           
                <div id="userNameDiv" class="form-group">
                    <label class="control-label" for="userName">Please confirm your user name</label>
                    <input id="userName" name='j_username' class="form-control" type="text" value=""  autocomplete="off" />
                	<span id="userNameMsg" class="control-label"></span>
                </div>
                <div id="passwordDiv" class="form-group">
                    <label class="control-label" for="password">Please enter your password</label>
                    <input id="password" name='j_password' class="form-control" type="password" value=""  autocomplete="off" />
                	<span id="passwordMsg" class="control-label"></span>
                </div>
                <div id="loginAsProgramDiv" class="form-group">
                <label class="control-label" for="loginAsProgam">Please select program you wish to login to</label>
                    <select id="programId" name="programId" class="form-control programId">
                    <option value="">Please select program you wish to login to</option>
                   <c:forEach items="${programList}" var="program" varStatus="programCount">
					   <option value="${program.id}">${program.programName}</option>
					</c:forEach>
                    </select>
                    <span id="loginAsProgramMsg" class="control-label"></span>
                </div>
               <div id="NoLoginAsUserDiv" class="form-group" style="display:none">
               		No users were found for this program.
               </div>
               
               <div id="loginAsUserDiv" class="form-group" style="display:none">
                <label class="control-label" for="loginAsUser">Login As</label>
                <div id="loginAsUserSelect" >
                    <select name="loginAsUser" id="loginAsUser" class="form-control loginAsUser">
                    <option value="">Please select the user you wish to login as</option>
                    </select>
                  </div>
                    <span id="loginAsUserMsg" class="control-label"></span>
                </div>
                <input type="button" id="submitButton" value="Login As" style="display:none" class="btn btn-primary pull-left" role="button"/>
            </fieldset>
        </form>
                    </div>
            </section>
    </div>
</div>
