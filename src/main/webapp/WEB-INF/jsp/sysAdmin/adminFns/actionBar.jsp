<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav class="navbar navbar-default actions-nav" role="navigation">
    <div class="contain">
        <div class="navbar-header">
            <h1 class="section-title navbar-brand">
                <c:choose>
                    <c:when test="${param['page'] == 'importfile'}">
                        <a href="importfile" title="Import File As User" class="unstyled-link">Import File as User</a>
                    </c:when>
   					<c:when test="${param['page'] == 'loginas'}">
                        <a href="loginAs" title="Login As User" class="unstyled-link">Login as User</a>
                    </c:when>
                </c:choose>
            </h1>
        </div>
        <ul class="nav navbar-nav navbar-right navbar-actions" role="menu">
           <c:choose>
                <c:when test="${param['page'] == 'details'}">
                    <li role="menuitem"><a href="javascript:void(0);" id="saveDetails" title="Save Form" role="button"><span class="glyphicon glyphicon-ok icon-stacked"></span> Save </a></li>
                    <li role="menuitem"><a href="javascript:void(0);" id="saveCloseDetails" title="Save &amp; Close" role="button"><span class="glyphicon glyphicon-floppy-disk icon-stacked"></span> Save &amp; Close</a></li>
                </c:when>
          </c:choose>
        </ul>
    </div>
</nav>
