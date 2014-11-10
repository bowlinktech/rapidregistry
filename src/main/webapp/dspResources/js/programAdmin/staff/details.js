

require(['./main'], function() {
    require(['jquery'], function($) {

        $("input:text,form").attr("autocomplete", "off");

        //Fade out the updated/created message after being displayed.
        if ($('.alert').length > 0) {
            $('.alert').delay(2000).fadeOut(1000);
        }
        
        getAssociatedPrograms();

        $('#saveDetails').click(function(event) {
            $('#action').val('save');

            $("#staffdetails").submit();
        });

        $('#saveCloseDetails').click(function(event) {
            $('#action').val('close');

            $("#staffdetails").submit();
        });
        
        //Function to open the new program association modal
        $(document).on('click', '#addProgram', function() {
           
           var i = getUrlParameter('i');
           var v = getUrlParameter('v');
           
           $.ajax({
                url: 'associateNewProgram.do',
                data: {'i':i, 'v': v},
                type: "GET",
                success: function(data) {
                    $('#programModal').html(data);
                }
           }); 
            
        });
        
        //Function to show modules and departments for the selected program 
        $(document).on('change', '.program', function() {
           var programId = $(this).val();
           
           //Get available modules for the selected program
           $.getJSON('/programAdmin/programs/getProgramAvailableModules.do', {
                programId: programId, ajax: true
            }, function(data) {
                var html = '';
                var len = data.length;

                for (var i = 0; i < len; i++) {
                    html += '<option value="' + data[i][0] + '">' + data[i][1] + '</option>';
                }
                $('#progamModules').html(html);
                $('#programModulesDiv').show();
            });
           
           //Get available first level org hierarchy
           
           
        });
        
        
        //Modal to view associated program modules
        $(document).on('click', '.viewModules', function() {
           var programId = $(this).attr('rel');
           
           var i = getUrlParameter('i');
           var v = getUrlParameter('v');
           
           $.ajax({
                url: 'getProgramModules.do',
                data: {'i':i, 'v': v, 'programId':programId},
                type: "GET",
                success: function(data) {
                    $('#programModulesModal').html(data);
                }
           });
        });
        
        //Function to submit the changes to an existing user program modules
        $(document).on('click', '#submitModuleButton', function(event) {
            
            var formData = $("#moduleForm").serialize();

            $.ajax({
                url: 'saveProgramUserModules.do',
                data: formData,
                type: "POST",
                async: false,
                success: function(data) {
                    var url = $(data).find('#encryptedURL').val();
                    window.location.href = "details"+url+"&msg=moduleAdded";
                }
            });
            event.preventDefault();
            return false;

        });
        
        

    });
});


function getAssociatedPrograms() {
   var i = getUrlParameter('i');
   var v = getUrlParameter('v');
    
   $.ajax({
        url: 'getAssociatedPrograms.do',
        data: {'i':i, 'v': v},
        type: "GET",
        success: function(data) {
            $('#associatedPrograms').html(data);
        }
    });
    
    
}

function getUrlParameter(sParam)
{
    var sPageURL = window.location.search.substring(1);
    var sURLVariables = sPageURL.split('&');
    for (var i = 0; i < sURLVariables.length; i++) 
    {
        var sParameterName = sURLVariables[i].split('=');
        if (sParameterName[0] == sParam) 
        {
            return sParameterName[1];
        }
    }
}   