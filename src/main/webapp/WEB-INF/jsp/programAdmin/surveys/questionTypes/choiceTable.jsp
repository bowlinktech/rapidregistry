<%-- 
    Document   : choiceTable
    Created on : Feb 26, 2015, 9:55:22 AM
    Author     : chadmccue
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<div class="panel-body">
    <table class="table" border="0">
        <thead>
            <tr>
                <th>Answers</th>
                <th class="center-text"></th>
                <th class="center-text">Default</th>
                <th>Associated Activity Code</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${surveyQuestion.questionChoices}" var="choiceDetails" varStatus="choice">
                <input type="hidden" name="questionChoices[${choice.index}].id" value="${choiceDetails.id}" />
                <input type="hidden" name="questionChoices[${choice.index}].questionId" value="${choiceDetails.questionId}" />
                <input type="hidden" name="questionChoices[${choice.index}].skipToPageId" value="${choiceDetails.skipToPageId}" />
                <input type="hidden" name="questionChoices[${choice.index}].skipToQuestionId" value="${choiceDetails.skipToQuestionId}" />
                <input type="hidden" name="questionChoices[${choice.index}].choiceValue" value="${choiceDetails.choiceValue}" />
                <tr>
                    <td>
                        <input type="text" name="questionChoices[${choice.index}].choiceText"  value="${choiceDetails.choiceText}" rel="${choice.index}" class="form-control fieldLabel formField" />
                    </td>
                    <td style="vertical-align:top;">
                        <c:if test="${empty surveyQuestion.populateFromTable}"><i class="glyphicon glyphicon-plus-sign addChoice" style="font-size:1.7em; cursor: pointer"></i>&nbsp;</c:if>
                        <i class="glyphicon glyphicon-minus-sign removeChoice" rel="${choiceDetails.questionId}" style="font-size:1.7em; cursor: pointer"></i>
                    </td>
                    <td class="center-text">
                        <input type="radio" name="defaultAnswer" value="1" <c:if test="${choiceDetails.defAnswer == true}">checked</c:if> />
                    </td>
                    <td>
                       <select name="questionChoices[${choice.index}].activityCodeId" class="form-control">
                            <option value="0" label=" - Select - " >- Select -</option>
                            <c:forEach items="${activityCodes}" varStatus="code">
                                <option value="${activityCodes[code.index].id}" <c:if test="${choiceDetails.activityCodeId == activityCodes[code.index].id}">selected</c:if>>${activityCodes[code.index].codeDesc}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
