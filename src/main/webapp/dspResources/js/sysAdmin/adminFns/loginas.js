

require(['./main'], function() {
    require(['jquery'], function($) {

        $("input:text,form").attr("autocomplete", "off");

        //Fade out the updated/created message after being displayed.
        if ($('.alert').length > 0) {
            $('.alert').delay(2000).fadeOut(1000);
        }

        
        //Need to populate the table columns or the selected table
        $(document).on('change', '.programId', function() {
        	var programId = $(this).val();
        	$.ajax({
            	type: "POST",
                url: 'getProgramUsers.do',
                data: {'programId': programId},
                success: function(data) {
                	if (data.indexOf('option') != -1) {
	                	$('#loginAsUserDiv').show();
	                	$('#NoLoginAsUserDiv').hide();
	                	$('#loginAsUserSelect').html(data);
	                	$('#submitButton').show();
                	} else {
                		$('#NoLoginAsUserDiv').show();
                		$('#loginAsUserDiv').hide();
                		$('#submitButton').hide();
                		
                	}
                }
            });
            event.preventDefault();
            return false;
        });
        
        
        
        //Function to submit the login as request and validates the password
        //It all checks pass the login as form will be submitted
        $('#submitButton').click(function(event) {
        	
        	$('div.form-group').removeClass("has-error");
            $('span.control-label').removeClass("has-error");
            $('span.control-label').html("");
            
        	//we do all the check
        	var proceed = true;
        	
        	//first we check to make sure all fields have a value
        	if ($('#userName').val().trim() == "") {
        		$('#userNameDiv').addClass("has-error");
                $('#userNameMsg').html('This cannot be blank!');
                proceed = false;
        	}
        	
        	if ($('#password').val().trim() == "") {
        		$('#passwordDiv').addClass("has-error");
                $('#passwordMsg').html('This cannot be blank!');
                proceed = false;
        	} 
        	
        	if ($('#loginAsUser').val() == "") {
        		$('#loginAsUserDiv').addClass("has-error");
                $('#loginAsUserMsg').html('Please select the user you would like to login as.');
                proceed = false;        	
           }
        	
           if ($('#realUsername').val() != $('#userName').val()) {
                $('#userNameDiv').addClass("has-error");
                $('#userNameMsg').html('That is not correct!');
                proceed = false;        	
            }
        	
        	if ($('#programId').val().trim() == "" ) {
                $('#loginAsProgramDiv').addClass("has-error");
                $('#loginAsProgramMsg').html('That is not correct!');
                proceed = false;        	
            }
        	
        	if ($('#loginAsUser').val().trim() == "" ) {
                $('#loginAsUserDiv').addClass("has-error");
                $('#loginAsUserMsg').html('That is not correct!');
                proceed = false;        	
            }
        	
        	if (proceed) {
        		var password = $('#password').val();
        		var loginAsUser = $('#loginAsUser').val();
	            var actionValue = 'loginAsCheck.do';
	            var programURL = $('#programURL').val();
	            
	            $.ajax({
	                url: actionValue,
	                data: {'password':password, 'loginAsUser':loginAsUser},
	                type: "POST",
	                async: false,
	                success: function(data) {
	                    if (data.indexOf('pwmatched') != -1) {
	                    	//set program url
	                    	//$('#form-admin-login').attr("action", "j_spring_security_check");
	                    	$('#form-admin-login').attr("action", programURL);
	                    	$('#form-admin-login').submit();
	                    } else {
	                    		$('#passwordDiv').addClass("has-error");
	                            $('#passwordMsg').html('Invalid Credentials');
	                    }
	                }
	            });
            	event.preventDefault();
            	return false;
        	}
        	
        });
    });
});



