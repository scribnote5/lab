var totalFileSize = 0;
var insertFileArray = [];
var deleteFileArray = [];
var imgDataId = 0;
var exit = null;

/* input tag event */
$("#file").on("change", function () {
    if (validateImageFile(this.files[0])) {
        // 부모 href 링크가 존재하는 경우 삭제
        if (!isEmpty($("#imgPreviewLink"))) {
            $("#imgPreviewLink").contents().unwrap();
        }
        totalFileSize = 0;

        document.getElementById("imgData").innerHTML = this.files[0].name + ", File size: " + convertFileSize(this.files[0].size);
        readURL(this.files[0]);
    } else {
        return false;
    }
});

function readURL(input) {
    var reader = new FileReader();

    reader.onload = function (e) {
        $('#imgPreview').attr('src', e.target.result);
    }

    reader.readAsDataURL(input);
}