
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
            <form:form id="sectionForm" modelAttribute="sectionDetails" method="post" role="form">
                <input type="hidden" id="action" name="action" value="save" />
                <input type="hidden" id="progamNameURL" value="${programName}" />
                <input type="hidden" id="section" name="section" value="${section}" />
                <input type="hidden" name="currdspPos" value="${sectionDetails.dspPos}" />
                <form:hidden path="id" id="id" /> 
                <form:hidden path="programId" />
                <form:hidden path="dateCreated" />
                 <div class="form-group">
                    <spring:bind path="status">
                        <div class="form-group ${status.error ? 'has-error' : '' }">
                            <label class="control-label" for="status">Status *</label>
                            <div>
                                <label class="radio-inline">
                                    <form:radiobutton id="status" path="status" value="true" /> Active
                                </label>
                                <label class="radio-inline">
                                    <form:radiobutton id="status" path="status" value="false" /> Inactive
                                </label>
                            </div>
                        </div>
                    </spring:bind> 
                    <spring:bind path="sectionName">
                        <div class="form-group ${status.error ? 'has-error' : '' }">
                            <label class="control-label" for="sectionName">Section Name *</label>
                            <form:input path="sectionName" id="sectionName" class="form-control" type="text" maxLength="255" />
                            <form:errors path="sectionName" cssClass="control-label" element="label" />
                        </div>
                    </spring:bind> 
                    <spring:bind path="dspPos">
                        <div id="displayPosDiv" class="form-group ${status.error ? 'has-error' : '' }">
                            <label class="control-label" for="dspPos">Display Position * </label>
                            <form:select path="dspPos" id="dspPos" class="form-control half">
                                <c:forEach varStatus="lIndex" begin="1" end="${maxDspPos}">
                                    <option value="${lIndex.index}" <c:if test="${sectionDetails.dspPos == lIndex.index}">selected</c:if>>${lIndex.index}</option>
                                </c:forEach>
                            </form:select>
                            <form:errors path="dspPos" cssClass="control-label" element="label" />
                        </div>
                    </spring:bind> 
                     
                </div>
                <div class="form-group">
                    <input type="button" id="submitSection" role="button" class="btn btn-primary" value="Save"/>
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