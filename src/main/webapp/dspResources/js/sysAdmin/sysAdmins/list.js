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
        $(document).on('click', '#createNewAdmin', function() {
            $.ajax({
                url: 'system-admins/administrator.create',
                type: "GET",
                success: function(data) {
                    $("#sysAdminModal").html(data);
                }
            });
        });
        
        //Function to submit the changes to an existing administrator or 
        //submit the new user fields from the modal window.
        $(document).on('click', '#submitButton', function(event) {
           
            var formData = $("#admindetailsform").serialize();

            var actionValue = $(this).attr('rel').toLowerCase();

            $.ajax({
                url: 'system-admins/'+actionValue+'_systemadmin',
                data: formData,
                type: "POST",
                async: false,
                success: function(data) {

                    if (data.indexOf('adminUpdated') != -1) {
                        window.location.href = "system-admins?msg=updated";

                    }
                    else if (data.indexOf('adminCreated') != -1) {
                        window.location.href = "system-admins?msg=created";

                    }
                    else {
                        $("#sysAdminModal").html(data);
                    }
                }
            });
            event.preventDefault();
            return false;

        });


        $(document).on('click', '.editSysAdmin', function() {
            var adminId = $(this).attr('rel');
            
            $.ajax({
                url: 'system-admins/administrator.edit',
                data: {'adminId': adminId},
                type: "GET",
                success: function(data) {
                    $("#sysAdminModal").html(data);
                }
            });
        });
        
        
       
        $(document).on('click', '.deleteSysAdmin', function() {
            
            var removeAdmin = confirm("Are you sure you want to delete this system administrator?");
            
            if(removeAdmin) {
                var selAdminId = $(this).attr('rel');
                
                 $.ajax({
                    url: 'system-admins/administrator.remove',
                    data: {'adminId': selAdminId},
                    type: "POST",
                    success: function(data) {
                      window.location.href = "system-admins?msg=removed";
                    }
                });
                
            }
            
        })
        
    });
});




