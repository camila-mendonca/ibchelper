$(function() {
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
		return '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">' + phone + email + website + notes + '<tr><td class="text-end">Language:</td><td>' + d.language + '</td></tr></table>';
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
		var typeVol = '';
		if (d.address != '') {
			address = '<tr><td class="text-end"><b>Address:</b></td><td>' + d.address + '</td></tr>';
		}
		if (d.details != '') {
			details = '<tr><td class="text-end"><b>Details:</b></td><td>' + d.details + '</td></tr>';
		}
		if (d.neededVolunteers.length > 0){
			typeVol = '<tr><td class="text-end"><b>Volunteers Needed:</b></td><td>';
			$.each(d.neededVolunteers, function(k, vol) {
				typeVol = typeVol + vol.name + ", "
			});
			typeVol = typeVol + '</td></tr>';
		}
		return '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">' + details + typeVol + address + '</table>';
	}
	
	var eventsTable = $('#eventsTable').DataTable({
		"order": [[0, "desc"]],
		ajax: {
			url: '/listevents',
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

function includeJS(jsFile){
	$.getScript( jsFile )
	  .fail(function( jqxhr, settings, exception ) {
	    console.log( "Problem loading " + jsFile + ". Exception: " + exception);
	});
}

includeJS('/js/moment.min.js');