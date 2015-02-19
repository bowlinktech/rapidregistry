

require(['./main'], function() {
    require(['jquery'], function($) {

        $("input:text,form").attr("autocomplete", "off");

        //Fade out the updated/created message after being displayed.
        if ($('.alert').length > 0) {
            $('.alert').delay(2000).fadeOut(1000);
        }
        
        getAssignedCategories();

        $('#saveDetails').click(function(event) {
            $('#action').val('save');
            if (checkForm()) {
            	$("#servicedetailsform").submit();
            }    
        });

        $('#saveCloseDetails').click(function(event) {
            $('#action').val('close');
            /** need to validate drop down & password lengths **/
            if (checkForm()) {
            	$("#servicedetailsform").submit();
            }
        });
        
        //Function to open the new category association modal
        $(document).on('click', '#addCategory', function() {
           
           var i = getUrlParameter('i');
           var v = getUrlParameter('v');
           
           $.ajax({
                url: 'assignNewCategory.do',
                data: {'i':i, 'v': v},
                type: "GET",
                success: function(data) {
                    $('#categoryModal').html(data);
                }
           }); 
            
        });
        
        
        //Function to remove the associated category
        $(document).on('click', '.removeCategory', function(event) {
            var i = getUrlParameter('i');
            var v = getUrlParameter('v');
            
            if(confirm("Are you sure you want to remove this category association?")) {
            
                $.ajax({
                    url: 'removeAssociatedServiceCategory.do',
                    data: {'i':i, 'v': v, 'categoryId': $(this).attr('rel')},
                    type: "POST",
                    success: function(data) {
                       window.location.href = "details?i="+i+"&v="+v+"&msg=categoryRemoved";
                    }
               }); 
           }
        })
        
       
        //Function to submit the changes to an existing user program modules
        $(document).on('click', '#assignCategoryButton', function(event) {
          
            if($('#selCategory').val() == null) {
                $('#categoryDiv').addClass("has-error");
                $('#categoryMsg').addClass("has-error");
                $('#categoryMsg').html('A category must be selected!');
            }
            else {
               var formData = $("#assignCategoryForm").serialize();

                $.ajax({
                    url: 'saveAssignedServiceCategories.do',
                    data: formData,
                    type: "POST",
                    async: false,
                    success: function(data) {
                        var url = $(data).find('#encryptedURL').val();
                        window.location.href = "details"+url+"&msg=categoryAdded";
                    }
                });
            }
            event.preventDefault();
           return false; 
            
        });
        

    });
});


function getAssignedCategories() {
   var i = getUrlParameter('i');
   var v = getUrlParameter('v');
    
   $.ajax({
        url: 'getAssignedCategories.do',
        data: {'i':i, 'v': v},
        type: "GET",
        success: function(data) {
            $('#assignedCategories').html(data);
        }
    });
    
}

function getUrlParameter(sParam)
{
    var sPageURL = window.location.search.substring(1);
    var sURLVariables = sPageURL.split('&');
    for (var i = 0; i < sURLVariables.length; i++) 
    {
        var sParameterName = sURLVariables[i].split('=');
        if (sParameterName[0] == sParam) 
        {
            return sParameterName[1];
        }
    }
}   


function checkForm()
{
	
	 $('div.form-group').removeClass("has-error");
     $('span.control-label').removeClass("has-error");
     $('span.control-label').html("");
     
    
	//Make sure the staff type is selected
    if($('#typeId').val() == 0) {
        $('#typeIdDiv').addClass('has-error');
        $('#typeIdMsg').addClass('has-error');
        $('#typeIdMsg').html('The staff type must be selected');
        return false;
    } 
    var newPassword = $('#password').val();
    var confirmPassword = $('#confirmPassword').val();
    
    if (newPassword.trim().length > 0 || confirmPassword.trim().length > 0) {
        if(newPassword.length < 5) {
            $('#passwordDiv').addClass("has-error");
            $('#passwordMsg').addClass("has-error");
            $('#passwordMsg').html('The new password must be between 5 and 15 characters.');
            return false;
        }
        if(confirmPassword.length < 5) {
            $('#confirmPasswordDiv').addClass("has-error");
            $('#confirmPasswordMsg').addClass("has-error");
            $('#confirmPasswordMsg').html('The confirm password must be between 5 and 15 characters.');
            return false;
        } 
        if(newPassword != confirmPassword) {
            $('#confirmPasswordDiv').addClass("has-error");
            $('#confirmPasswordMsg').addClass("has-error");
            $('#confirmPasswordMsg').html('The confirm password must be equal to the new password.');
            return false;
        } 
    } 
    return true;
}   