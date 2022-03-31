

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


	// =======================================================================================
	// === VOLUNTEER'S PORTAL ===
	// =======================================================================================

	// --- INFORMATION FORM

	//from additional-methods.js
	//Creating a method that validates the info contacts - will return an error msg if all the contact fields are empty
	$.validator.addMethod("validate_contacts", function(value, element, options) {
		var $fields = $(options[1], element.form),
			$fieldsFirst = $fields.eq(0),
			validator = $fieldsFirst.data("valid_req_grp") ? $fieldsFirst.data("valid_req_grp") : $.extend({}, this),
			isValid = $fields.filter(function() {
				return validator.elementValue(this);
			}).length >= options[0];

		// Store the cloned validator for future validation
		$fieldsFirst.data("valid_req_grp", validator);

		// If element isn't being validated, run each validate_contacts field's validation rules
		if (!$(element).data("being_validated")) {
			$fields.data("being_validated", true);
			$fields.each(function() {
				validator.element(this);
			});
			$fields.data("being_validated", false);
		}
		return isValid;
	}, $.validator.format("Please, inform at least one way of contact."));

	// - Saving new info
	$("#infoForm").validate({ //it first validates the form with jquery.validate
		messages: {
			name: "Please, inform a name!",
			area: "Please, select an area.",
			language: "Please, select a language.",
			typeAssistance: "Please, inform the type of assistance offered."
		},
		rules: { //applies the method implemented before
			email: { validate_contacts: [1, ".contacts"] },
			website: { validate_contacts: [1, ".contacts"] },
			phoneNumber: { validate_contacts: [1, ".contacts"] },
		},
		submitHandler: function() { //if the form is valid:
			console.log("Form is validated!");
			var formData = $('#infoForm').serialize();
			$.post("addinfo", formData, function(data) {
				if (data.message == "success") {
					$('#infoForm')[0].reset();
					$('#infoForm .richText-editor').html('<div></div>');
					$("#infoAdded").show();
					infoTable.ajax.reload();
					window.location.href = "/user/index#newInfo";
				}
			})
		}
	});

	$("#clearFormButton").on('click', function() {
		$('#infoForm')[0].reset();
		$('#confirmClearModal').modal('hide');
	});

	// --- INFORMATION DATATABLE

	//Formats the details that appear on the child row (when the user presses the + button)
	function formatInfoDetails(d) {
		var phone = '';
		var email = '';
		var website = '';
		var notes = '';
		if (d.phoneNumber != '') {
			var country = '';
			if (d.countryCode != '') {
				country = '+' + d.countryCode;
			}
			phone = '<tr><td class="text-end">Phone number:</td><td><a href="tel:' + country + d.phoneNumber + '">' + country + ' ' + d.phoneNumber + '</a></td></tr>';
		}
		if (d.email != '') {
			email = '<tr><td class="text-end">Email:</td><td><a href="mailto:' + d.email + '">' + d.email + '</a></td></tr>';
		}
		if (d.website != '') {
			website = '<tr><td class="text-end">Website:</td><td><a href="' + d.website + '">' + d.website + '</a></td></tr>';
		}
		if (d.notes != '') {
			notes = '<tr><td class="text-end">Additional notes:</td><td>' + d.notes + '</td></tr>';
		}
		return '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">' + phone + email + website + notes + '</table>';
	}

	//Uses datatables.min.js to generate a Datatable
	var infoTable = $('#infoTable').DataTable({
		"order": [[0, "desc"]],
		ajax: {
			url: '/user/listcontactinfo',
			dataSrc: ''
		},
		"columns": [
			{ "data": "contactInfoId", "visible": false },
			{ "className": 'dt-control', "orderable": false, "data": null, "defaultContent": '' },
			{ "data": "name" },
			{ "data": "area" },
			{ "data": "typeAssistance" },
			{ "className": 'dt-buttons', "data": null, "defaultContent": "<button type='button' class='editButton btn btn-primary btn-sm'><i class='fas fa-edit text-white-50'></i></button> <button type='button' class='removeButton btn btn-danger btn-sm'><i class='fas fa-trash-alt text-white-50'></i></button>" }
		]
	});

	//Add contact details on row child when the user presses (+)
	$('#infoTable tbody').on('click', 'td.dt-control', function() {
		var tr = $(this).closest('tr');
		var row = infoTable.row(tr);

		if (row.child.isShown()) { //If child row is opened
			row.child.hide();
			tr.removeClass('shown');
		}
		else {
			row.child(formatInfoDetails(row.data())).show();
			tr.addClass('shown');
		}
	});

	// --- INFORMATION TABLE FILTERING

	//Filter by Area
	$('input[name="lct"]').on('change', function() {
		var value = $('input[name="lct"]:checked').val();

		if (this.checked) {
			$('input[name="lct"]').not(this).prop('checked', false);
			console.log("checkbox clicked: " + value);
			infoTable.columns(3).search(value).draw();
		} else {
			infoTable.columns(3).search('').draw();
		}
	});

	//Order by ID (older/newer)
	$('input[name="orderOlder"]').on('change', function() {
		var value = $('input[name="orderOlder"]:checked').val();

		if (this.checked) {
			infoTable.column(0).order('asc').draw();
		} else {
			infoTable.column(0).order('desc').draw();
		}
	});

	// --- INFORMATION TABLE OPTIONS

	// Edit Info
	$('#infoTable tbody').on('click', "td .editButton", function() {
		console.log("Loading info to edit form...");
		var row = $(this).closest('tr');
		var id = infoTable.row(row).data().contactInfoId;
		loadEditInfo(id);

	});

	// Remove info
	$('#infoTable tbody').on('click', 'td .removeButton', function() {
		var row = $(this).closest('tr');
		var id = infoTable.row(row).data().contactInfoId;
		$("#confirmRemoveInfoButton").attr("value", id);
		$("#confirmRemoveInfoModal").modal('show');
	});

	// Confirm removal
	$("#confirmRemoveInfoButton").on('click', function() {
		var id = this.value;
		$.get("/user/removeinfo/info=" + id, function() {
			$("#confirmRemoveInfoModal").modal('hide');
			infoTable.ajax.reload();
		});
	});

	// --- EVENT DATATABLE
	
	function formatEventDetails(d) {
		var address = '';
		var details = '';
		if (d.address != '') {
			address = '<tr><td class="text-end">Address:</td><td>' + d.address + '</td></tr>';
		}
		if (d.details != '') {
			details = '<tr><td class="text-end">Details:</td><td>' + d.details + '</td></tr>';
		}
		return '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">' + address + details + '</table>';
	}
	
	var eventsTable = $('#eventsTable').DataTable({
		"order": [[0, "desc"]],
		ajax: {
			url: '/user/listevents',
			dataSrc: ''
		},
		"columns": [
			{ "data": "eventId", "visible": false },
			{ "className": 'dt-control', "orderable": false, "data": null, "defaultContent": '' },
			{ "data": "name" },
			{ "data": null,
                "render": function ( data, type, row ) {
                    // Combine the first and last names into a single table field
                    return moment(data.dateEvent).format('MMM Do (dddd)') + ', at ' + moment(data.hourEvent,'HH:mm:ss').format('HH:mm');
                }
			},			
			{ "data": "country"}
		]
	});
	
	$('#eventsTable tbody').on('click', 'td.dt-control', function() {
		var tr = $(this).closest('tr');
		var row = eventsTable.row(tr);

		if (row.child.isShown()) { //If child row is opened
			row.child.hide();
			tr.removeClass('shown');
		}
		else {
			row.child(formatEventDetails(row.data())).show();
			tr.addClass('shown');
		}
	});

	// --- EVENT FORM

	$('.text-editor').richText({
		bold: true,
		italic: true,
		underline: true,
		leftAlign: false,
		centerAlign: false,
		rightAlign: false,
		justify: false,
		ol: false,
		ul: false,
		heading: false,
		fonts: false,
		fontList: [],
		fontColor: false,
		fontSize: true,
		imageUpload: false,
		fileUpload: false,
		videoEmbed: false,
		urls: true,
		table: false,
		removeStyles: false,
		code: false,
		colors: [],
		fileHTML: '',
		imageHTML: '',
		translations: { 'title': 'Title', 'linkText': 'Link text', 'url': 'URL', 'size': 'Size', 'responsive': '<a href="https://www.jqueryscript.net/tags.php?/Responsive/">Responsive</a>', 'text': 'Text', 'openIn': 'Open in', 'sameTab': 'Same tab', 'newTab': 'New tab', 'add': 'Add', 'pleaseEnterURL': 'Please enter an URL', 'bold': 'Bold', 'italic': 'Italic', 'underline': 'Underline', 'addFontSize': 'Add font size', 'addURL': 'Add URL', 'undo': 'Undo', 'redo': 'Redo', 'close': 'Close' },
		youtubeCookies: false,
		preview: false,
		placeholder: '',
		useSingleQuotes: false,
		height: 0,
		heightPercentage: 0,
		id: "",
		class: "",
		useParagraph: false,
		maxlength: 0,
		useTabForNext: false,
		callback: undefined,
	});

	$("#eventForm").validate({ //it first validates the form with jquery.validate
		messages: {
			name: "Please, inform a name!",
			dateEvent: "Please, inform the date of the event.",
			hourEvent: "Please, inform the hour of the event.",
			country: "Please, inform the country.",
			address: "Please, inform where the event takes place"
		},
		submitHandler: function() { //if the form is valid:
			console.log("Form is validated!");
			
			var formData = $('#eventForm').serialize();
			$.post("/user/addevent", formData, function(data) {
				if (data.message == "success") {
					$('#eventForm')[0].reset();
					//$("#infoAdded").show();
					//infoTable.ajax.reload();
					window.location.href = "/user/volunteers";
				}
			})
			
		}
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
			window.location.href = "/user/index";
		}
	}).fail(function(data) {
		if (data.responseJSON.message == "UserAlreadyExist") {
			$("#emailError").show();
		}
	});
}

function includeJS(jsFile){
	$.getScript( jsFile )
	  .done(function( script, textStatus ) {
	    console.log( "Loaded js: " + jsFile );
	  })
	  .fail(function( jqxhr, settings, exception ) {
	    console.log( "Problem loading js!");
	});
}

includeJS('/js/jquery.richtext.min.js');
includeJS('/js/moment.min.js');