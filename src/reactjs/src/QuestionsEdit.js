import {useNavigate, useParams} from 'react-router-dom';
import Navbar from './Navbar';
import {useEffect, useRef, useState} from "react";
import UserService from "./service/UserService";
import Footer from "./Footer";
import $ from "jquery"
import 'jquery-ui-dist/jquery-ui'
import backend from "./service/Backend";
import popUpAlert from "./service/popUpAlert";
import {hover} from "@testing-library/user-event/dist/hover";
import Editor from "ckeditor5-custom-build";
import {CKEditor} from "@ckeditor/ckeditor5-react";

function Answer({answerID, getAnswers, getQuestions, correct, trySetCorrectAnswer}) {

    const [answer, setAnswer] = useState({})
    const [content, setContent] = useState('')
    //last time doing action
    const lastAction = useRef(Date.now())
    const answerIDRef = useRef('')
    const contentRef = useRef('')

    function getAnswer() {
        //get answer
        backend.post('answer/getByAnswerID', {
            answerID
        }).then(res => {
            if (!res) {
                window.location.replace('/')
            } else {
                setAnswer(res)
            }
        })
    }

    useEffect(() => {
        getAnswer();
    }, [answerID])

    useEffect(() => {
        answerIDRef.current = answer.id
        contentRef.current = answer.content
        setContent(answer.content)
    }, [answer.id])

    useEffect(() => {
        $('textarea').each(function () {
            this.setAttribute('style', 'height:' + (this.scrollHeight) + 'px;overflow-y:hidden;');
        }).on('input', function () {
            this.style.height = 'auto';
            this.style.height = (this.scrollHeight) + 'px';
        });
    })

    function trySave() {
        if (contentRef.current === answer.content) return
        if (answerIDRef.current != answerID) {
            popUpAlert.danger('Save answer failed, please wait about 2s to save before switch to another answer!')
            return
        }
        backend.post('answer/update', {
            ...answer,
            content: contentRef.current
        }).then(res => {
            if (res) {
                getAnswer()
            } else {
                popUpAlert.danger('Save answer failed')
            }
        })
    }

    function typing(e) {
        setContent(e.target.value)
        contentRef.current = e.target.value
        lastAction.current = Date.now()
        setTimeout(function () {
            if (Date.now() - lastAction.current > 1000) {
                lastAction.current = Date.now()
                trySave()
            }
        }, 1200)
    }

    function tryDeleteAnswer() {
        backend.post('answer/delete', {
            answerID
        }).then(res => {
            if (res) {
                getAnswers()
                getQuestions()
            } else {
                popUpAlert.danger('Delete answer failed')
            }
        })
    }

    return (
        <div className='Answer'>
            <input type='radio' style={{height: '20px', width: '20px', cursor: 'pointer'}} onChange={() => {
                trySetCorrectAnswer(answer)
            }} checked={correct ?? false}></input>
            <div className='content'>
                <textarea value={content} onChange={typing} className='form-control'></textarea>
                <div className={'save' + ((contentRef.current === answer.content) ? ' save-succeed' : ' text-danger')}>
                    <i className='fa-regular fa-floppy-disk'></i>
                </div>
            </div>
            <div className='trash' onClick={tryDeleteAnswer}>
                <i className="fa-solid fa-trash" style={{color: "#ff0000", fontSize: '20', cursor: 'pointer'}}></i>
            </div>
        </div>
    )
}

function Question({questionID, updateQuestion, setChosingQContent, afterDeleteQ, getQuestions}) {

    const [question, setQuestion] = useState({})
    const [answers, setAnswers] = useState([])

    function getAnswers() {
        backend.post('answer/getByQuestionID', {
            questionID
        }).then(res => {
            if (res) {
                setAnswers(res)
            }
        })
    }

    useEffect(() => {
        $('textarea').each(function () {
            this.setAttribute('style', 'height:' + (this.scrollHeight) + 'px;overflow-y:hidden;');
        }).on('input', function () {
            this.style.height = 'auto';
            this.style.height = (this.scrollHeight) + 'px';
        });
    })

    useEffect(() => {
        if (!questionID) return

        //get question
        backend.post('question/getByQuestionID', {
            questionID
        }).then(res => {
            if (res) {
                setQuestion(res)
            }
        })

        getAnswers()
    }, [questionID])

    function trySaveQuestion() {
        backend.post('question/update', {
            ...question
        }).then(res => {
            if (res) {
                setChosingQContent(question.content)
                updateQuestion(question)
                // popUpAlert.success('Save question succeed')
            } else {
                popUpAlert.danger('Save question failed')
            }
        })
    }

    function tryDeleteQuestion() {
        backend.post('question/delete', {
            questionID
        }).then(res => {
            if (res) {
                afterDeleteQ()
                // popUpAlert.success('Delete question succeed')
            } else {
                popUpAlert.danger('Delete question failed')
            }
        })
    }

    function tryAddAnswer() {
        backend.post('answer/create', {
            questionID
        }).then(res => {
            if (res) {
                getAnswers()
                getQuestions()
            } else {
                popUpAlert.danger('Add answer failed')
            }
        })
    }

    function trySetCorrectAnswer(correctAnswer) {
        let newAnswers = []
        for (let answer of answers) {
            let correct = (answer.id === correctAnswer.id)
            if (answer.correct !== correct) {
                newAnswers.push({
                    id: answer.id,
                    correct
                })
            }
        }
        // Backend.post('answer/reIndexMulti', {
        //     answers: newAnswers
        // }).then(res => {
        //     if (res) {
        //         getAnswers()
        //     } else {
        //         popUpAlert.danger('Update answer correct failed!')
        //     }
        // })
    }

    return (
        <div className='Question'>
            <div className="questionContentBlock">
                <h4 className='title'>Question Infomation</h4>
                <div className={"row"}>
                    <div className='questionContent col-12'>
                        <label className='form-label fw-bold'>Content</label>
                        <CKEditor
                            editor={Editor}
                            data={question ? question.content : ''}
                            onChange={(event, editor) => {
                                const data = editor.getData();
                                setQuestion({
                                    ...question,
                                    content: data
                                })
                                console.log(question)
                            }}
                        />
                    </div>

                    <div className="col-4">
                        <label className="form-label fw-bold">Type</label>
                        <select className="form-select" aria-label="Default select example"
                                value={Number(question.type) % 10}
                                onChange={e => {
                                    setQuestion({
                                        ...question,
                                        type: Number(question.type - (question.type % 10) + Number(e.target.value))
                                    })
                                }} required>
                            <option value="0">One Correct Answer
                            </option>
                            <option value="1">Multiple Correct
                                Answers
                            </option>
                        </select>
                    </div>

                    <div className='questionContentAction col-2'>
                        <div className='btn btn-danger me-2' onClick={tryDeleteQuestion}>Delete</div>
                        <div className='btn btn-primary' onClick={trySaveQuestion}>Save</div>
                    </div>
                </div>
            </div>
            <div className="answersContentBlock mt-3">
                <h4 className='title'>Answers ({answers ? answers.length : '...'})</h4>
                <div className='answers'>
                    {answers && answers.map((answer, answerIndex) => (
                        <Answer answerID={answer.id} getAnswers={getAnswers} correct={answer.correct}
                                trySetCorrectAnswer={trySetCorrectAnswer} getQuestions={getQuestions}
                                key={answerIndex}/>
                    ))}
                </div>

                <div className='m-2'>
                    <div className='btn btn-primary' onClick={tryAddAnswer}>Add new answer</div>
                </div>
            </div>
        </div>
    )
}

function QuestionsEdit({lessonId}) {

    const [questions, setQuestions] = useState([])
    const [chosingQ, setChosingQ] = useState('')
    const [chosingQContent, setChosingQContent] = useState('')

    function getQuestions() {
        if (!lessonId) return
        backend.post('question/getByLessonId', {
            lessonId
        }).then(res => {
            if (!res) {
                popUpAlert.warning("Get questions failed")
            } else {
                setQuestions(res)
                // popUpAlert.success("Save Successful!")
            }
        })
    }

    function updateQuestion(question) {
        // questions[chosingQ] = question
        for (let i = 0; i < questions.length; i++) {
            if (questions[i].id === question.id) {
                questions[i] = question
                setChosingQ(question.id)
                break;
            }
        }
        console.log(questions)
        setQuestions(questions)
        //to show content of question after save changes
    }

    function afterDeleteQ() {
        setChosingQ('')
        getQuestions()
    }

    useEffect(() => {
        if (!lessonId) return;
        getQuestions(lessonId)
    }, [lessonId]);

    function tryCreateNewQuestion() {
        backend.post('question/create', {
            lessonId
        }).then(res => {
            if (!res) {
                popUpAlert.warning("Create new question failed")
            } else {
                questions.push(res)
                setQuestions(questions)
                setChosingQ(res.id)
                setChosingQContent('')
                // popUpAlert.success("Create new question succeed!")
            }
        })
    }

    return (
        lessonId && questions &&
        <div className="questionsBlock mt-3">
            <div className='left-Pane'>
                <h4 className='title'>Questions ({questions ? questions.length : '...'})</h4>
                <div className="questionTags">
                    {questions.length > 0 && questions.map((question, questionIndex) => (
                        <div className={'questionTag' + (chosingQ === question.id ? ' selected' : '')}
                             key={question.id} id={'question_' + question.id} onClick={() => {
                            setChosingQ(question.id)
                            setChosingQContent(question.content)
                        }}>
                            <i className="fa-solid fa-ellipsis-vertical question-handle"
                               style={{cursor: "pointer"}}></i>
                            <span style={{overflowWrap: "anywhere"}}><span
                                className='fw-bold'>Q{questionIndex + 1}: </span>{chosingQ === question.id ? chosingQContent : question.content}</span>
                        </div>
                    ))}
                </div>
                <div className="btn btn-primary w-100 p-2" onClick={tryCreateNewQuestion}>Add new question</div>
            </div>

            <div className='right-Pane'>
                {(chosingQ && questions.length > 0) && <Question questionID={chosingQ} updateQuestion={updateQuestion}
                                                                 setChosingQContent={setChosingQContent}
                                                                 afterDeleteQ={afterDeleteQ}
                                                                 getQuestions={getQuestions}/>}
            </div>

        </div>
    )
}

export default QuestionsEdit;