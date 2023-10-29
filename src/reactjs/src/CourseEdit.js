import { useParams } from 'react-router-dom';
import Navbar from './Navbar';

function CourseEdit() {

    const { courseID } = useParams()

    return (
        <div className='CourseEdit'>
            <Navbar />
            <img src='/public/assets/imgs/logo.png'></img>
            {courseID}
        </div>
    );
}

export default CourseEdit;