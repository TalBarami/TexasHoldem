var serverAddress = 'http://localhost:8080';

function login(){
    var enteredUser=document.getElementById('userName').value.toString();
    var enteredPassword=document.getElementById('pass').value.toString();

    sessionStorage.setItem("userName", enteredUser);
    sessionStorage.setItem("password", enteredPassword);

    var xhr = new XMLHttpRequest();
    xhr.open('POST', serverAddress + '/session',true);
    xhr.setRequestHeader('Content-Type', 'application/json; charset=utf-8');
    xhr.onreadystatechange = function () {
        var json = JSON.parse(xhr.responseText);
        if(xhr.readyState === 4){
            if(xhr.status === 200){
                alert(json.message + '.\nRedirecting to statistics page.');
                window.location="statistics.html";
            } else {
                alert(json.message);
            }
        }
    };
    var data = JSON.stringify({
        "username":enteredUser,
        "password":enteredPassword
    });

    xhr.send(data);
    clearFields();
}

function logOut(){
    var xhr = new XMLHttpRequest();
    xhr.open('DELETE', serverAddress + '/session',true);
    xhr.setRequestHeader('Content-Type', 'application/json; charset=utf-8');
    xhr.onreadystatechange = function () {
        var json = JSON.parse(xhr.responseText);
        if (xhr.readyState === 4 && xhr.status === 200){
            alert(json.message+"\nRedirecting to main page.");
            window.location="index.html";
        }
    };
    var data = JSON.stringify({
        "username":sessionStorage.getItem("userName"),
        "password":sessionStorage.getItem("password")
    });
    xhr.send(data);
}

function getTop20GamesPlayed(){
    getTop20ByCategory("gamesPlayed", "Games Played");
}

function getTop20GrossProfit(){
    getTop20ByCategory("grossProfit", "Gross Profit");
}

function getTop20CashGain(){
    getTop20ByCategory("cashGain", "Cash Gain");
}

function getTop20ByCategory(category,tableCategoryName){
    var xhr = new XMLHttpRequest();
    xhr.open('GET', serverAddress + '/statistics/' + category + '/',true);
    xhr.onreadystatechange = function () {
        var json = JSON.parse(xhr.responseText);
        if(xhr.readyState === 4){
            if(xhr.status === 200){
                buildTableDataFromJsonData(json.data,tableCategoryName);
            } else {
                alert(json.message+"\nError while retrieving top 20 by" + category + '.');
            }
        }
    };
    xhr.send(null);
}

function buildTableDataFromJsonData(jsonArrayData,statName){
    var dataToShow="<table><tr><th>#</th><th>User Name</th><th>" + statName + "</th>";
    for(var i=1;i<=jsonArrayData.length;i++){
        dataToShow=dataToShow + "<tr><td>"+ i + "</td><td>" + jsonArrayData[i-1].key + "</td><td>" + jsonArrayData[i-1].value +"</td></tr>"
    }
    dataToShow+="</table>";
    showLeaderBoardResult(dataToShow);
}

function showLeaderBoardResult(dataToShow){
    var resultDiv=document.getElementById("leaderBoardData");
    resultDiv.innerHTML=dataToShow;
}

function initUserSelectionComboBox() {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', serverAddress + '/user/',true);
    xhr.onreadystatechange = function () {
        var json = JSON.parse(xhr.responseText);
        if(xhr.readyState === 4){
            if(xhr.status === 200){
                fillUsersCombobox(json.data);

            } else {
                alert(json.message+"\nError while trying to get all user names.");
            }
        }
    };
    xhr.send(null);
}

function fillUsersCombobox(data){
    var select = document.getElementById("userPicker");
    for(var i = 0; i < data.length; i++) {
        var opt = data[i];
        var el = document.createElement("option");
        el.textContent = opt;
        el.value = opt;
        select.appendChild(el);
    }
}

function getUserAvgCashGain(){
    if (!isUserSelected()) {
        return;
    }

    var pickedUsers = getUserPickerValue();
    var xhr = new XMLHttpRequest();
    xhr.open('GET', serverAddress + '/statistics/' + pickedUsers + '/avgNeto',true);
    xhr.onreadystatechange = function () {
        var json = JSON.parse(xhr.responseText);
        if(xhr.readyState === 4){
            if(xhr.status === 200){
                var dataToShow = pickedUsers + "'s average cash gain is : " + json.data + '$.';
                showUserStatisticsResult(dataToShow);
            } else {
                alert(json.message+"\nError while retrieving user's average cash gain.");
            }
        }
    };
    xhr.send(null);
}

function getUserAvgGrossProfit() {
    if (!isUserSelected()) {
        return;
    }

    var pickedUsers = getUserPickerValue();
    var xhr = new XMLHttpRequest();
    xhr.open('GET', serverAddress + '/statistics/' + pickedUsers + '/avgGross',true);
    xhr.onreadystatechange = function () {
        var json = JSON.parse(xhr.responseText);
        if(xhr.readyState === 4){
            if(xhr.status === 200){
                var dataToShow = pickedUsers + "'s average gross profit is : " + json.data + '$.';
                showUserStatisticsResult(dataToShow);
            } else {
                alert(json.message+"\nError while retrieving user's average gross profit.");
            }
        }
    };
    xhr.send(null);
}

function showUserStatisticsResult(dataToShow){
    var resultDiv=document.getElementById("userStatisticsData");
    resultDiv.innerHTML=dataToShow;
}

function isUserSelected(){
    var selectedValue = getUserPickerValue();
    if (selectedValue === 'Select A Player') {
        alert("Can't show requested information.\nPlease choose a player.");
        return false;
    }
    return true;
}

function getUserPickerValue(){
    var userSelectionComboBox = document.getElementById("userPicker");
    return userSelectionComboBox.options[userSelectionComboBox.selectedIndex].value;
}

function clearFields(){
    document.getElementById('userName').value="";
    document.getElementById('pass').value="";
}
