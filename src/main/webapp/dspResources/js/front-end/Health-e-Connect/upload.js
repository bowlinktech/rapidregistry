/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$.ajaxSetup({
    cache: false
});


jQuery(document).ready(function($) {
    
    $("input:text,form").attr("autocomplete", "off");
    
    //This function will launch the new file upload overlay with a blank screen
    $(document).on('click', '.uploadFile', function() {
        $.ajax({
            url: 'fileUploadForm',
            type: "GET",
            success: function(data) {
                $("#uploadFile").html(data);
            }
        });
    });
    
    //Function to submit file upload from the modal window.
    $(document).on('click', '#submitButton', function(event) {
        var errorFound = 0;
        
        //Remove any error message classes
        $('#configIdsDiv').removeClass("has-error");
        $('#configIdsMsg').removeClass("has-error");
        $('#configIdsMsg').html('');
        $('#uploadedFileDiv').removeClass("has-error");
        $('#uploadedFileMsg').removeClass("has-error");
        $('#uploadedFileMsg').html('');

        //Make sure at least one message type is selected
        if ($('#configIds').val() == '' || $('#configIds').val() == null) {
            $('#configIdsDiv').addClass("has-error");
            $('#configIdsMsg').addClass("has-error");
            $('#configIdsMsg').html('At least one message type must be selected.');
            errorFound = 1;
        }
        
        //Make sure a file is uploaded
        if ($('#uploadedFile').val() == '') {
            $('#uploadedFileDiv').addClass("has-error");
            $('#uploadedFileMsg').addClass("has-error");
            $('#uploadedFileMsg').html('The file is a required field.');
            errorFound = 1;
        }
        //Make sure the file is not an invalid format
        //(.jpg, .gif, .jpeg)
        if ($('#uploadedFile').val() != '' && ($('#uploadedFile').val().indexOf('.jpg') != -1 &&
                $('#uploadedFile').val().indexOf('.jpeg') != -1 &&
                $('#uploadedFile').val().indexOf('.gif') != -1 &&
                $('#uploadedFile').val().indexOf('.pdf') != -1)) {

            $('#uploadedFileDiv').addClass("has-error");
            $('#uploadedFileMsg').addClass("has-error");
            $('#uploadedFileMsg').html('The uploaded file cannot be an image.');
            errorFound = 1;

        }

        if (errorFound == 1) {
            event.preventDefault();
            return false;
        }

        $('#fileUploadForm').submit();

    });
});

