function loadCode(){
	$("#verifycode").attr("src","verify?common=login&_=" + Math.random());
}

$(function(){
		loadCode();
})
