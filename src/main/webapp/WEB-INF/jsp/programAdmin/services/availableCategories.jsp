 <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%-- 
    Document   : programModules
    Created on : Nov 4, 2014, 4:38:34 PM
    Author     : chadmccue
--%>

<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h3 class="panel-title">Available Categories</h3>
         </div>
         <div class="modal-body">
                <form id="assignCategoryForm" method="post" role="form">
                   <input type="hidden" name="i" value="${serviceId}" />
                   <input type="hidden" name="v" value="${v}" />
                   <input type="hidden" id="encryptedURL" value="${encryptedURL}" />
                   <c:choose>
                       <c:when test="${not empty categories}">
                           <div class="form-group">
                              <div id="categoryDiv" class="form-group ${status.error ? 'has-error' : '' }">
                                  <label class="control-label" for="tableName">Available Categories</label>
                                  <select id="selCategory" name="category" class="form-control" multiple="true">
                                      <c:forEach var="category" items="${categories}">
                                          <option value="${category.id}">${category.categoryName}</option>
                                      </c:forEach>
                                  </select>     
                                  <span id="categoryMsg" class="control-label" ></span>    
                              </div>
                          </div>
                          <div class="form-group">
                              <input type="button" id="assignCategoryButton" role="button" class="btn btn-primary" value="Save"/>
                          </div>
                      </c:when>
                    <c:otherwise>
                        <p>The service is associated to all available categories</p>
                    </c:otherwise>
                </c:choose>
              </form>
         </div>
    </div>
</div>
