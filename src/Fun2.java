import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Fun2 {
      public static void main(String[] args) throws SQLException {
         Scanner scan=new Scanner(System.in);
         System.out.print("������ �̸��� �Է��ϼ���: ");
         String rcorder=scan.nextLine();
         String ck=null;
         ResultSet rs = null;
         ResultSet rss = null;
         
         try{
         // JDBC�� �̿��� PostgreSQL ���� �� �����ͺ��̽� ����
        	 String url = "jdbc:postgresql://127.0.0.1:5432/";
             String user     = "postgres";
             String password = "0000";
           Connection conn = DriverManager.getConnection(url,user,password);  
           String querys= "select code,mname from menu;";
           PreparedStatement psts=conn.prepareStatement(querys);
           psts=conn.prepareStatement(querys);
           rss=psts.executeQuery();
           while (rss.next()) {
        	   String mname = rss.getString("mname");
        	   String code = rss.getString("code");
        	   if(mname.contains(rcorder))
        		   ck=code;
           }
           if(ck!=null) {
	           String query= "select * from recipe where code = ";
	           query = query + ck + "ORDER BY corder ASC;";
	           PreparedStatement pst=conn.prepareStatement(query);
	         pst=conn.prepareStatement(query);
	         rs=pst.executeQuery();
	        
	         System.out.println("������ ����");
	         while (rs.next()) { 
	               String code = rs.getString("code");
	               String corder = rs.getString("corder");
	               String intro = rs.getString("intro");
	               String imgURL = rs.getString("imgURL");
	               String tip = rs.getString("tip");
	               System.out.printf("������ �ڵ�: %s, ������ ����: %s, ������ ����: %s, ������ ����: %s, ���� tip: %s\n",code,corder,intro,imgURL,tip);                  
	 	     }  
	       } 
         }
         catch(SQLException ex) {
	            System.err.format("SQL State: %s\n%s", ex.getSQLState(), ex.getMessage());
	           throw ex;
	         }
      }
   }