const url = 'http://localhost:8080/tasks';
let tasklist;

document.onreadystatechange = () => {
    if (document.readyState == 'complete') {
        taskGetAll();
    }
}

function taskGetAll() {
    $.getJSON(url, function(data) {
        tasklist = data;
        const ul = $("<ul>");
        tasklist.forEach(e => {
            const desc = `<li>${e.description}</li>`
            ul.append(desc);
        });
        $("#task-list").append(ul);
    });
}

function taskCreate() {
    const description = $("#addtask-desc").val();
    const body = `{"description": "${description}"}`;

    $.ajax({
        type: "POST",
        url: url,
        data: body,
        success: (res) => { console.log(res); },
        contentType: "application/json",
        dataType: "json"
    });
}