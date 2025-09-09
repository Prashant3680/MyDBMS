import java.util.Scanner;
public class MyDBMS 
{
	static String[] query;
	static Scanner sc=new Scanner(System.in);
	public static void main(String[] args) 
	{	
		MyDB db=new MyDB("SCOTT","Tiger");
		
		while(true)
		{
			System.out.println("Enter username:");
			String user=sc.next();
			System.out.println("Enter password: ");
			String pass=sc.next();
			if(db.login(user,pass))
			{
				break;
			}
		}
		
		while(true)
		{
			queryReceiver();
			System.out.println();
			db.queryInterpreter(query);
			System.out.println();
		}	
		
	}
	
	public static void queryReceiver()
	{
		query=new String[20];
		int count=0;
		System.out.println("SQL>");
		
		for(int i=0;i<query.length;i++)
		{
			if(count>0.7*query.length)
			{
				String[] temp=new String[query.length*2];
				for(int j=0;j<count;j++)
				{
					temp[j]=query[j];
				}
				query=temp;
			}
			query[i]=sc.next().toUpperCase();
			count++;
	
			if(query[i].charAt(query[i].length()-1)==';')
			{
				break;
			}
		}
	}

}