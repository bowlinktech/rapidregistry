
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<aside class="secondary" style="margin-left:10px;">
    <div>
        <section class="panel panel-info">
            <div class="panel-heading">
                <h3 class="panel-title">Survey Builder</h3>
            </div>
            <div class="panel-body" style="padding:5px;">
                <c:forEach var="answerType" items="${answerTypeList}" varStatus="loopCount">
                <div style="padding: 2px;">
                    <span class="text">${answerType.answerType}</span>
                    <span class="pull-right" style="cursor:pointer; padding-right: 2px">
                        <a href="" id="addAQuestion" rel="${answerType.id}"><span class="glyphicon glyphicon-plus-sign"></span></a>
                    </span>
                    <c:if test="${!loopCount.last}"><hr /></c:if>
                </div>
                </c:forEach>
            </div>
        </section>
    </div>
</aside>