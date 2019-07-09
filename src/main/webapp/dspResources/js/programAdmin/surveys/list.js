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

        $("input:text,form").attr("autocomplete", "off");
        
        $(document).on('click', '#viewChangeLogs', function() {
        	var i = $(this).attr('rel');
                
        	$.ajax({
                url: 'surveys/changeLog',
                data:{'surveyId':i},
                type: "GET",
                success: function(data) {
                    $("#changeLogModal").html(data);
                }
            });
        });
        
	$(document).on('click', '#createNewSurveyCategory', function() {
	    
	    $.ajax({
		url: 'surveys/getSurveyCategoryForm',
		data:{},
		type: "GET",
		success: function(data) {
		    $("#surveyCategoryModal").html(data);
		}
	    });
        });
	
	$(document).on('click', '#editSurveyCategory', function() {
	    
	    var surveyCategoryId = $(this).attr('rel');
	    
	    $.ajax({
		url: 'surveys/getSurveyCategoryForm',
		data:{
		    'surveyCategoryId': surveyCategoryId
		},
		type: "GET",
		success: function(data) {
		    $("#surveyCategoryModal").html(data);
		}
	    });
        });
	
	$(document).on('click','#submitSurveyCategoryButton',function() {
	   
	   var errorFound = 0;
            
            if($('#categoryName').val() === "") {
                errorFound == 1
		$('#categoryNameDiv').addClass("has-error");
		$('#categoryNameMsg').show();
            }
	    
            
            if(errorFound == 0) {
                var formData = $("#surveyCategoryForm").serialize();
            
               $.ajax({
                    url: "surveys/submitSurveyCategoryForm",
                    data: formData,
                    type: "POST",
                    async: false,
                    success: function (data) {
                        window.location.href ='/programAdmin/surveys?msg=catcreated';
                    }
                });
            }
	});
	
	// Function to delete the selected survey category
        $(document).on('click', '.deleteSurveyCategory', function () {
            var surveyCategoryId = $(this).attr('rel');

            var confirmed = confirm("Are you sure want to remove this survey category?");

            if (confirmed == true) {

                $.ajax({
                    url: "surveys/deleteSurveyCategory.do",
                    data: {
			'surveyCategoryId': surveyCategoryId
		    },
                    type: "POST",
                    async: false,
                    success: function (data) {
                         window.location.href ='/programAdmin/surveys?msg=catdeleted';
                    }
                });
            }

        });
	
    });
});




