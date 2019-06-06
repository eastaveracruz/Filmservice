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


$(".content form").submit(function () {
    var filmId = $(this).attr('filmId');
    $.post({
        url: $(this).attr('action'),
        data: $(this).serialize(),
        success: function (data) {
            var resp = $.parseJSON(data);
            if (resp.scs) {
                var assessment = $(".content form[filmId=" + filmId + "] select").val();
                $("span[yourAssessment=" + filmId + "]").text(assessment);
                $(".content h2[filmId=" + filmId + "]").text(resp.rating);
            }
        }
    });
    return false;
});
