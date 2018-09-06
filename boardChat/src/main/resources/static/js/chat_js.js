/**
 * 
 */

// STOMP client session을 전역 변수로 선언
var stompClient = null;
var count = 1;

function setConnected(connected) {
	
	// connect, disconnect 버튼을 활성화/비활성화
	$("#connectBtn").prop("disabled", connected);
	$("#disconnectBtn").prop("disabled", !connected);
	
	if(connected)
		$("#chatOutputRoom").show();
	else
		$("#chatOutputRoom").hide();
	
	$("#chatOutput").html("");
}

function connect() {

	var socket = new SockJS("webSocket");
	stompClient = Stomp.over(socket);
	
	stompClient.connect({}, function(frame) {
		setConnected(true);
		
		console.log("connected: " + frame);
		
		stompClient.subscribe("/topic/in", function(message) {
			showMessage(message.body);
		});
		
		stompClient.subscribe("/topic/talk", function(message) {
			showMessage(JSON.parse(message.body).talker + " : " + JSON.parse(message.body).content);
		});
		
		stompClient.subscribe("/topic/out", function(message) {
			showMessage(message.body);
		});
		
		stompClient.send("/app/in", {}, "<< " + $("#name").text() + " 님이 입장하셨습니다. >>");
		$("#message").trigger("focus");
	});
	
}

function disconnect() {

	console.log("Disconnected..");

	stompClient.send("/app/out", {}, "<< " + $("#name").text() + " 님이 퇴장하셨습니다. >>");
	
	if(stompClient !== null) 
		stompClient.disconnect();
	
	setConnected(false);
}

function showMessage(message) {
	$("#chatOutput").append('<tr id="message_' + count + '"><td>' + message + '</td></tr>');	
	count++;
}

function sendMessage() {
	stompClient.send("/app/talk", {}, JSON.stringify({
		"talker" : $("#name").text(),
		"content" : $("#message").val()
	}));

}

$(document).ready(function() {
	
	$("#chatOutputRoom").hide();
	
	$("form").on("submit", function(event) {
		event.preventDefault();
	});
	
	$("#connectBtn").click(function() {
		connect();
	});
	
	$("#disconnectBtn").click(function() {
		disconnect();
	});
	
	$("#sendBtn").click(function() {
		sendMessage();
		$("#message").val("");
	});
});

$(window).on("beforeunload", function() {
	disconnect();
});
