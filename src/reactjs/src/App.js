import { Routes, Route, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import CourseEdit from "./CourseEdit";
import './css/header.css'


export default function App() {

  return (
    <div className="App">
      <Routes>
        <Route path='course/edit/:courseID' element={<CourseEdit />}></Route>
      </Routes>
    </div>
  );
}
