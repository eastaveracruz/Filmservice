const userAjaxUrl = "ajax/admin/users/";

$("footer").click(getAllUsers);

function getAllUsers() {
    $.getJSON(
        userAjaxUrl,
        function (data) {
            for (var i = 0, len = data.length; i < len; i++) {
                console.log(data[i].login + " - " + data[i].password);
                $("footer").append("<p>" + data[i].login + " - " + data[i].password + "</p>")
            }
        }
    )
}

