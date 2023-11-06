import Backend from './Backend'
import Cookies from 'js-cookie'

async function learnerLoggedIn() {
    try {
        let accessToken = Cookies.get('learnerJwtToken');
        if (!accessToken) return false;

        return await Backend.post('auth/learner')

    } catch (e) {
        return false;
    }
}

async function instructorLoggedIn() {
    try {
        let accessToken = Cookies.get('instructorJwtToken');
        if (!accessToken) return false;

        return await Backend.post('auth/instructor')

    } catch (e) {
        return false;
    }
}

async function adminLoggedIn() {
    try {
        let accessToken = Cookies.get('adminJwtToken');
        if (!accessToken) return false;

        return await Backend.post('auth/admin')

    } catch (e) {
        return false;
    }
}

const exportObject = { learnerLoggedIn, instructorLoggedIn, adminLoggedIn }

export default exportObject