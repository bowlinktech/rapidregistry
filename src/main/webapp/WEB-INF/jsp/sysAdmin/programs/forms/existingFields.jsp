
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- Get total count of search fields --%>
<c:set var="totalSearchFields" value="0" scope="page" />

<c:forEach items="${existingFields}" var="field" varStatus="fStatus">
    <c:if test="${existingFields[fStatus.index].searchField == true}"><c:set var="totalSearchFields" value="${totalSearchFields + 1}" scope="page"/></c:if>
</c:forEach>

<table class="table table-striped table-hover responsive">
    <thead>
        <tr>
            <th scope="col">Field / Display Name</th>
            <th scope="col">Crosswalk/Auto Populate</th>
            <th scope="col">Field Validation</th>
            <th scope="col" class="center-text">Required</th>
            <th scope="col" class="center-text">Hidden Field</th>
            <th scope="col" class="center-text">Form Dsp Position</th>
            <th scope="col">Show Field In</th>
            <th scope="col" class="center-text">Search/Summary Dsp Position</th>
            <th scope="col" class="center-text"></th>
        </tr>
    </thead>
    <tbody>
        <c:choose>
            <c:when test="${existingFields.size() > 0}">
                <c:forEach items="${existingFields}" var="field" varStatus="fStatus">
                    <tr>
                        <td scope="row">
                            ${existingFields[fStatus.index].fieldName} <br />
                            <strong>${existingFields[fStatus.index].fieldDisplayname} </strong>
                        </td>
                        <td>
                             <c:choose>
                                 <c:when test="${not empty existingFields[fStatus.index].cwName}">${existingFields[fStatus.index].cwName}</c:when>
                                 <c:when test="${existingFields[fStatus.index].autoPopulate == true}">
                                     <a href="#selectValuesModal" data-toggle="modal" class="btn btn-link selectValues" rel="${existingFields[fStatus.index].id}"  title="Select Field Values">
                                         <span class="glyphicon glyphicon-search"></span>
                                         Select Values
                                     </a>
                                 </c:when>
                                 <c:otherwise>--</c:otherwise>
                             </c:choose>
                        </td>
                        <td>
                            ${existingFields[fStatus.index].validationName} 
                        </td>
                        <td class="center-text">
                            ${existingFields[fStatus.index].requiredField} 
                        </td>
                        <td class="center-text">
                            <c:choose>
                                <c:when test="${existingFields[fStatus.index].hideField == true}">Yes</c:when>
                                <c:otherwise>No</c:otherwise>
                            </c:choose>
                        </td>
                        <td class="center-text">
                            <select rel="${existingFields[fStatus.index].dspPos}" name="displayOrder" class="displayOrder">
                                <option value="">- Select -</option>
                                <c:forEach begin="1" end="${existingFields.size()}" var="i">
                                    <option value="${i}" <c:if test="${existingFields[fStatus.index].dspPos  == i}">selected</c:if>>${i}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>
                            <%--<c:if test="${existingFields[fStatus.index].dataGridColumn == true}">Data Grid<br /></c:if>--%>
                            <c:if test="${existingFields[fStatus.index].searchField == true}">Client Search<br /></c:if>
                            <c:if test="${existingFields[fStatus.index].summaryField == true}">Client Summary</c:if>
                        </td>
                        <td class="center-text">
                        <c:choose>
                            <c:when test="${existingFields[fStatus.index].searchField == true}">
                                <select rel="${existingFields[fStatus.index].searchDspPos}" name="searchdisplayOrder" class="searchdisplayOrder">
                                    <option value="">- Select -</option>
                                    <c:forEach begin="1" end="${totalSearchFields}" var="i">
                                        <option value="${i}" <c:if test="${existingFields[fStatus.index].searchDspPos  == i}">selected</c:if>>${i}</option>
                                    </c:forEach>
                                </select>
                            </c:when>
                            <c:otherwise>
                                --
                            </c:otherwise>
                        </c:choose>
                        </td>
                        <td class="center-text">
                            <c:if test="${existingFields[fStatus.index].id > 0}">
                                <a href="#fieldModal" data-toggle="modal" class="btn btn-link editField" rel="${existingFields[fStatus.index].id}"  title="Edit this field">
                                   <span class="glyphicon glyphicon-edit"></span>
                                   Edit 
                                </a>
                            </c:if>
                            <a href="javascript:void(0);" class="btn btn-link removeField" rel3="${existingFields[fStatus.index].searchDspPos}" rel2="${existingFields[fStatus.index].dspPos}" rel="${existingFields[fStatus.index].fieldId}" title="Remove this field.">
                                <span class="glyphicon glyphicon-remove-circle"></span>
                                Remove
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise><tr><td scope="row" colspan="8" style="text-align:center">There have been no fields associated to this section.</td></c:otherwise>
       </c:choose>
    </tbody>
</table>


