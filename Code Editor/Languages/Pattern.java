

public class Pattern
{
	public static void main(String[] args)
	{
		for ( int i=1;i<=5;i++)
		{
			
			for( int j=i;j<=5;j++)
			{
				System.out.print(( char)(j+64));
			}

		System.out.println("");	
		}
		for ( int i=4;i>=1;i--)
		{
			for ( int k=5;k>=i;k--)
			{
				System.out.print(( char)(k+64));
			}	
			System.out.println("");	
		}
	}
}