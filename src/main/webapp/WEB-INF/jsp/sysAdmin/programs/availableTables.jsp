
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h3 class="panel-title">${modalTitle} ${success}</h3>
        </div>
        <div class="modal-body">
            <form:form id="availableTable" commandName="programAvailableTables"  method="post" role="form">
                <input type="hidden" id="action" name="action" value="save" />
                <input type="text" id="progamNameURL" value="${programName}" />
                <form:hidden path="id" id="id" />
                <form:hidden path="programId" />
                 <div class="form-group">
                    <spring:bind path="tableName">
                        <div id="tableNameDiv" class="form-group ${status.error ? 'has-error' : '' }">
                            <label class="control-label" for="tableName">Table Name *</label>
                            <form:select path="tableName" id="tableName" class="form-control half tableName">
                                <option value="0" label=" - Select - " ></option>
                                <c:forEach items="${tables}" varStatus="tname">
                                    <option value="${tables[tname.index]}" <c:if test="${fn:toLowerCase(availableTable.tableName) == fn:toLowerCase(tables[tname.index])}">selected</c:if>>${tables[tname.index]}</option>
                                </c:forEach>
                            </form:select>
                           <span id="tableNameMsg" class="control-label" ></span>         
                        </div>
                    </spring:bind>
                </div>
                <div class="form-group">
                    <input type="button" id="submitButton" role="button" class="btn btn-primary" value="Save"/>
                </div>
            </form:form>
        </div>
    </div>
</div>

<script type="text/javascript">

    $(document).ready(function() {
        $("input:text,form").attr("autocomplete", "off");
    });

</script>