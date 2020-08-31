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