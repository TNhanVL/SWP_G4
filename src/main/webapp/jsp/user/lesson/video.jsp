<%-- 
    Document   : video
    Created on : Jul 5, 2023, 8:54:54 PM
    Author     : TTNhan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
    if (lesson.getType() == 0) {
%>
<video id="my-video" src="/public/media/lesson/<%out.print(lesson.getID() + "/" + post.getContent());%>" controls>
    Trình duyệt của bạn không hỗ trợ video.
</video>
<%
} else {
%>
<div id="player"></div>
<%
    }
%>


<script>
    var video = document.getElementById('my-video');

//    if (video) {
//        video.addEventListener('ended', function () {
//            console.log('End');
//        });
//    }

    function onYouTubeIframeAPIReady() {
        // Create a new instance of the player
        player = new YT.Player('player', {
            videoId: '<%out.print(post.getContent());%>'
        });
    }

    let sendedCompletedVideo = false;

    function checkVideoProgress() {
        var duration; // Get the duration of the video
        var currentTime; // Get the current time of the video
        if (video && !sendedCompletedVideo) {
            duration = video.duration;
            currentTime = video.currentTime;
        }
        if (player && !sendedCompletedVideo) {
            var duration = player.getDuration(); // Get the duration of the video
            var currentTime = player.getCurrentTime(); // Get the current time of the video
        }
        if (video || player) {

            //check if 90% video
            if (currentTime >= duration * 0.9) {
                fetch("/markLessonComplete/<%out.print(lesson.getID());%>", {method: 'POST'})
                                        .catch(error => console.error(error));
                                sendedCompletedVideo = true;
                                let checkLesson = $(".lesson.active i")[0];
                                checkLesson.classList = [];
                                checkLesson.classList.add("fa-solid");
                                checkLesson.classList.add("fa-square-check");

//Increase progress lesson
                                let progressLesson = $(".progressLesson")[<%out.print(mooc.getIndex() - 1);%>];
                                let progressLessonText = progressLesson.innerHTML;
                                progressLesson.innerHTML = Number(progressLessonText.split("/")[0]) + 1 + "/" + progressLessonText.split("/")[1];
                            }
                        }
                    }
// Gọi hàm kiểm tra video theo một khoảng thời gian nhất định
                    setInterval(checkVideoProgress, 1000); // Kiểm tra sau mỗi giây (1000 milliseconds)
</script>