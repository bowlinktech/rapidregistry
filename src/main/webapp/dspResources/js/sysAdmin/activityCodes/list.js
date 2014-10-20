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
        
        //This function will launch the new MPI Algorithm overlay with a blank screen
        $(document).on('click', '#createNewCode', function() {
            $.ajax({
                url: 'code.create',
                type: "GET",
                success: function(data) {
                    $("#activityCodeModal").html(data);
                }
            });
        });
        
        //Function to submit the changes to an existing user or 
        //submit the new user fields from the modal window.
        $(document).on('click', '#submitButton', function(event) {
           
            var formData = $("#codedetailsform").serialize();

            var actionValue = $(this).attr('rel').toLowerCase();

            $.ajax({
                url: actionValue+'_activityCode',
                data: formData,
                type: "POST",
                async: false,
                success: function(data) {

                    if (data.indexOf('codeUpdated') != -1) {
                        window.location.href = "list?msg=updated";

                    }
                    else if (data.indexOf('codeCreated') != -1) {
                        window.location.href = "list?msg=created";

                    }
                    else {
                        $("#activityCodeModal").html(data);
                    }
                }
            });
            event.preventDefault();
            return false;

        });


        $(document).on('click', '.editCode', function() {
            var codeId = $(this).attr('rel');
            
            $.ajax({
                url: 'code.edit',
                data: {'codeId': codeId},
                type: "GET",
                success: function(data) {
                    $("#activityCodeModal").html(data);
                }
            });
        });


    });
});




