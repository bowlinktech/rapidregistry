/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

require(['./main'], function () {
    require(['jquery','duallistbox'], function($) {
        
        $('select[name="activitycodelistbox[]"]').bootstrapDualListbox({
           nonSelectedListLabel: 'Available Activity Codes',
           selectedListLabel: 'Associated Activity Codes'
        });
        
        $(document).on('change', '.changeCategory', function() {
           var selCategory = $(this).val();
           var programName = $(this).attr('rel');
           
           window.location.href='/sysAdmin/programs/'+programName+'/activity-codes?c='+selCategory;
           
        });
        
        //The function that will be called when the "Save" button
        //is clicked
        $('#saveDetails').click(function() {
            $('#action').val('save');
             
            var selCodes = $('select[name="activitycodelistbox[]"]').val();
            
            $('#selCodes').val(selCodes);
            
            $('#activityCodeForm').submit();
            
        });

        //The function that will be called when the "Next Step" button
        //is clicked
        $('#saveCloseDetails').click(function() {
            $('#action').val('close');
            
            var selCodes = $('select[name="activitycodelistbox[]"]').val();
            
            $('#selCodes').val(selCodes);
            
            $('#activityCodeForm').submit();
            
        });
        
    });
});

