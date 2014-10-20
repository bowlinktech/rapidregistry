
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
            <form:form id="hierarchyForm" modelAttribute="hierarchyDetails" method="post" role="form">
                <input type="hidden" id="progamNameURL" value="${programName}" />
                <form:hidden path="id" id="id" />
                <form:hidden path="programId" />
                <form:hidden path="dateCreated" />
                <input type="hidden" name="currdspPos" value="${hierarchyDetails.dspPos}" />
                 <div class="form-group">
                    <spring:bind path="name">
                        <div class="form-group ${status.error ? 'has-error' : '' }">
                            <label class="control-label" for="name">Name *</label>
                            <form:input path="name" id="name" class="form-control" type="text" maxLength="45" />
                            <form:errors path="name" cssClass="control-label" element="label" />
                        </div>
                    </spring:bind> 
                    <spring:bind path="dspPos">
                        <div id="dspPosDiv" class="form-group ${status.error ? 'has-error' : '' }">
                            <label class="control-label" for="dspPos">Display Position * </label>
                            <form:select path="dspPos" id="dspPos" class="form-control half">
                                <option value="0" label=" - Select - " >- Select Display Position -</option>
                                <c:forEach varStatus="lIndex" begin="1" end="${maxDspPos}">
                                    <option value="${lIndex.index}" <c:if test="${hierarchyDetails.dspPos == lIndex.index}">selected</c:if>>${lIndex.index}</option>
                                </c:forEach>
                            </form:select>
                            <span id="dspPosMsg" class="control-label" ></span> 
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