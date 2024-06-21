import { Routes, Route, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import CourseEdit from "./CourseEdit";


export default function App() {

  return (
    <div className="App">
      <Routes>
        <Route path='course/edit/:courseId' element={<CourseEdit />}></Route>
      </Routes>
    </div>
  );
}
