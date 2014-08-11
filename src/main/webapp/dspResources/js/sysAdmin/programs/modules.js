

require(['./main'], function() {
    require(['jquery'], function($) {

        $("input:text,form").attr("autocomplete", "off");

        //Fade out the updated/created message after being displayed.
        if ($('.alert').length > 0) {
            $('.alert').delay(2000).fadeOut(1000);
        }

        $('#saveDetails').click(function(event) {
            $('#action').val('save');
            
            var moduleIdList = "";
            $('input[type=checkbox]').each(function() {
                if(this.checked) {
                    moduleIdList += $(this).val() + ',';
                }
            })
            
            $('#moduleIds').val(moduleIdList.substring(0,moduleIdList.length-1));
            
            $("#programModules").submit();
        });

        $('#saveCloseDetails').click(function(event) {
            $('#action').val('close');

            var moduleIdList = "";
            $('input[type=checkbox]').each(function() {
                if(this.checked) {
                    moduleIdList += $(this).val() + ',';
                }
            })
            
            $('#moduleIds').val(moduleIdList.substring(0,moduleIdList.length-1));

            $("#programModules").submit();
        });
        

    });
});


