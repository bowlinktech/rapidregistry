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
        
        //This function will launch the new MCI Algorithm overlay with a blank screen
        $(document).on('click', '#createNewAlgorithm', function() {
            $.ajax({
                url: 'mci-algorithms/algorithm.create',
                type: "GET",
                success: function(data) {
                    $("#algorithmDetailsModal").html(data);
                }
            });
        });
        
        
        $(document).on('click', '#addNewField', function() {
            var tableBody = $('#fieldTable').find("tbody");
            var trLast = tableBody.find("tr:last");
            var trNew = trLast.clone();

            trLast.after(trNew);
        });
        
        
        //Function to submit the changes to an existing user or 
        //submit the new user fields from the modal window.
        $(document).on('click', '#submitButton', function(event) {
           
            var formData = $("#mcidetailsform").serialize();

            var actionValue = $(this).attr('rel').toLowerCase();

            $.ajax({
                url: 'mci-algorithms/'+actionValue+'_mcialgorithm',
                data: formData,
                type: "POST",
                async: false,
                success: function(data) {

                    if (data.indexOf('algorithmUpdated') != -1) {
                        window.location.href = "mci-algorithms?msg=updated";

                    }
                    else if (data.indexOf('algorithmCreated') != -1) {
                        window.location.href = "mci-algorithms?msg=created";

                    }
                    else {
                        $("#algorithmDetailsModal").html(data);
                    }
                }
            });
            event.preventDefault();
            return false;

        });


        $(document).on('click', '.editAlgorithm', function() {
            var mciId = $(this).attr('rel');
            
            $.ajax({
                url: 'mci-algorithms/algorithm.edit',
                data: {'mciId': mciId},
                type: "GET",
                success: function(data) {
                    $("#algorithmDetailsModal").html(data);
                }
            });
        });
        
        $(document).on('click', '.removeAlgorithmField', function() {
            var fieldId = $(this).attr('rel');
            
            $.ajax({
                url: 'mci-algorithms/removeAlgorithmField.do',
                data: {'algorithmfieldId': fieldId},
                type: "POST",
                success: function(data) {
                   $('#row_'+fieldId).remove();
                }
            });
        });
        
        $(document).on('click', '.deleteAlgorithm', function() {
            var id = $(this).attr('rel');
            
            
            if(confirm("Are you sure you want to remvoe this MCI algorithm?")) {
                $.ajax({
                    url: 'mci-algorithms/removeAlgorithm.do',
                    data: {'algorithmId': id},
                    type: "POST",
                    success: function(data) {
                        window.location.href = "mci-algorithms?msg=deleted";
                    }
                });
            }
        });
        
    });
});




