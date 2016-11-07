/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


require(['./main'], function () {
	require(['jquery', 'multiselect'], function($) {
		
    	$('#form-field-select-4').multiselect({
            inheritClass: true,
            buttonWidth: '400px', 
            numberDisplayed: 0,
            includeSelectAllOption: true
        });
    
    	
    	
    	
        
    });
});

