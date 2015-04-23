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
       
        loadEntities($('a.entityactive.entity').attr('rel'));
        
        $("input:text,form").attr("autocomplete", "off");
        
        $(document).on('click', '.loadEntities', function() {
            $('.entity').removeClass("entityactive");
            $(this).addClass("entityactive");
            loadEntities($(this).attr('rel'));
        });
        
        $(document).on('click', '#createNewEntityItem', function() {
           
           $.ajax({
                url: 'entity/newEntity',
                data: {'entityId':$(this).attr('rel'), 'dspPos': $(this).attr('dspPos')},
                type: "GET",
                success: function(data) {
                    $("#serviceModal").html(data);
                }
            });
           
           
        });
        
    });
});

function loadEntities(entityId) {
    $.ajax({
        url: 'entity/getEntityList',
        data: {'entityId':entityId},
        type: "GET",
        success: function(data) {
            $('#entityList').html(data);
        }
   }); 
}




