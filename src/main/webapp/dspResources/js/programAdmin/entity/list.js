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
        
        var sPageURL = window.location.search.substring(1);
        var sURLVariables = sPageURL.split('&');
        var entityId = 0;
        for (var i = 0; i < sURLVariables.length; i++) 
        {
            var sParameterName = sURLVariables[i].split('=');
            if (sParameterName[0] == "i") 
            {
                entityId = sParameterName[1];
            }
        }
        
        if(entityId == 0) {
           loadEntities($('a.entityactive.entity').attr('rel')); 
        }
        else {
            loadEntities(entityId);
        }
       
        $("input:text,form").attr("autocomplete", "off");
        
        $(document).on('click', '.loadEntities', function() {
            $('.entity').removeClass("entityactive");
            $(this).addClass("entityactive");
            loadEntities($(this).attr('rel'));
        });
        
        $(document).on('click', '.entityItemDetails', function() {
           $.ajax({
                url: 'entity/entityItemDetails',
                data: {'entityId':$(this).attr('rel'), 'itemId': $(this).attr('itemId'), 'dspPos': $(this).attr('dspPos')},
                type: "GET",
                success: function(data) {
                    $("#entityModal").html(data);
                }
            });
        });
        
        //Button to submit the entity item changes
        $(document).on('click','#submitButton', function(event) {
            var formData = $("#hierarchyItemdetailsform").serialize();
            
            var entityId = $('#entityId').val();

            $.ajax({
                url: 'entity/saveEntityItem',
                data: formData,
                type: "POST",
                async: false,
                success: function(data) {
                    
                    loadEntities(entityId);
                    $("#entityModal").modal('hide');

                    if (data.indexOf('itemUpdated') != -1) {
                        $('.itemSuccess').html('<strong>Success!</strong> The entity item has been successfully updated!');
                    }
                    else if (data.indexOf('itemCreated') != -1) {
                        $('.itemSuccess').html('<strong>Success!</strong> The entity item has been successfully created!');
                    }
                    
                    $('.itemSuccess').show();
                    $('.alert').delay(2000).fadeOut(1000);
                }
            });
            event.preventDefault();
            return false;
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



