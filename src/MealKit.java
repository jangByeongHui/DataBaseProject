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
				Scanner scan=new Scanner(System.in);
	            while(true)
	            {
	            	System.out.print("���ϴ� ����� �����ϼ���.(1. �޴��˻� 2. ������ ���� 3. ��� ���� �� �庸�� 4. ��ٱ��� ���� 5. ���� ) : ");
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
        System.out.print("������ �з����ּ���.(�ѽ�/�߱�/�Ͻ�/���/ǻ��/�����ƽþ�/����/��Ż����):");
        String rcode=scan.nextLine();
        System.out.println();
        System.out.print("������ �з����ּ���.(��/����/Ƣ��/�׶���):");
        String rcode2=scan.nextLine();
        String querys= "select * from menu";
        rs = st.executeQuery(querys);
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
            //System.out.printf("�������ڵ�: %s, �޴��̸�: %s, �����Ұ�: %s, �޴�Ÿ��: %s, �޴��̸�: %s, �����ð�: %s, �з�: %s, ���̵�: %s, ��ǥ�̹���: %s\n",code,mname,short_intro,type_name,type_menu,mtime,mvolume ,mlevel,imgURL);                  
            if(type_name.contains(rcode) && type_menu.contains(rcode2))
            	System.out.printf("�������ڵ�: %s, �޴��̸�: %s, �����Ұ�: %s, �޴�Ÿ��: %s, �޴��̸�: %s, �����ð�: %s, �з�: %s, ���̵�: %s, ��ǥ�̹���: %s\n",code,mname,short_intro,type_name,type_menu,mtime,mvolume ,mlevel,imgURL);                
            //
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
		
		System.out.print("�޴��� �Է����ּ���:");
        Scanner scan =new Scanner(System.in);
        String food = scan.nextLine();
        
        String querys= "select code,mname from menu;";
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
        	System.out.println("���� �� �޴��� �����ϴ�.");
        }
        else
        {
        	System.out.print("������ ������ ���Ͻʴϱ�?(y/n):");
            String check=scan.nextLine();
            
            if(check.equals("y")||check.equals("Y"))
            {
            	querys= "select * from recipe where code = ";
            	querys = querys + ck + " ORDER BY corder ASC;";
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
    	               System.out.printf("������ �ڵ�: %s, ������ ����: %s, ������ ����: %s, ������ ����: %s, ���� tip: %s\n",code,corder,intro,imgURL,tip);                  
    	 	     	} 
            }
        }
        
        
		
		
	}
	
	public static void ingredient_stack() throws IOException, SQLException
	{
		Connection conn;
	    Statement st;
	    ResultSet rs;
	    String ck=null;
	    conn = DriverManager.getConnection(url, user, password);
	    st = conn.createStatement();
	    
	    System.out.print("��Ḧ �������ϴ� �޴��� �Է����ּ���:");
        Scanner scan =new Scanner(System.in);
        String food = scan.nextLine();
        
        String querys= "select code,mname from menu;";
        
        PreparedStatement psts=conn.prepareStatement(querys);
        psts=conn.prepareStatement(querys);
        rs=psts.executeQuery();
        boolean flag=false;
        while (rs.next()) {
     	   String mname = rs.getString("mname");
     	   String rcode = rs.getString("code");
     	   if(mname.contains(food))
     	   {
     		   ck=rcode;
     		   flag=true;
     	   }
        }
        
        if(!flag)
        {
        	System.out.println("���� �� �޴��� �����ϴ�.");
        }
        else {
        	querys = "select iname,volume from ingredient where code=\'";
            querys = querys + ck + "\';";
       		rs=st.executeQuery(querys);
       		Stack <String> inameStack= new Stack<String>();
       		Stack <String> VolumeStack= new Stack<String>();
       		
       		System.out.print("�ʿ��� ��� ���� ������ ���Ͻʴϱ�?(y/n):");
            String check=scan.nextLine();
       		
       		if(check.equals("y")||check.equals("Y"))
       		{
       			System.out.println("--------------------�ʿ��� ��� list--------------------");
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
       		System.out.print("�ǽð����� ���� ���� ������ ���Ͻʴϱ�?(y/n):");
            check=scan.nextLine();
            if(check.equals("y")||check.equals("Y")) {
            	System.out.println("�˻��� �丮�� �ʿ��� ������ ���ϰڽ��ϴ�.");
            	System.out.print("�񱳱����� �Է��ϼ��� 1.���̹���ŷ 2. ���� ���ݼ� 3. ���� ���ݼ� 4. ����� �� 5. ���丹�� ��:");
        		int rank=scan.nextInt();
        		Stack <Locker> Stored=new Stack <Locker>();
        		while(!inameStack.empty())
        		{
        			String iname=inameStack.pop();
        			String volume=VolumeStack.pop();
        			WebResult ingredient=Search(iname,rank-1);
        			System.out.print("���� : "+ ingredient.iname+"\n��ǰ�� : "+ingredient.name+" \n���� : "+ingredient.price+"�� \n�񱳱��� : "+Ranks[ingredient.rank]+"\n��ǰ ������ : "+ingredient.URL+"\n");
        			int tcode=Integer.parseInt(ck);
        			Locker temp = new Locker(tcode,iname,volume,ingredient.price,rank,ingredient.URL, ingredient.name);
        			Stored.push(temp);
        		}
        		scan.nextLine();
        		System.out.print("���� ��Ḧ ��ٱ��Ͽ� ��ڽ��ϱ�?(y/n):");
                check=scan.nextLine();
                if(check.equals("y")||check.equals("Y"))
                {
                	store_locker(Stored);
                }
        		
            }
        }
        
   		
   		
   		
//////////////////////////////�ǽð� ���� �˻� ��//////////////////////////////////////////////////////
		
/////////////////////////////////�ǽð� ���� �� ��ǰ�� ������� ingredient(�˻��� ���, �񱳱���)//////////////////////////////////////				
		
	}
	
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
			
				String querys="insert into locker values(\'"+temp.Code+"\',\'"+temp.Iname+"\',\'"+temp.Volume+"\',\'"+temp.Price+"\',\'"+temp.Prank+"\',\'"+temp.Purl+"\',\'"+temp.Product+"\');"; //Locker�� ������ �����ϴ� SQL��
				//System.out.println(querys);
				st.execute(querys);
			}
		}catch(SQLException e)
		{
			System.out.println("������ ���� Error");
		}
			
	}
	
	public static void view_locker() throws SQLException {
		Connection conn;
	    Statement st;
	    ResultSet rs;
	    conn = DriverManager.getConnection(url, user, password);
	    st = conn.createStatement();
	    rs = st.executeQuery("select distinct(iname), price,product, purl from locker;");
	    boolean flag=false;
		while(rs.next())
		{
			String iname = rs.getString("iname");
			String price = rs.getString("price");
			String product = rs.getString("product");
			String purl = rs.getString("purl");
			System.out.println("���� : "+iname+" ���� : "+price+" ��ǰ�� : "+product+" ��ǰ �ּ� : "+purl);
			flag=true;
			
		}
		if(!flag)
		{
			System.out.println("���� �������� �� ��� �ֽ��ϴ�.");
		}
		Scanner scan = new Scanner(System.in);
		System.out.println("1. ��� ���� 2. ���� �� ���� 3. ���� : ");
		int check =scan.nextInt();
		if(check==1)
		{
			Delete_locker();
		}
		else if(check == 2)
		{
			rollup_locker();		
		}
		
	}
	
	
	
	public static void Delete_locker() throws SQLException{
		Connection conn;
	    Statement st;
	    ResultSet rs;
	    System.out.print("������ ��Ḧ �Է����ּ���:");
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
	
	}
	
	public static void rollup_locker() throws SQLException
	{
		ResultSet rs = null;
	    ResultSet vs = null;
	    Connection conn = DriverManager.getConnection(url,user,password);
	    System.out.println("��������");
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
