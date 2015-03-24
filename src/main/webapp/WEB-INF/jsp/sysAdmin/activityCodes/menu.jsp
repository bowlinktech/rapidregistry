<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<aside class="secondary">
    <nav class="secondary-nav" role="navigation">
        <ul class="nav nav-pills nav-stacked" role="menu">
            <li role="menuitem" ${param['page'] == 'categories' ? 'class="active"' : ''}><a href="/sysAdmin/activity-codes/categories" title="Activity Code Categories">Categories</a></li>
            <li role="menuitem" ${param['page'] == 'codes' ? 'class="active"' : ''}><a href="/sysAdmin/activity-codes/list" title="Activity Codes">Activity Codes</a></li>
        </ul>
    </nav>
</aside>