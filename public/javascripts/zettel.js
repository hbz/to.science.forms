function addDatepicker() {
	$(".datepicker").datepicker();

}
function initializeConnectionToParent() {
	if (top != self) {
		emitEvent();
	}
}

function enableAllGndAutocompletion() {
	$('.gnd-person-search input').each(function() {
		enableGndPersonAutocompletion($(this));
	});
	$('.gnd-subject-search input').each(function() {
		enableGndSubjectAutocompletion($(this));
	});
}
function enableGndPersonAutocompletion(inputElement) {
	inputElement.autocomplete({
		select : function(event, ui) {
			this.value = ui.item.value;
			$(this).siblings(".input-field-heading").html(
					"<b>" + ui.item.label + "</b>(" + ui.item.value + ")");
			emitResize();
			return false;
		},
		source : function(request, response) {
			$.ajax({
				url : "https://lobid.org/person",
				dataType : "jsonp",
				data : {
					name : request.term,
					format : "ids"
				},
				success : function(data) {
					response(data);
				}
			});
		}
	});
}
function enableGndSubjectAutocompletion(inputElement) {
	inputElement.autocomplete({
		select : function(event, ui) {
			this.value = ui.item.value;
			$(this).siblings(".input-field-heading").html(
					"<b>" + ui.item.label + "</b>(" + ui.item.value + ")");
			emitResize();
			return false;
		},
		source : function(request, response) {
			$.ajax({
				url : "https://lobid.org/subject",
				dataType : "jsonp",
				data : {
					name : request.term,
					format : "ids"
				},
				success : function(data) {
					response(data);
				}
			});
		}
	});
}

function handleMessage(evt) {
	if (evt.data.action == 'postDataToZettel' && evt.data.message != 0) {
		$.ajax({
			type : 'POST',
			url : "/tools/zettel/forms?" + evt.data.queryParam,
			data : decodeURI(evt.data.message),
			crossDomain : true,
			contentType : 'application/rdf+xml;charset=utf-8',
			success : function(data, textStatus, jqXHR) {
				var html = $('<div/>').html(data).contents();
				var newForm = $('form', html);
				var containerOfOldform = $('div.container');
				containerOfOldform.html(newForm);
				enableAllGndAutocompletion();
				addActionsToRemoveAndAddButtons();
				window.addEventListener("message", handleMessage, false);
				emitResize();
			},
			error : function(data, textStatus, jqXHR) {

			}
		});
	}
}
function destroyGndAutocompletion() {
	$('.gnd-person-search input').each(function() {
		$(this).autocomplete('destroy');
		$(this).removeData('autocomplete');
	});
	$('.gnd-subject-search input').each(function() {
		$(this).autocomplete('destroy');
		$(this).removeData('autocomplete');
	});
}
function resetIds(curFieldName) {
	var num = 0;
	$('.input-widget[name^=' + curFieldName + ']').each(function() {
		console.log(curFieldName);
		$(this).attr('name', curFieldName + "[" + num + "]");
		$(this).attr('id', curFieldName + "_" + num);
		num++;
	});
}

function addActionsToRemoveAndAddButtons() {
	$('.multi-field-wrapper').each(function() {
		var $wrapper = $('.multi-fields', this);
		var curFieldName = $('.multi-fields', this).attr('id');
		$(".add-field", $(this)).click(function(e) {
			destroyGndAutocompletion();
			var newField = $('.multi-field:first-child', $wrapper).clone(true);
			newField.appendTo($wrapper).find('.input-widget').val('').focus();
			resetIds(curFieldName);
			$(newField).find(".input-field-heading").html("");
			enableAllGndAutocompletion();
			emitResize();
		});
		$('.multi-field .remove-field', $wrapper).click(function() {
			if ($('.multi-field', $wrapper).length > 1){
				$(this).parents('.multi-field').remove();
				resetIds(curFieldName);
				emitResize();
			}
			else{
				destroyGndAutocompletion();
				var newField = $('.multi-field:first-child', $wrapper).clone(true);
				newField.appendTo($wrapper).find('.input-widget').val('').focus();
				resetIds(curFieldName);
				$(newField).find(".input-field-heading").html("");
				$(this).parents('.multi-field').remove();
				enableAllGndAutocompletion();
				emitResize();
			}
		});
		$('.multi-field .moveUp', $wrapper).click(function() {
			var $el = $(this).parents(".multi-field");
			if ($el.not(':first-child')) {
				$el.prev().before($el);
				resetIds(curFieldName);
			}
		});
		$('.multi-field .moveDown', $wrapper).click(function() {
			var $el = $(this).parents(".multi-field");
			if ($el.not(':last-child')) {
				$el.next().after($el);
				resetIds(curFieldName);
			}
		});

	});
}

function emitEvent() {
	var target = parent.postMessage ? parent
			: (parent.document.postMessage ? parent.document : undefined);
	if (typeof target != "undefined") {
		postData(target);
		resize(target);
	}
}
function postData(target) {
	var data = $('#embeddedJson').text();
	if (data.length) {
		target.postMessage({
			'action' : 'postData',
			'message' : data
		}, "*");
	} else {
		var topicId = $('#topicId').text();
		var documentId = $('#documentId').text();
		target.postMessage({
			'action' : 'establishConnection',
			'message' : null,
			'topicId' : topicId,
			'documentId' : documentId
		}, "*");
	}
}
function emitResize() {
	var target = parent.postMessage ? parent
			: (parent.document.postMessage ? parent.document : undefined);

	if (typeof target != "undefined") {
		resize(target);
	}
}

function resize(target) {
	var body = document.body;
	var html = document.documentElement;
	var height = body.offsetHeight;
	if (height === 0) {
		height = html.offsetHeight;
	}
	target.postMessage({
		'action' : 'resize',
		'message' : height
	}, '*');
}
