/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

require(['./main'], function () {
    require(['jquery', 'summernote'], function ($) {

        //Fade out the updated/created message after being displayed.
        if ($('.alert').length > 0) {
            $('.alert').delay(2000).fadeOut(1000);
        }

        /** scroll, tracking, make sure we know what page we are on **/
        window.pageId = 1;
        window.questionId = 0;

        /** scroll, making sure side bar stays along with main page **/
        $(document).ready(function () {

            /** Handle Page Logic Dropdown **/
            $(document).on('change', '.pageLogic', function () {
                var pageId = $(this).attr('rel');

                if ($(this).val() === 'Page Skip Logic') {
                    $('#pageLogic_' + pageId).show();
                }
                else {
                    $('#pageLogic_' + pageId).hide();
                }
            });

            getSurveyPages();

            /** Have the left menu scroll with the page **/
            var el = $('.secondary');
            var elpos_original = el.offset().top;
            $(window).scroll(function () {
                var elpos = el.offset().top;
                var windowpos = $(window).scrollTop();
                var finaldestination = windowpos;
                if (windowpos < elpos_original) {
                    finaldestination = elpos_original;
                    el.stop().css({'top': 10});
                } else if (windowpos > elpos_original) {
                    el.stop().animate({'top': finaldestination - elpos_original + 10}, 500);
                }

                var top = false;
                var total = $('.pageQuestionsPanel').length;
                $(".pageQuestionsPanel").each(function (index) {
                    var offset = $(this).offset();

                    if (windowpos + $(window).height() == $(document).height() && top == false) {
                        if (index == total - 1) {

                            window.pageId = $(this).attr('rel');
                            top = true;
                            return false;
                        }
                    }
                    else if (((offset.top + 100) > windowpos) && top == false) {
                        window.pageId = $(this).attr('rel');
                        top = true;
                        return false;
                    }
                });

            });

            /** dropdown change **/
            $(document).on('change', '.ddForPage', function () {
                var cTarget = ($(this).val() * 1) - 1;

                if (cTarget == 0) {
                    $('html, body').animate({
                        scrollTop: $("#surveyPages").offset().top
                    }, 1000);
                }
                else {
                    $('html, body').animate({
                        scrollTop: $("#newPageDiv_" + cTarget).offset().top
                    }, 1000);
                }

            });

            /** when clicking on the title, we want to replace
             * div with form that has survey title info
             */
            //Function to open the new program association modal
            $(document).on('click', '.editSurveyTitle', function () {

                var s = $(this).attr("rel");
                var v = $(this).attr("rel2");

                $.ajax({
                    url: 'getSurveyForm.do',
                    data: {'s': s, 'v': v},
                    type: "GET",
                    success: function (data) {
                        $(surveyModal).html(data);
                    }
                });
            });


            /** submit survey title form **/
            $(document).on('click', '#submitSurveyButton', function (event) {

                $('div.form-group').removeClass("has-error");
                $('span.control-label').removeClass("has-error");
                $('span.control-label').html("");

                var formData = $("#surveyForm").serialize();

                $.ajax({
                    url: "saveSurveyForm.do",
                    data: formData,
                    type: "POST",
                    async: false,
                    success: function (data) {

                        if (data.indexOf('updated') != -1) {
                            //update all titles on the page
                            if ($('#editSurveyTitleActionBar').text() != $("#title").val()) {
                                $(".editSurveyTitle").each(function (index, element) {
                                    $(element).html($("#title").val());
                                });
                            }
                            $('#surveyModal').modal('toggle');
                        } else {
                            $("#surveyModal").html(data);
                        }
                    }
                });
                event.preventDefault();
                return false;

            });

            /** end of survey title modal js **/


            /** edit page title **/
            $(document).on('click', '.editPageTitle', function () {
                var pageId = $(this).attr("rel");

                //Hide the heading
                $('#pageTitleHeading_' + pageId).hide();

                //Show the input field
                $('#pageTitleEdit_' + pageId).show();

            });


            /** submit page title form **/
            $(document).on('click', '.submitPageTitleChanges', function (event) {
                var pageId = $(this).attr("rel");

                $('div.form-group').removeClass("has-error");
                $('span.control-label').removeClass("has-error");
                $('span.control-label').html("");


                /** page title cannot be blank at this point **/
                if ($("#pageTitle_" + pageId).val().trim().length < 1) {
                    $('#pageTitleDiv_' + pageId).addClass("has-error");
                    return false;
                }

                $.ajax({
                    url: "savePageTitle.do",
                    data: {'pageId': pageId, 'pageTitle': $("#pageTitle_" + pageId).val().trim()},
                    type: "POST",
                    async: false,
                    success: function (data) {

                        //Show the heading
                        $('#pageTitleSpan_' + pageId).html(data);
                        $('#pageTitleHeading_' + pageId).show();

                        //Hide the input field
                        $('#pageTitleEdit_' + pageId).hide();
                    }
                });
            });

            /** Submit the page skip logic **/
            $(document).on('click', '.submitPageSkipChanges', function (event) {

                var pageId = $(this).attr("rel");

                $.ajax({
                    url: "savePageSkipLogic.do",
                    data: {'pageId': pageId, 'skipToPageId': $("#selSkipToPage_" + pageId).val()},
                    type: "POST",
                    async: false,
                    success: function (data) {
                        $('#pageLogic_' + pageId).hide();
                        $(".pageLogic").val($(".pageLogic option:first").val());
                    }
                });


            });

            /** New Page Button **/
            $(document).on('click', '.newPage', function () {

                var lastPageId = $(this).attr('rel');

                var s = $('#s').val();
                var v = $('#v').val();

                $.ajax({
                    url: "addNewSurveyPage.do",
                    data: {'s': s, 'v': v, 'lastPageId': lastPageId},
                    type: "POST",
                    async: false,
                    success: function (data) {
                        getSurveyPages();
                    }
                });


            });

            /** Hover over answer type in the menu **/
            $(document).on('mouseover', '.QuestionTypeDiv', function () {
                var answertypeid = $(this).attr('rel');

                $('.addQuestionTypeBtn').hide();
                $('#answertype_' + answertypeid).show();
            });

            /** Click to add a new question type **/
            $(document).on('click', '.addQuestionType', function () {

                var questionType = $(this).attr('rel');

                var s = $('#s').val();
                var v = $('#v').val();

                $.ajax({
                    url: "addNewPageQuestion.do",
                    data: {'s': s, 'v': v, 'pageId': window.pageId, 'questionType': questionType},
                    type: "GET",
                    async: false,
                    success: function (data) {
                        $('#newQuestionDiv_' + window.pageId).html(data);
                        $('#newQuestionDiv_' + window.pageId).show();
                        $('#emptyPageDiv_' + window.pageId).hide();
                    }
                });
            });

        });

        /** Hover over a question **/
        $(document).on('mouseover', '.questionDiv', function () {
            var qId = $(this).attr('rel');
            $('.questionDiv').removeClass("hoverQuestion");
            $(this).addClass("hoverQuestion");
            $('#questionBtns' + qId).show();
        });

        $(document).on('mouseleave', '.questionDiv', function () {
            var qId = $(this).attr('rel');
            $('#questionBtns' + qId).hide();
            $(this).removeClass("hoverQuestion");
        });


        /****************************************/
        /**** Question Functions - Start ********/
        /****************************************/

        /** Click one of the question buttons **/
        $(document).on('click', '.questionButton', function () {
            
            var questionId = $(this).attr('rel');
            var pane = $(this).attr('pane');
            var qNum = $('#qNum' + questionId).attr('rel');

            window.questionId = questionId;

            var s = $('#s').val();
            var v = $('#v').val();

            $.ajax({
                url: "editPageQuestion.do",
                data: {'s': s, 'v': v, 'pageId': window.pageId, 'questionId': questionId},
                type: "GET",
                async: false,
                success: function (data) {

                    if (pane === "movePane") {
                        data = data.replace("submitQuestion", "moveQuestion");
                    }
                    else if (pane === "copyPane") {
                        data = data.replace("submitQuestion", "copyQuestion");
                    }

                    $('#editQuestionDiv_' + questionId).html(data);
                    $('#editQuestionDiv_' + questionId).show();
                    $('#questionDiv' + questionId).hide();

                    $('.tab-pane-question').removeClass("active");
                    $('.tab-pane-question').removeClass("in");
                    $('.nav-tabs-question li').removeClass("active");

                    $('.' + pane + 'Tab').addClass("active");
                    $('.' + pane).addClass("active");
                    $('.' + pane).addClass("in");

                    /* Set the question number */
                    //$('.qNum').html("Q"+qNum);

                    if (pane === "logicPane") {
                        /* Loop through existing skip to pages to get columns */
                        $('.logicskipToPage').each(function () {
                            var indexVal = $(this).attr('rel');
                            var pageId = $(this).val();

                            if (pageId > 0) {

                                var selValue = $('#logicskipToQuestion_' + indexVal).attr('rel2');

                                populateTableValues(pageId, window.questionId, indexVal, selValue);

                            }
                        });
                    }
                }
            });
        });

        /** If the moveTab is clicked chagne the button value **/
        $(document).on('click', '.paneTab', function () {

            var whichPane = $(this).attr('href');

            if (whichPane === "#move") {
                $('.moveToQuestion').html("");
                $('.movePositionDiv').hide();
                $('.moveToQuestionDiv').hide();
                $('.saveQuestionBtn').attr("id", "moveQuestion");
            }
            else if (whichPane === "#copy") {
                $('.moveToQuestion').html("");
                $('.movePositionDiv').hide();
                $('.moveToQuestionDiv').hide();
                $('.saveQuestionBtn').attr("id", "copyQuestion");
            }
            else if (whichPane === "#logic") {
                /* Loop through existing skip to pages to get columns */
                $('.logicskipToPage').each(function () {
                    var indexVal = $(this).attr('rel');
                    var pageId = $(this).val();

                    if (pageId > 0) {

                        var selValue = $('#logicskipToQuestion_' + indexVal).attr('rel2');

                        populateTableValues(pageId, window.questionId, indexVal, selValue);

                    }
                });
                $('.saveQuestionBtn').attr("id", "submitQuestion");
            }
            else {
                $('.saveQuestionBtn').attr("id", "submitQuestion");
            }

        });

        /** Cancel add/edit question type **/
        $(document).on('click', '#cancelQuestion', function () {

            var qNum = $(this).attr('rel');
            var qId = $(this).attr('rel2');
            var pageId = $(this).attr('rel3');

            $('#newQuestionDiv_' + pageId).html("");
            $('#newQuestionDiv_' + pageId).hide();

            if (qId > 0) {
                $('#editQuestionDiv_' + qId).html("");
                $('#editQuestionDiv_' + qId).hide();
                $('#questionDiv' + qId).show();
            }
            else if ($('#pagePanel_' + pageId).children().children().length == 2) {
                $('#emptyPageDiv_' + pageId).show();
            }


        });

        /** Function to show the validation div for the edit/add question **/
        $(document).on('change', '#required', function () {

            if ($(this).is(':checked')) {
                $('#requiredResponseDiv').show();
            }
            else {
                $('#requiredResponseDiv').hide();
            }
        });
        
        /** Function to show the validation div for the edit/add question **/
        $(document).on('change', '#showOnSummaryPage', function () {

            if ($(this).is(':checked')) {
                $('#showOnSummaryPageResponseDiv').show();
            }
            else {
                $('#showOnSummaryPageResponseDiv').hide();
            }
        });

        /** Function to show the save to field div for the edit/add question **/
        $(document).on('change', '#saveTo', function () {

            if ($(this).is(':checked')) {
                $('#saveToDiv').show();
            }
            else {
                $('#saveToDiv').hide();
            }

        });

        /** Function to show the validation div for the edit/add question **/
        $(document).on('change', '#validateField', function () {

            if ($(this).is(':checked')) {
                $('#validationDiv').show();
            }
            else {
                $('#validationDiv').hide();
            }

        });

        /** Function to show the auto populate table list **/
        $(document).on('change', '#populate', function () {
            $('#questionChoiceDiv').hide();
            $('#populateFromTable').val("");
            if ($(this).is(':checked')) {
                $('#manual').prop('checked', false);
                $('#autoPopulateDiv').show();
            }
            else {
                $('#autoPopulateDiv').hide();
            }

        });

        /** Function to auto populate the answers when a table is selected **/
        $(document).on('change', '#populateFromTable', function () {

            var selTable = $(this).val();
            var cwId = 0;
            
            if($.isNumeric(selTable)) {
                cwId = eval(selTable*1);
                selTable = "";
            }
            
            var s = $('#s').val();
            var v = $('#v').val();

            var questionId = window.questionId;

            $.ajax({
                url: 'getQuestionChoicesForSelTable.do',
                data: {'s': s, 'v': v, 'pageId': window.pageId, 'questionId': questionId, 'tableName': selTable, 'cwId': cwId},
                type: "GET",
                success: function (data) {
                    $('#editQuestionDiv_' + questionId).html(data);
                    $('#editQuestionDiv_' + questionId).show();
                    $('#questionDiv' + questionId).hide();

                    $('.tab-pane-question').removeClass("active");
                    $('.tab-pane-question').removeClass("in");
                    $('.nav-tabs-question li').removeClass("active");

                    $('.editPane').addClass("active");
                    $('.editPane').addClass("active");
                    $('.editPane').addClass("in");
                }
            });

        });

        /** Function to show the auto populate table list **/
        $(document).on('change', '#manual', function () {
            $('#questionChoiceDiv').show();
            $('#autoPopulateDiv').hide();
            $('#populate').prop('checked', false);

            var s = $('#s').val();
            var v = $('#v').val();

            var questionId = window.questionId;

            $.ajax({
                url: 'getQuestionManualChoices.do',
                data: {'s': s, 'v': v, 'pageId': window.pageId, 'questionId': questionId},
                type: "GET",
                success: function (data) {
                    $('#editQuestionDiv_' + questionId).html(data);
                    $('#editQuestionDiv_' + questionId).show();
                    $('#questionDiv' + questionId).hide();

                    $('.tab-pane-question').removeClass("active");
                    $('.tab-pane-question').removeClass("in");
                    $('.nav-tabs-question li').removeClass("active");

                    $('.editPane').addClass("active");
                    $('.editPane').addClass("active");
                    $('.editPane').addClass("in");
                }
            });

        });

        /** Function to add a new question choice **/
        $(document).on('click', '.addChoice', function () {
            var newIndexVal = (($('.choiceTable tr:last').attr('rel') * 1) + 1);
	    
            $('.choiceTable').append($('.choiceTable tr:last').clone());

            $('.choiceTable tr:last').attr("rel", newIndexVal);

            $('.choiceTable tr:last').find('input[type=text]').each(function () {
                $(this).val("");
                $(this).attr("name", 'questionChoices[' + newIndexVal + '].choiceText');
                $(this).attr("rel", newIndexVal);
            });

            $('.choiceTable tr:last').find('input[type=radio]').each(function () {
                $(this).attr("name", 'questionChoices[' + newIndexVal + '].defAnswer');
            });

            $('.choiceTable tr:last').find('select').each(function () {
                $(this).val(0);
                $(this).attr("name", 'questionChoices[' + newIndexVal + '].activityCodeId');
            });
	    
	    $('.choiceTable tr:last').find('#answerNum').each(function () {
                $(this).val((newIndexVal*1)+1);
            });
	    
	    $('.choiceTable tr:last').find('#answerScore').each(function () {
                $(this).val((newIndexVal*1)+1);
            });
	    
        });

        /** Function to add a remove question choice **/
        $(document).on('click', '.removeChoice', function () {
            var indexVal = $(this).closest('tr').attr("rel");
            $('#id_' + indexVal).remove();
            $('#questionId_' + indexVal).remove();
            $('#skipToPageId_' + indexVal).remove();
            $('#skipToEnd_' + indexVal).remove();
            $('#skipToQuestionId_' + indexVal).remove();
            $('#choiceValue_' + indexVal).remove();
            $(this).closest('tr').remove();
        });

       
        /** Function to clear out all other selected default answers **/
        $(document).on('click', '.defAnswer', function () {

            $('.choiceTable').find('input[type=radio]').each(function () {
                $(this).prop('checked', false);
            });

            $(this).prop('checked', true);

        });

        /** Function to show the multiple choice column display div for the edit/add question **/
        $(document).on('change', '#choiceLayout', function () {

            if ($(this).is(':checked')) {
                $('#choiceLayoutDiv').show();
            }
            else {
                $('#choiceLayoutDiv').hide();
            }

        });
        
        $(document).on('change', '#dateType', function() {
           
            if($(this).val() != 3) {
                $('#collectTime').show();
            }
            else {
                $('#includeTime').val(0);
                $('#collectTime').hide();
            }
            
        });

        /** Function to show the dropdown OTHER column display div for the edit/add question **/
        $(document).on('change', '#otherOption', function () {

            if ($(this).is(':checked')) {
                $('#otherOptionDiv').show();
            }
            else {
                $('#otherOptionDiv').hide();
            }

        });

        /** Function to populate the list of questions for the selected page **/
        $(document).on('change', '.moveToPage', function () {
            var pageId = $(this).val();

            if (pageId == "") {
                $('.moveToQuestion').html("");
                $('.movePositionDiv').hide();
                $('.moveToQuestionDiv').hide();
            }
            else {
                /* Find out what tab */
                var whichTab = $('.paneTabLi.active').attr('rel');
                var questionId = window.questionId;

                if (whichTab === "copy") {
                    questionId = 0;
                }

                $.getJSON('getQuestionsForSelectedPage.do', {
                    pageId: pageId,
                    questionId: questionId,
                    ajax: true
                }, function (data) {
                    var html;
                    var len = data.length;

                    if (len > 0) {

                        for (var i = 0; i < len; i++) {
                            html += '<option value="' + data[i][0] + '">' + data[i][2] + '. ' + data[i][1] + '</option>';
                        }

                        $('.moveToQuestion').html(html);
                        $('.movePositionDiv').show();
                        $('.moveToQuestionDiv').show();
                    }
                    else {
                        $('.movePositionDiv').hide();
                        $('.moveToQuestionDiv').hide();
                    }
                });
            }

        });

        /** Function to populate the list of questions for the selected page **/
        $(document).on('change', '.logicskipToPage', function () {
            var pageId = $(this).val();
            var indexVal = $(this).attr('rel');

            if (pageId == -1) {
                $('#skipToPageId_' + indexVal).val("0");
                $('#skipToEnd_' + indexVal).val(true);
            }
            else {
                $('#skipToPageId_' + indexVal).val(pageId);
                $('#skipToEnd_' + indexVal).val(false);
            }

            if (pageId <= 0) {
                $('#logicskipToQuestion_' + indexVal).val(0);
                $('#logicskipToQuestion_' + indexVal).attr("disabled", true);
            }
            else {
                /* Find out what tab */
                var whichTab = $('.paneTabLi.active').attr('rel');
                var questionId = window.questionId;


                $.getJSON('getQuestionsForSelectedPage.do', {
                    pageId: pageId,
                    questionId: questionId,
                    ajax: true
                }, function (data) {
                    var html;
                    var len = data.length;

                    if (len > 0) {
                        html += '<option value="0">- Select Question - </option>';

                        for (var i = 0; i < len; i++) {
                            html += '<option value="' + data[i][0] + '">' + data[i][2] + '. ' + data[i][1] + '</option>';
                        }

                        $('#logicskipToQuestion_' + indexVal).html(html);
                        $('#logicskipToQuestion_' + indexVal).removeAttr("disabled");
                    }
                    else {
                        $('#logicskipQuestion_' + indexVal).html("");
                    }
                });
            }
        });

        /* Function to clear a skip logic */
        $(document).on('click', '.clearSkipLogic', function (event) {
            var indexVal = $(this).attr('rel');
            $('#logicskipToPage_' + indexVal).val(0);
            $('#logicskipToQuestion_' + indexVal).val(0);
            $('#logicskipToQuestion_' + indexVal).attr("disabled", true);
            $('#skipToEnd_' + indexVal).val(false);
            $('#skipToPageId_' + indexVal).val("0");
            $('#skipToQuestionId_' + indexVal).val("0");
            event.preventDefault();
            return false;
        });


        /** Function to populate the list of questions for the selected page **/
        $(document).on('change', '.logicskipQuestion', function () {
            var qid = $(this).val();
            var indexVal = $(this).attr('rel');

            $('#skipToQuestionId_' + indexVal).val(qid);

        });

        /** Function to move the question **/
        $(document).on('click', '#moveQuestion', function () {

            var newPage = $('#movemoveToPage').val();
            var position = $('#moveposition').val();
            var question = $('#movemoveToQuestion').val();

            if (newPage > 0) {

                if (question == null) {
                    question = 0;
                    position = "";
                }

                $.ajax({
                    url: "moveSurveyQuestion.do",
                    data: {'curQuestionId': window.questionId, 'newPage': newPage, 'position': position, 'selQuestionId': question},
                    type: "POST",
                    async: false,
                    success: function (data) {
                        getSurveyPages();
                    }
                });
            }

        });

        /** Function to copy the question **/
        $(document).on('click', '#copyQuestion', function () {

            var newPage = $('#copymoveToPage').val();
            var position = $('#copyposition').val();
            var question = $('#copymoveToQuestion').val();

            if (newPage > 0) {

                if (question == null) {
                    question = 0;
                    position = "";
                }

                $.ajax({
                    url: "copySurveyQuestion.do",
                    data: {'curQuestionId': window.questionId, 'newPage': newPage, 'position': position, 'selQuestionId': question},
                    type: "POST",
                    async: false,
                    success: function (data) {
                        getSurveyPages();
                    }
                });
            }

        });

        /** Function to submit the question **/
        $(document).on('click', '#submitQuestion', function () {

            var s = $('#s').val();
            var v = $('#v').val();

            if ($(this).attr('rel') == 'displayText') {
                var sHTML = $('#question').code();
                $('#questionVal').val(sHTML);
            }
            
            if ($(this).attr('rel') != 'displayText') {
                var helpTextHTML = $('#helpText').code();
                console.log(helpTextHTML);

                if(helpTextHTML !== "") {
                    $('#questionHelpVal').val(helpTextHTML);
                }
            }
            
            
            var questionTag = $('#questionTag').val();
            
            var errorFound = 0;
            
            if(questionTag !== "") {
                
                errorFound = checkForDuplicateQuestionTag($('#surveyId').val(),$('#qId').val(),questionTag);
                
                if(errorFound == 1) {
                    $('#questionTagDiv').addClass("has-error");
                    $('#questionTagMsg').show();
                }
            }
            
            if(errorFound == 0) {
                var formData = $("#surveyquestion").serialize();
            
                $.ajax({
                    url: "submitSurveyQuestion.do",
                    data: formData,
                    type: "POST",
                    async: false,
                    success: function (data) {
                        getSurveyPages();
                    }
                });
            }

        });

        /** Function to delete the selected question **/
        $(document).on('click', '.deleteQuestion', function () {
            var questionId = $(this).attr('rel');

            var s = $('#s').val();
            var v = $('#v').val();

            var confirmed = confirm("Are you sure want to remove this question?\n\nThis action can not be undone.");

            if (confirmed == true) {

                $.ajax({
                    url: "removeSurveyQuestion.do",
                    data: {'s': s, 'v': v, 'pageId': window.pageId, 'questionId': questionId},
                    type: "POST",
                    async: false,
                    success: function (data) {
                        getSurveyPages();
                    }
                });
            }
        });

        /** Function to hide the selected question **/
        $(document).on('click', '.hideQuestion', function () {
            var questionId = $(this).attr('rel');

            var s = $('#s').val();
            var v = $('#v').val();

            var confirmed = confirm("Are you sure want to hide this question?");

            if (confirmed == true) {

                $.ajax({
                    url: "hideSurveyQuestion.do",
                    data: {'s': s, 'v': v, 'pageId': window.pageId, 'questionId': questionId},
                    type: "POST",
                    async: false,
                    success: function (data) {
                        getSurveyPages();
                    }
                });
            }

        });

        /** Function to unhide the selected question **/
        $(document).on('click', '.unhideQuestion', function () {
            var questionId = $(this).attr('rel');

            var s = $('#s').val();
            var v = $('#v').val();

            var confirmed = confirm("Are you sure want to make this question visible?");

            if (confirmed == true) {

                $.ajax({
                    url: "unhideSurveyQuestion.do",
                    data: {'s': s, 'v': v, 'pageId': window.pageId, 'questionId': questionId},
                    type: "POST",
                    async: false,
                    success: function (data) {
                        getSurveyPages();
                    }
                });
            }

        });

        /** Function to delete a blank page **/
        $(document).on('click', '.deletePage', function () {
            var pageId = $(this).attr('rel');

            var confirmed = confirm("Are you sure want to remove this blank page?");

            if (confirmed == true) {
                $.ajax({
                    url: "deleteSurveyPage.do",
                    data: {'pageId': pageId},
                    type: "POST",
                    async: false,
                    success: function (data) {
                        getSurveyPages();
                    }
                });
            }

        });

        /****************************************/
        /**** Question Functions - End ********/
        /****************************************/


    });
});

function checkForDuplicateQuestionTag(surveyId, questionId, questionTag) {
    
    var duplicateFound = 0;
    
    $.ajax({
        url: 'checkForDuplicateQuestionTag.do',
        data: {
            'surveyId': surveyId,
            'questionId': questionId,
            'questionTag': questionTag
        },
        type: 'GET',
        async: false,
        success: function (data) {
           duplicateFound = data;
        },
        error: function (error) {
            console.log(error);
        }
    });
    
    return duplicateFound;
}

function populateTableValues(pageId, questionId, indexVal, selValue) {
    $.getJSON('getQuestionsForSelectedPage.do', {
        pageId: pageId,
        questionId: questionId,
        ajax: true
    }, function (data) {
        var html;
        var len = data.length;

        if (len > 0) {
            html += '<option value="0">- Select Question - </option>';

            for (var i = 0; i < len; i++) {
                if (selValue == data[i][0]) {
                    html += '<option value="' + data[i][0] + '" selected>' + data[i][2] + '. ' + data[i][1] + '</option>';
                }
                else {
                    html += '<option value="' + data[i][0] + '">' + data[i][2] + '. ' + data[i][1] + '</option>';
                }

            }

            $('#logicskipToQuestion_' + indexVal).html(html);
            $('#logicskipToQuestion_' + indexVal).removeAttr("disabled");
        }
        else {
            $('#logicskipQuestion_' + indexVal).html("");
        }
    });
}

function getSurveyPages() {

    var s = $('#s').val();
    var v = $('#v').val();

    $.ajax({
        url: 'getSurveyPages.do',
        data: {'s': s, 'v': v},
        type: "GET",
        success: function (data) {
            $('#surveyPages').html(data);

            window.pageId = $(".pageQuestionsPanel").first().attr('rel');
        }
    });
}





