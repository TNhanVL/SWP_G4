import {useNavigate, useParams} from 'react-router-dom';
import Navbar from './Navbar';
import {useEffect, useState} from "react";
import UserService from "./service/UserService";
import Footer from "./Footer";
import $ from "jquery"
import 'jquery-ui-dist/jquery-ui'
import backend from "./service/Backend";
import popUpAlert from "./service/popUpAlert";
import ListLessonEdit from "./ListLessonEdit";
import {confirmAlert} from 'react-confirm-alert'; // Import
import 'react-confirm-alert/src/react-confirm-alert.css'; // Import css
import Editor from 'ckeditor5-custom-build/build/ckeditor';
import {CKEditor} from '@ckeditor/ckeditor5-react';
import QuestionsEdit from "./QuestionsEdit";

function BlankEdit() {

    return (
        <div className="rightPane p-4 bg-white mt-5 ms-auto me-auto" style={{height: "max-content", width: "500px"}}>
            <h2 className={"text-center"}>Click on some thing to edit</h2>
        </div>
    )
}

function EditCourse({course, afterEditCourse}) {

    const [editCourse, setEditCourse] = useState(null)

    useEffect(() => {
        if (!course) return
        //Get course
        backend.post('course/getByCourseId', {
            courseId: course.id
        }).then(res => {
            if (res) {
                setEditCourse(res)
            }
        })
    }, [course]);

    function tryToSave() {
        if(!editCourse.name){
            popUpAlert.warning("Name can not be empty!")
            return
        }
        if(editCourse.price < 0){
            popUpAlert.warning("Price must be greater than or equal to 0!")
            return
        }
        backend.post('course/update', {
            ...editCourse
        }).then(res => {
            if (!res) {
                popUpAlert.warning("Save Course failed")
            } else {
                popUpAlert.success("Save Successful!")
                afterEditCourse()
            }
        })
    }

    return (
        editCourse &&
        <div className="rightPane p-4 bg-white" style={{height: "max-content"}}>
            <div className="addMooc" id="addMooc">
                <div>
                    <h1 className="text-center">Edit course</h1>
                    <div className="mt-4">
                        <form className="row g-3">
                            <div className="col-12">
                                <label htmlFor="CourseName" className="form-label fw-bold">Course Name</label>
                                <input type="text" className="form-control" id="CourseName" value={editCourse.name}
                                       onChange={e => {
                                           setEditCourse({
                                               ...editCourse,
                                               name: e.target.value
                                           })
                                       }}
                                       placeholder="Ex: Java Introduction" required/>
                            </div>

                            <div className="col-12">
                                <label htmlFor="CourseDescription" className="form-label fw-bold">Course
                                    Description</label>
                                <input type="text" className="form-control" id="CourseDescription"
                                       value={editCourse.description}
                                       onChange={e => {
                                           setEditCourse({
                                               ...editCourse,
                                               description: e.target.value
                                           })
                                       }}
                                       placeholder="Ex: This course help for newbie practice with Java code" required/>
                            </div>

                            <div className="col-6">
                                <label htmlFor="CoursePrice" className="form-label fw-bold">Course Price</label>
                                <input type="number" className="form-control" id="CoursePrice"
                                       value={editCourse.price}
                                       onChange={e => {
                                           setEditCourse({
                                               ...editCourse,
                                               price: e.target.value
                                           })
                                       }}
                                       required/>
                            </div>

                            <div className="col-12">
                                <div className="btn btn-primary" onClick={tryToSave}>Save</div>
                            </div>
                        </form>

                    </div>
                </div>
            </div>
        </div>
    )
}

function EditChapter({chapter, afterEditChapter}) {

    const [editChapter, setEditChapter] = useState(null)

    useEffect(() => {
        if (!chapter) return
        //Get course
        backend.post('chapter/getByChapterId', {
            chapterId: chapter.id
        }).then(res => {
            if (res) {
                setEditChapter(res)
            }
        })
    }, [chapter]);

    function tryToSave() {
        if(!editChapter.name){
            popUpAlert.warning("Name can not be empty!")
            return
        }
        backend.post('chapter/update', {
            ...editChapter
        }).then(res => {
            if (!res) {
                popUpAlert.warning("Save Chapter failed")
            } else {
                popUpAlert.success("Save Successful!")
                afterEditChapter()
            }
        })
    }

    function tryToDelete() {
        confirmAlert({
            title: 'Confirm to Delete',
            message: 'Are you sure to delete this chapter? It will delete all lesson in it!',
            buttons: [
                {
                    label: 'Yes',
                    onClick: () => {
                        backend.post('chapter/delete', {
                            chapterId: chapter.id
                        }).then(res => {
                            if (!res) {
                                popUpAlert.warning("Delete Chapter failed")
                            } else {
                                popUpAlert.success("Delete Successful!")
                                afterEditChapter()
                            }
                        })
                    }
                },
                {
                    label: 'No'
                }
            ]
        });
    }

    return (
        editChapter &&
        <div className="rightPane p-4 bg-white" style={{height: "max-content"}}>
            <div className="addMooc" id="addMooc">
                <div>
                    <h1 className="text-center">Edit chapter</h1>
                    <div className="mt-4">
                        <form className="row g-3">
                            <div className="col-12">
                                <label htmlFor="CourseName" className="form-label fw-bold">Chapter Name</label>
                                <input type="text" className="form-control" id="CourseName"
                                       value={editChapter && editChapter.name}
                                       onChange={e => {
                                           setEditChapter({
                                               ...editChapter,
                                               name: e.target.value
                                           })
                                       }}
                                       placeholder="Ex: If condition" required/>
                            </div>

                            <div className="col-12">
                                <label htmlFor="CourseDescription" className="form-label fw-bold">Chapter
                                    Description</label>
                                <input type="text" className="form-control" id="CourseDescription"
                                       value={editChapter.description}
                                       onChange={e => {
                                           setEditChapter({
                                               ...editChapter,
                                               description: e.target.value
                                           })
                                       }}
                                       placeholder="Ex: This chapter help for newbie practice with Java code" required/>
                            </div>

                            <div className="col-12">
                                <div className="btn btn-primary" onClick={tryToSave}>Save</div>
                                <div className="btn btn-danger ms-2" onClick={tryToDelete}>Delete</div>
                            </div>
                        </form>

                    </div>
                </div>
            </div>
        </div>
    )
}

function EditLesson({lesson, afterEditLesson}) {

    const [editLesson, setEditLesson] = useState(null)

    useEffect(() => {
        if (!lesson) return
        //Get course
        backend.post('lesson/getByLessonId', {
            lessonId: lesson.id
        }).then(res => {
            if (res) {
                setEditLesson(res)
            }
        })
    }, [lesson]);

    function tryToSave() {
        if(!editLesson.name){
            popUpAlert.warning("Name can not be empty!")
            return
        }
        if(editLesson.percentToPassed < 0){
            popUpAlert.warning("Percent to passed must be greater than or equal to 0!")
            return
        }
        if(editLesson.percentToPassed > 100){
            popUpAlert.warning("Percent to passed must be less than or equal to 0!")
            return
        }
        if(editLesson.time <= 0){
            popUpAlert.warning("Time must be greater than 0!")
            return
        }
        backend.post('lesson/update', {
            ...editLesson
        }).then(res => {
            if (!res) {
                popUpAlert.warning("Save Lesson failed")
            } else {
                popUpAlert.success("Save Successful!")
                afterEditLesson()
            }
        })
    }

    function tryToDelete() {
        confirmAlert({
            title: 'Confirm to Delete',
            message: 'Are you sure to delete this lesson? It will delete all thing in it!',
            buttons: [
                {
                    label: 'Yes',
                    onClick: () => {
                        backend.post('lesson/delete', {
                            lessonId: lesson.id
                        }).then(res => {
                            if (!res) {
                                popUpAlert.warning("Delete lesson failed")
                            } else {
                                popUpAlert.success("Delete Successful!")
                                afterEditLesson()
                            }
                        })
                    }
                },
                {
                    label: 'No'
                }
            ]
        });

    }

    return (
        editLesson &&
        <div className="rightPane p-4 bg-white" style={{height: "max-content"}}>
            <div className="addMooc" id="addMooc">
                <div>
                    <h1 className="text-center">Edit lesson</h1>
                    <div className="mt-4">
                        <form className="row g-3">
                            <div className="col-12">
                                <label htmlFor="CourseName" className="form-label fw-bold">Lesson Name</label>
                                <input type="text" className="form-control" id="CourseName"
                                       value={editLesson.name ?? ""}
                                       onChange={e => {
                                           setEditLesson({
                                               ...editLesson,
                                               name: e.target.value
                                           })
                                       }}
                                       placeholder="Ex: If condition" required/>
                            </div>

                            <div className="col-12">
                                <label htmlFor="CourseDescription" className="form-label fw-bold">Lesson
                                    Description</label>
                                <input type="text" className="form-control" id="CourseDescription"
                                       value={editLesson.description ?? ""}
                                       onChange={e => {
                                           setEditLesson({
                                               ...editLesson,
                                               description: e.target.value
                                           })
                                       }}
                                       placeholder="Ex: This lesson help for newbie practice with Java code" required/>
                            </div>

                            <div className="col-3">
                                <label htmlFor="LessonPercentoPassed" className="form-label fw-bold">Percent To
                                    Passed</label>
                                <input type="number" className="form-control" id="LessonPercentoPassed"
                                       value={editLesson.percentToPassed}
                                       onChange={e => {
                                           setEditLesson({
                                               ...editLesson,
                                               percentToPassed: e.target.value
                                           })
                                       }} required/>
                            </div>

                            <div className="col-2 d-flex flex-column align-items-center justify-content-center">
                                <label htmlFor="MustBeCompleted" className="form-check-label fw-bold">Must Be
                                    Completed</label>
                                <input type="checkbox" className="form-check-input" id="MustBeCompleted"
                                       checked={editLesson.mustBeCompleted ? true : false}
                                       onChange={e => {
                                           setEditLesson({
                                               ...editLesson,
                                               mustBeCompleted: e.target.checked
                                           })
                                       }} required/>
                            </div>

                            <div className="col-4">
                                <label htmlFor="Type" className="form-label fw-bold">Type</label>
                                <select className="form-select" aria-label="Default select example"
                                        value={editLesson.type}
                                        onChange={e => {
                                            setEditLesson({
                                                ...editLesson,
                                                type: e.target.value
                                            })
                                        }} required>
                                    <option value="0" selected={editLesson.type == 0 ? true : false}>Video
                                    </option>
                                    <option value="1" selected={editLesson.type == 1 ? true : false}>Post</option>
                                    <option value="2" selected={editLesson.type == 2 ? true : false}>Quiz</option>
                                    <option value="3" selected={editLesson.type == 3 ? true : false}>Youtube video
                                    </option>
                                </select>
                            </div>

                            <div className='col-3'>
                                <label className='form-label fw-bold'>Time</label>
                                <div className='input-group'>
                                    <input value={editLesson.time ?? ''} onChange={e => {
                                        setEditLesson({
                                            ...editLesson,
                                            time: e.target.value
                                        })
                                    }} className='form-control' type='number'></input>
                                    <span className="input-group-text">minutes</span>
                                </div>
                            </div>

                            {editLesson.type == 3 &&
                                <div className="col-3">
                                    <label htmlFor="YoutubeVideoID" className="form-label fw-bold">Youtube Video
                                        ID</label>
                                    <input type="text" className="form-control" id="YoutubeVideoID"
                                           value={editLesson.content}
                                           onChange={e => {
                                               setEditLesson({
                                                   ...editLesson,
                                                   content: e.target.value
                                               })
                                           }} placeholder={"dQw4w9WgXcQ"} required/>
                                </div>
                            }

                            {editLesson.type == 1 &&
                                <div>
                                    <CKEditor
                                        editor={Editor}
                                        data={editLesson.content}
                                        onChange={(event, editor) => {
                                            const data = editor.getData();
                                            setEditLesson({
                                                ...editLesson,
                                                content: data
                                            })
                                        }}
                                    />
                                </div>
                            }
                            <div className="col-12">
                                <div className="btn btn-primary" onClick={tryToSave}>Save</div>
                                <div className="btn btn-danger ms-2" onClick={tryToDelete}>Delete</div>
                            </div>
                        </form>

                    </div>
                </div>
            </div>
        </div>
    )
}


function CourseEdit() {

    const {courseId} = useParams()
    const [instructor, setInstructor] = useState(null)
    const [admin, setAdmin] = useState(null)
    const [course, setCourse] = useState(null);
    const [chapters, setChapters] = useState([])
    const [mode, setMode] = useState(0)
    const [editChapter, setEditChapter] = useState(null)
    const [editLesson, setEditLesson] = useState(null)
    const [lessonChanged, setLessonChanged] = useState(false)

    let navigate = useNavigate();

    function getCourse(courseId) {
        backend.post('course/getByCourseId', {
            courseId: courseId
        }).then(res => {
            if (!res) {
                window.location.replace('/')
            }
            setCourse(res)
        })
    }

    function getChapters(courseId, getIndex) {
        backend.post('chapter/getByCourseId', {
            courseId: courseId
        }).then(res => {
            if (res) {
                res.sort(function (a, b) {
                    return a.index - b.index
                })
                setChapters(res)
                if (getIndex) {
                    let oldIndex = editChapter ? editChapter.index : 0
                    if (res.length >= oldIndex) {
                        setEditChapter(res[oldIndex - 1])
                    } else {
                        if (res.length) {
                            setEditChapter(res[res.length - 1])
                        }
                    }
                }
            } else {
                if (mode == 2) setMode(0)
            }
        })
    }

    useEffect(() => {

        //Get instructor infomation
        let instructor = UserService.instructorLoggedIn()
        instructor.then(res => {
            if (res) {
                setInstructor(res)
            } else {
                let admin = UserService.adminLoggedIn()
                admin.then(res => {
                    if (res) {
                        setAdmin(res)
                    } else {
                        window.location.replace('/')
                    }
                })
            }
        })

        //Get course
        getCourse(courseId)

        //Get all chapters
        getChapters(courseId, false)
    }, []);

    useEffect(() => {
        $(".chapterTitles").sortable({
            placeholder: "drag-location",
            handle: ".mooc-handle",
            start: function (e, ui) {
                ui.placeholder.height(ui.helper.outerHeight());
            },
            stop: tryReIndexChapters
        });
    }, [chapters]);

    function showEditMoocByID(event) {

    }

    function afterEditCourse() {
        getCourse(courseId)
    }

    function showEditCourse() {
        setMode(1)
    }

    function showEditChapter(chapter) {
        setEditChapter(chapter)
        setMode(2)
    }

    function showEditLesson(lesson, chapter) {
        setEditLesson(lesson)
        setEditChapter(chapter)
        setMode(3)
    }

    function afterEditLesson() {
        setLessonChanged(true)
    }

    function afterEditChapter() {
        getChapters(courseId, true);
    }

    function tryAddNewChapter() {
        backend.post('chapter/create', {
            courseId: courseId
        }).then(res => {
            if (res) {
                // popUpAlert.success("Add new chapter successful")
                getChapters(courseId, false)
                setEditChapter(res)
                setMode(2)
            } else {
                popUpAlert.warning("Add new chapter failed")
            }
        })
    }

    function tryReIndexChapters(e, ui) {
        //map question to new index
        let tmpChapters = []
        let index = 1
        var sorted = $(".chapterTitles").sortable("serialize", {key: "chapter"}).split('&');
        for (let str of sorted) {
            let oldIndex = str.split('=')[1]
            tmpChapters.push({
                id: chapters[oldIndex].id,
                index: index++
            })
        }
        let sendChapters = []
        for (let i = 0; i < chapters.length; i++) {
            if (tmpChapters[i].id !== chapters[i].id || tmpChapters[i].index !== chapters[i].index) {
                sendChapters.push(tmpChapters[i])
            }
        }
        //send to server
        let data = {
            size: sendChapters.length,
            courseId
        }
        for (let i = 0; i < sendChapters.length; i++) {
            data['id_' + i] = sendChapters[i].id
            data['index_' + i] = sendChapters[i].index
        }
        if (sendChapters.length !== 0) {
            backend.post('chapter/reIndexs', data).then(res => {
                if (!res) {
                    popUpAlert.warning("Reindex chapters failed")
                } else {
                    getChapters(courseId)
                    $(".chapterTitles").sortable("cancel")
                }
            })
        }
    }

    return (
        <div className='CourseEdit'>
            <Navbar instructor={instructor} admin={admin}/>

            <div id="body-editCourse" style={{minHeight: "calc(100vh - 60px)"}}>

                <div className="leftPane">

                    <h3>{course && course.name} <i className="text-info fa-solid fa-pen-to-square"
                                                   onClick={showEditCourse} style={{cursor: "pointer"}}></i></h3>
                    <div className="accordion chapterTitles" id="accordionExample">

                        {chapters.map((chapter, chapterIndex) => (
                            <div key={chapter.id} id={"chapter_" + chapterIndex}
                                 className="accordion-item"
                                 style={{cursor: "all-scroll"}}>
                                <h4 className="accordion-header" onClick={() => showEditChapter(chapter)}>
                                    <div className="mooc-handle">
                                        <i className="fas fa-ellipsis-v"></i>
                                    </div>
                                    <button
                                        className={"accordion-button collapsed" + ((mode == 2 && editChapter && chapter.id == editChapter.id) ? " chosing" : "")}
                                        type="button"
                                        data-bs-toggle="collapse"
                                        data-bs-target={"#collapse_" + chapter.id} aria-expanded="false"
                                        aria-controls="collapseOne">
                                        <strong>#{chapterIndex + 1}</strong><span className="ms-1">{chapter.name}</span>
                                    </button>
                                </h4>

                                <ListLessonEdit chapter={chapter} mode={mode} setMode={setMode}
                                                showEditLesson={showEditLesson} editLesson={editLesson}
                                                setEditLesson={setEditLesson} lessonChanged={lessonChanged}
                                                setLessonChanged={setLessonChanged} editChapter={editChapter}/>

                            </div>
                        ))}
                    </div>

                    <div className="accordion-btn">
                        <button onClick={tryAddNewChapter} className="btn btn-primary w-100 p-2">Add new chapter
                        </button>
                    </div>

                </div>

                <div>
                    {mode == 0 && <BlankEdit/>}
                    {mode == 1 && course && <EditCourse course={course} afterEditCourse={afterEditCourse}/>}
                    {mode == 2 && course && <EditChapter chapter={editChapter} afterEditChapter={afterEditChapter}/>}
                    {mode == 3 && course && <EditLesson lesson={editLesson} afterEditLesson={afterEditLesson}/>}
                    {mode == 3 && course && editLesson && editLesson.type == 2 &&
                        <QuestionsEdit lessonId={editLesson.id}/>}
                </div>


            </div>

            <Footer/>
        </div>
    );
}

export default CourseEdit;