

require(['./main'], function() {
    require(['jquery'], function($) {

        $("input:text,form").attr("autocomplete", "off");

        //Fade out the updated/created message after being displayed.
        if ($('.alert').length > 0) {
            $('.alert').delay(2000).fadeOut(1000);
        }

        $('#saveDetails').click(function(event) {
            $('#action').val('save');
            
            var reportIdList = "";
            $('input[type=checkbox]').each(function() {
                if(this.checked) {
                    reportIdList += $(this).val() + ',';
                }
            })
            
            $('#reportIds').val(reportIdList.substring(0,reportIdList.length-1));
            
            $("#programReports").submit();
        });

        $('#saveCloseDetails').click(function(event) {
            $('#action').val('close');

            var reportIdList = "";
            $('input[type=checkbox]').each(function() {
                if(this.checked) {
                    reportIdList += $(this).val() + ',';
                }
            })
            
            $('#reportIds').val(reportIdList.substring(0,reportIdList.length-1));
            
            $("#programReports").submit();
        });
        

    });
});


