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

/* 파일 크기 변환 */
function convertFileSize(fileSize) {
    var retFormat = "0";
    var s = ['bytes', 'KB', 'MB', 'GB', 'TB', 'PB'];
    var e = Math.floor(Math.log(fileSize) / Math.log(1024));

    if (fileSize != 0) {
        retFormat = (fileSize / Math.pow(1024, e)).toFixed(2) + " " + s[e];
    } else {
        retFormat = fileSize + " " + s[0];
    }

    return retFormat;
};

/* 카멜 케이스 변수를 출력에 적합한 문자열로 변환
* activeStatus -> active status
* */
function camelCaseToTitle(str) {
    return str.replace(/([A-Z])/g, function (arg) {
        return " " + arg.toLowerCase();
    });
}
