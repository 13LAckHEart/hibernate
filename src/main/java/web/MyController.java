package web;
import java.util.*;
import org.hibernate.*;
import javax.transaction.*;
import org.springframework.ui.*;
import org.springframework.boot.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;

import javax.servlet.http.*;

@Controller
public class MyController {

	@RequestMapping("/")
	String index() {
		return "index";
	}

	@RequestMapping("/test")
	String test() {
		return "test.jsp";
	}
	
	/*
	@RequestMapping("/list") @ResponseBody
	List showCoffee() {
		Session database = factory.openSession();
		Query query = database.createQuery("from Coffee");
		List list = query.list();
		database.close();
		return list;
	}
	*/
	
	@Autowired
	SessionFactory factory;
	
	@RequestMapping("/login")
	String showLoginPage() {
		return "login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	String checkLogin(HttpSession session, 
			String email, String password) {
		Session database = factory.openSession();
		Query query      = database.createQuery(
			"from User where email = :e and password = sha2(:p, 512)");
		query.setParameter("e", email);
		query.setParameter("p", password);
		List list = query.list();
		if (list.size() == 0) {
			return "redirect:/login";
		} else {
			return "redirect:/profile";
		}
	}
}
