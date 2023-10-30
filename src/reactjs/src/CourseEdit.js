import {useParams} from 'react-router-dom';
import Navbar from './Navbar';
import {useEffect, useState} from "react";
import UserService from "./service/UserService";

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


        </div>
    );
}

export default CourseEdit;