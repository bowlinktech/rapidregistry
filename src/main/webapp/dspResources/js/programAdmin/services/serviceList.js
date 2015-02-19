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
        
        //This function will launch the new program service category overlay with a blank screen
        $(document).on('click', '#createNewService', function() {
            $.ajax({
                url: 'services/newService',
                type: "GET",
                success: function(data) {
                    $("#serviceModal").html(data);
                }
            });
        });
        
        
        $(document).on('click', '#submitButton', function(event) {
        	
            var formData = $("#servicedetailsform").serialize();

            $.ajax({
                url: 'services/create_service',
                data: formData,
                type: "POST",
                async: false,
                success: function(data) {

                    if (data.indexOf('serviceCreated') != -1) {
                       var url = $(data).find('#encryptedURL').val();
                       window.location.href = "services/details"+url;
                    }
                    else {
                        $("#serviceCategoryModal").html(data);
                    }
                }
            });
            event.preventDefault();
            return false;

        });
    });
});

