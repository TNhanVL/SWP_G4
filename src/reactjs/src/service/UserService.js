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

const exportObject = { learnerLoggedIn }

export default exportObject