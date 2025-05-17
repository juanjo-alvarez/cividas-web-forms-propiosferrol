function showSignResultCallback(baseUri, idSolicitud, idAttachmentData, obs) {

	return function(signatureB64, certificateB64){

		$.ajax({
			url: document.location,
			method: "POST",
			transformRequest: urlEncodeObject,
			data: {
				action: "uploadSignature",
				"idSolicitud": idSolicitud,
				"idDocumento": idAttachmentData,
				"firma": signatureB64,
				"observaciones": obs,
			},
			headers: {
				"Content-Type": "application/x-www-form-urlencoded",
				"Accept": "application/json",
			},
			success: function(data){
				PF('statusDialog').hide();
				if(data.code == 1){
					if(data.message2 != undefined){
						alert(data.message2);
					}else if(data.message != undefined){
						alert(data.message);
					}
				}else{
					if(data && data.data && data.data[0] && data.data[0].operationinfo === "[SIGNATURE_OPERATION_SUCESSFULLY]"){
						window.location.href = window.location.href.substring(start,window.location.href.lastIndexOf('signature/'))+"signature/signature-success.xhtml?idsolicitud="+idSolicitud+"&idattachmentdata="+data.data[0].idattachmentdata.replace("[","").replace("]","");
					} else {
						console.error("Bad result", data);
					}
				}
			},
			error: function(error, err, xhr){
				console.log(error, err, xhr);
				PF('statusDialog').hide();
				alert("Ocorreu un erro o mandar o documento firmado. Por favor intentao de novo.");
			}
		});

	}
}

function showErrorCallback(errorType, errorMessage){
	console.error(errorType, errorMessage);
	PF('statusDialog').hide();
	PF('dialogError').show();
	if(errorMessage){
		$("#textSignError").show();
		$("#textSignError").html(errorMessage);
	} else {
		$("#textSignError").hide();
		$("#textSignError").html("Ocorreu un erro no proceso de firma con Autofirma.");
	}
}

function downloadedSuccessCallback(baseUri, idSolicitud, idAttachmentData, obs, user, sessionId) {

	return function(data) {
		try {
			getSignaturePositionInfo(data, baseUri, idSolicitud, idAttachmentData, obs, user, sessionId);
		} catch(e) {
			try {
				console.error("Type: " + MiniApplet.getErrorType() + "\nMessage: " + MiniApplet.getErrorMessage(), e);
			} catch(ex) {
				console.error("Error: " + e);
			}
		}
	}
}

function getSignaturePositionInfo(previousData, baseUri, idSolicitud, idAttachmentData, obs, user, sessionId) {
	try {
		PF('statusDialog').getJQ().find(".ui-dialog-title").text("Obtendo información da firma...");
		$.ajax({
			url: document.location,
			method: "POST",
			headers: {
				"Content-Type": "application/x-www-form-urlencoded",
				"Accept": "application/json",
			},
			transformRequest: urlEncodeObject,
			data: {
				action: "getSignaturePositionInfo",
				idSolicitud: idSolicitud,
				idAttachmentData: idAttachmentData,
				user: user,
				sessionid: sessionId,
			},
			success: function(data){
				var params =  "";
				if(data.code == 0){
					if(data.data[0] != undefined){
						params = "signaturePositionOnPageLowerLeftX=" + data.data[0].left +
						"\nsignaturePositionOnPageLowerLeftY="+ data.data[0].bottom +
						"\nsignaturePositionOnPageUpperRightX="+ data.data[0].right +
						"\nsignaturePositionOnPageUpperRightY="+ data.data[0].top +
						"\nlayer2Text=" + data.data[0].layer2Text +
						"\nsignaturePage=" + data.data[0].signaturePage +
						"\nlayer2FontFamily=1" +
						"\nlayer2FontSize=" + data.data[0].layer2FontSize + "\n";
					}
					PF('statusDialog').getJQ().find(".ui-dialog-title").text("Firmando...");
					AutoScript.cargarAppAfirma();
					MiniApplet.sign(
						(previousData != undefined && previousData != null && previousData != "") ? previousData : null,
						"SHA256withRSA",
						"Adobe PDF",
						params,
						showSignResultCallback(baseUri, idSolicitud, idAttachmentData, obs),
						showErrorCallback);
				}else{
					PF('statusDialog').hide();
					if(data.message != null && data.message != undefined){
						if(data.message != "DOCUMENT_IS_ALREADY_IN_SIGN_PROCESS"){
							alert(data.message);
						}else{
							alert("Ocorreu un erro, por favor intentalo de novo.");
						}
					}else{
						alert("Ocorreu un erro, por favor intentalo de novo.");
					}

				}
			},
			error: function(error, err, xhr){
				console.log(error, err, xhr);
				PF('statusDialog').hide();
			}
		});
	} catch(e) {
		console.error("Error en la descarga de los datos: " + e);
		PF('statusDialog').hide();
	}
}

function downloadedErrorCallback(err){
	console.log(err);
	console.error("error", err);
	PF('statusDialog').hide();
}

function downloadAndSign(baseUri, idSolicitud, idAttachmentData, obs, user, sessionId) {
	try {

		PF('statusDialog').show()
		PF('statusDialog').getJQ().find(".ui-dialog-title").text("Obtendo documento...");
		MiniApplet.downloadRemoteData(
			document.location + "&dl=1",
			downloadedSuccessCallback(baseUri, idSolicitud, idAttachmentData, obs, user, sessionId),
			downloadedErrorCallback);
	} catch(e) {
		console.error("Error en la descarga de los datos: " + e);
		PF('statusDialog').hide();
	}
}

function urlEncodeObject(obj) {
	var str = [];
	for(var p in obj)
	str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
	return str.join("&");
}

function rejectSign(baseUri, idSolicitud, observacionesRechazo, user, sessionId) {
	try {

		if(!observacionesRechazo || !observacionesRechazo.trim()){
			console.error("observacionesRechazo es obligatorio");
			return;
		}

		PF('statusDialog').show();
		PF('statusDialog').getJQ().find(".ui-dialog-title").text("Rexeitando...");
		$.ajax({
			url: document.location,
			method: "POST",
			headers: {
				"Content-Type": "application/x-www-form-urlencoded",
				"Accept": "application/json",
			},
			transformRequest: urlEncodeObject,
			data: {
				action: "rejectSignature",
				signaturesIds: JSON.stringify([idSolicitud]),
				rejectObservations: observacionesRechazo,
				user: user,
				sessionid: sessionId,
			},
			success: function(data){
				PF('statusDialog').hide();
				if(data.code == 1){
					alert(data.message);
				}else{
					PF('dialogReject').hide();
					if(data && data.data && data.data[0]){
						window.location.href = window.location.href.substring(start,window.location.href.lastIndexOf('signature/'))+"signature/signature-reject-success.xhtml?idsolicitud="+idSolicitud+"&idattachmentdata="+data.data[0].idattachmentdata;
					}
				}
			},
			error: function(error, err, xhr){
				console.log(error, err, xhr);
				PF('statusDialog').hide();
			}
		});
	} catch(e) {
		console.error("Error en la descarga de los datos: " + e);
		PF('statusDialog').hide();
	}
}

//Firmado en lote para documentos tipados
var documentsSigned = [];
var resumenSigned = [];
var numDocs = 0;
var timeoutDuration = 1000;

function checkDocuments() {

	let documentsSize = document.getElementById("filesB64:uploadedFilesSize").value;
	let documentsBase64 = arrayDocuments(document.getElementById("filesB64:filesBase64").value);

	let numDocsCheck = documentsBase64[0] == '' ? 99999 : documentsBase64.length;

	return documentsSize == numDocsCheck;
}

function signDocuments() {
	try {
		PF('confirmdoc').getJQ().find(".ui-dialog-title").text("Obtendo información da firma...");

		updateFilesBase64();

		function checkAndExecute() {
			if (checkDocuments()) {
				PF('confirmdoc').getJQ().find(".ui-dialog-title").text("Firmando...");
				AutoScript.cargarAppAfirma();
				MiniApplet.setStickySignatory(true);
				let documentsBase64 = arrayDocuments(document.getElementById("filesB64:filesBase64").value);
				numDocs = documentsBase64.length;
				MiniApplet.sign(
					documentsBase64[0],
					"SHA256withRSA",
					"Adobe PDF",
					"",
					showBatchSignResultCallback(documentsBase64),
					showBatchErrorCallback
				);
			} else {
				// Reintentar después de 1 segundo (1000 ms)
				setTimeout(checkAndExecute, 1000);
			}
		}

		checkAndExecute();

	} catch(e) {
		console.error("Error en la descarga de los datos: " + e);
		PF('confirmdoc').hide();
		disbaleOverlay();
	}
}

function calculateSHA1(base64String) {
	var words = CryptoJS.enc.Base64.parse(base64String);
	var hash = CryptoJS.SHA1(words);
	var result = CryptoJS.enc.Hex.stringify(hash);
	return result;
}

var contadorReplaces = 0;

function showBatchSignResultCallback(documentsBase64) {
	return function(signatureB64, certificateB64){
		documentsSigned.push(signatureB64);
		documentsBase64.shift();
		if(documentsBase64.length>0){
			let params =  "";
			MiniApplet.sign(
				documentsBase64[0],
				"SHA256withRSA",
				"Adobe PDF",
				params,
				showBatchSignResultCallback(documentsBase64),
				showBatchErrorCallback);
		}else{
			if(documentsSigned.length == numDocs){

				var table = document.getElementById("finalList");
				if (table) {
					// Selecciona todas las filas del cuerpo de la tabla
					var rows = table.getElementsByTagName("tbody")[0].getElementsByTagName("tr");

					documentsSigned.forEach(function(filecontent) {
						var hash = calculateSHA1(filecontent);
						var cells = rows[contadorReplaces].getElementsByTagName("td");

						if (cells.length > 0) {
							cells[1].innerText = hash;
						}

						var signedFilesInputBase64 = document.getElementById("filesBase64Signed");

						if (signedFilesInputBase64.value != '') {
							signedFilesInputBase64.value = signedFilesInputBase64.value + "," + filecontent;
						} else {
							// Si es el primer valor, simplemente asignalo
							signedFilesInputBase64.value = filecontent;
						}
						contadorReplaces++;
					});
				} else {
					console.log("No se ha encontrado la tabla para pintar los hash");
				}
				createDiv();

			}else{
				showBatchErrorCallback("ERROR","Error al intentar acceder a los documentos")
				PF('confirmdoc').hide();
				disbaleOverlay();
			}
		}
	}
}

let signedFiles = {};

function createDiv() {
	setTimeout(function() {
		PF('confirmdoc').hide();
		const tableRows = $('#divFinalList tbody tr');

		tableRows.each(function() {
			const filename = $(this).find('td:first').text();
			const hash = $(this).find('td:last').text();
            if (!filename.includes("Non se atoparon resultados.") && !signedFiles[hash]) {
				signedFiles[hash] = hash;
				timeoutDuration = 1000;
			}
		});
		
		const signedFileCount = Object.keys(signedFiles).length;
        if (signedFileCount === numDocs && contadorReplaces === numDocs) {
			$("#divFinalList").show();
			disbaleOverlay();
            generateResume('divresume');
		} else {
			timeoutDuration *= 2;
			createDiv();
		}
	}, timeoutDuration);
}

function generateResume(div) {
    let element = document.getElementById(div);
    let file = "";
    var opt = {
        margin: 70,
        filename:     'solicitude-asinada.pdf',
        image: { type: 'jpeg', quality: 1 },
        html2canvas: { scale: 2, width: 650, scrollY: 0 },
        jsPDF: { unit: 'pt', format: 'a4', orientation: 'portrait' }
    };
    // Generar el PDF y firmarlo
    html2pdf().set(opt).from(element).outputPdf().then((p) => {
        file += btoa(p);
        resumenSigned.push(file);
        signResume();
    });
}

function signResume() {
	enableOverlay()
	setTimeout(function() {
		try {
			let resumeDocument = resumenSigned[0];
			AutoScript.cargarAppAfirma();
			MiniApplet.setStickySignatory(true);
			MiniApplet.sign(
				resumeDocument,
				"SHA256withRSA",
				"Adobe PDF",
				"",
				showResumeSignResultCallback(resumeDocument),
				showBatchErrorCallback
			);
		} catch(e) {
			console.error("Error en la descarga de los datos: " + e);
			PF('confirmdoc').hide();
			disbaleOverlay();
		}
	}, 1000)

}


var count = 0
function showResumeSignResultCallback(resumeDocument) {
	return function(signatureB64, certificateB64){
		if(count === 0){
			resumenSigned.push(signatureB64);
			count++
			let params =  "";
			MiniApplet.sign(
				resumeDocument,
				"SHA256withRSA",
				"Adobe PDF",
				params,
				showResumeSignResultCallback(resumeDocument),
				showBatchErrorCallback
			);
		} else {
			var resumeFinal = resumenSigned[1];
            var inputField = document.getElementById("resumeBase64Signed");
            inputField.value = resumeFinal;
			setTimeout(function() {
				disbaleOverlay();
                $('#hiddenSendButton').click();
			}, 1000)
		}
	};
}


function showBatchErrorCallback(errorType, errorMessage){
	console.error(errorType, errorMessage);
	PF('confirmdoc').hide();
	disbaleOverlay();
	PF('dialogError').show();
	if(errorMessage){
		$("#textSignError").show();
		$("#textSignError").html(errorMessage);
	} else {
		$("#textSignError").hide();
		$("#textSignError").html("Ocorreu un erro no proceso de firma con Autofirma.");
	}
}

function arrayDocuments(files){
	let documents = files.substring(1, files.length-1);
	let documentsArray = documents.split(", ");
	return documentsArray;
}

function isValid(){
	let valido = true;
	$("#NewRegistryForm input[aria-required='true']").each(function() {
		if(valido && $( this ).val()==""){
			valido=false;
		}
	});
	if(!valido){
		addmessageerror('Es necesario cubrir todos los campos obligatorios.');
	}
	return valido;
}

function addmessageerror(messagetxt){
	message=document.getElementById("NewRegistryForm:messages")
	if(!$('#NewRegistryForm\\:messages').find('div.ui-messages-error ul').length){
		messagediv=document.createElement("div");
		messagediv.setAttribute('class', 'ui-messages-error ui-corner-all');
		messageul=document.createElement("ul");
		messagediv.appendChild(messageul);
		message.appendChild(messagediv);
	}else{
		messageul=message.querySelector("ul");
	}
	newli = document.createElement("li");
	newspan = document.createElement("span");
	newspan.setAttribute('class', 'ui-messages-error-summary');
	newspan.innerHTML = messagetxt;
	newli.appendChild(newspan);
	messageul.appendChild(newli);
}

function enableOverlay() {
	document.getElementById("gifprocesando").style.display = "block";
	document.getElementById("opacidadfondo").style.display = "block";
}

function disbaleOverlay() {
	document.getElementById("gifprocesando").style.display = "none";
	document.getElementById("opacidadfondo").style.display = "none";
}

function validateBeforeConfirm() {
	var signActive = (singactive === 'true');

	if (!signActive) {
		PF('confirmdoc').show();
		return;
	}
		var missingTextFields = false;
		var missingCheckboxes = false;
		var missingAddress = false;
		var missingProvince = false;
		var missingTown = false;
		var missingIssue = false;
		var missingExpose = false;
		var missingAsk = false;
		var invalidEmail = false;
		var missingIdtaxreport = false;
		var missingTax = false;
		var missingMandatory = false;
		var errorMessage = "";
		var mandatoryValidation = $('#mandatoryValidation')
		var fullAddress = $('#fulladdress')
		var provinceSelect = $('#province');
		var townSelect = $('#town');
		var addressField = $('#address');
		var issueField = $('#asunto');
		var exposeField = $('#expongo');
		var askField = $('#solicito');
		var emailField = $('#email');
		var isTelematicSelected = $('#networknotification\\:0').is(':checked');
		var idtaxreport = $("div[id$='idtaxreport'] select");
		var noTaxes = $('#noTaxes');

		if (idtaxreport.val() === "") {
			missingIdtaxreport = true;
		}

		if (noTaxes.val() === "" || noTaxes.val() === null){
			missingTax = true
		}

		if (mandatoryValidation.val() === "") {
			missingMandatory = true;
		}

		// Validación genérica para checkbox
		$('#NewRegistryForm [aria-required="true"]').each(function() {
			if ($(this).is(':checkbox') && !$(this).is(':checked')) {
				missingCheckboxes = true;
			}
		});

		$('#NewRegistryForm textarea').each(function() {
			var textareaId = $(this).attr('id');
			if (validateField(textareaId)) {
				missingTextFields = true;
			}
		});

		// Comprobación específica para el campo de dirección
		if (fullAddress.val() === '0' && addressField.length > 0 && !addressField.val().trim()) {
			missingAddress = true;
		}

		// Comprobación específica para el campo de provincias
		if (fullAddress.val() === '0' && provinceSelect.val() === "") {
			missingProvince = true;
		}

		// Comprobación específica para el campo de población
		if (fullAddress.val() === '0' && townSelect.val() === "") {
			missingTown = true;
		}

		if (issueField.val() === "") {
			missingIssue = true;
		}

		if (exposeField.val() === "") {
			missingExpose = true;
		}

		if (askField.val() === "") {
			missingAsk = true;
		}

		// Validación del email solo si la opción telemática está seleccionada
		if (isTelematicSelected && (emailField.val().trim() === '' || !emailField.val().match(/^[^@]+@[^@]+\.[^@]+$/))) {
			invalidEmail = true;
		}

		// Construir el mensaje de error basado en los campos que faltan
		if (missingIdtaxreport) errorMessage += (errorMessage ? "<br>" : "") + i18n_missing_idtaxreport;
		if (missingTax) errorMessage += (errorMessage ? "<br>" : "") + i18n_missing_tax;
		if (missingMandatory) errorMessage += (errorMessage ? "<br>" : "") + i18n_mandatory_documents;
		if (missingTextFields) errorMessage += (errorMessage ? "<br>" : "") + i18n_missing_text;
		if (missingCheckboxes) errorMessage += (errorMessage ? "<br>" : "") + i18n_missing_check;
		if (missingAddress) errorMessage += (errorMessage ? "<br>" : "") + i18n_missing_address;
		if (missingProvince) errorMessage += (errorMessage ? "<br>" : "") + i18n_missing_province;
		if (missingTown) errorMessage += (errorMessage ? "<br>" : "") + i18n_missing_town;
		if (missingIssue) errorMessage += (errorMessage ? "<br>" : "") + i18n_missing_issue;
		if (missingExpose) errorMessage += (errorMessage ? "<br>" : "") + i18n_missing_expose;
		if (missingAsk) errorMessage += (errorMessage ? "<br>" : "") + i18n_missing_ask;
		if (invalidEmail) errorMessage += (errorMessage ? "<br>" : "") + i18n_wrong_email;

		// Mostrar el diálogo de validación o el diálogo de confirmación
		if (errorMessage) {
			updateAndShowDialog(errorMessage);
		} else {
			PF('confirmdoc').show();
		}
}

function updateAndShowDialog(message) {
    $('#validationMessages').html(message);
    PF('validationDialog').show();
}

function validateField(fieldid) {
	var textArea = document.getElementById(fieldid);
	if (!textArea.disabled && textArea.required) {
		return textArea.value.trim() === '';
	}
	return false;
}


