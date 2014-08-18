<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<aside class="secondary">
    <nav class="secondary-nav" role="navigation">
        <ul class="nav nav-pills nav-stacked" role="menu">
            <li role="menuitem" ${param['page'] == 'demo' ? 'class="active"' : ''}><a href="${param['page'] != 'details' ? 'demo-fields' : 'javascript:void(0);'}" title="Demographic Fields">Demographic Fields</a></li>
            <li role="menuitem" ${param['page'] == 'health' ? 'class="active"' : ''}><a href="${param['page'] != 'health' ? 'health-fields' : 'javascript:void(0);'}" title="Health Fields">Health Fields</a></li>
        </ul>
    </nav>
</aside>