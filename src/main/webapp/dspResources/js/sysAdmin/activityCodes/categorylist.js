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
        
        //This function will launch the new activity code category overlay with a blank screen
        $(document).on('click', '#createNewCategory', function() {
            $.ajax({
                url: 'category.create',
                type: "GET",
                success: function(data) {
                    $("#activityCodeCategoryModal").html(data);
                }
            });
        });
        
        //Function to submit the changes to an existing user or 
        //submit the new user fields from the modal window.
        $(document).on('click', '#submitButton', function(event) {
           
            var formData = $("#categorydetailsform").serialize();

            var actionValue = $(this).attr('rel').toLowerCase();

            $.ajax({
                url: actionValue+'_activityCodeCategory',
                data: formData,
                type: "POST",
                async: false,
                success: function(data) {

                    if (data.indexOf('categoryUpdated') != -1) {
                        window.location.href = "categories?msg=updated";

                    }
                    else if (data.indexOf('categoryCreated') != -1) {
                        window.location.href = "categories?msg=created";

                    }
                    else {
                        $("#activityCodeCategoryModal").html(data);
                    }
                }
            });
            event.preventDefault();
            return false;

        });


        $(document).on('click', '.editCategory', function() {
            var categoryId = $(this).attr('rel');
            
            $.ajax({
                url: 'category.edit',
                data: {'categoryId': categoryId},
                type: "GET",
                success: function(data) {
                    $("#activityCodeCategoryModal").html(data);
                }
            });
        });


    });
});




