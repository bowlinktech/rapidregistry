<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav class="navbar navbar-default actions-nav" role="navigation">
    <div class="contain">
        <div class="navbar-header">
            <h1 class="section-title navbar-brand">
                <c:choose>
                    <c:when test="${param['page'] == 'demo'}">
                        <a href="javascript:void(0);" title="Demographic Data Elements" class="unstyled-link">Demographic Data Elements</a>
                    </c:when>
                    <c:when test="${param['page'] == 'health'}">
                        <a href="javascript:void(0);" title="Health Data Elements" class="unstyled-link">Health Data Elements</a>
                    </c:when>
                </c:choose>
            </h1>
        </div>
        <ul class="nav navbar-nav navbar-right navbar-actions" role="menu">
         </ul>
    </div>
</nav>
