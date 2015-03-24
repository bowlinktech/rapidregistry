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
        
        getCategories();
        
         $('#saveDetails').click(function(event) {
            $('#action').val('save');

            $("#codedetails").submit();
        });

        $('#saveCloseDetails').click(function(event) {
            $('#action').val('close');

            $("#codedetails").submit();
        });
        
        //Open the new association modal
        $('#associateNewCategory').click(function() {
           
            $.ajax({
                url: '/sysAdmin/activity-codes/getAvailableCategories',
                type: "GET",
                data: {'codeId': $(this).attr('rel')},
                success: function(data) {
                    $("#newAssocModal").html(data);
                }
            });
        });
        
        //Button to submit the selected categories
        $(document).on('click','#submitItemAssoc', function(event) {
           
            var codeId = $('#codeId').val();
            
           
            $.ajax({
                url: '/sysAdmin/activity-codes/saveCategoryAssocItem',
                data: {'codeId': codeId, 'categories': "'" + $('#selItems').val()  + "'"},
                type: "POST",
                async: false,
                success: function(data) {
                    
                    getCategories();
                    $("#newAssocModal").modal('hide');

                    $('.assocSuccess').html('<strong>Success!</strong> The selected category has been associated to the activity code!');
                    $('.assocSuccess').show();
                    $('.alert').delay(2000).fadeOut(1000);
                }
            });
            event.preventDefault();
            return false;
        });
        
        //Button to remove the selected category
        $(document).on('click', '.deleteCategory', function() {
           var id = $(this).attr('rel');
           
           $.ajax({
                url: '/sysAdmin/activity-codes/removeCategoryAssoc',
                data: {'id': id},
                type: "POST",
                async: false,
                success: function(data) {
                    
                    getCategories();
                    $('.assocSuccess').html('<strong>Success!</strong> The selected category has been removed from the activity code!');
                    $('.assocSuccess').show();
                    $('.alert').delay(2000).fadeOut(1000);
                }
            });
            
            
        });
        
        
    });
});

function getCategories() {
    
    var codeId = $('#codeId').val();
    
    $.ajax({
        url: '/sysAdmin/activity-codes/getAssocCategories',
        data: {'codeId': codeId},
        type: "POST",
        async: false,
        success: function(data) {
            $("#activityCodeCategories").html(data);
        }
    });
}