

require(['./main'], function () {
    require(['jquery'], function($) {
        
        $("input:text,form").attr("autocomplete", "off");
        
        
        //Fade out the updated/created message after being displayed.
        if ($('.alert').length > 0) {
            $('.alert').delay(2000).fadeOut(1000);
        }
        
        //Open up the modal to display the patient entry form
        $(document).on('click', '#createNewsection', function() {
            $.ajax({
                url: 'sectionForm',
                data: {'id':0, 'section': $(this).attr('rel')},
                type: "GET",
                success: function(data) {
                    $("#sectionModal").html(data);
                }
            });
        });
        
        //Open up the modal to display the patient entry form
        $(document).on('click', '.editsection', function() {
            $.ajax({
                url: 'sectionForm',
                data: {'id':$(this).attr('rel'), 'section': $(this).attr('rel2')},
                type: "GET",
                success: function(data) {
                    $("#sectionModal").html(data);
                }
            });
        });
        
        //Function to submit the changes to an existing user or 
        //submit the new user fields from the modal window.
        $(document).on('click', '#submitSection', function(event) {
            
            var formData = $("#sectionForm").serialize();
            
            if($('#section').val() == 'patient-sections') {
                var url = 'savePatienSection';
            }
            else {
                var url = 'saveEngagementSection';
            }
            
            $.ajax({
                url: url,
                data: formData,
                type: "POST",
                async: false,
                success: function(data) {

                    if (data.indexOf('sectionSaved') != -1) {
                       window.location.href = "/sysAdmin/programs/"+$('#progamNameURL').val()+"/forms/"+$('#section').val()+"?msg=sectionsaved";
                    }
                    else {
                        $("#sectionModal").html(data);
                    }
                }
            });
            
           event.preventDefault();
           return false;
        });
       
        
    });
});
