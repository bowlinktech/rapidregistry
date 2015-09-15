

require(['./main'], function () {
    require(['jquery'], function($) {
        
        $("input:text,form").attr("autocomplete", "off");
        populateCrosswalks(1);
        populateCustomFields(1);
        populateFields(0);
        
        //Fade out the updated/created message after being displayed.
        if ($('.alert').length > 0) {
            $('.alert').delay(2000).fadeOut(1000);
        }
        
        //This function will get the next/prev page for the crosswalk list
        $(document).on('click', '.nextPage', function() {
            var page = $(this).attr('rel');
            populateCrosswalks(page);
        });
        
        //This function will get the next/prev page for the custom field list
        $(document).on('click', '.nextcustonFieldPage', function() {
            var page = $(this).attr('rel');
            populateCustomFields(page);
        });
        
        


        //This function will launch the crosswalk overlay with the selected
        //crosswalk details
        $(document).on('click', '.viewCrosswalk', function() {
            $.ajax({
                url: '/sysAdmin/data-elements/viewCrosswalk' + $(this).attr('rel'),
                type: "GET",
                success: function(data) {
                    $("#crosswalkModal").html(data);
                }
            });
        });
        
        //This function will launch the new crosswalk overlay with a blank form
        $(document).on('click', '#createNewCrosswalk', function() {
            var orgId = $('#orgId').val();
            var sectionName = $('#sectionName').val();
               

            $.ajax({
                url: '/sysAdmin/data-elements/newCrosswalk',
                data: {'frompage': sectionName},
                type: "GET",
                success: function(data) {
                    $("#crosswalkModal").html(data);
                }
            });
        });

        //The function to submit the new crosswalk
        $(document).on('click', '#submitCrosswalkButton', function(event) {
            
            var sectionId = $('#sectionId').val();

            $('#crosswalkNameDiv').removeClass("has-error");
            $('#crosswalkNameMsg').removeClass("has-error");
            $('#crosswalkNameMsg').html('');
            $('#crosswalkDelimDiv').removeClass("has-error");
            $('#crosswalkDelimMsg').removeClass("has-error");
            $('#crosswalkDelimMsg').html('');
            $('#crosswalkFileDiv').removeClass("has-error");
            $('#crosswalkFileMsg').removeClass("has-error");
            $('#crosswalkFileMsg').html('');

            var errorFound = 0;
            var actionValue = $(this).attr('rel').toLowerCase();

            //Make sure a title is entered
            if ($('#name').val() == '') {
                $('#crosswalkNameDiv').addClass("has-error");
                $('#crosswalkNameMsg').addClass("has-error");
                $('#crosswalkNameMsg').html('The crosswalk name is a required field!');
                errorFound = 1;
            }

            //Need to make sure the crosswalk name doesn't already exist.
            var orgId = $('#orgId').val();

            $.ajax({
                url: '/sysAdmin/data-elements/checkCrosswalkName.do',
                type: "POST",
                async: false,
                data: {'name': $('#name').val(), 'orgId': orgId},
                success: function(data) {
                    if (data == 1) {
                        $('#crosswalkNameDiv').addClass("has-error");
                        $('#crosswalkNameMsg').addClass("has-error");
                        $('#crosswalkNameMsg').html('The name entered is already associated with another crosswalk in the system!');
                        errorFound = 1;
                    }
                }
            });

            //Make sure a delimiter is selected
            if ($('#delimiter').val() == '') {
                $('#crosswalkDelimDiv').addClass("has-error");
                $('#crosswalkDelimMsg').addClass("has-error");
                $('#crosswalkDelimMsg').html('The file delimiter is a required field!');
                errorFound = 1;
            }

            //Make sure a file is selected and is a text file
            if ($('#crosswalkFile').val() == '' || $('#crosswalkFile').val().indexOf('.txt') == -1) {
                $('#crosswalkFileDiv').addClass("has-error");
                $('#crosswalkFileMsg').addClass("has-error");
                $('#crosswalkFileMsg').html('The crosswalk file must be a text file!');
                errorFound = 1;
            }

            if (errorFound == 1) {
                event.preventDefault();
                return false;
            }
            
            $('#sectionIdVal').val(sectionId);
            $('#crosswalkdetailsform').attr('action', '/sysAdmin/data-elements/' + actionValue + 'Crosswalk');
            $('#crosswalkdetailsform').submit();

        });
        
        //Set the field display name on change
        $(document).on('change', '#field', function() {
            $('#customfield option:eq("")').prop('selected', true);
            var selectedFieldText = $('#field').find(":selected").attr("rel");
            $('#fieldDisplayName').val(selectedFieldText);
            
        });
        
        //Set the field display name on change of custom field
        $(document).on('change', '#customfield', function() {
            $('#field option:eq("")').prop('selected', true);
            var selectedFieldText = $('#customfield').find(":selected").attr("rel");
            $('#fieldDisplayName').val(selectedFieldText);
            
        });

        //This function will handle populating the program demographic field table
        //The trigger will be when a crosswalk is selected along with a
        //field
        $(document).on('click', '#submitFieldButton', function() {
            var selectedField = $('#field').val();
            var selectedCW = $('#crosswalk').val();
            var selectedFieldText = $('#field').find(":selected").attr("rel");
            var selectedCWText = $('#crosswalk').find(":selected").text();
            var selectedValidation = $('#fieldValidation').val();
            var selectedValidationText = $('#fieldValidation').find(":selected").text();
            var required = $('#requiredField').val();
            var hideField = $('#hideField').val();
            var fieldDisplayName = $('#fieldDisplayName').val();
            var dataGridColumn = $('#dataGridColumn').is(':checked');
            var searchColumn = $('#searchColumn').is(':checked');
            var summaryColumn = $('#summaryColumn').is(':checked');
            var selectedCustomField = $('#customfield').val();
            var selectedCustomFieldText = $('#customfield').find(":selected").attr("rel");
            var readOnly = $('#readOnlyField').val();
            
            //Remove all error classes and error messages
            $('div').removeClass("has-error");
            $('span').html("");

            var errorFound = 0;

            if (selectedField == 0 && selectedCustomField == 0) {
                $('#fieldDiv').addClass("has-error");
                $('#fieldMsg').addClass("has-error");
                $('#fieldMsg').html('A field must be selected!');
                $('#customfieldDiv').addClass("has-error");
                $('#customfieldMsg').addClass("has-error");
                $('#customfieldMsg').html('A field must be selected!');
                errorFound = 1;
            }
            
            if (errorFound == 0) {
                
                var sectionId = $('#sectionId').val();
                var section = $('#sectionName').val();
                
                $.ajax({
                    url: "../setField.do",
                    type: "POST",
                    data: {'fieldId': selectedField, 'sectionId': sectionId, 'fieldText': selectedFieldText, 'fieldDisplayName': fieldDisplayName, 'cw': selectedCW, 'CWText': selectedCWText, 'validationId': selectedValidation
                        , 'validationName': selectedValidationText, 'requiredField': required, 'hideField': hideField, 'dataGridColumn' : dataGridColumn, 'section' : section
                        , 'searchColumn': searchColumn, 'summaryColumn': summaryColumn, 'customFieldId': selectedCustomField, 'customFieldText': selectedCustomFieldText, 'readOnly': readOnly
                    },
                    success: function(data) {
                        $('#fieldMsgDiv').show();
                        $("#existingFields").html(data);
                        //Need to clear out the select boxes
                        $('#field option:eq("")').prop('selected', true);
                        $('#customfield option:eq("")').prop('selected', true);
                        $('#crosswalk option:eq("")').prop('selected', true);
                        $('#fieldValidation option:eq("0")').prop('selected', true);
                        $('#requiredField option:eq("0")').prop('selected', true);
                        $('#readOnlyField option:eq("0")').prop('selected', true);
                        $('#fieldDisplayName').val("");
                        $('#dataGridColumn').attr('checked', false); 
                        $('#searchColumn').attr('checked', false); 
                        $('#summaryColumn').attr('checked', false); 
                        $('#hideField option:eq("0")').prop('selected', true);
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
            var section = $('#sectionName').val();

            $('.displayOrder').each(function() {
                if ($(this).attr('rel') == newDspPos) {
                    //Need to update the saved process order
                    $.ajax({
                        url: '../updateFieldDspOrder.do',
                        data: {'section' : section, 'currdspOrder' : currDspPos,  'newdspOrder': newDspPos},
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
        
        
        //Function that will handle changing a search/summary display order and
        //making sure another field does not have the same display order 
        //selected. It will swap display position
        //values with the requested position.
        $(document).on('change', '.searchdisplayOrder', function() {
            //Store the current position
            var currDspPos = $(this).attr('rel');
            var newDspPos = $(this).val();
            var section = $('#sectionName').val();

            $('.searchdisplayOrder').each(function() {
                if ($(this).attr('rel') == newDspPos) {
                    //Need to update the saved process order
                    $.ajax({
                        url: '../updateFieldSearchDspOrder.do',
                        data: {'section' : section, 'currdspOrder' : currDspPos,  'newdspOrder': newDspPos},
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
                data: {'id':$(this).attr('rel'), 'section': $('#sectionName').val(), 'sectionId': $('#sectionId').val()},
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
            
            if($('#section').val() == 'patient-sections') {
                var url = '../savePatientField';
            }
            else {
                var url = '../saveEngagementField';
            }
            
            $.ajax({
                url: url,
                data: formData,
                type: "POST",
                async: false,
                success: function(data) {

                    if (data.indexOf('fieldSaved') != -1) {
                       window.location.href = "/sysAdmin/programs/"+$('#progamNameURL').val()+"/forms/"+$('#section').val()+"/fields?s="+$('#sectionId').val()+"&msg=fieldsaved";
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
            var section = $('#sectionName').val();
            var currSearchPos = $(this).attr('rel3');

            //Need to remove the translation
            $.ajax({
                url: '../removeField.do',
                data: {'section' : section, 'fieldId' : fieldId, 'dspOrder' : currPos, 'searchDspOrder': currSearchPos },
                type: "POST",
                success: function(data) {
                    $('#fieldMsgDiv').show();
                    populateFields(1);
                }
            });

        });
        
        //Funciton that will save the section fields to the DB
        $(document).on('click', '#saveDetails', function() {
            
            var sectionId = $('#sectionId').val();
            var section = $('#sectionName').val();
            
            $.ajax({
               url: '../saveSectionFields.do',
               data: {'section' : section, 'sectionId' : sectionId},
               type: "POST",
               success: function(data) {
                   window.location.href = "/sysAdmin/programs/"+$('#progamNameURL').val()+"/forms/"+section+"/fields?s="+sectionId+"&msg=fieldssaved";
                }
            });
            
        });
        
        //The function that will be called when the "Next Step" button
        //is clicked
        $('#saveCloseDetails').click(function() {
            var sectionId = $('#sectionId').val();
            var section = $('#sectionName').val();
            
            $.ajax({
               url: '../saveSectionFields.do',
               data: {'section' : section, 'sectionId' : sectionId},
                type: "POST",
                success: function(data) {
                    window.location.href = "../"+section+"?msg=fieldssaved";
                }
            });
        });
        
        
        //The function to populate the select fields modal
        $(document).on('click', '.selectValues', function() {
            var section = $('#sectionName').val();
            var fieldId = $(this).attr('rel');

            $.ajax({
                url: '../getFieldValues',
                data: {'section': section, 'fieldId': fieldId},
                type: "GET",
                success: function(data) {
                    $("#selectValuesModal").html(data);
                }
            });
        });
        
        $(document).on('click', '#submitFieldValuesButton', function(event) {
            
            var sectionId = $('#sectionId').val();
            var section = $('#section').val();
            var fieldId = $('#fieldId').val();
            
            
            var valueList = "";
            $('.valueCheckbox').each(function() {
                if(this.checked) {
                    valueList += $(this).val() + '~' + $(this).next('label').text() + '|';
                }
            })
            
            $.ajax({
                url: '../saveFieldValues',
                data: {'section': section, 'fieldId': fieldId, 'selectedValues': valueList.substring(0,valueList.length-1)},
                type: "POST",
                async: false,
                success: function(data) {

                    if (data.indexOf('valuesSaved') != -1) {
                       window.location.href = "/sysAdmin/programs/"+$('#progamNameURL').val()+"/forms/"+section+"/fields?s="+sectionId+"&msg=fieldValuesSaved";
                    }
                    else {
                        $("#selectValuesModal").html(data);
                    }
                }
            });
            event.preventDefault();
            return false;
            
        });
        
        $(document).on('click', '.selectAllFieldValues', function() {
            if(this.checked) {
                 $('.valueCheckbox').each(function() {
                    this.checked = true;
                 });
            }
            else {
                $('.valueCheckbox').each(function() {
                    this.checked = false;
                 });
            }
           
            
        });
        
        //This function will launch the new custom field overlay with a blank form
        $(document).on('click', '#createNewCustomField', function() {
            var orgId = $('#orgId').val();
            var sectionName = $('#sectionName').val();

            $.ajax({
                url: '/sysAdmin/data-elements/newCustomField',
                data: {'frompage': sectionName},
                type: "GET",
                success: function(data) {
                    $("#customFieldModal").html(data);
                }
            });
        });
        
        //This function will launch the custom field overlay with the selected
        //field details
        $(document).on('click', '.viewCustomField', function() {
            var sectionName = $('#sectionName').val();
            $.ajax({
                url: '/sysAdmin/data-elements/viewCustomField' + $(this).attr('rel'),
                data: {'frompage': sectionName},
                type: "GET",
                success: function(data) {
                    $("#customFieldModal").html(data);
                }
            });
        });
        
        //The function to submit the new custom field
        $(document).on('click', '#submitCustomField', function(event) {
            
            var sectionId = $('#sectionId').val();
            
            $('#fieldNameDiv').removeClass("has-error");
            $('#fieldNameMsg').removeClass("has-error");
            $('#fieldNameMsg').html('');
            $('#saveToTableDiv').removeClass("has-error");
            $('#saveToTableMsg').removeClass("has-error");
            $('#saveToTableMsg').html('');
            $('#saveToTableColDiv').removeClass("has-error");
            $('#saveToTableColMsg').removeClass("has-error");
            $('#saveToTableColMsg').html('');

            var errorFound = 0;
          
            //Make sure a field Name is entered
            if ($('#fieldName').val() == '') {
                $('#fieldNameDiv').addClass("has-error");
                $('#fieldNameMsg').addClass("has-error");
                $('#fieldNameMsg').html('The field name is a required field!');
                errorFound = 1;
            }
            else {
                $.ajax({
                    url: '/sysAdmin/data-elements/checkCustomFieldName.do',
                    type: "POST",
                    async: false,
                    data: {'name': $('#fieldName').val(), 'fieldId': $('#id').val()},
                    success: function(data) {
                        if (data == 1) {
                            $('#fieldNameDiv').addClass("has-error");
                            $('#fieldNameMsg').addClass("has-error");
                            $('#fieldNameMsg').html('The field name entered has already been used!');
                            errorFound = 1;
                        }
                    }
                });
            }

            //Make sure a table is selected
            if ($('#saveToTable').val() == '') {
                $('#saveToTableDiv').addClass("has-error");
                $('#saveToTableMsg').addClass("has-error");
                $('#saveToTableMsg').html('The save to table is a required field!');
                errorFound = 1;
            }

            //Make sure a table column is selected
            if ($('#saveToTableCol').val() == '') {
                $('#saveToTableColDiv').addClass("has-error");
                $('#saveToTableColMsg').addClass("has-error");
                $('#saveToTableColMsg').html('The save to table column is a required field!');
                errorFound = 1;
            }

            if (errorFound == 1) {
                event.preventDefault();
                return false;
            }
            
            $('#sectionIdVal').val(sectionId);
            $('#customfielddetailsform').attr('action', '/sysAdmin/data-elements/saveCustomField');
            $('#customfielddetailsform').submit();

        });
        
        
        //Function that will handle setting a default value for a field
        //selected crosswalk.
        $(document).on('change', '.setDefaultValue', function() {
            var id = $(this).attr('rel');
            var value = $(this).val();
            var sectionName = $('#sectionName').val();
            
             //Need to update the saved process order
            $.ajax({
                url: '../updateDefaultValue?section=' + sectionName + '&fieldId=' + id + '&selValue=' + value,
                type: "POST",
                success: function(data) {
                    $('#fieldMsgDiv').show();
                    populateFields(1);
                }
            });
            
        });
        
    });
    
    
});


function populateFields(reload) {
    
    var sectionId = $('#sectionId').val();
    var section = $('#sectionName').val();
    
    $.ajax({
        url: '../getFields.do',
        type: "GET",
        data: {'reload': reload, 'section': section, 'sectionId': sectionId},
        success: function(data) {
            $("#existingFields").html(data);
        }
    });
}

function populateCrosswalks(page) {
    var orgId = $('#orgId').val();

    $.ajax({
        url: '/sysAdmin/data-elements/getCrosswalks.do',
        type: "GET",
        data: {'page': page, 'maxCrosswalks': 8},
        success: function(data) {
            $("#crosswalksTable").html(data);
        }
    });
}

function populateCustomFields(page) {
    var orgId = $('#orgId').val();

    $.ajax({
        url: '/sysAdmin/data-elements/getCustomFields.do',
        type: "GET",
        data: {'page': page, 'maxFields': 8},
        success: function(data) {
            $("#customFieldTable").html(data);
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
