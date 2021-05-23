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
      System.out.print("유형을 분류해주세요.(한식/증식/일식/양식):");
      String rcode=scan.nextLine();
      System.out.println();
      System.out.print("음식을 분류해주세요.(국/나물/튀김 등):");
      String rcode2=scan2.nextLine();
      
      ResultSet rs = null;
      
      try{
      // JDBC를 이용해 PostgreSQL 서버 및 데이터베이스 연결
    	  String url = "jdbc:postgresql://127.0.0.1:5432/";
          String user     = "postgres";
          String password = "0000";
        Connection conn = DriverManager.getConnection(url,user,password);  
      System.out.println("음식 정보");
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
        //    System.out.printf("레시피코드: %s, 메뉴이름: %s, 간략소개: %s, 메뉴타입: %s, 메뉴이름: %s, 조리시간: %s, 분량: %s, 난이도: %s, 대표이미지: %s\n",code,mname,short_intro,type_name,type_menu,mtime,mvolume ,mlevel,imgURL);                  

            if(type_name.contains(rcode) && type_menu.contains(rcode2))
            	System.out.printf("레시피코드: %s, 메뉴이름: %s, 간략소개: %s, 메뉴타입: %s, 메뉴이름: %s, 조리시간: %s, 분량: %s, 난이도: %s, 대표이미지: %s\n",code,mname,short_intro,type_name,type_menu,mtime,mvolume ,mlevel,imgURL);                 
      	}  
      
      } catch(SQLException ex) {
         System.err.format("SQL State: %s\n%s", ex.getSQLState(), ex.getMessage());
         throw ex;
      }
   }
}