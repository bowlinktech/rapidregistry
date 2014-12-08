<%-- 
    Document   : addNewField
    Created on : Nov 25, 2013, 10:47:00 AM
    Author     : chadmccue
--%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h3 class="panel-title">Add New Field ${success}</h3>
        </div>
        <div class="modal-body">
            <div class="form-container">
            <form:form id="fieldForm" modelAttribute="dataElementFormFields" method="post" role="form">
                <form:hidden path="id" />
                <div class="form-group">
                    <spring:bind path="status">
                        <div id="statusDiv" class="form-group ${status.error ? 'has-error' : '' }">
                            <label class="control-label" for="status">Status *</label>
                            <form:select path="status" id="status" class="form-control half">
                                <option value="1">Active</option>
                                <option value="0">Inactive</option>
                            </form:select>
                           <span id="statusMsg" class="control-label" ></span>         
                        </div>
                    </spring:bind>
                </div>
                <div class="form-group">
                    <spring:bind path="elementName">
                        <div id="elementNameDiv" class="form-group ${status.error ? 'has-error' : '' }">
                            <label class="control-label" for="elementName">Element Name *</label>
                            <form:input path="elementName" id="elementName" class="form-control" type="text" maxLength="45" />
                            <form:errors path="elementName" cssClass="control-label" element="label" />
                        </div>
                    </spring:bind>
                </div>
                <div class="form-group">
                    <spring:bind path="answerType">
                        <label class="control-label" for="name">Field Type *</label>
                        <form:select path="answerType" class="form-control half">
                            <option value="">- Select -</option>
                            <c:forEach items="${answerTypes}" var="atype" varStatus="aStatus">
                                <option value="${answerTypes[aStatus.index][0]}"  <c:if test="${fn:toLowerCase(dataElementFormFields.answerType) == fn:toLowerCase(answerTypes[aStatus.index][0])}">selected</c:if>>${answerTypes[aStatus.index][1]} </option>
                            </c:forEach>
                        </form:select>
                    </spring:bind>
                </div>
                <div class="form-group">
                    <spring:bind path="saveToTableName">
                        <div id="saveToTableNameDiv" class="form-group ${status.error ? 'has-error' : '' }">
                            <label class="control-label" for="saveToTableName">Table Name *</label>
                            <form:select path="saveToTableName" id="saveToTableName" class="form-control half tableName">
                                <option value="" label=" - Select - " ></option>
                                <c:forEach items="${infoTables}"  var="infotablenames" varStatus="tname">
                                    <option value="${infoTables[tname.index]}" <c:if test="${fn:toLowerCase(dataElementFormFields.saveToTableName) == fn:toLowerCase(infoTables[tname.index])}">selected</c:if>>${infoTables[tname.index]}</option>
                                </c:forEach>
                            </form:select>
                           <form:errors path="saveToTableName" cssClass="control-label" element="label" />      
                        </div>
                    </spring:bind>
                </div>
                <div class="form-group">
                    <spring:bind path="saveToTableCol">
                        <div id="saveToTableColDiv" class="form-group ${status.error ? 'has-error' : '' }">
                            <label class="control-label" for="saveToTableCol">Table Column Name *</label>
                            <form:select path="saveToTableCol" id="saveToTableCol" class="form-control half" rel="${dataElementFormFields.saveToTableCol}">
                                <option value="" label=" - Select - " ></option>
                            </form:select>
                            <form:errors path="saveToTableCol" cssClass="control-label" element="label" />     
                        </div>
                    </spring:bind>
                </div>
                <div class="form-group">
                    <input type="button" id="submitField" class="btn btn-primary" value="Submit"/>
                </div>
            </form:form>
            </div>
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
        
        
        //Need to submit the form
        $('#submitNewField').click(function() {
           var errorsFound = 0;
           
           errorsFound = checkformFields();
           
           if (errorsFound === 0) {
               $("#newFieldForm").submit();
           }
        });
    });
    
    //This function will make sure all the required fields are entered with a  value
    function checkformFields() {
        var errorsFound = 0;
        
        //Remove all errors
        $('div.form-group').removeClass("has-error");
        $('span.control-label').removeClass("has-error");
        $('span.control-label').html("");
        
        if($('#elementName').val() === "") {
            $('#elementNameDiv').addClass("has-error");
            $('#elementNameMsg').html("The element name is required!");
            errorsFound = 1;
        }
        if($('#saveToTableName').val() === "0") {
            $('#saveToTableNameDiv').addClass("has-error");
            $('#saveToTableNameMsg').html("The table name is required!");
            errorsFound = 1;
        }
         if($('#saveToTableCol').val() === "0") {
            $('#saveToTableColDiv').addClass("has-error");
            $('#saveToTableColMsg').html("The table col is required!");
            errorsFound = 1;
        }
        
        return errorsFound;
    }
    
    //This functin will be used to populate the tableCols drop down.
    //function takes in the name of the selected table name and the
    //row it is working with.
    function populateTableColumns(tableName, colVal) {
        $.getJSON('getTableCols.do', {
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

