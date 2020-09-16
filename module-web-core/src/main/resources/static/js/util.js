/* URI 파라미터 반환 */
function getUriParams() {
    var params = {};
    window.location.search.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(str, key, value) { params[key] = value; });
    return params;
}

/* URI 생성 */
function makeGetUri(uri, params) {
    Object.keys(params).forEach(function(key, index) {
        uri += (index === 0 ? "?" : "&") + key + "=" + params[key];
    });

    return uri;
}

/* 객체 empty 여부 반환 */
function isEmpty(obj) {
    for(var prop in obj) {
        if(obj.hasOwnProperty(prop))
            return false;
    }

    return true;
}

/* 첫 글자만 대문자 */
function capitalize(str) {
    str = str.toLowerCase();
    return str.charAt(0).toUpperCase() + str.slice(1);
}