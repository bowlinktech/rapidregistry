/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

require(['./main'], function () {
    require(['jquery'], function ($) {

        //Fade out the updated/created message after being displayed.
        if ($('.alert').length > 0) {
            $('.alert').delay(2000).fadeOut(1000);
        }

        $("input:text,form").attr("autocomplete", "off");

        //Function to submit new survey - inserts title
        $('#saveNewSurvey').click(function (event) {
            $('#action').val('save');
            removeErrorClasses();
            $('#surveyForm').submit();
        });

        /** save and close new survey**/
        $('#saveNextNewSurvey').click(function (event) {
            $('#action').val('saveAndClose');
            removeErrorClasses();
            $('#surveyForm').submit();
        });


        /**survey changes - save button only **/
        $(document).on('click', '#saveSurveyDetails', function () {
            $('#action').val('save');
            removeErrorClasses();
            //we makes sure there is a note saved
            var actionValue = 'saveNote';

            $.ajax({
                url: actionValue,
                type: "GET",
                async: false,
                success: function (data) {
                    $('#surveySaveNote').html(data);
                }
            });
            event.preventDefault();
            return false;
        });
        // end of save survey only


        $(document).on('click', '#saveNextSurveyDetails', function () {
            $('#action').val('saveAndClose');
            removeErrorClasses();
            //we makes sure there is a note saved
            var actionValue = 'saveNote';

            $.ajax({
                url: actionValue,
                type: "GET",
                async: false,
                success: function (data) {
                    $('#surveySaveNote').html(data);
                }
            });
            event.preventDefault();
            return false;
        });


        /** we save note **/
        $(document).on('click', '#saveNote', function () {
            /**
             * make sure note is not blank
             **/
            removeErrorClasses();

            //we submit and save note
            var actionValue = 'saveNote';
            var notes = $('#notes').val();
            if (notes.trim().length < 1) {
                $('#notesDiv').addClass("has-error");
                $('#notesMsg').addClass("has-error");
                $('#notesMsg').html('Please enter a note.  It cannot be blank.');
                return false;
            }

            var notes = $('#notes').val();
            var id = $('#id').val();
            $.ajax({
                url: actionValue,
                data: {'surveyId': id, 'notes': notes.trim()},
                type: "POST",
                async: false,
                success: function (data) {
                    $('#surveyForm').submit();
                }
            });
            event.preventDefault();
            return false;
        });
        
        
        /** Button to open the overlay to select an existing survey to copy **/
        $(document).on('click', '#copySurvey', function() {
           
           $.ajax({
                url: 'getAvailableSurveys.do',
                type: "GET",
                success: function(data) {
                    $('#surveyCopyModal').html(data);
                }
           }); 
            
        });
        
        $(document).on('click', '#copySurveySubmit', function() {
           var selSurveyId = $('#availSurveys').val();
           
           if(selSurveyId > 0) {
               $.ajax({
                url: 'copySurveySubmit.do',
                data: {'surveyId':selSurveyId},
                type: "POST",
                success: function(data) {
                   var url = $(data).find('#encryptedURL').val();
                   window.location.href = "details"+url;
                }
           }); 
           }
        });


    });
});

function removeErrorClasses() {
    $('div.form-group').removeClass("has-error");
    $('span.control-label').removeClass("has-error");
    $('span.control-label').html("");
}



