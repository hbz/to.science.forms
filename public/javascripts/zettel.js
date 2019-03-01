function addDatepicker() {
	$.datepicker.setDefaults({ dateFormat: 'yy-mm-dd', changeMonth: true, changeYear: true });
	$(".datepicker").datepicker();
}

function initializeConnectionToParent() {
	if (top != self) {
		emitEvent();
	}
}
function addAutocompletionWithDynamicEndpoint(autocompleteItem){
	endpoint = autocompleteItem.siblings("select").val();
	enableAutocompletion(autocompleteItem,endpoint);
	autocompleteItem.siblings("select").change(function(){
		newEndpoint=$(this).val();
		enableAutocompletion(autocompleteItem,newEndpoint);
	 });
}

function enableAutocompletionEndpoints() {
	$('.search input').each(function() {
		addAutocompletionWithDynamicEndpoint($(this));
	});
	$('.mydaterangepicker').each(function() {
		$(this).daterangepicker({
		      autoUpdateInput: false,
		      locale: {
		          cancelLabel: 'Clear'
		      }
		  });
		$(this).on('apply.daterangepicker', function(ev, picker) {
		      $(this).val(picker.startDate.format('MM/DD/YYYY') + ' - ' + picker.endDate.format('MM/DD/YYYY'));
		});
		$(this).on('cancel.daterangepicker', function(ev, picker) {
		      $(this).val('');
		});
	});
}

function enableAutocompletion(inputElement,endpoint) {
	$(inputElement).autocomplete({source:["No Result"]});
	var agrovoc=	"/tools/skos-lookup/autocomplete";
	
	if(agrovoc == endpoint){
		inputElement.autocomplete({
			select : function(event, ui) {
				this.value = ui.item.value;
				$(this).siblings(".input-field-heading").html(
						"<b>" + ui.item.label + " </b><a href=\""+ ui.item.value +"\" target=\"_blank\"><span class=\"octicon octicon-link-external\"></span></a>");
				$(this).siblings("select").css('display','none');
				$(this).css('display','none');
				emitResize();
				return false;
			},
			source : function(request, response) {	
				$.ajax({
					url : endpoint,
					dataType : "jsonp",
					data : {
						q:request.term,
                        lang:"de",
                        index:"agrovoc"
					},
					success : function(data) {
						response(data);
					}
				});
			}
		});
	}
	else {
		inputElement.autocomplete({
			select : function(event, ui) {
				this.value = ui.item.value;
				$(this).siblings(".input-field-heading").html(
						"<b>" + ui.item.label + " </b><a href=\""+ ui.item.value +"\" target=\"_blank\"><span class=\"octicon octicon-link-external\"></span></a>");
				$(this).siblings("select").css('display','none');
				$(this).css('display','none');
				emitResize();
				return false;
			},
			source : function(request, response) {	
				$.ajax({
					url : endpoint,
					dataType : "jsonp",
					data : {
						q : request.term,
					},
					success : function(data) {
						response(data);
					}
				});
			}
		});
	}
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
				enableAutocompletionEndpoints();
				enableSelect2();
				addGeonamesLookup();
				addGeonamesReverseLookup();
				addActionsToRemoveAndAddButtons();
				window.addEventListener("message", handleMessage, false);
				enableHelpOpenButtons();
				enableHelpCloseButtons();
				emitResize();
			},
			error : function(data, textStatus, jqXHR) {

			}
		});
	} else if(evt.data.action === 'sendReferrer'){
		var sourceUrl=evt.data.message;
		Cookies.set("cancel",sourceUrl);
	}
}
function destroyAutocompletion() {
	$('.search input').each(function() {
		$(this).autocomplete('destroy');
		$(this).removeData('autocomplete');
	});
}
function destroySelect2() {
	$('.custom-combobox').each(function() {
		$(this).select2('destroy');
	});
}

function resetIds() {
	$(document).find(".multi-fields").each(function(){
		$('li.multi-field',this).each(function(){
			var curIndex=$(this).index();
			fixIds($(this),curIndex);
		});
	});
}

function fixIds(elem, cntr) {
    $(elem).find("[name]").each(function() {
        $(this).attr("name",$(this).attr("name").replace(/\d+/, cntr));
    });
    $(elem).find("[id]").each(function() {
        $(this).attr("id",$(this).attr("id").replace(/\d+/, cntr));
    })
    
}

function addActionsToRemoveAndAddButtons() {
	addDatepicker();
	$('.multi-field-wrapper').each(function() {
		var $wrapper = $('.multi-fields', this);
		$('.multi-fields input', this).addClass("focus");
		$(".add-field", $(this)).click(function(e) {
			destroyAutocompletion();
			destroySelect2();
			var newField = $('.multi-field:first-child', $wrapper).clone(true);
			newField.appendTo($wrapper).find('.input-widget').val('').focus();
			newField.appendTo($wrapper).find('.search.input-widget').css('display','inline');
			newField.appendTo($wrapper).find('select').css('display','inline');

			newField.appendTo($wrapper).find('.custom-combobox').css('display','inline');
			newField.appendTo($wrapper).find('.help-text').css('display','none');
			newField.appendTo($wrapper).find('.form-control-label').css( 'visibility','hidden');
			newField.appendTo($wrapper).find('.inline-help').css('display','none');
			resetIds();
			$(newField).find(".input-field-heading").html("");
			enableAutocompletionEndpoints();
			enableSelect2();
			emitResize();
		});
		$('.multi-field .remove-field', $wrapper).click(function() {
			if ($('.multi-field', $wrapper).length > 1){
				$(this).parents('.multi-field').remove();
				resetIds();
				emitResize();
			}
			else{
				destroyAutocompletion();
				destroySelect2();
				var newField = $('.multi-field:first-child', $wrapper).clone(true);
				newField.appendTo($wrapper).find('.input-widget').val('').focus();
				resetIds();
				$(newField).find(".input-field-heading").html("");
				$(this).parents('.multi-field').remove();
				enableAutocompletionEndpoints();
				enableSelect2();
				emitResize();
			}
		});
		$('.multi-field .moveUp', $wrapper).click(function() {
			var $el = $(this).parents(".multi-field");
			if ($el.not(':first-child')) {
				$el.prev().before($el);
				resetIds();
			}
		});
		$('.multi-field .moveDown', $wrapper).click(function() {
			var $el = $(this).parents(".multi-field");
			if ($el.not(':last-child')) {
				$el.next().after($el);
				resetIds();
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
		var formType =$('#formType').text();
		target.postMessage({
			'action' : 'establishConnection',
			'message' : null,
			'topicId' : topicId,
			'documentId' : documentId,
			'formType' :formType
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

function resetHelpText(helpDiv){
	$('div',helpDiv).remove();
	helpDiv.css('display','none');
}
function enableHelpOpenButtons(){
	var helpTextUrl=$(document).find('#helpTextUrl').attr('href');
	$('.inline-help').on("click",function(){
		var fieldName = $(this).attr('name');
		var helpDiv=$(this).siblings('.help-text[name='+fieldName+']');
		$.ajax({
			type : 'GET',
			url : helpTextUrl,
			crossDomain : true,
			success : function(data, textStatus, jqXHR) {
				resetHelpText(helpDiv);
				var all = $('<div/>').html(data).contents();
				var text = $('#'+fieldName,$(all));
				if(typeof text.html() != 'undefined' ){
					var heading=$('h2',text);
					$('h2',text).replaceWith($('<b>' + heading.html() + '</b></br>'));
					helpDiv.append('<div>'+text.html()+'</div>');
				}else{
					var text = '<b>Noch kein Hilfetext verf&uuml;gbar! </b><br/> '+
					'Bitte navigieren sie zur <a href="'+helpTextUrl+'" target="blank"> Hilfeseite</a>'+
					', dr&uuml;cken Sie auf &quot;Bearbeiten&quot; und tragen Sie dort Ihren Text ein.'+
					' Damit der Text an dieser Stelle erscheint, rahmen Sie ihn bitte in folgendes'+
					' HTML-Markup ein:<br/><pre> &lt;div id=&quot;'+fieldName+'&quot;&gt; Hier kommt der Text hin! &lt;/div&gt;</pre>';
					helpDiv.append('<div>'+text+'</div>');
				}
			},
			error : function(data, textStatus, jqXHR) {
				resetHelpText(helpDiv);
				var text = '<b>Noch kein Hilfetext verf&uuml;gbar! </b><br/> '+
				'Bitte navigieren sie zur <a href="'+helpTextUrl+'" target="blank"> Hilfeseite</a>'+
				', dr&uuml;cken Sie auf &quot;Bearbeiten&quot; und tragen Sie dort Ihren Text ein.'+
				' Damit der Text an dieser Stelle erscheint, rahmen Sie ihn bitte in folgendes'+
				' HTML-Markup ein:<br/><pre> &lt;div id=&quot;'+fieldName+'&quot;&gt; Hier kommt der Text hin! &lt;/div&gt;</pre>';
				helpDiv.append('<div>'+text+'</div>');
			}
		}).done(function (){
			helpDiv.css("display","block");
			emitResize();
		});
	});
}
function enableHelpCloseButtons(){
	$('button.close-help').on("click",function(){
		var fieldName = $(this).attr('name');
		var helpDiv=$('.help-text[name='+fieldName+']');
		resetHelpText(helpDiv);
		emitResize();
	});
}

function addGeonamesLookup(){	
	$('#recordingLocation').after('<div id="geoSearchDiv"><input id="geoSearchQuery"></input><button type="button" id="geofind-button">find</button></div>');
	$('.input-widget.geonames-lookup').css('display','none');
	var findButton=$('#geofind-button');
	$('#geoSearchQuery').bind('keypress keydown keyup', function(e){
	      if(e.keyCode == 13) { e.preventDefault(); findButton.click();}
	});
	findButton.on("click",function(){
		var geoSearchQuery=$('#geoSearchQuery').val();
		var geoNamesUrl = "geoSearch?q="+geoSearchQuery;
		$.ajax({
			type : 'GET',
			url : geoNamesUrl,
			crossDomain : true,
			success : function(data, textStatus, jqXHR) {
				displayMap(data.geonames);
			},
			error : function(data, textStatus, jqXHR) {
				console.log(data);
			}
		}).done(function (){
			
		});
	}
	);
	emitResize();
}

function displayMap(geonamesArr){	
	$("#mapid").remove();
	$('#geoSearchDiv').append('<div id="mapid" style="height:180px"></div>');
	var i = 0;
	var mymap;
	$.each(geonamesArr,function(key,value){
		var lat = value.lat;
		var lng = value.lng;
		if(i==0){
			mymap=initMap(lat,lng);
		}
		L.marker([lat, lng]).addTo(mymap).on('click',function(e){
			$('input.geonames-lookup.focus').val("http://www.geonames.org/"+value.geonameId);
			$('input.geonames-lookup.focus').siblings(".input-field-heading").html(
					"<b>" + value.name + "  </b><a href=\"http://www.geonames.org/"+value.geonameId+"\" target=\"_blank\"><span class=\"octicon octicon-link-external\"></span></a>");
		}).on('mouseover',function(e){
			this.openPopup();
		}).on('mouseout', function (e) {
            this.closePopup();
        }).bindPopup(value.name);
		
		if(i==10){
			return false;
		}
		i++;
	});
	i=0;
	emitResize();
}

function initMap(lat,lng){
	L.map('mapid').remove();
	var mymap = L.map('mapid').setView([lat, lng], 13);
	L.tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
	    attribution: '&copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>',
	    maxZoom: 18
	}).addTo(mymap);
	return mymap;
}

function addGeonamesReverseLookup(){	
	$('#recordingCoordinates').after('<div id="geoReverseSearchDiv"><input id="geoReverseSearchQuery"></input><button type="button" id="georevfind-button">Open Map</button></div>');
	$('.input-widget.geonames-reverse-lookup').css('display','none');
	var findButton=$('#georevfind-button');
	$('#geoReverseSearchQuery').bind('keypress keydown keyup', function(e){
	      if(e.keyCode == 13) { e.preventDefault(); findButton.click();}
	});
	findButton.on("click",function(){
		var geoSearchQuery=$('#geoReverseSearchQuery').val();
		
		var array = geoSearchQuery.split(',');
         displayReverseMap(array[0],array[1]);	
	}
	);
	emitResize();
}

function displayReverseMap(lat,lng){	
	if (typeof(lat)==='undefined' || lat =="") lat = "50.94";
	if (typeof(lng)==='undefined') lng = "6.95";
	
	$("#revmapid").remove();
	$('#geoReverseSearchDiv').append('<div id="revmapid" style="height:180px"></div>');
	var mymap=initRevMap(lat,lng);
	var marker = new L.marker([lat, lng], {draggable:'true'});
	marker.addTo(mymap);
	marker.on('click',function(e){
			var position = marker.getLatLng();
			var link ="http://www.openstreetmap.org/?mlat="+position.lat+"&mlon="+position.lng;
			$('input.geonames-reverse-lookup.focus').val(link);
			$('input.geonames-reverse-lookup.focus').siblings(".input-field-heading").html(
					"<b>" + position.lat+","+position.lng+ "  </b><a href=\""+link+"\" target=\"_blank\"><span class=\"octicon octicon-link-external\"></span></a>");
		});
	marker.on('dragend', function(event){
            var marker = event.target;
            var position = marker.getLatLng();
            marker.setLatLng(position,{draggable:'true'}).bindPopup(position.lat+","+position.lng).update();
            $('#geoReverseSearchQuery').val(position.lat+","+position.lng);
		});
	marker.on('mouseover',function(e){
			this.openPopup();
		});
	marker.on('mouseout', function (e) {
            this.closePopup();
        });
	//marker.bindPopup(lat+","+lng);
	emitResize();
}

function initRevMap(lat,lng){
	L.map('revmapid').remove();
	var mymap = L.map('revmapid').setView([lat, lng], 13);
	L.tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
	    attribution: '&copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>',
	    maxZoom: 18
	}).addTo(mymap);
	return mymap;
}

function addActionToCancelButton(){
	if (top != self){
		setTimeout(function(){
		$("#cancel").click(function(){
			emitCancel();
		});},3000);
	}else{
		var sourceUrl=document.referrer;
		var targetUrl=decodeURIComponent(window.location.href);
		if( sourceUrl !== targetUrl){
			Cookies.set("cancel",document.referrer);
		}
		$("#cancel").click(function(){
			window.location.href = Cookies.get("cancel");
		});
	}
	
}

function emitCancel() {
	var target = parent.postMessage ? parent
			: (parent.document.postMessage ? parent.document : undefined);
	if (typeof target != "undefined") {
		postCancel(target);
	}
}

function postCancel(target) {
	var cookie=Cookies.get("cancel");
	target.postMessage({
		'action' : 'cancel',
		'message' : cookie
	}, '*');
}

function enableSelect2() {
	$('.custom-combobox').each(function() {
		$(this).select2();
	});
}