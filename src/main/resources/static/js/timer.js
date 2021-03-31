let width = 200,
	height = 90,
	timePassed = 0,
	timeLimit = 15

let fields = [{
	value: timeLimit,
	size: timeLimit,
	update: function () {
		return timePassed = timePassed + 1
	}
}]

let nilArc = d3.svg.arc()
	.innerRadius(width / 3 - 0)
	.outerRadius(width / 3 - 0)
	.startAngle(0)
	.endAngle(2 * Math.PI);

let arc = d3.svg.arc()
	.innerRadius(width / 3 - 25)
	.outerRadius(width / 3 - 30)
	.startAngle(0)
	.endAngle(function (d) {
		return ((d.value / d.size) * 2 * Math.PI)
	});

let svg = d3.select(".timer-container").append("svg")
	.attr('width', width)
	.attr('height', height)

let field = svg.selectAll('.field')
	.data(fields)
	.enter().append('g')
	.attr("transform", "translate(" + width / 2 + "," + height / 2 + ")")
	.attr('class', "field")

let back = field.append("path")
	.attr("class", "path path--background")
	.attr("d", arc)

let path = field.append("path")
	.attr("class", "path path--foreground");

let label = field.append("text")
	.attr("class", "label")
	.attr("dy", ".35em")



function updateTimer() {

	field
		.each(function (d) {
			d.previous = d.value, d.value = d.update(timePassed)
		})

	path.transition()
		.ease("elastic")
		.duration(500)
		.attrTween("d", arcTween)

	if ((timeLimit - timePassed) <= 10)
		pulseText()
	else
		label
			.text(function (d) {
				return d.size - d.value
			})
	if (timePassed <= timeLimit)
		setTimeout(updateTimer, 1000 - (timePassed % 1000))
	else {
		timePassed = 0

		back.classed("pulse", false)
		label.classed("pulse", false)

	}
}

function pulseText() {
	back.classed("pulse", true)
	label.classed("pulse", true)

	if ((timeLimit - timePassed) >= 0) {
		label.style("font-size", "55px")
			.attr("transform", "translate(0," + +4 + ")")
			.text(function (d) {
				return d.size - d.value
			});
	}

	label.transition()
		.ease("Elastic")
		.duration(900)
		.style("font-size", "38px")
		.attr("transform", "translate(0," + -2 + ")")
}

function arcTween(b) {
	let i = d3.interpolate({
		value: b.previous
	}, b)
	return function (t) {
		return arc(i(t))
	}
}

updateTimer()