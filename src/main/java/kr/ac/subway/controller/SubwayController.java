package kr.ac.subway.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.ac.subway.model.Subway;
import kr.ac.subway.service.SubwayService;

@Controller
public class SubwayController {

	private String temperature = "";
	private String humidity = "";
	private String ultrasonic = "";
	private String sounds = "";
	private SubwayService service;

	@Autowired
	public void setSubwayService(SubwayService service) {
		this.service = service;
	}

	@RequestMapping("/login")
	public String DoLogin(Model model) {
		return "login";
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String DoTemperate(HttpServletRequest request, HttpServletResponse response, Model model) // throws
																										// ServletException,
																										// //
																										// IOException
	{
		temperature = request.getParameter("field1").toString();
		humidity = request.getParameter("field2").toString();
		// String sounds = request.getParameter("field4").toString();
		System.out.println("�µ� ������ ������");
		printer(model);
		// System.out.println("���� �Ҹ� : " + sounds);

		// if ((!temperature.equals(null)) && (!humidity.equals(null)) &&
		// (!ultrasonic.equals(null))
		// && (!sounds.equals(null))) {
		// Subway subway = new Subway(date, temperature, humidity, ultrasonic,
		// sounds);
		// service.insert(subway);
		//
		// List<Subway> subwayInfo = service.getCurrent();
		// model.addAttribute("subwayInfo", subwayInfo);
		// // request.setAttribute("subwayInfo" , subwayInfo);
		// }
		return "subwayInfo";
		// String page="/WEB-INF/views/subwayInfo.jsp";
		// RequestDispatcher dispatcher = request.getRequestDispatcher(page);
		// dispatcher.forward(request, response);
	}

	@RequestMapping(value = "/ultra", method = RequestMethod.GET)
	public String DoUltraSonic(HttpServletRequest request, HttpServletResponse response, Model model) // throws
	// ServletException, // IOException
	{
		ultrasonic = request.getParameter("field3").toString();
		// String sounds = request.getParameter("field4").toString();

		printer(model);

		// System.out.println("���� �Ҹ� : " + sounds);

		return "subwayInfo";
		// String page="/WEB-INF/views/subwayInfo.jsp";
		// RequestDispatcher dispatcher = request.getRequestDispatcher(page);
		// dispatcher.forward(request, response);
	}

	public void printer(Model model) {
		Date dateup = new Date();
		String date = dateup.toString();

		if (!(temperature=="") && !(humidity=="") && !(ultrasonic=="")
				&& !(sounds=="")) {
			 Subway subway = new Subway(date, temperature, humidity, ultrasonic,
			 sounds);
			 service.insert(subway);
			
			 
			 //request.setAttribute("subwayInfo" , subwayInfo);
			
		}
		System.out.println("���� �µ� : " + temperature);
		System.out.println("���� ���� : " + humidity);
		System.out.println("���� �ð� : " + date);
		System.out.println("���� ������ : " + ultrasonic);
		System.out.println("���� �Ҹ�: " + sounds);
		
	}

	@RequestMapping(value = "/sounds", method = RequestMethod.GET)
	public String DoSounds(HttpServletRequest request, HttpServletResponse response, Model model) {
		sounds = request.getParameter("field4").toString();

		printer(model);

		return "subwayInfo";
	}
	
	@RequestMapping("/subway")
	public String DoMangement(Model model)
	{
		List<Subway> subwayInfo = service.getCurrent();
		 System.out.println("����");
		 model.addAttribute("subwayInfo", subwayInfo);
		 
		 return "subway";
	}

}
