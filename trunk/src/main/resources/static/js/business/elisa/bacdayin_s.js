$(function() {
	$.getJSON(
		"../../../../json/jsontable.text",
		function(json) {
			var str = "<table class='layui-table'><thead>" +
				"<tr>" +
				"<th  width='200'>@</th>" +
				"<th>1</th>" +
				"<th>2</th>" +
				"<th>3</th>" +
				"<th>4</th>" +
				"<th>5</th>" +
				"<th style='width:13%'>6</th>" +
				"<th>7</th>" +
				"<th>8</th>" +
				"<th>9</th>" +
				"<th>10</th>"+
				"<th>11</th>"+
				"<th>12</th>"+
				"</tr><thead>";
			$.each(
				json,
				function(i, n) {
					str += "<tbody><tr>" +
						"<td width='200'>" + n.name + "</td>" +
						"<td>" + n.age + "</td>" +
						"<td>" + n.a + "</td>" +
						"<td>" + n.b + "</td>" +
						"<td>" + n.c + "</td>" +
						"<td>" + n.d + "</td>" +
						"<td>" + n.e + "</td>" +
						"<td>" + n.f + "</td>" +
						"<td>" + n.g + "</td>" +
						"<td>" + n.h + "</td>" +
						"<td>" + n.i + "</td>" +
						"<td>" + n.m + "</td>" +
						"<td>" + n.q + "</td></tr></tbody>";
				}
			);
			str += "</table>";
			$("#bustable").html(str);
		}
	);
})