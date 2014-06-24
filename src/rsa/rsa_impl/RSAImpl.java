package rsa.rsa_impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

public class RSAImpl implements RSA
{
	/**
	 * The number 0.
	 */
	private BigInteger zero;
	
	/**
	 * The number 1.
	 */
	private BigInteger one;
	
	/**
	 * The number 2;
	 */
	private BigInteger two;
	
	/**
	 * A Random class object.
	 */
	private Random rand;
	
	
	
	
	/**
	 * Default Constructor.
	 */
	public RSAImpl()
	{
		zero = new BigInteger("0");
		one = new BigInteger("1");
		two  = new BigInteger("2");
		rand = new Random();
	}
	
	
	
	
	@Override
	public BigInteger GCD(BigInteger b1 , BigInteger b2)
	{
		if(b2.equals(BigInteger.ZERO)) return b1;
			
		return GCD(b2 , b1.mod(b2));
	}
	
	
	
	
	@Override
	public boolean IsPrime(BigInteger number)
    {
		/*
		 * Uses Miller-Rabin AND Fermat primality test to determine if number is probable prime.
		 * It starts with 10 checks. Feel free to change the values , according the accuracy you want.
		 */
		return (MillerRabinPrimeCheck(number , 10) && FermatPrimalityTest(number , 10));
    }
	
	
	
	
	@Override
	public BigInteger GetProbablePrime(int n)
	{
		BigInteger big = new BigInteger(n,rand);
		
		while(! (MillerRabinPrimeCheck(big,5) && FermatPrimalityTest(big,5) ) )
		{
			
			big = new BigInteger(n,rand); 
		}
		
		return big;
	}
	
	
	
	
	@Override
	public BigInteger[] GetPublicKey(BigInteger p , BigInteger q)
	{
		return new BigInteger[]{ p.multiply(q) , Select_e(p,q)};  
	}
	
	
	
	
	@Override
	public BigInteger[] GetPrivateKey(BigInteger p,BigInteger q,BigInteger e)
	{
		//temp = (p-1)*(q-1)
		BigInteger temp = (p.subtract(one)).multiply(q.subtract(one));
		
		BigInteger[] privateKey = ExtendedGCD(e,temp);
		
		//if d is positive , then d=d else d= d + (p-1)*(q-1).
		privateKey[0]=(privateKey[0].compareTo(zero)>0)?privateKey[0]:((p.subtract(one)).multiply(q.subtract(one))).add(privateKey[0]);
		
		return new BigInteger[]{p.multiply(q), privateKey[0] , privateKey[1] };
	}
	
	
	
	
	@Override
	public String GetCiphertext(String plaintext , BigInteger n , BigInteger e)
	{
		//the ciphertext to be returned. Initially empty.
		String ciphertext = "";
		
		for(int i=0; i<plaintext.length(); i++)
		{
			//get the tactical number of the current character.
			int TacticalNumber = plaintext.charAt(i);
			
			//convert the int to BigInteger.
			BigInteger number = BigInteger.valueOf(TacticalNumber);
			
			number=modPower(n,e,number);
			
			if(!(ciphertext.equals(""))){ ciphertext+="#";}
			ciphertext+=number.toString();
		}
		
		return ciphertext;
	}
	
	
		
	
	@Override
	public String GetPlaintext(String ciphertext , BigInteger n , BigInteger d)
	{
		//the plaintexttext to be returned. Initially empty.
		String plaintext = "";
		
		StringTokenizer tok = new StringTokenizer(ciphertext,"#");
		
		while(tok.hasMoreTokens())
		{
			BigInteger number = new BigInteger(tok.nextToken());
			
			number = modPower(n,d,number);
			
			int TacticalNumber = number.intValue();
			
			plaintext+=String.valueOf((char)TacticalNumber);
		}
		
		return plaintext;
	}
	
	
	
	
	/*
	 * Miller-Rabin primality test. It is a probabilistic primality test.
	 * Returns true if p is probable prime , or false if it is definite composite.
	 * size is number of times we want to check p for primality.
	 * Bigger size means more accurate results but more execution time...
	 */
	private boolean MillerRabinPrimeCheck(BigInteger p , int  size)
    {
		//if number is 1 then return no prime!
		if(p.equals(one)) return false;
		
    	BigInteger minusOne = p.subtract(one);
    	BigInteger q = p.subtract(one);
    	BigInteger t = q;
    	
    	for(int i = 2; i < size; i++)
    	{
    		//make sure i = p^x because then all powers of i are zero
    		if (BigInteger.valueOf(i).mod(p).compareTo(zero)!=0)
    		{
    			t = p.subtract(one);
    			BigInteger check = modPower(p,t,BigInteger.valueOf(i) );
    			
	    		if (check.compareTo(one)!=0 ) return false;
	    		
	    		while(t.mod(two).compareTo(zero)==0)
	    		{
	    			t = t.divide(two);
	    			
	    			//bool check = i^q mod p;
	    			check = modPower(p,t,BigInteger.valueOf(i));
	    			
	    			if (check.compareTo(one)!=0 && check.compareTo(minusOne)!=0)
	    			{
	    				return false;
	    			}
	    			
	    			if (check.compareTo(minusOne)==0)
	    			{
	    				break;
	    			}
	    		}
    		}
    	}
    	
    	return true;
    }
	
	
	
	
	/*
	 * Fermat primality test. It is a probabilistic primality test.
	 * Returns true if p is probable prime , or false if it is definite composite.
	 * size is number of times we want to check p for primality.
	 * Bigger size means more accurate results but more execution time...
	 */
    private boolean FermatPrimalityTest(BigInteger number , int threshold)
    {    
        //A randomly choosen number in space [1 , number-1].
        BigInteger a;
                   
        for(int i = 0; i<threshold; i++ )
        {
            a=new BigInteger(number.bitLength(),rand);
                
            //a must be in space [1,number-1].
            while(a.compareTo(one)<0 || a.compareTo(number)>=0)
            {
                a=new BigInteger(number.bitLength(),rand);
            }
                
            if( !(modPower(number,number.subtract(one),a).equals(one)) ) return false;
                
        }
            
        return true;
    }
	
    
    
    
    //Calculates power of (a^exp) % mod.
  	private BigInteger modPower(BigInteger mod, BigInteger exp, BigInteger a)
    {
  		if (exp.compareTo(one) == 0) return a.mod(mod);
      	if (exp.compareTo(zero) == 0) return one;
      	
      	if(exp.mod(two).compareTo(zero) == 0)
  		{
  			BigInteger x = modPower(mod,exp.divide(two),a);
  			return (x.multiply(x)).mod(mod) ;
  		}
  		
  		BigInteger x = modPower(mod,exp.subtract(one).divide(two),a);
  		
  		return (x.multiply(x).multiply(a)).mod(mod);	
  	}
  	
  	
  	
  	
  	//selects a suitable e.
  	private BigInteger Select_e(BigInteger p , BigInteger q)
  	{
  		ArrayList<BigInteger> list = new ArrayList<BigInteger>();
  			
  		//(p-1)*(q-1)
  		BigInteger temp =(p.subtract(one)).multiply(q.subtract(one));
  			
  		BigInteger threshold=new BigInteger("1000");
  			
  		//get a list of all suitable e's.
  		list=Get_All_e(temp,threshold);
  		
  		//while no suitable e's are found...
  		while(list.size()==0)
  		{
  			//if there are no suitable e's in the current space , double the search space.
  			threshold=threshold.add(threshold);
  				
  			list=Get_All_e(temp,threshold);
  		}
  			
  			
  		//if there are a lot of e's select one randomly
  		Random r = new Random();
  		
  		return list.get(r.nextInt(list.size()));
  	}
  	
  	
  	
  	
  	//returns a list that holds all the suitable e's.
  	private ArrayList<BigInteger> Get_All_e(BigInteger temp , BigInteger number)
  	{
  		ArrayList<BigInteger> list = new ArrayList<BigInteger>();
  		
  		//find all e's that satisfy 1<e<(p-1)(q-1) and GCD(e,(p-1)(q-1))=1.
  		//For time economy we only look in space (p-1)(q-1)-number<e<(p-1)(q-1).
  		for(BigInteger i = temp.subtract(number); i.compareTo(temp)<0; i=i.add(one))
  		{
  			if(i.compareTo(one)>0 && GCD(i,temp).equals(one) )  list.add(i);
  		}
  		
  		return list;
  	}
  	
  	
  	
  	
  	/*
  	 * Returns the pair (q,r) that satisfy Bezout's identity:  value*q + n*r = GCD(value,n).
  	 * In the special case that "value" and "n" are co-prime i.e. GCD(value , n) = 1 ,
  	 * then q is the modular inverse of "value" mod "n"
  	 */
  	private BigInteger[] ExtendedGCD(BigInteger value , BigInteger n)
  	{
  		if(n.equals(zero)) return new BigInteger[]{one,zero};
  			
  		BigInteger q = value.divide(n);
  		BigInteger r = value.mod(n);
  		BigInteger[] array = ExtendedGCD (n,r);
  		
  		return new BigInteger[]{array[1],array[0].subtract( q.multiply(array[1]) )};
  	}
}