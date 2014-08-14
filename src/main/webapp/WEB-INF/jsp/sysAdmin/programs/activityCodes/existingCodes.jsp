
<link rel="stylesheet" href="/dspResources/css/bootstrap-duallistbox.css" type="text/css" media="screen">
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<div class="main clearfix" role="main">
    <div class="row-fluid">
        <div class="col-md-12">
            <c:if test="${not empty savedStatus}" >
                <div class="alert alert-success" role="alert">
                    <strong>Success!</strong> 
                    <c:choose>
                        <c:when test="${savedStatus == 'codesupdated'}">The activity codes have been successfully added!</c:when>
                        <c:when test="${savedStatus == 'created'}">The program has been successfully added!</c:when>
                    </c:choose>
                </div>
            </c:if>
            <section class="panel panel-default">
                <div class="panel-body">
                    <dt>
                    <dd><strong>Program Summary:</strong></dd>
                    <dd><strong>Program Name:</strong> ${programDetails.programName}</dd>
                    </dt>
                </div>
            </section>
        </div>
    </div>
    <div class="row-fluid">
        <div class="col-md-12">
            <section class="panel panel-default">
                <div class="panel-body">
                    <div class="form-container scrollable">

                        <form id="activityCodeForm" action="" method="post">
                            <input type="hidden" name="activityCodeList" id="selCodes" value="" />
                            <input type="hidden" id="action" name="action" value="save" />

                            <select multiple="multiple" size="15" name="activitycodelistbox[]">
                                <c:choose>
                                    <c:when test="${not empty availactivityCodes}">
                                        <c:forEach var="activityCodes" items="${availactivityCodes}">
                                            <option value="${activityCodes.id}" ${activityCodes.selected == 'true' ? 'selected' : ''}>${activityCodes.code}</option>
                                        </c:forEach>
                                    </c:when>
                                </c:choose>   
                            </select>

                        </form>
                   </div>
              </div>
            </section>
        </div>
    </div>
</div>