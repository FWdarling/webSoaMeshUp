
var url = "http://fwdarling2020.cn:8080/api"
var list = document.getElementById("hotList");
var typeTable = document.getElementById("typeTable");
var contentTable = document.getElementById("contentTable");
var typeTr = document.getElementById("typeTr");
var textTran = document.getElementById("translation");

function getHotList(){
    $.ajax({
        url: url + "/search/hotlist",
        type: "GET",
        dataType: "json",
        success: function(data){
            for(var i = 0; i < 5; i++){
                var rand = parseInt(Math.random()*(40),10);
                var li = document.createElement("li");
                li.setAttribute("class", "li");
                li.innerHTML = data[rand];
                list.appendChild(li);
            } 
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert("请求失败: " + XMLHttpRequest + "," + textStatus + "," + errorThrown);
        }
    })
}

function search(){
    var text = document.getElementById("searchIn").value;
    var type = document.getElementById("searchType").value;
    if(type == "music"){
        $.ajax({
            url: url + "/search/music?key=" + text,
            type :"GET",
            dataType: "xml",
            success:function(data){
                //console.log(data);
                typeTr.children.item(0).innerHTML = "id";
                typeTr.children.item(1).innerHTML = "name";
                typeTr.children.item(2).innerHTML = "artist";
                typeTr.children.item(3).innerHTML = "type";
                var children = contentTable.children;
                for(var i = children.length - 1; i >= 0; i--){
                    children[i].remove();
                }
                var songsName = data.getElementsByTagName('name');
                var songsId = data.getElementsByTagName('id');
                var artists = data.getElementsByTagName('artist');
                var types = data.getElementsByTagName('type');
                for(var i = 0; i < songsName.length; i++){
                    var newRow = contentTable.insertRow();
                    newRow.insertCell(0).innerHTML = songsId[i].innerHTML;
                    newRow.insertCell(1).innerHTML = songsName[i].innerHTML;
                    newRow.insertCell(2).innerHTML = artists[i].innerHTML;
                    newRow.insertCell(3).innerHTML = types[i].innerHTML;
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert("请求失败: " + XMLHttpRequest + "," + textStatus + "," + errorThrown);
            }
        })
    }else{
        $.ajax({
            url: url + "/video?keyword=" + text,
            type :"GET",
            dataType: "json",
            success:function(data){
                //console.log(data);
                typeTr.children.item(0).innerHTML = "title";
                typeTr.children.item(1).innerHTML = "author";
                typeTr.children.item(2).innerHTML = "description";
                typeTr.children.item(3).innerHTML = "url";
                var children = contentTable.children;
                for(var i = children.length - 1; i >= 0; i--){
                    children[i].remove();
                }
                for(var i = 0; i < data.length; i++){
                    var d = data[i];
                    var newRow = contentTable.insertRow();
                    newRow.insertCell(0).innerHTML = d["title"];
                    newRow.insertCell(1).innerHTML = d["author"];
                    newRow.insertCell(2).innerHTML = d["description"];
                    newRow.insertCell(3).innerHTML = "<a href = \"" + d["url"] + "\">" + d["title"] + "</a>";
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert("请求失败: " + XMLHttpRequest + "," + textStatus + "," + errorThrown);
            }
        })
    }
}

function translated(){
    var text = document.getElementById("searchIn").value;
    $.ajax({
        url: url + "/translate?text=" + text,
        type: "GET",
        dataType: "text",
        success: function(data){
            textTran.innerHTML = data;
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert("请求失败: " + XMLHttpRequest + "," + textStatus + "," + errorThrown);
        }
    })
}

function select(){
    var text = document.getElementById("titleIn").value;
    var type = document.getElementById("searchTypePlay").value;
    var lyrics = document.getElementById("lyrics");
    $.ajax({
        url: url + "/lyrics/" + text + "?type=" + type,
        type: "GET",
        dataType: "text",
        success: function(data){
            var res = data.split(/\[\d*\:\d*.\d*\]/);
            var children = lyrics.children;
            for(var i = children.length - 1; i >= 0; i--){
                children[i].remove();
            }
            for(var i = 0; i < res.length; i++){
                var li = document.createElement("li");
                li.setAttribute("class", "li");
                li.innerHTML = res[i];
                lyrics.appendChild(li);
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert("请求失败: " + XMLHttpRequest + "," + textStatus + "," + errorThrown);
        }
    })
}

function play(){
    var text = document.getElementById("titleIn").value;
    var type = document.getElementById("searchTypePlay").value;
    $.ajax({
        url: url + "/play/" + text + "?type=" + type,
        type: "GET",
        dataType: "text",
        success: function(data){
            window.open(data, "播放音乐");
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert("请求失败: " + XMLHttpRequest + "," + textStatus + "," + errorThrown);
        }
    })
}

function func(){
    if(document.getElementById("titleIn").value == "请输入歌曲id并选择type")
        document.getElementById("titleIn").value = "";
}

function func1(){
    if(document.getElementById("titleIn").value == "")
        document.getElementById("titleIn").value = "请输入歌曲id并选择type";
}

getHotList();