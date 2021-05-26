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
import org.postgresql.util.PSQLException;

import java.sql.PreparedStatement;
import java.util.Stack;

public class MealKit {
	static String url = "jdbc:postgresql://127.0.0.1:5432/postgres";
	static String user = "postgres";
	static String password = "5110";
	static String[] Ranks={"���̹���ŷ","�������ݼ�","�������ݼ�","����ϼ�","���丹����"};
	
	public static void main(String[] args) throws SQLException, ClassNotFoundException
	 {
		
		String[] Ranks={"���̹���ŷ","�������ݼ�","�������ݼ�","����ϼ�","���丹����"};

        
		try {
				Scanner scan=new Scanner(System.in);
	            while(true)
	            {
	            	System.out.print("���ϴ� ����� �����ϼ���.(1. �޴� �˻� 2. ������ �˻� 3. ��� �˻� �� �ǽð� ���� �� 4. ��ٱ��� ���� 5. ���� ) : ");
		            int choice = scan.nextInt();
		            if(choice==5) break;
		            else if(choice == 1)
		            {
		            	//�޴� �˻�
		            	look_menu();
		            }
		            else if(choice == 2)
		            {
		            	//������ �˻�
		            	recipe_look();
		            }
		            else if(choice == 3)
		            {
		            	//��� ���� �� ������ ���
		            	ingredient_stack();
		            	
		            }
		            else if(choice == 4)
		            {
		            	//������ ����
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
//		�Լ� ���� �ϴ� �κ�
//
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// name: �˻� ��� ,rank : �񱳱���, �庸�� ī�װ����� ����
	//rank 0: ���̹� ��ŷ�� / 1: ���� ���ݼ� / 2:���� ���ݼ� / 3:����ϼ� / 4 :���� ���� ��
	public static WebResult Search(String name,int rank) throws IOException {
		
		//rank 0: ���̹� ��ŷ�� / 1: ���� ���ݼ� / 2:���� ���ݼ� / 3:����ϼ� / 4 :���� ���� ��
		String URL=""; // �˻� ������
		String PURL=""; //��ǰ ������
		String price=""; // ��ǰ ����
		String pname=""; // ��ǰ ��Ī
		
		if(rank==0)
		{
			//���̹� ��ŷ ù��°
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
			//�ּ� ���� 0 ���� ���ݼ� ù��°
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
			//���ǹ��� ����� ���� �ְ��� 100000������ ���� ���� ���ݼ� ù��°
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
			//����� �� ù��° ��ǰ
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
			//���� ���� �� ù��° ��ǰ
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
			System.out.println("�߸��� �Է��Դϴ�.");
		}
		int INT_PRICE;
		try {
			price=price.replace("��","");
			price=price.replace(",","");
			INT_PRICE=Integer.parseInt(price);
		}
		catch(NumberFormatException e)
		{
			//�庸�� ī�װ� ���� �� ���̹� ��ŷ ù��°�� ����
			URL="https://search.shopping.naver.com/search/all?frm=NVSHATC&query="+name;
			Document doc = Jsoup.connect(URL).get();
			Elements contents = doc.select("#__next > div > div.style_container__1YjHN > div.style_inner__18zZX > div.style_content_wrap__1PzEo > div.style_content__2T20F > ul > div > div:nth-child(1) > li > div > div.basicList_info_area__17Xyo > div.basicList_price_area__1UXXR > strong > span > span");
			Elements product = doc.select("#__next > div > div.style_container__1YjHN > div.style_inner__18zZX > div.style_content_wrap__1PzEo > div.style_content__2T20F > ul > div > div:nth-child(1) > li > div > div.basicList_info_area__17Xyo > div.basicList_title__3P9Q7 > a");
			price = contents.text();
			pname = product.text();
			PURL=product.attr("href");
			
			price=price.replace("��","");
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
        System.out.print("ã�� ���� ������ �������ּ���.\n(�ѽ�/�߱�/�Ͻ�/���/ǻ��/�����ƽþ�/����/��Ż����):");
        String rcode=scan.nextLine();
        System.out.println();
        System.out.print("ã�� ���� ������ �������ּ���.\n(����/��/�׶���/������/����/��ä/������/���ö�/����/��/�Ѱ�/����/���/�ع���/��ġ/��/����/��ħ/��/����/������ġ/�ܹ���/�����/���/����/����/�/����/��Ʃ/��/Ƣ��/ĿƲ��/����):");
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
            	System.out.printf("�޴�: %s, ���� �ð�: %s, �з�: %s, ���̵�: %s\n���� �Ұ�: %s\n��ǥ �̹��� URL: %s\n\n",mname,mtime,mvolume ,mlevel,short_intro,imgURL);
            	flag=true;
            }
            //
        }
        if(!flag)
        {
        	System.out.println("���ǿ� �´� ������ �������� �ʽ��ϴ�.");
        }
    }
	
	public static void recipe_look() throws SQLException
	{        
        Connection conn;
        Statement st;
        ResultSet rs = null;
        conn = DriverManager.getConnection(url, user, password);
		st = conn.createStatement();
		
		System.out.print("�����Ǹ� ã�� ������ �Է����ּ���:");
        Scanner scan =new Scanner(System.in);
        String food = scan.nextLine();
        boolean flag=false;
        String querys= "select code from menu where mname=\'"+food+"\';";
        rs=st.executeQuery(querys);
        String code="";
        String mname="";
        String ck="";
        while(rs.next())
        {
        	code=rs.getString("code");
            ck=code;
            flag=true;
        }
        if(!flag)
        {
        	System.out.println("�Է��Ͻ� �޴��� �������� �ʽ��ϴ�.\n(Ȥ�� �޴��� ��Ȯ�� �Է����ּ���.)");
        }
        else
        {
        	
        	
        	
            querys= "select * from recipe where code = ";
        	querys = querys + ck + " ORDER BY corder asc;";
        	//System.out.println(querys);
        	PreparedStatement pst=conn.prepareStatement(querys);
        	pst=conn.prepareStatement(querys);
        	rs=pst.executeQuery();
        	
        	System.out.println("������ ����");
	        while (rs.next()) { 
	               String corder = rs.getString("corder");
	               String intro = rs.getString("intro");
	               String imgURL = rs.getString("imgURL");
	               String tip = rs.getString("tip");
	               if(tip.equals(" ") && imgURL.equals(" "))
	               {
	            	   System.out.printf("%s. ����: %s\n",corder,intro);
	               }
	               else if(tip.equals(" "))
	               {
	            	   System.out.printf("%s. ����: %s\n������ ���� URL: %s\n",corder,intro,imgURL);
	               }
	               else if(imgURL.equals(" "))
	               {
	            	   System.out.printf("%s. ����: %s\nTIP: %s\n",corder,intro,tip);
	               }
	               else
	               {
	            	   System.out.printf("%s. ����: %s\n������ ���� URL: %s\nTIP: %s\n",corder,intro,imgURL,tip);  
	               }
	               
	               //System.out.printf("������ �ڵ�: %s, ������ ����: %s, ������ ����: %s, ������ ����: %s, ���� tip: %s\n",code,corder,intro,imgURL,tip);
	 	     	} 

      
        
        }
        
        System.out.println();
		
		
	}
	
	public static void ingredient_stack() throws IOException, SQLException
	{
		Connection conn;
	    Statement st;
	    ResultSet rs;
	    conn = DriverManager.getConnection(url, user, password);
	    st = conn.createStatement();
	    
	    System.out.print("�ʿ��� ��Ḧ ã�� �޴��� �Է����ּ���:");
        Scanner scan =new Scanner(System.in);
        String food = scan.nextLine();
        String querys= "select code from menu where mname=\'"+food+"\';";
        String code="";
        rs=st.executeQuery(querys);
        boolean flag=false;
        
        while(rs.next())
        {
        	code=rs.getString("code");
            flag=true;
        }
        
        if(!flag)
        {
        	System.out.println("�Է��Ͻ� �޴��� �������� �ʽ��ϴ�.\n(Ȥ�� �޴��� ��Ȯ�� �Է����ּ���.)");
        }
        else {
        	querys = "select iname,volume from ingredient where code=\'";
            querys = querys + code + "\';";
       		rs=st.executeQuery(querys);
       		Stack <String> inameStack= new Stack<String>();
       		Stack <String> VolumeStack= new Stack<String>();
       		System.out.printf("%s�� �ʿ��� ��� list\n",food);
       		
       		while (rs.next()) {	
   				String iname = rs.getString("iname");
   				String volume = rs.getString("volume");
   				System.out.printf("����: %s, ��� �뷮: %s\n",iname,volume);
   				inameStack.push(iname);
   				VolumeStack.push(volume);
   			}
       		
       		
       		System.out.println("\n�ʿ��� ������ �ǽð� ���� �񱳸� �����ϰڽ��ϴ�.");
        	System.out.print("�� ������ �Է��ϼ���\n1.���̹� ��ŷ 2. ���� ���ݼ� 3. ���� ���ݼ� 4. ����� �� 5. ���� ���� ��:");
        	int rank=scan.nextInt();
        	Stack <Locker> Stored=new Stack <Locker>();
        	while(!inameStack.empty())
    		{
    			String iname=inameStack.pop();
    			String volume=VolumeStack.pop();
    			WebResult ingredient=Search(iname,rank-1);
    			System.out.print("- ���� : "+ ingredient.iname+", ��ǰ�� : "+ingredient.name+", ���� : "+ingredient.price+"�� \n��ǰ ������ URL : "+ingredient.URL+"\n\n");
    			int tcode=Integer.parseInt(code);
    			Locker temp = new Locker(tcode,iname,volume,ingredient.price,rank,ingredient.URL, ingredient.name);
    			Stored.push(temp);
    		}
        	scan.nextLine();
    		System.out.print("1.��ü ��� ��� 2. �Ϻ� ��� ��� 3. ���� ȭ�� ���ư��� : ");
            int check =scan.nextInt();
            if(check==1)
            {
            	store_locker(Stored);
            }
            else if(check==2)
            {
            	//������ �޴��� ���� ����
            	Stack <Locker> Subset_Stored=new Stack <Locker>();
            	while(!Stored.empty())
            	{
            		Locker temp=Stored.pop();
            		System.out.print("���� : "+ temp.Iname+"("+temp.Price+" ��)�� ��ٱ��Ͽ� �����ðڽ��ϱ�?(1.�� 2.�ƴϿ�) : ");
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
        
   		
   		
   		
//////////////////////////////�ǽð� ���� �˻� ��//////////////////////////////////////////////////////
		
/////////////////////////////////�ǽð� ���� �� ��ǰ�� �������� ingredient(�˻��� ���, �񱳱���)//////////////////////////////////////				
		
	
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
						+ "where locker.prank<>excluded.prank and locker.code=excluded.code and locker.iname=excluded.iname;"; //Locker�� ������ �����ϴ� SQL��
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
						//Locker�� ������ �����ϴ� SQL��
				
				
				//String querys="insert into locker values(\'"+temp.Code+"\',\'"+temp.Iname+"\',\'"+temp.Volume+"\',\'"+temp.Price+"\',\'"+temp.Prank+"\',\'"+temp.Purl+"\',\'"+temp.Product+"\');\r\n";
				//System.out.println(querys);
				st.execute(querys);
			}
		}catch(SQLException e)
		{
			System.out.println("������ ����");
		
		}

		System.out.println("��ٱ��Ͽ� ��� �߰��� �Ϸ��߽��ϴ�.");
	}
	
	public static void view_locker() throws SQLException {
		Connection conn;
	    Statement st;
	    Statement st2;
	    Statement st3;
	    ResultSet rs;
	    String mname="";
	    String code="";
	    String Querys="";
	    Stack <String> menus= new Stack <String>();
	    
	    conn = DriverManager.getConnection(url, user, password);
	    System.out.println("���� ��ٱ��Ͽ� ��� ���");
	    
	    st = conn.createStatement();
	    st2 =conn.createStatement();
	    st3 =conn.createStatement();
	    boolean flag=false;
	    Querys="select distinct code from locker;";
	    rs=st.executeQuery(Querys);
	    while(rs.next())
	    {
	    	code=rs.getString("code");
	    	ResultSet rs_menu;
	    	rs_menu=st2.executeQuery("select mname from menu where code=\'"+code+"\';");
	    	flag=true;
	    	//�޴� �̸� ��������
	    	while(rs_menu.next())
	    	{
	    		mname=rs_menu.getString("mname");
	    		System.out.println(mname+"�� �ʿ��� ��� List");
	    		System.out.println("------------------------");
	    		ResultSet rs_ingredient=st3.executeQuery("select iname,price from locker where code=\'"+code+"\';");
		    	while(rs_ingredient.next())
		    	{
		    		String iname =rs_ingredient.getString("iname");
		    		String price =rs_ingredient.getString("price");
		    		System.out.println("��� : "+iname+" ���� : "+price+"��");
		    	}
		    	System.out.println();
		    
	    		
	    	}
	    }
	    

		if(!flag)
		{
			System.out.println("���� �������� ��� �ֽ��ϴ�.");
			Scanner scan = new Scanner(System.in);
			System.out.print("�ѹ� �� ������ �������� ���ư��ϴ�.");
			scan.nextLine();
		}
		else if(flag)
		{
			Scanner scan = new Scanner(System.in);
			System.out.print("1. ��� �� ���� 2. ��� ���� 3. ��ٱ��� ���� 4. ��ٱ��� ���� ��� 5. ���� ȭ�� ���ư��� : ");
			int check =scan.nextInt();
			if(check==1) {
				conn = DriverManager.getConnection(url, user, password);
			    st = conn.createStatement();
			    Querys="select iname,price,product,purl from locker";
				rs=st.executeQuery(Querys);
				while(rs.next())
				{
					String iname = rs.getString("iname");
					String price = rs.getString("price");
					String product = rs.getString("product");
					String purl = rs.getString("purl");
					System.out.println("���� : "+iname+", ���� : "+price+", ��ǰ�� : "+product+"\n��ǰ ������ URL : "+purl+"\n");
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
				Querys="Delete from locker\r\n"
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
	    Scanner scan =new Scanner(System.in);
	    System.out.print("1.��� �丮���� ������ 2.Ư���丮�� ������ 3. ���ư��� : ");
	    int check = scan.nextInt();
	    if(check==1)
	    {
	    	scan.nextLine();
	    	System.out.print("������ ��Ḧ �Է����ּ���:");
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
			System.out.println("������ �Ϸ�Ǿ����ϴ�.");
	    }
	    else if(check==2)
	    {
	    	conn = DriverManager.getConnection(url, user, password);
	    	st = conn.createStatement();
	    	scan.nextLine();
	    	System.out.print("������ �޴��� �Է����ּ���:");
	    	String menu = scan.nextLine();
	    	String qmenu="select code from menu where mname = \'"+menu+"\';";
	    	rs=st.executeQuery(qmenu);
	    	String code="";
	    	while(rs.next())
	    	{
	    		code=rs.getString("code");
	    	}
	    	System.out.print("������ ��Ḧ �Է����ּ���:");
	    	String ingredient = scan.nextLine();
	    	try {
	    		String Querys="delete from locker where iname=\'"+ingredient+"\' and code = \'"+code+"\';";
				st.execute(Querys);
	    	}catch(SQLException e)
	    	{
					System.out.println(e);
			}
		
			System.out.println("������ �Ϸ�Ǿ����ϴ�.");    	
	    }
	    else if(check==3)
	    {
	    	view_locker();
	    }
	    else
	    {
	    	System.out.println("�߸��� �Է�!");

	    }
	    
	}
	
	public static void rollup_locker() throws SQLException
	{
		ResultSet rs = null;
	    ResultSet vs = null;
	    Connection conn = DriverManager.getConnection(url,user,password);
	    System.out.println("��� ���� ���");
	    String query= "select code,iname, sum(price) from locker group by rollup(code,iname);";
	    PreparedStatement pst=conn.prepareStatement(query);
	    pst=conn.prepareStatement(query);
	    rs=pst.executeQuery();
	    while (rs.next()) {
            String code = rs.getString("code");
            String iname = rs.getString("iname");
            String sum = rs.getString("sum");

            if(code==null && iname==null)
            	System.out.printf("���� ��ٱ��Ͽ� �����ϴ� ��� ������ ���� : %s�� �Դϴ�.\n",sum);
            else if(code!=null && iname ==null) {
            	String query2 = "select mname from menu where code="+code+";";
            	 PreparedStatement pst2=conn.prepareStatement(query);
                 pst2=conn.prepareStatement(query2);
                 vs=pst2.executeQuery();
                 while(vs.next()) {
                	 String mname = vs.getString("mname");
                	 System.out.printf("��ٱ��� �� %s ��� ������ ����: %s�� �Դϴ�.\n",mname, sum);
                 	}
            	}
	    	}
		}
}
