    /**
        * Created by lmovse on 2017/8/7.
        * Tomorrow is a nice day.
        */
    var edit = $(".input-text")[0];
var $trans = $("#trans");
var $base = $("#base");
var $phrase = $("#phrase");
var $content = $(".content");
var $spinner = $(".spinner");
$(function () {
    edit.focus();
});
$(".button").on("click", function () {
    $content.hide();
    $spinner.fadeIn('slow');
    $.get("query?q=" + edit.value + "&dictId=23", function (data) {
        if (data.code !== 200) {
            alert(data.message);
            return;
        }
        $spinner.fadeOut('fast');
        $(".button").blur();
        $content.fadeIn('slow');
        var colloc = "";
        var collexa = "";
        $(data.data.senses).each(function (i, val) {
            $(val.collosections).each(function (j, value) {
                $(value.collocates).each(function (k, obj) {
                    if (obj.colloc !== "" && obj.colloc !== undefined) {
                        colloc += obj.colloc + "<br>" + obj.collocTrans + "<br><br>";
                    }
                    if (obj.collexa !== "" && obj.collexa !== undefined) {
                        collexa += obj.collexa + "<br>" + obj.collexaTrans + "<br><br>";
                    }
                })
            })
        });
        if (data.data.etymology === "") {
            $base.html(data.data.wordName
                + "<br><br>"
                + data.data.pron
                + "<br><br>"
                + data.data.etymology
                + "<br><br>");
            $trans.html(colloc);
        }
        $base.html(data.data.wordName
            + "<br><br>"
            + data.data.pron
            + "<br><br>"
            + data.data.etymology
            + "<br><br>");
        $trans.html(colloc);
        $phrase.html(collexa);
    });
});