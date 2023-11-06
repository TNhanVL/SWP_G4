import {useNavigate, useParams} from 'react-router-dom';
import Navbar from './Navbar';
import {useEffect, useState} from "react";
import UserService from "./service/UserService";
import Footer from "./Footer";
import $ from "jquery"
import 'jquery-ui-dist/jquery-ui'
import backend from "./service/Backend";
import popUpAlert from "./service/popUpAlert";
import {hover} from "@testing-library/user-event/dist/hover";

function ListLessonEdit({
                            chapter,
                            mode,
                            setMode,
                            showEditLesson,
                            editLesson,
                            setEditLesson,
                            lessonChanged,
                            setLessonChanged,
                            editChapter
                        }) {

    const [lessons, setLessons] = useState([])

    function getLessons(chapter, getIndex) {
        backend.post('lesson/getByChapterID', {
            chapterID: chapter.id
        }).then(res => {
            if (res) {
                res.sort(function (a, b) {
                    return a.index - b.index
                })
                setLessons(res)
                if (getIndex) {
                    let oldIndex = editLesson ? editLesson.index : 0
                    if (res.length >= oldIndex) {
                        setEditLesson(res[oldIndex - 1])
                    } else {
                        if (res.length) {
                            setEditLesson(res[res.length - 1])
                        }
                    }
                }
            } else {
                if (mode == 3) setMode(0)
            }
        })
    }

    useEffect(() => {
        if (!chapter || (chapter && !chapter.id)) return;
        getLessons(chapter, null)
    }, [chapter]);

    useEffect(() => {
        if (!lessonChanged) return
        if (chapter.id == editChapter.id) {
            getLessons(chapter, null)
        }
        setLessonChanged(false)
    }, [lessonChanged]);

    return (chapter && lessons && <div id={"collapse_" + chapter.id} className="accordion-collapse collapse"
                                       data-bs-parent="#accordionExample">

        <div className="accordion-body lessonTitles">

            {lessons.map((lesson, lessonIndex) => (
                <div key={"lesson_" + lesson.id} className="accordion-item" onClick={() => {
                    showEditLesson(lesson, chapter)
                }}>
                    <h4 className="accordion-header">
                        <div><i className="lesson-handle fas fa-ellipsis-v ms-3"
                                style={{cursor: "all-scroll"}}></i>
                        </div>
                        <button
                            className={"btn" + ((mode == 3 && editLesson && lesson.id == editLesson.id) ? " chosing" : "")}
                            type="button">
                            #{lessonIndex + 1 + " " + lesson.name}
                        </button>
                    </h4>
                </div>))}


            <div className="accordion-btn">
                    <span onclick="showAddLessonArea()"
                          className="btn btn-primary w-100 p-2 text-center">
                    Add new lesson</span>
            </div>

        </div>

    </div>)
}

export default ListLessonEdit;