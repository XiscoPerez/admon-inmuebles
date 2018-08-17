function addControlState(div, emptyMessage) {
	var input = div.getElementsByClassName("form-control")[0];
	input.addEventListener('blur', function() {
		input.checkValidity();
		if (input.validity.valid) {
			addStateClass(div, '', false)
		}
	}, false);

	input.addEventListener('change', function() {
		if (this.value.trim() === '') {
			this.value = '';
		}
		this.checkValidity();
		if (this.validity.valid) {
			addStateClass(div, '', false)
		}
	}, false);

	input.addEventListener('invalid', function() {
		if (this.validity.valueMissing) {
			addStateClass(div, emptyMessage, true)
		} else if (input.validity.patternMismatch) {
			addStateClass(div, this.title, true)
		} else {
			addStateClass(div, '', false)
		}
	}, false);
}

function addStateClass(div, message, error) {
	var input = div.getElementsByClassName("form-control")[0];
	var glyphicon = div.getElementsByTagName('span')[0];
	var helpBlock = div.getElementsByTagName('span')[1];
	$(helpBlock).text(message);
	if (error) {
		$(div).removeClass('has-success').addClass('has-error');
		$(glyphicon).removeClass('glyphicon-ok').addClass('glyphicon-remove');
		input.setCustomValidity(' ');
	} else {
		$(div).removeClass('has-error').addClass('has-success');
		$(glyphicon).removeClass('glyphicon-remove').addClass('glyphicon-ok');
		input.setCustomValidity('');
	}
}