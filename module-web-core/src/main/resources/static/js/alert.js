const Toast = Swal.mixin({
    toast: true,
    position: 'top-end',
    showConfirmButton: false,
    timer: 5000,

    didOpen: (toast) => {
        toast.addEventListener('mouseenter', Swal.stopTimer)
        toast.addEventListener('mouseleave', Swal.resumeTimer)
    }
})

const Confirm = Swal.mixin({
    showCancelButton: true,
    confirmButtonColor: 'rgb(158, 37, 41)',
    cancelButtonColor: 'rgb(128, 128, 128)',
})

const Alert = Swal.mixin({
    title: "Error occurred.",
})

function searchFail() {
    Toast.fire({
        icon: 'error',
        title: 'Search failed.',
    })
}

function registerSuccess() {
    Toast.fire({
        icon: 'success',
        title: 'Register succeeded.',
    })
}

function updateSuccess() {
    Toast.fire({
        icon: 'success',
        title: 'Update succeeded.',
    })
}

function deleteSuccess() {
    Toast.fire({
        icon: 'success',
        title: 'Delete succeeded.',
    })
}

function sweetalertFire(index) {
    /* sweetalert2 */
    switch (localStorage.getItem("result")) {
        case "/" + index + "/register-success" :
            registerSuccess();
            break;
        case "/" + index + "/update-success":
            updateSuccess();
            break;
        case "/" + index + "/delete-success":
            deleteSuccess();
            break;
    }

    localStorage.removeItem("result");
}