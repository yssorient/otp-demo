package com.wjs.otpdemo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.wjs.dao.UserDao;
import com.wjs.dao.UserVo;
import com.wjs.util.TotpUtil;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String userName = request.getParameter("username");
		String otp = request.getParameter("otp");

		System.out.println("userName:" + userName + ",otp:" + otp);
		response.setCharacterEncoding("UTF-8");   
		response.setContentType("text/html;charset=UTF-8");
		UserDao dao = new UserDao();
		UserVo vo = dao.getUserByName(userName);
		if (null == vo) {
			response.getWriter().println("�û������ڣ�userName:" + userName);
			return;
		}
		String secretBase32 = vo.getOtpSk();

		if (StringUtils.isNotBlank(secretBase32)) {
			if (!TotpUtil.verify(secretBase32, otp)) {
				response.getWriter().println("�����ȷ��otp_code:" + otp);
				return;
			} else {
				response.getWriter().println("<H1>��¼�ɹ����������</H1><br/><span>СС��Դ������һ���������껪������</span><br/><image src='https://oss.aliyuncs.com/aliyun_id_photo_bucket/account-console-aliyun-com/suyin58_gmail_com149720850051248671.jpeg'></image>");
				return;
			}
		}
	}

}
