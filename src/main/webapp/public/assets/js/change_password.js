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
            // Proceed with form submission or perform other actions
        }
    });
});

