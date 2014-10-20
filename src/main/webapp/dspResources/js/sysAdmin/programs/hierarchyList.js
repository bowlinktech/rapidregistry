/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

require(['./main'], function () {
    require(['jquery'], function($) {

        //Fade out the updated/created message after being displayed.
        if ($('.alert').length > 0) {
            $('.alert').delay(2000).fadeOut(1000);
        }

        $("input:text,form").attr("autocomplete", "off");
        
        //Open up the modal to display the org hierarchy form
        $(document).on('click', '#createNewHierarchy', function() {
            $.ajax({
                url: 'organization-hierarchy/hierarchyForm',
                data: {'id':0},
                type: "GET",
                success: function(data) {
                    $("#hierarchyDetailsModal").html(data);
                }
            });
        });
        
        //Open up the modal to display the patient org hierarchy form
        $(document).on('click', '.editHierarchy', function() {
            $.ajax({
                url: 'organization-hierarchy/hierarchyForm',
                data: {'id':$(this).attr('rel')},
                type: "GET",
                success: function(data) {
                    $("#hierarchyDetailsModal").html(data);
                }
            });
        });
        
        
        //Function to submit the changes to an existing hierarchy or 
        //submit the new hierarchy fields from the modal window.
        $(document).on('click', '#submitButton', function(event) {
            
            var formData = $("#hierarchyForm").serialize();
            
            if($('#dspPos').val() == 0) {
                $('#dspPosDiv').addClass("has-error");
                $('#dspPosMsg').addClass("has-error");
                $('#dspPosMsg').html('A display position must be selected!');
            }
            else {
            
                $.ajax({
                    url: 'organization-hierarchy/saveOrgHierarchy',
                    data: formData,
                    type: "POST",
                    async: false,
                    success: function(data) {

                        if (data.indexOf('hierarchySaved') != -1) {
                           window.location.href = "/sysAdmin/programs/"+$('#progamNameURL').val()+"/organization-hierarchy?msg=hierarchysaved";
                        }
                        else {
                            $("#hierarchyDetailsModal").html(data);
                        }
                    }
                });
            }
            
           event.preventDefault();
           return false;
        });
        
        
    });
});




