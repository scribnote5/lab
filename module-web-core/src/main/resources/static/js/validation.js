/* 공백 및 공란 검사 */
function validateByWhiteSpace(str) {
    if (str.trim().length == 0) {
        return false;
    } else {
        return true;
    }
}

/* 공백 문자(글자 내 공백 허용) 및 공란 검사 */
function validateByEmpty(str) {
    if (str.search(/^\s+|\s+$/g) != -1 || str.length == 0) {
        return true;
    } else {
        return false;
    }
}

/* 특수 문자 검사 */
function validateBySpecialChar(str) {
    var regExp = /[`~!@#$%^&*|\\\'\";:\/?]/gi;
    if (regExp.test(str) == true) {
        return true;
    } else {
        return false;
    }
}

/* 바이트 수 반환 */
function getByteSize(el) {
    var codeByte = 0;
    for (var idx = 0; idx < el.length; idx++) {
        var oneChar = escape(el.charAt(idx));
        if (oneChar.length == 1) {
            codeByte++;
        } else if (oneChar.indexOf("%u") != -1) {
            codeByte += 2;
        } else if (oneChar.indexOf("%") != -1) {
            codeByte++;
        }
    }
    return codeByte;
}

/* ID 검사: 영어 소문자와 숫자 검사 */
function validateById(str) {
    var regexp = /^[a-z]+[a-z0-9]{3,19}$/g;
    if (regexp.test(str) == true) {
        return true;
    } else {
        Alert.fire({
            icon: "error",
            text: "The ID must be only lower case letters and numbers are available, more than 4 characters and less than 16 characters." +
                "\n(Number of characters currently entered: " + document.getElementsByName("username")[0].value.length + ")."
        }).then((result) => {
            document.getElementsByName("username")[0].focus();
            document.getElementsByName("username")[0].value = "";
            document.getElementById("usernameCheckResult").innerHTML = "";
        })

        return false;
    }
}


/* input tag validation - 문자열 길이 */
function validateByLength(inputName, maxStrLength, title) {
    var strLength = document.getElementsByName(inputName)[0].value.length;

    if (strLength > maxStrLength) {
        Alert.fire({
            icon: "error",
            text: "The " + title + " is up to " + maxStrLength + " characters long." +
                "\n(Number of characters currently entered: " + strLength + ")",
        })
        document.getElementsByName(inputName)[0].focus();

        return false;
    } else if (!validateByWhiteSpace(document.getElementsByName(inputName)[0].value)) {
        Alert.fire({
            icon: "error",
            text: "The " + title + " must not be blank.",
        })
        document.getElementsByName(inputName)[0].focus();

        return false;
    } else {
        return true;
    }
}

/* input tag validation - 공백을 제외한 문자열 */
function validateByBlank(inputName, title) {
    if (!validateByWhiteSpace(document.getElementsByName(inputName)[0].value)) {
        Alert.fire({
            icon: "error",
            text: "The " + title + " must not be blank.",
        })
        document.getElementsByName(inputName)[0].focus();

        return false;
    } else {
        return true;
    }
}

/* input tag validation - 문자열 크기 */
function validateBySize(inputName, maxByteSize, title) {
    var byteSize = getByteSize(document.getElementsByName(inputName)[0].value);

    if (byteSize > maxByteSize) {
        Alert.fire({
            icon: "error",
            text: "The " + title + "is up to " + maxByteSize + " bytes size." +
                "\n(Size of characters currently entered: " + byteSize + " bytes)."
        })
        document.getElementsByName(inputName)[0].focus();

        return false;
    } else {
        return true;
    }
}

/* select tag validation - inactive or delete option인지 확인 */
function validateBySelect(inputName, title) {
    var target = document.getElementsByName(inputName)[0];

    if (target.options[target.selectedIndex].value == -1) {
        Alert.fire({
            icon: "error",
            text: "The " + title + " is inactive or deleted." +
                "\nPlease check validate " + title + ".",
        })

        return false;
    } else {
        return true;
    }
}

/* external validation - 외부 조건에 따른 결과 출력 */
function validateByExternal(inputName, title, result) {
    if (result) {
        Alert.fire({
            icon: "error",
            text: "Please check " + title + ".",
        })
        document.getElementsByName(inputName)[0].focus();

        return false;
    } else {
        return true;
    }
}

// 배열 요소가 NULL인 경우를 제외하여 배열 길이를 계산함
function deleteArrayIndexIsNull() {
    insertFileArrayLength = insertFileArray.filter(function (item) {
        return item !== null && item !== undefined && item !== '';
    }).length;

    deleteFileArrayLength = deleteFileArray.filter(function (item) {
        return item !== null && item !== undefined && item !== '';
    }).length;
}

/* 파일 validation - 파일 존재 여부 */
function validateByFileExist() {
    // 배열 요소가 null인 경우를 제외하여 배열 길이를 계산함
    deleteArrayIndexIsNull();

    if (uploadedAttachedFileLength + insertFileArrayLength - deleteFileArrayLength !== 1) {
        Alert.fire({
            icon: "error",
            text: "The upload file does not exist." +
                "\nPlease upload file.",
        })

        return false;
    } else {
        return true;
    }
}

// 업로드하는 파일 개수가 number가 넘어가는 경우, 파일 업로드 불가
function validateByFileNumber(files, number) {
    // 배열 요소가 NULL인 경우를 제외하여 배열 길이를 계산함
    deleteArrayIndexIsNull();

    if (files.length > number || (uploadedAttachedFileLength - insertFileArrayLength - deleteFileArrayLength !== 0)) {
        Alert.fire({
            icon: "error",
            text: "The number of file that must be uploaded is 1."
        })

        return false;
    } else {
        return true;
    }
}

/*
 * 파일 validation - 필수 확장자
 *
 * [전역 변수 선언 필요]
 * var totalFileSize = 0;
 */
function validateImageFile(file) {
    // file validation - 필수 확장자
    var includeArray = [".jpg", ".jpeg", ".png", ".tif", ".tiff"];
    // 파일 이름
    var fileName = file.name;
    // 파일 확장자명(대문자를 소문자로 변경)
    var extensionName = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
    // 필수 확장자명 사용 여부 판단
    var result = false;
    // 첨부 파일 크기
    var fileSize = file.size;
    // 업로드 가능한 파일 크기: 20 MB
    var maxSize = 20 * 1024 * 1024;

    // 첨부 파일이 있는 경우
    if (fileName != "") {
        /* 확장자명 검사 */
        for (var j = 0; j < includeArray.length; j++) {
            if (extensionName == includeArray[j]) {
                result = true;
                break;
            }
        }

        if (!result) {
            Alert.fire({
                icon: "error",
                text: "The attached file only uses [" + includeArray.join(', ') + "] extension. "
            })
            $("#file").replaceWith($("#file").clone(true));
            $("#file").val('');

            return false;
        }

        /* 파일 크기 검사 */
        if (fileSize > maxSize) {
            Alert.fire({
                icon: "error",
                text: "The attached file can upload within 20 MB size."
            })
            $("#file").replaceWith($("#file").clone(true));
            $("#file").val('');

            return false;
        }

        /* 모든 파일 크기 검사 */
        if (fileSize + totalFileSize > maxSize) {
            Alert.fire({
                icon: "error",
                text: "All attached files must be within 20 MB size."
            })
            $("#file").replaceWith($("#file").clone(true));
            $("#file").val('');

            return false;
        }

        totalFileSize += fileSize;
    }

    return true;
}

/*
 * 파일 validation - 필수 확장자
 *
 * [전역 변수 선언 필요]
 * var totalFileSize = 0;
 */
function validatePdfFile(file) {
    // file validation - 필수 확장자
    var includeArray = [".pdf"];
    // 파일 이름
    var fileName = file.name;
    // 파일 확장자명(대문자를 소문자로 변경)
    var extensionName = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
    // 필수 확장자명 사용 여부 판단
    var result = false;
    // 첨부 파일 크기
    var fileSize = file.size;
    // 업로드 가능한 파일 크기: 20 MB
    var maxSize = 20 * 1024 * 1024;

    // 첨부 파일이 있는 경우
    if (fileName != "") {
        /* 확장자명 검사 */
        for (var j = 0; j < includeArray.length; j++) {
            if (extensionName == includeArray[j]) {
                result = true;
                break;
            }
        }

        if (!result) {
            Alert.fire({
                icon: "error",
                text: "The attached file only uses [" + includeArray.join(', ') + "] extension. "
            })
            $("#file").replaceWith($("#file").clone(true));
            $("#file").val('');

            return false;
        }

        /* 파일 크기 검사 */
        if (fileSize > maxSize) {
            Alert.fire({
                icon: "error",
                text: "The attached file can upload within 20 MB size."
            })
            $("#file").replaceWith($("#file").clone(true));
            $("#file").val('');

            return false;
        }

        /* 모든 파일 크기 검사 */
        if (fileSize + totalFileSize > maxSize) {
            Alert.fire({
                icon: "error",
                text: "All attached files must be within 20 MB size."
            })
            $("#file").replaceWith($("#file").clone(true));
            $("#file").val('');

            return false;
        }

        totalFileSize += fileSize;
    }

    return true;
}

/*
 * 파일 validation - 필수 확장자
 *
 * [전역 변수 선언 필요]
 * var totalFileSize = 0;
 */
function validateVideoFile(file) {
    // file validation - 필수 확장자
    var includeArray = [".mp4", ".m4v", ".avi", ".wmv", ".mwa", ".asf", ".ts"];
    // 파일 이름
    var fileName = file.name;
    // 파일 확장자명(대문자를 소문자로 변경)
    var extensionName = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
    // 필수 확장자명 사용 여부 판단
    var result = false;
    // 첨부 파일 크기
    var fileSize = file.size;
    // 업로드 가능한 파일 크기: 20 MB
    var maxSize = 20 * 1024 * 1024;

    // 첨부 파일이 있는 경우
    if (fileName != "") {
        /* 확장자명 검사 */
        for (var j = 0; j < includeArray.length; j++) {
            if (extensionName == includeArray[j]) {
                result = true;
                break;
            }
        }

        if (!result) {
            Alert.fire({
                icon: "error",
                text: "The attached file only uses [" + includeArray.join(', ') + "] extension. "
            })
            $("#file").replaceWith($("#file").clone(true));
            $("#file").val('');

            return false;
        }

        /* 파일 크기 검사 */
        if (fileSize > maxSize) {
            Alert.fire({
                icon: "error",
                text: "The attached file can upload within 20 MB size."
            })
            $("#file").replaceWith($("#file").clone(true));
            $("#file").val('');

            return false;
        }

        /* 모든 파일 크기 검사 */
        if (fileSize + totalFileSize > maxSize) {
            Alert.fire({
                icon: "error",
                text: "All attached files must be within 20 MB size."
            })
            $("#file").replaceWith($("#file").clone(true));
            $("#file").val('');

            return false;
        }

        totalFileSize += fileSize;
    }

    return true;
}


/*
 * 파일 validation - 유효한 파일 확장자
 *
 * [전역 변수 선언 필요]
 *  var totalFileSize = 0;
 */
function validateFile(file) {
    // file validation - 제외 파일 확장자
    var excludeArray = [".exe", ".jar", ".js", ".swf", ".swf", ".bin", ".wmf", ".class", ".chm", ".pgm", ".pcx", ".hlp", ".acc", ".css", ".sh",
        ".com", "bat", "cmd", ".scf", ".lnk", ".inf", ".reg"];
    // 파일 이름
    var fileName = file.name;
    // 파일 확장자명(대문자를 소문자로 변경)
    var extensionName = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
    // 첨부 파일 크기
    var fileSize = file.size;
    // 업로드 가능한 파일 크기: 20 MB
    var maxSize = 20 * 1024 * 1024;

    if (fileName != "") {
        /* 확장자명 검사 */
        for (var i = 0; i < excludeArray.length; i++) {
            if (extensionName == excludeArray[i]) {
                Alert.fire({
                    icon: "error",
                    text: "[" + extensionName + "] extension doesn't support uploading attached file.",
                })
                $("#file").val('');
                $("#file").replaceWith($("#file").clone(true));

                return false;
            }
        }

        /* 파일 크기 검사 */
        if (fileSize > maxSize) {
            Alert.fire({
                icon: "error",
                text: "The attached file can upload within 20 MB size.",
            })
            $("#file").replaceWith($("#file").clone(true));
            $("#file").val('');

            return false;
        }

        /* 모든 파일 크기 검사 */
        if (fileSize + totalFileSize > maxSize) {
            Alert.fire({
                icon: "error",
                text: "All attached files must be within 20 MB size.",
            })
            $("#file").replaceWith($("#file").clone(true));
            $("#file").val('');

            return false;
        }

        totalFileSize += fileSize;

    }

    return true;
}

/*
 * validation response message alert
 */
function parseErrorMsg(msg) {
    var alertMsg = null;

    if (!isEmpty(msg.responseText)) {
        var parseMsg = JSON.parse(msg.responseText);

        if (isEmpty(parseMsg.errors)) {
            alertMsg = parseMsg.message;
        } else {
            alertMsg = parseMsg.message + "\n" + parseMsg.errors[0].reason;
        }
    } else {
        alertMsg = "NetworkError: Failed to execute 'send' on 'XMLHttpRequest'.";
    }

    Toast.fire({
        icon: "error",
        title: "Error occurred.",
        text: alertMsg
    })
}