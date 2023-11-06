let searchArray = [];
let dataArray = [];
$.get("/api/course/fetchCourse", function (data) {
    data.forEach(d => {
        dataArray = data;
        searchArray.push(d.name);
    })
});

searchArray.sort(function (a, b) {
    return a.rate - b.rate
})

const search_div = document.getElementById("search_div");

let result = [];

document.getElementById("search-course").onkeyup = function () {
    const input_div = document.getElementById("search-course");

    let inner_html = ``;

    let input = input_div.value;
    if (input) {
        result = dataArray.filter((course) => {
            return course.name.toLowerCase().includes(input.toLowerCase());
        })
    } else {
        result = [];
    }

    result.sort((a, b) => {
        return b.rate - a.rate
    })

    result.forEach(function (x) {
        inner_html += `
        <a href="/course/${x.id}">        
                    <div id="search_instance" style="border-right: #a7c080 solid 5px;background: white">
                        <p class=" text text-end course-name" style="font-size: larger">
                            ${x.name}
                        </p>
                        <p class=" text text-end">
                            ${x.rate}
                        </p>
                    </div>
                    </a>

                    `
    })

    document.getElementById("search_list").innerHTML = inner_html;
}
