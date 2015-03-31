<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<header id="header" class="header" role="banner">
    <!-- Primary Nav -->
    <!--role="navigation" used for accessibility -->
    <nav class="navbar primary-nav" role="navigation">
        <div class="contain">
            <div class="navbar-header">
                <a href="<c:url value='/administrator' />" class="navbar-brand" title="{company name}">
                    <!--
                            <img src="<%=request.getContextPath()%>/dspResources/img/health-e-link/img-health-e-link-logo.png" class="logo" alt="Health-e-Link Logo"/>
                            Required logo specs:
                            logo width: 125px
                            logo height: 30px

                            Plain text can be used without image logo

                            sprite can be used with class="logo":
                    -->
                    <span class="logo" alt="{Company Name Logo}"></span>
                </a>
                 <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
            </div>
            <div class="collapse navbar-collapse navbar-ex1-collapse">
                <ul class="nav navbar-nav" role="menu">
                    <li role="menuitem" class="${param['sect'] == 'programs' ? 'active' : 'none'}"><a href="<c:url value='/sysAdmin/programs' />" title="Program Manager">Program Manager</a><c:if test="${param['sect'] == 'programs'}"><span class="indicator-active arrow-up"></span></c:if></li>
                    <li role="menuitem" class="${param['sect'] == 'activitycodes' ? 'active' : 'none'}"><a href="<c:url value='/sysAdmin/activity-codes/categories' />" title="Activity Code Manager">Activity Code Manager</a><c:if test="${param['sect'] == 'activitycodes'}"><span class="indicator-active arrow-up"></span></c:if></li>
                    <li role="menuitem" class="${param['sect'] == 'dataelements' ? 'active' : 'none'}"><a href="<c:url value='/sysAdmin/data-elements/list' />" title="Data Element Manager">Data Element Manager</a><c:if test="${param['sect'] == 'dataelements'}"><span class="indicator-active arrow-up"></span></c:if></li>
                    <li role="menuitem" class="${param['sect'] == 'reports' ? 'active' : 'none'}"><a href="<c:url value='/sysAdmin/reports/list' />" title="Report Manager">Report Manager</a><c:if test="${param['sect'] == 'reports'}"><span class="indicator-active arrow-up"></span></c:if></li>
                    <li role="menuitem" class="${param['sect'] == 'systemadmins' ? 'active' : 'none'}"><a href="<c:url value='/sysAdmin/system-admins/' />" title="System Admins Manager">System Admins Manager</a><c:if test="${param['sect'] == 'systemadmins'}"><span class="indicator-active arrow-up"></span></c:if></li>
                    <li role="menuitem" class="${param['sect'] == 'adminFns' ? 'active' : 'none'}"><a href="<c:url value='/sysAdmin/adminFns/importfile' />" title="Admin Functions">Admin Functions</a><c:if test="${param['sect'] == 'adminFns'}"><span class="indicator-active arrow-up"></span></c:if></li>
                </ul>
                <ul class="nav navbar-nav navbar-right" id="secondary-nav">
                  <li><a class="logout" href="<c:url value='/logout' />">Log out</a></li>
                </ul>
            </div>
        </div>
    </nav>
    <!-- // End Primary Nav -->
</header>
