var params = new Array;
var videos = {};//Yvideo class
var vCount = 0;
//Buttons
var playBtn = $(".playbtn");
var autoVolume = $(".autoVolume");
var bAutoVolume = false;
var moveRange = false;
var playing = false;
var vRatio = {'width':16, 'height':9};

// Yvideo Class
function Yvideo(){
    this.id;
    this.url;
    this.videoId;
    this.sTime = 0;//Start time(sec)
    this.ReferenceTime = 0;
    this.player;//Youtube Player
    this.loaded = false;
    this.status = -1;
    this.focused = false;
    this.addDiv = addDivBox;
    this.setSeek = setSeek;
    this.doSync = syncTime;

}
function syncTime(idx){
    //console.log('sync: ' + idx);
    if(!videos[idx].focused){
        var rtime = videos[idx].player.playerInfo.mediaReferenceTime;
        videos[idx].ReferenceTime = rtime;
    }
    // Sync Panel-Range
    if(!moveRange && idx == 'player_0'){
        var dTime = videos[idx].ReferenceTime;
        var dTimeFormat = new Date(dTime * 1000).toISOString().substr(11, 8);
        $('#formControlRange').prop('value', dTime);
        $('.panel_time').html(dTimeFormat);
    }
    setTimeout(syncTime, 10, idx);
}
function setSeek(secTime){
    console.log('setSeek - ' + secTime);
    this.player.seekTo(secTime, true);
}
function addDivBox(){
    var parentDiv = $('body');
    var youtubeDiv = document.createElement('div');
    youtubeDiv.classList.add('div_viewer');
    youtubeDiv.setAttribute("id", this.id);
    parentDiv.append(youtubeDiv);
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
            'autoplay': 1,
            'origin': 'http://localhost:11160/watch'
        }
    });
}
// Yvideo Class End

function onPlayerReady(event) {
    event.target.playVideo();
    console.log('onPlayerReady - ' + event.target.h.id);

    if (event.target.h.id == 'player_0'){
        var maxSec = videos['player_0'].player.playerInfo.duration;
        var sTime = videos['player_0'].sTime;
        $('#formControlRange').prop('min', '0');
        $('#formControlRange').prop('value', sTime);
        $('#formControlRange').prop('max', maxSec);
    }
}
function onPlayerStateChange(event) {
    //console.log('onPlayerStateChange: ' + event.data + '/ID:' + event.target.h.id);
    videos[event.target.h.id].status = event.data;
    if (event.data == YT.PlayerState.PLAYING && !videos[event.target.h.id].loaded){
        videos[event.target.h.id].setSeek(videos[event.target.h.id].sTime);
        event.target.pauseVideo();
        videos[event.target.h.id].loaded = true;
        videos[event.target.h.id].doSync(event.target.h.id);
        playBtn.removeClass('paused');
    }
    if (event.data == YT.PlayerState.ENDED){
        console.log('ENDED');
        for(var video in videos){
            if(videos[video].status != 0){
                return false;
            }
        }
        console.log('All ENDED');
        playBtn.removeClass('paused');
        for(var video in videos){
            videos[video].loaded = false;
            videos[video].player.playVideo();
        }
    }
    if (event.data == YT.PlayerState.CUED){
        console.log('CUED');
    }
}
function allPlay(){
    for(var video in videos){
        videos[video].player.playVideo();
    }
    playing = true;
}
function allPause(){
    for(var video in videos){
        videos[video].player.pauseVideo();
    }
    playing = false;
}

//Add Videos
function addVideo() {
    if((typeof YT !== "undefined") && YT && YT.Player){
        for(var v in params){
            var video = new Yvideo();
            video.videoId = params[v]['v'];
            video.sTime = params[v]['t'];
            video.id = 'player_' + vCount;

            videos['player_' + vCount] = video;
            videos['player_' + vCount].addDiv();

            vCount += 1;
        }
        setSize();
        initPanelPos();
    }else{
        setTimeout(addVideo, 100);
    }
}

$(window).resize(function(){
    setSize();
    initPanelPos();
});

// set Iframe Size
function setSize(){
    //console.log('setSize()');
    var doc_width = document.documentElement.clientWidth;
    var doc_height = document.documentElement.clientHeight;
    var r_width;
    var r_height;
    var totalArea = doc_width* doc_height;
    var remainRatio = 1.0;
    var bestCol = 0;
    var view_count = Object.keys(videos).length;
    for(var col=1; col <= view_count; col++){
        var width =  doc_width / col;
        var rows = Math.ceil(view_count / col);
        var height = width * (vRatio['height'] / vRatio['width']);
        if ((height * rows) > doc_height){
            height = doc_height/ rows;
            width = height * (vRatio['width'] / vRatio['height']);
        }

        var nowRatio = (totalArea - (width * height * view_count) ) / totalArea;
        if (nowRatio >= 0 && nowRatio <= remainRatio){
            remainRatio = nowRatio;
            r_width = width;
            r_height = height;
            bestCol = col;
        }
        //console.log('Best(' + bestCol + ')-remainRatio:' + remainRatio);
    }
    //set Youtube Iframe size.
    var viewer = $('.div_viewer');
    viewer.css('width', r_width + 'px');
    viewer.css('height', r_height + 'px');

    //set body padding.
    $('body').css('padding-left',  '0px');
    if (doc_width > (r_width * bestCol)){
        var lMargin = (doc_width - (r_width * bestCol))/2;
        $('body').css('padding-left',  lMargin + 'px');
    }
    var rows = Math.ceil(view_count / bestCol);

    $('body').css('padding-top', '0px');
    if (doc_height > (r_height * rows)){
        var tMargin = (doc_height - (r_height * rows)) / 2;
        $('body').css('padding-top',  tMargin + 'px');
    }
}

//set Control Panel
dragElement(document.getElementById("control_panel"));
function initPanelPos(){
    var doc_width = document.documentElement.clientWidth;
    var doc_height = document.documentElement.clientHeight;
    var pWidth = $('#control_panel').width();
    var pHeigth = $('#control_panel').height();
    $('#control_panel').css('top', (doc_height - pHeigth - 20) + 'px');
    $('#control_panel').css('left', ((doc_width/2) -(pWidth/2)) + 'px');
}

function dragElement(elmnt) {
    var pos1 = 0, pos2 = 0, pos3 = 0, pos4 = 0;
    if (document.getElementById(elmnt.id)) {
        document.getElementById(elmnt.id).onmousedown = dragMouseDown;
    } else {
        elmnt.onmousedown = dragMouseDown;
    }
    function dragMouseDown(e) {
        if(e.path[0].id == 'formControlRange' || e.path[0].id == 'autoVolumeSec'){return	}//pass input[range]
        e = e || window.event;
        e.preventDefault();
        pos3 = e.clientX;
        pos4 = e.clientY;
        document.onmouseup = closeDragElement;
        document.onmousemove = elementDrag;
    }
    function elementDrag(e) {
        e = e || window.event;
        e.preventDefault();
        pos1 = pos3 - e.clientX;
        pos2 = pos4 - e.clientY;
        pos3 = e.clientX;
        pos4 = e.clientY;
        elmnt.style.top = (elmnt.offsetTop - pos2) + "px";
        elmnt.style.left = (elmnt.offsetLeft - pos1) + "px";
    }
    function closeDragElement() {
        document.onmouseup = null;
        document.onmousemove = null;
    }
}

// Panel-Play/Pause Button Event
playBtn.click(function() {
    togglePlayButton();
});
function togglePlayButton(){
    playBtn.toggleClass("paused");
    if(playBtn.hasClass('paused')){
        allPlay();
    }else{
        allPause();
    }
    return false;
}

//Auto Volume Button Event
autoVolume.click(function() {
    toggleautoVolume();
});
function toggleautoVolume(){

    autoVolume.toggleClass("auto");
    if(autoVolume.hasClass('auto')){
        bAutoVolume = true;
        changeVolume('player_0');
    }else{
        bAutoVolume = false;
    }
    return false;
}
function changeVolume(vId){
    //console.log('tick-changeVolume-' + vId);
    if(!bAutoVolume){return;}
    for(var video in videos){
        if(video == vId){
            videos[video].player.unMute();
            for (var n=1; n < 5; n=n+2){
                setTimeout(noticeVol, n*100, video, 'hidden');
                setTimeout(noticeVol, (n+1)*100, video, 'visible');
            }
        }else{
            videos[video].player.mute();
        }
    }
    var aTime = $('#autoVolumeSec').val();
    var idx = parseInt(vId.replace('player_', ''));
    var view_count = Object.keys(videos).length;
    idx = idx + 1;
    if (view_count <= idx){
        idx = 0
    }
    setTimeout(changeVolume, (aTime * 1000), 'player_' + idx);
}


function noticeVol(vId, visib){
    $('#' + vId).css('visibility', visib);
}

//Panel-resetSync Button Click Event
function resetSync(){
    console.log('resetSync()');
    for(var video in videos){
        videos[video].setSeek(videos[video].sTime);
    }
}
//Panel-Sync Button Click Event
function resyncTime(){
    //console.log('syncTime()');
    var totalPlayTime = parseFloat(videos['player_0'].ReferenceTime) - parseFloat(videos['player_0'].sTime);
    for(var video in videos){
        var setTime = parseFloat(totalPlayTime) + parseFloat(videos[video].sTime)
        videos[video].setSeek(setTime);
    }
}
//Panel-ratio Button Click Event
function changeVratio(){
    var w = vRatio['width'];
    var h = vRatio['height'];
    vRatio['width'] = h;
    vRatio['height'] = w;
    $('.ratiobtn').html(vRatio['width'] + ':' + vRatio['height']);
    setSize();
}

//Panel-Range Moving Event
$('#formControlRange').focus(function(){
    moveRange = true;
});
$('#formControlRange').blur(function(){
    moveRange = false;
});
$('#formControlRange').on('change',function(){
    videos['player_0'].setSeek($(this).val());
    $('#formControlRange').blur();
    setTimeout(resyncTime, 100);
});