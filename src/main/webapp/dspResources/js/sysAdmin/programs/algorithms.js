/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

require(['./main'], function () {
    require(['jquery'], function($) {

        //Fade out the updated/created message after being displayed.
        if ($('.alert').length > 0) {
            $('.alert').delay(2000).fadeOut(5000);
        }

        $("input:text,form").attr("autocomplete", "off");
        
        //var oSettings = datatable.fnSettings();
        
        //datatable.fnSort( [ [3,'desc'] ] );
        
        //This function will launch the new MCI Algorithm overlay with a blank screen
        $(document).on('click', '#createNewAlgorithm', function() {
        	var importTypeId = $(this).attr('rel');
            $.ajax({
                url: 'mci-algorithms/algorithm.create',
                data:{'importTypeId':importTypeId},
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
            var algorithmId = $("#importTypeId").val();

            $.ajax({
                url: 'mci-algorithms/'+actionValue+'_mcialgorithm',
                data: formData,
                type: "POST",
                async: false,
                success: function(data) {

                    if (data.indexOf('algorithmUpdated') != -1) {
                        window.location.href = "mci-algorithms?s=" + algorithmId + "&msg=updated";

                    }
                    else if (data.indexOf('algorithmCreated') != -1) {
                        window.location.href = "mci-algorithms?s=" + algorithmId + "&msg=created";

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
            var algorithmId = $(this).attr('rel');
            
            $.ajax({
                url: 'mci-algorithms/algorithm.edit',
                data: {'algorithmId': algorithmId},
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
            var importTypeId = $(this).attr('rel2');
            
            
            if(confirm("Are you sure you want to remvoe this MCI algorithm?")) {
                $.ajax({
                    url: 'mci-algorithms/removeAlgorithm.do',
                    data: {'algorithmId': id, 'importTypeId': importTypeId},
                    type: "POST",
                    success: function(data) {
                        window.location.href = "mci-algorithms?msg=deleted";
                    }
                });
            }
        });
        
        
      //Function that will handle changing a process order and
        //making sure another field does not have the same display 
        //order selected. It will swap display position
        //values with the requested position.
        $(document).on('change', '.processOrder', function() {
            //Store the current position
            var currPos = $(this).attr('rel');
            var importTypeId = $(this).attr('rel2');
            var newPos = $(this).val();
            
            $('.processOrder').each(function() {
                if ($(this).attr('rel') == newPos  && $(this).attr('rel2') == importTypeId) {
                	//Need to update the saved process order
                	$.ajax({
                        url: 'mci-algorithms/updateProcessOrder.do',
                        data: {'sectionId' : sectionId, 'currOrder' : currPos,  'newOrder': newPos},
                        type: "POST",
                        success: function(data) {
                            $('#processOrderMsgDiv').show();
                        }
                    });                    
                    $(this).val(currPos);
                    $(this).attr('rel', currPos);
                }
            });

            $(this).val(newPos);
            $(this).attr('rel', newPos);

        });
             
    });
});



