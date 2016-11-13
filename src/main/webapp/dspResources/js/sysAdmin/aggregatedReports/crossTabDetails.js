/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


require(['./main'], function () {
	require(['jquery', 'multiselect'], function($) {
		
    	$('#form-field-select-4').multiselect({
            inheritClass: true,
            buttonWidth: '400px', 
            numberDisplayed: 0,
            includeSelectAllOption: true
        });
    
    	$(document).on('click', '#actionBarReportTitle', function() {
            $('#reportTitleEdit').show();
         });
         
    	/** submit page title form **/
        $(document).on('click', '.submitReportTitleChange', function (event) {
            var reportId = $(this).attr("rel");

            $('div.form-group').removeClass("has-error");
            $('span.control-label').removeClass("has-error");
            $('span.control-label').html("");


            /** page title cannot be blank at this point **/
            if ($('#reportTitle').val().trim().length < 1) {
                $('#reportTitleEditDiv').addClass("has-error");
                return false;
            }

            $.ajax({
                url: "savePageTitle.do",
                data: {'reportId': reportId, 'reportTitle': $('#reportTitle').val().trim()},
                type: "POST",
                async: false,
                success: function (data) {

                    //Show the heading
                    $('#actionBarReportTitle').html(data);
                    $('#reportTitle').html(data);

                    //Hide the input field
                    $('#reportTitleEdit').hide();
                }
            });
        });
    	
        /** modal for table details with form **/
        //This function will launch the new program service category overlay with a blank screen
        $(document).on('click', '.crossTabTitle', function() {
            $.ajax({
                url: 'getCrossTabTable.do',
                data: {'crossTabId':$(this).attr('rel')},
                type: "GET",
                success: function(data) {
                    $("#reportModal").html(data);
                }
            });
        });
        
        /** modal for table details with form **/
        //This function will launch the new program service category overlay with a blank screen
        $(document).on('click', '.newCrossTabReportForm', function() {
        	$.ajax({
                url: 'newCrossTabTableForm.do',
                data: {'reportId':$(this).attr('rel')},
                type: "GET",
                success: function(data) {
                    $("#reportModal").html(data);
                }
            });
        });
        
        
        
        $(document).on('click', '#submitButton', function(event) {
        	var pageId = $(this).attr('rel1');
        	var reportId = $('#reportId').val();
        	
        	var formData = $("#details").serialize();
            $.ajax({
                url: 'updateCrossTabReport',
                data: formData,
                type: "POST",
                async: false,
                success: function(data) {
                	
                	if (data.indexOf('detailsFailed') != -1) {
                    	$("#reportModal").html(data);
                    } else if  (data.indexOf('addedNewCrossTabItem') != -1)  {
                    	//modify later to add a div
                    	//for now, refresh page
                    	window.location.href = "reportDetails?r="+ reportId +"&msg=ctAdded";
                    	
                    } else {	
                    	$('#reportModal').modal('toggle');
                    	//replace the table data with data
                    	$('#tableDiv' + pageId).html(data);                    	
                    }
                }
            });
            event.preventDefault();
            return false;
        });
        
        /** delete cross tab table **/
      //Function to remove the associated category
        $(document).on('click', '.removeCrossTabTable', function(event) {
            var crossTabId = $(this).attr('rel');
            var reportId = $(this).attr('rel1');
            
            
            if(confirm("Are you sure you want to remove this cross tab table?")) {
            
                $.ajax({
                    url: 'removeCrossTable.do',
                    data: {'crossTabId':crossTabId},
                    type: "POST",
                    success: function(data) {
                       window.location.href = "reportDetails?r="+ reportId +"&msg=ctRemoved";
                    }
               }); 
           }
        })
        	
        
        
        
    });
});

