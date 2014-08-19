
require(['./main'], function() {
    require(['jquery'], function($) {


        $("input:text,form").attr("autocomplete", "off");
        
        //Fade out the updated/created message after being displayed.
        if ($('.alert').length > 0) {
            $('.alert').delay(2000).fadeOut(1000);
        }
        
        

        //Function that will display the new field module
        $(document).on('click', '#createNewField', function() {
           
            $.ajax({
                url: 'addNewDemoField',
                type: "GET",
                success: function(data) {
                    $("#fieldModal").html(data);
                }
            });
        });
        
        $(document).on('click', '.editField', function() {
            var fieldId = $(this).attr('rel');
            $.ajax({
                url: 'editDemoField',
                type: "GET",
                data: {'fieldId': fieldId},
                success: function(data) {
                    $("#fieldModal").html(data);
                }
            });
        });
        
    });
});
