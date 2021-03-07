var totalFileSize = 0;
var insertFileArray = [];
var deleteFileArray = [];
var imgDataId = 0;
var exit = null;

// 파일 Number validation
var insertFileArrayLength = 0;
var deleteFileArrayLength = 0;

$(document).ready(function () {
    <!-- File Drop -->
    $("#fileDrop").on("dragenter dragover", function (event) {
        event.preventDefault(); // 기본 이벤트 발생을 막음
    });
});

$("#fileDrop").on("dragover", function (event) {
    event.stopPropagation();
    event.preventDefault();

    document.getElementById("fileDrop").style.opacity = 0.25;
});

$("#fileDrop").on("dragleave", function (event) {
    event.stopPropagation();
    event.preventDefault();

    document.getElementById("fileDrop").style.opacity = 1;
});

// 새로 업로드한 파일을 취소하는 경우
function cancelFile(fileId) {
    $('#imgData' + fileId).remove();

    totalFileSize -= insertFileArray[fileId].size;
    insertFileArray[fileId] = null;

    document.getElementById("totalFileSize").innerHTML = convertFileSize(totalFileSize);
}

// 기존 업로드한 파일을 삭제하는 경우
function deleteFile(fileId, idx, savedFileName) {
    $('#imgData' + fileId).remove();

    deleteFileArray.push(idx);
}

/* input tag event */
$("#file").change(function () {
    var files = document.getElementsByName("file")[0].files;
    document.getElementsByName("file")[0].files.value = null;

    // 업로드하는 파일 개수가 number개가 넘어가는 경우, 파일 업로드 불가
    if (!validateByFileNumber(files, 1)) {
        return false;
    }

    for (var i = 0; i < files.length; i++) {
        if (validatePdfFile(files[i])) {
            insertFileArray.push(files[i]);
            document.getElementById("totalFileSize").innerHTML = convertFileSize(totalFileSize);

            $("#attachedFileList").append('<div id="imgData' + imgDataId + '">'
                + '<span>'
                + files[i].name + ",&nbsp; File Size: " + convertFileSize(files[i].size) + "&nbsp;"
                + '<i class="far fa-times-circle cancel-icon" onClick="cancelFile(' + imgDataId + ')"></i>'
                + '</span>'
                + '</div>');

            imgDataId++;
        } else {
            return false;
        }
    }
});

/* Drag & drop event */
$("#fileDrop").on("drop", function (event) {
    event.preventDefault(); // 기본 효과를 막음
    document.getElementById("fileDrop").style.opacity = 1;

    // 드래그된 파일의 정보
    // event : jQuery의 이벤트
    // originalEvent : javascript의 이벤트
    var files = event.originalEvent.dataTransfer.files;

    // 업로드하는 파일 개수가 number개가 넘어가는 경우, 파일 업로드 불가
    if (!validateByFileNumber(files, 1)) {
        return false;
    }

    for (var i = 0; i < files.length; i++) {
        if (validatePdfFile(files[i])) {
            insertFileArray.push(files[i]);
            document.getElementById("totalFileSize").innerHTML = convertFileSize(totalFileSize);

            $("#attachedFileList").append('<div id="imgData' + imgDataId + '">'
                + '<span>'
                + files[i].name + ",&nbsp; File Size: " + convertFileSize(files[i].size) + "&nbsp;"
                + '<i class="far fa-times-circle cancel-icon" onClick="cancelFile(' + imgDataId + ')"></i>'
                + '</span>'
                + '</div>');

            imgDataId++;
        } else {
            return false;
        }
    }
});