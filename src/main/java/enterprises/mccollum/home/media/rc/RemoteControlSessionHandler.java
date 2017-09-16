package enterprises.mccollum.home.media.rc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.Session;

@ApplicationScoped
public class RemoteControlSessionHandler {
	Map<String, List<Session>> playerSessions = new HashMap<String, List<Session>>();
	
	public void addToSession(String player_session_id, Session ssn) {
		if(!playerSessions.containsKey(player_session_id)) {
			playerSessions.put(player_session_id, new ArrayList<Session>(3));
		}
		playerSessions.get(player_session_id).add(ssn);
		printReport();
	}
	
	public void broadcast(String msg, String ssnId, Session ssn) {
		for(Session session : playerSessions.get(ssnId)) {
			if(ssn.hashCode() != session.hashCode()) {
				try {
					session.getBasicRemote().sendText(msg);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		printReport();
	}
	
	public void printReport() {
		Logger l = Logger.getLogger(RemoteControlSessionHandler.class.getSimpleName());
		for(String ssnId : playerSessions.keySet()) {
			l.info("Session: " + ssnId + "(" + playerSessions.get(ssnId).size() + ")");
		}
	}

	public void removeFromSession(String player_session_id, Session ssn) {
		playerSessions.get(player_session_id).remove(ssn);
		if(playerSessions.get(player_session_id).size() < 1) {
			playerSessions.remove(player_session_id);
		}
	}
}
