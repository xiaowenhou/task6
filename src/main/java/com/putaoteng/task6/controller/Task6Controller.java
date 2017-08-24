package com.putaoteng.task6.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.putaoteng.task6.model.BasicVo;
import com.putaoteng.task6.model.Profession;
import com.putaoteng.task6.model.User;
import com.putaoteng.task6.service.ExcellentStudentDaoService;
import com.putaoteng.task6.service.ProfessionDaoService;
import com.putaoteng.task6.service.UserDaoService;
import com.putaoteng.task6.utils.AutoAuthenticate;
import com.putaoteng.task6.utils.CookieUtils;
import com.putaoteng.task6.utils.Log;
import com.putaoteng.task6.utils.LogLevel;
import com.putaoteng.task6.utils.MD5Encryption;

@Controller
public class Task6Controller {

	@Resource(name = "excellentStudentDaoService")
	private ExcellentStudentDaoService excellentStudentDaoService;
	@Resource(name = "professionDaoService")
	private ProfessionDaoService professionDaoService;
	@Resource(name = "userDaoService")
	private UserDaoService userDaoService;

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String homePage(Model model) {
		List<BasicVo> list = new ArrayList<BasicVo>();

		list = excellentStudentDaoService.findAll();

		model.addAttribute("excellentStudentList", list);

		return "home";
	}

	@ResponseBody
	@RequestMapping(value = "/json", method = RequestMethod.GET)
	public List<BasicVo> jsonPage() {
		List<BasicVo> list = new ArrayList<BasicVo>();

		list = excellentStudentDaoService.findAll();

		return list;
	}
	
	@ResponseBody
	@RequestMapping(value = "/cache", method = RequestMethod.GET)
	public List<BasicVo> cacheThrough() {
		List<BasicVo> list = new ArrayList<BasicVo>();

		String name = "nnnnnnnn";
		
		list = excellentStudentDaoService.findByExcellentStudentName(name);

		return list;
	}

	@RequestMapping(value = "/u/profession", method = RequestMethod.GET)
	public String professionPage(Model model) {
		List<BasicVo> list = new ArrayList<BasicVo>();
		
		list = professionDaoService.findAll();

		model.addAttribute("professionList", list);
		model.addAttribute("listLength", list.size());

		return "profession";
	}

	@RequestMapping(value = "/u/front", method = RequestMethod.GET)
	public String frontPage(Model model) {
		List<BasicVo> list = new ArrayList<BasicVo>();
		list = professionDaoService.findAll();

		Profession profession = null;
		Iterator<BasicVo> iterator = list.iterator();
		while (iterator.hasNext()) {
			profession = (Profession) iterator.next();
			if (!("CSS".equals(profession.getProfession()) || "js".equals(profession.getProfession())))
				iterator.remove();
		}

		model.addAttribute("professionList", list);
		model.addAttribute("listLength", list.size());

		return "front";
	}

	@RequestMapping(value = "/u/test", method = RequestMethod.GET)
	public String tagTest(Model model) {
		Profession profession = (Profession) professionDaoService.findByPK(32L);
		long nowTime = System.currentTimeMillis();

		model.addAttribute("profession", profession);
		model.addAttribute("nowTime", nowTime);

		return "test";
	}

	@RequestMapping(value = "/login/0", method = RequestMethod.POST)
	public String loginPage(HttpServletRequest request, HttpServletResponse response, Model model, String username,
			String password) throws NoSuchAlgorithmException, ServletException, IOException {
		String fullUrl = request.getHeader("Referer");
		request.getSession().setAttribute("path", fullUrl);
		// 根据用户名在数据库中查找数据
		User user = (User) userDaoService.findByUsername(MD5Encryption.EncoderByMd5(username));

		// 如果不为空,再判断输入的密码和数据库中的密码是否相同
		if (user != null) {
			// 如果匹配则判断密码是否相同,相同则跳回到原来的页面,否则就再判断一次
			if (user.getPassword().equals(MD5Encryption.EncoderByMd5(password))) {
				// 更新登录时间
				user.setLoginAt(System.currentTimeMillis());
				userDaoService.update(user);

				// 添加自动认证(完成任务,所以不同的url采用了不同的认证方式,实际使用一种就好)
				int index = fullUrl.lastIndexOf("/");
				if (fullUrl.substring(index).equals("/profession")) {
					// cookie实现方式
					AutoAuthenticate.cookieAuthenticate(response, username, user.getLoginAt() + "");
				} else {
					// Session 实现方式
					AutoAuthenticate.sessionAuthenticate(request);
				}
				return "redirect:" + fullUrl;
			}
			// 提示密码不正确
			request.getSession().setAttribute("info", "密码错误,请重新输入");
			request.getSession().setMaxInactiveInterval(1);
			return "redirect:" + fullUrl;
		}
		// 提示未注册
		request.getSession().setAttribute("info", "账户不存在,请先注册");
		request.getSession().setMaxInactiveInterval(1);
		return "redirect:" + fullUrl;
	}

	@RequestMapping(value = "/u/loginout", method = RequestMethod.GET)
	public String logoutTest(HttpServletRequest request, HttpServletResponse response, Model model) {
		// 删除cookie
		CookieUtils.deleteCookieByName(request, response, "username");
		CookieUtils.deleteCookieByName(request, response, "loginTime");
		CookieUtils.deleteCookieByName(request, response, "encry");

		// 清空session
		HttpSession session = request.getSession();
		session.setAttribute("isLogin", "false");
		session.setMaxInactiveInterval(0);

		return "bye";
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String registrationTest(HttpServletRequest request, HttpServletResponse response, Model model,
			String username, String password) throws NoSuchAlgorithmException, ServletException, IOException {

		String encryUsername = MD5Encryption.EncoderByMd5(username);
		String encryPassword = MD5Encryption.EncoderByMd5(password);
		User userTest = (User) userDaoService.findByUsername(encryUsername);
		if (userTest != null) {
			model.addAttribute("info", "该用户已存在,请重新注册...");
			return "registration";
		}
		User user = new User();
		user.setUserName(encryUsername);
		user.setPassword(encryPassword);
		user.setCreateAt(System.currentTimeMillis());
		user.setLoginAt(System.currentTimeMillis());
		userDaoService.save(user);

		// 注册成功添加自动认证
		AutoAuthenticate.cookieAuthenticate(response, username, user.getLoginAt() + "");
		AutoAuthenticate.sessionAuthenticate(request);

		return "welcome";
	}
}
