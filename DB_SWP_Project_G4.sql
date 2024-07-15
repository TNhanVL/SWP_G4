USE master;
go

IF EXISTS (SELECT name
           FROM sys.databases
           WHERE name = N'DB_SWP_Project_G4')
    BEGIN
        ALTER DATABASE [DB_SWP_Project_G4] SET OFFLINE WITH ROLLBACK IMMEDIATE;
        ALTER DATABASE [DB_SWP_Project_G4] SET ONLINE;
        DROP DATABASE [DB_SWP_Project_G4];
    END

CREATE DATABASE DB_SWP_Project_G4
GO

USE DB_SWP_Project_G4
GO

CREATE TABLE [Admin]
(
    adminId    INT IDENTITY (1,1) PRIMARY KEY,
    username   VARCHAR(50) NOT NULL,
    [password] VARCHAR(50) NOT NULL
);
GO

CREATE TABLE Country
(
    countryId INT PRIMARY KEY,
    name      NVARCHAR(60) NOT NULL
);
GO

CREATE TABLE Organization
(
    organizationId INT IDENTITY (1,1) PRIMARY KEY,
    -- giá trị bắt đầu là 1, giá trị tăng thêm là 1
    countryId      INT FOREIGN KEY REFERENCES [Country],
    username       VARCHAR(50) NOT NULL,
    [password]     VARCHAR(50) NOT NULL,
    email          VARCHAR(320),
    picture        TEXT,
    [name]         VARCHAR(100),
    [description]  NVARCHAR(100),
    [status]       int DEFAULT 0
);
GO

CREATE TABLE [Learner]
(
    learnerId     INT IDENTITY (1,1) PRIMARY KEY,
    picture       TEXT,
    username      VARCHAR(50)   NOT NULL,
    [password]    VARCHAR(50)   NOT NULL,
    email         VARCHAR(320),
    emailVerified BIT DEFAULT 0 NOT NULL,
    [firstName]   NVARCHAR(50),
    [lastName]    NVARCHAR(50),
    birthday      DATE,
    countryId     INT FOREIGN KEY REFERENCES [Country],
    [status]      int DEFAULT 0
);
GO

CREATE TABLE Instructor
(
    instructorId   INT IDENTITY (1,1) PRIMARY KEY,
    organizationId INT         NOT NULL,
    countryId      INT FOREIGN KEY REFERENCES [Country],
    username       VARCHAR(50) NOT NULL,
    [password]     VARCHAR(50) NOT NULL,
    email          VARCHAR(320),
    picture        TEXT,
    [firstName]    NVARCHAR(50),
    [lastName]     NVARCHAR(50),
    [status]       int DEFAULT 0,
    FOREIGN KEY (organizationId) REFERENCES Organization (organizationId)
);
GO

CREATE TABLE Course
(
    courseId       INT IDENTITY (1,1) NOT NULL PRIMARY KEY,
    organizationId INT                NOT NULL,
    name           NVARCHAR(50)       NOT NULL,
    [picture]      TEXT,
    [description]  NVARCHAR(50),
    verified       BIT DEFAULT 0,
    totalTime      INT DEFAULT 0,
    price          NUMERIC(10, 2)     NOT NULL,
    rate           NUMERIC(2, 1),
    numberOfRated  INT DEFAULT 0,
    FOREIGN KEY (organizationId) REFERENCES Organization (organizationId)
);
GO

CREATE TABLE Sale
(
    saleID   INT IDENTITY (1,1) NOT NULL PRIMARY KEY,
    courseId INT                NOT NULL,
    price    NUMERIC(10, 2)     NOT NULL,
    startAt  DATETIME,
    endAt    DATETIME,
    FOREIGN KEY (courseId) REFERENCES Course (courseId)
);
GO

CREATE TABLE [Transaction]
(
    transactionId INT IDENTITY (1,1) NOT NULL PRIMARY KEY,
    learnerId     INT                NOT NULL,
    courseId      INT                NOT NULL,
    originPrice   NUMERIC(10, 2)     NOT NULL,
    price         NUMERIC(10, 2)     NOT NULL,
    type          INT DEFAULT 0,
    description   NTEXT,
    status        INT DEFAULT 0,
    FOREIGN KEY (learnerId) REFERENCES [Learner] (learnerId),
    FOREIGN KEY (courseId) REFERENCES Course (courseId)
);
GO

CREATE TABLE Instruct
(
    instructId   INT IDENTITY (1,1) NOT NULL PRIMARY KEY,
    courseId     INT                NOT NULL,
    instructorId INT                NOT NULL,
    FOREIGN KEY (instructorId) REFERENCES [Instructor] (instructorId),
    FOREIGN KEY (courseId) REFERENCES Course (courseId),
    UNIQUE (instructorId, courseId)
);
GO

CREATE TABLE Review
(
    reviewId     INT IDENTITY (1,1) NOT NULL PRIMARY KEY,
    instructorId INT,
    courseId     INT,
    reviewed     BIT DEFAULT 0      NOT NULL,
    verified     BIT DEFAULT 0      NOT NULL,
    note         NTEXT,
    FOREIGN KEY (instructorId) REFERENCES [Instructor] (instructorId),
    FOREIGN KEY (courseId) REFERENCES Course (courseId),
    UNIQUE (instructorId, courseId)
);
GO

CREATE TABLE Cart
(
    learnerId   INT NOT NULL,
    courseId INT NOT NULL,
    FOREIGN KEY (learnerId) REFERENCES [Learner] (learnerId),
    FOREIGN KEY (courseId) REFERENCES Course (courseId),
    UNIQUE (learnerId, courseId)
);
GO

CREATE TABLE Chapter
(
    chapterId     INT IDENTITY (1,1) NOT NULL PRIMARY KEY,
    courseId      INT,
    [index]       INT                NOT NULL,
    name          NVARCHAR(50),
    [description] NVARCHAR(50),
    totalTime     INT DEFAULT 0      NOT NULL,
    FOREIGN KEY (courseId) REFERENCES Course (courseId)
);
GO

CREATE TABLE Lesson
(
    lessonId        INT IDENTITY (1,1) NOT NULL PRIMARY KEY,
    chapterId       INT,
    quizId          INT,
    name            NVARCHAR(50),
    description     NTEXT,
    percentToPassed INT DEFAULT 80     NOT NULL,
    mustBeCompleted BIT DEFAULT 1      NOT NULL,
    content         NTEXT,
    [index]         INT                NOT NULL,
    [type]          INT                NOT NULL,
    [time]          INT DEFAULT 0      NOT NULL,
    FOREIGN KEY (chapterId) REFERENCES Chapter (chapterId),
);
GO

CREATE TABLE CourseProgress
(
    courseProgressId     INT IDENTITY (1,1)                 NOT NULL PRIMARY KEY,
    learnerId            INT,
    courseId             INT,
    enrolled             BIT      DEFAULT 0                 NOT NULL,
    progressPercent      INT      DEFAULT 0                 NOT NULL,
    completed            BIT      DEFAULT 0                 NOT NULL,
    actionAfterCompleted BIT      DEFAULT 0                 NOT NULL,
    rated                BIT      DEFAULT 0                 NOT NULL,
    rate                 INT      DEFAULT 0                 NOT NULL,
    totalTime            INT      DEFAULT 0                 NOT NULL,
    startAt              DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    FOREIGN KEY (learnerId) REFERENCES [Learner] (learnerId),
    FOREIGN KEY (courseId) REFERENCES Course (courseId)
);
GO

CREATE TABLE ChapterProgress
(
    chapterProgressId INT IDENTITY (1,1)                 NOT NULL PRIMARY KEY,
    chapterId         INT,
    courseProgressId  INT,
    progressPercent   INT      DEFAULT 0,
    completed         BIT      DEFAULT 0                 NOT NULL,
    totalTime         INT      DEFAULT 0,
    startAt           DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    FOREIGN KEY (chapterId) REFERENCES Chapter (chapterId),
    FOREIGN KEY (courseProgressId) REFERENCES CourseProgress (courseProgressId)
);
GO

CREATE TABLE LessonProgress
(
    lessonProgressId  INT IDENTITY (1,1)                 NOT NULL PRIMARY KEY,
    lessonId          INT,
    chapterProgressId INT,
    progressPercent   INT      DEFAULT 0                 NOT NULL,
    completed         BIT      DEFAULT 0                 NOT NULL,
    startAt           DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    FOREIGN KEY (lessonId) REFERENCES Lesson (lessonId),
    FOREIGN KEY (chapterProgressId) REFERENCES ChapterProgress (chapterProgressId)
);
GO

CREATE TABLE Quiz
(
    [quizId]        INT IDENTITY (1,1) NOT NULL PRIMARY KEY,
    lessonId        INT,
    name            NTEXT,
    description     NTEXT,
    noQuestion      INT DEFAULT 0      NOT NULL,
    mustBeCompleted BIT DEFAULT 1      NOT NULL,
    percentToPassed INT DEFAULT 80     NOT NULL,
    FOREIGN KEY (lessonId) REFERENCES lesson (lessonId)
);
GO

ALTER TABLE Lesson
ADD FOREIGN KEY (quizId) REFERENCES Quiz (quizId)

GO

CREATE TABLE Question
(
    [questionId] INT IDENTITY (1,1) NOT NULL PRIMARY KEY,
    quizId       INT,
    [index]      INT                NOT NULL,
    content      NTEXT              NOT NULL,
    [type]       INT                NOT NULL,
    point        INT DEFAULT 1      NOT NULL,
    FOREIGN KEY (quizId) REFERENCES Quiz ([quizId])
);
GO

CREATE TABLE Answer
(
    [answerId] INT IDENTITY (1,1) NOT NULL PRIMARY KEY,
    questionId INT,
    content    NTEXT,
    correct    BIT DEFAULT 0      NOT NULL,
    --True: 1, False: 0
    FOREIGN KEY (questionId) REFERENCES Question (questionId)
);
GO

CREATE TABLE QuizResult
(
    quizResultId          INT IDENTITY (1,1) NOT NULL PRIMARY KEY,
    lessonId              INT,
    lessonProgressId      INT,
    numberOfCorrectAnswer INT,
    numberOfQuestion      INT,
    mark                  INT,
    finished              BIT DEFAULT 0      NOT NULL,
    startAt               DATETIME,
    endAt                 DATETIME,
    FOREIGN KEY (lessonId) REFERENCES Lesson (lessonId),
    FOREIGN KEY (lessonProgressId) REFERENCES [LessonProgress] (lessonProgressId)
);
GO

CREATE TABLE ChosenAnswer
(
    chosenAnswerId INT IDENTITY (1,1) NOT NULL PRIMARY KEY,
    quizResultId   INT,
    answerId       INT,
    correct        BIT DEFAULT 0      NOT NULL,
    FOREIGN KEY (quizResultId) REFERENCES QuizResult (quizResultId),
    FOREIGN KEY (answerId) REFERENCES Answer (answerId)
);
GO

CREATE TABLE Notification
(
    notificationId INT IDENTITY (1,1) NOT NULL PRIMARY KEY,
    learnerId      INT                NOT NULL,
    type           INT,
    description    NTEXT              NOT NULL,
    [read]         BIT                NOT NULL,
    --True: 1, False: 0
    receive_at     DATETIME,
    FOREIGN KEY (learnerId) REFERENCES [Learner] (learnerId)
);
GO

CREATE OR ALTER TRIGGER updateChapterTotalTimeTrigger
    ON Lesson
    AFTER INSERT, UPDATE, DELETE
    AS
BEGIN
    update Chapter
    set Chapter.totalTime = IIF(cal.totalTime IS NULL, 0, cal.totalTime)
    from (select chapterId, sum(time) totalTime
          from Lesson
          group by chapterId) cal
    where Chapter.chapterId in (select distinct chapterId
                                from inserted
                                union
                                select distinct chapterId
                                from deleted)
      and Chapter.chapterId = cal.chapterId
END
GO

CREATE OR ALTER TRIGGER updateCourseTotalTimeTrigger
    ON Chapter
    AFTER INSERT, UPDATE, DELETE
    AS
BEGIN
    update Course
    set Course.totalTime = IIF(cal.totalTime IS NULL, 0, cal.totalTime)
    from (select courseId, sum(totalTime) totalTime
          from Chapter
          group by courseId) cal
    where Course.courseId in (select distinct courseId
                              from inserted
                              union
                              select distinct courseId
                              from deleted)
      and Course.courseId = cal.courseId
END
GO

CREATE OR ALTER TRIGGER updateCourseProgressTrigger
    ON ChapterProgress
    AFTER INSERT, UPDATE, DELETE
    AS
BEGIN
    update CourseProgress
    set CourseProgress.totalTime       = IIF(chapter_progress_info.totalTime IS NULL, 0,
                                             chapter_progress_info.totalTime),
        CourseProgress.completed       = IIF(sumCompleted = numberOfChapter, 1, 0),
        CourseProgress.progressPercent = IIF(chapter_info.sumTime IS NULL OR chapter_info.sumTime = 0 OR
                                             chapter_progress_info.totalTime IS NULL, 0,
                                             ROUND(chapter_progress_info.totalTime * 100.0 / chapter_info.sumTime,
                                                   0))
    from (select ChapterProgress.courseProgressId,
                 courseId,
                 sum(ChapterProgress.totalTime)              totalTime,
                 sum(cast(ChapterProgress.completed as INT)) sumCompleted
          from ChapterProgress
                   join CourseProgress cp on ChapterProgress.courseProgressId = cp.courseProgressId
          group by ChapterProgress.courseProgressId, courseId) chapter_progress_info
             join (select Course.courseId, count(*) numberOfChapter, sum(Course.totalTime) sumTime
                   from Course
                            join Chapter c on Course.courseId = c.courseId
                   group by Course.courseId) chapter_info
                  on chapter_progress_info.courseId = chapter_info.courseId
    where CourseProgress.courseProgressId in (select distinct courseProgressId
                                              from inserted
                                              union
                                              select distinct courseProgressId
                                              from deleted)
      and CourseProgress.courseProgressId = chapter_progress_info.courseProgressId
END
GO

CREATE OR ALTER TRIGGER updateChapterProgressTrigger
    ON LessonProgress
    AFTER INSERT, UPDATE, DELETE
    AS
BEGIN
    update ChapterProgress
    set ChapterProgress.totalTime       = IIF(cal.totalTime IS NULL, 0, cal.totalTime),
        ChapterProgress.completed       = IIF(cal1.completed IS NULL, 0, cal1.completed),
        ChapterProgress.progressPercent = IIF(cal1.sumTime IS NULL OR cal1.sumTime = 0 OR cal.totalTime IS NULL, 0,
                                              round(cal.totalTime * 100.0 / cal1.sumTime, 0))
    from (select lp.chapterProgressId, sum(time) totalTime
          from LessonProgress lp
                   full join Lesson l on lp.lessonId = l.lessonId
          where lp.completed = 1
          group by chapterProgressId) cal
             join
         (select chapter_progress_info_with_chapterId.chapterProgressId,
                 IIF(sumCompleted = sumMustBeCompleted, 1, 0) completed,
                 sumTime
          from (select chapterId, sum(cast(mustBeCompleted as INT)) sumMustBeCompleted, sum(time) sumTime
                from Lesson
                group by chapterId) chapter_info
                   join
               (select ChapterProgress.chapterProgressId, sumCompleted, chapterId
                from (select LessonProgress.chapterProgressId, sum(cast(completed as INT)) sumCompleted
                      from LessonProgress
                      group by LessonProgress.chapterProgressId) chapter_progress_info
                         join ChapterProgress
                              on ChapterProgress.chapterProgressId =
                                 chapter_progress_info.chapterProgressId) chapter_progress_info_with_chapterId
               on chapter_info.chapterId = chapter_progress_info_with_chapterId.chapterId) cal1
         on cal.chapterProgressId = cal1.chapterProgressId
    where ChapterProgress.chapterProgressId in (select distinct chapterProgressId
                                                from inserted
                                                union
                                                select distinct chapterProgressId
                                                from deleted)
      and ChapterProgress.chapterProgressId = cal.chapterProgressId
END
GO

-- insert data

INSERT INTO Country
    (countryId, [name])
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
INSERT INTO [Admin]
    (username, [password])
VALUES ('admin', '0e7517141fb53f21ee439b355b5a1d0a'),
       ('quantri', '0e7517141fb53f21ee439b355b5a1d0a'),
       ('sussy', '80b87ad4e28b6e6c6b0efc1cb797c649'),
       ('dylan12', 'e10adc3949ba59abbe56e057f20f883e')

GO
INSERT INTO [Learner]
(picture, username, [password], email, firstName, lastName, birthday, countryId, [status])
VALUES ('a.jpg', 'ttnhan', '0cc175b9c0f1b6a831c399e269772661', 'nhan12341184@gmail.com', 'Nhan', 'Tran Thanh',
        '1990-01-01', 16, 0),
       ('a.jpg', 'dylan12', 'e10adc3949ba59abbe56e057f20f883e', 'dylan@example.com', 'Huong', 'Nguyen Thi Diem',
        '2003-10-12', 16, 0),
       ('a.jpg', 'sussy', '80b87ad4e28b6e6c6b0efc1cb797c649', 'giangltce170378@fpt.edu.vn', 'Huong', 'Nguyen Thi Diem',
        '2003-10-12', 16, 0),
       ('a.jpg', 'anho1210', '12345678', 'anhvlce171612@fpt.edu.vn', 'An', 'Loc', '2003-10-10', 16, 0)
GO
--password Fpt@123
INSERT INTO Organization
    (countryId, [name], username, password, email, picture, [description])
VALUES (16, 'FPT University', 'fptuni', '5e7c74592ea8dffbfdc20c84de15afea', 'NhanTTCE171358@fpt.edu.vn', 'FPT.png',
        N'Trường đại học top 1 Việt Nam');
GO

-- INSERT INTO Instructor (organizationId, countryId, username, [password], email, picture, [firstName], [lastName], [status])
-- VALUES (1, 1, 'sussybaka', '0cc175b9c0f1b6a831c399e269772661', 'instructor_email@example.com', '', 'Le', 'Truong Giang',
--         1),
--        (1, 1, 'instructor_1', '202cb962ac59075b964b07152d234b70', 'instructor_1@example.com', '', 'John', 'Doe', 1),
--        (2, 2, 'instructor_2', 'c4ca4238a0b923820dcc509a6f75849b', 'instructor_2@example.com', '', 'Jane', 'Doe', 1);
INSERT INTO Instructor (organizationId, countryId, username, [password], email, picture, [firstName], [lastName],
                        [status])
VALUES (1, 1, 'ttnhan', '0cc175b9c0f1b6a831c399e269772661', 'instructor_1@example.com', 'a.jpg', 'John', 'Doe', 0),
       (1, 1, 'instructor_2', '202cb962ac59075b964b07152d234b70', 'instructor_2@example.com', 'a.jpg', 'Jane', 'Doe',
        0),
       (1, 1, 'instructor_3', 'c4ca4238a0b923820dcc509a6f75849b', 'instructor_3@example.com', 'a.jpg', 'Peter',
        'Parker',
        0);


GO
INSERT INTO Course
(name, [picture], [description], organizationId, price, rate, verified, totalTime)
VALUES ('Dekiru Nihongo', 'nihon.png', 'easy', 1, 1, 4.2, 1, 0),
       ('Java advance', 'javaAd.png', 'medium', 1, 2, 4.5, 1, 0),
       ('C++', 'a.png', 'hard', 1, 1.2, 4.7, 1, 0),
       ('PYTHON FOR BEGINNER', 'python.png', 'easy', 1, 1.4, 4.2, 1, 0),
       ('Java fundamentals', 'a.png', 'medium', 1, 2.5, 4.5, 1, 0),
       ('C for beginner', 'c.png', 'hard', 1, 600, 4.7, 1, 0),
       ('JavaScript basics', 'js.png', 'easy', 1, 200, 4.2, 1, 0),
       ('PHP', 'php.png', 'medium', 1, 0.4, 4.5, 1, 0),
       ('Advanced C++', 'a.png', 'hard', 1, 5, 4.7, 1, 0),
       ('Data Structure and Algorithms', 'dsa.png', 'easy', 1, 1, 4.2, 1, 0),
       ('MySQL', 'sql.png', 'medium', 1, 2, 4.5, 1, 0),
       ('C#', 'cc.png', 'hard', 1, 5, 4.7, 1, 0)
GO
INSERT INTO Instruct(courseId, instructorId)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 1),
       (1, 2),
       (2, 3),
       (3, 1),
       (4, 2),
       (1, 3),
       (2, 1),
       (3, 2),
       (4, 3),
       (5, 2),
       (6, 3),
       (7, 1),
       (8, 2),
       (9, 3),
       (10, 1),
       (11, 2),
       (12, 3)
GO
INSERT INTO Cart
    (learnerId, courseId)
VALUES (1, 3)
GO
INSERT INTO CourseProgress(learnerId, courseId)
VALUES (1, 1),
       (1, 2)
GO
INSERT INTO Chapter
    (courseId, [index], name, [description])
VALUES (1, 1, N'Hiragana 。ひらがな', ''),
       (1, 2, N'Katakana 。かたがな', ''),
       (2, 1, N'Test chapter', ''),
       (3, 1, N'Introduction', ''),
       (3, 2, N'Basic statements', '')
GO
INSERT INTO Lesson
(chapterId, name, [index], [type], [time], mustBeCompleted, content)
VALUES (1, 'A, Ka Row', 1, 3, 3, 1, 's4RXDEVFO_E'),
       (1, 'Sa, Ta Row', 2, 3, 3, 1, 'J9MvqJnj5kQ'),
       (1, 'Practice 1: Choose the pronunciation', 3, 2, 5, 1, ''),
       (1, 'Na, Ha Row', 4, 3, 3, 1, 'rsL86uUTJpw'),
       (1, 'Ma, Ya Row', 5, 3, 3, 1, '_Hk2d4AO-Uk'),
       (1, 'Ra, Wa, N Row', 6, 3, 3, 1, 'AmQ9kmom1v8'),
       (1, 'Dakuon - Ga, Za, Da, Ba', 7, 3, 3, 1, 'lW8V5uMMM-4'),
       (1, 'Handakuon - Pa', 8, 3, 3, 1, 'EZb3fs4Ntgc'),
       (1, 'Sokuon - small つ', 9, 3, 3, 1, 'nGLciw6mZCo'),
       (1, 'Chouon - Long vowels', 10, 3, 3, 1, '5JGjT9Cy2ak'),
       (1, 'Hiragana combination', 11, 3, 3, 1, 'Asy10OI-lFU'),
       (1, 'Dakuon & Handakuon of Hiragana combination', 12, 3, 3, 1, 'V34OFinfTbU'),
       (1, 'Multiple-choice test (10 questions)', 13, 2, 30, 1, ''),
       (2, 'Nihongo2', 1, 2, 5, 1, ''),
       (2, 'You tube video', 2, 3, 3, 1, '5JGjT9Cy2ak'),
       (2, 'Post', 3, 1, 5, 1, ''),
       (3, 'Youtube', 1, 3, 5, 1, 'V34OFinfTbU'),
       (4, 'Object Oriented Programming', 1, 3, 19, 1, '7BVt6OGfVfQ'),
       (4, 'Features of Object Oriented Programming', 2, 3, 27, 1, '5Y74odV3IAI'),
       (4, 'History of C++', 3, 3, 20, 1, '_cbfR690u74'),
       (4, 'Practice 1: Review part 1', 4, 2, 5, 1, ''),
       (5, 'Cout in C++', 1, 3, 5, 1, 'pci97hk17rM'),
       (5, 'Cin in C++', 2, 3, 22, 1, 'HvhxbpJYNM0'),
       (5, 'Array of Objects in C++', 3, 3, 17, 1, 'Hu2tePjqcZ4'),
       (5, 'Using Array Inside Class', 4, 3, 17, 1, 'qvtEl-HCwXc'),
       (5, 'Multiple-choice test (5 questions)', 5, 2, 20, 1, '')

GO
INSERT INTO Quiz (lessonId, name, description)
VALUES (3, 'Practice 1: Choose the pronunciation', 'This is description'),
       (13, 'Multiple-choice test', 'This is description'),
       (21, 'Practice 1: Review part 1', 'This is description'),
       (26, 'Multiple-choice test', 'This is description')
GO
UPDATE Lesson SET quizId = 1 WHERE lessonId = 3;
UPDATE Lesson SET quizId = 2 WHERE lessonId = 13;
UPDATE Lesson SET quizId = 3 WHERE lessonId = 21;
UPDATE Lesson SET quizId = 4 WHERE lessonId = 26;
GO
INSERT INTO Question
    (quizId, [index], content, [type], point)
VALUES (1, 1, 'a.png', 0, 1),
       (1, 2, 'i.png', 0, 1),
       (1, 3, 'u.png', 1, 1),
       (1, 4, 'e.png', 1, 1),
       (1, 5, N'What is the character あ?', 20, 1),
       (1, 6, 'https://statics.gojapan.vn/ufiles/2019/11/5b5bf0532cc51939d51b9798/5dde210b3b77504afb2f9cab.png', 10, 1),
       (2, 1, 'https://statics.gojapan.vn/ufiles/2019/11/5b5bf0532cc51939d51b9798/5ddf8d1a3b7750201c44b5cd.png', 10,
        0.5),--ga
       (2, 2, 'https://statics.gojapan.vn/ufiles/2019/11/5b5bf0532cc51939d51b9798/5ddf8bd13b77504ea52ea0c4.png', 10,
        0.5),--gi
       (2, 3, 'https://statics.gojapan.vn/ufiles/2020/01/5b5bf0532cc51939d51b9798/5e3273cb59e7be49cd345ba4.png', 10,
        0.5),--go
       (2, 4, 'https://statics.gojapan.vn/ufiles/2020/01/5b5bf0532cc51939d51b9798/5e3273d959e7be49cd345ba7.png', 10,
        0.5),--za
       (2, 5, 'https://statics.gojapan.vn/ufiles/2020/01/5b5bf0532cc51939d51b9798/5e3273f459e7be49cd345baa.png', 10,
        0.5),--zu
       (2, 6, 'https://statics.gojapan.vn/ufiles/2020/01/5b5bf0532cc51939d51b9798/5e32741759e7be49cd345bad.png', 10,
        0.5),--zo
       (2, 7, 'https://statics.gojapan.vn/ufiles/2020/01/5b5bf0532cc51939d51b9798/5e327e4259e7be6c8434ba83.png', 10,
        0.5),--da
       (2, 8, 'https://statics.gojapan.vn/ufiles/2020/01/5b5bf0532cc51939d51b9798/5e327e5559e7be64dc788d6e.png', 10,
        0.5),--di
       (2, 9, 'https://statics.gojapan.vn/ufiles/2020/01/5b5bf0532cc51939d51b9798/5e327e7159e7be6a7d27ebbd.png', 10,
        0.5),--de
       (2, 10, N'Which words contain letters in the Ka row?', 21, 0.5),
       (3, 1, N'Which of the following is NOT a key feature of C++?', 21, 1),
       (3, 2, N'Which of the following is the correct syntax to declare an integer variable in C++?', 21, 1),
       (3, 3, N'Which of the following is the correct way to print the value of a variable in C++?', 21, 1),
       (4, 1, N'Which of the following is a control statement in C++?', 21, 1),
       (4, 2, N'Which of the following is a selection statement in C++?', 21, 1),
       (4, 3, N'Which of the following is a looping statement in C++?', 21, 1),
       (4, 4, N'Which of the following is an input statement in C++?', 21, 1),
       (4, 5, N'Which of the following is an output statement in C++?', 21, 1)

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
INSERT INTO Answer
    (questionId, content, correct)
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
       (16, N'にんぎょう', 0),
       (17, 'Object-oriented programming', 0),
       (17, 'Generic programming', 0),
       (17, 'Functional programming', 1),
       (18, 'int x;', 1),
       (18, 'int x = 0;', 0),
       (18, 'x = int;', 0),
       (19, 'cout << x;', 1),
       (19, 'printf("%d", x);', 0),
       (19, 'print(x);', 0),
       (20, 'Declaration statement', 0),
       (20, 'Expression statement', 0),
       (20, 'Selection statement', 1),
       (21, 'if statement', 1),
       (21, 'else statement', 0),
       (21, 'else if statement', 0),
       (22, 'for loop', 1),
       (22, 'while loop', 0),
       (22, 'do while loop', 0),
       (23, 'scanf() function', 0),
       (23, 'cin object', 0),
       (23, 'cin >> x; statement', 1),
       (24, 'cout << x; statement', 1),
       (24, 'printf() function', 0),
       (24, 'print(x) function', 0)

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
-- select * from Instructor where countryId ='1'
-- select * from Learner
-- select * from [Transaction]
-- insert into [Transaction](userId, courseId, originPrice, price, type, description, status)
-- values('2','7','20.00000000','40.00000000','2','dung roi','0')
-- update [Transaction] set courseId = '7' , originPrice = '40',price = '60',type ='3',description ='moi update',status ='1'where userId ='2'
-- delete from [Transaction] where userId ='1'
-- select * from Instruct
-- insert into Instruct(userId, courseId)values ('2','8')
-- delete from Instruct where userId = '1' and courseId = '10'
-- insert into Sale(courseId, price, start_date, end_date)
-- values ('2','20','12/16/2022','12/20/2022')
--     UPDATE Sale set price = '30',start_date ='11/22/2022',end_date ='12/23/2022'where courseId ='2'
--     delete from Sale where courseId = '2'
--  select * from Review
--SELECT * FROM [Learner];
-- insert into Review(userId, courseId, reviewed, verified, note)
-- values ('2','7','0','2','COI LAI KIEN THUC')
-- update Review set courseId = '7',reviewed='0',verified='1',note='thay sai roi' where userId ='2'
-- delete from Review where userId = '1'
--get sum of completed lesson of a course
--select sum([time]) as sumTime from
--(select l.ID, [time] from
--(select * from Lesson) as l
--join
--(select * from Chapter where courseId = 1) as m
--on l.chapterId = m.ID) l
--join
--(select * from lesson_completed where userId = 1) lc
--on l.ID = lc.lessonId

-- Check if question are correct
--select 1 from
--(select selected_answer as ID from ChosenAnswer where quizResultId = 1 and questionId = 4) a
--full join
--(select answerId from Answer where questionId = 4 and correct = 1) b
--on a.ID = b.answerId
--where a.ID is null or b.answerId is null;

--select top 1 * from QuizResult where userId = 1 and lessonId = 2 order by startAt desc;

--get number completed lesson of a Chapter
--select count(*) as number from
--(select lessonId as ID from lesson_completed where userId = 1) as a
--join
--(select ID from Lesson where chapterId = 1) as b
--on a.ID = b.ID;

--get last lessonId
--select top 1 lessonId from
--(select ID as chapterId, [index] as chapterIndex from chapter where courseId = 1) as a
--join
--(select chapterId, ID as lessonId, [index] as lessonIndex from Lesson) as b on a.chapterId = b.chapterId
--order by chapterIndex desc, lessonIndex desc;

--get first uncompleted lessonId
--select top 1 lessonId from
--(select chapterIndex, lessonId, lessonIndex from
--(select ID as chapterId, [index] as chapterIndex from Chapter where courseId = 1) as a
--join
--(select chapterId, ID as lessonId, [index] as lessonIndex from Lesson) as b on a.chapterId = b.chapterId) a
--where lessonId not in
--(select lessonId from lesson_completed where userId = 1)
--order by chapterIndex, lessonIndex;

update Lesson
set content =
        N'<h3>Lý thuyết</h3>
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
        '
where lessonId = 16;
GO

INSERT INTO Notification (learnerId, type, description, [read], receive_at)
VALUES (1, 0, 'Course registration successful: Dekiru Nihongo', 0, CURRENT_TIMESTAMP),
       (1, 0, 'Course registration successful: Java advance', 0, CURRENT_TIMESTAMP);


INSERT INTO [Transaction] (learnerId, courseId, originPrice, price, type, description, status)
VALUES (1, 1, 1.00, 1.00, 0, 'Course registration', 0),
       (1, 2, 2.00, 2.00, 0, 'Course registration', 0);

-- SELECT Course.name
-- FROM [Transaction]
--          JOIN Course
--               ON [Transaction].courseId = Course.courseId
-- WHERE [Transaction].learnerId = 1;

