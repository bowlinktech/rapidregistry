<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="modal-dialog-lg">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h3 class="panel-title">Survey Category Details</h3>
         </div>
         <div class="modal-body">
            <form:form id="surveyCategoryForm" commandName="surveyCategoryDetails" method="post" role="form">
		<form:hidden path="id" />
		<form:hidden path="programId" /> 
		<form:hidden path="dateCreated" /> 

		<section class="panel panel-default">
		    <div class="panel-body">
			<div class="form-container">
			    <div class="form-group">
				<spring:bind path="categoryName">
				    <div id="categoryNameDiv" class="form-group ${status.error ? 'has-error' : '' }">
					<label class="control-label" for="categoryName">Category Name *</label>
					<form:input path="categoryName" id="categoryName" class="form-control" type="text"  maxLength="40" />
					<span id="categoryNameMsg" class="control-label has-error" style="display:none">The category name is a required field.</span>
				    </div> 
				</spring:bind> 
			    </div>
			</div>
		    </div>
		</section>
		<div class="form-group">
		   <input type="button" id="submitSurveyCategoryButton" role="button" class="btn btn-primary" value="Save"/>
	       </div>
	    </form:form>
        </div>
    </div>
</div>

<script type="text/javascript">

    $(document).ready(function() {
        $("input:text,form").attr("autocomplete", "off");
    });
</script>

