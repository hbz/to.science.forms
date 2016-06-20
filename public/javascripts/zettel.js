function enableAllGndAutocompletion() {
	$('input.gnd-person-search').each(function() {
		enableGndPersonAutocompletion($(this));
	});
	$('input.gnd-subject-search').each(function() {
		enableGndSubjectAutocompletion($(this));
	});
}
function enableGndPersonAutocompletion(inputElement) {
	inputElement.autocomplete({
		select : function(event, ui) {
			this.value = ui.item.value;
			$(this).siblings(".input-field-heading").html(
					"<b>" + ui.item.label + "</b>(" + ui.item.value + ")");
			return false;
		},
		source : function(request, response) {
			$.ajax({
				url : "http://api.lobid.org/person",
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
			return false;
		},
		source : function(request, response) {
			$.ajax({
				url : "http://api.lobid.org/subject",
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
function destroyGndAutocompletion() {
	$('input.gnd-person-search').each(function() {
		$(this).autocomplete('destroy');
		$(this).removeData('autocomplete');
	});
	$('input.gnd-subject-search').each(function() {
		$(this).autocomplete('destroy');
		$(this).removeData('autocomplete');
	});
}
function resetIds(curFieldName) {
	var num = 0;
	$('input[name^=' + curFieldName + ']').each(function() {
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
			newField.appendTo($wrapper).find('input').val('').focus();
			resetIds(curFieldName);
			$(newField).find(".input-field-heading").html("");
			enableAllGndAutocompletion();
		});
		$('.multi-field .remove-field', $wrapper).click(function() {
			if ($('.multi-field', $wrapper).length > 1)
				$(this).parents('.multi-field').remove();
			resetIds(curFieldName);
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
			: (parent.document.postMessage ? parent.document
					: undefined);
	if (typeof target != "undefined") {
		postData(target);
		resize(target);
		 
	}
}
function postData(target){
	var data = $('#embeddedJson').text();
	if (data.length) {
		target.postMessage(data, "*");
	}
}
function resize(target){
	var body = document.body;
	var html = document.documentElement;
	var height = body.offsetHeight;
    if(height === 0){
        height = html.offsetHeight;
    }
		target.postMessage({'action':'RESIZE', 'height':height}, '*');
}
