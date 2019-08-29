/**
 * 
 */

$(function(){
	$(".item_section").mouseover(function(){
		$(this).css('background-color', 'rgba(100, 100, 100, 0.7)');
	});
	$(".item_section").mouseout(function(){
		$(this).css('background-color', 'rgba(22, 22, 22, 0.6)');
	});
	
	$(".cmp1BorderSiteMapItem").click(function(){
		alert('dfsdf');
	});
});