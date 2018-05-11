var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/app/hi", {}, JSON.stringify({'name': ''}));
}

function sendName2() {
    var fields = $("#idDoc").val();
    var value= fields;
    fields = $("#idPat").val();
    var value2=fields;
    var date = $("#cday").val();
    stompClient.send("/app/hi", {}, JSON.stringify({'Doctor ID ': value, 'Patient ID: ':value2, 'Date: ':date}));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
//        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
    $( "#addCons" ).click(function() { sendName2();
            var fields = $("#idPat").val();
            var value= fields;
            fields = $("#idDoc").val();
            var value2=fields;
            fields= $("#cday").val();
            window.location.href="/makeAppointment?idDoc="+value2+"&idPat="+value+"&cday="+fields;
            });
});

