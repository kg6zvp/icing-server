package enterprises.mccollum.home.media.rc;

import java.io.IOException;
import java.io.StringReader;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ApplicationScoped
@ServerEndpoint("/wsremotecontrol")
public class RemoteControlEndpoint {
	@Inject
	RemoteControlSessionHandler hndlr;
	
	private final String SSN_ID = "session_id";
	
	@OnOpen
	public void open(Session ssn, EndpointConfig conf) {
		System.out.println("Connected to client! ");
	}
	
	@OnClose
	public void close(Session ssn, CloseReason closeReason) {
		hndlr.removeFromSession( (String)ssn.getUserProperties().get(SSN_ID), ssn);
	}
	
	@OnError
	public void error(Session ssn, Throwable error) {
		System.out.println("Error!");
		error.printStackTrace();
	}
	
	@OnMessage
	public void handleMessage(Session ssn, String msg) {
		System.out.println("Received message: "+msg);
		try (JsonReader jsonReader = Json.createReader(new StringReader(msg))) {
			JsonObject jsMsg = jsonReader.readObject();
			if( !ssn.getUserProperties().containsKey(SSN_ID) ) {
				//REGISTER
				if(jsMsg.containsKey(SSN_ID)) {
					hndlr.addToSession(jsMsg.getString(SSN_ID), ssn);
					ssn.getUserProperties().put(SSN_ID, jsMsg.getString(SSN_ID));
				} else {
					ssn.getBasicRemote().sendText("unregistered client not providing session ID. Closing");
					ssn.close();
				}
			}
			if(jsMsg.containsKey("event")) {
				hndlr.broadcast(msg, (String)ssn.getUserProperties().get(SSN_ID), ssn);
			}
		} catch (IOException e) {
			e.printStackTrace(); //ssn.close()
		}
	}
}
