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
	static String password = "0000";
	static String[] Ranks={"네이버랭킹","낮은가격순","높은가격순","등록일순","리뷰많은순"};
	
	public static void main(String[] args) throws SQLException, ClassNotFoundException
	 {
		
		String[] Ranks={"네이버랭킹","낮은가격순","높은가격순","등록일순","리뷰많은순"};

        
		try {
				Scanner scan=new Scanner(System.in);
	            while(true)
	            {
	            	System.out.print("원하는 기능을 선택하세요.(1. 메뉴 검색 2. 레시피 검색 3. 재료 검색 및 실시간 가격 비교 4. 장바구니 보기 5. 종료 ) : ");
		            int choice = scan.nextInt();
		            if(choice==5) break;
		            else if(choice == 1)
		            {
		            	//메뉴 검색
		            	look_menu();
		            }
		            else if(choice == 2)
		            {
		            	//레시피 검색
		            	recipe_look();
		            }
		            else if(choice == 3)
		            {
		            	//재료 보기 및 보관함 담기
		            	ingredient_stack();
		            	
		            }
		            else if(choice == 4)
		            {
		            	//보관함 보기
		            	view_locker();
		            }
		            
	            }
				
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
        System.out.print("찾을 음식 유형을 선택해주세요.\n(한식/중국/일식/양식/퓨전/동남아시아/서양/이탈리아):");
        String rcode=scan.nextLine();
        System.out.println();
        System.out.print("찾을 음식 조건을 선택해주세요.\n(구이/국/그라탕/리조또/나물/생채/샐러드/도시락/간식/떡/한과/만두/면류/밑반찬/김치/밥/볶음/부침/빵/과자/샌드위치/햄버거/양념장/양식/음료/조림/찌개/전골/스튜/찜/튀김/커틀릿/피자):");
        String rcode2=scan.nextLine();
        String querys= "select * from menu order by code desc";
        rs = st.executeQuery(querys);
        boolean flag=false;
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
           if(type_name.contains(rcode) && type_menu.contains(rcode2))
            {
            	System.out.printf("메뉴: %s, 조리 시간: %s, 분량: %s, 난이도: %s\n간략 소개: %s\n대표 이미지 URL: %s\n\n",mname,mtime,mvolume ,mlevel,short_intro,imgURL);
            	flag=true;
            }
            //
        }
        if(!flag)
        {
        	System.out.println("조건에 맞는 음식이 존재하지 않습니다.");
        }
    }
	
	public static void recipe_look() throws SQLException
	{        
        Connection conn;
        Statement st;
        ResultSet rs;
        ResultSet rss = null;
        conn = DriverManager.getConnection(url, user, password);
		st = conn.createStatement();
		
		System.out.print("레시피를 찾을 음식을 입력해주세요:");
        Scanner scan =new Scanner(System.in);
        String food = scan.nextLine();
        
        String querys= "select code,mname from menu order by code asc;";
        PreparedStatement psts=conn.prepareStatement(querys);
        psts=conn.prepareStatement(querys);
        rss=psts.executeQuery();
        rs=st.executeQuery(querys);
        String code="";
        String mname="";
        String ck="";
        boolean flag=false;
        while(rss.next())
        {
        	
        	code=rss.getString("code");
            mname=rss.getString("mname");
            if(mname.contains(food))
            {
            	ck=code;
            	flag=true;
            }
        }
        if(!flag)
        {
        	System.out.println("입력하신 메뉴는 존재하지 않습니다.");
        }
        else
        {
            querys= "select * from recipe where code = ";
        	querys = querys + ck + " ORDER BY corder asc;";
        	//System.out.println(querys);
        	PreparedStatement pst=conn.prepareStatement(querys);
        	pst=conn.prepareStatement(querys);
        	rs=pst.executeQuery();
        	
        	System.out.println("레시피 과정");
	        while (rs.next()) { 
	               String corder = rs.getString("corder");
	               String intro = rs.getString("intro");
	               String imgURL = rs.getString("imgURL");
	               String tip = rs.getString("tip");
	               if(tip.equals(" ") && imgURL.equals(" "))
	               {
	            	   System.out.printf("%s. 설명: %s\n",corder,intro);
	               }
	               else if(tip.equals(" "))
	               {
	            	   System.out.printf("%s. 설명: %s\n레시피 사진 URL: %s\n",corder,intro,imgURL);
	               }
	               else if(imgURL.equals(" "))
	               {
	            	   System.out.printf("%s. 설명: %s\nTIP: %s\n",corder,intro,tip);
	               }
	               else
	               {
	            	   System.out.printf("%s. 설명: %s\n레시피 사진 URL: %s\nTIP: %s\n",corder,intro,imgURL,tip);  
	               }
	               
	               //System.out.printf("레시피 코드: %s, 레시피 순서: %s, 레시피 설명: %s, 레시피 사진: %s, 설명 tip: %s\n",code,corder,intro,imgURL,tip);
	 	     	} 
        }
        
        System.out.println();
		
		
	}
	
	public static void ingredient_stack() throws IOException, SQLException
	{
		Connection conn;
	    Statement st;
	    ResultSet rs;
	    String ck=null;
	    conn = DriverManager.getConnection(url, user, password);
	    st = conn.createStatement();
	    
	    System.out.print("필요한 재료를 찾을 메뉴를 입력해주세요:");
        Scanner scan =new Scanner(System.in);
        String food = scan.nextLine();
        String mname=null;
        String querys= "select code,mname from menu;";
        
        PreparedStatement psts=conn.prepareStatement(querys);
        psts=conn.prepareStatement(querys);
        rs=psts.executeQuery();
        boolean flag=false;
        while (rs.next()) {
     	   String name = rs.getString("mname");
     	   String rcode = rs.getString("code");
     	   if(name.contains(food))
     	   {
     		   mname=name;
     		   ck=rcode;
     		   flag=true;
     	   }
        }
        
        if(!flag)
        {
        	System.out.println("입력하신 메뉴는 존재하지 않습니다.");
        }
        else {
        	querys = "select iname,volume from ingredient where code=\'";
            querys = querys + ck + "\';";
       		rs=st.executeQuery(querys);
       		Stack <String> inameStack= new Stack<String>();
       		Stack <String> VolumeStack= new Stack<String>();
       		System.out.printf("%s에 필요한 재료 list\n",mname);
       		
       		while (rs.next()) {	
   				String iname = rs.getString("iname");
   				String volume = rs.getString("volume");
   				System.out.printf("재료명: %s, 재료 용량: %s\n",iname,volume);
   				inameStack.push(iname);
   				VolumeStack.push(volume);
   			}
       		
       		
       		System.out.println("\n필요한 재료들의 실시간 가격 비교를 진행하겠습니다.");
        	System.out.print("비교 기준을 입력하세요\n1.네이버 랭킹 2. 낮은 가격순 3. 높은 가격순 4. 등록일 순 5. 리뷰 많은 순:");
        	int rank=scan.nextInt();
        	Stack <Locker> Stored=new Stack <Locker>();
        	while(!inameStack.empty())
    		{
    			String iname=inameStack.pop();
    			String volume=VolumeStack.pop();
    			WebResult ingredient=Search(iname,rank-1);
    			System.out.print("- 재료명 : "+ ingredient.iname+", 상품명 : "+ingredient.name+", 가격 : "+ingredient.price+"원 \n상품 페이지 URL : "+ingredient.URL+"\n\n");
    			int tcode=Integer.parseInt(ck);
    			Locker temp = new Locker(tcode,iname,volume,ingredient.price,rank,ingredient.URL, ingredient.name);
    			Stored.push(temp);
    		}
        	scan.nextLine();
    		System.out.print("1.전체 재료 담기 2. 일부 재료 담기 3. 이전 화면 돌아가기 : ");
            int check =scan.nextInt();
            if(check==1)
            {
            	store_locker(Stored);
            }
            else if(check==2)
            {
            	//선택한 메뉴만 담을 스택
            	Stack <Locker> Subset_Stored=new Stack <Locker>();
            	while(!Stored.empty())
            	{
            		Locker temp=Stored.pop();
            		System.out.print("재료명 : "+ temp.Iname+"("+temp.Price+" 원)을 장바구니에 담으시겠습니까?(1.네 2.아니오) : ");
            		int Sub_Check=scan.nextInt();
            		if(Sub_Check==1)
            		{
            			Subset_Stored.push(temp);
            		}
            		
            	}
            	store_locker(Subset_Stored);
            	
            }
        }
        }
        
   		
   		
   		
//////////////////////////////실시간 가격 검색 비교//////////////////////////////////////////////////////
		
/////////////////////////////////실시간 가격 및 상품명 가져오기 ingredient(검색할 재료, 비교기준)//////////////////////////////////////				
		
	
	public static void store_locker(Stack<Locker> result)
	{
		try {
			Connection conn;
			Statement st;
			ResultSet rs;
			conn = DriverManager.getConnection(url, user, password);
			st = conn.createStatement();
	    
			while(!result.empty())
			{
				Locker temp = result.pop();
				/*
				String querys="insert into locker(code,iname,volume,price,prank,purl,product) \r\n"
						+ "values(\'"+temp.Code+"\',\'"+temp.Iname+"\',\'"+temp.Volume+"\',\'"+temp.Price+"\',\'"+temp.Prank+"\',\'"+temp.Purl+"\',\'"+temp.Product+"\')\r\n"
						+ "on conflict (iname,code)\r\n"
						+ "do update\r\n"
						+ "set code=\'"+temp.Code+"\',\r\n"
						+ "iname=\'"+temp.Iname+"\',\r\n"
						+ "volume=\'"+temp.Volume+"\',\r\n"
						+ "price=\'" + temp.Price+"\',\r\n"
						+ "prank=\'"+temp.Prank+"\',\r\n"
						+ "purl=\'"+temp.Purl+"\',\r\n"
						+ "product=\'"+temp.Product+"\'\r\n"
						+ "where locker.prank<>excluded.prank and locker.code=excluded.code and locker.iname=excluded.iname;"; //Locker에 삽입을 실행하는 SQL문
				*/
				String querys="insert into locker(code,iname,volume,price,prank,purl,product) \r\n"
						+ "values("+temp.Code+",\'"+temp.Iname+"\',\'"+temp.Volume+"\',"+temp.Price+","+temp.Prank+",\'"+temp.Purl+"\',\'"+temp.Product+"\')\r\n"
						+ "on conflict (iname,code)\r\n"
						+ "do update\r\n"
						+ "set code="+temp.Code+",\r\n"
						+ "iname=\'"+temp.Iname+"\',\r\n"
						+ "volume=\'"+temp.Volume+"\',\r\n"
						+ "price=" + temp.Price+",\r\n"
						+ "prank="+temp.Prank+",\r\n"
						+ "purl=\'"+temp.Purl+"\',\r\n"
						+ "product=\'"+temp.Product+"\'";
						//Locker에 삽입을 실행하는 SQL문
				
				
				//String querys="insert into locker values(\'"+temp.Code+"\',\'"+temp.Iname+"\',\'"+temp.Volume+"\',\'"+temp.Price+"\',\'"+temp.Prank+"\',\'"+temp.Purl+"\',\'"+temp.Product+"\');\r\n";
				//System.out.println(querys);
				st.execute(querys);
			}
		}catch(SQLException e)
		{
			System.out.println("보관함 에러");
		
		}

		System.out.println("장바구니에 재료 추가를 완료했습니다.");
	}
	
	public static void view_locker() throws SQLException {
		Connection conn;
	    Statement st;
	    ResultSet rs;
	    String min="";
	    String minname="";
	    
	    Statement st2;
	    ResultSet rs2;
	    conn = DriverManager.getConnection(url, user, password);
	    System.out.println("현재 장바구니에 담긴 재료 list");
	    
	    st = conn.createStatement();
	    rs = st.executeQuery("select iname, min(price) from locker group by iname");
	    boolean flag=false;
		
	    while(rs.next())
		{
			minname = rs.getString("iname");
			min = rs.getString("min");
			
			st2=conn.createStatement();
			rs2=st2.executeQuery("select distinct(iname),price,product,purl from locker where iname=\'"+minname+"\' and price = "+min+";");

			while(rs2.next())
			{
				String iname = rs2.getString("iname");
				String price = rs2.getString("price");
				String product = rs2.getString("product");
				String purl = rs2.getString("purl");
				System.out.println("재료명 : "+iname+", 가격 : "+price+", 상품명 : "+product+"\n상품 페이지 URL : "+purl+"\n");
				flag=true;
			}
		}
		if(!flag)
		{
			System.out.println("현재 보관함이 비어 있습니다.");
			Scanner scan = new Scanner(System.in);
			System.out.print("한번 더 누르면 이전으로 돌아갑니다.");
			scan.nextLine();
		}
		else if(flag)
		{
			Scanner scan = new Scanner(System.in);
			System.out.print("1. 장바구니 확인하기 2. 재료 삭제 3. 장바구니 비우기 4. 재료 가격 출력 5. 이전 화면 돌아가기 : ");
			int check =scan.nextInt();
			if(check==1) {
				conn = DriverManager.getConnection(url, user, password);
			    st = conn.createStatement();
				String Querys="select iname,price,product,purl from locker";
				rs=st.executeQuery(Querys);
				while(rs.next())
				{
					String iname = rs.getString("iname");
					String price = rs.getString("price");
					String product = rs.getString("product");
					String purl = rs.getString("purl");
					System.out.println("재료명 : "+iname+", 가격 : "+price+", 상품명 : "+product+"\n상품 페이지 URL : "+purl+"\n");
					flag=true;
				}
			}
			else if(check==2)
			{
				Delete_locker();
			}
			else if(check == 3)
			{
				conn = DriverManager.getConnection(url, user, password);
			    st = conn.createStatement();
				String Querys="Delete from locker\r\n"
						+ "where iname in (select iname from locker);";
				st.execute(Querys);
			}
			else if(check == 4)
			{
				rollup_locker();		
			}
		}
	}
	
	
	
	public static void Delete_locker() throws SQLException{
		Connection conn;
	    Statement st;
	    ResultSet rs;
	    System.out.print("삭제할 재료를 입력해주세요:");
        Scanner scan =new Scanner(System.in);
        String food = scan.nextLine();
	    conn = DriverManager.getConnection(url, user, password);
	    st = conn.createStatement();
	    rs = st.executeQuery("select distinct(iname), price, product, purl from locker;");
		while(rs.next())
		{
			try {
					String iname = rs.getString("iname");
					String price = rs.getString("price");
					String product = rs.getString("product");
					String purl = rs.getString("purl");
					if(iname.contains(food))
					{
						String Querys="delete from locker where iname=\'"+iname+"\';";
						st.execute(Querys);
						break;
					}
				}
			catch(SQLException e){
				System.out.println(e);
				}
			
		}
		System.out.println("삭제가 완료되었습니다.");
	}
	
	public static void rollup_locker() throws SQLException
	{
		ResultSet rs = null;
	    ResultSet vs = null;
	    Connection conn = DriverManager.getConnection(url,user,password);
	    System.out.println("재료 가격 출력");
	    String query= "select code,iname, sum(price) from locker group by rollup(code,iname);";
	    PreparedStatement pst=conn.prepareStatement(query);
	    pst=conn.prepareStatement(query);
	    rs=pst.executeQuery();
	    while (rs.next()) {
            String code = rs.getString("code");
            String iname = rs.getString("iname");
            String sum = rs.getString("sum");

            if(code==null && iname==null)
            	System.out.printf("현재 장바구니에 존재하는 재료 가격의 합은 : %s원 입니다.\n",sum);
            else if(code!=null && iname ==null) {
            	String query2 = "select mname from menu where code="+code+";";
            	 PreparedStatement pst2=conn.prepareStatement(query);
                 pst2=conn.prepareStatement(query2);
                 vs=pst2.executeQuery();
                 while(vs.next()) {
                	 String mname = vs.getString("mname");
                	 System.out.printf("장바구니 내 %s 재료 가격의 합은: %s원 입니다.\n",mname, sum);
                 	}
            	}
	    	}
		}
}
