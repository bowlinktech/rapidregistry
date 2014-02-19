/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$.ajaxSetup({
    cache: false
});


jQuery(document).ready(function($) {
    
    //This function will launch the status detail overlay with the selected
    //status
    $(document).on('click', '.viewStatus', function() {
        $.ajax({
            url: '/Health-e-Web/viewStatus' + $(this).attr('rel'),
            type: "GET",
            success: function(data) {
                $("#statusModal").html(data);
            }
        });
    });
    
    //This function will update the status of the batch when the download link is clicked.
    $(document).on('click', '.downloadFile', function() {
        var batchId = $(this).attr('rel');
        var fileName = $(this).attr('rel1');
        var orgId = $(this).attr('rel2');
        
         $.ajax({
            url: '/Health-e-Connect/downloadBatch.do',
            type: 'POST',
            data: {'batchId': batchId},
            success: function(data) {
                
               //Need to rewrite the status of the clicked batch
               var statusHTML = '<a href="#statusModal" data-toggle="modal" class="btn btn-link viewStatus" rel="22" title="View this Status">SDL&nbsp;<span class="badge badge-help" data-placement="top" title="" data-original-title="">?</span></a>';
               $('#statusDiv'+batchId).html(statusHTML);
               
               var d = new Date();
               var month = d.getMonth()+1;
               var day = d.getDate();
               
               var hourString;
               var minString;
               var amPm = "AM";
               if ( d.getHours() > 11 ) {
                    amPm = "PM"
                    if(d.getHours() == 12) {
                        hourString = (d.getHours() - 0);
                    }
                    else {
                        hourString = (d.getHours() - 12);
                    }
                    
                } else {
                    amPm = "AM"
                    hourString = d.getHours();
                }
                
                
                if(d.getMinutes() < 10) {
                    minString = "0"+d.getMinutes();
                }
                else {
                    minString = d.getMinutes();
                }
                
               $('#lastDownloadDiv'+batchId).html(month + '/' + (day<10 ? '0' : '') + day + '/' + d.getFullYear() + '<br/>' + hourString + ':' + minString + ':' + d.getSeconds() + ' ' + amPm);
               
               window.location.href = "/downloadFile.do?filename="+fileName+"&foldername=output files&orgId="+orgId;
                
            }
        });
    });
    
    $('#searchBatchesBtn').click(function() { 
        $('#searchForm').submit();
    });
    
    $(document).on('click','.changePage', function() {
       $('#page').val($(this).attr('rel'));
       $('#searchForm').submit();
    });
    
});


function searchByDateRange() {
   var fromDate = $('.daterange span').attr('rel');
   var toDate = $('.daterange span').attr('rel2');
    
   $('#fromDate').val(fromDate);
   $('#toDate').val(toDate);
   
   $('#searchForm').submit();

}


