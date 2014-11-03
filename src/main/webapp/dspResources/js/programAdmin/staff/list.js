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
                url: 'staffmember.create',
                type: "GET",
                success: function(data) {
                    $("#staffMemberModal").html(data);
                }
            });
        });
        
        //Function to submit the changes to an existing administrator or 
        //submit the new user fields from the modal window.
        $(document).on('click', '#submitButton', function(event) {
           
            var formData = $("#staffmemberdetailsform").serialize();

            var actionValue = $(this).attr('rel').toLowerCase();

            $.ajax({
                url: actionValue+'_staffmember',
                data: formData,
                type: "POST",
                async: false,
                success: function(data) {

                    if (data.indexOf('staffUpdated') != -1) {
                        window.location.href = "?msg=updated";

                    }
                    else if (data.indexOf('staffCreated') != -1) {
                        window.location.href = "?msg=created";

                    }
                    else {
                        $("#staffMemberModal").html(data);
                    }
                }
            });
            event.preventDefault();
            return false;

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
            
        })
        
    });
});




