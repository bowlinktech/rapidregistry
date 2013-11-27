<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<aside class="secondary">
	<nav class="secondary-nav" role="navigation">
		<ul class="nav nav-pills nav-stacked">
			<li ${param['page'] == 'table' ? 'class="active"' : ''} ${id > 0 ? '' : 'class="disabled"'}><a href="<c:url value='/administrator/sysadmin/list'/>" title="Table Data">Look Up Tables</a></li>
			<li ${param['page'] == 'macros' ? 'class="active"' : ''} ${id > 0 ? '' : 'class="disabled"'}><a href="<c:url value='/administrator/sysadmin/macroList'/>" title="Macros">Macros</a></li>
			<li ${param['page'] == 'logo' ? 'class="active"' : ''} ${id > 0 ? '' : 'class="disabled"'}><a href="<c:url value='/administrator/sysadmin/logo'/>" title="Logo">Logo</a></li>
		</ul>
	</nav>
</aside>