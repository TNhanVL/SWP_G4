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

        if (oldPassword.trim() === "" || newPassword.trim() === "" || confirmPassword.trim() === "" || check_old_password(oldPassword)) {
            $("#passwordError").show();
            event.preventDefault(); // Prevent form submission
        } else if (newPassword !== confirmPassword) {
            $("#passwordMatchError").show();
            event.preventDefault(); // Prevent form submission
        } else {
            $("#passwordError").hide();
            $("#passwordMatchError").hide();
        }
    });
    var formData = {
        password: $("#oldPassword").val()
    }

    function check_old_password(password) {
        $.ajax({
            type: "POST",
            url: "/learner_request/change_password",
            data: jQuery.param({password: $("#oldPassword").val()}),
            success: function () {
                console.log("iu truong 3 chu")
            },
            error: function () {
                console.log("dit me truong 3 chu")
            }
        })
        return true
    }
});


