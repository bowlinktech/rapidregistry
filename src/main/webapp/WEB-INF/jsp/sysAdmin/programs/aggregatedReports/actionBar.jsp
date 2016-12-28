<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav class="navbar navbar-default actions-nav" role="navigation">
    <div class="contain">
        <div class="navbar-header">
            <h1 class="section-title navbar-brand">
                <c:choose>
                    <c:when test="${param['page'] == 'details'}">
                       <a href="javascript:void(0);" title="Aggregated Report Details" class="unstyled-link" id="reportTitleHref"><span id="actionBarReportTitle">${report.reportName}</span>&nbsp;&nbsp;<span class="glyphicon glyphicon-edit" style="cursor: pointer;"></span></a>
                        <div id="reportTitleEdit" style="margin: 10px 0 0 0;display:none;">
                        <form class="form-inline">
                            <div id="reportTitleEditDiv" class="form-group">
                                <input type="text" id="reportTitle" class="form-control" value="${report.reportName}" />
                            </div>
                            <button type="button" rel="${report.id}" class="btn btn-default submitReportTitleChange">Save</button>
                        </form>
                    </div>
                    </c:when>                       
                </c:choose>
            </h1>
        </div>
        <ul class="nav navbar-nav navbar-right navbar-actions" role="menu">
           <c:choose>
                <c:when test="${param['page'] == 'details'}">
                 <li role="menuitem"><a href="../aggregated-reports" title="Cancel" role="button"><span class="glyphicon glyphicon-ban-circle icon-stacked"></span> Exit Report Builder</a></li></c:when>
          </c:choose>
        </ul>
    </div>
</nav>
