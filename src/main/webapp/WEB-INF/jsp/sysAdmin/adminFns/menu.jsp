<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<aside class="secondary">
    <nav class="secondary-nav" role="navigation">
        <ul class="nav nav-pills nav-stacked" role="menu">
            <li role="menuitem" ${param['page'] == 'loginas' ? 'class="active"' : ''}><a href="/sysAdmin/adminFns/loginas" title="Login as User">Login as User</a></li>
        </ul>
        <ul class="nav nav-pills nav-stacked" role="menu">
            <li role="menuitem" ${param['page'] == 'importfile' ? 'class="active"' : ''}><a href="/sysAdmin/adminFns/importfile" title="Import File as User">Import File as User</a></li>
        </ul>
    </nav>
</aside>