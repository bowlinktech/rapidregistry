
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<table class="table table-striped table-hover responsive">
    <thead>
        <tr>
            <th scope="col">Activity Code</th>
            <th scope="col">Code Desc</th>
            <th scope="col">Display Order</th>
            <th scope="col" class="center-text"></th>
        </tr>
    </thead>
    <tbody>
        <c:choose>
            <c:when test="${existingCodes.size() > 0}">
                <c:forEach items="${existingCodes}" var="code" varStatus="cStatus">
                    <tr>
                        <td scope="row">
                            ${existingCodes[cStatus.index].code} 
                        </td>
                        <td scope="row">
                            ${existingCodes[cStatus.index].codeDesc} 
                        </td>
                        <td>
                            <select rel="${existingCodes[cStatus.index].dspPos}" name="displayOrder" class="displayOrder">
                                <option value="">- Select -</option>
                                <c:forEach begin="1" end="${existingCodes.size()}" var="i">
                                    <option value="${i}" <c:if test="${existingCodes[cStatus.index].dspPos  == i}">selected</c:if>>${i}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td class="center-text">
                            <a href="javascript:void(0);" class="btn btn-link removeCode" rel2="${existingCodes[cStatus.index].dspPos}" rel="${existingCodes[cStatus.index].fieldId}" title="Remove this Activity Code.">
                                <span class="glyphicon glyphicon-edit"></span>
                                Remove
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise><tr><td scope="row" colspan="6" style="text-align:center">There have been no activity codes associated to this program.</td></c:otherwise>
       </c:choose>
    </tbody>
</table>


