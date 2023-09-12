var stompClient = null;

$(document).ready(function() {
    console.log("Index page is ready");
    connect();


});

function connect() {
    var socket = new SockJS('/webSocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        updateNotificationDisplay();
        stompClient.subscribe('/topic/getData', function (message) {
            showMessage(JSON.parse(message.body).content);
        });
    });
}

