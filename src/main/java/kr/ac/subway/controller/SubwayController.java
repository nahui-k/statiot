package kr.ac.subway.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.ac.subway.model.Sounds;
import kr.ac.subway.model.TempAndHumid;
import kr.ac.subway.model.UltraSonic;
import kr.ac.subway.service.SubwayService;

@Controller
public class SubwayController {

	private String temperature = "";
	private String humidity = "";
	private String ultrasonic = "";
	private String code="";
	
	private SubwayService service;

	@Autowired
	public void setSubwayService(SubwayService service) {
		this.service = service;
	}

	//�Ƶ��̳뿡�� ���� �½��� �����͸� �����ϴ� �޼ҵ� 
	@RequestMapping(value = "/tempHumid", method = RequestMethod.POST)
	public void Fetch_Temperate(HttpServletRequest request, HttpServletResponse response)			// throws
																									// ServletException,
																									// IOException
		{
		Date dateup = new Date();
		String date = dateup.toString();
		temperature = request.getParameter("temperature").toString();
		humidity = request.getParameter("humidity").toString();
		code=request.getParameter("code").toString();	//����ö �� �ڵ尪 -> �Ƶ��̳뿡 �߰��ؾ��� ex) 409 = ����� �ΰ�ó�� 
		
		//�׽�Ʈ������ �ܼ�â�� ����ִ� ����
		System.out.println("�µ� ���� �׽�Ʈ");
		System.out.println("��¥ : "+date);
		System.out.println("�µ� : "+temperature);
		System.out.println("���� : "+humidity);
		System.out.println("code :"+code);
		
		//��ü ������ db�� ����
		TempAndHumid tempAndHumid=new TempAndHumid(date,temperature,humidity,code);
		service.addTempAndHumid(tempAndHumid);
		
	}
	
	//�Ƶ��̳뿡�� ���� ���� �ܿ��� �����͸� �����ϴ� �޼ҵ� 
	@RequestMapping(value = "/ultraSonic", method = RequestMethod.GET)
	public void Fetch_UltraSonic(HttpServletRequest request, HttpServletResponse response)
	
	{
		Date dateup = new Date();
		String date = dateup.toString();
		
		//������ ���� �ΰ��� �Ҳ��� �ϳ��� ���ڿ� �ٸ��ϳ��� ���ڿ����� sex���� �ȿ� male , female �־ ����, ���� ȭ��� ����
		String sex=request.getParameter("sex").toString();
		ultrasonic = request.getParameter("ultrasonic").toString();
		code=request.getParameter("code").toString();	//����ö �� �ڵ尪 -> �Ƶ��̳뿡 �߰��ؾ��� ex) 409 = ����� �ΰ�ó�� 
		
		//�׽�Ʈ������ �ܼ�â�� ����ִ� ����
		System.out.println("��,�� ȭ��� �ܿ��� �׽�Ʈ");
		System.out.println("��¥ : "+date);
		System.out.println("���� : "+sex);
		System.out.println("����ġ : "+ultrasonic);		//���� % ȭ �Ұ�
		System.out.println("code :"+code);
		
		//��ü ������ db�� ����
		UltraSonic ultraSonic=new UltraSonic(date,sex,ultrasonic,code);
		service.addUltraSonic(ultraSonic);
	}
	
	//�Ҹ� ���� �����Ͱ��� �����ϴ� �޼ҵ�
	@RequestMapping(value = "/sounds", method = RequestMethod.GET)
	public void Fetch_Sounds(HttpServletRequest request, HttpServletResponse response) {
		
		Date dateup = new Date();
		String date = dateup.toString();
		
		String place = request.getParameter("place").toString();
		int dB=Integer.parseInt(request.getParameter("dB"));
		code=request.getParameter("code").toString();
		
		//�׽�Ʈ������ �ܼ�â�� ����ִ� ����
		System.out.println("��Һ� �Ҹ����� �׽�Ʈ");
		System.out.println("��¥ : "+date);
		System.out.println("��ġ : "+place);
		System.out.println("dB�� : "+dB);		//���� %ȭ �Ұ�
		System.out.println("code :"+code);
		
		//��ü ������ db�� ����
		Sounds sounds =new Sounds(date,place,dB,code);
		service.addSounds(sounds);
	}

	//�׽�Ʈ������ �ּ� �Ű� ��������
	/*public void printer(Model model) {
		Date dateup = new Date();
		String date = dateup.toString();
		
		Sounds sounds = new Sounds(date, men_rest_room_sound, women_rest_room_sound, machine_room_sound, substation_sound, code)
		if (!(temperature=="") && !(humidity=="") && !(ultrasonic=="")
				&& !(men_rest_room_sound=="") && !(women_rest_room_sound=="") && !(machine_room_sound=="") && !(substation_sound=="")) {
			 Subway subway = new Subway(date, temperature, humidity, ultrasonic, men_rest_room_sound, women_rest_room_sound, machine_room_sound, substation_sound);
			 
			 service.addSounds(sounds);
			
			 
			 //request.setAttribute("subwayInfo" , subwayInfo);
			
		}
		System.out.println("���� �µ� : " + temperature);
		System.out.println("���� ���� : " + humidity);
		System.out.println("���� �ð� : " + date);
		System.out.println("���� ������ : " + ultrasonic);
		System.out.println("���� ���� ȭ��� �Ҹ�: " + men_rest_room_sound);//����
		System.out.println("���� ���� ȭ��� �Ҹ�: " + women_rest_room_sound);//����
		System.out.println("���� ���� �Ҹ�: " + machine_room_sound);//����
		System.out.println("���� ������ �Ҹ�: " + substation_sound);//����
		
	}*/

	
	
	@RequestMapping("/subway")		//Ŭ���̾�Ʈ ��û�� �޾� ����ö ���������͸� DB�κ��� ������ ������
									//Front-End �κп��� ���� �������־����!
	public String SubwayMangement(Model model)
	{
		
		//�µ� ������ �ֽ� 5���� �׷��� �׸� �� �ֵ����Ѵ�.
		 List<TempAndHumid> tempAndHumidList = service.getTempAndHumid();
		 for(int i=0;i<5;i++){
			 model.addAttribute("tempAndHumid"+i, tempAndHumidList.get(i));
		 }
		 
		 
		 List<Sounds> soundList = service.getSounds();
		 //model.addAttribute("soundList", soundList.get(0));
		 for(int i=0;i<5;i++){
			 //model.addAttribute("soundList"+i, soundList.get(i));
		 }
		 
		 List<UltraSonic> ultraSonicList = service.getUltraSonic();
		 //model.addAttribute("ultraSonic",ultraSonicList.get(0));
		 //�����۾�
		 //String temperature=service.getTemperature();
		
		 return "subway";
	}
	
	@RequestMapping("/survey")//��������κ� �ʿ� x ���� �߰� ����
	public String survey(Model model)
	{
		 return "survey";
	}

}
