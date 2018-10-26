$(document).ready(function () {
    //print all data from db
    $.ajax({
        url: "https://to-do-app21.herokuapp.com/rest/task/all"
    }).then(function (data) {
        var task_data = '';
        $.each(data, function (key, value) {
            task_data += '<tr>';
            task_data += '<td>' + value.title + '</td>';
            task_data += '<td>' + value.description + '</td>';
            task_data += '<td>' + value.date + '</td>';
            task_data += '</tr>';
        });
        $('.table').append(task_data);
    });

    // add data to db
    $("button.add_task").click(function () {
        $(".new_task_form").show();
    });

     $(".close_task_form").click(function () {
        $(".new_task_form").hide();
     });

     //
      $("button.add_project").click(function () {
        $(".new_project_form").show();
      });

      $(".close_project_form").click(function () {
        $(".new_project_form").hide();
      });

});



