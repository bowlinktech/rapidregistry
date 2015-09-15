<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<table class="table table-striped table-hover responsive">
    <thead>
        <tr>
            <th scope="col">Name</th>
            <th scope="col">Save To Table</th>
            <th scope="col">Save To Table Column</th>
            <th scope="col"></th>
        </tr>
    </thead>
    <tbody>
        <c:choose>
            <c:when test="${availableCustomFields.size() > 0}">
                <c:forEach items="${availableCustomFields}" var="field" varStatus="pStatus">
                    <tr>
                        <td scope="row">
                            ${availableCustomFields[pStatus.index].fieldName}
                        </td>
                        <td>
                            ${availableCustomFields[pStatus.index].saveToTable}
                        </td>
                        <td>
                            ${availableCustomFields[pStatus.index].saveToTableCol}
                        </td>
                        <td class="center-text">
                            <a href="#customFieldModal" data-toggle="modal" class="btn btn-link viewCustomField" rel="?i=${availableCustomFields[pStatus.index].id}" title="View this Custom Field">
                                <span class="glyphicon glyphicon-edit"></span>
                                View
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise><tr><td scope="row" colspan="3" style="text-align:center">No Custom Fields Found</td></c:otherwise>
            </c:choose>
    </tbody>
</table>
<ul class="pagination pull-right" role="navigation" aria-labelledby="Paging ">
    <c:if test="${currentPage > 1}"><li><a href="javascript:void(0);" class="nextcustonFieldPage" rel="${currentPage-1}">&laquo;</a></li></c:if>
        <c:forEach var="i" begin="1" end="${totalPages}">
        <li><a href="javascript:void(0);" class="nextcustonFieldPage" rel="${i}">${i}</a></li>
        </c:forEach>
        <c:if test="${currentPage < totalPages}"><li><a href="javascript:void(0);" class="nextcustonFieldPage" rel="${currentPage+1}">&raquo;</a></li></c:if>
</ul>