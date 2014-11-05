

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
        
        
        //Open up the modal to display the patient entry form
        $(document).on('click', '#createNewEntryMethod', function() {
            $.ajax({
                url: '../entryMethodForm',
                data: {'id':0},
                type: "GET",
                success: function(data) {
                    $("#entryMethodModal").html(data);
                }
            });
        });
        
        //Function to submit the changes to an existing user or 
        //submit the new user fields from the modal window.
        $(document).on('click', '#submitEntryButton', function(event) {
            
            var formData = $("#entryMethod").serialize();
            
            if($('#dspPos').val() == 0) {
                $('#dspPosDiv').addClass("has-error");
                $('#dspPosMsg').addClass("has-error");
                $('#dspPosMsg').html('A display position must be selected!');
            }
            else {
            
                $.ajax({
                   url: '../saveEntryMethod',
                   data: formData,
                   type: "POST",
                   async: false,
                   success: function(data) {

                       if (data.indexOf('entrySaved') != -1) {
                          window.location.href = "/sysAdmin/programs/"+$('#progamNameURL').val()+"/details?msg=entrysaved";
                       }
                       else {
                           $("#entryMethodModal").html(data);
                       }
                   }
               });
               
               event.preventDefault();
               return false;
           }
           
        });
        
        //Open up the modal to display the selected patient entry method form
        $(document).on('click', '.editEntryMethod', function() {
            
            $.ajax({
                url: '../entryMethodForm',
                data: {'id':$(this).attr('rel')},
                type: "GET",
                success: function(data) {
                    $("#entryMethodModal").html(data);
                }
            });
        });
        
        $(document).on('click', '.deleteEntryMethod', function(event) {
           
            if(confirm("Are you sure you want to remove this entry method?")) {
                $.ajax({
                    url: '../deleteEntryMethod',
                    data: {'id':$(this).attr('rel')},
                    type: "POST",
                    success: function(data) {
                        window.location.href = "/sysAdmin/programs/"+data+"/details?msg=entrydeleted";
                    }
                });
            }
            
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