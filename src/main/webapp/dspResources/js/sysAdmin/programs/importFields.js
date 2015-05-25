

require(['./main'], function () {
    require(['jquery'], function($) {
        
        $("input:text,form").attr("autocomplete", "off");
        populateFields(0);
        
        //Fade out the updated/created message after being displayed.
        if ($('.alert').length > 0) {
            $('.alert').delay(2000).fadeOut(1000);
        }
        
        
        //This function will handle populating the program import field table
        $(document).on('click', '#submitFieldButton', function() {
        	var selectedField = $('#field').val();
            var selectedFieldText = $('#field').find(":selected").attr("rel");
            var selectedValidation = $('#fieldValidation').val();
            var selectedValidationText = $('#fieldValidation').find(":selected").text();
           
            var required = $('#requiredField').val();
            var useField = $('#useField').val();
            var multiValue = $('#multiValue').val();
            
            
            
            //Remove all error classes and error messages
            $('div').removeClass("has-error");
            $('span').html("");

            var errorFound = 0;

            if (selectedField == "") {
                $('#fieldDiv').addClass("has-error");
                $('#fieldMsg').addClass("has-error");
                $('#fieldMsg').html('A field must be selected!');
                errorFound = 1;
            }
            
            if (errorFound == 0) {
                
                var importTypeId = $('#importTypeId').val();
                
                $.ajax({
                    url: "../setField.do",
                    type: "POST",
                    data: {'fieldId': selectedField, 'importTypeId': importTypeId, 'fieldText': selectedFieldText, 'validationId': selectedValidation
                        , 'validationName': selectedValidationText, 'requiredField': required, 'useField': useField, 'multiValue':multiValue
                    },
                    success: function(data) {
                        $('#fieldMsgDiv').show();
                        $("#existingFields").html(data);
                        //Need to clear out the select boxes
                        $('#field option:eq("")').prop('selected', true);
                        $('#fieldValidation option:eq("0")').prop('selected', true);
                        $('#requiredField option:eq("0")').prop('selected', true);
                    }
                });
            }


        });

        //Function that will handle changing a form display order and
        //making sure another field does not have the same display 
        //order selected. It will swap display position
        //values with the requested position.
        $(document).on('change', '.displayOrder', function() {
            //Store the current position
            var currDspPos = $(this).attr('rel');
            var newDspPos = $(this).val();

            $('.displayOrder').each(function() {
                if ($(this).attr('rel') == newDspPos) {
                    //Need to update the saved process order
                    $.ajax({
                        url: '../updateFieldDspOrder.do',
                        data: {'currdspOrder' : currDspPos,  'newdspOrder': newDspPos},
                        type: "POST",
                        success: function(data) {
                            $('#fieldMsgDiv').show();
                            populateFields(1);
                        }
                    });
                    $(this).val(currDspPos);
                    $(this).attr('rel', currDspPos);
                }
            });

            $(this).val(newDspPos);
            $(this).attr('rel', newDspPos);

        });
        
        
        //Edit an existing field
        $(document).on('click', '.editField', function() {
            
            $.ajax({
                url: '../fieldForm',
                data: {'id':$(this).attr('rel'),  'importTypeId': $('#importTypeId').val()},
                type: "GET",
                success: function(data) {
                    $("#fieldModal").html(data);
                }
            });
           
        });
        
        //Function to submit the changes to an existing user or 
        //submit the new user fields from the modal window.
        $(document).on('click', '#submitFieldEditButton', function(event) {
            
            var formData = $("#fieldForm").serialize();
            
            $.ajax({
                url: '../saveImportField',
                data: formData,
                type: "POST",
                async: false,
                success: function(data) {

                    if (data.indexOf('fieldSaved') != -1) {
                       window.location.href = "/sysAdmin/programs/"+$('#progamNameURL').val()+"/imports/fields?s="+$('#importTypeId').val()+"&msg=fieldsaved";
                    }
                    else {
                        $("#fieldModal").html(data);
                    }
                }
            });
            
           event.preventDefault();
           return false;
        });
        

        //Function that will handle removing a line item from the
        //existing data translations. Function will also update the
        //processing orders for each displayed.
        $(document).on('click', '.removeField', function() {
            var currPos = $(this).attr('rel2');
            var fieldId = $(this).attr('rel');

            //Need to remove the translation
            $.ajax({
                url: '../removeField.do',
                data: {'fieldId' : fieldId, 'dspOrder' : currPos},
                type: "POST",
                success: function(data) {
                    $('#fieldMsgDiv').show();
                    populateFields(1);
                }
            });

        });
        
        //Funciton that will save the section fields to the DB
        $(document).on('click', '#saveDetails', function() {
            
            var importTypeId = $('#importTypeId').val();
            
            $.ajax({
               url: '../saveImportFields.do',
               data: {'importTypeId' : importTypeId},
               type: "POST",
               success: function(data) {
                   window.location.href = "/sysAdmin/programs/"+$('#progamNameURL').val()+"/imports/fields?s="+importTypeId+"&msg=fieldssaved";
                }
            });
            
        });
        
        //The function that will be called when the "Next Step" button
        //is clicked
        $('#saveCloseDetails').click(function() {
            var importTypeId = $('#importTypeId').val();
            
            $.ajax({
               url: '../saveImportFields.do',
               data: {'importTypeId' : importTypeId},
                type: "POST",
                success: function(data) {
                    window.location.href = "../imports?msg=fieldssaved";
                }
            });
        });
        
      
        
    });
});


function populateFields(reload) {
    
    var importTypeId = $('#importTypeId').val();
    
    $.ajax({
        url: '../getFields.do',
        type: "GET",
        data: {'reload': reload, 'importTypeId': importTypeId},
        success: function(data) {
            $("#existingFields").html(data);
        }
    });
}

function removeVariableFromURL(url_string, variable_name) {
    var URL = String(url_string);
    var regex = new RegExp("\\?" + variable_name + "=[^&]*&?", "gi");
    URL = URL.replace(regex, '?');
    regex = new RegExp("\\&" + variable_name + "=[^&]*&?", "gi");
    URL = URL.replace(regex, '&');
    URL = URL.replace(/(\?|&)$/, '');
    regex = null;
    return URL;
}
