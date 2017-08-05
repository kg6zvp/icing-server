package enterprises.mccollum.home.media;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import enterprises.mccollum.home.media.control.RawMediaAccess;

@WebServlet(RawPageServlet.RAW_PAGE_PATH)
public class RawPageServlet extends HttpServlet {
	public static final String RAW_PAGE_PATH = "/web/testRaw/*";

	@Inject
	RawMediaAccess rawMedia;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//super.doGet(req, resp);
		resp.setStatus(200);
		resp.getWriter().print(req.getRequestURI());
	}

	@Override
	protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doHead(req, resp);
	}
}
