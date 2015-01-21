<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h3 class="panel-title">Enter Save Note</h3>
         </div>
         <div class="modal-body">
             <div id="detailForm">
                 <form id="saveNoteForm" method="post" role="form">
                    <div class="form-group">
                    	  <div id="notesDiv" class="form-group ${status.error ? 'has-error' : '' }">
                    <label class="control-label" for="identifier">Note: *</label>
                   <textarea id="notes" class="form-control" rows="15"></textarea>
                    <span id="notesMsg" class="control-label"></span>
                	</div>  
      
               		 </div>
                        <div class="form-group">
                            <input type="button" id="saveNote" rel="saveNote" role="button" class="btn btn-primary" value="${btnValue}"/>
                        </div>
                </form>
             </div>
             
        </div>
    </div>
</div>

<script type="text/javascript">

    $(document).ready(function() {
        $("input:text,form").attr("autocomplete", "off");
    });
</script>
