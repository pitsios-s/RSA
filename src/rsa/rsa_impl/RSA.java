package rsa.rsa_impl;

import java.math.BigInteger;


/**
 * contains all the methods that need to be implemented for the RSA algorithm.
 * 
 * @author Stamatis Pitsios
 */
public interface RSA
{
	/**
	 * Uses Euclidean algorithm for finding the greatest common divisor of b1 and b2.
	 */
	public BigInteger GCD(BigInteger b1 , BigInteger b2);
		
	/**
	 * Returns true if "number" is prime or probable prime and false if it is composite.
	 * Naive methods , Probabilistic tests or Fast deterministic tests can be used for this implementation,
	 * concidering the accuracy and/or running time that we tolerate.
	 */
	public boolean IsPrime(BigInteger number);
	
	/**
	 * Returns a probable prime with the size of n bits.
	 */
	public BigInteger GetProbablePrime(int n);
	
	/**
	 * Returns the public key pair (n,e).
	 * result[0] = n , result[1] = e.
	 */
	public BigInteger[] GetPublicKey(BigInteger p , BigInteger q);
	
	/**
	 * Returns the private key (n,d)
	 * result[0] = n , result[1] = d.
	 * Extended Euclidean Algorithm is used for the computation of d.
	 */
	public BigInteger[] GetPrivateKey(BigInteger p,BigInteger q,BigInteger e);
	
	/**
	 * Returns the ciphertext from the given plaintext, and the public key pair(n,e).
	 */
	public String GetCiphertext(String plaintext , BigInteger n , BigInteger e);
	
	/**
	 * Returns the plaintext from the given ciphertext and the private key pair(n,d).
	 */
	 public String GetPlaintext(String ciphertext , BigInteger n , BigInteger d);
}