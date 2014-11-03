<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<aside class="secondary">
    <nav class="secondary-nav" role="navigation">
        <ul class="nav nav-pills nav-stacked" role="menu">
            <li role="menuitem" ${param['page'] == 'surveys' ? 'class="active"' : ''}><a href="/programAdmin/surveys" title="Surveys">Surveys</a></li>
            <li role="menuitem" ${param['page'] == 'staff' ? 'class="active"' : ''}><a href="/programAdmin/staff" title="Staff Members">Staff Members</a></li>
            <li role="menuitem" ${param['page'] == 'schedules' ? 'class="active"' : ''}><a href="/programAdmin/client-schedules" title="Surveys">Client Schedules</a></li>
            <li role="menuitem" ${param['page'] == 'calendar' ? 'class="active"' : ''}><a href="/programAdmin/calendar" title="Inactive Day Calendar">Inactive Day Calendar</a></li>
        </ul>
    </nav>
</aside>