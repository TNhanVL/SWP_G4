drop table [admin], [transaction], chosenAnswer, answer, question, quizResult, post, lessonCompleted, lesson, chapter, cartProduct, purchasedCourse, [certificate], course, lecturer, [user], organization, country
-- Create the tables
CREATE TABLE [admin]
(
    username   VARCHAR(50) PRIMARY KEY,
    [password] VARCHAR(50)
);
GO

CREATE TABLE country
(
    countryID INT PRIMARY KEY,
    name      NVARCHAR(60)
);
GO

CREATE TABLE organization
(
    ID            INT IDENTITY (1,1) PRIMARY KEY, -- giá trị bắt đầu là 1, giá trị tăng thêm là 1
    countryID     INT FOREIGN KEY REFERENCES [country],
    username      VARCHAR(50),
    [password]    VARCHAR(50),
    email         VARCHAR(320),
    picture       TEXT,
    [name]        VARCHAR(100),
    [description] TEXT
);
GO

CREATE TABLE [user]
(
    ID          INT IDENTITY (1,1) PRIMARY KEY,
    picture      TEXT,
    username    VARCHAR(50),
    [password]  VARCHAR(50),
    email       VARCHAR(320),
    [firstName] NVARCHAR(50),
    [lastName]  NVARCHAR(50),
    [role]      INT,
    birthday    DATE,
    countryID   INT FOREIGN KEY REFERENCES [country],
    [status]    int
);
GO

CREATE TABLE lecturer
(
    userID         INT NOT NULL PRIMARY KEY,
    organizationID INT NOT NULL,
    FOREIGN KEY (userID) REFERENCES [user] (ID),
    FOREIGN KEY (organizationID) REFERENCES organization (ID)
);
GO

CREATE TABLE course
(
    courseID       INT IDENTITY (1,1) NOT NULL PRIMARY KEY,
    name           NVARCHAR(50)       NOT NULL,
    [image]        TEXT,
    [description]  NVARCHAR(50),
    organizationID INT                NOT NULL,
    lecturerID     INT                NOT NULL,
    price          DECIMAL(10, 2)     NOT NULL,
    rate           DECIMAL(2, 1)      NOT NULL,
    FOREIGN KEY (organizationID) REFERENCES organization (ID),
    FOREIGN KEY (lecturerID) REFERENCES [user] (ID)
);
GO

CREATE TABLE [certificate]
(
    userID          INT NOT NULL,
    courseID        INT NOT NULL,
    certificateName text,
    PRIMARY KEY (userID, courseID),
    FOREIGN KEY (userID) REFERENCES [user] (ID),
    FOREIGN KEY (courseID) REFERENCES course (courseID)
);
GO

CREATE TABLE purchasedCourse
(
    userID   INT NOT NULL,
    courseID INT NOT NULL,
    FOREIGN KEY (userID) REFERENCES [user] (ID),
    FOREIGN KEY (courseID) REFERENCES course (courseID),
    UNIQUE (userID, courseID)
);
GO

CREATE TABLE [transaction]
(
    transactionID INT IDENTITY (1,1) NOT NULL PRIMARY KEY,
    userID        INT                NOT NULL,
    courseID      INT                NOT NULL,
    originPrice   DECIMAL(10, 2)     NOT NULL,
    price         DECIMAL(10, 2)     NOT NULL,
    type          INT,
    description   NTEXT,
    status        INT,
    FOREIGN KEY (userID) REFERENCES [user] (ID),
    FOREIGN KEY (courseID) REFERENCES course (courseID),
    UNIQUE (userID, courseID)
);
GO

CREATE TABLE cartProduct
(
    userID   INT NOT NULL,
    courseID INT NOT NULL,
    FOREIGN KEY (userID) REFERENCES [user] (ID),
    FOREIGN KEY (courseID) REFERENCES course (courseID),
    UNIQUE (userID, courseID)
);
GO

CREATE TABLE chapter
(
    chapterID     INT IDENTITY (1,1) NOT NULL PRIMARY KEY,
    courseID      INT                NOT NULL,
    [index]       INT                NOT NULL,
    name          NVARCHAR(50),
    [description] NVARCHAR(50),
    FOREIGN KEY (courseID) REFERENCES course (courseID)
);
GO

CREATE TABLE lesson
(
    lessonID  INT IDENTITY (1,1) NOT NULL PRIMARY KEY,
    chapterID INT                NOT NULL,
    name      NVARCHAR(50),
    [index]   INT                NOT NULL,
    [type]    INT                NOT NULL,
    [time]    INT                NOT NULL,
    FOREIGN KEY (chapterID) REFERENCES chapter (chapterID)
);
GO

CREATE TABLE lessonCompleted
(
    lessonID INT NOT NULL,
    userID   INT NOT NULL,
    FOREIGN KEY (lessonID) REFERENCES lesson (lessonID),
    FOREIGN KEY (userID) REFERENCES [user] (ID),
    UNIQUE (lessonID, userID)
);
GO

CREATE TABLE post
(
    [ID]     INT IDENTITY (1,1) NOT NULL PRIMARY KEY,
    content  NTEXT              NOT NULL,
    lessonID INT,
    FOREIGN KEY (lessonID) REFERENCES lesson (lessonID)
);
GO

CREATE TABLE question
(
    [questionID] INT IDENTITY (1,1) NOT NULL PRIMARY KEY,
    lessonID     INT                NOT NULL,
    [index]      INT                NOT NULL,
    content      NTEXT              NOT NULL,
    [type]       INT                NOT NULL,
    point        INT,
    FOREIGN KEY (lessonID) REFERENCES lesson (lessonID)
);
GO

CREATE TABLE answer
(
    [answerID] INT IDENTITY (1,1) NOT NULL PRIMARY KEY,
    questionID INT                NOT NULL,
    content    NTEXT,
    correct    BIT                NOT NULL, --True: 1, False: 0
    FOREIGN KEY (questionID) REFERENCES question (questionID)
);
GO

CREATE TABLE quizResult
(
    quizResultID INT IDENTITY (1,1) NOT NULL PRIMARY KEY,
    lessonID     INT                NOT NULL,
    userID       INT                NOT NULL,
    startTime    DATETIME,
    endTime      DATETIME,
    FOREIGN KEY (lessonID) REFERENCES lesson (lessonID),
    FOREIGN KEY (userID) REFERENCES [user] (ID)
);
GO

CREATE TABLE chosenAnswer
(
    quizResultID   INT NOT NULL,
    questionID     INT NOT NULL,
    selectedAnswer INT NOT NULL,
    FOREIGN KEY (quizResultID) REFERENCES quizResult (quizResultID),
    FOREIGN KEY (questionID) REFERENCES question (questionID),
    FOREIGN KEY (selectedAnswer) REFERENCES answer (answerID),
    UNIQUE (quizResultID, questionID, selectedAnswer)
);
GO

-- insert data

INSERT INTO country(countryID, [name])
VALUES (1, 'United States'),
       (2, 'Canada'),
       (3, 'Mexico'),
       (4, 'Germany'),
       (5, 'France'),
       (6, 'Italy'),
       (7, 'Spain'),
       (8, 'China'),
       (9, 'Japan'),
       (10, 'South Korea'),
       (11, 'Chile'),
       (12, 'India'),
       (13, 'Peru'),
       (14, 'South Korea'),
       (15, 'Thailand'),
       (16, 'Vietnam'),
       (17, 'Indonesia'),
       (18, 'Philippines'),
       (19, 'Malaysia'),
       (20, 'Singapore')
GO
INSERT INTO [admin](username, [password])
VALUES ('admin', '0e7517141fb53f21ee439b355b5a1d0a'),
       ('quantri', '0e7517141fb53f21ee439b355b5a1d0a')
GO
INSERT INTO [user](picture, username, [password], email, firstName, lastName, [role], birthday, countryID, [status])
VALUES ('a.jpg', 'ttnhan', '0cc175b9c0f1b6a831c399e269772661', 'nhan12341184@gmail.com', 'Nhan', 'Tran Thanh', 0,
        '1990-01-01', 16, 1),
       ('a.jpg', 'dylan12', '0cc175b9c0f1b6a831c399e269772661', 'dylan@example.com', 'Huong', 'Nguyen Thi Diem', 0,
        '2003-10-12', 16, 1),
       ('a.jpg', 'diemhuong1210', '12345678', 'dh1210@example.com', 'Duong', 'Thanh', 1, '2003-10-10', 16, 1)
GO
INSERT INTO organization([name], picture, [description])
VALUES ('FPT University', 'FPT.png', N'Trường đại học top 1 Việt Nam');
GO
INSERT INTO lecturer(userID, organizationID)
VALUES (1, 1),
       (2, 1)
GO
INSERT INTO course(name, [image], [description], organizationID, lecturerID, price, rate)
VALUES ('Dekiru Nihongo', 'a.png', 'ezsy', 1, 1, 1, 4.2),
       ('Java advance', 'a.png', 'medium difficult', 1, 2, 2, 4.5),
       ('C++', 'a.png', 'hard', 1, 3, 1.2, 4.7),
       ('PYTHON FOR BEGINNER', 'a.png', 'ezsy', 1, 1, 1.4, 4.2),
       ('Java advance', 'a.png', 'medium difficult', 1, 2, 2.5, 4.5),
       ('C++', 'a.png', 'hard', 1, 3, 600, 4.7),
       ('Java basic', 'a.png', 'ezsy', 1, 1, 200, 4.2),
       ('Java advance', 'a.png', 'medium difficult', 1, 2, 0.4, 4.5),
       ('C++', 'a.png', 'hard', 1, 3, 5, 4.7),
       ('Java basic', 'a.png', 'ezsy', 1, 1, 3, 4.2),
       ('Java advance', 'a.png', 'medium difficult', 1, 2, 2, 4.5),
       ('C++', 'a.png', 'hard', 1, 3, 5, 4.7)
GO
INSERT INTO cartProduct(userID, courseID)
VALUES (1, 2),
       (1, 3)
GO
INSERT INTO purchasedCourse(userID, courseID)
VALUES (1, 1),
       (1, 2)
GO
INSERT INTO chapter(courseID, [index], name, [description])
VALUES (1, 1, N'Hiragana 。ひらがな', ''),
       (1, 2, N'Katakana 。かたがな', ''),
       (2, 1, N'Test chapter', '')
GO
INSERT INTO lesson(chapterID, name, [index], [type], [time])
VALUES (1, 'A, Ka Row', 1, 3, 3),
       (1, 'Sa, Ta Row', 2, 3, 3),
       (1, 'Practice 1: Choose the pronunciation', 3, 2, 5),
       (1, 'Na, Ha Row', 4, 3, 3),
       (1, 'Ma, Ya Row', 5, 3, 3),
       (1, 'Ra, Wa, N Row', 6, 3, 3),
       (1, 'Dakuon - Ga, Za, Da, Ba', 7, 3, 3),
       (1, 'Handakuon - Pa', 8, 3, 3),
       (1, 'Sokuon - small つ', 9, 3, 3),
       (1, 'Chouon - Long vowels', 10, 3, 3),
       (1, 'Hiragana combination', 11, 3, 3),
       (1, 'Dakuon & Handakuon of Hiragana combination', 12, 3, 3),
       (1, 'Multiple-choice test (10 questions)', 13, 2, 30),
       (2, 'Nihongo2', 1, 2, 5),
       (2, 'Video', 2, 0, 5),
       (2, 'Post', 3, 1, 5),
       (3, 'Youtube', 1, 3, 5)
GO
INSERT INTO post(content, lessonID)
VALUES ('s4RXDEVFO_E', 1),
       ('J9MvqJnj5kQ', 2),
       ('rsL86uUTJpw', 4),
       ('_Hk2d4AO-Uk', 5),
       ('AmQ9kmom1v8', 6),
       ('lW8V5uMMM-4', 7),
       ('EZb3fs4Ntgc', 8),
       ('nGLciw6mZCo', 9),
       ('5JGjT9Cy2ak', 10),
       ('Asy10OI-lFU', 11),
       ('V34OFinfTbU', 12),
       ('DoggieCorgi-4.mp4', 15),
       ('V34OFinfTbU', 17)
GO
INSERT INTO question(lessonID, [index], content, [type], point)
VALUES (3, 1, 'a.png', 0, 1),
       (3, 2, 'i.png', 0, 1),
       (3, 3, 'u.png', 1, 1),
       (3, 4, 'e.png', 1, 1),
       (3, 5, N'What is the character あ?', 20, 1),
       (3, 6, 'https://statics.gojapan.vn/ufiles/2019/11/5b5bf0532cc51939d51b9798/5dde210b3b77504afb2f9cab.png', 10, 1),
       (13, 1, 'https://statics.gojapan.vn/ufiles/2019/11/5b5bf0532cc51939d51b9798/5ddf8d1a3b7750201c44b5cd.png', 10,
        0.5),--ga
       (13, 2, 'https://statics.gojapan.vn/ufiles/2019/11/5b5bf0532cc51939d51b9798/5ddf8bd13b77504ea52ea0c4.png', 10,
        0.5),--gi
       (13, 3, 'https://statics.gojapan.vn/ufiles/2020/01/5b5bf0532cc51939d51b9798/5e3273cb59e7be49cd345ba4.png', 10,
        0.5),--go
       (13, 4, 'https://statics.gojapan.vn/ufiles/2020/01/5b5bf0532cc51939d51b9798/5e3273d959e7be49cd345ba7.png', 10,
        0.5),--za
       (13, 5, 'https://statics.gojapan.vn/ufiles/2020/01/5b5bf0532cc51939d51b9798/5e3273f459e7be49cd345baa.png', 10,
        0.5),--zu
       (13, 6, 'https://statics.gojapan.vn/ufiles/2020/01/5b5bf0532cc51939d51b9798/5e32741759e7be49cd345bad.png', 10,
        0.5),--zo
       (13, 7, 'https://statics.gojapan.vn/ufiles/2020/01/5b5bf0532cc51939d51b9798/5e327e4259e7be6c8434ba83.png', 10,
        0.5),--da
       (13, 8, 'https://statics.gojapan.vn/ufiles/2020/01/5b5bf0532cc51939d51b9798/5e327e5559e7be64dc788d6e.png', 10,
        0.5),--di
       (13, 9, 'https://statics.gojapan.vn/ufiles/2020/01/5b5bf0532cc51939d51b9798/5e327e7159e7be6a7d27ebbd.png', 10,
        0.5),--de
       (13, 10, N'Which words contain letters in the Ka row?', 21, 0.5)

--(13, 11, 'https://statics.gojapan.vn/ufiles/2020/01/5b5bf0532cc51939d51b9798/5e327f4359e7be6c8434ba8f.png', 10, 0.5),--pu
--(13, 12, 'https://statics.gojapan.vn/ufiles/2020/01/5b5bf0532cc51939d51b9798/5e33cdfc59e7be091357234a.png', 10, 0.5),--kitte
--(13, 13, 'https://statics.gojapan.vn/ufiles/2020/01/5b5bf0532cc51939d51b9798/5e33d2a359e7be0e323e3cd3.png', 10, 0.5),--otto
--(13, 14, 'https://statics.gojapan.vn/ufiles/2020/01/5b5bf0532cc51939d51b9798/5e33d36859e7be208b356af9.png', 10, 0.5),--buka
--(13, 15, 'https://statics.gojapan.vn/ufiles/2020/01/5b5bf0532cc51939d51b9798/5e33d3b559e7be22a750dc63.png', 10, 0.5),--sekken
--(13, 16, 'https://statics.gojapan.vn/ufiles/2020/01/5b5bf0532cc51939d51b9798/5e33d3ee59e7be208b356afe.png', 10, 0.5),--kassai
--(13, 17, 'https://statics.gojapan.vn/ufiles/2020/02/5b5bf0532cc51939d51b9798/5e3a2c4059e7be74e2743f83.png', 10, 0.5),--ningyou
--(13, 18, 'https://statics.gojapan.vn/ufiles/2020/02/5b5bf0532cc51939d51b9798/5e3a2c9c59e7be42f8565c5d.png', 10, 0.5),--kyuukei
--(13, 19, 'https://statics.gojapan.vn/ufiles/2020/02/5b5bf0532cc51939d51b9798/5e3a2cdd59e7be49d06e8cae.png', 10, 0.5),--byouin
--(13, 20, 'https://statics.gojapan.vn/ufiles/2020/02/5b5bf0532cc51939d51b9798/5e3a2d9a59e7be49d06e8cb7.png', 10, 0.5)--josei

GO
INSERT INTO answer(questionID, content, correct)
VALUES (1, 'a', 1),
       (1, 'u', 0),
       (1, 'e', 0),
       (1, 'i', 0),
       (2, 'a', 0),
       (2, 'u', 0),
       (2, 'e', 0),
       (2, 'i', 1),
       (3, 'a', 0),
       (3, 'u', 1),
       (3, 'e', 0),
       (3, 'i', 0),
       (4, 'a', 0),
       (4, 'u', 0),
       (4, 'e', 1),
       (4, 'i', 0),
       (5, 'a', 1),
       (5, 'u', 0),
       (5, 'e', 0),
       (5, 'i', 0),
       (6, 'o', 1),
       (6, 'u', 0),
       (6, 'e', 0),
       (6, 'i', 0),
       (7, 'ga', 1),
       (7, 'gu', 0),
       (7, 'ge', 0),
       (7, 'gi', 0),
       (8, 'ga', 0),
       (8, 'gu', 0),
       (8, 'ge', 0),
       (8, 'gi', 1),
       (9, 'ga', 0),
       (9, 'gu', 0),
       (9, 'ge', 0),
       (9, 'go', 1),
       (10, 'za', 1),
       (10, 'zu', 0),
       (10, 'ze', 0),
       (10, 'zi', 0),
       (11, 'za', 0),
       (11, 'zu', 1),
       (11, 'ze', 0),
       (11, 'zi', 0),
       (12, 'za', 0),
       (12, 'zu', 0),
       (12, 'ze', 0),
       (12, 'zo', 1),
       (13, 'ja', 1),
       (13, 'ju', 0),
       (13, 'je', 0),
       (13, 'ji', 0),
       (14, 'da', 0),
       (14, 'du', 0),
       (14, 'de', 0),
       (14, 'di', 1),
       (15, 'da', 0),
       (15, 'du', 0),
       (15, 'de', 1),
       (15, 'di', 0),
       (16, N'かっさい', 1),
       (16, N'きゅうけい', 1),
       (16, N'おっと', 0),
       (16, N'にんぎょう', 0)

--(17, 'pa', 0), (17, 'pu', 1), (17, 'pe', 0), (17, 'pi', 0),
--(18, 'kitte', 1), (18, 'kiite', 0), (18, 'kitde', 0), (18, 'kite', 0),
--(19, 'oto', 0), (19, 'otto', 1), (19, 'uto', 0), (19, 'utto', 0),
--(20, 'puka', 0), (20, 'fuka', 0), (20, 'buka', 1), (20, 'kabu', 0),
--(21, 'sekken', 1), (21, 'seken', 0), (21, 'seke', 0), (21, 'kesen', 0),
--(22, 'kassai', 1), (22, 'sakai', 0), (22, 'kasai', 0), (22, 'kassa', 0),
--(23, 'nigyou', 0), (23, 'ningyuo', 0), (23, 'ningyo', 0), (23, 'ningyou', 1),
--(24, 'kyuuke', 0), (24, 'kyuukei', 1), (24, 'kyukei', 0), (24, 'kukei', 0),
--(25, 'byoui', 0), (25, 'byoin', 0), (25, 'byouin', 1), (25, 'byoi', 0),
--(26, 'josei', 1), (26, 'jiso', 0), (26, 'jose', 0), (26, 'jisei', 0)

GO
--SELECT * FROM [user];

--get sum of completed lesson of a course
--select sum([time]) as sumTime from
--(select l.ID, [time] from
--(select * from lesson) as l
--join
--(select * from chapter where courseID = 1) as m
--on l.chapterID = m.ID) l
--join
--(select * from lessonCompleted where userID = 1) lc
--on l.ID = lc.lessonID

-- Check if question are correct
--select 1 from
--(select selectedAnswer as ID from chosenAnswer where quizResultID = 1 and questionID = 4) a
--full join
--(select answerID from answer where questionID = 4 and correct = 1) b
--on a.ID = b.answerID
--where a.ID is null or b.answerID is null;

--select top 1 * from quizResult where userID = 1 and lessonID = 2 order by startTime desc;

--get number completed lesson of a chapter
--select count(*) as number from
--(select lessonID as ID from lessonCompleted where userID = 1) as a
--join
--(select ID from lesson where chapterID = 1) as b
--on a.ID = b.ID;

--get last lessonID
--select top 1 lessonID from
--(select ID as chapterID, [index] as chapterIndex from chapter where courseID = 1) as a
--join
--(select chapterID, ID as lessonID, [index] as lessonIndex from lesson) as b on a.chapterID = b.chapterID
--order by chapterIndex desc, lessonIndex desc;

--get first uncompleted lessonID
--select top 1 lessonID from
--(select chapterIndex, lessonID, lessonIndex from
--(select ID as chapterID, [index] as chapterIndex from chapter where courseID = 1) as a
--join
--(select chapterID, ID as lessonID, [index] as lessonIndex from lesson) as b on a.chapterID = b.chapterID) a
--where lessonID not in
--(select lessonID from lessonCompleted where userID = 1)
--order by chapterIndex, lessonIndex;

insert into post(lessonID, content)
values (16, N'<h3>Lý thuyết</h3>
    <p>Khái niệm biến trong lập trình cũng giống khái niệm biến trong toán học, biến được dùng để đại diện cho một giá trị nào đó.</p>
    <p>Để khai báo biến kiểu số nguyên trong Java bạn sử dụng từ khóa <code>int</code> (<code>int</code> và viết tắt của <code>integer</code>) giống như sau:</p>
    <pre class=" language-cpp"><code class=" language-cpp"><span class="token keyword">int</span> tên_biến<span class="token punctuation">;</span></code></pre>
    <p>Ví dụ về chương trình tạo và sử dụng biến kiểu số nguyên:</p>
    <pre class=" language-java"><code class=" language-java"><span class="token keyword">public</span> <span class="token keyword">class</span> <span class="token class-name">Variable</span> <span class="token punctuation">{</span>
        <span class="token keyword">public</span> <span class="token keyword">static</span> <span class="token keyword">void</span> <span class="token function">main</span><span class="token punctuation">(</span><span class="token class-name">String</span><span class="token punctuation">[</span><span class="token punctuation">]</span> args<span class="token punctuation">)</span> <span class="token punctuation">{</span>
            <span class="token comment">// Khai báo biến a kiểu số nguyên</span>
            <span class="token keyword">int</span> a<span class="token punctuation">;</span>
            <span class="token comment">// Gán giá trị cho a = 5</span>
            a <span class="token operator">=</span> <span class="token number">5</span><span class="token punctuation">;</span>
            <span class="token comment">// Hiển thị ra màn hình giá trị của biến a</span>
            <span class="token class-name">System</span><span class="token punctuation">.</span>out<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span><span class="token string">"a = "</span> <span class="token operator">+</span> a<span class="token punctuation">)</span><span class="token punctuation">;</span>
        <span class="token punctuation">}</span>
    <span class="token punctuation">}</span></code></pre>
    <p>Kết quả khi chạy chương trình:</p>
    <pre class=" language-markup"><code class=" language-markup">a = 5</code></pre>
    <p>Bạn còn có thể vừa khai báo vừa gán giá trị cho biến trong 1 câu lệnh giống như chương trình sau:</p>
    <pre class=" language-java"><code class=" language-java"><span class="token keyword">public</span> <span class="token keyword">class</span> <span class="token class-name">Variable</span> <span class="token punctuation">{</span>
        <span class="token keyword">public</span> <span class="token keyword">static</span> <span class="token keyword">void</span> <span class="token function">main</span><span class="token punctuation">(</span><span class="token class-name">String</span><span class="token punctuation">[</span><span class="token punctuation">]</span> args<span class="token punctuation">)</span> <span class="token punctuation">{</span>
            <span class="token comment">// Khai báo biến a kiểu số nguyên và gán giá trị cho a = 438</span>
            <span class="token keyword">int</span> a <span class="token operator">=</span> <span class="token number">438</span><span class="token punctuation">;</span>
            <span class="token comment">// Khai báo biến b kiểu số nguyên và gán giá trị cho b = 238</span>
            <span class="token keyword">int</span> b <span class="token operator">=</span> <span class="token number">238</span><span class="token punctuation">;</span>
            <span class="token comment">// Hiển thị ra màn hình hiệu của a và b</span>
            <span class="token class-name">System</span><span class="token punctuation">.</span>out<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span><span class="token string">"a - b = "</span> <span class="token operator">+</span> <span class="token punctuation">(</span>a <span class="token operator">-</span> b<span class="token punctuation">)</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
        <span class="token punctuation">}</span>
    <span class="token punctuation">}</span></code></pre>
    <p>Kết quả khi chạy chương trình:</p>
    <pre class=" language-markup"><code class=" language-markup">a - b = 200</code></pre>
    <p>Ngoài ra, bạn cũng có thể khai báo và gán giá trị cho nhiều biến trong 1 câu lệnh như giống như chương trình sau:</p>
    <pre class=" language-java"><code class=" language-java"><span class="token keyword">public</span> <span class="token keyword">class</span> <span class="token class-name">Variable</span> <span class="token punctuation">{</span>
        <span class="token keyword">public</span> <span class="token keyword">static</span> <span class="token keyword">void</span> <span class="token function">main</span><span class="token punctuation">(</span><span class="token class-name">String</span><span class="token punctuation">[</span><span class="token punctuation">]</span> args<span class="token punctuation">)</span> <span class="token punctuation">{</span>
            <span class="token keyword">int</span> a <span class="token operator">=</span> <span class="token number">438</span><span class="token punctuation">,</span> b <span class="token operator">=</span> <span class="token number">238</span><span class="token punctuation">;</span>
            <span class="token class-name">System</span><span class="token punctuation">.</span>out<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span><span class="token string">"a - b = "</span> <span class="token operator">+</span> <span class="token punctuation">(</span>a <span class="token operator">-</span> b<span class="token punctuation">)</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
        <span class="token punctuation">}</span>
    <span class="token punctuation">}</span></code></pre>
    <p>Khi đặt tên cho biến bạn cần tuân theo 1 số nguyên tắc sau:</p>
    <ul>
    <li>Tên biến có thể có chữ cái, chữ số và dấu gạch dưới nhưng ký tự đầu tiên của tên biến bắt buộc phải là dấu gạch dưới hoặc chữ cái.
    <ul></ul>
    </li>
    <li>Tên biến không được có khoảng trắng.</li>
    <li>Tên biến không được trùng với các từ khóa như<span>&nbsp;</span><code>int, float, double, class, ...</code>&nbsp;(bạn có thể tham khảo thêm về các từ khóa trong Java <a href="https://codelearn.io/sharing/tong-hop-50-keyword-can-nho-trong-java">tại đây</a>)</li>
    </ul>
    <p>Một số tên biến hợp lệ:</p>
    <pre class=" language-cpp"><code class=" language-cpp"><span class="token keyword">int</span> a<span class="token punctuation">;</span>
    <span class="token keyword">int</span> _a<span class="token punctuation">;</span>
    <span class="token keyword">int</span> a10_<span class="token punctuation">;</span></code></pre>
    <p>Một số tên biến không hợp lệ:</p>
    <pre class=" language-cpp"><code class=" language-cpp"><span class="token keyword">int</span> <span class="token number">10</span>a<span class="token punctuation">;</span>
    <span class="token keyword">int</span> a <span class="token number">10</span><span class="token punctuation">;</span>
    <span class="token keyword">int</span> <span class="token keyword">int</span><span class="token punctuation">;</span>
    </code></pre>
    ');