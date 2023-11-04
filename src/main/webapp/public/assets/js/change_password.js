$(document).ready(function () {


    $("#changePasswordButton").click(function () {
        $("#passwordForm").addClass("fade-in").show();
        $("#overlay").addClass("fade-in").show();
    });

    $("#cancelPasswordChange").click(function () {
        $("#passwordForm").addClass("fade-out");
        $("#overlay").addClass("fade-out");

        setTimeout(function () {
            $("#passwordForm").removeClass("fade-out").hide();
            $("#overlay").removeClass("fade-out").hide();
        }, 300);
    });

    $("#oldPassword, #password, #confirmPassword").on("input", function () {
        $("#passwordError, #passwordMatchError").hide();
    });

    $("#passwordForm").on("submit", function (event) {
        var oldPassword = $("#oldPassword").val();
        var newPassword = $("#password").val();
        var confirmPassword = $("#confirmPassword").val();

        if (oldPassword.trim() === "" || newPassword.trim() === "" || confirmPassword.trim() === "") {
            $("#passwordError").show();
            event.preventDefault(); // Prevent form submission
        } else if (newPassword !== confirmPassword) {
            $("#passwordMatchError").show();
            event.preventDefault(); // Prevent form submission
        } else {
            $("#passwordError").hide();
            $("#passwordMatchError").hide();
            $("#mismatchOldPassword").hide();
            change_password();
            event.preventDefault(); // Prevent form submission
        }
    });

    function change_password() {
        $.ajax({
            type: "POST",
            url: "/learner_request/change_password",
            data: jQuery.param({
                old_password: $("#oldPassword").val(),
                new_password: $("#password").val(),
                username: $("#username").val(),
            }),
            success: function (data, xhr, status) {
                if (data === 1) {
                    $("#mismatchOldPassword").show();
                }

                if (data === 0) {
                    window.location.replace("/");
                }
            },
            error: function (xhr, status) {
                // check if xhr.status is defined in $.ajax.statusCode
                // if true, return false to stop this function
                if (typeof this.statusCode[xhr.status] != 'undefined') {
                    return false;
                }
                // else continue
                console.log('ajax.error');
            }
        })
        return true
    }
});


