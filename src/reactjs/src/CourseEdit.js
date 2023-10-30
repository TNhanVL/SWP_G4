import {useParams} from 'react-router-dom';
import Navbar from './Navbar';
import {useEffect, useState} from "react";
import UserService from "./service/UserService";
import Footer from "./Footer";

function CourseEdit() {

    const {courseID} = useParams()
    const [learner, setLearner] = useState(null)

    useEffect(() => {
        let learner = UserService.learnerLoggedIn()
        learner.then(res => {
            setLearner(res)
        })
    }, []);

    return (
        <div className='CourseEdit'>
            <Navbar learner={learner}/>

            <div id="body-editCourse">

                <div className="leftPane">

                    <div className="accordion moocTitles" id="accordionExample">

                        <div className="accordion-item" onClick="showEditMoocByID(this)">
                            <h4 className="accordion-header">
                                <div><a href=""><i className="mooc-handle fas fa-ellipsis-v ms-3"
                                                   style={{cursor: "all-scroll"}}></i></a></div>
                                <button className="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                                        data-bs-target="#collapse_1" aria-expanded="false" aria-controls="collapseOne">
                                    Mooc #1
                                </button>

                            </h4>
                            <div id="collapse_1" className="accordion-collapse collapse"
                                 data-bs-parent="#accordionExample"
                            >

                                <div className="accordion-body lessonTitles">

                                    <div className="accordion-item" onClick="showEditLessonByID(this)">
                                        <h4 className="accordion-header">
                                            <div><i className="lesson-handle fas fa-ellipsis-v ms-3"
                                                    style={{cursor: "all-scroll"}}></i>
                                            </div>
                                            <button className="btn chosing" type="button">
                                                Lesson #1
                                            </button>
                                        </h4>
                                    </div>

                                    <div className="accordion-item" onClick="showEditLessonByID(this)">
                                        <h4 className="accordion-header">
                                            <div><i className="lesson-handle fas fa-ellipsis-v ms-3"
                                                    style={{cursor: "all-scroll"}}></i>
                                            </div>
                                            <button className="btn" type="button">
                                                Lesson #2
                                            </button>
                                        </h4>
                                    </div>

                                    <div className="accordion-item" onClick="showEditLessonByID(this)">
                                        <h4 className="accordion-header">
                                            <div><i className="lesson-handle fas fa-ellipsis-v ms-3"
                                                    style={{cursor: "all-scroll"}}></i>
                                            </div>
                                            <button className="btn" type="button">
                                                Lesson #3
                                            </button>
                                        </h4>
                                    </div>

                                    <div className="accordion-btn">
                                <span onClick="showAddLessonArea()" className="btn btn-primary w-100 p-2 text-center">
                                    Add new lesson</span>
                                    </div>

                                </div>

                            </div>
                        </div>

                        <div className="accordion-item" onClick="showEditMoocByID(this)">
                            <h4 className="accordion-header">
                                <div><a href=""><i className="mooc-handle fas fa-ellipsis-v ms-3"
                                                   style={{cursor: "all-scroll"}}></i></a></div>
                                <button className="accordion-button collapsed chosing" type="button"
                                        data-bs-toggle="collapse"
                                        data-bs-target="#collapse_2" aria-expanded="false" aria-controls="collapse_2">
                                    Mooc #2
                                </button>
                            </h4>
                            <div id="collapse_2" className="accordion-collapse collapse"
                                 data-bs-parent="#accordionExample"
                            >
                                <div className="accordion-body">
                                </div>
                            </div>
                        </div>

                        <div className="accordion-item" onClick="showEditMoocByID(this)">
                            <h4 className="accordion-header">
                                <div><a href=""><i className="mooc-handle fas fa-ellipsis-v ms-3"
                                                   style={{cursor: "all-scroll"}}></i></a></div>
                                <button className="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                                        data-bs-target="#collapse_3" aria-expanded="false" aria-controls="collapse_3">
                                    Mooc #3
                                </button>
                            </h4>
                            <div id="collapse_3" className="accordion-collapse collapse"
                                 data-bs-parent="#accordionExample">
                                <div className="accordion-body">
                                </div>
                            </div>
                        </div>

                        <div className="accordion-btn">
                            <button onClick="showAddMoocArea()" className="btn btn-primary w-100 p-2">Add new mooc
                            </button>
                        </div>
                    </div>

                </div>

                <div className="rightPane p-4 ">

                    <div className="addMooc" id="addMooc">
                        <div>
                            <h1 className="text-center">Add new mooc</h1>
                            <div className="mt-4">


                                <form method="POST" action="" className="row g-3">

                                    <div className="col-md-4 col-sm-6">
                                        <label htmlFor="MoocID" className="form-label">Mooc ID</label>
                                        <input type="text" className="form-control" id="MoocID"
                                               value="Auto increasing ID"
                                               disabled=""/>
                                    </div>

                                    <div className="col-md-8 col-sm-6">
                                    </div>

                                    <div className="col-6">
                                        <label htmlFor="MoocTitle" className="form-label">Mooc Title</label>
                                        <input type="text" className="form-control" id="MoocTitle" value=""
                                               name="MoocTitle"
                                               placeholder="Ex: Java Introduction" required=""/>
                                    </div>

                                    <div className="col-6">
                                        <label htmlFor="MoocDescription" className="form-label">Mooc Description</label>
                                        <input type="text" className="form-control" id="MoocDescription" value=""
                                               name="MoocDescription"
                                               placeholder="Ex: This mooc help for newbie practice with Java code"
                                               required=""/>
                                    </div>

                                    <div className="col-12">
                                        <button className="btn btn-primary" type="submit">Add mooc</button>
                                    </div>
                                </form>

                            </div>
                        </div>
                    </div>

                    <div className="editMooc" id="editMooc">
                        <div>
                            <div className="mt-4">
                                <h1 className="text-center">Edit mooc</h1>

                                <form method="POST" action="" className="row g-3">

                                    <div className="col-md-4 col-sm-6">
                                        <label htmlFor="MoocID" className="form-label">Mooc ID</label>
                                        <input type="text" className="form-control" id="MoocID" value="" disabled=""/>
                                    </div>

                                    <div className="col-md-8 col-sm-6">
                                    </div>

                                    <div className="col-6">
                                        <label htmlFor="MoocTitle" className="form-label">Mooc Title</label>
                                        <input type="text" className="form-control" id="MoocTitle" value=""
                                               name="MoocTitle"
                                               placeholder="Ex: " required=""/>
                                    </div>

                                    <div className="col-6">
                                        <label htmlFor="MoocDescription" className="form-label">Mooc Description</label>
                                        <input type="text" className="form-control" id="MoocDescription" value=""
                                               name="MoocDescription" placeholder="Ex: " required=""/>
                                    </div>


                                    <div className="col-12">
                                        <button className="btn btn-primary" type="submit">Update mooc</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>

                    <div className="addLesson" id="addLesson">
                        <div className="head">
                            <h1 className="text-center">Add new lesson</h1>

                            <form method="POST" action="" className="row g-3">


                                <div className="col-6">
                                    <label htmlFor="lessonTitle" className="form-label">Lesson ID</label>
                                    <input type="text" className="form-control" id="LessonID" value="Auto Increasing ID"
                                           name="LessonTitle" required="" readOnly/>
                                </div>
                                <div className="col-6"></div>

                                <div className="col-6">
                                    <label htmlFor="lessonTitle" className="form-label">Lesson name</label>
                                    <input type="text" className="form-control" id="LessonTitle" value=""
                                           name="LessonTitle"
                                           placeholder="Ex: How to declare a variable in Java program" required=""/>
                                </div>


                                <div className="col-6">
                                    <label htmlFor="lessonTime" className="form-label">Lesson Time learning</label>
                                    <input type="text" className="form-control" id="LessonDescription" value=""
                                           name="LessonDescription" placeholder="Time need to complete this lesson"
                                           required=""/>
                                </div>


                                <div className="col-6">
                                    <label htmlFor="lessonType" className="form-label col-12">Type of lesson</label>
                                    <select value="1" required="required" onChange="showDiv()" name="typeOfLesson"
                                            id="lessonType" className="col-12 form-control">
                                        <option value=""></option>
                                        <option value="0">Video</option>
                                        <option value="1">Quiz</option>
                                        <option value="2">Reading</option>
                                    </select>
                                </div>
                            </form>

                        </div>


                        <div id="lessonType_1">
                            <div className="col-6">
                                <label htmlFor="videoURL" className="form-label">Provide video URL</label>
                                <input type="file" className="form-control" id="videoURL" value="" name="videoURL"
                                       placeholder="Browse" required=""/>
                            </div>
                        </div>
                        <div id="lessonType_2" className="row col-12">
                            <div className="row">

                                <div className="accordion moocTitles col-3 mt-5" id="accordionExample_1">
                                    <div className="accordion-item">
                                        <h4 className="accordion-header">
                                            <button className="accordion-button collapsed" type="button"
                                                    data-bs-toggle="collapse"
                                                    data-bs-target="#collapse_1_1" aria-expanded="false"
                                                    aria-controls="collapseOne">
                                                Question List
                                            </button>

                                        </h4>
                                        <div id="collapse_1_1" className="accordion-collapse collapse"
                                             data-bs-parent="#accordionExample_1">

                                            <div className="accordion-body lessonTitles">

                                                <div className="accordion-item">
                                                    <h4 className="accordion-header">
                                                        <div><i className="lesson-handle fas fa-ellipsis-v ms-3"
                                                                style={{cursor: "all-scroll"}}></i>
                                                        </div>
                                                        <button className="btn chosing" type="button">
                                                            Question 1
                                                        </button>
                                                    </h4>
                                                </div>

                                                <div className="accordion-item">
                                                    <h4 className="accordion-header">
                                                        <div><i className="lesson-handle fas fa-ellipsis-v ms-3"
                                                                style={{cursor: "all-scroll"}}></i>
                                                        </div>
                                                        <button className="btn" type="button">
                                                            Question 2
                                                        </button>
                                                    </h4>
                                                </div>

                                                <div className="accordion-item">
                                                    <h4 className="accordion-header">
                                                        <div><i className="lesson-handle fas fa-ellipsis-v ms-3"
                                                                style={{cursor: "all-scroll"}}></i>
                                                        </div>
                                                        <button className="btn" type="button">
                                                            Question 3
                                                        </button>
                                                    </h4>
                                                </div>

                                                <div className="accordion-btn">
                                            <span onClick="showAddLessonArea()"
                                                  className="btn btn-primary w-100 p-2 text-center">
                                                Add new lesson</span>
                                                </div>

                                            </div>

                                        </div>
                                    </div>
                                </div>

                                <div id="questionCreated" className="col-9">

                                    <div id="question_create_area" className="col-12 mt-5">
                                        <div className="question row col-12">
                                            <h5>Question 1</h5>
                                            <div className="col-6">
                                                <label htmlFor="questionType" className="form-label col-12">Type of
                                                    question</label>
                                                <select onChange="showShowQuestionOfThisType()" value=""
                                                        required="required"
                                                        name="questionType" id="questionType"
                                                        className="col-12 form-control">
                                                    <option value=""></option>
                                                    <option value="0">Câu hỏi hình ảnh (ảnh trong máy)</option>
                                                    <option value="1">Câu hỏi hình ảnh (link ảnh)</option>
                                                    <option value="2">Câu hỏi text</option>
                                                </select>
                                            </div>

                                            <div className="question-types col-6">
                                                <div id="questionType_0" className="col-12">
                                                    <label htmlFor="questionType_0_content" className="form-label">Browser
                                                        the
                                                        question</label>
                                                    <input type="file" className="form-control"
                                                           id="questionType_0_content" value=""
                                                           name="questionType_0_content" placeholder="Chose a question"
                                                           required=""/>
                                                </div>
                                                <div id="questionType_1" className="col-12">
                                                    <label htmlFor="questionType_1_content" className="form-label">Enter
                                                        question
                                                        URL</label>
                                                    <input type="text" className="form-control"
                                                           id="questionType_1_content" value=""
                                                           name="questionType_1_content" placeholder="URL question"
                                                           required=""/>
                                                </div>
                                                <div id="questionType_2" className="col-12">
                                                    <label htmlFor="questionType_2_content" className="form-label">Enter
                                                        the
                                                        question</label>
                                                    <textarea name="questionType_2_content" id="questionType_2_content"
                                                              cols="30" rows="3"></textarea>
                                                </div>
                                            </div>
                                        </div>

                                        <div className="anwer row col-12 mt-3">
                                            <div className="col-6">
                                                <label htmlFor="answerType" className="form-label">Type of
                                                    answer</label>
                                                <select onChange="showShowAnswerOfThisType(this)" value="0"
                                                        required="required"
                                                        name="answerType" id="answerType"
                                                        className="col-12 form-control">
                                                    <option value=""></option>
                                                    <option value="0">Multiple choice</option>
                                                    <option value="1">Chose all correct</option>
                                                </select>
                                            </div>

                                            <div className="col-6">

                                                <div id="answer_type_0">
                                                    <p>Answer content</p>


                                                    <span onClick="addAnswerRatio()" className=" btn btn-primary mt-2"
                                                          id="addRatio">Add
                                                Answer</span>
                                                </div>
                                                <div id="answer_type_1">
                                                    <p>Answer content</p>
                                                    <span onClick="addAnswerCheckbox()"
                                                          className=" btn btn-primary mt-2"
                                                          id="addCheckbox">Add Answer</span>
                                                </div>
                                            </div>
                                        </div>
                                        <span onClick="createQuestion()" className="btn btn-primary mt-3 col-3 ml-5">Create
                                    question</span>
                                    </div>
                                </div>
                            </div>

                        </div>
                        <div id="lessonType_3" className="mt-3">
                            <div id="editor">
                                <p>This is some sample content.</p>
                            </div>
                        </div>
                        <div className="col-12">
                            <button className="btn btn-primary mt-5 col-12" type="submit">Add lesson</button>
                        </div>
                    </div>

                    <div className="editLesson" id="editLesson">

                        <div className="head">
                            <h1 className="text-center">Edit lesson</h1>
                            <form method="POST" action="" className="row g-3">

                                <div className="col-6">
                                    <label htmlFor="lessonTitle" className="form-label">Lesson ID</label>
                                    <input type="text" className="form-control" id="LessonID-edit" value=""
                                           name="LessonTitle"
                                           required="" readOnly/>
                                </div>
                                <div className="col-6"></div>

                                <div className="col-6">
                                    <label htmlFor="lessonTitle" className="form-label">Lesson name</label>
                                    <input type="text" className="form-control" id="LessonTitle-edit" value=""
                                           name="LessonTitle"
                                           placeholder="Ex: How to declare a variable in Java program" required=""/>
                                </div>

                                <div className="col-6">
                                    <label htmlFor="lessonTime" className="form-label">Lesson Time learning</label>
                                    <input type="text" className="form-control" id="LessonTime-edit" value=""
                                           name="LessonDescription" placeholder="Time need to complete this lesson"
                                           required=""/>
                                </div>


                                <div className="col-6">
                                    <label htmlFor="lessonType" className="form-label col-12">Type of lesson</label>
                                    <select value="1" required="required" onChange="showDivEdit()" name="typeOfLesson"
                                            id="lessonType-edit" className="col-12 form-control">
                                        <option value=""></option>
                                        <option value="0">Video</option>
                                        <option value="1">Quiz</option>
                                        <option value="2">Reading</option>
                                    </select>
                                </div>
                            </form>

                        </div>


                        <div id="lessonType_1-edit">
                            <div className="col-6">
                                <label htmlFor="videoURL-edit" className="form-label">Provide video URL</label>
                                <input type="file" className="form-control" id="videoURL-edit" value="" name="videoURL"
                                       placeholder="Browse" required=""/>
                            </div>
                        </div>
                        <div id="lessonType_2-edit" className="row col-12">
                            <div className="row">

                                <div className="accordion moocTitles col-3 mt-5" id="accordionExample_2">
                                    <div className="accordion-item">
                                        <h4 className="accordion-header">
                                            <button className="accordion-button collapsed" type="button"
                                                    data-bs-toggle="collapse"
                                                    data-bs-target="#collapse_1_2" aria-expanded="false"
                                                    aria-controls="collapseOne">
                                                Question List
                                            </button>

                                        </h4>
                                        <div id="collapse_1_2" className="accordion-collapse collapse"
                                             data-bs-parent="#accordionExample_2">

                                            <div className="accordion-body lessonTitles">

                                                <div className="accordion-item">
                                                    <h4 className="accordion-header">
                                                        <div><i className="lesson-handle fas fa-ellipsis-v ms-3"
                                                                style={{cursor: "all-scroll"}}></i>
                                                        </div>
                                                        <button className="btn chosing" type="button">
                                                            Question 1
                                                        </button>
                                                    </h4>
                                                </div>


                                                <div className="accordion-btn">
                                            <span onClick="showAddLessonArea()"
                                                  className="btn btn-primary w-100 p-2 text-center">
                                                Add new lesson</span>
                                                </div>

                                            </div>

                                        </div>
                                    </div>
                                </div>

                                <div id="questionCreated" className="col-9">

                                    <div id="question_create_area_edit" className="col-12 mt-5">
                                        <div className="question row col-12">
                                            <h5>Question 1</h5>
                                            <div className="col-6">
                                                <label htmlFor="questionType-edit" className="form-label col-12">Type of
                                                    question</label>
                                                <select onChange="showShowQuestionOfThisType()" value=""
                                                        required="required"
                                                        name="questionType" id="questionType-edit"
                                                        className="col-12 form-control">
                                                    <option value=""></option>
                                                    <option value="0">Câu hỏi hình ảnh (ảnh trong máy)</option>
                                                    <option value="1">Câu hỏi hình ảnh (link ảnh)</option>
                                                    <option value="2">Câu hỏi text</option>
                                                </select>
                                            </div>

                                            <div className="question-types col-6">
                                                <div id="questionType_0-edit" className="col-12">
                                                    <label htmlFor="questionType_0_content_edit" className="form-label">Browser
                                                        the
                                                        question</label>
                                                    <input type="file" className="form-control"
                                                           id="questionType_0_content_edit" value=""
                                                           name="questionType_0_content_edit"
                                                           placeholder="Chose a question"
                                                           required=""/>
                                                </div>
                                                <div id="questionType_1-edit" className="col-12">
                                                    <label for="lessonTime-edit" className="form-label">Enter question
                                                        URL</label>
                                                    <input type="text" className="form-control"
                                                           id="questionType_1_content-edit" value=""
                                                           name="questionType_1_content-edit" placeholder="URL question"
                                                           required=""/>
                                                </div>
                                                <div id="questionType_2-edit" className="col-12">
                                                    <label for="questionType_2_content-edit" className="form-label">Enter
                                                        the question</label>
                                                    <textarea name="" id="questionType_2_content-edit" cols="30"
                                                              rows="3"></textarea>
                                                </div>
                                            </div>
                                        </div>

                                        <div className="anwer row col-12 mt-3">
                                            <div className="col-6">
                                                <label for="answerType-edit" className="form-label">Type of
                                                    answer</label>
                                                <select onchange="showShowAnswerOfThisType(this)" value=""
                                                        required="required"
                                                        name="answer_0_0_type" id="answerType-edit"
                                                        className="col-12 form-control">
                                                    <option value=""></option>
                                                    <option value="0">Multiple choice</option>
                                                    <option value="1">Chose all correct</option>
                                                </select>
                                            </div>

                                            <div className="col-6">

                                                <div id="answer_type_0_edit">
                                                    <p>Answer content</p>
                                                    <span onclick="addAnswerRatio()" className=" btn btn-primary mt-2"
                                                          id="addRatio">Add
                                                Answer</span>
                                                </div>
                                                <div id="answer_type_1_edit">
                                                    <p>Answer content</p>
                                                    <span onclick="addAnswerCheckbox()"
                                                          className=" btn btn-primary mt-2"
                                                          id="addCheckbox">Add Answer</span>
                                                </div>
                                            </div>
                                        </div>
                                        <span className="btn btn-primary mt-3 col-3 ml-5">Update question</span>
                                    </div>
                                </div>
                            </div>

                        </div>
                        <div id="lessonType_3-edit" className="mt-3">
                            <div id="editor-edit">
                                <p>This is some sample content.</p>
                            </div>
                        </div>
                        <div className="col-12">
                            <button className="btn btn-primary mt-5 col-12" type="submit">Update Lesson</button>
                        </div>

                    </div>
                </div>

            </div>

            <Footer/>
        </div>
    );
}

export default CourseEdit;