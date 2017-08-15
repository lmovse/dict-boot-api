/**
 * Created by lmovse on 2017/8/7.
 * Tomorrow is a nice day.
 */
var edit = $(".input-text")[0];
var $trans = $("#trans");
var $word = $("#word");
var $phrase = $("#phrase");
var $content = $(".content");
var $spinner = $(".spinner");
var $count = $("#count");
$(function () {
    edit.focus();
});

$(".button").on("click", function () {
    $content.hide();
    $spinner.fadeIn('slow');
    $.get("q?q=" + edit.value, function (data) {
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
            $word.html(data.data.wordName
                + "<br><br>"
                + (data.data.pron === '' ? '' : data.data.pron + '<br><br>'));
        } else {
            $word.html(data.data.wordName
                + '<br><br>'
                + (data.data.pron === '' ? '' : data.data.pron + '<br><br>')
                + data.data.etymology
                + '<br><br>');
        }
        $count.text(data.data.queryAccount);
        if (colloc === '') {
            $trans.hide();
        } else {
            $trans.show();
            $trans.html(colloc);
        }
        if (collexa === '') {
            $phrase.hide();
        } else {
            $phrase.show();
            $phrase.html(collexa)
        }
    });
});