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
        	var surveyId = $(this).attr('rel');
        	
        	$.ajax({
                url: 'surveys/changeLog',
                data:{'s':surveyId},
                type: "GET",
                success: function(data) {
                    $("#changeLogModal").html(data);
                }
            });
        });
        
        
        
    });
});




