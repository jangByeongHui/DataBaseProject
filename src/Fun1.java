import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Fun1 {
   public static void main(String[] args) throws SQLException {
      Scanner scan=new Scanner(System.in);
      Scanner scan2=new Scanner(System.in);
      System.out.print("������ �з����ּ���.(�ѽ�/����/�Ͻ�/���):");
      String rcode=scan.nextLine();
      System.out.println();
      System.out.print("������ �з����ּ���.(��/����/Ƣ�� ��):");
      String rcode2=scan2.nextLine();
      
      ResultSet rs = null;
      
      try{
      // JDBC�� �̿��� PostgreSQL ���� �� �����ͺ��̽� ����
    	  String url = "jdbc:postgresql://127.0.0.1:5432/";
          String user     = "postgres";
          String password = "0000";
        Connection conn = DriverManager.getConnection(url,user,password);  
      System.out.println("���� ����");
      String query= "select * from menu";
      PreparedStatement pst=conn.prepareStatement(query);
      pst=conn.prepareStatement(query);
      rs=pst.executeQuery();
		
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
      
      } catch(SQLException ex) {
         System.err.format("SQL State: %s\n%s", ex.getSQLState(), ex.getMessage());
         throw ex;
      }
   }
}