package enterprises.mccollum.home.media.rc;

import java.util.UUID;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import static enterprises.mccollum.home.media.jsf.FacesUtils.getParam;

@Named
@RequestScoped
public class RemoteControl {
	public String getId() {
		String id = getParam("rc_id");
		if(id != null)
			return id;
		return UUID.randomUUID().toString();
	}
}
