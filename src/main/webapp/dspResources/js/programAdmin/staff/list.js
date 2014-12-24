/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

require(['./main'], function () {
    require(['jquery'], function($) {

        //Fade out the updated/created message after being displayed.
        if ($('.alert').length > 0) {
            $('.alert').delay(2000).fadeOut(1000);
        }

        $("input:text,form").attr("autocomplete", "off");
        
        //This function will launch the new program administrator overlay with a blank screen
        $(document).on('click', '#createNewStaffMember', function() {
            $.ajax({
                url: 'staff/staffmember.create',
                type: "GET",
                success: function(data) {
                    $("#staffMemberModal").html(data);
                }
            });
        });
        
        //Function to submit the changes to an existing administrator or 
        //submit the new user fields from the modal window.
        $(document).on('click', '#submitButton', function(event) {
        	
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
            
            if(newPassword.length < 5) {
                $('#passwordDiv').addClass("has-error");
                $('#passwordMsg').addClass("has-error");
                $('#passwordMsg').html('The new password must be between 5 and 15 characters.');
            }
            else if(confirmPassword.length < 5) {
                $('#confirmPasswordDiv').addClass("has-error");
                $('#confirmPasswordMsg').addClass("has-error");
                $('#confirmPasswordMsg').html('The confirm password must be between 5 and 15 characters.');
            }
            else if(newPassword != confirmPassword) {
                $('#confirmPasswordDiv').addClass("has-error");
                $('#confirmPasswordMsg').addClass("has-error");
                $('#confirmPasswordMsg').html('The confirm password must be equal to the new password.');
            } else {
           
                var formData = $("#staffmemberdetailsform").serialize();

                var actionValue = $(this).attr('rel').toLowerCase();

                $.ajax({
                    url: 'staff/'+actionValue+'_staffmember',
                    data: formData,
                    type: "POST",
                    async: false,
                    success: function(data) {

                        if (data.indexOf('?i=') != -1) {
                            var url = $(data).find('#encryptedURL').val();
                            window.location.href = "staff/details"+url;
                        }
                        else {
                            $("#staffMemberModal").html(data);
                        }
                    }
                });
                event.preventDefault();
                return false;
            }

        });

        
       
        $(document).on('click', '.deleteStaffMember', function() {
            
            var removeAdmin = confirm("Are you sure you want to delete this staff member?");
            
            if(removeAdmin) {
                var selAdminId = $(this).attr('rel');
                
                 $.ajax({
                    url: 'staffmember.remove',
                    data: {'adminId': selAdminId},
                    type: "POST",
                    success: function(data) {
                      window.location.href = "staff?msg=removed";
                    }
                });
                
            }
            
        });
        
        //Clear search fields
        $(document).on('click', '#clearButton', function() {
           $('#clear').val('yes');
           $('#searchForm').submit();
       });
        
    });
});




