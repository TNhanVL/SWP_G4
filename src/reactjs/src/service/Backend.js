import axios from 'axios'
import popUpAlert from "./popUpAlert"
import Cookies from 'js-cookie'

function afterAxios(res) {
    return res.then(res => {
        switch (res.status) {
            case 200: {
                if (!res.data.success) {
                    // popUpAlert.warning("Server: " + res.data.message)
                    console.log("Server: " + res.data.message)
                }
                return res.data
            }
            default: {
                popUpAlert.danger('There are some error when get data from server!')
                return {success: false}
            }
        }
    }).catch(e => {
        if (e.response) {
            if (e.response.data) {
                if (e.response.data.message) {
                    console.log("Server: " + e.response.data.message)
                    // popUpAlert.warning("Server: " + e.response.data.message)
                } else {
                    popUpAlert.danger('There are some error when get data from server!')
                }
                return e.response.data
            }
            popUpAlert.danger('There are some error when connect to server!')
            return {success: false}
        }
        popUpAlert.danger('Some unknown error, please contact IT support')
        return {success: false}
    })
}

function get(url, data, config) {
    const res = axios.get(process.env.REACT_APP_BACKEND_ENDPOINT + url, data, {
        ...config,
        headers: {
            // 'Access-Control-Allow-Origin': '*'
        },
        withCredentials: true
    })
    return afterAxios(res)
}

function post(url, data, config) {
    const res = axios.post(process.env.REACT_APP_BACKEND_ENDPOINT + url, data, {
        ...config,
        headers: {
            // 'Access-Control-Allow-Origin': '*',
        },
        withCredentials: true
    })
    return afterAxios(res)
}

const exportObject = {get, post}

export default exportObject