public class MyDB 
{
	private String user;
	private String pass;
	
	private int empRec;
	private Emp[] empTable=new Emp[20];
	{
		empTable[empRec++]=new Emp("EMPNO ","ENAME    ","JOB        ","MGR    ","HIREDATE    ","SAL   ","COMM  ","DEPTNO");
		empTable[empRec++]=new Emp("----- ","------   ","--------   ","----   ","---------   ","----  ","----  ","------");
		empTable[empRec++]=new Emp("7369  ","SMITH    ","CLERK      ","7902   ","17-DEC-80   ","800   ","      ","20");
		empTable[empRec++]=new Emp("7499  ","ALLEN    ","SALESMAN   ","7698   ","20-FEB-81   ","1600  ","300   ","30");
		empTable[empRec++]=new Emp("7521  ","WARD     ","SALESMAN   ","7698   ","22-FEB-81   ","1250  ","500   ","30");
		empTable[empRec++]=new Emp("7566  ","JONES    ","MANAGER    ","7839   ","02-APR-81   ","2975  ","      ","20");
		empTable[empRec++]=new Emp("7654  ","MARTIN   ","SALESMAN   ","7698   ","28-SEP-81   ","1250  ","1400  ","30");
		empTable[empRec++]=new Emp("7698  ","BLAKE    ","MANAGER    ","7839   ","01-MAY-81   ","2850  ","      ","30");
		empTable[empRec++]=new Emp("7782  ","CLARK    ","MANAGER    ","7839   ","09-JUN-81   ","2450  ","      ","10");
		empTable[empRec++]=new Emp("7788  ","SCOTT    ","ANALYST    ","7566   ","19-APR-87   ","3000  ","      ","20");
		empTable[empRec++]=new Emp("7839  ","KING     ","PRESIDENT  ","       ","17-NOV-81   ","5000  ","      ","10");
		empTable[empRec++]=new Emp("7844  ","TURNER   ","SALESMAN   ","7698   ","08-SEP-81   ","1500  ","0     ","30");
		empTable[empRec++]=new Emp("7876  ","ADAMS    ","CLERK      ","7788   ","23-MAY-87   ","1100  ","      ","20");
		empTable[empRec++]=new Emp("7900  ","JAMES    ","CLERK      ","7698   ","03-DEC-81   ","950   ","      ","30");
		empTable[empRec++]=new Emp("7902  ","FORD     ","ANALYST    ","7566   ","03-DEC-81   ","3000  ","      ","20");
		empTable[empRec++]=new Emp("7934  ","MILLER   ","CLERK      ","7782   ","23-JAN-82   ","1300  ","      ","10");
	}
	
	private int depRec;
	private Dept[] depTable=new Dept[8];
	{
		depTable[depRec++]=new Dept("DEPTNO ","DNAME       ","LOC");
		depTable[depRec++]=new Dept("------ ","----------  ","--------");
		depTable[depRec++]=new Dept("10     ","ACCOUNTING  ","NEW-YORK");
		depTable[depRec++]=new Dept("20     ","RESEARCH    ","DALLAS");
		depTable[depRec++]=new Dept("30     ","SALES       ","CHICAGO");
		depTable[depRec++]=new Dept("40     ","OPERATIONS  ","BOSTON");
			
	}
	
	
	MyDB(){}
	
	MyDB(String user,String pass)
	{
		this.user=user;
		this.pass=pass;
	}
	
	public boolean login(String user,String pass)
	{
		if(this.user.equals(user) && this.pass.equals(pass))
		{
			System.out.println("Connected to:"
					+ "SQL Database 10g Enterprise Edition Release 1 - Production With the Partitioning, OLAP and Data Mining options");
			System.out.println();
			return true;
		}
		System.out.println("Invalid username/password; login denied");
		System.out.println();
		return false;
	}
	
	public void queryInterpreter(String[] query)
	{
		switch(query[0])
		{
			case "SELECT":
			from(query);
			break;
		
			case "INSERT":
			insertQuery(query);
			break;
			
			default:
				System.out.println("Syntax error!");
		}
	}
	
	private void insertQuery(String[] query)
	{
		if(query[0].equals("INSERT") && query[1].equals("INTO") && query[3].substring(0,6).equals("VALUES"))
				{
					switch(query[2])
					{
						case "EMP" :
						insertEmp(query[3].substring(7,query[3].length()-2));
						break;
						
						case "DEPT" :
						//	insertDept();
						break;
						
						default:
						System.out.println("Table not found in the database!");
					}
				}
		else
			System.out.println("Syntax Error!");
		
	}
	
	private void insertEmp(String values) 
	{
		String[] attri=values.split(",");
		String empNo=attri[0]+"  ";
		String ename=attri[1]+"     "; 
		String job=attri[2]+"      ";
		String mgr=attri[3]+"   ";
		String hireDate=attri[4]+"   ";
		String sal=attri[5]+"  ";
		String comm=attri[6]+"  ";
		String deptNo=attri[7]+"  ";
	
		
			if(empRec>empTable.length*0.7)
			{
				Emp[] temp=new Emp[empTable.length*2];
				for(int i=0;i<empRec;i++)
				{
					temp[i]=empTable[i];
				}
				
				empTable=temp;
			}
			
			empTable[empRec++]=new Emp(empNo,ename,job,mgr,hireDate,sal,comm,deptNo);
			System.out.println("Emp "+ename+" added in Employee table!");
			return;
		
	}
	
	private void from(String[] query)
	{	
		String tableName=query[3];
		if(tableName.endsWith(";"))
			tableName=tableName.substring(0,tableName.length()-1);
			
			
		switch(tableName)
		{
			case "EMP":
			{	
				if(query[3].endsWith(";"))
				{	
					select(empTable,query[1]);
				}
				else if(query[4].equals("WHERE"))
				{
					Emp[] displayEmp=filterEmp(query);	

					if(displayEmp==null)
					{
						System.out.println("Column not found!");
						return;
					}
					if(query[7].charAt(query[7].length()-1)==';')
					{
						select(displayEmp,query[1]);
					}
					else if(query[8].equals("GROUP"))
					{
						
					}
					else if(query[8].equals("ORDER"))
					{
						
					}
					else
					{
						System.out.println("Syntax error!");
					}
				}
				//else if(query[4].equals("GROUP"))
					//groupBy(query);
				//else if(query[4].equals("ORDER"))
					//orderBY(query);
				else
					System.out.println("Syntax error!");
				
				break;
			}
			
			case "DEPT":
			{
				if(query[3].endsWith(";"))
				{
					select(depTable,query[1]);
				}
				else if(query[4].equals("WHERE"))
				{
					Dept[] displayDept=filterDept(query);	
					if(displayDept==null)
					{
						return;
					}
					if(query[7].charAt(query[7].length()-1)==';')
					{
						select(displayDept,query[1]);
					}
					else if(query[8].equals("GROUP"))
					{
						
					}
					else if(query[8].equals("ORDER"))
					{
						
					}
					else
					{
						System.out.println("Syntax error!");
					}
				}
				//else if(query[4].equals("GROUP"))
					//groupBy(query);
				//else if(query[4].equals("ORDER"))
					//orderBY(query);
				else
					System.out.println("Syntax error!");
				break;
			}
			
			default:
				System.out.println("Table not found!");
		}
		
	}
	
	private Emp[] filterEmp(String[] query)
	{
		Emp[] display=new Emp[empRec];
		String attri;
		String value=query[7];
		if(value.endsWith(";"))
			value=query[7].substring(0,query[7].length()-1);
		int rec=0;	
		for(int i=0;i<empRec;i++)
		{
			switch(query[5])
			{
				case "EMPNO":
					attri=empTable[i].empNo;
					break;
				
				case "ENAME":
					attri=empTable[i].ename;
					break;
				
				case "JOB":
					attri=empTable[i].job;
					break;
				
				case "MGR":
					attri=empTable[i].mgr;
					break;
				
				case "HIREDATE":
					attri=empTable[i].hiredate;
					break;
				
				case "SAL":
					attri=empTable[i].sal;
					break;
				
				case "COMM":
					attri=empTable[i].comm;
					break;
				
				case "DEPTNO":
					attri=empTable[i].deptNo;
					break;
				
				default:
					System.out.println("column not found!");
					return null;
				
			}
			
			  if(i<=1)
				display[rec++]=empTable[i];
			  else 
			  {
				  if(query[6].equals("=") && attri.stripTrailing().equals(value))
					  display[rec++]=empTable[i];
				  else if(query[6].equals("!=") && !attri.stripTrailing().equals(value))
					  display[rec++]=empTable[i];
				  else if(query[6].equals(">") && Integer.parseInt(attri.stripTrailing())>Integer.parseInt(value.stripTrailing()))
					  display[rec++]=empTable[i];
				  else if(query[6].equals(">=") && Integer.parseInt(attri.stripTrailing())>=Integer.parseInt(value.stripTrailing()))
					  display[rec++]=empTable[i];
				  else if(query[6].equals("<") && Integer.parseInt(attri.stripTrailing())<Integer.parseInt(value.stripTrailing()))
					  display[rec++]=empTable[i];
				  else if(query[6].equals("<=") && Integer.parseInt(attri.stripTrailing())<=Integer.parseInt(value.stripTrailing()))
					  display[rec++]=empTable[i];
				  else if(query[6].equals("IN") && value.contains(attri.stripTrailing()))
					  display[rec++]=empTable[i];
				  else if(query[6].equals("NOTIN") && !value.contains(attri.stripTrailing()))
					  display[rec++]=empTable[i];
				  
			  }
				
				
		}
		
		return display;
	}
	
	private Dept[] filterDept(String[] query)
	{
		Dept[] display=new Dept[depRec];
		String attri;
		String value=query[7].substring(0,query[7].length()-1);
		int rec=0;	
		for(int i=0;i<depRec;i++)
		{
			switch(query[5])
			{
				case "DEPTNO":
					attri=depTable[i].deptNo;
					break;
				
				case "DNAME":
					attri=depTable[i].dname;
					break;
				
				case "LOC":
					attri=depTable[i].loc;
					break;
				
				default:
					System.out.println("Wrong column entered in filter condition!");
					return null;
				
			}
			
			  if(i<=1)
				display[rec++]=depTable[i];
			  else
			  {
				  if(query[6].equals("=") && attri.stripTrailing().equals(value))
					  display[rec++]=depTable[i];
				  else if(query[6].equals("!=") && !attri.stripTrailing().equals(value))
					  display[rec++]=depTable[i];
				  else if(query[6].equals(">") && attri.stripTrailing().compareTo(value)>0)
					  display[rec++]=depTable[i];		  
				  else if(query[6].equals(">=") && attri.stripTrailing().compareTo(value)>=0)
					  display[rec++]=depTable[i];
				  else if(query[6].equals("<") && attri.stripTrailing().compareTo(value)<0)
					  display[rec++]=depTable[i];
				  else if(query[6].equals("<=") && attri.stripTrailing().compareTo(value)<=0)
					  display[rec++]=depTable[i];
				  else if(query[6].equals("IN") && value.contains(attri.stripTrailing()))
					  display[rec++]=depTable[i];
				  else if(query[6].equals("NOTIN") && !value.contains(attri.stripTrailing()))
					  display[rec++]=depTable[i];
			  }
				  
				
		}
		
		return display;
		
	}
	
	private void select(Emp[] display,String columns)
	{
		String[] attri=columns.split(",");
		int i=0;
		for(  ;i<display.length && display[i]!=null ;i++)
		{
			for(int j=0;j<attri.length;j++)
			{	columns=attri[j];
				if(columns.equals("EMPNO")||columns.equals("*"))
					System.out.print(display[i].empNo);
				if(columns.equals("ENAME")||columns.equals("*"))
					System.out.print(display[i].ename);
				if(columns.equals("JOB")||columns.equals("*"))
					System.out.print(display[i].job);
				if(columns.equals("MGR")||columns.equals("*"))
					System.out.print(display[i].mgr);
				if(columns.equals("HIREDATE")||columns.equals("*"))
					System.out.print(display[i].hiredate);
				if(columns.equals("SAL")||columns.equals("*"))
					System.out.print(display[i].sal);
				if(columns.equals("COMM")||columns.equals("*"))
					System.out.print(display[i].comm);
				if(columns.equals("DEPTNO")||columns.equals("*"))
					System.out.print(display[i].deptNo);
			}
			
			System.out.println();	
		}
		System.out.println();
		System.out.println((i-2)+" rows selected");
	}
	
	private void select(Dept[] display,String columns)
	{
		String[] attri=columns.split(",");
		int i=0;
		for(  ;i<display.length && display[i]!=null ;i++)
		{
			for(int j=0;j<attri.length;j++)
			{	columns=attri[j];
				if(columns.equals("DEPTNO")||columns.equals("*"))
					System.out.print(display[i].deptNo);
				if(columns.equals("DNAME")||columns.equals("*"))
					System.out.print(display[i].dname);
				if(columns.equals("LOC")||columns.equals("*"))
					System.out.print(display[i].loc);
				
			}
			
			System.out.println();	
		}
		System.out.println();
		System.out.println((i-2)+" rows selected");
	}
	
}