package com.cividas.web.common.utils;

import java.util.Vector;

/**
 * Example:
 * 
 * P<UTOERIKSSON<<ANNA<MARIA<<<<<<<<<<<<<<<<<<<
 * L898902C<3UTO6908061F9406236ZE184226B<<<<<14
 * 
 * Passport Number: L898902C<
 * Check digit: 3
 * 
 * 
 * 1. ######### - Passport Number
 * 
 * This is the passport number, as assigned by the issuing country. Each country is free to assign numbers using any system it likes. If the number has non-letter or number characters they are replaced with the filler character <.
 * 
 * 
 * 
 * 
 * 2. C - Check digit
 * 
 * Check digits are calculated based on the previous field. Thus, the first check digit is based on the passport number, the next is based on the date of birth, the next on the expiration date, and the next on the personal number
 * 
 * 
 * 
 * 3. Check Digit Calculations
 * 
 * First, break the input into individual characteres and numbers.
 * 
 * Next, convert non-digits into numbers. A through Z are encoded to 10 through 25. The filler character < is encoded as 0.
 * <	A	B	C	D	E	F	G	H	I	J	K	L	M	N	O	P	Q	R	S	T	U	V	W	X	Y	Z
 * 0	10	11	12	13	14	15	16	17	18	19	20	21	22	23	24	25	26	27	28	29	30	31	32	33	34	35
 * 
 * Now, multiply each number by the corresponding weighting. The first digit is multipled by 7, the next by 3, and the next by 1. The pattern then repeats (7, 3, 1, 7, 3, 1, 7, 3, 1, etc).
 * 
 * Add up the results, then divide by 10. The remainder is the check digit.
 * 
 * As a special case, if the personal number on the second line is not used (and thus entirely filled with the filler character <), the check digit for that section can be replaced with the filler character <.
 * 
 * An example for the input AB2134:
 * Input:	A	 	B	 	2	 	1	 	3	 	4	 	<	 	<	 	<		
 * Value:	10	 	11	 	2	 	1	 	3	 	4	 	0	 	0	 	0		
 * Weight:	7	 	3	 	1	 	7	 	3	 	1	 	7	 	3	 	1		
 * Products:	70	 	33	 	2	 	7	 	9	 	4	 	0	 	0	 	0		
 * Sum:	70	+	33	+	2	+	7	+	9	+	4	+	0	+	0	+	0	=	125
 * 
 * Division: 125 ÷ 10 = 12, remainder 5 
 * 
 *
 */
public class PassportUtils {
	/**
	 * 9 digits/letters
	 * 1 control number
	 * 
	 * @param number
	 * @return
	 */
	public static boolean isPassportWellFormed(String number) {
		
		boolean wellFormed 		= false;
		
		char [] passport		= null;
		
		Integer controlNumber 			= null;		
		Vector<Integer> controlValues 	= new Vector<Integer>();
		Integer sumControlValues		= null;
		Integer calculatedCtrolNumber	= null;

		try {
			if (number!=null && number.length()>1 && number.length()<=10) {
				
				number=number.toUpperCase();
				
				passport = number.toCharArray();
				
				controlNumber = Character.getNumericValue(passport[passport.length-1]);
				
				controlValues = getNumericValues(passport);			
				controlValues = getControlValues(controlValues);
				sumControlValues = sumValues(controlValues);			 
				calculatedCtrolNumber = getCalculatedControlNumber(sumControlValues);
				
				if (calculatedCtrolNumber.equals(controlNumber)) {
					wellFormed = true;
				}
			}
		} catch (Exception e) {
			System.out.println("E_CHECKING_PASSPORT_NUMBER");
			e.printStackTrace();
			wellFormed = false;
		}
		
		return wellFormed;
	}
	
	/**
	 * Gets the numeric value of the letters
	 * 
	 * @param passport
	 * @return
	 */
	public static Vector<Integer> getNumericValues(char [] passport) throws Exception {
		
		Vector<Integer> control = new Vector<Integer>();
		char val;
		
		for (int i=0; i<passport.length-1; i++) {				
			val = passport[i];
			control.add(getNumericValue(val));
		}
		
		return control;
	}
	
	public static Integer getNumericValue(char c) throws Exception {
			
		Integer value = null;
		
		if (c=='<') {
			value = Integer.valueOf(0);
		}
		else {
			value = Integer.valueOf(Character.getNumericValue(c));
		}
		
		return value;
	}
	
	/**
	 * Adds all the numeric calculated control values
	 * 
	 * @param v
	 * @return
	 */
	public static Integer sumValues(Vector<Integer> v) throws Exception {
		
		Integer sum = Integer.valueOf(0);
		
		 for(Integer val : v) {
			
			 sum = sum + val;
		 }
		
		
		return sum;
	}
	
	/**
	 * Gets the calculated control values (obtained after multiplying each number by 7 or or 1, depending on its position)
	 * 
	 * @param i
	 * @return
	 */
	public static Integer getCalculatedControlNumber(Integer i) throws Exception {
		
		Integer cn = null;
		
		cn = i%10;
		
		return cn;
	}
	
	public static Vector<Integer> getControlValues(Vector<Integer> v) throws Exception {
		
		Vector<Integer> values = new Vector<Integer>();
		
		int multValue;
		
		for (int i=0; i<v.size(); i++) {
			
			multValue = getMultValue(i);
			values.add(v.elementAt(i)*multValue);
		}
		
		return values;
	}
	
	/**
	 * Calcula el valor por el que debemos multiplicar los valores de control dependiendo de su posición, 
	 * 	1(x7), 2(x3), 3(x1), 4(x7), 5(x3), 6(x1), ... 
	 * 
	 * @param n
	 * @return
	 * @throws Exception
	 */
	public static int getMultValue(int n) throws Exception {
		
		int val = 0;
		
		switch((n+1)%3) {
			case 0: val=1; break;
			case 1: val=7; break;
			case 2: val=3; break;
		}
		
		return val;
	}
}
