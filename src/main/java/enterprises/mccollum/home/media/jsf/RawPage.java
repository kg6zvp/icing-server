package enterprises.mccollum.home.media.jsf;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.github.amr.mimetypes.MimeTypes;

import enterprises.mccollum.jee.urlutils.UrlContextUtils;


@Named
@RequestScoped
public class RawPage{
	@Inject
	UrlContextUtils urlCtxUtils;

	public RawPage(){}
	
	public String getTitle(){
		String filePath = getParam("filePath");
		String fileName = filePath.substring(filePath.lastIndexOf("/")+1);
		return fileName.substring(0, fileName.lastIndexOf("."));
	}
	
	public String getMimeType(){
		String filePath = getParam("filePath");
		String extension = filePath.substring(filePath.lastIndexOf(".")+1);
		return MimeTypes.getInstance().getByExtension(extension).getMimeType();
	}
	
	public String getUrl(){
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		return FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath()+"/api/raw/"+getParam("source")+"/"+getParam("filePath");
	}
	
	private String getParam(String param){
		return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(param); /*.getSession(true)).getAttribute(param);*/
	}
}
