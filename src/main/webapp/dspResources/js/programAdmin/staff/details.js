

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
            if (checkForm()) {
            	$("#staffdetails").submit();
            }    
        });

        $('#saveCloseDetails').click(function(event) {
            $('#action').val('close');
            /** need to validate drop down & password lengths **/
            if (checkForm()) {
            	$("#staffdetails").submit();
            }
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
        
        //Function to submit the selected program to the user
        $(document).on('click', '#submitProgramButton', function(event) {
            
            $('.hierarchyDropBox').each(function() {
                $('#hierarchyValues').val($('#hierarchyValues').val() + "," + $(this).attr('rel') + "-" + $(this).val());
            });
            
            var formData = $("#newProgramForm").serialize();
            
            $.ajax({
                url: 'saveProgramUser.do',
                data: formData,
                type: "POST",
                async: false,
                success: function(data) {
                    var url = $(data).find('#encryptedURL').val();
                    window.location.href = "details"+url+"&msg=programAdded";
                }
            });
            
            
            event.preventDefault();
            return false;

        });
        
        //Function to remove the association of a program and user
        $(document).on('click', '.removeProgram', function(event) {
            var i = getUrlParameter('i');
            var v = getUrlParameter('v');
            
            if(confirm("Are you sure you want to remove this program association?")) {
            
                $.ajax({
                    url: 'removeUserProgram.do',
                    data: {'i':i, 'v': v, 'programId': $(this).attr('rel')},
                    type: "POST",
                    success: function(data) {
                       window.location.href = "details?i="+i+"&v="+v+"&msg=programRemoved";
                    }
               }); 
           }
        })
        
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
            
            $.ajax({
                url: '/programAdmin/programs/getProgramAvailableHierarchy.do',
                data: {'programId':programId},
                type: "GET",
                success: function(data) {
                    $('.orgHierarchyDiv').html(data);
                    populateHierarchy(programId, 1, 0, $(data).find(".hierarchyDropBox").first().attr('rel'));
                    $('.orgHierarchyDiv').show();
                }
            }); 
           
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
            
            var ProgramModules = [];
            
            $('.programModules').each(function() {
                if($(this).is(":checked")) {
                     ProgramModules.push($(this).val());
                }
            });
            var s = ProgramModules.join(',');
            
            $('#selProgramModules').val(s);
            
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
        
        //Function to display the selected hierarchy for the selected program and user
        $(document).on('click', '.viewEntities', function() {
            var programId = $(this).attr('rel');
           
            var i = getUrlParameter('i');
            var v = getUrlParameter('v');
            
            $.ajax({
                 url: 'getProgramEntities.do',
                 data: {'i':i, 'v': v, 'programId':programId},
                 type: "GET",
                 success: function(data) {
                     $('#programEntityModal').html(data);
                     showSelHierarchy(programId);
                 }
            });
            
            
        });
        
        //Function that will control the hierarchy drop boxes
        $(document).on('change', '.hierarchyDropBox', function(event) {
            
            var boxId = $('.orgHierarchyDiv').find(".hierarchyDropBox").eq($(this).attr('rel3')).attr('rel');
            var level = eval(($(this).attr('rel3')*1)+1);
            
            populateHierarchy($(this).attr('rel2'), level, $(this).val(), boxId);
        });
        
        //Function to submit the selected program to the user
        $(document).on('click', '#submitEntityButton', function(event) {
            
            $('.hierarchyDropBox').each(function() {
                $('#hierarchyValues').val($('#hierarchyValues').val() + "," + $(this).attr('rel') + "-" + $(this).val());
            });
            
            var formData = $("#newProgramEntityForm").serialize();
            
            $.ajax({
                url: 'saveProgramUserEntity.do',
                data: formData,
                type: "POST",
                async: false,
                success: function(data) {
                    var url = $(data).find('#encryptedURL').val();
                    window.location.href = "details"+url+"&msg=entityAdded";
                }
            });
            
            
            event.preventDefault();
            return false;

        });
        
        //Function to remove the entity association of a program and user
        $(document).on('click', '.removeEntity', function(event) {
            var i = getUrlParameter('i');
            var v = getUrlParameter('v');
            
            if(confirm("Are you sure you want to remove this entity?")) {
            
                $.ajax({
                    url: 'removeUserEntity.do',
                    data: {'idList': $(this).attr('rel')},
                    type: "POST",
                    success: function(data) {
                       window.location.href = "details?i="+i+"&v="+v+"&msg=entityRemoved";
                    }
               }); 
           }
        });
        

    });
});

function showSelHierarchy(programId) {
    
    $.ajax({
        url: '/programAdmin/organization-hierarchy/getProgramAvailableHierarchy.do',
        data: {'programId':programId},
        type: "GET",
        success: function(data) {
            $('.orgHierarchyDiv').html(data);
            populateHierarchy(programId, 1, 0, $(data).find(".hierarchyDropBox").first().attr('rel'));
        }
    }); 
}

function populateHierarchy(programId, level, assocId, boxId) {
   $.getJSON('/programAdmin/organization-hierarchy/getOrgHierarchyListOptions.do', {
        programId: programId, level: level, assocId: assocId, ajax: true
    }, function(data) {
        var html = '<option value="0">- All -</option>';
        var len = data.length;

        for (var i = 0; i < len; i++) {
            html += '<option value="' + data[i][0] + '">' + data[i][1] + '</option>';
        }
        
        $('#hierarchy_'+boxId).html(html);
    });
           
}

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


function checkForm()
{
	
	 $('div.form-group').removeClass("has-error");
     $('span.control-label').removeClass("has-error");
     $('span.control-label').html("");
     
    
	//Make sure the staff type is selected
    if($('#typeId').val() == 0) {
        $('#typeIdDiv').addClass('has-error');
        $('#typeIdMsg').addClass('has-error');
        $('#typeIdMsg').html('The staff type must be selected');
        return false;
    } 
    var newPassword = $('#password').val();
    var confirmPassword = $('#confirmPassword').val();
    
    if (newPassword.trim().length > 0 || confirmPassword.trim().length > 0) {
        if(newPassword.length < 5) {
            $('#passwordDiv').addClass("has-error");
            $('#passwordMsg').addClass("has-error");
            $('#passwordMsg').html('The new password must be between 5 and 15 characters.');
            return false;
        }
        if(confirmPassword.length < 5) {
            $('#confirmPasswordDiv').addClass("has-error");
            $('#confirmPasswordMsg').addClass("has-error");
            $('#confirmPasswordMsg').html('The confirm password must be between 5 and 15 characters.');
            return false;
        } 
        if(newPassword != confirmPassword) {
            $('#confirmPasswordDiv').addClass("has-error");
            $('#confirmPasswordMsg').addClass("has-error");
            $('#confirmPasswordMsg').html('The confirm password must be equal to the new password.');
            return false;
        } 
    } 
    return true;
}   