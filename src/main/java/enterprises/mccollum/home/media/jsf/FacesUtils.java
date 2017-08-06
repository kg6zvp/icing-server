package enterprises.mccollum.home.media.jsf;

import javax.faces.context.FacesContext;

public class FacesUtils {

	public static String getParam(String paramName){
		return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(paramName);
	}
}
