
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<table class="table table-striped table-hover responsive">
    <thead>
        <tr>
            <th scope="col">Field</th>
            <th scope="col">Crosswalk</th>
            <th scope="col">Field Validation</th>
            <th scope="col" class="center-text">Required</th>
            <th scope="col">Display Order</th>
            <th scope="col" class="center-text"></th>
        </tr>
    </thead>
    <tbody>
        <c:choose>
            <c:when test="${existingFields.size() > 0}">
                <c:forEach items="${existingFields}" var="field" varStatus="fStatus">
                    <tr>
                        <td scope="row">
                            ${existingFields[fStatus.index].fieldName} 
                        </td>
                        <td>
                            ${existingFields[fStatus.index].cwName} 
                        </td>
                        <td>
                            ${existingFields[fStatus.index].validationName} 
                        </td>
                         <td class="center-text">
                            ${existingFields[fStatus.index].requiredField} 
                        </td>
                        <td>
                            <select rel="${existingFields[fStatus.index].dspPos}" name="displayOrder" class="displayOrder">
                                <option value="">- Select -</option>
                                <c:forEach begin="1" end="${existingFields.size()}" var="i">
                                    <option value="${i}" <c:if test="${existingFields[fStatus.index].dspPos  == i}">selected</c:if>>${i}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td class="center-text">
                            <a href="javascript:void(0);" class="btn btn-link removeField" rel2="${existingFields[fStatus.index].dspPos}" rel="${existingFields[fStatus.index].fieldId}" title="Remove this field.">
                                <span class="glyphicon glyphicon-edit"></span>
                                Remove
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise><tr><td scope="row" colspan="6" style="text-align:center">There have been no fields associated to this program.</td></c:otherwise>
       </c:choose>
    </tbody>
</table>


