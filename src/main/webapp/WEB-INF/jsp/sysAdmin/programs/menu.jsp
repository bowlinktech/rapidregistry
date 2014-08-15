<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<aside class="secondary">
    <nav class="secondary-nav" role="navigation">
        <ul class="nav nav-pills nav-stacked" role="menu">
            <li role="menuitem" ${param['page'] == 'details' ? 'class="active"' : ''}><a href="${param['page'] != 'details' ? './' : 'javascript:void(0);'}" title="Program Details">Details</a></li>
            <li role="menuitem" ${param['page'] == 'sharing' ? 'class="active"' : ''} ${id > 0 ? '' : 'class="disabled"'}><a href="${param['page'] != 'sharing' && id > 0 ? 'patient-sharing' : 'javascript:void(0);'}" title="Program Patient Sharing">Patient Sharing</a></li>
            <li role="menuitem" ${param['page'] == 'modules' ? 'class="active"' : ''} ${id > 0 ? '' : 'class="disabled"'}><a href="${param['page'] != 'modules' && id > 0 ? 'program-modules' : 'javascript:void(0);'}" title="Program Modules">Program Modules</a></li>
            <li role="menuitem" ${param['page'] == 'demodata' ? 'class="active"' : ''} ${id > 0 ? '' : 'class="disabled"'}><a href="${param['page'] != 'demodata' && id > 0 ? 'demo-data-elements' : 'javascript:void(0);'}" title="Demographic Data Elements">Demo Data Elements</a></li>
            <li role="menuitem" ${param['page'] == 'healthdata' ? 'class="active"' : ''} ${id > 0 ? '' : 'class="disabled"'}><a href="${param['page'] != 'healthdata' && id > 0 ? 'health-data-elements' : 'javascript:void(0);'}" title="Health Data Elements">Health Data Elements</a></li>
            <li role="menuitem" ${param['page'] == 'activitycodes' ? 'class="active"' : ''} ${id > 0 ? '' : 'class="disabled"'}><a href="${param['page'] != 'activitycodes' && id > 0 ? 'activity-codes' : 'javascript:void(0);'}" title="Activity Codes">Activity Codes</a></li>
            <li role="menuitem" ${param['page'] == 'cannedreports' ? 'class="active"' : ''} ${id > 0 ? '' : 'class="disabled"'}><a href="${param['page'] != 'cannedreports' && id > 0 ? 'canned-reports' : 'javascript:void(0);'}" title="Canned Reports">Canned Reports</a></li>
            <li role="menuitem" ${param['page'] == 'extracts' ? 'class="active"' : ''} ${id > 0 ? '' : 'class="disabled"'}><a href="${param['page'] != 'extracts' && id > 0 ? 'extracts-imports' : 'javascript:void(0);'}" title="Extracts / Imports">Extracts / Imports</a></li>
            <li role="menuitem" ${param['page'] == 'mpi' ? 'class="active"' : ''} ${id > 0 ? '' : 'class="disabled"'}><a href="${param['page'] != 'mpi' && id > 0 ? 'mpi-algorithms' : 'javascript:void(0);'}" title="MPI Algorithms">MPI Algorithms</a></li>
            <li role="menuitem" ${param['page'] == 'admins' ? 'class="active"' : ''} ${id > 0 ? '' : 'class="disabled"'}><a href="${param['page'] != 'admins' && id > 0 ? 'program-admins' : 'javascript:void(0);'}" title="Program Admins">Program Admins</a></li>
        </ul>
    </nav>
</aside>