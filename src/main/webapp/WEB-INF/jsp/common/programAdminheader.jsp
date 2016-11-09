<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
                    <c:choose>
                        <c:when test="${fn:length(sessionScope.availPrograms) > 4}">
                            <li role="menuitem" class="active">
                                <select class="form-control registrySelect" style="margin-top:10px;">
                                    <option value="">- Select Registry-</option>
                                    <c:forEach var="program" items="${sessionScope.availPrograms}">
                                        <option value="${program[0]}" <c:if test="${program[0] == sessionScope.selprogramId}">selected</c:if>>${program[1]}</option>
                                    </c:forEach>
                                </select>
                            </li>
                        </c:when>
                        <c:otherwise>
                             <c:forEach var="program" items="${sessionScope.availPrograms}">
                                <li role="menuitem" class="${program[0] == sessionScope.selprogramId ? 'active' : 'none'}"><a href="<c:url value='/programAdmin/changeRegistry?i=${program[0]}' />" title="${program[1]}">${program[1]}</a><c:if test="${program[0] == sessionScope.selprogramId}"><span class="indicator-active arrow-up"></span></c:if></li>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </ul>
                <ul class="nav navbar-nav navbar-right" id="secondary-nav">
                  <li><a class="logout" href="<c:url value='/logout' />">Log out</a></li>
                </ul>
            </div>
        </div>
    </nav>
    <!-- // End Primary Nav -->
</header>
                
<script>
    require(['./main'], function () {
        require(['jquery'], function ($) {
            $(document).on('change', '.registrySelect', function() {
                if($(this).val() !== '') {
                    window.location.href="/programAdmin/changeRegistry?i="+$(this).val();
                } 
             });
        });
    });
    
</script>
