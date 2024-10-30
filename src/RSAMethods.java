
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class RSAMethods {
	
	private static List<Integer> numberToBase(BigInteger n, int base) {
		List<Integer> digits = new ArrayList<>();
		BigInteger b = BigInteger.valueOf(base);
		if (n.equals(BigInteger.ZERO)) {
			digits.add(0);
			return digits;
		}
		while (n.compareTo(BigInteger.ZERO) > 0) {
			digits.add(0, Integer.parseInt(n.remainder(b).toString()));
			n = n.divide(b);
		}
		return digits;
	}
	
	private static List<Integer> stringToBase(String s, int base) {
		List<Integer> digits = new ArrayList<>();
		for (int i = 0; i < s.length(); i++) {
			digits.add((int) s.charAt(i) - Byte.MAX_VALUE + base);
		}
		return digits;
	}
	
	public static String numberToString(BigInteger n, int base) {
		List<Integer> digits = numberToBase(n, base);
		String result = "";
		for (int i = 0; i < digits.size(); i++) {
			result += ((char) ((int) digits.get(i) + Byte.MAX_VALUE - base));
		}
//		System.out.println(stringToNumber(result));
		return result;
	}
	
	public static BigInteger stringToNumber(String s, int base) {
		List<Integer> digits = stringToBase(s, base);
		BigInteger sum = BigInteger.ZERO;
		BigInteger b = BigInteger.valueOf(base);
		for (int i = 0; i < digits.size(); i++) {
			sum = sum.add(BigInteger.valueOf(digits.get(i)).multiply(b.pow(digits.size() - i - 1)));
		}
		return sum;
	}
	
	public static String encryptString(String s, BigInteger N, BigInteger e) {
		BigInteger stringNum = stringToNumber(s, 95);
//		System.out.println(stringNum);
		BigInteger encrypted = stringNum.modPow(e, N);
		return numberToString(encrypted, 95);
	}
	
	public static String decryptString(String s, BigInteger N, BigInteger d) {
		BigInteger stringNum = stringToNumber(s, 95);
		BigInteger decrypted = stringNum.modPow(d, N);
		return numberToString(decrypted, 95);
	}

//	public static void main(String[] args) {
//		String s = "What is an algorithm that solves the maximum flow problem in O(VE) time?";
//		s = s.replace(String.valueOf((char) (8217)), String.valueOf((char) (39)));
//		
//		Random r = new Random(0);
//		BigInteger p = BigInteger.probablePrime(2048, r);
//		BigInteger q = BigInteger.probablePrime(2048, r);
//		BigInteger N = p.multiply(q);
//		BigInteger M = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
//		
//		BigInteger e = BigInteger.valueOf(65537);
//		BigInteger d = e.modInverse(M);
//		
//		System.out.println("p: " + p);
//		System.out.println("q: " + q);
//		System.out.println("N: " + N);
//		System.out.println("(p - 1)(q - 1): " + M);
//		System.out.println("e: " + e);
//		System.out.println("d: " + d + "\n");
//		
//		System.out.println("Original Message  : " + s);
//		String encryptedString = encryptString(s, N, e);
//		System.out.println("Encrypted Message : " + encryptedString);
//		System.out.println("Decrypted Message : " + decryptString(encryptedString, N, d) + "\n");
//	}
}
