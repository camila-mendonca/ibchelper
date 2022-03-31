$(function(){
	$('#langForm').submit(function(event){
		event.preventDefault();
		var formData = $('#langForm').serialize();
		
		$.post("addlanguage", formData, function(data){
			if (data.message == "success") {
				window.location.href = "/adminpage";
			}
		});
	});
	
	$('#volTypeForm').submit(function(event){
		event.preventDefault();
		var formData = $('#volTypeForm').serialize();
		
		$.post("addvoltype", formData, function(data){
			if (data.message == "success") {
				window.location.href = "/adminpage";
			}
		});
	});
});