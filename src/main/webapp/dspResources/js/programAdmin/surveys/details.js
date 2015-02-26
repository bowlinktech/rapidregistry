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
        
        /** scroll, tracking, make sure we know what page we are on **/
        window.pageId = 1;
        window.questionId = 0;
        
        /** scroll, making sure side bar stays along with main page **/
    	$(document).ready(function () {
            
           getSurveyPages();
           
           /** Have the left menu scroll with the page **/
           var el = $('.secondary');
           var elpos_original = el.offset().top;
           $(window).scroll(function(){
                var elpos = el.offset().top;
                var windowpos = $(window).scrollTop();
                var finaldestination = windowpos;
                if(windowpos<elpos_original) {
                    finaldestination = elpos_original;
                    el.stop().css({'top':10});
                } else if (windowpos>elpos_original) {
                    el.stop().animate({'top':finaldestination-elpos_original+10},500);
                }
                
                var top = false;
                $(".pageQuestionsPanel").each( function() {
                    var offset = $(this).offset();
                   
                    if(((offset.top+100) > windowpos) && top == false) {
                        window.pageId =  $(this).attr('rel');
                        top = true;
                        return false;
                    }
                });
                
            });
            
            /** dropdown change **/
           $(document).on('change', '.ddForPage', function() {
             var cTarget = ($(this).val()*1)-1;

             if(cTarget == 0) {
                 $('html, body').animate({
                       scrollTop: $("#surveyPages").offset().top
                 }, 1000); 
             }
             else {
                 $('html, body').animate({
                       scrollTop: $("#newPageDiv_"+cTarget).offset().top
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
                   data: {'s':s, 'v': v},
                   type: "GET",
                   success: function(data) {
                       $(surveyModal).html(data);
                   }
                });         
           });


           /** submit survey title form **/
           $(document).on('click', '#submitSurveyButton', function(event) {

               $('div.form-group').removeClass("has-error");
               $('span.control-label').removeClass("has-error");
               $('span.control-label').html("");

               var formData = $("#surveyForm").serialize();

               $.ajax({
                   url: "saveSurveyForm.do",
                   data: formData,
                   type: "POST",
                   async: false,
                   success: function(data) {

                       if (data.indexOf('updated') != -1) {
                           //update all titles on the page
                           if ($('#editSurveyTitleActionBar').text() != $("#title").val()) {
                               $(".editSurveyTitle").each( function(index, element) {
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
           $(document).on('click', '.editPageTitle', function() {
               var pageId = $(this).attr("rel");

               //Hide the heading
               $('#pageTitleHeading_'+pageId).hide();

               //Show the input field
               $('#pageTitleEdit_'+pageId).show();

           });


           /** submit page title form **/
           $(document).on('click', '.submitPageTitleChanges', function(event) {
               var pageId = $(this).attr("rel");

               $('div.form-group').removeClass("has-error");
               $('span.control-label').removeClass("has-error");
               $('span.control-label').html("");


               /** page title cannot be blank at this point **/
               if ($("#pageTitle_"+pageId).val().trim().length < 1) {
                   $('#pageTitleDiv_'+pageId).addClass("has-error");
                   return false;            	
               }

               $.ajax({
                   url: "savePageTitle.do",
                   data: {'pageId': pageId, 'pageTitle': $("#pageTitle_"+pageId).val().trim()},
                   type: "POST",
                   async: false,
                   success: function(data) {

                       //Show the heading
                       $('#pageTitleSpan_'+pageId).html(data);
                       $('#pageTitleHeading_'+pageId).show();

                       //Hide the input field
                       $('#pageTitleEdit_'+pageId).hide();
                   }
               });
           });

           /** New Page Button **/
           $(document).on('click', '.newPage', function() {

               var lastPageId = $(this).attr('rel');

               var s = $('#s').val();
               var v = $('#v').val();

               $.ajax({
                  url: "addNewSurveyPage.do",
                  data: {'s':s, 'v': v, 'lastPageId': lastPageId},
                  type: "POST",
                  async: false,
                  success: function(data) {
                       getSurveyPages();
                  }
               }); 


           });

            /** Hover over answer type in the menu **/
           $(document).on('mouseover', '.QuestionTypeDiv', function() {
              var answertypeid = $(this).attr('rel');
              
              $('.addQuestionTypeBtn').hide();
              $('#answertype_'+answertypeid).show();
           });
           
           /** Click to add a new question type **/
           $(document).on('click', '.addQuestionType', function() {
               
               var questionType = $(this).attr('rel');
               
               var s = $('#s').val();
               var v = $('#v').val();
            
               $.ajax({
                  url: "addNewPageQuestion.do",
                  data: {'s':s, 'v': v, 'pageId': window.pageId, 'questionType': questionType},
                  type: "GET",
                  async: false,
                  success: function(data) {
                      $('#newQuestionDiv_'+window.pageId).html(data);
                      $('#newQuestionDiv_'+window.pageId).show();
                      $('#emptyPageDiv_'+window.pageId).hide();
                  }
               }); 
           });
            
    	});
        
        /** Hover over a question **/
        $(document).on('mouseover', '.questionDiv', function() {
            var qId = $(this).attr('rel');
            $('.questionDiv').removeClass("hoverQuestion");
            $(this).addClass("hoverQuestion");
            $('#questionBtns'+qId).show();
        });
        
        $(document).on('mouseleave', '.questionDiv', function() {
            var qId = $(this).attr('rel');
            $('#questionBtns'+qId).hide();
            $(this).removeClass("hoverQuestion");
        });
        
        
        /****************************************/
        /**** Question Functions - Start ********/
        /****************************************/
        
        /** Click one of the question buttons **/
        $(document).on('click', '.questionButton', function() {

            var questionId = $(this).attr('rel');
            var pane = $(this).attr('pane');
            
            window.questionId = questionId;

            var s = $('#s').val();
            var v = $('#v').val();

            $.ajax({
               url: "editPageQuestion.do",
               data: {'s':s, 'v': v, 'pageId': window.pageId, 'questionId': questionId},
               type: "GET",
               async: false,
               success: function(data) {
                   
                   if(pane === "movePane") {
                       data = data.replace("submitQuestion", "moveQuestion");
                   }
                   else if(pane === "copyPane") {
                       data = data.replace("submitQuestion", "copyQuestion");
                   }
                   
                   $('#editQuestionDiv_'+questionId).html(data);
                   $('#editQuestionDiv_'+questionId).show();
                   $('#questionDiv'+questionId).hide();
                   
                   $('.tab-pane-question').removeClass("active");
                   $('.tab-pane-question').removeClass("in");
                   $('.nav-tabs-question li').removeClass("active");
                   
                   $('.'+pane+'Tab').addClass("active");
                   $('.'+pane).addClass("active");
                   $('.'+pane).addClass("in");
               }
            }); 
        });
        
        /** If the moveTab is clicked chagne the button value **/
        $(document).on('click', '.paneTab', function() {
           
           var whichPane = $(this).attr('href');
           
           if(whichPane === "#move") {
               $('.moveToQuestion').html("");
               $('.movePositionDiv').hide();
               $('.moveToQuestionDiv').hide();
               $('.saveQuestionBtn').attr("id", "moveQuestion");
           }
           else if(whichPane === "#copy") {
               $('.moveToQuestion').html("");
               $('.movePositionDiv').hide();
               $('.moveToQuestionDiv').hide();
               $('.saveQuestionBtn').attr("id", "copyQuestion");
           }
           else {
               $('.saveQuestionBtn').attr("id", "submitQuestion");
           }
            
        });
        
        /** Cancel add/edit question type **/
        $(document).on('click', '#cancelQuestion', function() {
            
            var qNum = $(this).attr('rel');
            var qId = $(this).attr('rel2');
            
            $('#newQuestionDiv_'+window.pageId).html("");
            $('#newQuestionDiv_'+window.pageId).hide();
            $('#editQuestionDiv_'+qId).html("");
            $('#editQuestionDiv_'+qId).hide();
            
            if(qId > 0) {
                $('#questionDiv'+qId).show();
            }
            else if($('#pagePanel_'+window.pageId).children().children().length == 2) {
                $('#emptyPageDiv_'+window.pageId).show();
            }
            
            
        });
        
        /** Function to show the validation div for the edit/add question **/
        $(document).on('change', '#required', function() {
           
           if($(this).is(':checked')) {
               $('#requiredResponseDiv').show();
           }
           else {
               $('#requiredResponseDiv').hide();
           }
            
        });
        
        /** Function to show the save to field div for the edit/add question **/
        $(document).on('change', '#saveTo', function() {
           
           if($(this).is(':checked')) {
               $('#saveToDiv').show();
           }
           else {
               $('#saveToDiv').hide();
           }
            
        });
        
    	/** Function to show the validation div for the edit/add question **/
        $(document).on('change', '#validateField', function() {
           
           if($(this).is(':checked')) {
               $('#validationDiv').show();
           }
           else {
               $('#validationDiv').hide();
           }
            
        });
        
        /** Funtion to show the auto populate table list **/
        $(document).on('change', '#populate', function() {
            
           if($(this).is(':checked')) {
               $('#manual').attr('checked', false);
               $('#autoPopulateDiv').show();
           }
           else {
               $('#autoPopulateDiv').hide();
           }
            
        });
        
        /** Function to show the multiple choice column display div for the edit/add question **/
        $(document).on('change', '#choiceLayout', function() {
           
           if($(this).is(':checked')) {
               $('#choiceLayoutDiv').show();
           }
           else {
               $('#choiceLayoutDiv').hide();
           }
            
        });
        
        /** Function to populate the list of questions for the selected page **/
        $(document).on('change', '.moveToPage', function() {
            var pageId = $(this).val();
            
            if(pageId == "") {
                $('.moveToQuestion').html("");
                $('.movePositionDiv').hide();
                $('.moveToQuestionDiv').hide();
            }
            else {
                /* Find out what tab */
                var whichTab = $('.paneTabLi.active').attr('rel');
                var questionId = window.questionId;
                
                if(whichTab === "copy") {
                    questionId = 0;
                }
                
                $.getJSON('getQuestionsForSelectedPage.do', {
                    pageId: pageId, 
                    questionId: questionId,
                    ajax: true
                }, function(data) {
                    var html;
                    var len = data.length;
                    
                    if(len > 0) {

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
            
        })
        
        /** Function to move the question **/
        $(document).on('click', '#moveQuestion', function() {
           
            var newPage = $('#movemoveToPage').val();
            var position = $('#moveposition').val();
            var question = $('#movemoveToQuestion').val();
           
            if(newPage > 0) {
                
                if(question == null) {
                    question = 0;
                    position = "";
                }
                 
                $.ajax({
                    url: "moveSurveyQuestion.do",
                    data: {'curQuestionId': window.questionId, 'newPage': newPage, 'position': position, 'selQuestionId': question},
                    type: "POST",
                    async: false,
                    success: function(data) {
                        getSurveyPages();
                    }
                });
            }
            
        });
        
        /** Function to copy the question **/
        $(document).on('click', '#copyQuestion', function() {
           
            var newPage = $('#copymoveToPage').val();
            var position = $('#copyposition').val();
            var question = $('#copymoveToQuestion').val();
            
            if(newPage > 0) {
                
                if(question == null) {
                    question = 0;
                    position = "";
                }
                 
                $.ajax({
                    url: "copySurveyQuestion.do",
                    data: {'curQuestionId': window.questionId, 'newPage': newPage, 'position': position, 'selQuestionId': question},
                    type: "POST",
                    async: false,
                    success: function(data) {
                        getSurveyPages();
                    }
                });
            }
            
        });
        
        /** Function to submit the question **/
        $(document).on('click', '#submitQuestion', function() {
            
            var s = $('#s').val();
            var v = $('#v').val();
             
            var formData = $("#surveyquestion").serialize();

            $.ajax({
                url: "submitSurveyQuestion.do",
                data: formData,
                type: "POST",
                async: false,
                success: function(data) {
                    getSurveyPages();
                }
            });
             
        });
        
        /** Function to delete the selected field **/
        $(document).on('click', '.deleteQuestion', function() {
            var questionId = $(this).attr('rel');
            
            var s = $('#s').val();
            var v = $('#v').val();
            
            var confirmed = confirm("Are you sure want to remove this question?");
            
            if(confirmed == true) {
            
                $.ajax({
                    url: "removeSurveyQuestion.do",
                    data: {'s':s, 'v': v, 'pageId': window.pageId, 'questionId': questionId},
                    type: "POST",
                    async: false,
                    success: function(data) {
                        getSurveyPages();
                    }
                });
            }
            
        });
        
        /****************************************/
        /**** Question Functions - Start ********/
        /****************************************/
     
      
    });
});

function getSurveyPages() {
    
    var s = $('#s').val();
    var v = $('#v').val();

    $.ajax({
       url: 'getSurveyPages.do',
       data: {'s':s, 'v': v},
       type: "GET",
       success: function(data) {
           $('#surveyPages').html(data);
           
           window.pageId = $(".pageQuestionsPanel").first().attr('rel');
       }
    });  
}




