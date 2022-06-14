var hostdomain = 'https://emapp.cc/';
var videos = {};//Yvideo class
var vCount = 0;

// Yvideo Class
function Yvideo(){
    this.id;
    this.url;
    this.videoId;
    this.sTime = 0;//Start time(sec)
    this.ReferenceTime = 0;
    this.player;//Youtube Player
    this.loaded = false;
    this.focused = false;
    this.removed = false;
    this.status;

    this.getId = getVideoId;
    this.addDiv = addDivBox;
    this.removeDiv = removeDivBox;
    this.setSeek = setSeek;
    this.doSync = syncTime;

}
function syncTime(idx){
    //console.log('sync: ' + idx);
    var ipt = $('#time_' + idx);
    //parseFloat(player[0].playerInfo.mediaReferenceTime)
    if((typeof videos[idx] == "undefined")){return false;}//Removed
    if(!videos[idx].focused){
        var rtime = videos[idx].player.playerInfo.mediaReferenceTime;
        ipt.val(rtime);
        videos[idx].ReferenceTime = rtime;
    }
    setTimeout(syncTime, 10, idx);
}
function setSeek(sTime){
    this.player.seekTo(sTime, true);
}
function removeDivBox(){
    this.removed = true;
    function remove_delay(idx){
        $('#wrap_' + idx).remove();
        console.log(idx + ' removed');
    }
    setTimeout(remove_delay, 100, this.id);
}
function addDivBox(){
    var parentDiv = $('#addVideos');
    var playerDiv = document.createElement('div');
    playerDiv.classList.add('wrapVideo');
    playerDiv.setAttribute("id", 'wrap_' + this.id);

    var youtubeDiv = document.createElement('div');
    youtubeDiv.classList.add('yPlayer');
    youtubeDiv.setAttribute("id", this.id);

    var timeDiv = document.createElement('div');
    timeDiv.classList.add('setTime');
    var timeDivHtml = 'Start time(sec) <input class="synctime" id="time_' + this.id + '" type="text" value="0" onkeypress="onlyNumber();" onchange="videos[\'' + this.id + '\'].player.seekTo(value)">';
    timeDivHtml += '<button class="removeVideo" onclick="removeVideo(\'' + this.id + '\')"><i class="far fa-trash-alt"></i></button>';
    timeDiv.innerHTML = timeDivHtml;

    playerDiv.append(youtubeDiv);
    playerDiv.append(timeDiv);
    parentDiv.append(playerDiv);

    // add youtube video
    this.player = new YT.Player(this.id, {
        videoId: this.videoId,//'M7lc1UVf-VE',
        events: {
            'width':'100%',
            'height':'100%',
            'onReady': onPlayerReady,
            'onStateChange': onPlayerStateChange,
        },
        playerVars: {
            //'autoplay': false,
            'origin': hostdomain + 'youtube-multi-view'
        }
    });
    setFoucsEvent();
}
function getVideoId(){
    //Check video url
    if(this.url.indexOf('youtube.com/watch?v=') == -1 && this.url.indexOf('youtu.be/') == -1){
        console.log('invalid url');
        add_toast('Warning Info', 'invalid url');
        return false;
    }
    //get video ID
    var vid;
    if(this.url.indexOf('youtube.com/watch?v=') != -1){
        var str1 = this.url.split('youtube.com/watch?v=');
        vid = str1[1];
    }else if(this.url.indexOf('youtu.be/') != -1){
        var str1 = this.url.split('youtu.be/');
        vid = str1[1];
    }
    if(vid.indexOf('&') != -1){
        var str2 = vid.split('&');
        vid = str2[0];
    }
    this.videoId = vid.trim();
    return true;
}
// Yvideo Class End

function onPlayerReady(event) {
    event.target.playVideo();
}
function onPlayerStateChange(event) {
    //console.log('onPlayerStateChange: ' + event.data + '/ID:' + event.target.h.id);
    videos[event.target.h.id].status = event.data;
    if (event.data == YT.PlayerState.PLAYING && !videos[event.target.h.id].loaded){
        videos[event.target.h.id].setSeek(0);
        event.target.pauseVideo();
        videos[event.target.h.id].loaded = true;
        videos[event.target.h.id].doSync(event.target.h.id);
    }
    if (event.data == YT.PlayerState.CUED){
        console.log('CUED');
    }
}

function addVideo() {
    var url = $('#youtube_url').val();
    var video = new Yvideo();
    video.url = url;
    if(!video.getId()){
        return
    }
    video.id = 'player_' + vCount;
    videos['player_' + vCount] = video;
    videos['player_' + vCount].addDiv();
    vCount += 1;
}
function removeVideo(vId) {
    videos[vId].removeDiv();
    delete videos[vId];
}
function setFoucsEvent(){
    $('.synctime').off('focus');
    $('.synctime').off('blur');
    $('.synctime').focus(function(){
        //console.log('synctime focus - ' + this.id);
        videos[this.id.replace('time_','')].focused = true;
    });
    $('.synctime').blur(function(){
        //console.log('synctime blur - ' + this.id);
        videos[this.id.replace('time_','')].focused = false;
    });
}
function getLink(){
    var linkUrl = hostdomain + 'watch/';
    var vs = new Array();

    for(var v in videos){
        videos[v].player.pauseVideo();
        vs.push({'v':videos[v].videoId,'t':videos[v].ReferenceTime});
    }
    //console.log(linkUrl + JSON.stringify(vs));
    var result = $('#result_link');
    result.val(linkUrl + JSON.stringify(vs));
}

function copytoclip(){
    $('#result_link').select();
    document.execCommand("copy");
    add_toast('Success Info', 'Copied to clipboard.<br>Paste it where you need it with (Ctrl+v).');
    //add_toast('Warning Info', data.error);
}
function newWindow(){
    window.open($('#result_link').val(), '_blank');
}

