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
        
        //This function will launch the new report overlay with a blank screen
        $(document).on('click', '#createNewReport', function() {
            $.ajax({
                url: 'reports/report.create',
                type: "GET",
                success: function(data) {
                    $("#reportModal").html(data);
                }
            });
        });
        
        //Function to submit the changes to an existing report or 
        //submit the new report fields from the modal window.
        $(document).on('click', '#submitButton', function(event) {
           
            var formData = $("#reportform").serialize();

            var actionValue = $(this).attr('rel').toLowerCase();

            $.ajax({
                url: 'reports/'+actionValue+'_report',
                data: formData,
                type: "POST",
                async: false,
                success: function(data) {

                    if (data.indexOf('reportUpdated') != -1) {
                        window.location.href = "reports?msg=updated";

                    }
                    else if (data.indexOf('reportCreated') != -1) {
                        window.location.href = "reports?msg=created";

                    }
                    else {
                        $("#reportModal").html(data);
                    }
                }
            });
            event.preventDefault();
            return false;

        });


        $(document).on('click', '.editReport', function() {
            var reportId = $(this).attr('rel');
            
            $.ajax({
                url: 'reports/report.edit',
                data: {'reportId': reportId},
                type: "GET",
                success: function(data) {
                    $("#reportModal").html(data);
                }
            });
        });


    });
});




