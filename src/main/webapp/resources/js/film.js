const userAjaxUrl = "ajax/admin/users/";

// Рейтинг
$(".assessment").submit(function () {

    var filmId = $(this).attr('filmId');
    $.post({
        url: $(this).attr('action'),
        data: $(this).serialize(),
        success: function (data) {
            var resp = $.parseJSON(data);
            if (resp.scs) {
                var assessment = $("form[filmId=" + filmId + "] select").val();
                $("span[yourAssessment=" + filmId + "]").text(assessment);
                $("h2[filmId=" + filmId + "]").text(resp.rating);
            }
        }
    });
    return false;
});

//Удаление
var text = $(".content .description").text();
$(".content .description").text(text.substr(0, 200) + "...");


$(".deleteFilm").click(function () {
    if (confirm("Удалить фильм?")) {
        var obj = $(this);
        $.post({
            url: $(this).attr('href'),
            success: function (data) {
                if (data === "true") {
                    obj.parents("tr").remove();
                }
            }
        });
    }
    return false;
});
