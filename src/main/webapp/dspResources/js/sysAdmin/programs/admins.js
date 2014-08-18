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
                url: 'administrator.create',
                type: "GET",
                success: function(data) {
                    $("#adminDetailsModal").html(data);
                }
            });
        });
        
        //Function to submit the changes to an existing administrator or 
        //submit the new user fields from the modal window.
        $(document).on('click', '#submitButton', function(event) {
           
            var formData = $("#admindetailsform").serialize();

            var actionValue = $(this).attr('rel').toLowerCase();

            $.ajax({
                url: actionValue+'_programadmin',
                data: formData,
                type: "POST",
                async: false,
                success: function(data) {

                    if (data.indexOf('adminUpdated') != -1) {
                        window.location.href = "program-admins?msg=updated";

                    }
                    else if (data.indexOf('adminCreated') != -1) {
                        window.location.href = "program-admins?msg=created";

                    }
                    else {
                        $("#adminDetailsModal").html(data);
                    }
                }
            });
            event.preventDefault();
            return false;

        });


        $(document).on('click', '.editAdmin', function() {
            var adminId = $(this).attr('rel');
            
            $.ajax({
                url: 'administrator.edit',
                data: {'adminId': adminId},
                type: "GET",
                success: function(data) {
                    $("#adminDetailsModal").html(data);
                }
            });
        });
        
        
        $(document).on('click', '#showNewForm', function() {
           $('#selectAdminForm').hide();
           $('#detailForm').show();
        });
        
        $(document).on('change', '#existingAdmin', function() {
           
            var selAdminId = $(this).val();
            
            if(selAdminId > 0) {
                $.ajax({
                    url: 'administrator.associateToProgram',
                    data: {'adminId': selAdminId},
                    type: "POST",
                    success: function(data) {
                      window.location.href = "program-admins?msg=associated";
                    }
                });
            }
            
        });
        
        $(document).on('click', '.removeUser', function() {
            
            var removeAdmin = confirm("Are you sure you want to remove this admin from this program?");
            
            if(removeAdmin) {
                var selAdminId = $(this).attr('rel');
                
                 $.ajax({
                    url: 'administrator.removeFromProgram',
                    data: {'adminId': selAdminId},
                    type: "POST",
                    success: function(data) {
                      window.location.href = "program-admins?msg=removed";
                    }
                });
                
            }
            
        })
        
    });
});




