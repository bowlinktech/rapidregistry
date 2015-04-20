<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
                       <table class="table table-striped table-hover table-default">
                                <thead>
                                    <tr>
                                        
                                        <th scope="col" class="center-text">Process Order</th>
                                        <th scope="col">Selected Fields</th>
                                        <th scope="col" class="center-text">Selected Action</th>                                        
                                        <th scope="col" class="center-text">Status</th>                                        
                                        <th scope="col" class="center-text">Date Created</th>
                                        
                                        <th scope="col"></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <c:choose>
                                    <c:when test="${not empty category.algorithms}">
                                        <c:forEach var="algorithm" items="${category.algorithms}">
                                            <tr>
                                            	<td class="center-text">
						                            <select rel="${algorithm.processOrder}" rel2="${importType.id}" rel3="${category.id}" id="processOrder${categoryId}" name="processOrder" class="processOrder">
						                                <option value="">- Select -</option>
						                                <c:forEach begin="1" end="${category.algorithms.size()}" var="i">
						                                    <option value="${i}" <c:if test="${algorithm.processOrder  == i}">selected</c:if>>${i}</option>
						                                </c:forEach>
						                            </select>
                        						</td>
                                                <td>
                                                    <c:forEach items="${algorithm.fields}" var="field" varStatus="fIndex">
                                                        ${fIndex.index+1}. <strong><c:choose><c:when test="${fIndex.index+1 == 1}">If</c:when><c:otherwise>And </c:otherwise></c:choose></strong> ${field.fieldName}&nbsp;<strong>${field.action}</strong><br />
                                                    </c:forEach>
                                                </td>
                                                <td class="center-text">
                                                    ${algorithm.actionName}
                                                </td>
                                                <td class="center-text"><c:if test="${algorithm.status}">Active</c:if><c:if test="${!algorithm.status}">Inactive</c:if>
                                                </td>
                                                <td class="center-text"><fmt:formatDate value="${algorithm.dateCreated}" type="date" pattern="M/dd/yyyy" /></td>
                                                <td class="actions-col">
                                                    <a href="#algorithmDetailsModal" data-toggle="modal" rel="${algorithm.id}" class="btn btn-link editAlgorithm" title="Edit this algorithm" role="button">
                                                        <span class="glyphicon glyphicon-edit"></span>
                                                        Edit
                                                    </a>
                                                    <a href="javascript:void(0);" rel="${algorithm.id}" rel2="${importType.id}" class="btn btn-link deleteAlgorithm" title="Delete this algorithm" role="button">
                                                        <span class="glyphicon glyphicon-remove"></span>
                                                        Delete
                                                    </a>    
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <tr><td colspan="7" class="center-text">There are currently no algorithms set up for this upload type.</td></tr>
                                    </c:otherwise>
                                </c:choose>
                            </tbody>
                        </table>                  