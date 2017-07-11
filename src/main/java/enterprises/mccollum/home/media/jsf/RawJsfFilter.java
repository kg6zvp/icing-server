package enterprises.mccollum.home.media.jsf;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import enterprises.mccollum.home.media.model.FilePathCodec;

@WebFilter(RawJsfFilter.RAW_URL)
public class RawJsfFilter implements Filter {
	public static final String RAW_URL = "/web/raw";
	
	@Inject
	FilePathCodec pathCodec;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String path = ((HttpServletRequest)request).getRequestURI().substring(((HttpServletRequest)request).getContextPath().length());
		
		String baseUrl = path.substring(0, path.indexOf("/", RAW_URL.length()+1));
		String realPath = path.substring(baseUrl.length()+1);
		//String encodedPath = pathCodec.encodePath(realPath);
		
		request.setAttribute("source", baseUrl.substring(baseUrl.lastIndexOf("/")+1));
		request.setAttribute("filePath", realPath);
		
		HttpServletRequest req = new HttpServletRequestWrapper((HttpServletRequest) request){
			StringBuffer requestURL = new StringBuffer(RAW_URL);
			
			@Override
			public StringBuffer getRequestURL() {
				return requestURL;
			}
		};
		
		chain.doFilter(req, response);
		
		/*try {
			reCtx.setRequestUri(new URI(baseUrl+"/"+encodedPath));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}//*/
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

}
