(function(name, module, context){
	if(window.define)
		define([], module);
	else
		context.auth = module();
})("login", function(){
	
	var frmEle = document.getElementById("loginFrm");
	
	function _login(){
		frmEle.submit();
	};
	
	return {
		"login": function(){
			_login();
		}
	}
}, window);