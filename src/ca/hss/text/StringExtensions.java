/**
 * StringExtensions.java
 *
 * Copyright 2015-2018 Heartland Software Solutions Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the license at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the LIcense is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ca.hss.text;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import ca.hss.general.OutVariable;

/**
 * Helper methods for manipulating strings.
 * 
 * @author Travis
 */
public abstract class StringExtensions {
	
	/**
	 * Capitalize an entire string. Converts all characters to lower
	 * case before doing the capitalization.
	 * 
	 * @param str
	 * @return
	 */
	@Deprecated
	public static String capitalizeFully(String str) {
		return capitalizeFully(str, null);
	}
	
	/**
	 * Capitalize all characters after a given delimiter. Converts all characters to lower
	 * case before doing the capitalization.
	 * 
	 * @param str
	 * @param delimiters
	 * @return
	 */
	@Deprecated
	public static String capitalizeFully(String str, final char... delimiters) {
		final int delimLen = delimiters == null ? -1 : delimiters.length;
		if (str == null || str.isEmpty() || delimLen == 0)
			return str;
		str = str.toLowerCase();
		return capitalize(str, delimiters);
	}

	/**
	 * Capitalize each word in a string.
	 * @param input The string to capitalize the words in.
	 * @return The same string as input but with each word capitalized.
	 */
	public static String capitalizeFully(final String input) {
		String[] lower = input.toLowerCase().split(" ");
		StringBuilder result = new StringBuilder();
		
		for(int i = 0; i < lower.length; i++) {
			result.append(Character.toUpperCase(lower[i].charAt(0)));
			result.append(lower[i].substring(1, lower[i].length()));
			result.append(" ");
		}
		
		return result.toString().trim();
	}

	/**
	 * Capitalize all characters after a given delimiter.
	 * 
	 * @param str
	 * @param delimiters
	 * @return
	 */
	@Deprecated
	public static String capitalize(final String str, final char... delimiters) {
		final int delimLen = delimiters == null ? -1 : delimiters.length;
		if (str == null || str.isEmpty() || delimLen == 0)
			return str;
		final char[] buffer = str.toCharArray();
		boolean capitalizeNext = true;
		for (int i = 0; i < buffer.length; i++) {
			final char ch = buffer[i];
			if (isDelimiter(ch, delimiters)) {
				capitalizeNext = true;
			} else if (capitalizeNext) {
				buffer[i] = Character.toTitleCase(ch);
				capitalizeNext = false;
			}
		}
		return new String(buffer);
	}

	@Deprecated
	private static boolean isDelimiter(final char ch, final char[] delimiters) {
		if (delimiters == null) {
			return Character.isWhitespace(ch);
		}
		for (final char delimiter : delimiters) {
			if (ch == delimiter) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Mimics the behaviour of C++'s strok_s function.
	 * 
	 * @param strToken
	 * @param strDelimit
	 * @param context
	 * @return
	 */
	public static String strtok_s(String strToken, String strDelimit, OutVariable<StringTokenizer> context) {
		if (strToken != null) {
			context.value = new StringTokenizer(strToken, strDelimit, false);
			if (context.value.hasMoreElements())
				return context.value.nextToken();
		}
		else if (context.value != null) {
			if (context.value.hasMoreElements())
				return context.value.nextToken(strDelimit);
		}
		return null;
	}

	@Deprecated
	public static String strcpy_strip_s(String _Src, String _Strip) {
		String _Dst = _Src;
		for (int i = 0; i < _Strip.length(); i++) {
			_Dst = _Dst.replace(String.valueOf(_Strip.charAt(i)), "");
		}
		return _Dst;
	}

	public static int _stscanf_s(String str, String pattern, List<OutVariable<Object>> vars) {
		return sscanf(str, pattern, vars);
	}

	public static int sscanf(String str, String pattern, List<OutVariable<Object>> vars) {
		List<String> pattlist = new ArrayList<String>();
		String patt = "";
		int pattcount = 0;
		for (int i = 0; i < pattern.length(); i++) {
			char c = pattern.charAt(i);
			if (c == '%') {
				if (patt.length() > 0) {
					pattlist.add(patt);
					patt = "";
				}
				char c2 = pattern.charAt(++i);
				if (c2 == 'd') {
					pattlist.add("%d");
					pattcount++;
				}
				else if (c2 == 'i') {
					pattlist.add("%i");
					pattcount++;
				}
				else if (c2 == 'f') {
					pattlist.add("%f");
					pattcount++;
				}
				else if (c2 == 'o') {
					pattlist.add("%o");
					pattcount++;
				}
				else if (c2 == 's') {
					pattlist.add("%s");
					pattcount++;
				}
				else if (c2 == 'l') {
					char c3 = pattern.charAt(++i);
					if (c3 == 'f') {
						pattlist.add("%lf");
						pattcount++;
					}
					else if (c3 == 'd') {
						pattlist.add("%ld");
						pattcount++;
					}
					else if (c3 == 'i') {
						pattlist.add("%ld");
						pattcount++;
					}
					else if (c3 == 'l') {
						char c4 = pattern.charAt(++i);
						if (c4 == 'f') {
							pattlist.add("%Lf");
							pattcount++;
						}
					}
				}
				else if (c2 == 'L') {
					char c3 = pattern.charAt(++i);
					if (c3 == 'f') {
						pattlist.add("%Lf");
						pattcount++;
					}
				}
				else if (c2 == 'h') {
					char c3 = pattern.charAt(++i);
					if (c3 == 'h') {
						char c4 = pattern.charAt(++i);
						if (c4 == 'i') {
							pattlist.add("%hhi");
							pattcount++;
						}
						else if (c4 == 'd') {
							pattlist.add("%hhd");
							pattcount++;
						}
					}
					else if (c3 == 'i') {
						pattlist.add("%hi");
						pattcount++;
					}
					else if (c3 == 'd') {
						pattlist.add("%hd");
						pattcount++;
					}
				}
				else if (c2 == '%')
					pattlist.add("%");
				else
					throw new IllegalArgumentException();
			}
			else
				patt += c;
		}
		if (patt.length() > 0)
			pattlist.add(patt);
		if (pattcount > vars.size())
			return -1;
		int varindex = 0;
		int strindex = 0;
		for (String p : pattlist) {
			if (p.equals("%d") || p.equals("%i")) {
				String it = "";
				char t = str.charAt(strindex);
				if (!Character.isDigit(t))
					return -1;
				while (Character.isDigit(t) && t != '.' && t != ',') {
					it += t;
					if (strindex < (str.length() - 1))
						t = str.charAt(++strindex);
					else
						t = 'h';
				}
				int radix = 10;
				if (p.equals("%i")) {
					if (it.startsWith("0x"))
						radix = 16;
					else if (it.startsWith("0"))
						radix = 8;
				}
				Integer in = Integer.parseInt(it, radix);
				vars.get(varindex++).value = in;
			}
			else if (p.equals("%ld") || p.equals("%li")) {
				String it = "";
				char t = str.charAt(strindex);
				if (!Character.isDigit(t))
					return -1;
				while (Character.isDigit(t) && t != '.' && t != ',') {
					it += t;
					if (strindex < (str.length() - 1))
						t = str.charAt(++strindex);
					else
						t = 'h';
				}
				int radix = 10;
				if (p.equals("%li")) {
					if (it.startsWith("0x"))
						radix = 16;
					else if (it.startsWith("0"))
						radix = 8;
				}
				Long in = Long.parseLong(it, radix);
				vars.get(varindex++).value = in;
			}
			else if (p.equals("%hd") || p.equals("%hi")) {
				String it = "";
				char t = str.charAt(strindex);
				if (!Character.isDigit(t))
					return -1;
				while (Character.isDigit(t) && t != '.' && t != ',') {
					it += t;
					if (strindex < (str.length() - 1))
						t = str.charAt(++strindex);
					else
						t = 'h';
				}
				int radix = 10;
				if (p.equals("%hi")) {
					if (it.startsWith("0x"))
						radix = 16;
					else if (it.startsWith("0"))
						radix = 8;
				}
				Short in = Short.parseShort(it, radix);
				vars.get(varindex++).value = in;
			}
			else if (p.equals("%hhd") || p.equals("%hhi")) {
				String it = "";
				char t = str.charAt(strindex);
				if (!Character.isDigit(t))
					return -1;
				while (Character.isDigit(t) && t != '.' && t != ',') {
					it += t;
					if (strindex < (str.length() - 1))
						t = str.charAt(++strindex);
					else
						t = 'h';
				}
				int radix = 10;
				if (p.equals("%hi")) {
					if (it.startsWith("0x"))
						radix = 16;
					else if (it.startsWith("0"))
						radix = 8;
				}
				Byte in = Byte.parseByte(it, radix);
				vars.get(varindex++).value = in;
			}
			else if (p.equals("%f")) {
				String it = "";
				boolean foundSplitter = false;
				char t = str.charAt(strindex);
				if (!Character.isDigit(t) && t != '.' && t != ',')
					return -1;
				while (Character.isDigit(t) || t == '.' || t == ',') {
					if (t == '.' || t == ',') {
						if (foundSplitter)
							break;
						foundSplitter = true;
					}
					it += t;
					if (strindex < (str.length() - 1))
						t = str.charAt(++strindex);
					else
						t = 'h';
				}
				Float d = Float.parseFloat(it);
				vars.get(varindex++).value = d;
			}
			else if (p.equals("%lf")) {
				String it = "";
				boolean foundSplitter = false;
				char t = str.charAt(strindex);
				if (!Character.isDigit(t) && t != '.' && t != ',')
					return -1;
				while (Character.isDigit(t) || t == '.' || t == ',') {
					if (t == '.' || t == ',') {
						if (foundSplitter)
							break;
						foundSplitter = true;
					}
					it += t;
					if (strindex < (str.length() - 1))
						t = str.charAt(++strindex);
					else
						t = 'h';
				}
				Double d = Double.parseDouble(it);
				vars.get(varindex++).value = d;
			}
			else if (p.equals("%Lf")) {
				String it = "";
				boolean foundSplitter = false;
				char t = str.charAt(strindex);
				if (!Character.isDigit(t) && t != '.' && t != ',')
					return -1;
				while (Character.isDigit(t) || t == '.' || t == ',') {
					if (t == '.' || t == ',') {
						if (foundSplitter)
							break;
						foundSplitter = true;
					}
					it += t;
					if (strindex < (str.length() - 1))
						t = str.charAt(++strindex);
					else
						t = 'h';
				}
				BigDecimal d = new BigDecimal(it);
				vars.get(varindex++).value = d;
			}
			else if (p.equals("%s")) {
				String it = "";
				char t = str.charAt(strindex);
				if (Character.isWhitespace(t))
					return -1;
				while (!Character.isWhitespace(t)) {
					it += t;
					if (strindex < (str.length() - 1))
						t = str.charAt(++strindex);
					else
						t = ' ';
				}
				vars.get(varindex++).value = it;
			}
			else {
				strindex += p.length();
			}
			if (strindex >= (str.length() - 1))
				break;
		}
		return varindex;
	}
	
	@Deprecated
	public static int strstr(String val1, String val2) {
		return val1.indexOf(val2);
	}
	
	@Deprecated
	public static long strtol(String val, OutVariable<Integer> lastPos, int base) {
		if (val.length() < 1 || base < Character.MIN_RADIX || base > Character.MAX_RADIX) {
			if (lastPos != null)
				lastPos.value = null;
			return 0;
		}
		char[] array = val.toLowerCase().toCharArray();
		int start = 0;
		while (start < array.length && (array[start] == ' ' || array[start] == '\t')) start++;
		if (start == array.length) {
			if (lastPos != null)
				lastPos.value = null;
			return 0;
		}
		boolean positive = array[start] != '-';
		if (array[start] == '-' || array[start] == '+')
			start++;
		int end = start;
		for (; end < array.length; end++) {
			try {
				Integer.parseInt(Character.toString(array[end]), base);
			}
			catch (NumberFormatException ex) {
				break;
			}
		}
		int length = end - start;
		if (length < 1) {
			if (lastPos != null)
				lastPos.value = null;
			return 0;
		}
		if (lastPos != null)
			lastPos.value = end;
		long result = Long.parseLong(new String(array, start, length), base);
		if (!positive)
			return -result;
		return result;
	}
}
