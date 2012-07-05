OrderBooks = function(hidden) {
    function selectISBN(element, hidden) {
        var radio = $(element);
        if (radio.attr("checked") && radio.attr("checked") === 'checked') {
            $(hidden).val(checkBox.val());
        }
    }

    function registerHandler(elements) {
        $(elements).change(function() {
            selectISBN($(this), hidden);
        });
    }

    return {
        registerHandler : registerHandler
    }
};