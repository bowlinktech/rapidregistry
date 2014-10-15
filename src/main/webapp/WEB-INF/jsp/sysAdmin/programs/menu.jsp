<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<aside class="secondary">
    <nav class="secondary-nav" role="navigation">
        <ul class="nav nav-pills nav-stacked" role="menu">
            <li role="menuitem" ${param['page'] == 'details' ? 'class="active"' : ''}><a href="${param['page'] != 'details' ? './details' : 'javascript:void(0);'}" title="Program Details">Details</a></li>
            <li role="menuitem" ${param['page'] == 'hierarchy' ? 'class="active"' : ''} ${id > 0 ? '' : 'class="disabled"'}><a href="${param['page'] != 'hierarchy' && id > 0 ? 'organization-hierarchy' : 'javascript:void(0);'}" title="Organization Hierarchy">Organization Hierarchy</a></li>
            <li role="menuitem" ${param['page'] == 'sharing' ? 'class="active"' : ''} ${id > 0 ? '' : 'class="disabled"'}><a href="${param['page'] != 'sharing' && id > 0 ? 'patient-sharing' : 'javascript:void(0);'}" title="Program Patient Sharing">Patient Sharing</a></li>
            <li role="menuitem" ${param['page'] == 'modules' ? 'class="active"' : ''} ${id > 0 ? '' : 'class="disabled"'}><a href="${param['page'] != 'modules' && id > 0 ? 'program-modules' : 'javascript:void(0);'}" title="Program Modules">Program Modules</a></li>
            <li role="menuitem" ${param['page'] == 'patientsections' ? 'class="active"' : ''} ${id > 0 ? '' : 'class="disabled"'}><a href="${param['page'] != 'patientsections' && id > 0 ? 'forms/patient-sections' : 'javascript:void(0);'}" title="Patient Detail Sections">Patient Detail Sections</a></li>
            <li role="menuitem" ${param['page'] == 'engagementsections' ? 'class="active"' : ''} ${id > 0 ? '' : 'class="disabled"'}><a href="${param['page'] != 'engagementsections' && id > 0 ? 'forms/engagement-sections' : 'javascript:void(0);'}" title="Engagement Sections">Engagement Sections</a></li>
            <li role="menuitem" ${param['page'] == 'activitycodes' ? 'class="active"' : ''} ${id > 0 ? '' : 'class="disabled"'}><a href="${param['page'] != 'activitycodes' && id > 0 ? 'activity-codes' : 'javascript:void(0);'}" title="Activity Codes">Activity Codes</a></li>
            <li role="menuitem" ${param['page'] == 'cannedreports' ? 'class="active"' : ''} ${id > 0 ? '' : 'class="disabled"'}><a href="${param['page'] != 'cannedreports' && id > 0 ? 'canned-reports' : 'javascript:void(0);'}" title="Canned Reports">Canned Reports</a></li>
            <li role="menuitem" ${param['page'] == 'extracts' ? 'class="active"' : ''} ${id > 0 ? '' : 'class="disabled"'}><a href="${param['page'] != 'extracts' && id > 0 ? 'extracts-imports' : 'javascript:void(0);'}" title="Extracts / Imports">Extracts / Imports</a></li>
            <li role="menuitem" ${param['page'] == 'mci' ? 'class="active"' : ''} ${id > 0 ? '' : 'class="disabled"'}><a href="${param['page'] != 'mpi' && id > 0 ? 'mci-algorithms' : 'javascript:void(0);'}" title="MCI Algorithms">MCI Algorithms</a></li>
            <li role="menuitem" ${param['page'] == 'admins' ? 'class="active"' : ''} ${id > 0 ? '' : 'class="disabled"'}><a href="${param['page'] != 'admins' && id > 0 ? 'program-admins' : 'javascript:void(0);'}" title="Program Admins">Program Admins</a></li>
        </ul>
    </nav>
</aside>