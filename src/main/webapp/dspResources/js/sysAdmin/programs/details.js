

require(['./main'], function() {
    require(['jquery'], function($) {

        $("input:text,form").attr("autocomplete", "off");

        //Fade out the updated/created message after being displayed.
        if ($('.alert').length > 0) {
            $('.alert').delay(2000).fadeOut(1000);
        }

        $('#saveDetails').click(function(event) {
            $('#action').val('save');

            $("#program").submit();
        });

        $('#saveCloseDetails').click(function(event) {
            $('#action').val('close');

            $("#program").submit();
        });
        
        //Open up the modal to display a list of available tables to associate with
        $(document).on('click', '#createNewSurveyTable', function() {
            $.ajax({
                url: '../availableTables',
                data: {'id':0},
                type: "GET",
                success: function(data) {
                    $("#surveyTableModal").html(data);
                }
            });
        });
        
        //Function to submit the changes to an existing user or 
        //submit the new user fields from the modal window.
        $(document).on('click', '#submitButton', function(event) {
            
            var formData = $("#availableTable").serialize();

            $.ajax({
                url: '../saveAvailableTables',
                data: formData,
                type: "POST",
                async: false,
                success: function(data) {
                    
                    if (data.indexOf('tableSaved') != -1) {
                       window.location.href = "/sysAdmin/programs/"+$('#progamNameURL').val()+"/?msg=tablesaved";
                    }
                    else {
                        $("#surveyTableModal").html(data);
                    }
                }
            });
            event.preventDefault();
            return false;

        });

        

    });
});


