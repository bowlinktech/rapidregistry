
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<aside class="secondary" style="margin-left:10px; width:250px;">
    <div class="sidebar list-group" >
        <c:forEach var="answerType" items="${answerTypeList}" varStatus="loopCount">
            <div class="list-group-item list-group-item-warning padding-sm QuestionTypeDiv" rel="${answerType.id}">
                <a href="javascript:void(0);" class="addQuestionType" rel="${answerType.id}" style="text-decoration:none; color: #000000">
                <strong>${answerType.answerType}</strong></a>
               <button style="display:none;" type="button" id="answertype_${answerType.id}" class="pull-right btn btn-primary btn-xs addQuestionType addQuestionTypeBtn" rel="${answerType.id}">+ Add</button> 
            </div>
        </c:forEach>
    </div>
</aside>

