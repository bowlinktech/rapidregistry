<%-- 
    Document   : orgHierarchySelectBoxes
    Created on : Nov 13, 2014, 11:02:38 AM
    Author     : chadmccue
--%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<c:forEach var="hierarchy" items="${orgHierarchy}">
    <div class="form-group">    
        <div id="hierarchy_${hierarchy.id}Div">
            <label class="control-label" for="hierarchy_${hierarchy.id}">${hierarchy.name} *</label>
            <select id="hierarchy_${hierarchy.id}" name="hierarchy" rel="${hierarchy.id}" rel2="${programId}" rel3="${hierarchy.dspPos}" class="hierarchyDropBox form-control">
                <option value="0">- All -</option>
            </select>
        </div>    
    </div>
</c:forEach>



