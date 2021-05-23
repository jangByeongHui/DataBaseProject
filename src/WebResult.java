
public class WebResult {
	
	int price;
	int rank;
	String iname;
	String URL;
	String name;
	public WebResult(String Iname,String site,String pname,int Price,int Rank)
	{
		this.iname=Iname;
		this.price=Price;
		this.rank=Rank;
		this.URL=site;
		this.name=pname;
	}
	

}
