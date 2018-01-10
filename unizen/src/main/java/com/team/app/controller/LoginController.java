package com.team.app.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.team.app.domain.AdminUser;
import com.team.app.domain.TblUserInfo;
import com.team.app.logger.AtLogger;
import com.team.app.service.ConsumerInstrumentService;
import com.team.app.service.LoginService;

@Controller
public class LoginController {
	private static final AtLogger logger = AtLogger.getLogger(LoginController.class);
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private ConsumerInstrumentService consumerInstrumentServiceImpl;
		
	
	@RequestMapping(value= {"/"})
	public String defaultURL(){
		return "index";
	}
	
	@RequestMapping(value= {"/onSubmitlogin"}, method=RequestMethod.POST)
	public ModelAndView loginUser(HttpServletRequest request, HttpSession session, HttpServletResponse response,RedirectAttributes redirectAttributes) throws Exception{
		logger.debug("in /onSubmitlogin");
		String username = request.getParameter("uname") == null ? "" : request
				.getParameter("uname");
		String password = request.getParameter("pass") == null ? "" : request
				.getParameter("pass");
		
        AdminUser adminUser=null;
        boolean needToChangePwd=false;
		
        if (username.equalsIgnoreCase("") || password.equalsIgnoreCase("")) {
        	redirectAttributes.addFlashAttribute("status",
					"<div class='failure'>Enter User Name/Password!!</div");
			return new ModelAndView("redirect:/");
		} else {			
			adminUser=loginService.getLoginUser(username,password);
		}
        
        if(adminUser!=null){
        	session.setAttribute("adminUser", adminUser);
        	         	
        }
        
        
        if (adminUser!=null) {
			if (adminUser.getPwdChangedDate()== null || adminUser.getPwdChangedDate().equals("")) {
				needToChangePwd = true;
			}
		}
        
        if (needToChangePwd) {
			session.setAttribute("username", username);
			session.setAttribute("password", password);
				return new ModelAndView("redirect:/changePasswordReq");
			
		} 
        
        if(adminUser!=null){
        	 	return new ModelAndView("redirect:/adminHome");
        	
        }else{
        	session.setAttribute("adminUser", "");
        	redirectAttributes.addFlashAttribute("status","<div class='failure'>Invalid User Name/Password !</div");
        	return new ModelAndView("redirect:/");
        }
		
	
	}
	
	 @RequestMapping(value= {"/adminHome"}, method=RequestMethod.GET)
	 public String home(Map<String,Object> map) throws Exception{
		List<TblUserInfo> userInfos= consumerInstrumentServiceImpl.getUserInfosCount();
		  map.put("userInfos",userInfos);
				 return "AdminView";
	    	
	}
	
	@RequestMapping(value= {"/inValid"})
	public String inValidCredentials(){
		return "index";
	}
	
	@RequestMapping(value= {"/forgotPassword"})
	public String forgetPasswordHandler(){
		return "forgotPassword";
	}
	
		 
		
		 
	@RequestMapping(value= {"/logout"})
			public String goToLogout(HttpServletRequest request,HttpServletResponse response,Map<String,Object> map){
				logger.debug("In gotoLogout Page......");
					HttpSession session = request.getSession(true);
						session.invalidate();
							response.setHeader("Cache-Control",	"no-cache, no-store, must-revalidate");
								response.setHeader("Pragma", "co-cache");
									response.setDateHeader("Expires", 0);
										return "redirect:/";
			}
		 	
		 	
		 	
				

			
}
