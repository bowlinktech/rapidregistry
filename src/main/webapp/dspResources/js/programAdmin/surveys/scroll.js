/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

require(['./main'], function () {
    require(['jquery'], function($) {
    	window.pageId = 1;
    	$(document).ready(function () {

    		/** side bar scroll **/
    	    var length = $('#left').height() - $('#sidebar').height() + $('#left').offset().top;

    	    $(window).scroll(function () {

    	        var scroll = $(this).scrollTop();
    	        var height = $('#sidebar').height() + 'px';

    	        if (scroll < $('#left').offset().top) {

    	            $('#sidebar').css({
    	                'position': 'absolute',
    	                'top': '0'
    	            });

    	        } else if (scroll > length) {

    	            $('#sidebar').css({
    	                'position': 'absolute',
    	                'bottom': '0',
    	                'top': 'auto'
    	            });

    	        } else {

    	            $('#sidebar').css({
    	                'position': 'fixed',
    	                'top': '0',
    	                'height': height
    	            });
    	        }
    	    });
    	});
    	
    	
    	/** scroll track **/
    	$(function(){
    	    $(window).bind('scroll', function() {
    	        $('.post').each(function() {
    	            var post = $(this);
    	            var position = post.position().top - $(window).scrollTop();
    	            
    	            if (position <= 0) {
    	                post.addClass('selected');
    	                window.pageId = post.attr("rel");
    	            } else {
    	                post.removeClass('selected');
    	            }
    	        });        
    	    });
    	});

    });
});




