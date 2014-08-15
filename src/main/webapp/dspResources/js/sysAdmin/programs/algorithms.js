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
        $(document).on('click', '#createNewAlgorithm', function() {
            $.ajax({
                url: 'algorithm.create',
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
           
            var formData = $("#mpidetailsform").serialize();

            var actionValue = $(this).attr('rel').toLowerCase();

            $.ajax({
                url: actionValue+'_mpialgorithm',
                data: formData,
                type: "POST",
                async: false,
                success: function(data) {

                    if (data.indexOf('algorithmUpdated') != -1) {
                        window.location.href = "mpi-algorithms?msg=updated";

                    }
                    else if (data.indexOf('algorithmCreated') != -1) {
                        window.location.href = "mpi-algorithms?msg=created";

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
            var mpiId = $(this).attr('rel');
            
            $.ajax({
                url: 'algorithm.edit',
                data: {'mpiId': mpiId},
                type: "GET",
                success: function(data) {
                    $("#algorithmDetailsModal").html(data);
                }
            });
        });
        
        $(document).on('click', '.removeAlgorithmField', function() {
            var fieldId = $(this).attr('rel');
            
            $.ajax({
                url: 'removeAlgorithmField.do',
                data: {'algorithmfieldId': fieldId},
                type: "POST",
                success: function(data) {
                   $('#row_'+fieldId).remove();
                }
            });
        });


    });
});




