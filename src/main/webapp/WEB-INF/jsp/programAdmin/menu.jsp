<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<aside class="secondary">
    <nav class="secondary-nav" role="navigation">
        <ul class="nav nav-pills nav-stacked" role="menu">
            <li role="menuitem" ${param['page'] == 'surveys' ? 'class="active"' : ''}><a href="/programAdmin/surveys" title="Surveys">Survey Management</a></li>
            <li role="menuitem" ${param['page'] == 'staff' ? 'class="active"' : ''}><a href="/programAdmin/staff" title="Staff Members">User Management</a></li>
            <li role="menuitem" ${param['page'] == 'entity' ? 'class="active"' : ''}><a href="/programAdmin/entity" title="Entity Management">Entity Management</a></li>
            <li role="menuitem" ${param['page'] == 'entity' ? 'style="display:block;"' : 'style="display:none;"'}>
                <div class="list-group">
                    <c:forEach var="entity" items="${entities}">
                        <a href="javascript:void(0);" class="loadEntities list-group-item ${selEntity == entity.id ? 'entityactive entity' : 'entity'}" rel="${entity.id}" title="${entity.name}">${entity.name}</a>
                    </c:forEach>
                </div>
            </li>
            <li role="menuitem" ${param['page'] == 'categories' ? 'class="active"' : ''}><a href="/programAdmin/services/categories" title="Service Categories">Service Categories</a></li>
            <li role="menuitem" ${param['page'] == 'services' ? 'class="active"' : ''}><a href="/programAdmin/services" title="Services">Services</a></li>
            <%--<li role="menuitem" ${param['page'] == 'schedules' ? 'class="active"' : ''}><a href="/programAdmin/client-schedules" title="Surveys">Client Schedules</a></li>
            <li role="menuitem" ${param['page'] == 'calendar' ? 'class="active"' : ''}><a href="/programAdmin/calendar" title="Inactive Day Calendar">Inactive Day Calendar</a></li>--%>
        </ul>
    </nav>
</aside>