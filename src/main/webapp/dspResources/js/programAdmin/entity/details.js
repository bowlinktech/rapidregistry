

require(['./main'], function() {
    require(['jquery'], function($) {

        $("input:text,form").attr("autocomplete", "off");

        //Fade out the updated/created message after being displayed.
        if ($('.alert').length > 0) {
            $('.alert').delay(2000).fadeOut(1000);
        }
        
        $('#saveDetails').click(function(event) {
            $('#action').val('save');
            $("#hierarchyItemdetailsform").submit();
        });

        $('#saveCloseDetails').click(function(event) {
            $('#action').val('close');
            $("#hierarchyItemdetailsform").submit();
        });
        
        //Associate to the selected enttiy
        $(document).on('click', '.entitySelect', function() {
            var isChecked = 0;
            
            if($(this).prop( "checked" ) ) {
                isChecked = 1;
            }
            $.ajax({
                url: 'associateEntity',
                data: {'isChecked': isChecked, 'entityId':$(this).attr('entityId'), 'itemId': $('#id').val()},
                type: "POST",
                success: function(data) {
                   
                }
           });
            
        });
       
    });
});



