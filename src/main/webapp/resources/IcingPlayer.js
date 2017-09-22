//reconnecting-websocket
!function(a,b){"function"==typeof define&&define.amd?define([],b):"undefined"!=typeof module&&module.exports?module.exports=b():a.ReconnectingWebSocket=b()}(this,function(){function a(b,c,d){function l(a,b){var c=document.createEvent("CustomEvent");return c.initCustomEvent(a,!1,!1,b),c}var e={debug:!1,automaticOpen:!0,reconnectInterval:1e3,maxReconnectInterval:3e4,reconnectDecay:1.5,timeoutInterval:2e3};d||(d={});for(var f in e)this[f]="undefined"!=typeof d[f]?d[f]:e[f];this.url=b,this.reconnectAttempts=0,this.readyState=WebSocket.CONNECTING,this.protocol=null;var h,g=this,i=!1,j=!1,k=document.createElement("div");k.addEventListener("open",function(a){g.onopen(a)}),k.addEventListener("close",function(a){g.onclose(a)}),k.addEventListener("connecting",function(a){g.onconnecting(a)}),k.addEventListener("message",function(a){g.onmessage(a)}),k.addEventListener("error",function(a){g.onerror(a)}),this.addEventListener=k.addEventListener.bind(k),this.removeEventListener=k.removeEventListener.bind(k),this.dispatchEvent=k.dispatchEvent.bind(k),this.open=function(b){h=new WebSocket(g.url,c||[]),b||k.dispatchEvent(l("connecting")),(g.debug||a.debugAll)&&console.debug("ReconnectingWebSocket","attempt-connect",g.url);var d=h,e=setTimeout(function(){(g.debug||a.debugAll)&&console.debug("ReconnectingWebSocket","connection-timeout",g.url),j=!0,d.close(),j=!1},g.timeoutInterval);h.onopen=function(){clearTimeout(e),(g.debug||a.debugAll)&&console.debug("ReconnectingWebSocket","onopen",g.url),g.protocol=h.protocol,g.readyState=WebSocket.OPEN,g.reconnectAttempts=0;var d=l("open");d.isReconnect=b,b=!1,k.dispatchEvent(d)},h.onclose=function(c){if(clearTimeout(e),h=null,i)g.readyState=WebSocket.CLOSED,k.dispatchEvent(l("close"));else{g.readyState=WebSocket.CONNECTING;var d=l("connecting");d.code=c.code,d.reason=c.reason,d.wasClean=c.wasClean,k.dispatchEvent(d),b||j||((g.debug||a.debugAll)&&console.debug("ReconnectingWebSocket","onclose",g.url),k.dispatchEvent(l("close")));var e=g.reconnectInterval*Math.pow(g.reconnectDecay,g.reconnectAttempts);setTimeout(function(){g.reconnectAttempts++,g.open(!0)},e>g.maxReconnectInterval?g.maxReconnectInterval:e)}},h.onmessage=function(b){(g.debug||a.debugAll)&&console.debug("ReconnectingWebSocket","onmessage",g.url,b.data);var c=l("message");c.data=b.data,k.dispatchEvent(c)},h.onerror=function(b){(g.debug||a.debugAll)&&console.debug("ReconnectingWebSocket","onerror",g.url,b),k.dispatchEvent(l("error"))}},1==this.automaticOpen&&this.open(!1),this.send=function(b){if(h)return(g.debug||a.debugAll)&&console.debug("ReconnectingWebSocket","send",g.url,b),h.send(b);throw"INVALID_STATE_ERR : Pausing to reconnect websocket"},this.close=function(a,b){"undefined"==typeof a&&(a=1e3),i=!0,h&&h.close(a,b)},this.refresh=function(){h&&h.close()}}return a.prototype.onopen=function(){},a.prototype.onclose=function(){},a.prototype.onconnecting=function(){},a.prototype.onmessage=function(){},a.prototype.onerror=function(){},a.debugAll=!1,a.CONNECTING=WebSocket.CONNECTING,a.OPEN=WebSocket.OPEN,a.CLOSING=WebSocket.CLOSING,a.CLOSED=WebSocket.CLOSED,a});

class IcingPlayer {
	constructor(movieId, rcId){
		this.rcId = rcId;
		this.player = document.getElementById(movieId);
		
		this.player.onclick = this.handleClick.bind(this);
		this.player.ondblclick = this.handleDblClick.bind(this);
		this.player.onkeypress = this.handleKeyPress.bind(this);
		this.player.onmousemove = this.onMouseMove.bind(this);
		document.body.onkeypress = this.handleKeyPress.bind(this);
		document.cancelFullScreen = document.webkitExitFullscreen || document.mozCancelFullScreen || document.exitFullscreen;
		
		this.socket = new ReconnectingWebSocket("ws://"+this.getServerHostname()+"/wsremotecontrol");
		console.log("connect: ws://"+this.getServerHostname()+"/wsremotecontrol");
		this.socket.onmessage = this.onMessage.bind(this);
		this.socket.onopen = this.onOpen.bind(this);
		
		//setup events
		/*this.player.playing = this.onPlay.bind(this);
		this.player.play = this.onPlay.bind(this);
		this.player.pause = this.onPause.bind(this);//*/
		this.player.timeupdate = this.onSeek.bind(this);
	}
	
	onOpen(event){
		console.log('Connected to server!');
		this.socket.send(JSON.stringify({'session_id': this.rcId}));
		this.socket.send(JSON.stringify({event: 'connect'}));
	}
	
	onPlay(){
		console.log("onPlay()");
		this.fireEvent('play');
	}
	
	onPause(){
		console.log("onPause()");
		this.fireEvent('pause');
	}
	
	onSeek(){
		console.log("onSeek()");
		this.fireEvent('seek');
	}
	
	fireEvent(eventName){
		this.socket.send(JSON.stringify(
		{
			event: eventName,
			ts: this.player.currentTime,
			date: new Date().getTime()
		}));
	}
	
	fireUpdate(){
		if(this.player.paused){
			this.onPause();
		}else{
			this.onPlay();
		}
	}
	
	getServerHostname(){
		let hn = window.location.hostname;
		let pt = window.location.port;
		let pn = window.location.pathname;

		if( !(pt == 80 || pt == 443) ){
			hn += ":"+pt;
		}
		if(pn.startsWith("/media")){
			hn += "/media";
		}
		return hn;
	}
	
	onMessage(msgEvt){
		console.log('Received '+msgEvt.data);
		let msg = JSON.parse(msgEvt.data);
		let correction = (new Date().getTime() - msg.date)/1000;
		switch(msg.event) {
		case 'play':
			this.player.play();
			console.log("play received");
			break;
		case 'pause':
			this.player.pause();
			correction = 0;
			console.log("pause received")
			break;
		case 'seek':
			console.log("Seek received");
			break;
		case 'connect':
			this.fireUpdate();
			return;
		default:
			console.log("unknown message " + msg.event + " received");
		}
		this.player.currentTime = msg.ts + correction;
	}
	
	toggleFullscreen(){
		if(this.player.fullScreen || this.player.webkitDisplayingFullscreen || this.player.mozFullScreen){
			document.cancelFullScreen();
		}else{
			if(this.player.requestFullScreen){
				this.player.requestFullScreen();
			}else if(this.player.webkitRequestFullScreen){
				this.player.webkitRequestFullScreen();
			}else if(this.player.mozRequestFullScreen){
				this.player.mozRequestFullScreen();
			}else{
				alert("No fullscreen playback available in your browser.");
			}
		}
	}

	playPause(){
		if(this.player.paused){
			this.player.play();
			this.onPlay();
		}else{
			this.player.pause();
			this.onPause();
		}
	}
	
	showControls(){
		this.player.setAttribute("controls","");
	}
	
	toggleMute(){
		if(this.player.muted){
			this.player.muted = false;
		}else{
			this.player.muted = true;
		}
	}
	
	handleKeyPress(event){
		switch(event.keyCode){
			case 32: //spacebar: play/pause
				this.playPause();
				break;
			case 72: //h: jump back 15 seconds
			case 104:
				this.player.currentTime = this.player.currentTime-15;
				this.onSeek();
				break;
			case 76: //l: jump ahead 15 seconds
			case 108:
				this.player.currentTime = this.player.currentTime+15;
				this.onSeek();
				break;
			case 102: //f: fullscreen
				this.toggleFullscreen();
				break;
			case 109: //m: mute/unmute
				this.toggleMute();
				break;
			case 86: //v: display session id
			case 118:
				alert('Send this link to a friend so they can join in: '+window.location.href+'?rc_id='+this.rcId);
				break;
			default:
				console.log(event.keyCode);
		}
		this.showControls();
	}
	
	handleClick(){
		this.playPause();
		this.onMouseMove();
	}
	
	handleDblClick(){
		this.toggleFullscreen();
		this.onMouseMove();
	}
	
	mouseStopped(){
		this.player.style.cursor = 'none';
	}
	
	onMouseMove(){
		this.player.style.cursor = null;
		clearTimeout(this.cursorMoveTimer);
		this.cursorMoveTimer = setTimeout(this.mouseStopped.bind(this), 1500);
	}
}