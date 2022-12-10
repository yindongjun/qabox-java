package yi.master.util.xss;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * XSS过滤
 * @author Mark sunlightcs@gmail.com
 */
public class XssFilter implements Filter {
    public static final List<String> WHITE_LIST = new ArrayList<>();

    static {
        WHITE_LIST.add("");
    }

	@Override
	public void init(FilterConfig config) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        String uri = servletRequest.getServletPath();
        if (StringUtils.isNotBlank(uri) && WHITE_LIST.contains(uri)) {
            chain.doFilter(request, response);
            return;
        }

		XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper(
				(HttpServletRequest) request);
		chain.doFilter(xssRequest, response);
	}

	@Override
	public void destroy() {
	}

}