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
                $("h2[filmId=" + filmId + "]").text(number_format(resp.rating, 1));
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


// https://alittlebit.ru/blog/vebmasterskaya/js-jquery/formatirovanie-chisel-na-js.html
function number_format(number, decimals, dec_point, thousands_sep) {
    number = (number + '').replace(/[^0-9+\-Ee.]/g, '');
    var n = !isFinite(+number) ? 0 : +number,
        prec = !isFinite(+decimals) ? 0 : Math.abs(decimals),
        sep = (typeof thousands_sep === 'undefined') ? ',' : thousands_sep,
        dec = (typeof dec_point === 'undefined') ? '.' : dec_point,
        s = '',
        toFixedFix = function(n, prec) {
            var k = Math.pow(10, prec);
            return '' + (Math.round(n * k) / k)
                .toFixed(prec);
        };
    // Fix for IE parseFloat(0.55).toFixed(0) = 0;
    s = (prec ? toFixedFix(n, prec) : '' + Math.round(n))
        .split('.');
    if (s[0].length > 3) {
        s[0] = s[0].replace(/\B(?=(?:\d{3})+(?!\d))/g, sep);
    }
    if ((s[1] || '')
        .length < prec) {
        s[1] = s[1] || '';
        s[1] += new Array(prec - s[1].length + 1)
            .join('0');
    }
    return s.join(dec);
}
