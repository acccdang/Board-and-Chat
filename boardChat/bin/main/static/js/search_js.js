/**
 * 
 */

$(document).ready(function() {
	$("#searchForm").on("submit", function(event) {
		event.preventDefault();
		return false;
	});

	$("#search").keypress(function(event) {
		if (event.keyCode == 13)
			$("#searchBtn").click();
	});

	$("#searchBtn").click(function() {
		var category = $("#category").val();
		var option = $("#option").val();
		var search = $("#search").val();

		location.href = "/board/list?category=" + category + "&option=" + option + "&search=" + search;
	});

	$("#categoryOption").change(function() {
		switch ($(this).val()) {
		case "category_all":
			$("#category").val("all");
			break;
		case "category_notice":
			$("#category").val("notice");
			break;
		case "category_common":
			$("#category").val("common");
			break;
		}
	});

	$("#searchOption").change(function() {
		switch ($(this).val()) {
		case "search_all":
			$("#option").val("all");
			break;
		case "search_title":
			$("#option").val("title");
			break;
		case "search_content":
			$("#option").val("content");
			break;
		case "search_writer":
			$("#option").val("writer");
			break;
		}
	});
});