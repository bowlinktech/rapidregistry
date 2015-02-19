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
        $(document).on('click', '#createNewCategory', function() {
            $.ajax({
                url: 'categoryDetails',
                data: {'categoryId': 0},
                type: "GET",
                success: function(data) {
                    $("#serviceCategoryModal").html(data);
                }
            });
        });
        
        //This function will display the form of the clicked service category
        $(document).on('click', '.editCategory', function() {
            
            var categoryId = $(this).attr('rel');
            
            $.ajax({
                url: 'categoryDetails',
                data: {'categoryId': categoryId},
                type: "GET",
                success: function(data) {
                    $("#serviceCategoryModal").html(data);
                }
            });
        });
        
        $(document).on('click', '#submitButton', function(event) {
        	
            var formData = $("#categorydetailsform").serialize();

            var actionValue = $(this).attr('rel').toLowerCase();

            $.ajax({
                url: 'saveserviceCategory',
                data: formData,
                type: "POST",
                async: false,
                success: function(data) {

                    if (data.indexOf('categoryUpdated') != -1) {
                        window.location.href = "?msg=updated";

                    }
                    else if (data.indexOf('categoryCreated') != -1) {
                        window.location.href = "?msg=created";

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

