sendHeight = function() {
	var body = document.body, html = document.documentElement;
	var height = Math.max(body.offsetHeight, html.offsetHeight);
	window.parent.postMessage({
		"height" : height
	}, "*")
}
$(window).on('resize', sendHeight);




function fullAddressChange() {
	if ($('#fulladdress option:selected').attr("value") == "0") {
		if ($('#addressFields').is(":hidden")) {
			$('#addressFields').slideDown(50);
		}
	} else {
		$('#addressFields').hide();
	}
	sendHeight();
};

function fullRepreAddressChange() {
	if ($('#fullRepreaddress option:selected').attr("value") == "0") {
		if ($('#addressRepreFields').is(":hidden")) {
			$('#addressRepreFields').slideDown(50);
		}
	} else {
		$('#addressRepreFields').hide();
	}
	sendHeight();
};

var enableRegistrySubmitButton = function(enabled) {

	var buttons = $('#send');

	buttons[0].disabled = !enabled;
}

function disable(formId) {
    var inputs = document.getElementById(formId).getElementsByTagName("input");
    for (i = 0; i < inputs.length; i++) {
        inputs[i].disabled = true;
    }
    
    var textareas = document.getElementById(formId).getElementsByTagName("textarea");
    for (i = 0; i < textareas.length; i++) {
    	textareas[i].disabled = true;
    }
    
    for (item in PrimeFaces.widgets) {
        widget = PrimeFaces.widgets[item];
        if (widget instanceof PrimeFaces.widget.SelectOneMenu && widget.id.startsWith(formId)) {
            widget.disable();
        }
    }
}

function enable(formId) {
    var inputs = document.getElementById(formId).getElementsByTagName("input");
    for (i = 0; i < inputs.length; i++) {
        inputs[i].disabled = false;
    }
    for (item in PrimeFaces.widgets) {
        widget = PrimeFaces.widgets[item];
        if (widget instanceof PrimeFaces.widget.SelectOneMenu && widget.id.startsWith(formId)) {
            widget.enable();
        }
    }
}

function enableOverlay() {
	document.getElementById("gifprocesando").style.display = "block";
	document.getElementById("opacidadfondo").style.display = "block";
}

function disbaleOverlay() {
	document.getElementById("gifprocesando").style.display = "none";
	document.getElementById("opacidadfondo").style.display = "none";
}



function deletevalidation(){
	document.getElementById("mandatoryValidation").value="";
}

function deletefield(campo){
	let padre;
	let hermano;
	padre = campo.closest('li.ui-datalist-item');
	hermano = padre.querySelector("input");
	hermano.value='';
}

function expodetail() {
  var textarea = document.getElementById(expongoDetail)
    if ($('#expongoOption input:checked').val() == 0 ||$('#expongoOption input:checked').val() == 3) {
      expongoDetail.disabled = false
      expongoDetail.required = true
    } else {
      expongoDetail.disabled = true
      expongoDetail.required = false
      expongoDetail.value = ''
    }
}

function detail() {
  var textarea = document.getElementById(expongoDetail)
  if ($('#expongoOption input:checked').val() == 4 ||$('#expongoOption input:checked').val() == 5) {
    expongoDetail.disabled = false
    expongoDetail.required = true
  } else {
    expongoDetail.disabled = true
    expongoDetail.required = false
    expongoDetail.value = ''
  }
}

function fullmanualRepreAddressChange() {
	if ($('#representantemanual input[type=checkbox]:checked').length == "1") {
		if ($('#manualRepreFields').is(":hidden")) {
			$('#manualRepreFields').slideDown(90);
		}
		if ($('#docrepresentanteaux1').is(":hidden")) {
			$('#docrepresentanteaux1').slideDown(400);
			$('#docrepresentante').prop('required', true);
		}
		if ($('#docrepresentanteaux2').is(":hidden")) {
			$('#docrepresentanteaux2').slideDown(400);

		}
	} else if ($('#secondinterested input:checked').val() == "1" || $('#secondinterested input:checked').val() == "2") {
		if ($('#manualRepreFields').is(":hidden")) {
			$('#manualRepreFields').slideDown(90);
		}
		if ($('#docrepresentanteaux1').is(":hidden")) {
			$('#docrepresentanteaux1').slideDown(400);
			$('#docrepresentante').prop('required', true);
		}
		if ($('#docrepresentanteaux2').is(":hidden")) {
			$('#docrepresentanteaux2').slideDown(400);

		}
	} else {
		$('#manualRepreFields').hide();
		$('#docrepresentanteaux1').hide();
		$('#docrepresentanteaux2').hide();
	}
	sendHeight();
};




function openPublicationDialog(publicationId, title){

	$("#modal-iframe-src").attr(
			'src',
			"#{request.contextPath}/public/publications/publication-details-iframe.xhtml?idregulation="+publicationId
	);

	$("#modal-iframe-src").load(function(){
		$("#modal-iframe-src").css(
				'width',
				"100%"
		);

		$("#modal-iframe-src").css(
				'box-sizing',
				"border-box"
		);

		$("#modal-iframe-src").css(
				'-moz-box-sizing',
				"border-box"
		);
	});

	dialog = $("#modal-iframe-src").dialog({
		title: title,
		autoOpen: false,
		height: Math.min(Math.floor($(window).height()*90/100), 590),
		width: Math.min(Math.floor($(window).width()*80/100), 740),
		modal: true,
		resizable: true,
		open: function(event, ui) {
			$("body").css({ overflow: 'hidden' });
			$('.ui-dialog').css('z-index',103);
			$('.ui-widget-overlay').css('z-index',102);
		},
		beforeClose: function(event, ui) {
		 $("body").css({ overflow: 'inherit' })
		}
	});

	dialog.dialog('open');

}

function secondinterestedChange() {
	if ($('#secondinterested input:checked').val() == "1" || $('#secondinterested input:checked').val() == "2") {
		if ($('#manualRepreFields').is(":hidden")) {
			$('#manualRepreFields').slideDown(50);
		}
	} else {
		$('#manualRepreFields').hide();
	}
	sendHeight();
}

function checkSecondInterestedUnselect() {
	if ($('#secondinterested input[type=checkbox]:checked').length != "1") {
		$('#manualRepreFields').hide();
		$('#docrepresentanteaux1').hide();
		$('#docrepresentanteaux2').hide();
	}
}

function handleCheckboxChange(checkboxId, inputId) {
    var isChecked = document.getElementById(checkboxId).checked

    var inputText = document.getElementById(inputId)

    if (!isChecked) {
        inputText.disabled = true
    } else {
        inputText.disabled = false
    }
}





