$(function() {	
	
	//===================================================================================
	// --- PUBLIC PAGE ---
	//===================================================================================

	/*
	$.get("/user", function(data) {
		console.log("Username:"+data.name);
		$("#user").html(data.name);
		$(".unauthenticated").hide()
		$(".authenticated").show()
	});*/
	
	// --- INFORMATION DATA CARDS

	//Original array with the info from the database
	var publicInfo = (function() {
		var json = null;
		$.ajax({
			'async': false,
			'global': false,
			'url': '/contactinfos',
			'dataType': "json",
			'success': function(data) {
				json = data;
			}
		});
		return json;
	})();

	drawInfoCards(publicInfo);
	$('.infoCard').slice(0, 6).show();

	// Load more info when user presses Show More
	$("#loadInfoBtn").on('click', function(e) {
		//e.preventDefault();
		$(".infoCard:hidden").slice(0, 3).slideDown();
		if ($(".infoCard:hidden").length == 0) {
			$("#loadInfoBtn").fadeOut('slow');
		}
	});

	$("#hideInfoBtn").on('click', function(e) {
		//e.preventDefault();
		$(".infoCard").slice(-3).fadeOut('slow');
		// $(".infoCard").slice(-3).hide();
		if ($(".infoCard").length == 6) {
			$("#hideInfoBtn").fadeOut('slow');
		}
	});

	// - Filtering for the public page	

	//Filtering will always happen when one of the inputs are changed
	$('input[name="infoCountry"]').on('change', function() {

		//Makes the filter array
		var filters = $('#infoFilters').map(function() {
			var f = [];
			$(this).find('input[name="infoCountry"]:checked').each(function() {
				var o = {};
				o[$(this).data('area')] = this.value;
				f.push(o);
			});
			return f;
		}).get();

		//Filter - makes a new array 		
		var newInfo = [];
		$.each(publicInfo, function(key, info) {
			//console.log("Name: " + info.name + ", Country: " + info.area);
			$.each(filters, function(x, filter) {
				//console.log("Against filter: " + filter.area);
				if (filter.area == info.area) {
					newInfo.push(info);
				}
			});
		});
		if (filters.length > 0) drawInfoCards(newInfo);
		else drawInfoCards(publicInfo);
	});


	// =======================================================================================
	// === USER RELATED FUNCTIONS ===
	// =======================================================================================

	$('#userForm').submit(function(event) {
		register(event);
	});

	$('#resendConfirmationButton').on('click', function() {
		var token = $('#token').val();
		console.log("Token: " + token);
		$.get("/resendRegistrationToken?token=" + token, function(data) {
			window.location.href = "/login";
		}).fail(function() {
			$("#resentError").show();
		});
	});	
	
	$('#resetForm').submit(function(event){
		resetPassword(event);
	});
	
	$('#resetPasswordForm').validate({
		rules:{
			newPassword: "required",
			confirmPassword: {
				required: true,
				equalTo: "#newPassword"
			}			
		},
		messages:{
			newPassword: "Please, inform a password",
			confirmPassword: {
				required: "Please, confirm your password",
				equalTo: "Please, write the same password on both fields"
			}
		},
		submitHandler: function(){
			console.log("Form is validated!")
			var formData = $('#resetPasswordForm').serialize();
			$.post("savepassword", formData, function(data){
				if (data.message == "message.passwordChanged"){
					window.location.href="/login?message="+data.message;
					console.log("Password reset succesful!");
				} else {
					console.log("Fail to reset password! Reason: " + data.message);
				}
			});
		}
	});

	// Volunteer form methods
	$('#Hosting').change(function () {
			if ($(this).is(":checked")) {
					$('#hosting-form').show();
			} else {
					$('#hosting-form').hide();
			}
	});

	$('#Driving').change(function () {
			if ($(this).is(":checked")) {
					$('#driving-form').show();
			} else {
					$('#driving-form').hide();
			}
	});

	$('#volunteerForm').submit(function (e) {
			e.preventDefault();
			$('#volunteerSubmit').prop('disabled', true).attr('value', 'Loading...');
			const form = $(this);

			const multiSelectKeys = ['volunteerType', 'veTypeHelp', 'veDestination'];

			function mutateVal(value) {
				if (value === 'on') {
					return true;
				}
				if (value === 'off') {
					return false;
				}

				return value;
			}

			const serializedArray = form.serializeArray();

			const body = {
				volunteerType: [],
				veTypeHelp: [],
				veDestination: [],
				isHosting: false,
				isDriving: false,
				availableWeekdays: false,
				availableWeekends: false,
			};

			for (const fieldArr of serializedArray) {
				const key = fieldArr.name;
				const val = mutateVal(fieldArr.value);

				// Set isHosting and isDriving
				if (key === 'volunteerType') {
					if (val === '56') {
						body.isHosting = true;
					}
					else if (val === '57') {
						body.isDriving = true;
					}
				}

				if (multiSelectKeys.includes(key)) {
					if (!body[key]) {
						body[key] = [];
					}

					body[key].push(val);
				} else {
					body[key] = val;
				}
			}

			$.ajax({
				url:"/addvolunteer",
				type:"POST",
				data:JSON.stringify(body),
				contentType:"application/json; charset=utf-8",
				dataType:"json",
				success: function() {
					$('#responseMessage').text('Success! Please check your email for a confirmation');
				},
				error: function(data) {
					let responseMessage = 'Unknown server error';
					if (data.responseJSON && data.responseJSON.message) {
						responseMessage = data.responseJSON.message;
					}
					$('#responseMessage').text('Error: ' + responseMessage);
				},
				complete: function() {
					$('#volunteerSubmit').prop('disabled', false).attr('value', 'Submit');
				},
			});
	});

}); // End of document ready

function changeLang(lang) { window.location.replace('?lang=' + lang); }

//===================================================================================
// --- PUBLIC PAGE ---
//===================================================================================

//Piece of ugly code to fill the information cards
function drawInfoCards(object) {
	$('.infoCard').slice(1).remove();
	$.each(object, function(k, info) {
		if (k == 0) {
			$('.infoCard').find('.info-CountryImg').attr("src", "img/countries/" + info.area + ".png");
			$('.infoCard').find('.info-Country').text(info.area);
			$('.infoCard').find('.info-typeAssistance').text(info.typeAssistance);
			$('.infoCard').find('.info-name').text(info.name);
			$('.infoCard').find('.info-notes').text(info.notes);
			$('.infoCard').find('.infoCardList').empty();//need to empty the list, otherwise it will keep adding and adding the elements to it everytime the filters are used
			if (info.phoneNumber.length > 0) $('.infoCard ul').append("<li><i class='fas fa-phone-alt'></i> <a href='tel:+" + info.countryCode + info.phoneNumber + "'>+" + info.countryCode + " " + info.phoneNumber + "</a></li>");
			if (info.email.length > 0) $('.infoCard ul').append("<li><i class='fas fa-envelope'></i> <a href='mailto:" + info.email + "'>" + info.email + "</a></li>");
			if (info.website.length > 0) $('.infoCard ul').append("<li><i class='fas fa-desktop'></i> <a href='" + info.website + "' target='_blank'>Website</a></li>");
		} else {
			var infoCardCloned = $('.infoCard').first().clone();
			infoCardCloned.find('.info-CountryImg').attr("src", "img/countries/" + info.area + ".png");
			infoCardCloned.find('.info-Country').text(info.area);
			infoCardCloned.find('.info-typeAssistance').text(info.typeAssistance);
			infoCardCloned.find('.info-name').text(info.name);
			infoCardCloned.find('.info-notes').html(info.notes);
			infoCardCloned.find('.infoCardList').empty();
			if (info.phoneNumber.length > 0) infoCardCloned.find('ul').append("<li><i class='fas fa-phone-alt'></i> <a href='tel:+" + info.countryCode + info.phoneNumber + "'>+" + info.countryCode + " " + info.phoneNumber + "</a></li>");
			if (info.email.length > 0) infoCardCloned.find('ul').append("<li><i class='fas fa-envelope'></i> <a href='mailto:" + info.email + "'>" + info.email + "</a></li>");
			if (info.website.length > 0) infoCardCloned.find('ul').append("<li><i class='fas fa-desktop'></i> <a href='" + info.website + "' target='_blank'>Website</a></li>");
			$('#infoCards').append(infoCardCloned);
		}
	});
}

// - Registers new user (not for long, let's move it to jquery.validate)
function register(event) {
	event.preventDefault();
	var formData = $('#userForm').serialize();

	var pass = $("#password").val();
	var valid = pass == $("#confirmPassword").val();
	if (!valid) {
		$("#passwordMismatch").show();
		return;
	}

	$.post("adduser", formData, function(data) {
		if (data.message == "success") {
			window.location.href = "/login?message=UserRegistered";
			//window.location.href = "/user/index";
		}
	}).fail(function(data) {
		if (data.responseJSON.message == "UserAlreadyExist") {
			$("#emailError").show();
		}
	});
}

function resetPassword(event){
	event.preventDefault();	
    var username = $("#username").val();
	
    $.post("resetPassword",{username: username} ,function(data){
        console.log("Email: " + username);    
		//window.location.href = "/login?message=" + data.message;
    })
    .fail(function(data) {
    	if(data.responseJSON.error.indexOf("MailError") > -1)
        {
            $("#emailError").show();
        }
        else{
            window.location.href = "/login?message=" + data.responseJSON.message;
        }
    });
}

function includeJS(jsFile){
	$.getScript( jsFile )
	  .fail(function( jqxhr, settings, exception ) {
	    console.log( "Problem loading " + jsFile + ". Exception: " + exception);
	});
}

includeJS('/js/jquery.richtext.min.js');
includeJS('/js/moment.min.js');