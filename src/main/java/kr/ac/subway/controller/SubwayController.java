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

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class SubwayController {

	private String temperature = "";
	private String humidity = "";
	private String ultrasonic = "";
	private String code="";
	
	private SubwayService service;
	private String url = "https://fcm.googleapis.com/fcm/send"; 
	private String message = "\"������\"}";//"\"������\"}"
	//String parameters = "{"+ "\"data\": {"+ "\"message\": \"test\""+"},"+ "\"to\": \"/topics/notice\""+"}"; 
	private String parameters = "{\"data\": {"+"\"message\":"+message+",\"to\":\"/topics/notice\"}";
	@Autowired
	public void setSubwayService(SubwayService service) {
		this.service = service;
	}

	
	
	//�Ƶ��̳뿡�� ���� �½��� �����͸� �����ϴ� �޼ҵ� 
	@RequestMapping(value = "/tempHumid", method = RequestMethod.GET
			/*headers="content-type=application/x-www-form-urlencoded"*/)
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
		
		
		if(dB>=100)
		{//dB�� 100���� ū ���
			if(place.equals("toilet"))
			{
				message = "\"ȭ���\"}";
			}
			else if(place.equals("machine"))
			{
				message = "\"����\"}";
			}
			//��Ұ� toilet���� machine���� Ȯ�� �ϱ�
			try {
				String result = sendPost(url,parameters);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//�׽�Ʈ������ �ܼ�â�� ����ִ� ����
			System.out.println("��Һ� �Ҹ����� �׽�Ʈ");
			System.out.println("��¥ : "+date);
			System.out.println("��ġ : "+place);
			System.out.println("dB�� : "+dB);		//���� %ȭ �Ұ�
			System.out.println("code :"+code);
		
		}
		
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
		 
		 for(int i=0;i<10;i++){
			 if(ultraSonicList.get(i).getSex().equals("male"))
			 {
				 model.addAttribute("ultraSonic_male", Integer.parseInt(ultraSonicList.get(i).getUltraSonic()));
			 }
			 else{
				 model.addAttribute("ultraSonic_female", Integer.parseInt(ultraSonicList.get(i).getUltraSonic()));
			 }
			 //model.addAttribute("soundList"+i, soundList.get(i));
		 }
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
	
	public String sendPost(String url, String parameters) throws Exception { 
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager(){
                public X509Certificate[] getAcceptedIssuers(){return new X509Certificate[0];}
                public void checkClientTrusted(X509Certificate[] certs, String authType){}
                public void checkServerTrusted(X509Certificate[] certs, String authType){}
        }};
        SSLContext sc = SSLContext.getInstance("TLS");
        sc.init(null, trustAllCerts, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
   
       URL obj = new URL(url);
       HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
   
       //reuqest header
       con.setRequestMethod("POST");
       con.setRequestProperty("Content-Type", "application/json");
       con.setRequestProperty("Authorization", "key= AAAA6Nz5EqM:APA91bFC3h1KtQeFu3KV2cZzWxEPwL0LFF-61lnsZ6bV5WO_VzGx17iG_m1Q1RghZMtuPXKChQYnffRCpNBl8LQxPExr13I0FDHLY28JAyxE7msA6JISoxX_CnEEnSCforfi0BmVlT6c ");
       String urlParameters = parameters;
   
       //post request
       con.setDoOutput(true);
       DataOutputStream wr = new DataOutputStream(con.getOutputStream());
       wr.write(urlParameters.getBytes("UTF-8"));
       wr.flush();
       wr.close();

       int responseCode = con.getResponseCode();     
       System.out.println("Post parameters : " + urlParameters);
       System.out.println("Response Code : " + responseCode);
   
       StringBuffer response = new StringBuffer();
   
       if(responseCode == 200){   
              BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream()));
              String inputLine;
              while ((inputLine = in.readLine()) != null) {
                     response.append(inputLine);
              }
              in.close();   
       }
       //result
       System.out.println(response.toString());
       return response.toString();
}


}
