<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<aside class="secondary">
    <nav class="secondary-nav" role="navigation">
        <ul class="nav nav-pills nav-stacked" role="menu">
            <li role="menuitem" ${param['page'] == 'details' ? 'class="active"' : ''}><a href="/sysAdmin/programs/${sessionScope.programName}/details" title="Program Details">Details</a></li>
            <li role="menuitem" ${param['page'] == 'hierarchy' ? 'class="active"' : ''} ${id > 0 ? '' : 'class="disabled"'}><a href="/sysAdmin/programs/${sessionScope.programName}/organization-hierarchy" title="Organization Profiles">Organization Profiles</a></li>
            <li role="menuitem" ${param['page'] == 'sharing' ? 'class="active"' : ''} ${id > 0 ? '' : 'class="disabled"'}><a href="/sysAdmin/programs/${sessionScope.programName}/patient-sharing" title="Program Patient Sharing">Patient Sharing</a></li>
            <li role="menuitem" ${param['page'] == 'modules' ? 'class="active"' : ''} ${id > 0 ? '' : 'class="disabled"'}><a href="/sysAdmin/programs/${sessionScope.programName}/program-modules" title="Program Modules">Program Modules</a></li>
            <li role="menuitem" ${param['page'] == 'patientsections' ? 'class="active"' : ''} ${id > 0 ? '' : 'class="disabled"'}><a href="/sysAdmin/programs/${sessionScope.programName}/forms/patient-sections" title="Patient Detail Sections">Patient Detail Sections</a></li>
            <li role="menuitem" ${param['page'] == 'engagementsections' ? 'class="active"' : ''} ${id > 0 ? '' : 'class="disabled"'}><a href="/sysAdmin/programs/${sessionScope.programName}/forms/engagement-sections" title="Engagement Sections">Engagement Sections</a></li>
            <li role="menuitem" ${param['page'] == 'programProfileFields' ? 'class="active"' : ''} ${id > 0 ? '' : 'class="disabled"'}><a href="/sysAdmin/programs/${sessionScope.programName}/forms/programProfile/fields" title="Program Profile Fields">Program Profile Fields</a></li>
            <li role="menuitem" ${param['page'] == 'activitycodes' ? 'class="active"' : ''} ${id > 0 ? '' : 'class="disabled"'}><a href="/sysAdmin/programs/${sessionScope.programName}/activity-codes" title="Activity Codes">Activity Codes</a></li>
            <li role="menuitem" ${param['page'] == 'cannedreports' ? 'class="active"' : ''} ${id > 0 ? '' : 'class="disabled"'}><a href="/sysAdmin/programs/${sessionScope.programName}/canned-reports" title="Canned Reports">Canned Reports</a></li>
            <li role="menuitem" ${param['page'] == 'aggreports' ? 'class="active"' : ''} ${id > 0 ? '' : 'class="disabled"'}><a href="/sysAdmin/programs/${sessionScope.programName}/aggregated-reports" title="Aggregated Reports">Aggregated Reports</a></li>
            <li role="menuitem" ${param['page'] == 'imports' ? 'class="active"' : ''} ${id > 0 ? '' : 'class="disabled"'}><a href="/sysAdmin/programs/${sessionScope.programName}/imports" title="Imports">Imports</a></li>
            <li role="menuitem" ${param['page'] == 'admins' ? 'class="active"' : ''} ${id > 0 ? '' : 'class="disabled"'}><a href="/sysAdmin/programs/${sessionScope.programName}/program-admins" title="Program Admins">Program Admins</a></li>
            <li role="menuitem" ${param['page'] == 'crosswalks' ? 'class="active"' : ''} ${id > 0 ? '' : 'class="disabled"'}><a href="/sysAdmin/programs/${sessionScope.programName}/crosswalks" title="crosswalks">Crosswalks</a></li>
            <c:if test="${sessionScope.programId == 13}">
                <li role="menuitem" ${param['page'] == 'environmentalStrategies' ? 'class="active"' : ''} ${id > 0 ? '' : 'class="disabled"'}><a href="/sysAdmin/programs/${sessionScope.programName}/environmentalStrategies" title="Environmental Strategies">Environmental Strategies</a></li>
            </c:if>
        </ul>
    </nav>
</aside>