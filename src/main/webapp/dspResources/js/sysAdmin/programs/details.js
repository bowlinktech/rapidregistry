

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
        
        
        //Open up the modal to display the patient entry form
        $(document).on('click', '#createNewEntryMethod', function() {
            $.ajax({
                url: '../entryMethodForm',
                data: {'id':0},
                type: "GET",
                success: function(data) {
                    $("#entryMethodModal").html(data);
                }
            });
        });
        
        //Function to submit the changes to an existing user or 
        //submit the new user fields from the modal window.
        $(document).on('click', '#submitEntryButton', function(event) {
            
            var formData = $("#entryMethod").serialize();
            
            if($('#dspPos').val() == 0) {
                $('#dspPosDiv').addClass("has-error");
                $('#dspPosMsg').addClass("has-error");
                $('#dspPosMsg').html('A display position must be selected!');
            }
            else {
            
                $.ajax({
                   url: '../saveEntryMethod',
                   data: formData,
                   type: "POST",
                   async: false,
                   success: function(data) {

                       if (data.indexOf('entrySaved') != -1) {
                          window.location.href = "/sysAdmin/programs/"+$('#progamNameURL').val()+"/details?msg=entrysaved";
                       }
                       else {
                           $("#entryMethodModal").html(data);
                       }
                   }
               });
               
               event.preventDefault();
               return false;
           }
           
        });
        
        //Open up the modal to display the selected patient entry method form
        $(document).on('click', '.editEntryMethod', function() {
            
            $.ajax({
                url: '../entryMethodForm',
                data: {'id':$(this).attr('rel')},
                type: "GET",
                success: function(data) {
                    $("#entryMethodModal").html(data);
                }
            });
        });
        
        $(document).on('click', '.deleteEntryMethod', function(event) {
           
            if(confirm("Are you sure you want to remove this entry method?")) {
                $.ajax({
                    url: '../deleteEntryMethod',
                    data: {'id':$(this).attr('rel')},
                    type: "POST",
                    success: function(data) {
                        window.location.href = "/sysAdmin/programs/"+data+"/details?msg=entrydeleted";
                    }
                });
            }
            
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
        
        //Open up the modal to display a list of available tables to associate with
        $(document).on('click', '.editTable', function() {
            
            $.ajax({
                url: '../availableTables',
                data: {'id':$(this).attr('rel')},
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
            
            if($('#tableName').val() == 0) {
                $('#tableNameDiv').addClass("has-error");
                $('#tableNameMsg').addClass("has-error");
                $('#tableNameMsg').html('A table name must be selected!');
            }
            else {
                 $.ajax({
                    url: '../saveAvailableTables',
                    data: formData,
                    type: "POST",
                    async: false,
                    success: function(data) {

                        if (data.indexOf('tableSaved') != -1) {
                           window.location.href = "/sysAdmin/programs/"+$('#progamNameURL').val()+"/details?msg=tablesaved";
                        }
                        else {
                            $("#surveyTableModal").html(data);
                        }
                    }
                });
                event.preventDefault();
                return false;
            }

        });
        
        
        $(document).on('click', '.deleteTable', function(event) {
           
            if(confirm("Are you sure you want to remove this table?")) {
                $.ajax({
                    url: '../deleteAvailableTable',
                    data: {'id':$(this).attr('rel')},
                    type: "POST",
                    success: function(data) {
                        window.location.href = "/sysAdmin/programs/"+data+"/details?msg=tabledeleted";
                    }
                });
            }
            
        });
        
        

    });
});


