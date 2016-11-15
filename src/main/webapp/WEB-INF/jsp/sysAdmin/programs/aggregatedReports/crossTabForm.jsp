<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h3 class="panel-title">${btnValue} Cross Tab Details</h3>
            <span id="message" style="display:none;">${message}</span>
                </div>
            <form:form id="details" commandName="details" method="post" role="form">
            <input type="hidden" id="action" name="action" value="save" />
            <form:hidden path="id" id="id" />
            <form:hidden path="reportId"  id="reportId"/>
            <form:hidden path="dspPos"  id="dspPos"/>

                <div class="panel-body">
                    <div class="form-container">
                        <spring:bind path="tableTitle">
                            <div class="form-group ${status.error ? 'has-error' : '' }">
                                <label class="control-label" for="tableTitle">Table Title *</label>
                                <form:input path="tableTitle" id="tableTitle" class="form-control" type="text" maxLength="100" />
                                <form:errors path="tableTitle" cssClass="control-label" element="label" />
                            </div>
                        </spring:bind>
                        <div class="form-group">
                                <label for="status">Status *</label>
                                <div>
                                    <label class="radio-inline">
                                        <form:radiobutton id="statusId" path="statusId" value="1" /> Active
                                    </label>
                                    <label class="radio-inline">
                                        <form:radiobutton id="statusId" path="statusId" value="2" /> Inactive
                                    </label>
                                </div>
                            </div>
                        <spring:bind path="cwIdCol">
                            <div class="form-group ${status.error ? 'has-error' : '' }">
                                <label class="control-label" for="cwIdCol">Row Crosswalk *</label>
                                <form:select id="cwIdCol" path="cwIdCol" cssClass="form-control half">
                                        <option value="" label=" - Select - " ></option>
                                         <form:options items="${crosswalks}" itemValue="id" itemLabel="name"/>
                                    </form:select><form:errors path="cwIdCol" cssClass="control-label" element="label" />
                            </div>
                        </spring:bind>   
                        <spring:bind path="cwIdRow">
                            <div class="form-group ${status.error ? 'has-error' : '' }">
                                <label class="control-label" for="cwIdRow">Column Crosswalk *</label>
                                <form:select id="cwIdRow" path="cwIdRow" cssClass="form-control half">
                                        <option value="" label=" - Select - " ></option>
                                       <form:options items="${crosswalks}" itemValue="id" itemLabel="name"/>
                                    </form:select><form:errors path="cwIdRow" cssClass="control-label" element="label" />
                            </div>
                        </spring:bind>   
                        <div class="form-group">
                            <input type="button" id="submitButton" rel1="${details.id}" rel="${btnValue}" role="button" class="btn btn-primary" value="${btnValue}"/>
                        </div>
                    </div>
                </div>
       </form:form>
                    
        </div>
    </div>

<script type="text/javascript">

    $(document).ready(function() {
        $("input:text,form").attr("autocomplete", "off");
    });
</script>

