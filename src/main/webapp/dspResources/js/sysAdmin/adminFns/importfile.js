/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

require(['./main'], function () {
    require(['jquery'], function($) {

        //Fade out the updated/created message after being displayed.
        if ($('.alert').length > 0) {
            $('.alert').delay(2000).fadeOut(10000);
        }

        $("input:text,form").attr("autocomplete", "off");
       
        //Need to populate the table columns or the selected table
        $(document).on('change', '.userId', function() {
        	var userId = $(this).val();
            $.ajax({
            	type: "POST",
                url: 'getProgramUploadTypes.do',
                data: {'userId': userId},
                success: function(data) {
                	$('#programUploadTypeIdDiv').show();
                	$('#programUploadTypeId').html(data);
                }
            });
            event.preventDefault();
            return false;
        });

      //Function to submit file upload from the modal window.
        $(document).on('click', '#submitButton', function(event) {
            var errorFound = 0;

            //Remove any error message classes
            
            $('div.form-group').removeClass("has-error");
            $('span.control-label').removeClass("has-error");
            $('span.control-label').html("");

            //Make sure at least one user is selected
            if ($('#userId').val() == '' || $('#userId').val() == null) {
                $('#userIdDiv').addClass("has-error");
                $('#userIdMsg').addClass("has-error");
                $('#userIdMsg').html('A user must be selected.');
                errorFound = 1;
            }
            if (errorFound == 0) {
            	if ($('#programUploadTypeId').val() == '' || $('#programUploadTypeId').val() == null) {
                    $('#programUploadTypeIdDiv').addClass("has-error");
                    $('#programUploadTypeIdMsg').addClass("has-error");
                    $('#programUploadTypeIdMsg').html('A program upload type must be selected.');
                    errorFound = 1;
                }
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
            if ($('#uploadedFile').val() != '' 
            	&& ($('#uploadedFile').val().indexOf('.jpg') != -1 ||
                    $('#uploadedFile').val().indexOf('.jpeg') != -1 ||
                    $('#uploadedFile').val().indexOf('.gif') != -1 ||
                    $('#uploadedFile').val().indexOf('.png') != -1 ||
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
});



