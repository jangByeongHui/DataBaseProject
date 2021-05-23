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
	static String[] Ranks={"���̹���ŷ","�������ݼ�","�������ݼ�","����ϼ�","���丹����"};
	
	public static void main(String[] args) throws SQLException, ClassNotFoundException
	 {
		
		String[] Ranks={"���̹���ŷ","�������ݼ�","�������ݼ�","����ϼ�","���丹����"};

        
		try {
//////////////////////////////////////////1. ���� ������ �޴� ����////////////////////////////////////////////////////////
	            look_menu();
/////////////������ ���� : rcode , ������ ���� �з� : rcode2///////////////////////////////////////
///////////////////////////////1.���� ������ �޴� ���� ����///////////////////////////////////////
	            
///////////////////////////////2. ������ �޴� ������ �� ��� ����///////////////////////////////////
	            String code =recipe_look();
/////////////////////////////code : ������ �ڵ� , food : �丮��
////////////////////////////////2. ������ �޴� ������ �� ��� ���� ����////////////////////////////////////
	            
////////////////////////////////3. ������ ���� , ���� ���///////////////////////////////////////////
	            Stack <Locker> result = ingredient_stack(code);
////////////////////////////////3. ������ ��� ���� ��� �� ���� ����///////////////////////////////////////
	            
///////////////////////////////////4. ������ �߰�//////////////////////////////////////////////////////
	     
////////////////////////////////////////////////////////////////////////////////////////////////////	         
				
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
        System.out.print("������ �з����ּ���.(�ѽ�/�߱�/�Ͻ�/���/ǻ��/�����ƽþ�/����/��Ż����):");
        String rcode=scan.nextLine();
        System.out.println();
        System.out.print("������ �з����ּ���.(��/����/Ƣ��/�׶���):");
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
        //    System.out.printf("�������ڵ�: %s, �޴��̸�: %s, �����Ұ�: %s, �޴�Ÿ��: %s, �޴��̸�: %s, �����ð�: %s, �з�: %s, ���̵�: %s, ��ǥ�̹���: %s\n",code,mname,short_intro,type_name,type_menu,mtime,mvolume ,mlevel,imgURL);                  
            if(type_name.contains(rcode) && type_menu.contains(rcode2))
            	System.out.printf("�������ڵ�: %s, �޴��̸�: %s, �����Ұ�: %s, �޴�Ÿ��: %s, �޴��̸�: %s, �����ð�: %s, �з�: %s, ���̵�: %s, ��ǥ�̹���: %s\n",code,mname,short_intro,type_name,type_menu,mtime,mvolume ,mlevel,imgURL);                
            }
	}
	
	public static String recipe_look() throws SQLException
	{        
        Connection conn;
        Statement st;
        ResultSet rs;
        conn = DriverManager.getConnection(url, user, password);
		st = conn.createStatement();
		
		System.out.print("�޴��� �������ּ���:");
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
        
        
        System.out.print("������ ������ ���Ͻʴϱ�?(y/n):");
        String check=scan.nextLine();
        
        if(check.equals("y")||check.equals("Y"))
        {
        	querys= "select * from recipe where code = ";
        	querys = querys + code + "ORDER BY corder ASC;";
        	PreparedStatement pst=conn.prepareStatement(querys);
        	pst=conn.prepareStatement(querys);
        	rs=pst.executeQuery();
        	
        	System.out.println("������ ����");
	         while (rs.next()) { 
	               String corder = rs.getString("corder");
	               String intro = rs.getString("intro");
	               String imgURL = rs.getString("imgURL");
	               String tip = rs.getString("tip");
	               System.out.printf("������ �ڵ�: %s, ������ ����: %s, ������ ����: %s, ������ ����: %s, ���� tip: %s\n",code,corder,intro,imgURL,tip);                  
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
   		System.out.print("�ʿ��� ��� ���� ������ ���Ͻʴϱ�?(y/n):");
        String check=scan.nextLine();
        
   		System.out.println("--------------------�ʿ��� ��� list--------------------");
   		
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
   				System.out.printf("����: %s, ��: %s\n",iname,volume);
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
   		
//////////////////////////////�ǽð� ���� �˻� ��//////////////////////////////////////////////////////
		System.out.println("�츮 �丮�� �ʿ��� ������ ���ϰڽ��ϴ�.");
/////////////////////////////////�ǽð� ���� �� ��ǰ�� ������� ingredient(�˻��� ���, �񱳱���)//////////////////////////////////////				
		System.out.print("�񱳱����� �Է��ϼ��� 1.���̹���ŷ 2. ���� ���ݼ� 3. ���� ���ݼ� 4. ����� �� 5. ���丹�� ��:");
		int rank=scan.nextInt();
		
		System.out.print("�ʿ��� �ǽð� ���� ������ ���ðڽ��ϱ�?(y/n):");
		Stack <Locker> Stored=new Stack <Locker>();
		scan.nextLine(); //���� ����
		check=scan.nextLine();
		if(check.equals("Y")||check.equals("y")){
        		while(!inameStack.empty())
        		{
        			String iname=inameStack.pop();
        			String volume=VolumeStack.pop();
        			WebResult ingredient=Search(iname,rank-1);
        			System.out.print("���� : "+ ingredient.iname+"\n��ǰ�� : "+ingredient.name+" \n���� : "+ingredient.price+"�� \n�񱳱��� : "+Ranks[ingredient.rank]+"\n��ǰ ������ : "+ingredient.URL+"\n");
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
