import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.Stack; //import
public class Fun3 {
	public static void main(String[] args) throws SQLException {
		ResultSet rs = null;
		String ck=null;
		
		Scanner scan=new Scanner(System.in);
		System.out.print("레시피 이름 입력:");
		String rname=scan.nextLine();
		
		try{
		// JDBC를 이용해 PostgreSQL 서버 및 데이터베이스 연결
		String url = "jdbc:postgresql://127.0.0.1:5432/";
        String user     = "postgres";
        String password = "0000";
        Connection conn = DriverManager.getConnection(url,user,password);  
        
        String querys= "select code,mname from menu;";
        PreparedStatement psts=conn.prepareStatement(querys);
        psts=conn.prepareStatement(querys);
        rs=psts.executeQuery();
        while (rs.next()) {
     	   String mname = rs.getString("mname");
     	   String rcode = rs.getString("code");
     	   if(mname.contains(rname))
     		   ck=rcode;
        }
        if(ck!=null) {
	        String query= "select iname,volume from ingredient where code=";
	        query = query + ck + ";";
	        PreparedStatement pst=conn.prepareStatement(query);
	   		pst=conn.prepareStatement(query);
	   		rs=pst.executeQuery();
	   		System.out.println("필요한 재료 list");
	   		while (rs.next()) {	
	               String iname = rs.getString("iname");
	               String volume = rs.getString("volume");
	               System.out.printf("재료명: %s, 양: %s\n",iname,volume);
	        }  
        }  
		} catch(SQLException ex) {
			System.err.format("SQL State: %s\n%s", ex.getSQLState(), ex.getMessage());
			throw ex;
		}
	}
}
