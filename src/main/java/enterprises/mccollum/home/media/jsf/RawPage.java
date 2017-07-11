package enterprises.mccollum.home.media.jsf;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import com.github.amr.mimetypes.MimeTypes;


@Named
@RequestScoped
public class RawPage{

	public RawPage(){}
	
	public String getTitle(){
		String filePath = getParam("filePath");
		return filePath.substring(filePath.lastIndexOf("/")+1);
	}
	
	public String getMimeType(){
		String filePath = getParam("filePath");
		String extension = filePath.substring(filePath.lastIndexOf(".")+1);
		return MimeTypes.getInstance().getByExtension(extension).getMimeType();
	}
	
	public String getUrl(){
//		try {
			return "http://localhost:8080/media/api/raw/"+getParam("source")+"/"+getParam("filePath");
			/*return URLEncoder.encode("http://localhost:8080/media/api/raw/"+getParam("source")+"/"+getParam("filePath"), "UTF-8") ;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "null";//*/
	}
	
	private String getParam(String param){
		return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(param); /*.getSession(true)).getAttribute(param);*/
	}
}
