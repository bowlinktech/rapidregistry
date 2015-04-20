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
                    <c:when test="${param['page'] == 'patientsections'}">
                        <a href="javascript:void(0);" title="Patient Detail Sections" class="unstyled-link">Patient Detail Sections</a>
                    </c:when>
                    <c:when test="${param['page'] == 'engagementsections'}">
                        <a href="javascript:void(0);" title="Engagement Details Sections" class="unstyled-link">Engagement Details Sections</a>
                    </c:when>
                    <c:when test="${param['page'] == 'fields'}">
                        <a href="javascript:void(0);" title="Patient Detail Sections" class="unstyled-link">Section Fields</a>
                    </c:when>    
                    <c:when test="${param['page'] == 'activitycodes'}">
                        <a href="javascript:void(0);" title="Activity Codes" class="unstyled-link">Activity Codes</a>
                    </c:when>
                    <c:when test="${param['page'] == 'cannedreports'}">
                        <a href="javascript:void(0);" title="Canned Reports" class="unstyled-link">Canned Reports</a>
                    </c:when>  
                    <c:when test="${param['page'] == 'imports' ||param['page'] == 'importFields' ||param['page'] == 'importRules'}">
                        <a href="javascript:void(0);" title="Imports" class="unstyled-link">Imports</a>
                    </c:when>   
                    <c:when test="${param['page'] == 'admins'}">
                        <a href="javascript:void(0);" title="Program Admins" class="unstyled-link">Program Admins</a>
                    </c:when> 
                    <c:when test="${param['page'] == 'hierarchy'}">
                        <a href="javascript:void(0);" title="Program Organizaion Hierarchy" class="unstyled-link">Program Organization Profiles</a>
                    </c:when>
                </c:choose>
            </h1>
        </div>
        <ul class="nav navbar-nav navbar-right navbar-actions" role="menu">
            <c:choose>
                <c:when test="${param['page'] == 'details' || param['page'] == 'sharing' || param['page'] == 'modules' || param['page'] == 'fields' || param['page'] == 'activitycodes' || param['page'] == 'importFields'}">
                    <li role="menuitem"><a href="javascript:void(0);" id="saveDetails" title="Save Form" role="button"><span class="glyphicon glyphicon-ok icon-stacked"></span> Save </a></li>
                    <li role="menuitem"><a href="javascript:void(0);" id="saveCloseDetails" title="Save &amp; Close" role="button"><span class="glyphicon glyphicon-floppy-disk icon-stacked"></span> Save &amp; Close</a></li>
                </c:when>
          </c:choose>
        </ul>
    </div>
</nav>
