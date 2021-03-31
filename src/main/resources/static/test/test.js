loginField = document.getElementById('loginField')
roomName = document.getElementById('roomName')
roomId = document.getElementById('roomId')
chairId = document.getElementById('chairId')
roomId2 = document.getElementById('roomId2')
raiseValue = document.getElementById('raiseValue')

function login() {
    var url = "http://localhost:8080/user/login";
    var xhr = new XMLHttpRequest();
    xhr.open("POST", url);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            console.log(xhr.status);
            console.log(xhr.responseText);
        }
    };
    var data = '{"nickname":"' + loginField.value + '"}';
    xhr.send(data);
}

function getLogin() {
    var url = "http://localhost:8080/user/getLogin";
    var xhr = new XMLHttpRequest();
    xhr.open("POST", url);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            console.log(xhr.status);
            console.log(xhr.responseText);
        }
    };
    var data = '{"nickname":"Skilk2"}';
    xhr.send(data);
}

function createRoom() {
    var url = "http://localhost:8080/room/create";
    var xhr = new XMLHttpRequest();
    xhr.open("POST", url);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            console.log(xhr.status);
            console.log(xhr.responseText);
        }};
    var data = '{"name":"' + roomName.value +'"}';
    xhr.send(data);
}

function enterRoom() {
    var url = "http://localhost:8080/id" + roomId.value;
    var xhr = new XMLHttpRequest();
    xhr.open("GET", url);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            console.log(xhr.status);
            console.log(xhr.responseText);
        }};
    xhr.send();
}



function getSse() {
    const eventSource = new EventSource('/room/getEmitter');
    console.log(eventSource)
    eventSource.onopen = (e) => console.log("open");
    eventSource.onerror = (e) => {
        if (e.readyState === EventSource.CLOSED) {
            console.log("close");
        } else {
            console.log(e);
        }
    };
    eventSource.onmessage = function (event) {
        console.log(event.data)
    }
}



function sitDown() {
    var url = "http://localhost:8080/room/sit";
    var xhr = new XMLHttpRequest();
    xhr.open("POST", url);
    xhr.setRequestHeader("Content-Type", "application/json");

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            console.log(xhr.status);
            console.log(xhr.responseText);
        }};
    var data = '{"chairId": "' + chairId.value + '"}';
    xhr.send(data);

}

function startRoom() {
    var url = "http://localhost:8080/room/start";
    var xhr = new XMLHttpRequest();
    xhr.open("POST", url);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            console.log(xhr.status);
            console.log(xhr.responseText);
        }};
    var data = '{"roomId": "' + roomId2.value + '"}';
    xhr.send(data);
}

function check() {
    var url = "http://localhost:8080/game/check";
    var xhr = new XMLHttpRequest();
    xhr.open("POST", url);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            console.log(xhr.status);
            console.log(xhr.responseText);
        }};
    xhr.send();
}

function call() {
    var url = "http://localhost:8080/game/call";
    var xhr = new XMLHttpRequest();
    xhr.open("POST", url);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            console.log(xhr.status);
            console.log(xhr.responseText);
        }};
    xhr.send();
}

function raise() {
    var url = "http://localhost:8080/game/raise";
    var xhr = new XMLHttpRequest();
    xhr.open("POST", url);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            console.log(xhr.status);
            console.log(xhr.responseText);
        }};
    var data = '{"value":"' + raiseValue.value + '"}';
    xhr.send(data);
}

function fold() {
    var url = "http://localhost:8080/game/fold";
    var xhr = new XMLHttpRequest();
    xhr.open("POST", url);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            console.log(xhr.status);
            console.log(xhr.responseText);
        }};
    var data = '{"value":"' + raiseValue.value + '"}';
    xhr.send(data);
}