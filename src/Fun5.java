import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Fun5 {
	public static void main(String[] args) throws SQLException {
		Scanner scan=new Scanner(System.in);
		System.out.print("�������ڵ� �Է�:");
		String rcode=scan.nextLine();
		int num=0;
		ResultSet rs = null;
		
		try{
		// JDBC�� �̿��� PostgreSQL ���� �� �����ͺ��̽� ����
		String url = "jdbc:postgresql://127.0.0.1:5432/";
        String user     = "postgres";
        String password = "0000";
        Connection conn = DriverManager.getConnection(url,user,password);  
        String query= "select iname,volume from ingredient where code=";
        query = query + rcode + ";";
        PreparedStatement pst=conn.prepareStatement(query);
		pst=conn.prepareStatement(query);
		rs=pst.executeQuery();
		System.out.println("�ʿ��� ��� list");
		while (rs.next()) {
            String iname = rs.getString("iname");
            String volume = rs.getString("volume");
            System.out.printf("%d.����: %s, ��: %s\n",num++,iname,volume);                  
        }  
		while(true) {
			System.out.print("�ʿ��� ��� ��ȣ �Է�(�� ������): ");
			String re_num=scan.nextLine();
		}
		} catch(SQLException ex) {
			System.err.format("SQL State: %s\n%s", ex.getSQLState(), ex.getMessage());
			throw ex;
		}
	}
}
