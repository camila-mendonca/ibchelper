$(function(){
	$('#langForm').submit(function(event){
		event.preventDefault();
		var formData = $('#langForm').serialize();
		
		$.post("addlanguage", formData, function(data){
			if (data.message == "success") {
				window.location.href = "admin/adminpage";
			}
		});
	});
	
	$('#volTypeForm').submit(function(event){
		event.preventDefault();
		var formData = $('#volTypeForm').serialize();
		
		$.post("admin/addvoltype", formData, function(data){
			if (data.message == "success") {
				window.location.href = "admin/adminpage";
			}
		});
	});
});