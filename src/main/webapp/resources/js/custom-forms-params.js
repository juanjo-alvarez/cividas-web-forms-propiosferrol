if (window.addEventListener) {
	window.addEventListener("message", receiveSessionInfo, false);
} else {
	window.attachEvent("onmessage", receiveSessionInfo);
}

function receiveSessionInfo(evt) {
	if (evt.data.request == null) {
		// Esta comprobación no debería ser necesaria y es saltable, lo comento para que no dé problemas por falta de configuración de este script... 
//		if ((evt.origin != "https://10.7.0.185:8443" && evt.origin != "http://10.7.0.185:8080") && (evt.origin != "https://10.7.0.236:8443" && evt.origin != "http://10.7.0.236:8080") 
//				&& (evt.origin != "https://localhost:8443" && evt.origin != "http://localhost:8080")) {
//			console.log("Invalid origin, discard received data.");
//		} else {
			document.getElementById("received-message").value = evt.data;
			document.getElementById("sendParametersToBeanButton").click();
//		}

	}
	evt.stopPropagation();
	return;
}

function validateFieldsColor(){
	var form = document.getElementById("NewRegistryForm").querySelectorAll("[aria-required]");
	for(var i=0; i< form.length; i++){
		if(form[i].labels !=null){
			if(form[i].value ==""){
				form[i].labels[0].style.color = '#b94a48';
			}else{
				form[i].labels[0].style.color = '#005ea4';
			}
		}
	}
	if(document.getElementById('networknotification:0').checked =="" && document.getElementById('networknotification:1').checked ==""){
		document.getElementById("networknotificationlabel").style.color='#b94a48';
	}else {
		document.getElementById("networknotificationlabel").style.color='#005ea4';
	}
	if(document.getElementById("address")!=null && document.getElementById("address").disabled==false && document.getElementById("address").value==''){
		document.getElementById("addresslabel").style.color='#b94a48';
	}else{
		document.getElementById("addresslabel").style.color='#005ea4';
	}
		if(document.getElementById("manualaddressRepre")!=null && document.getElementById("address").disabled==false && document.getElementById("address").value==''){
		document.getElementById("addresslabelman").style.color='#b94a48';
	}else{
		document.getElementById("addresslabelman").style.color='#005ea4';
	}
	
	var checkboxs = document.getElementById("NewRegistryForm").querySelectorAll('input[type="checkbox"]');
	if(checkboxs!=null && checkboxs.length>0){
		for(var i=0; i< checkboxs.length; i++){
			if(checkboxs[i].id.search('j_id')==-1 && checkboxs[i].checked ==false){
				checkboxs[i].labels[0].style.color = '#b94a48';
			}else{
				checkboxs[i].labels[0].style.color = '#005ea4';
			}
		} 
	} 
}


