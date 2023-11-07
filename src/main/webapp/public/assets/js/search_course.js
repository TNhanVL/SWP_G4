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
        }).slice(0, 5)
    } else {
        result = [];
    }

    result.sort((a, b) => {
        return b.rate - a.rate
    })

    result.forEach(function (x) {
        inner_html += `
        <a href="/course/${x.id}">        
            <div id="search_instance" class="container row border border-success bg-white" 
            style="width: 200%; padding: 5px; border-radius: 14px">
                    <img src="/public/media/course/${x.id}/${x.picture}" alt="" class="img-thumbnail col-2" 
                    style="height: 77px;width: 77px; object-fit: contain; ">
                
                <div class="col-9" style="display: flex; flex-direction: column">
                    <p class="h5 text text-start course-name" style="margin: 1px">
                        <strong>${x.name}</strong>
                    </p>
                    <p class="h6 text text-start course-name" style="margin: 1px">
                        ${x.description.toUpperCase()}
                    </p>
                    
                    <div class="d-flex flex-row justify-content-between">
                        <p class="h6 text text-start course-name" style="margin: 1px">
                            <i class="fa-solid fa-star" style="color: yellow"></i>  ${x.rate}
                        </p>
                        <p class="h6 text text-start course-name" style="margin: 1px">
                            <strong>${x.price} $</strong>
                        </p>
                    </div>
                </div>
            </div>
        </a>
                    `
    })

    document.getElementById("search_list").innerHTML = inner_html;
}
