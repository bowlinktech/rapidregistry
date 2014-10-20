
require(['./main'], function() {
    require(['jquery'], function($) {


        $("input:text,form").attr("autocomplete", "off");
        
        //Fade out the updated/created message after being displayed.
        if ($('.alert').length > 0) {
            $('.alert').delay(2000).fadeOut(1000);
        }
        
        

        //Function that will display the new field module
        $(document).on('click', '#createNewField', function() {
           
            $.ajax({
                url: 'fieldForm',
                type: "GET",
                data: {'fieldId': 0},
                success: function(data) {
                    $("#fieldModal").html(data);
                }
            });
        });
        
        $(document).on('click', '.editField', function() {
            var fieldId = $(this).attr('rel');
            $.ajax({
                url: 'fieldForm',
                type: "GET",
                data: {'fieldId': fieldId},
                success: function(data) {
                    $("#fieldModal").html(data);
                }
            });
        });
        
        //Function to submit the changes to an existing user or 
        //submit the new user fields from the modal window.
        $(document).on('click', '#submitField', function(event) {
           
            var formData = $("#fieldForm").serialize();


            $.ajax({
                url: 'submitFieldForm',
                data: formData,
                type: "POST",
                async: false,
                success: function(data) {

                    if (data.indexOf('fieldUpdated') != -1) {
                        window.location.href = "list?msg=updated";

                    }
                    else if (data.indexOf('fieldCreated') != -1) {
                        window.location.href = "list?msg=created";

                    }
                    else {
                        $("#fieldModal").html(data);
                    }
                }
            });
            event.preventDefault();
            return false;

        });
        
    });
});
