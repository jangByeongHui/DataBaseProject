import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Fun5 {
	public static void main(String[] args) throws SQLException {
		Scanner scan=new Scanner(System.in);
		System.out.print("레시피코드 입력:");
		String rcode=scan.nextLine();
		int num=0;
		ResultSet rs = null;
		
		try{
		// JDBC를 이용해 PostgreSQL 서버 및 데이터베이스 연결
		String url = "jdbc:postgresql://127.0.0.1:5432/";
        String user     = "postgres";
        String password = "0000";
        Connection conn = DriverManager.getConnection(url,user,password);  
        String query= "select iname,volume from ingredient where code=";
        query = query + rcode + ";";
        PreparedStatement pst=conn.prepareStatement(query);
		pst=conn.prepareStatement(query);
		rs=pst.executeQuery();
		System.out.println("필요한 재료 list");
		while (rs.next()) {
            String iname = rs.getString("iname");
            String volume = rs.getString("volume");
            System.out.printf("%d.재료명: %s, 양: %s\n",num++,iname,volume);                  
        }  
		while(true) {
			System.out.print("필요한 재료 번호 입력(한 가지씩): ");
			String re_num=scan.nextLine();
		}
		} catch(SQLException ex) {
			System.err.format("SQL State: %s\n%s", ex.getSQLState(), ex.getMessage());
			throw ex;
		}
	}
}
