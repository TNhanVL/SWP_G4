function openMenu() {

    var b = document.getElementById("userMenu")

    console.log(b)

    if (b.classList.contains("close")) {
        b.classList.remove("close")
        console.log("Show")
    } else {
        b.classList.add("close")
        console.log("Remove")

    }
}

function addItem(thisID) {
    var id = parseInt(thisID.split('-')[1]);


}

function removeItem() {

}

var courseTakenDivs = document.getElementsByClassName("courseTaken");

for (var i = 0; i < courseTakenDivs.length; i++) {
    var progress = courseTakenDivs[i].querySelector("progress").value;
    var notCompletedDiv = courseTakenDivs[i].querySelector(".notCompleted");
    var completedDiv = courseTakenDivs[i].querySelector(".completed");

    if (progress === 100) {
        notCompletedDiv.style.display = "none";
        completedDiv.style.display = "flex";
    } else {
        notCompletedDiv.style.display = "flex";
        completedDiv.style.display = "none";
    }
}


// console.log(tabHidden, progressView);


function strangeMode(mode) {

    var tabHidden = document.querySelector(".tabStranger")
    var progressView = document.querySelectorAll(".ProgressviewMode")
    var rateView = document.querySelectorAll(".rate");

    if (mode == 'off') {
        tabHidden.classList.add("hidden")
        progressView.forEach(element => {
            element.classList.remove("hidden")
        });
        rateView.forEach(element => {
            element.classList.add("hidden")
        });
    } else {
        tabHidden.classList.remove("hidden")
        progressView.forEach(element => {
            element.classList.add("hidden")
        });
        rateView.forEach(element => {
            element.classList.remove("hidden")
        });
    }
}

// strangeMode('on')


var input = document.getElementById('profile-photo');

if (input) {
    // Kiểm tra xem người dùng đã chọn tệp tin nào chưa
    if (input.files && input.files[0]) {
        var file = input.files[0];

        // Tạo một đối tượng FileReader
        var reader = new FileReader();

        // Xử lý sự kiện khi file đã được đọc
        reader.onload = function (e) {
            // Lấy link upload từ thuộc tính "result" của FileReader
            var uploadLink = e.target.result;

            // Đặt link upload vào thuộc tính "src" của thẻ <img>
            var imgPreview = document.getElementById('imgPreview');
            imgPreview.src = uploadLink;
        }

        // Đọc file như là một URL data
        reader.readAsDataURL(file);
    } else {
        // Nếu không tìm thấy ảnh upload, sử dụng ảnh mặc định
        var imgPreview = document.getElementById('imgPreview');
        imgPreview.src = "./assets/imgs/logoooooo.png";
    }
}

const notification_background_color = ["#e6ffcf", "#deead2"];

function get_notification() {
    if (!$("#id_string")[0]) return;
    $.post("/learner_request/notification", jQuery.param({
            id_string: $("#id_string").val()
        }), function (data) {
            console.log(data)
            let unread_notification = 0;
            if (data.length === 0) {
                document.getElementById("notification_list").innerHTML += `                
                <li>
                    <div id="notification_instance" 
                    style="background: ${notification_background_color[0]}">
                        <p class="h5 text text-center" >
                            No notification
                        </p>
                    </div>
                </li>`

            } else {
                data.forEach((noti) => {
                    unread_notification += noti["read"] ? 0 : 1;
                    var date = new Date(noti["receiveAt"]);
                    let date_string = date.getDate() + "/" + date.getMonth() + "/" + date.getFullYear();
                    document.getElementById("notification_list").innerHTML += `                
                <li>
                    <div id="notification_instance" 
                    style="border-right: #a7c080 solid 5px;background: ${notification_background_color[noti.read ? 1 : 0]}">
                        <p class=" text text-end" style="font-size: larger">
                            ${noti.description}
                        </p>
                        <p class=" text text-end">
                            ${date_string}
                        </p>
                    </div>
                </li>`
                })
            }
            if (unread_notification !== 0) {
                $("#notification_quantity").text(unread_notification).show()
            }
        }
    )
}

function toggle_notification() {
    $("#notification_list").toggle()
}


get_notification()