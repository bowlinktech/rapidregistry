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
        
        //This function will launch the new system user overlay with a blank screen
        $(document).on('click', '#createNewProgram', function() {
            $.ajax({
                url: 'programs/create',
                type: "GET",
                success: function(data) {
                    $("#newProgramModal").html(data);
                }
            });
        });
        
        //Function to submit the changes to an existing user or 
        //submit the new user fields from the modal window.
        $(document).on('click', '#submitButton', function(event) {
            
            var formData = $("#program").serialize();

            $.ajax({
                url: 'programs/create',
                data: formData,
                type: "POST",
                async: false,
                success: function(data) {

                    if (data.indexOf('programCreated') != -1) {
                       window.location.href = "/sysAdmin/programs?msg=created";
                    }
                    else {
                        $("#newProgramModal").html(data);
                    }
                }
            });
            event.preventDefault();
            return false;

        });


    });
});




