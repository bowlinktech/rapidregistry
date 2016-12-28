<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


      <div class="col-md-12">
          <section class="panel panel-default">
          	  
              <div class="panel-heading" <c:if test="${crossTab.statusId == 1}">style="background-color: #4B7C88"</c:if> <c:if test="${crossTab.statusId == 2}">style="background-color: #efefef"</c:if>>
              <div class="pull-right">
       				 <a href="#reportModal" data-toggle="modal" class="btn btn-primary btn-xs btn-action removeCrossTabTable" rel="${crossTab.id}" rel1="${crossTab.reportId}" title="Delete this Table">Delete this Table</a>
    			</div>
              <a href="#reportModal" data-toggle="modal" rel="${crossTab.id}" title="Table Details" class="panel-title unstyled-link crossTabTitle" id="crossTabTitleHref_${crossTab.id}" <c:if test="${crossTab.statusId == 1}">style="color:#ffffff"</c:if> <c:if test="${crossTab.statusId == 2}">style="color:#000000"</c:if>>
                <span id="ctTableSpan_${crossTab.id}">${crossTab.tableTitle}</span><span class="glyphicon glyphicon-edit" style="padding-left:5px; cursor: pointer;"></span>
              </a>
              <c:if test="${crossTab.statusId == 2}">&nbsp;&nbsp;(INACTIVE)</c:if>
              <span id="crossTabId" class="${newCrossTab}" style="display:none;">${crossTab.id}</span>
            </div>
            <div class="panel-body">
	                    <div class="form-container">
	                    	<%-- cross table tab form --%>
	                    	<form id="cwDataForm_${crossTab.id}" method="post">
	                    	<table class="table table-striped table-hover table-default">
            <thead>
            	<tr>
            	<th scope="col" class="text-center" colspan="${fn:length(crossTab.cwDataRow)+ 2}">${crossTab.tableTitle}
            		<br/>Selection Data <i>(${hierarchyList[0].name}, ${hierarchyList[1].name}, Date selection, etc...)</i>
            	</th>
            	</tr>
                <tr>
                	<td>&nbsp;</td>
                	 <c:forEach var="rowData" items="${crossTab.cwDataRow}">
                   		 <th scope="col" class="text-center">${rowData.descValue}</th>
                    </c:forEach>
                   		 <th scope="col" class="text-center">${crossTab.labelRow}</th>
                </tr>
            </thead>
           <tbody>
             <c:choose>
                <c:when test="${not empty crossTab.cwDataCol}">
                    <c:forEach var="colData" items="${crossTab.cwDataCol}">
                        <tr id="col_${colData.id}_${rowData.id}">
                            <th scope="row">
                              ${colData.descValue}
                            </th>
                            <c:forEach var="formRowData" items="${crossTab.cwDataRow}">
                            	<td class="text-center"><input type="checkbox" id="cwData_${colData.id}_${formRowData.id}" name="cwDataSet" value="${colData.id}_${formRowData.id}"/></td> 
                            </c:forEach>
                          	<td class="text-center">
                                -- 
                            </td>
                        </tr>
                    </c:forEach>
                    <tr id="col_${colData.id}_${rowData.id}">
                            <th scope="row">
                             ${crossTab.labelCol}
                            </th>
                            <c:forEach var="colData" items="total_${crossTab.cwDataRow}">
                            	<td class="text-center">--</td> 
                            </c:forEach>
                          	<td class="text-center">
                                &nbsp; --
                            </td>
                        </tr>
                </c:when>
                <c:otherwise>
                    <tr><td colspan="8" class="text-center">There are currently no items set up for this ${entityName}.</td></tr>
                </c:otherwise>
            </c:choose>
            </tbody>
        </table>
		<div class="form-group">
                            <input type="button" id="cwSaveButton_${crossTab.id}" rel="${crossTab.id}" role="button" class="btn btn-primary saveCWButton" value="Save"/>
                            <input type="button" id="cwResetButton_${crossTab.id}" rel="${crossTab.reportId}" role="button" class="btn btn-danger cancelCWButton" value="Cancel"/>
                            
                        </div>
                        	<input type="hidden" name="cwCrossTabId" value="${crossTab.id}"/>
	                    	</form>
	                    
	                    </div>
                    </div>
       </section>
    </div>  

<script type="text/javascript">
require(['./main'], function () {
	require(['jquery', 'multiselect'], function($) {
	
var crossTabId = ${crossTab.id};
populateTableColumns(crossTabId);

function populateTableColumns(crossTabId) {
	
	$.getJSON('getCWDataList.do', {
        crossTabId: crossTabId, ajax: true
    }, function(data) {
        var len = data.length;
        
        for (var i = 0; i < len; i++) {
        	$('#cwData_' + data[i]).prop('checked', true);
        }
       
    });
}

	});
});
</script>
