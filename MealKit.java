import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.sql.PreparedStatement;
import java.util.Stack;

public class MealKit {
	static String url = "jdbc:postgresql://127.0.0.1:5432/postgres";
	static String user = "postgres";
	static String password = "5110";
	static String[] Ranks={"네이버랭킹","낮은가격순","높은가격순","등록일순","리뷰많은순"};
	
	public static void main(String[] args) throws SQLException, ClassNotFoundException
	 {
		
		String[] Ranks={"네이버랭킹","낮은가격순","높은가격순","등록일순","리뷰많은순"};

        
		try {
//////////////////////////////////////////1. 선택 가능한 메뉴 나열////////////////////////////////////////////////////////
	            look_menu();
/////////////선택한 유형 : rcode , 선택한 유형 분류 : rcode2///////////////////////////////////////
///////////////////////////////1.선택 가능한 메뉴 나열 종료///////////////////////////////////////
	            
///////////////////////////////2. 선택한 메뉴 레시피 및 재료 정보///////////////////////////////////
	            String code =recipe_look();
/////////////////////////////code : 레시피 코드 , food : 요리명
////////////////////////////////2. 선택한 메뉴 레시피 및 재료 정보 종료////////////////////////////////////
	            
////////////////////////////////3. 레시피 재료명 , 재료양 출력///////////////////////////////////////////
	            Stack <Locker> result = ingredient_stack(code);
////////////////////////////////3. 레시피 재료 정보 출력 및 저장 종료///////////////////////////////////////
	            
///////////////////////////////////4. 보관함 추가//////////////////////////////////////////////////////
	     
////////////////////////////////////////////////////////////////////////////////////////////////////	         
				
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
				

	 }
	
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
//		함수 구현 하는 부분
//
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// name: 검색 대상 ,rank : 비교기준, 장보기 카테고리에서 선택
	//rank 0: 네이버 랭킹순 / 1: 낮은 가격순 / 2:높은 가격순 / 3:등록일순 / 4 :리뷰 많은 순
	public static WebResult Search(String name,int rank) throws IOException {
		
		//rank 0: 네이버 랭킹순 / 1: 낮은 가격순 / 2:높은 가격순 / 3:등록일순 / 4 :리뷰 많은 순
		String URL=""; // 검색 페이지
		String PURL=""; //상품 페이지
		String price=""; // 상품 가격
		String pname=""; // 상품 명칭
		
		if(rank==0)
		{
			//네이버 랭킹 첫번째
			URL="https://search.shopping.naver.com/search/all?frm=NVSHATC&productSet=grocery&query="+name;
			Document doc = Jsoup.connect(URL).get();
			Elements contents = doc.select("#__next > div > div.style_container__1YjHN > div.style_inner__18zZX > div.style_content_wrap__1PzEo > div.style_content__2T20F > ul > div > div:nth-child(1) > li > div > div.basicList_info_area__17Xyo > div.basicList_price_area__1UXXR > strong > span.basicList_num__1yXM9");
			Elements product = doc.select("#__next > div > div.style_container__1YjHN > div.style_inner__18zZX > div.style_content_wrap__1PzEo > div.style_content__2T20F > ul > div > div:nth-child(1) > li > div > div.basicList_info_area__17Xyo > div.basicList_title__3P9Q7 > a");
			price = contents.text();
			pname = product.text();
			PURL=product.attr("href");
			
		}
		else if(rank==1)
		{
			//최소 가격 0 낮은 가격순 첫번째
			URL="https://search.shopping.naver.com/search/all?frm=NVSHPRC&productSet=grocery&sort=price_asc&query="+name;
			Document doc = Jsoup.connect(URL).get();
			Elements contents = doc.select("#__next > div > div.style_container__1YjHN > div.style_inner__18zZX > div.style_content_wrap__1PzEo > div.style_content__2T20F > ul > div > div:nth-child(1) > li > div > div.basicList_info_area__17Xyo > div.basicList_price_area__1UXXR > strong > span.basicList_num__1yXM9");
			Elements product = doc.select("#__next > div > div.style_container__1YjHN > div.style_inner__18zZX > div.style_content_wrap__1PzEo > div.style_content__2T20F > ul > div > div:nth-child(1) > li > div > div.basicList_info_area__17Xyo > div.basicList_title__3P9Q7 > a");
			price = contents.text();
			pname = product.text();
			PURL=product.attr("href");
		}
		else if(rank==2)
		{
			//유의미한 결과를 위해 최고가격 100000원으로 설정 높은 가격순 첫번째
			URL="https://search.shopping.naver.com/search/all?frm=NVSHPRC&productSet=grocery&maxPrice=100000&minPrice=0&sort=price_dsc&query="+name;
			Document doc = Jsoup.connect(URL).get();
			Elements contents = doc.select("#__next > div > div.style_container__1YjHN > div.style_inner__18zZX > div.style_content_wrap__1PzEo > div.style_content__2T20F > ul > div > div:nth-child(1) > li > div > div.basicList_info_area__17Xyo > div.basicList_price_area__1UXXR > strong > span.basicList_num__1yXM9");
			Elements product = doc.select("#__next > div > div.style_container__1YjHN > div.style_inner__18zZX > div.style_content_wrap__1PzEo > div.style_content__2T20F > ul > div > div:nth-child(1) > li > div > div.basicList_info_area__17Xyo > div.basicList_title__3P9Q7 > a");
			price = contents.text();
			pname = product.text();
			PURL=product.attr("href");
			
		}
		else if(rank==3)
		{
			//등록일 순 첫번째 상품
			URL="https://search.shopping.naver.com/search/all?frm=NVSHPRC&productSet=grocery&sort=date&query="+name;
			Document doc = Jsoup.connect(URL).get();
			Elements contents = doc.select("#__next > div > div.style_container__1YjHN > div.style_inner__18zZX > div.style_content_wrap__1PzEo > div.style_content__2T20F > ul > div > div:nth-child(1) > li > div > div.basicList_info_area__17Xyo > div.basicList_price_area__1UXXR > strong > span.basicList_num__1yXM9");
			Elements product = doc.select("#__next > div > div.style_container__1YjHN > div.style_inner__18zZX > div.style_content_wrap__1PzEo > div.style_content__2T20F > ul > div > div:nth-child(1) > li > div > div.basicList_info_area__17Xyo > div.basicList_title__3P9Q7 > a");
			price = contents.text();
			pname = product.text();
			PURL=product.attr("href");
		}
		else if(rank==4)
		{
			//리뷰 많은 순 첫번째 상품
			URL="https://search.shopping.naver.com/search/all?frm=NVSHPRC&productSet=grocery&sort=review&query="+name;
			Document doc = Jsoup.connect(URL).get();
			Elements contents = doc.select("#__next > div > div.style_container__1YjHN > div.style_inner__18zZX > div.style_content_wrap__1PzEo > div.style_content__2T20F > ul > div > div:nth-child(1) > li > div > div.basicList_info_area__17Xyo > div.basicList_price_area__1UXXR > strong > span.basicList_num__1yXM9");
			Elements product = doc.select("#__next > div > div.style_container__1YjHN > div.style_inner__18zZX > div.style_content_wrap__1PzEo > div.style_content__2T20F > ul > div > div:nth-child(1) > li > div > div.basicList_info_area__17Xyo > div.basicList_title__3P9Q7 > a");
			price = contents.text();
			pname = product.text();
			PURL=product.attr("href");
			
			
		}
		else 
		{
			System.out.println("잘못된 입력입니다.");
		}
		int INT_PRICE;
		try {
			price=price.replace("원","");
			price=price.replace(",","");
			INT_PRICE=Integer.parseInt(price);
		}
		catch(NumberFormatException e)
		{
			//장보기 카테고리 없을 시 네이버 랭킹 첫번째로 선택
			URL="https://search.shopping.naver.com/search/all?frm=NVSHATC&query="+name;
			Document doc = Jsoup.connect(URL).get();
			Elements contents = doc.select("#__next > div > div.style_container__1YjHN > div.style_inner__18zZX > div.style_content_wrap__1PzEo > div.style_content__2T20F > ul > div > div:nth-child(1) > li > div > div.basicList_info_area__17Xyo > div.basicList_price_area__1UXXR > strong > span > span");
			Elements product = doc.select("#__next > div > div.style_container__1YjHN > div.style_inner__18zZX > div.style_content_wrap__1PzEo > div.style_content__2T20F > ul > div > div:nth-child(1) > li > div > div.basicList_info_area__17Xyo > div.basicList_title__3P9Q7 > a");
			price = contents.text();
			pname = product.text();
			PURL=product.attr("href");
			
			price=price.replace("원","");
			price=price.replace(",","");
			INT_PRICE=Integer.parseInt(price);
			rank=0;
			
		}
		
		WebResult result = new WebResult(name,PURL,pname,INT_PRICE,rank);
		return result;
	}
	
	public static void look_menu() throws SQLException {
		Connection conn;
        Statement st;
        ResultSet rs;
 
        conn = DriverManager.getConnection(url, user, password);
		st = conn.createStatement();
		
		Scanner scan=new Scanner(System.in);
        System.out.print("유형을 분류해주세요.(한식/중국/일식/양식/퓨전/동남아시아/서양/이탈리아):");
        String rcode=scan.nextLine();
        System.out.println();
        System.out.print("음식을 분류해주세요.(국/나물/튀김/그라탕):");
        String rcode2=scan.nextLine();
        
        rs = st.executeQuery("SELECT * from menu");
        while (rs.next()) {
            String code = rs.getString("code");
            String mname = rs.getString("mname");
            String short_intro = rs.getString("short_intro");
            String type_name = rs.getString("type_name");
            String type_menu = rs.getString("type_menu");
            String mtime  = rs.getString("mtime");
            String mvolume  = rs.getString("mvolume");
            String mlevel = rs.getString("mlevel");
            String imgURL = rs.getString("imgURL");
        //    System.out.printf("레시피코드: %s, 메뉴이름: %s, 간략소개: %s, 메뉴타입: %s, 메뉴이름: %s, 조리시간: %s, 분량: %s, 난이도: %s, 대표이미지: %s\n",code,mname,short_intro,type_name,type_menu,mtime,mvolume ,mlevel,imgURL);                  
            if(type_name.contains(rcode) && type_menu.contains(rcode2))
            	System.out.printf("레시피코드: %s, 메뉴이름: %s, 간략소개: %s, 메뉴타입: %s, 메뉴이름: %s, 조리시간: %s, 분량: %s, 난이도: %s, 대표이미지: %s\n",code,mname,short_intro,type_name,type_menu,mtime,mvolume ,mlevel,imgURL);                
            }
	}
	
	public static String recipe_look() throws SQLException
	{        
        Connection conn;
        Statement st;
        ResultSet rs;
        conn = DriverManager.getConnection(url, user, password);
		st = conn.createStatement();
		
		System.out.print("메뉴를 선택해주세요:");
        Scanner scan =new Scanner(System.in);
        String food = scan.nextLine();
        
        String querys= "select code,mname from menu where mname = \'"+food+"\';";
        System.out.println(querys);
        
        rs=st.executeQuery(querys);
        String code="";
        String mname="";
        while(rs.next())
        {
        	code=rs.getString("code");
            mname=rs.getString("mname");
        }
        
        
        System.out.print("레시피 제공을 원하십니까?(y/n):");
        String check=scan.nextLine();
        
        if(check.equals("y")||check.equals("Y"))
        {
        	querys= "select * from recipe where code = ";
        	querys = querys + code + "ORDER BY corder ASC;";
        	PreparedStatement pst=conn.prepareStatement(querys);
        	pst=conn.prepareStatement(querys);
        	rs=pst.executeQuery();
        	
        	System.out.println("레시피 과정");
	         while (rs.next()) { 
	               String corder = rs.getString("corder");
	               String intro = rs.getString("intro");
	               String imgURL = rs.getString("imgURL");
	               String tip = rs.getString("tip");
	               System.out.printf("레시피 코드: %s, 레시피 순서: %s, 레시피 설명: %s, 레시피 사진: %s, 설명 tip: %s\n",code,corder,intro,imgURL,tip);                  
	 	     	} 
        }
		
		return code;
	}
	
	public static Stack<Locker> ingredient_stack(String recipe_code) throws IOException, SQLException
	{
		Connection conn;
	    Statement st;
	    ResultSet rs;
	    conn = DriverManager.getConnection(url, user, password);
	    st = conn.createStatement();
	   
   		Scanner scan =new Scanner(System.in);
   		System.out.print("필요한 재료 정보 제공을 원하십니까?(y/n):");
        String check=scan.nextLine();
        
   		System.out.println("--------------------필요한 재료 list--------------------");
   		
   		String querys = "select iname,volume from ingredient where code=";
        querys = querys + recipe_code + ";";
   		rs=st.executeQuery(querys);
   		Stack <String> inameStack= new Stack<String>();
   		Stack <String> VolumeStack= new Stack<String>();
   		
   		if(check.equals("y")||check.equals("Y"))
   		{
   			while (rs.next()) {	
   				String iname = rs.getString("iname");
   				String volume = rs.getString("volume");
   				System.out.printf("재료명: %s, 양: %s\n",iname,volume);
   				inameStack.push(iname);
   				VolumeStack.push(volume);
   			}
   		}
   		else
   		{
   			while (rs.next()) {	
   				String iname = rs.getString("iname");
   				String volume = rs.getString("volume");
   				inameStack.push(iname);
   				VolumeStack.push(volume);
   			}
   			
   		}
   		
//////////////////////////////실시간 가격 검색 비교//////////////////////////////////////////////////////
		System.out.println("우리 요리에 필요한 재료들을 비교하겠습니다.");
/////////////////////////////////실시간 가격 및 상품명 가졍고기 ingredient(검색할 재료, 비교기준)//////////////////////////////////////				
		System.out.print("비교기준을 입력하세요 1.네이버랭킹 2. 낮은 가격순 3. 높은 가격순 4. 등록일 순 5. 리뷰많은 순:");
		int rank=scan.nextInt();
		
		System.out.print("필요한 실시간 가격 정보를 보시겠습니까?(y/n):");
		Stack <Locker> Stored=new Stack <Locker>();
		scan.nextLine(); //버퍼 비우기
		check=scan.nextLine();
		if(check.equals("Y")||check.equals("y")){
        		while(!inameStack.empty())
        		{
        			String iname=inameStack.pop();
        			String volume=VolumeStack.pop();
        			WebResult ingredient=Search(iname,rank-1);
        			System.out.print("재료명 : "+ ingredient.iname+"\n상품명 : "+ingredient.name+" \n가격 : "+ingredient.price+"원 \n비교기준 : "+Ranks[ingredient.rank]+"\n상품 페이지 : "+ingredient.URL+"\n");
        			int tcode=Integer.parseInt(recipe_code);
        			Locker temp = new Locker(tcode,iname,volume,ingredient.price,rank,ingredient.URL, ingredient.name);
        			Stored.push(temp);
        		}
        	
        }
        else{
    			while(!inameStack.empty())
    			{
    				String iname=inameStack.pop();
    				String volume=VolumeStack.pop();
    				WebResult ingredient=Search(iname,rank-1);
    				int tcode=Integer.parseInt(recipe_code);
    				Locker temp = new Locker(tcode,iname,volume,ingredient.price,rank,ingredient.URL, ingredient.name);
    				Stored.push(temp);
    			}
        	}
		return Stored; 
	}
}
