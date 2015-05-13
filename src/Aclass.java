
public class Aclass {
	String strdata="a";
	int index;
	public Aclass()
	{
		index=0;
	    
	   
	}
	 String int2str(int index2, String buff) {
		// TODO Auto-generated method stub
		System.out.print("int2strA"+buff);
		return ""+buff;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Bclass b = new Bclass();
		Aclass a = ((Aclass) b);
		a.int2str(1,a.strdata);
	
	}

}

 class Bclass extends Aclass
{
	int index;
	String strdata="b";
	
}

