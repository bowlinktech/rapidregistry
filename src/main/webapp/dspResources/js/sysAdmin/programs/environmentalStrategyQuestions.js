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
        
        //This function will launch the new crosswalk overlay with a blank form
        $(document).on('click', '.questionDetails', function() {
            var code = $(this).attr('rel');
            var qId = $(this).attr('id');
               
            $.ajax({
                url: 'environmentalStrategyQuestion',
                data: {
                    'qId': qId,
                    'code': code
                },
                type: "GET",
                success: function(data) {
                    $("#questionModal").html(data);
                }
            });
        });
        
        //The function to submit the new Question
        $(document).on('click', '#submitQuestionButton', function(event) {

            $('#questionMsgDiv').removeClass("has-error");
            $('#questionMsgMsg').removeClass("has-error");
            $('#questionMsgMsg').html('');
            $('#defaultValueDiv').removeClass("has-error");
            $('#defaultValueMsg').removeClass("has-error");
            $('#defaultValueMsg').html('');
            $('#qTagDiv').removeClass("has-error");
            $('#qTagMsg').removeClass("has-error");
            $('#qTagMsg').html('');
            $('#maxCharactersAllowedDiv').removeClass("has-error");
            $('#maxCharactersAllowedMsg').removeClass("has-error");
            $('#maxCharactersAllowedMsg').html('');
            $('#maxValueDiv').removeClass("has-error");
            $('#maxValueMsg').removeClass("has-error");
            $('#maxValueMsg').html('');

            var errorFound = 0;
            var actionValue = $(this).attr('rel').toLowerCase();

            //Make sure a question is entered
            if ($('#question').val() == '') {
                $('#questionMsgDiv').addClass("has-error");
                $('#questionMsgMsg').addClass("has-error");
                $('#questionMsgMsg').html('The question is a required field!');
                errorFound = 1;
            }
            
            //Make sure a qTag is entered
            if ($('#defaultValue').val() == '') {
                $('#defaultValueDiv').addClass("has-error");
                $('#defaultValueMsg').addClass("has-error");
                $('#defaultValueMsg').html('The default value is a required field!');
                errorFound = 1;
            }

            //Make sure a qTag is entered
            if ($('#qTag').val() == '') {
                $('#qTagDiv').addClass("has-error");
                $('#qTagMsg').addClass("has-error");
                $('#qTagMsg').html('The question tag is a required field!');
                errorFound = 1;
            }
            
            //Make sure a max value is entered
            if ($('#maxValue').val() == '') {
                $('#maxValueDiv').addClass("has-error");
                $('#maxValueMsg').addClass("has-error");
                $('#maxValueMsg').html('The max field value is a required field!');
                errorFound = 1;
            }
            
            //Make sure a max Characters Allowed is entered
            if ($('#maxCharactersAllowed').val() == '') {
                $('#maxCharactersAllowedDiv').addClass("has-error");
                $('#maxCharactersAllowedMsg').addClass("has-error");
                $('#maxCharactersAllowedMsg').html('The max number of characters allowed is a required field!');
                errorFound = 1;
            }


            if (errorFound == 1) {
                event.preventDefault();
                return false;
            }
            
            $('#questionform').submit();

        });

        
    });
});