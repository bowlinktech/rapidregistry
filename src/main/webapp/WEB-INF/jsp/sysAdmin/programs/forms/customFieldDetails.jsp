<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h3 class="panel-title"><c:choose><c:when test="${customField.id > 0}">View</c:when><c:when test="${btnValue == 'Create'}">Add</c:when></c:choose> Custom Field ${success}</h3>
                </div>
                <div class="modal-body">

            <form:form id="customfielddetailsform" commandName="customField" modelAttribute="customField" method="post" role="form">
                <form:hidden path="id" id="id" />
                <form:hidden path="programId" id="programId" />
                <input type="hidden" name="sectionIdVal" id="sectionIdVal" />
                <input type="hidden" id="frompage" name="frompage" value="${frompage}" />
                <div class="form-container">
                    <div class="form-group">
                        <spring:bind path="fieldName">
                            <div id="fieldNameDiv" class="form-group ${status.error ? 'has-error' : '' }">
                                <label class="control-label" for="fieldName">Field Name *</label>
                                <form:input path="fieldName" id="fieldName" class="form-control" type="text" maxLength="255" />
                                <form:errors path="fieldName" cssClass="control-label" element="label" />
                                <span id="fieldNameMsg" class="control-label"></span>
                            </div>
                        </spring:bind>
                    </div>
                    <div class="form-group">
                        <spring:bind path="answerType">
                            <label class="control-label" for="name">Field Type *</label>
                            <form:select path="answerType" id="answerType" class="form-control half">
                                <option value="">- Select -</option>
                                <c:forEach items="${answerTypes}" var="atype" varStatus="aStatus">
                                    <option value="${answerTypes[aStatus.index][0]}"  <c:if test="${fn:toLowerCase(customField.answerType) == fn:toLowerCase(answerTypes[aStatus.index][0])}">selected</c:if>>${answerTypes[aStatus.index][1]} </option>
                                </c:forEach>
                            </form:select>
                        </spring:bind>
                    </div>
                    <div class="form-group" id="allowMultipleDiv" style="display:none">
                        <form:checkbox path="allowMultipleAns" id="allowMultipleAns" />&nbsp;<label class="control-label" for="allowMultipleAns">Allow more than one answer to this question (use checkboxes)</label>
                    </div>
                    <div class="form-group">
                        <spring:bind path="saveToTable">
                            <div id="saveToTableDiv" class="form-group ${status.error ? 'has-error' : '' }">
                                <label class="control-label" for="saveToTable">Table Name *</label>
                                <form:select path="saveToTable" id="saveToTable" class="form-control half tableName">
                                    <option value="" label=" - Select - " ></option>
                                    <c:forEach items="${infoTables}"  var="infotablenames" varStatus="tname">
                                        <option value="${infoTables[tname.index]}" <c:if test="${fn:toLowerCase(customField.saveToTable) == fn:toLowerCase(infoTables[tname.index])}">selected</c:if>>${infoTables[tname.index]}</option>
                                    </c:forEach>
                                </form:select>
                               <form:errors path="saveToTable" cssClass="control-label" element="label" />    
                               <span id="saveToTableMsg" class="control-label"></span>
                            </div>
                        </spring:bind>
                    </div>
                    <div class="form-group">
                        <spring:bind path="saveToTableCol">
                            <div id="saveToTableColDiv" class="form-group ${status.error ? 'has-error' : '' }">
                                <label class="control-label" for="saveToTableCol">Table Column Name *</label>
                                <form:select path="saveToTableCol" id="saveToTableCol" class="form-control half" rel="${customField.saveToTableCol}">
                                    <option value="" label=" - Select - " ></option>
                                </form:select>
                                <form:errors path="saveToTableCol" cssClass="control-label" element="label" /> 
                                <span id="saveToTableColMsg" class="control-label"></span>
                            </div>
                        </spring:bind>
                    </div>
                </div>
                <div class="form-group">
                    <input type="button" id="submitCustomField" class="btn btn-primary" value="Submit"/>
                </div>
            </form:form>
        </div>
    </div>
</div>

<script type="text/javascript">

    $(document).ready(function() {
        $("input:text,form").attr("autocomplete", "off");
        
        //Loop through all selected table names to populate the columns
        var currTableName = "";
        var currTableCols = [];
        
        $('.tableName').each(function() {
            var row = $(this).attr('rel');
            var tableName = $(this).val();
           
            if (tableName !== "") {

                if ((currTableName == "" || tableName != currTableName)) {
                    currTableName = tableName;
                    
                    var colVal = $('#saveToTableCol').attr('rel');

                    currTableCols = populateTableColumns(tableName,colVal);
                }

            }
        });
        
        //Need to populate the table columns or the selected table
        $(document).on('change', '.tableName', function() {
            var tableName = $(this).val();
            populateTableColumns(tableName,"");
        });
        
         //Need to populate the table columns or the selected table
        $(document).on('change', '#answerType', function() {
            if($('#answerType').val() == 1) {
                $('#allowMultipleDiv').show();
            }
            else {
                $('#allowMultipleDiv').hide();
            }
        });
        
       
    });
    
    //This functin will be used to populate the tableCols drop down.
    //function takes in the name of the selected table name and the
    //row it is working with.
    function populateTableColumns(tableName, colVal) {
        $.getJSON('/sysAdmin/data-elements/getTableCols.do', {
            tableName: tableName, ajax: true
        }, function(data) {
            var html = '<option value="0">- Select - </option>';
            var len = data.length;
            
            for (var i = 0; i < len; i++) {
              
                if (colVal.toLowerCase() == data[i].toLowerCase()) {
                    html += '<option value="' + data[i] + '" selected>' + data[i] + '</option>';
                }
                else {
                    html += '<option value="' + data[i] + '">' + data[i] + '</option>';
                }
        
            }
            $('#saveToTableCol').html(html);
        });
    }

</script>