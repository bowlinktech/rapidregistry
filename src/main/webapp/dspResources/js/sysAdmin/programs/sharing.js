

require(['./main'], function() {
    require(['jquery'], function($) {

        $("input:text,form").attr("autocomplete", "off");

        //Fade out the updated/created message after being displayed.
        if ($('.alert').length > 0) {
            $('.alert').delay(2000).fadeOut(1000);
        }

        $('#saveDetails').click(function(event) {
            $('#action').val('save');
            
            var programIdList = "";
            $('input[type=checkbox]').each(function() {
                if(this.checked) {
                    programIdList += $(this).val() + ',';
                }
            })
            
            $('#programIds').val(programIdList.substring(0,programIdList.length-1));
            
            $("#patientSharing").submit();
        });

        $('#saveCloseDetails').click(function(event) {
            $('#action').val('close');

            var programIdList = "";
            $('input[type=checkbox]').each(function() {
                if(this.checked) {
                    programIdList += $(this).val() + ',';
                }
            })
            
            $('#programIds').val(programIdList.substring(0,programIdList.length-1));

            $("#patientSharing").submit();
        });
        

    });
});


