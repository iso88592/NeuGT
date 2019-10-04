var timers = [];

function timeEvent(key, fn, args, ms) {
    clearTimeout(timers[key]);
    timers[key] = setTimeout(function() {
        fn(args);
    }, ms);
}

function changeGolyaName(userId) {
    var newUserIndex = document.getElementById('n' + userId).firstChild.value;
    var xhttp = new XMLHttpRequest();
    xhttp.open("POST", "/admin/golya/update", true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send("id=" + userId + "&name=" + encodeURIComponent(newUserIndex));
}

function changeGolyaPhone(userId) {
    var newUserIndex = document.getElementById('ph' + userId).firstChild.value;
    var xhttp = new XMLHttpRequest();
    xhttp.open("POST", "/admin/golya/update", true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send("id=" + userId + "&phone=" + encodeURIComponent(newUserIndex));
}

function changeGolyaParentPhone(userId) {
    var newUserIndex = document.getElementById('pp' + userId).firstChild.value;
    var xhttp = new XMLHttpRequest();
    xhttp.open("POST", "/admin/golya/update", true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send("id=" + userId + "&parentPhone=" + encodeURIComponent(newUserIndex));
}

function changeBekaUser(userId) {
    var newUserIndex = document.getElementById('u' + userId).firstChild.value;
    var xhttp = new XMLHttpRequest();
    xhttp.open("POST", "/admin/beka/update", true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send("id=" + userId + "&userId=" + newUserIndex);
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            document.open();
            document.write(this.responseText);
            document.close();
        }
    };
}

function changeBekaTeam(userId) {
    var newUserIndex = document.getElementById('t' + userId).firstChild.value;
    var xhttp = new XMLHttpRequest();
    xhttp.open("POST", "/admin/beka/update", true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send("id=" + userId + "&teamId=" + newUserIndex);
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            document.open();
            document.write(this.responseText);
            document.close();
        }
    };
}

function changeBekaUser(userId) {
    var newUserIndex = document.getElementById('u' + userId).firstChild.value;
    var xhttp = new XMLHttpRequest();
    xhttp.open("POST", "/admin/beka/update", true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send("id=" + userId + "&userId=" + newUserIndex);
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            document.open();
            document.write(this.responseText);
            document.close();
        }
    };
}

function changeBekaTeam(userId) {
    var newUserIndex = document.getElementById('t' + userId).firstChild.value;
    var xhttp = new XMLHttpRequest();
    xhttp.open("POST", "/admin/beka/update", true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send("id=" + userId + "&teamId=" + newUserIndex);
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            document.open();
            document.write(this.responseText);
            document.close();
        }
    };
}
