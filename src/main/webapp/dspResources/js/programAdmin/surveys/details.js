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
        
       /** scroll, making sure side bar stays along with main page **/
    	$(document).ready(function () {
    		
    		/** side bar scroll **/
    	    var length = $('#left').height() - $('#sidebar').height() + $('#left').offset().top;

    	    $(window).scroll(function () {

    	        var scroll = $(this).scrollTop();
    	        var height = $('#sidebar').height() + 'px';

    	        if (scroll < $('#left').offset().top) {

    	            $('#sidebar').css({
    	                'position': 'absolute',
    	                'top': '0'
    	            });

    	        } else if (scroll > length) {

    	            $('#sidebar').css({
    	                'position': 'absolute',
    	                'bottom': '0',
    	                'top': 'auto'
    	            });

    	        } else {

    	            $('#sidebar').css({
    	                'position': 'fixed',
    	                'top': '0',
    	                'height': height
    	            });
    	        }
    	    });
    	});
    	
    	
    	 /** scroll, tracking, make sure we know what page we are on **/
        window.pageId = 1;
    	$(function(){
    	    $(window).bind('scroll', function() {
    	        $('.post').each(function() {
    	            var post = $(this);
    	            var position = post.position().top - $(window).scrollTop();
    	            
    	            if (position <= 0) {
    	                post.addClass('selected');
    	                window.pageId = post.attr("relPage");
    	            } else {
    	                post.removeClass('selected');
    	            }
    	        });        
    	    });
    	});
    	
    	
    	 /** when clicking on the title, we want to replace
         * div with form that has survey title info
         */
        //Function to open the new program association modal
        $(document).on('click', '.editSurveyTitle', function() {
        	//$('#divSurTitle' + surveyId).html("new replacement content");
        	var surveyId = $(this).attr("relS");
        	$.ajax({
                url: 'getSurveyForm.do',
                data: {'s':surveyId},
                type: "GET",
                success: function(data) {
                    $(surveyModal).html(data);
                }
           });         
        });
        
        
        /** edit page title **/
        /** when clicking on the page title we get the page Id from relP and we load page title form **/
        $(document).on('click', '.editPageTitle', function() {
        	var pageId = $(this).attr("relPage");
        	$.ajax({
                url: 'getPageTitleForm.do',
                data: {'p':pageId},
                type: "GET",
                success: function(data) {
                    $(surveyModal).html(data);
                }
           });         
        });
       
        
        /** this shows the mouse over edit button when mousing over a question **/
        /** need to figure out which question users are mouse over **/
        //example if they are on question 1
        //$('#questionDiv' + questionId)
        $( "#questionDiv1" ).mouseover(function() {
        	  $( "#editButtons1" ).show();
        });
        $( "#questionDiv1" ).mouseout(function() {
      	  $( "#editButtons1" ).hide();
        });
        $( "#questionDiv2" ).mouseover(function() {
      	  $( "#editButtons2" ).show();
      });
      $( "#questionDiv2" ).mouseout(function() {
    	  $( "#editButtons2" ).hide();
      });
      /** etc etc etc  but need to be dynamic **/
        
      /** clicking on the edit button brings up the edit question modal **/
      $(document).on('click', '.editQuestionButton', function() {
    	var questionId = $(this).attr("relQ");
      	$.ajax({
              url: 'getQuestionForm.do',
              data: {'q':questionId},
              type: "GET",
              success: function(data) {
                  $(surveyModal).html(data);
              }
         });         
      }); 
        
        
        
        /** modal js**/
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
      
      /** submit page title form **/
      $(document).on('click', '#submitPageButton', function(event) {
        	
        	$('div.form-group').removeClass("has-error");
            $('span.control-label').removeClass("has-error");
            $('span.control-label').html("");
            var pageNum = $(this).attr("relPage");
            
            /** page title cannot be blank at this point **/
            if ($("#pageTitle").val().trim().length < 1) {
            	$('#pageTitleDiv').addClass("has-error");
            	$('#pageTitleMsg').addClass("has-error");
                $('#pageTitleMsg').html('Please enter a page title.  It cannot be blank.');
            	return false;            	
            }
            var formData = $("#pageForm").serialize();

            $.ajax({
                url: "savePageTitleForm.do",
                data: formData,
                type: "POST",
                async: false,
                success: function(data) {

                    if (data.indexOf('updated') != -1) {
                    	//update all titles on the page
                    	$('#pageTitle' + pageNum).html($("#pageTitle").val());
                    	$('#surveyModal').modal('toggle');
                    } else {
                        $("#surveyModal").html(data);
                    }
                }
            });
            event.preventDefault();
            return false;

        });
      
      /** dropdown change **/
      $('.ddForPage').change(function() {
    	  var cTarget = $(this).val();
    	  window.location.hash = cTarget;
    	});
     
       
      
    });
});




