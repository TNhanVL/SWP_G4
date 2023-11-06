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

function EditCourse({course, afterEditCourse}) {

    const [editCourse, setEditCourse] = useState(null)

    useEffect(() => {
        if (!course) return
        //Get course
        backend.post('course/getByCourseID', {
            courseID: course.id
        }).then(res => {
            if (res) {
                setEditCourse(res)
            }
        })
    }, [course]);

    function tryToSave() {
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
                                <label htmlFor="CourseName" className="form-label">Course Name</label>
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
                                <label htmlFor="CourseDescription" className="form-label">Course Description</label>
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
                                <label htmlFor="CoursePrice" className="form-label">Course Price</label>
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
        backend.post('chapter/getByChapterID', {
            chapterID: chapter.id
        }).then(res => {
            if (res) {
                setEditChapter(res)
            }
        })
    }, [chapter]);

    function tryToSave() {
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
        backend.post('chapter/delete', {
            chapterID: chapter.id
        }).then(res => {
            if (!res) {
                popUpAlert.warning("Delete Chapter failed")
            } else {
                // popUpAlert.success("Delete Successful!")
                afterEditChapter()
            }
        })
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
                                <label htmlFor="CourseName" className="form-label">Chapter Name</label>
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
                                <label htmlFor="CourseDescription" className="form-label">Chapter Description</label>
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

function CourseEdit() {

    const {courseID} = useParams()
    const [instructor, setInstructor] = useState(null)
    const [course, setCourse] = useState(null);
    const [chapters, setChapters] = useState([])
    const [mode, setMode] = useState(0)
    const [editChapter, setEditChapter] = useState(null)
    const [editLesson, setEditLesson] = useState(null)

    let navigate = useNavigate();

    function getCourse(courseID) {
        backend.post('course/getByCourseID', {
            courseID: courseID
        }).then(res => {
            if (!res) {
                window.location.replace('/')
            }
            setCourse(res)
        })
    }

    function getChapters(courseID, getIndex) {
        backend.post('chapter/getByCourseID', {
            courseID: courseID
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
            if (!res) {
                window.location.replace('/')
            }
            setInstructor(res)
        })

        //Get course
        getCourse(courseID)

        //Get all chapters
        getChapters(courseID, false)
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
        getCourse(courseID)
    }

    function showEditCourse() {
        setMode(1)
    }

    function showEditChapter(chapter) {
        setEditChapter(chapter)
        setMode(2)
    }

    function showEditLesson(lesson) {
        setEditLesson(lesson)
        setMode(3)
    }

    function afterEditChapter() {
        getChapters(courseID, true);
    }

    function tryAddNewChapter() {
        backend.post('chapter/create', {
            courseID: courseID
        }).then(res => {
            if (res) {
                // popUpAlert.success("Add new chapter successful")
                getChapters(courseID, false)
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
            courseID
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
                    getChapters(courseID)
                    $(".chapterTitles").sortable("cancel")
                }
            })
        }
    }

    return (
        <div className='CourseEdit'>
            <Navbar learner={instructor}/>

            <div id="body-editCourse" style={{maxHeight: "calc(100vh - 60px)"}}>

                <div className="leftPane" style={{maxHeight: "100%"}}>

                    <h3>{course && course.name} <i className="text-info fa-solid fa-pen-to-square"
                                                   onClick={showEditCourse} style={{cursor: "pointer"}}></i></h3>
                    <div className="accordion chapterTitles" id="accordionExample" style={{maxHeight: "calc(100% - 150px)"}}>

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

                                <ListLessonEdit chapterID={chapter.id} mode={mode} setMode={setMode} showEditLesson={showEditLesson} editLesson={editLesson} setEditLesson={setEditLesson}/>

                            </div>
                        ))}

                    </div>

                    <div className="accordion-btn">
                        <button onClick={tryAddNewChapter} className="btn btn-primary w-100 p-2">Add new chapter
                        </button>
                    </div>

                </div>

                {mode == 1 && course && <EditCourse course={course} afterEditCourse={afterEditCourse}/>}
                {mode == 2 && course && <EditChapter chapter={editChapter} afterEditChapter={afterEditChapter}/>}

            </div>

            <Footer/>
        </div>
    );
}

export default CourseEdit;