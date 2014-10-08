<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav class="navbar navbar-default actions-nav" role="navigation">
    <div class="contain">
        <div class="navbar-header">
            <h1 class="section-title navbar-brand">
                <c:choose>
                    <c:when test="${param['page'] == 'list'}">
                        <a href="javascript:void(0);" title="Progam List" class="unstyled-link">Programs</a>
                    </c:when>
                    <c:when test="${param['page'] == 'details'}">
                        <a href="javascript:void(0);" title="Progam Details" class="unstyled-link">
                            <c:choose>
                                <c:when test="${not empty id}">
                                    Program Details
                                </c:when>
                                <c:otherwise>
                                    Create New Program
                                </c:otherwise>
                            </c:choose>
                        </a>
                    </c:when>
                    <c:when test="${param['page'] == 'sharing'}">
                        <a href="javascript:void(0);" title="Program Patient Sharing" class="unstyled-link">Program Patient Sharing</a>
                    </c:when>
                    <c:when test="${param['page'] == 'modules'}">
                        <a href="javascript:void(0);" title="Program Modules" class="unstyled-link">Program Modules</a>
                    </c:when>
                    <c:when test="${param['page'] == 'demodata'}">
                        <a href="javascript:void(0);" title="Demographic Data Elements" class="unstyled-link">Demo Data Elements</a>
                    </c:when>
                    <c:when test="${param['page'] == 'healthdata'}">
                        <a href="javascript:void(0);" title="Health Data Elements" class="unstyled-link">Health Data Elements</a>
                    </c:when>
                    <c:when test="${param['page'] == 'activitycodes'}">
                        <a href="javascript:void(0);" title="Activity Codes" class="unstyled-link">Activity Codes</a>
                    </c:when>
                    <c:when test="${param['page'] == 'cannedreports'}">
                        <a href="javascript:void(0);" title="Canned Reports" class="unstyled-link">Canned Reports</a>
                    </c:when>  
                    <c:when test="${param['page'] == 'extracts'}">
                        <a href="javascript:void(0);" title="Extracts / Imports" class="unstyled-link">Extracts / Imports</a>
                    </c:when>   
                    <c:when test="${param['page'] == 'mpi'}">
                        <a href="javascript:void(0);" title="MPI Algorithms" class="unstyled-link">MPI Algorithms</a>
                    </c:when>  
                    <c:when test="${param['page'] == 'admins'}">
                        <a href="javascript:void(0);" title="Program Admins" class="unstyled-link">Program Admins</a>
                    </c:when>      
                </c:choose>
            </h1>
        </div>
        <ul class="nav navbar-nav navbar-right navbar-actions" role="menu">
            <c:choose>
                <c:when test="${param['page'] != 'list' && param['page'] != 'mpi' && param['page'] != 'admins' }">
                    <li role="menuitem"><a href="javascript:void(0);" id="saveDetails" title="Save Form" role="button"><span class="glyphicon glyphicon-ok icon-stacked"></span> Save </a></li>
                    <li role="menuitem"><a href="javascript:void(0);" id="saveCloseDetails" title="Save &amp; Close" role="button"><span class="glyphicon glyphicon-floppy-disk icon-stacked"></span> Save &amp; Close</a></li>
                </c:when>
          </c:choose>
        </ul>
    </div>
</nav>
