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
            
            $('#nameDiv').removeClass("has-error");
            $('#nameLabelDiv').removeClass("has-error");
            $('#nameLabelDiv').html("");
            $('#displayDiv').removeClass("has-error");
            $('#displayIdDiv').removeClass("has-error");
            $('#displayIdDiv').html("");
            
            $.ajax({
                url: 'entity/saveEntityItem',
                data: formData,
                type: "POST",
                async: false,
                success: function(data) {
                    if (data.indexOf('itemUpdated') != -1) {
                        $('.itemSuccess').html('<strong>Success!</strong> The entity item has been successfully updated!');
                        loadEntities(entityId);
                        //only hide if no errors
                        $("#entityModal").modal('hide');
                    }
                    else if (data.indexOf('itemCreated') != -1) {
                        $('.itemSuccess').html('<strong>Success!</strong> The entity item has been successfully created!');
                        loadEntities(entityId);
                        //only hide if no errors
                        $("#entityModal").modal('hide');
                    } else if (data.indexOf('itemHasError') != -1) {
                        $('#nameDiv').addClass("has-error");
                        $('#nameLabelDiv').addClass("has-error");
                        $('#nameLabelDiv').html("Name cannot be blank");
                        //$('.itemSuccess').html('<strong>Errors!</strong> The entity name must not be empty.'); 
                    }
                     else if (data.indexOf('displayIdHasError') != -1) {
                        $('#displayDiv').addClass("has-error");
                        $('#displayIdDiv').addClass("has-error");
                        $('#displayIdDiv').html("Display Id cannot be blank");
                        //$('.itemSuccess').html('<strong>Errors!</strong> The entity name must not be empty.'); 
                    }  else if (data.indexOf('nameError') != -1) {
                        $('#nameDiv').addClass("has-error");
                        $('#nameLabelDiv').addClass("has-error");
                        $('#nameLabelDiv').html("Another entity with the same name exists.");
                    }  else if (data.indexOf('folderError') != -1) {
                        $('#nameDiv').addClass("has-error");
                        $('#nameLabelDiv').addClass("has-error");
                        $('#nameLabelDiv').html("Another folder with the same name exists, please contact admin for assistance.");
                    }
                    $('.itemSuccess').show();
                    $('.alert').delay(2000).fadeOut(1000);
                }
            });
            event.preventDefault();
            return false;
        });
        
        // Delete Entity 
        $(document).on('click', '.deleteEntity', function() {
            
            var entityId = $(this).attr('rel');
            var dspPos = $(this).attr('dspPos');
            var itemId = $(this).attr('itemId');
            
            $.ajax({
                url: 'entity/deleteEntity',
                data: {'entityId':entityId, 'dspPos': dspPos, 'itemId': itemId},
                type: "POST",
                success: function(data) {
                    if(data == "") {
                        loadEntities(entityId);
                    }
                    else {
                        $('.cantDelete').show();
                        $('.cantDelete').html("<p><strong>The selected entity can't be deleted because of the following:</strong></p>" + data);
                        $('.cantDelete').delay(2000).fadeOut(1000);
                    }
                }
           });
            
            
        })
        
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




