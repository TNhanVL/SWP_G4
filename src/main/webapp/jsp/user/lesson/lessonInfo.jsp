<%-- 
    Document   : lessonInfo
    Created on : Jul 5, 2023, 9:06:33 PM
    Author     : TTNhan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="tab">
    <button class="tablinks" onclick="openTab(event, 'Overview')">Overview</button>
    <button class="tablinks" onclick="openTab(event, 'LessonContent')">Lesson Content</button>
    <button class="tablinks" onclick="openTab(event, 'QandA')">Q&A</button>
    <button class="tablinks" onclick="openTab(event, 'Rating')">Rating</button>
</div>

<!-- Tab content -->


<div id="Overview" class="tabcontent ">
    <div class="content">
        <ul>
            <li>Fluently master the Hiragana and Katakana alphabets, over 100 Kanji characters, and the
                basic grammar of N5 level.</li>
            <li>Use Japanese to communicate in simple situations with native speakers.</li>
            <li>Confidently conquer the JLPT with the highest score.</li>
            <li>Have a solid grasp of fundamental knowledge as a prerequisite for studying higher levels of
                Japanese.</li>
            <li>PROVIDE dedicated support to address any concerns or questions from students regarding
                study-related issues as well as working in Japan.</li>
            <li>Save costs when registering for the N5-N4, N5-N3 combo courses.</li>
        </ul>
    </div>
</div>

<div id="LessonContent" class="tabcontent active">
    <div class="content">
        <table>
            <thead>
                <tr>
                    <th>Characters</th>
                    <th>Romaji</th>
                    <th>Pronunciation tips</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>あ</td>
                    <td>a</td>
                    <td>あ is pronounced as "a" in "car" but shorter</td>
                </tr>
                <tr>
                    <td>い</td>
                    <td>i</td>
                    <td>い is pronounced as "ee" in "meet", but shorter</td>
                </tr>
                <tr>
                    <td>う</td>
                    <td>u</td>
                    <td>う is pronounced as "u" in "hula", but shorter</td>
                </tr>
                <tr>
                    <td>え</td>
                    <td>e</td>
                    <td>え is pronounced as "e" in "get"</td>
                </tr>
                <tr>
                    <td>お</td>
                    <td>o</td>
                    <td>お is pronounced as "o" in "or", but shorter</td>
                </tr>
                <tr>
                    <td>か</td>
                    <td>ka</td>
                    <td>か is pronounced as "ca" in "car", but shorter</td>
                </tr>
                <tr>
                    <td>き</td>
                    <td>ki</td>
                    <td>き is pronounced as "ki" in "keep", but shorter</td>
                </tr>
                <tr>
                    <td>く</td>
                    <td>ku</td>
                    <td>く is pronounced as "ku" in "Kuwait", but shorter</td>
                </tr>
                <tr>
                    <td>け</td>
                    <td>ke</td>
                    <td>け is pronounced as "ke" in "Kevin"</td>
                </tr>
                <tr>
                    <td>こ</td>
                    <td>ko</td>
                    <td>こ is pronounced as "ko" in "koala"</td>
                </tr>
            </tbody>
        </table>
    </div>
</div>

<div id="QandA" class="tabcontent ">
    <div class="content">
        <div class="writeComment">
            <form action="">
                <input placeholder="Write your comment" type="text" name="comment">
                <input type="submit" value="Post">
            </form>
        </div>

        <!-- comment area -->
        <div class="comments">
            <!-- start a comment -->
            <div class="comment">
                <img src="/public/assets/imgs/D1.jpg" alt="" class="userAvatar">
                <div class="commentContent">
                    <p class="userName">Nguyen Thanh Duong</p>
                    <p class="content">
                        W3Schools is optimized for learning and training. Examples might be simplified to
                        improve reading and learning. Tutorials, references, and examples are constantly
                        reviewed to avoid errors, but we cannot warrant full correctness of all content.
                        While using W3Schools, you agree to have read and accepted our terms of use, cookie
                        and privacy policy.
                    </p>
                    <span class="option">
                        <span class="time">03/07/2023</span>
                        <!-- Edit -->
                        <!-- <span>Edit</span> -->
                        <!-- Reply -->
                        <span>Reply</span>
                    </span>
                </div>
            </div>
            <!-- end a comment -->
            <div class="comment">
                <img src="/public/assets/imgs/instructor.png" alt="" class="userAvatar">
                <div class="commentContent">
                    <p class="userName">Nguyen Thanh Duong</p>
                    <p class="content">
                        W3Schools is optimized for learning and training. Examples might be simplified to
                        improve reading and learning. Tutorials, references, and examples are constantly
                        reviewed to avoid errors, but we cannot warrant full correctness of all content.
                        While using W3Schools, you agree to have read and accepted our terms of use, cookie
                        and privacy policy.
                    </p>
                    <span class="option">
                        <span class="time">03/07/2023</span>
                        <!-- Edit -->
                        <!-- <span>Edit</span> -->
                        <!-- Reply -->
                        <span>Reply</span>
                    </span>
                </div>
            </div>
            <!-- end a comment -->
            <div class="comment">
                <img src="/public/assets/imgs/logo.png" alt="" class="userAvatar">
                <div class="commentContent">
                    <p class="userName">Nguyen Thanh Duong</p>
                    <p class="content">
                        W3Schools is optimized for learning and training. Examples might be simplified to
                        improve reading and learning. Tutorials, references, and examples are constantly
                        reviewed to avoid errors, but we cannot warrant full correctness of all content.
                        While using W3Schools, you agree to have read and accepted our terms of use, cookie
                        and privacy policy.
                    </p>
                    <span class="option">
                        <span class="time">03/07/2023</span>
                        <!-- Edit -->
                        <!-- <span>Edit</span> -->
                        <!-- Reply -->
                        <span>Reply</span>
                    </span>
                </div>
            </div>
            <!-- end a comment -->
        </div>

        <ul class="menu">
            <li><i class="fa-sharp fa-solid fa-angle-left"></i></li>
            <li class="active"><a href="#">1</a></li>
            <li class=""><a href="#">2</a></li>
            <li class=""><a href="#">3</a></li>
            <li class=""><a href="#">4</a></li>
            <li class=""><a href="#">5</a></li>
            <li class=""><a href="#">6</a></li>
            <li class=""><a href="#">7</a></li>
            <li class=""><i class="fa-sharp fa-solid fa-angle-right"></i></li>
        </ul>
    </div>
</div>

<div id="Rating" class="tabcontent ">
    <div class="rating-container">
        <div class="rating-left">
            <h1>4.5</h1>
            <i class="fas fa-star star"></i>
            <i class="fas fa-star star"></i>
            <i class="fas fa-star star"></i>
            <i class="fas fa-star star"></i>
            <i class="fas fa-star star"></i>
        </div>
        <div class="rating-right">
            <div class="rating-detail">
                <div class="progress-bar">
                    <div class="progress" style="width: 80%;"></div>
                </div>
                <span>5<i class="fas fa-star star"></i>
                    <i class="fas fa-star star"></i>
                    <i class="fas fa-star star"></i>
                    <i class="fas fa-star star"></i>
                    <i class="fas fa-star star"></i></span>

                <span>80%</span>
            </div>
            <div class="rating-detail">
                <div class="progress-bar">
                    <div class="progress" style="width: 10%;"></div>
                </div>
                <span>4<i class="fas fa-star star"></i>
                    <i class="fas fa-star star"></i>
                    <i class="fas fa-star star"></i>
                    <i class="fas fa-star star"></i>
                    <i class="fa-regular fa-star"></i></span>

                <span>10%</span>
            </div>
            <div class="rating-detail">
                <div class="progress-bar">
                    <div class="progress" style="width: 7%;"></div>
                </div>
                <span>3<i class="fas fa-star star"></i>
                    <i class="fas fa-star star"></i>
                    <i class="fas fa-star star"></i>
                    <i class="fa-regular fa-star"></i>
                    <i class="fa-regular fa-star"></i></span>

                <span>7%</span>
            </div>
            <div class="rating-detail">
                <div class="progress-bar">
                    <div class="progress" style="width: 2%;"></div>
                </div>
                <span>2<i class="fas fa-star star"></i>
                    <i class="fas fa-star star"></i>
                    <i class="fa-regular fa-star"></i>
                    <i class="fa-regular fa-star"></i>
                    <i class="fa-regular fa-star"></i></span>

                <span>2%</span>
            </div>
            <div class="rating-detail">
                <div class="progress-bar">
                    <div class="progress" style="width: 1%;"></div>
                </div>
                <span>1<i class="fas fa-star star"></i>
                    <i class="fa-regular fa-star"></i>
                    <i class="fa-regular fa-star"></i>
                    <i class="fa-regular fa-star"></i>
                    <i class="fa-regular fa-star">

                    </i></span>

                <span>1%</span>
            </div>
        </div>
    </div>
    <!-- Rating coment of user -->
    <div class="rating-comments">
        <!-- start a rating -->
        <div class="rating-content">
            <img src="/public/assets/imgs/gg.png" alt="" class="rateUserImg">
            <div class="content">
                <p class="UserRateName">
                    Google
                </p>
                <div class="UserRateComment">
                    Phần này thì hiển thị cái đánh giá của nguyên course, rồi hiển thị user nào đã rate
                    Phần này thì hiển thị cái đánh giá của nguyên course, rồi hiển thị user nào đã rate
                </div>
                <div class="rateOption">
                    <span class="like">
                        <i class="fa-solid fa-thumbs-up"></i>
                        <i class="fa-solid fa-thumbs-down"></i>
                    </span>
                    <span class="report"><b>Report</b></span>
                </div>
            </div>
        </div>
        <!-- end a rating -->
        <!-- start a rating -->
        <div class="rating-content">
            <img src="/public/assets/imgs/gg.png" alt="" class="rateUserImg">
            <div class="content">
                <p class="UserRateName">
                    Google
                </p>
                <div class="UserRateComment">
                    Phần này thì hiển thị cái đánh giá của nguyên course, rồi hiển thị user nào đã rate
                    Phần này thì hiển thị cái đánh giá của nguyên course, rồi hiển thị user nào đã rate
                </div>
                <div class="rateOption">
                    <span class="like">
                        <i class="fa-solid fa-thumbs-up"></i>
                        <i class="fa-solid fa-thumbs-down"></i>
                    </span>
                    <span class="report"><b>Report</b></span>
                </div>
            </div>
        </div>
        <!-- end a rating -->
        <!-- start a rating -->
        <div class="rating-content">
            <img src="/public/assets/imgs/gg.png" alt="" class="rateUserImg">
            <div class="content">
                <p class="UserRateName">
                    Google
                </p>
                <div class="UserRateComment">
                    Phần này thì hiển thị cái đánh giá của nguyên course, rồi hiển thị user nào đã rate
                    Phần này thì hiển thị cái đánh giá của nguyên course, rồi hiển thị user nào đã rate
                </div>
                <div class="rateOption">
                    <span class="like">
                        <i class="fa-solid fa-thumbs-up"></i>
                        <i class="fa-solid fa-thumbs-down"></i>
                    </span>
                    <span class="report"><b>Report</b></span>
                </div>
            </div>
        </div>
        <!-- end a rating -->


    </div>

</div>
