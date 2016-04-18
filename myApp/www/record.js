function notifyTemperatureIncrease(){

document.addEventListener('deviceready', function () {
     var sound = device.platform == 'Android' ? 'file://sound.mp3' : 'file://beep.caf';

    // Schedule notification for tomorrow to remember about the meeting
    cordova.plugins.notification.local.schedule({
        id: 10,
        title: "Be cautious!!! Babeque about to burn!!",
        text: "Your barbeque is about to burn out. Please check it.",
        sound: sound,
        icon: 'res://icon',
        smallIcon: 'res://ic_popup_sync'
    });

    // Join BBM Meeting when user has clicked on the notification 
    cordova.plugins.notification.local.on("click", function (notification) {
        if (notification.id == 10) {
            
            //joinMeeting(notification.data.meetingId);
        }
    });

}, false);

}
window.onload = function () {
  var url = document.location.href;
  url += "?stop=hello"
  var params = url.split('?')[1].split('&');
  var data = {};
  var tmp;
  for (var i = 0, l = params.length; i < 1; i++){
    tmp = params[i].split('=');
    data[tmp[0]] = tmp[1];
  }
  document.getElementById('header').innerHTML = data.name;
}

function stopJS() {
  var url = 'index.html';

  document.location.href = url;
}

// function incrementJS() {
//   var currentTemp = document.getElementById('temp').innerHTML;
//   if (currentTemp[15] == "."){
//     currentTemp = currentTemp.substring(14, 17);
//   } else {
//     currentTemp = currentTemp.substring(14, 15);
//   }
//   currentTemp = parseFloat(currentTemp);
//   currentTemp += .5;
//   currentTemp.toString();
//   currentTemp += " F";
//   currentTemp = "Current Temp: " + currentTemp;
//   document.getElementById('temp').innerHTML = currentTemp;
// }

function parseIsoDatetime(dtstr) {
    var dt = dtstr.split(/[: T-]/).map(parseFloat);
    return new Date(dt[0], dt[1] - 1, dt[2], dt[3] || 0, dt[4] || 0, dt[5] || 0, 0);
}

window.onload = function () {
    var host = "d1e69ef8.ngrok.io"
    var ws = new WebSocket("ws://"+host+"/bbq/websocket/bbq");
    ws.onopen = function(){
    };
    ws.onmessage = function(message){
        //document.getElementById("data").innerHTML += message.data+"<br>";
        var obj = JSON.parse(message.data);
        var clientid = obj['clientid'];
        var xval = parseIsoDatetime(obj['timestamp']);
        var yval = obj['value'];
        document.getElementById("currenttemp").innerHTML = yval;
        if(yval==450){
            notifyTemperatureIncrease();
        }
        updateChart(xval,yval);
    };
    function postToServer(){
        ws.send(document.getElementById("msg").value);
        document.getElementById("msg").value = "";
    }
    function closeConnect(){
        ws.close();
    }


    var dps = []; // dataPoints

    var chart = new CanvasJS.Chart("chartContainer",{
        title :{
            text: "Current Temperature Data"
        },
        axisX:{
            title: "Time",
            gridThickness: 2,
            intervalType: "hour",
            valueFormatString: "hh:MM:ss TT",
            labelAngle: -20
        },
        axisY: {
            title: "Temperature"
        },
        data: [{
            type: "line",
            dataPoints: dps
        }]
    });

    var xVal = 0;
    var yVal = 100;
    var updateInterval = 100;
    var dataLength = 500; // number of dataPoints visible at any point

    var updateChart = function (xval,yval) {

        dps.push({
            x: xval,
            y: yval
        });

        if (dps.length > dataLength)
        {
            dps.shift();
        }

        chart.render();

    };
}
