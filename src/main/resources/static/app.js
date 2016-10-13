var stompClient = null;

function connect() {
    var socket = new SockJS('/stompendpoint');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        
        stompClient.subscribe('/topic/newpoint', function (data) {
        var theObject =JSON.parse(data.body);
        
            
            var canvas = document.getElementById('myCanvas');
            var context = canvas.getContext('2d');
            context.beginPath();
            context.arc(theObject.x,theObject.y,2,2,3*Math.PI);
            context.stroke();
        });
        stompClient.subscribe('/topic/newpolygon', function (data) {
        var theObject =JSON.parse(data.body);
        
            
            var canvas = document.getElementById('myCanvas');
            var context = canvas.getContext('2d');
            context.fillStyle = '#f00';
            context.beginPath();
            context.moveTo(theObject[0].x,theObject[0].y);
            context.lineTo(theObject[1].x,theObject[1].y);
            context.lineTo(theObject[2].x,theObject[2].y);
            context.lineTo(theObject[3].x,theObject[3].y);
            context.closePath();
            context.fill();
            
           
        });
    });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendPoint(){
    var coorx = $("#x").val();
    var coory = $("#y").val();
    stompClient.send("/topic/newpoint", {}, JSON.stringify({x: coorx, y: coory}));
}

$(document).ready(
        function () {
            connect();
            console.info('connecting to websockets');

            function getMousePos(canvas, evt) {
                var rect = canvas.getBoundingClientRect();
                return {
                    x: evt.clientX - rect.left,
                    y: evt.clientY - rect.top
                };
            }
            var canvas = document.getElementById('myCanvas');
            var context = canvas.getContext('2d');

            canvas.addEventListener('mousedown', function (evt) {
                var mousePos = getMousePos(canvas, evt);
                var message = 'Mouse position: ' + mousePos.x + ',' + mousePos.y;
                stompClient.send("/app/newpoint", {}, JSON.stringify({x: mousePos.x, y: mousePos.y}));
                //writeMessage(canvas, message);
            }, false);

        }

);

