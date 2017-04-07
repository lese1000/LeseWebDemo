
/*
*通用的js
*/

//动态加载css或js文件
var dynamicLoading = {
    css: function (path, id) {
        if (!path || path.length === 0) {
            throw new Error('argument "path" is required !');
        }
        var head = document.getElementsByTagName('head')[0];
        var link = document.createElement('link');
        link.id = id;
        link.href = path;
        link.rel = 'stylesheet';
        link.type = 'text/css';
        head.appendChild(link);
    },
    js: function (path, callBack) {
        if (!path || path.length === 0) {
            throw new Error('argument "path" is required !');
        }
        var script = document.createElement("script");
        script.type = "text/javascript";
        if (callBack) {
            if (script.readyState) {  //IE
                script.onreadystatechange = function () {
                    if (script.readyState == "loaded" || script.readyState == "complete") {
                        script.onreadystatechange = null;
                        callBack();
                    }
                };
            } else {  //Others
                script.onload = function () {
                    callBack();
                };
            }
        }
        script.src = path;
        document.getElementsByTagName("html")[0].appendChild(script);
    }
};
// 设置Cookie
function setCookie(name, value) {
	var expires = (arguments.length > 2) ? arguments[2] : null;
	document.cookie = name + "=" + encodeURIComponent(value) + ((expires == null) ? "" : ("; expires=" + expires.toGMTString())) + ";path=" + "";
}

// 获取Cookie
function getCookie(name) {
	var value = document.cookie.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
	if (value != null) {
		return decodeURIComponent(value[2]);
    } else {
		return null;
	}
}

//删除cookie
function removeCookie(name) {
	var expires = new Date();
	expires.setTime(expires.getTime() - 1000 * 60);
	setCookie(name, "", expires);
} 

function isNotBlank(obj){
	if(obj!=null && obj!=""){
		return true;
	}else{
		return false;	
	}
}

function isBlank(obj){
	return !isNotBlank(obj);
}
function trim(str){ //删除左右两端的空格
	if(str==null || str==""){
		return "";
	}
    return str.replace(/(^\s*)|(\s*$)/g, "");
}

function getURLParam(name){
	var value="";
	//get search string
	var query=window.location.search.substring(1);
	var pairs=query.split("&");
	for(var i=0;i<pairs.length; i++){
		//is key/value?
        var pos = pairs[i].indexOf("=");
        if(pos==-1){
        	continue;
        }
        var key = pairs[i].substring(0,pos);
        if(key==name){
	        var value = pairs[i].substring(pos+1);
        	value =decodeURIComponent(value);
        	break;
        }
    }
    return value; 
};