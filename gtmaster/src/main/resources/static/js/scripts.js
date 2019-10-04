var timers = [];

function timeEvent(key, fn, args, ms) {
    clearTimeout(timers[key]);
    timers[key] = setTimeout(function () {
        fn(args);
    }, ms);
}

function commitRequest(prefix, id, url, field, reload) {
    var userIndex = document.getElementById(prefix + id).firstChild.value;
    var xhttp = new XMLHttpRequest();
    xhttp.open("POST", url, true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send("id=" + id + "&" + field + "=" + encodeURIComponent(userIndex));
    if (reload) {
        xhttp.onreadystatechange = function () {
            if (this.readyState === 4 && this.status === 200) {
                document.open();
                document.write(this.responseText);
                document.close();
            }
        };
    }
}

function changeGolyaName(userId) {
    commitRequest("n", userId, "/admin/golya/update", "name", false);
}

function changeGolyaPhone(userId) {
    commitRequest("ph", userId, "/admin/golya/update", "phone", false);
}

function changeGolyaParentPhone(userId) {
    commitRequest("pp", userId, "/admin/golya/update", "parentPhone", false);
}

function changeBekaUser(userId) {
    commitRequest("u", userId, "/admin/beka/update", "userId", true);
}

function changeBekaTeam(userId) {
    commitRequest("t", userId, "/admin/beka/update", "teamId", true);
}

function changeGolyaTeam(userId) {
    commitRequest("t", userId, "/admin/golya/update", "teamId", true);
}

function changeGolyaClass(userId) {
    commitRequest("c", userId, "/admin/golya/update", "classes", true);
}

function changeGolyaHouse(userId) {
    commitRequest("h", userId, "/admin/golya/update", "house", true);
}

function changeBekaName(userId) {
    commitRequest("n", userId, "/admin/beka/update", "fullName", false);
}
function changeCalendarLeader(userId) {
    commitRequest("l", userId, "/admin/calendar/update", "leader", true);
}

function changeCalendarDeputy(userId) {
    commitRequest("d", userId, "/admin/calendar/update", "deputy", true);
}