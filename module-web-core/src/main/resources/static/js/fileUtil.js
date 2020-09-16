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
