import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Fun6 {

	public static void main(String[] args) throws SQLException {
	      ResultSet rs = null;
	      ResultSet vs=null;
	      
	      try{
	      // JDBC�� �̿��� PostgreSQL ���� �� �����ͺ��̽� ����
	    	  String url = "jdbc:postgresql://127.0.0.1:5432/";
	          String user     = "postgres";
	          String password = "0000";
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
	      
	      } catch(SQLException ex) {
	         System.err.format("SQL State: %s\n%s", ex.getSQLState(), ex.getMessage());
	         throw ex;
	      }
	 }
}
