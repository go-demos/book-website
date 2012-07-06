OrderBooks = function() {

    var form;

    function registerChangeEvent(checkboxes) {
        var self = this;
        $(checkboxes).change(function() {
            var radio = $(this);
            if (radio.attr("checked") && radio.attr("checked") === 'checked') {
                self.form = radio.parent();
            }
        });
    }

    function clickHandler(submitButton) {
        var self = this;
        $(submitButton).click(function() {
            self.form.submit();
        })
    }

    return {
        clickHandler : clickHandler,
        registerChangeEvent : registerChangeEvent
    }
};