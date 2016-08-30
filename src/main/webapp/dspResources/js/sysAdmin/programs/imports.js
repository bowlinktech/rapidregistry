

require(['./main'], function () {
    require(['jquery'], function($) {
        
        $("input:text,form").attr("autocomplete", "off");
        
        
        //Fade out the updated/created message after being displayed.
        if ($('.alert').length > 0) {
            $('.alert').delay(2000).fadeOut(1000);
        }
        
        //Open up the modal to display the import type form
        $(document).on('click', '#createNewImportType', function() {
            $.ajax({
                url: 'importTypeForm',
                data: {'id':0,},
                type: "GET",
                success: function(data) {
                    $("#importModal").html(data);
                }
            });
        });
        
        //Open up the modal to display the import type form
        $(document).on('click', '.editImportType', function() {
            $.ajax({
                url: 'importTypeForm',
                data: {'id':$(this).attr('rel')},
                type: "GET",
                success: function(data) {
                    $("#importModal").html(data);
                }
            });
        });
        
        //Function to submit the changes to an existing user or 
        //submit the new user fields from the modal window.
        $(document).on('click', '#submitImportType', function(event) {
            
        	$('div.form-group').removeClass("has-error");
            $('span.control-label').removeClass("has-error");
            $('span.control-label').html("");
            
            var errorCount  = 0;
            var formData = $("#importTypeForm").serialize();
            
            var helConfigId = $("#helConfigId").val();
            var helDropPath = $("#helDropPath").val();
            var helPickUpPath = $("#helPickUpPath").val();
            var outFileTypeId = $("#outFileTypeId").val();
            
            
            /**check to make sure paths are not blank**/
            if($('#useHEL1').is(':checked')) {
            	//make sure paths are filled in
            	if ($.trim(helDropPath).length < 1) {
            		 $("#helDropPathDiv").addClass("has-error");
            		 $('#helDropPathMsg').addClass("has-error");
                     $('#helDropPathMsg').html('Health-e-link Input Path cannot be empty'); 
                     errorCount++;
            	}
            	if ($.trim(helPickUpPath).length < 1) {
           		 $("#helPickUpPathDiv").addClass("has-error");
           		 $('#helPickUpPathMsg').addClass("has-error");
                 $('#helPickUpPathMsg').html('Health-e-link Output Path cannot be empty'); 
                    errorCount++;
            	}
            	if (outFileTypeId == 1) {
              		 $("#outFileTypeIdDiv").addClass("has-error");
              		 $('#outFileTypeIdMsg').addClass("has-error");
                    $('#outFileTypeIdMsg').html('Please select a Health-e-link output file type'); 
                       errorCount++;
               	}
            	
            	
            }
            
            if (errorCount > 0) {
            	return false;
            }
            
            $.ajax({
                url: 'saveImportType',
                data: formData,
                type: "POST",
                async: false,
                success: function(data) {

                    if (data.indexOf('importTypeSaved') != -1) {
                       window.location.href = "/sysAdmin/programs/"+$('#progamNameURL').val()+"/imports?msg=importTypesaved";
                    }
                    else {
                        $("#importModal").html(data);
                    }
                }
            });
            
           event.preventDefault();
           return false;
        });
        
        $(document).on('click', '.deleteImportType', function(event) {
           
            var confirmed =  confirm('Are you sure you want to delete this import type?');
            
            if(confirmed) {
                $.ajax({
                    url: 'removeImportType.do',
                    data: {'id':$(this).attr('rel')},
                    type: "POST",
                    async: false,
                    success: function(data) {
                      window.location.href = "/sysAdmin/programs/"+data;
                    }
                });
            }
            
        });
        
    });
});


function helPaths(show) {
	if (show) {
		$("#helPaths").show();
	} else {
		$("#helPaths").hide();
	}
}

function isParentPUT(show) {
	if (show) {
		$("#isParentDiv").show();
		$("#helPaths").hide();
	} else {
		$("#isParentDiv").hide();
		$("#helPaths").show();
	}
}

