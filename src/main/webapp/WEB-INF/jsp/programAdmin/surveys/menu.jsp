<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<aside class="secondary">
<div class="table-responsive">
  <table class="table table-bordered table-hovered">
    <tr><td colspan="2">Survey Builder</td></tr>
    <!--  loop and display answer choices -->
    <c:forEach var="answerType" items="${answerTypeList}">
		<tr><td>${answerType.answerType}</td>
		<td class="text-center"><a href="" id="addAQuestion" rel="${answerType.id}" class="btn btn-primary btn-xs btn-action"><span class="glyphicon glyphicon-plus"></span>Add</a></td></tr>
    </c:forEach>
  </table>
</div>
</aside>