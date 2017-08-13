
class IcingPlayer {
	constructor(movieId){
		this.player = document.getElementById(movieId);
		
		this.player.onclick = this.handleClick.bind(this);
		this.player.ondblclick = this.handleDblClick.bind(this);
		this.player.onkeypress = this.handleKeyPress.bind(this);
		this.player.onmousemove = this.onMouseMove.bind(this);
		document.body.onkeypress = this.handleKeyPress.bind(this);
		document.cancelFullScreen = document.webkitExitFullscreen || document.mozCancelFullScreen || document.exitFullscreen;
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
		}else{
			this.player.pause();
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
				break;
			case 76: //l: jump ahead 15 seconds
			case 108:
				this.player.currentTime = this.player.currentTime+15;
				break;
			case 102: //f: fullscreen
				this.toggleFullscreen();
				break;
			case 109: //m: mute/unmute
				this.toggleMute();
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