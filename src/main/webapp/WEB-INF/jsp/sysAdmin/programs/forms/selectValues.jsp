<%-- 
    Document   : selectValues
    Created on : Jan 16, 2015, 10:31:46 AM
    Author     : chadmccue
--%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h3 class="panel-title">Select the values to use for the ${fieldName} field ${success}</h3>
        </div>
        <div class="modal-body">
            <form id="fieldValueForm" method="post" role="form">
                <input type="hidden" id="section" name="section" value="${section}" />
                <input type="hidden" id="fieldId" name="fieldId" value="${fieldId}" />
                <div class="form-container">
                    <c:if test="${selectedFieldIds.size() != tableValues.size()}">
                        <div class="form-group">
                            <label class="checkbox-inline" for="sendEmailAlert">
                                <input type="checkbox" class="selectAllFieldValues" value="${tableValues[tValue.index][0]}" />Select All
                            </label>
                            <hr />
                        </div>
                    </c:if>
                    <c:forEach items="${tableValues}" var="tableValue" varStatus="tValue">
                        <div class="form-group">
                            <label class="checkbox-inline" for="sendEmailAlert">
                                <input type="checkbox" class="valueCheckbox" name="seltableValue" value="${tableValues[tValue.index][0]}" <c:if test="${ fn:contains(selectedFieldIds, tableValues[tValue.index][0])}">checked</c:if> /><label for="${tableValues[tValue.index][1]} ">${tableValues[tValue.index][1]} </label>
                            </label>
                        </div>
                    </c:forEach>
                </div>
                <div class="form-group">
                    <input type="button" id="submitFieldValuesButton"  class="btn btn-primary" value="Save Selections"/>
                </div>
             </form>
        </div>
    </div>
</div>
