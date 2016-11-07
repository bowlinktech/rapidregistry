
require(['./main'], function () {
    require(['jquery'], function($) {

	    $(document).on('change', '.changeRepType', function() {
           var selRepType = $(this).val();
           var programName = $(this).attr('rel');
           
           window.location.href='/sysAdmin/programs/'+programName+'/aggregated-reports?rtId='+selRepType;
           
        });
        
    });

});
