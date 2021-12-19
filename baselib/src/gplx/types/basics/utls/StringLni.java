package gplx.types.basics.utls;
import java.util.Objects;
public class StringLni {
	public static final Class<?> ClsRefType = String.class;
	public static final String ClsValName = "string";
	public static final int FindNone = -1;
	public static final int PosNeg1 = -1;
	public static final String
		Empty = "",
		Tab = "\t",
		Nl = "\n",
		CrLf = "\r\n",
		NullMark = "<<NULL>>";

	public static final String[] AryEmpty = new String[0];
	public static final boolean Eq(String lhs, String rhs)                            {return Objects.equals(lhs, rhs);}
	public static final boolean EqNot(String lhs, String rhs)                         {return !Objects.equals(lhs, rhs);}
	public static final int Len(String s)                                             {return s.length();}
	public static final boolean IsNullOrEmpty(String s)                               {return s == null || s.length() == 0;}
	public static final boolean IsNotNullOrEmpty(String s)                            {return s != null && s.length() >  0;}
	public static final boolean Has(String s, String find)                            {return s.indexOf(find) != FindNone;}
	public static final boolean HasAtBgn(String s, String v)                          {return s.startsWith(v);}
	public static final boolean HasAtEnd(String s, String v)                          {return s.endsWith(v);}
	public static final char CharAt(String s, int i)                                  {return s.charAt(i);}
	public static final int CodePointAt(String s, int i)                              {return s.codePointAt(i);}
	public static final int FindFwd(String s, String find)                            {return s.indexOf(find);}
	public static final int FindFwd(String s, String find, int pos)                   {return s.indexOf(find, pos);}
	public static final int FindBwd(String s, String find)                            {return s.lastIndexOf(find);}
	public static final int FindBwd(String s, String find, int pos)                   {return s.lastIndexOf(find, pos);}
	public static final String Cast(Object v)                                         {return (String)v;}
	public static final String CastOrNull(Object obj)                                 {return obj instanceof String ? (String)obj : null;}
	public static final String Lower(String s)                                        {return s.toLowerCase();}
	public static final String Upper(String s)                                        {return s.toUpperCase();}
	public static final String CaseNormalize(boolean caseMatch, String s)             {return caseMatch ? s : Lower(s);}
	public static final String Trim(String s)                                         {return s.trim();}
	public static final String Mid(String s, int bgn)                                 {return s.substring(bgn);}
	public static final String MidByLen(String s, int bgn, int len)                   {return s.substring(bgn, bgn + len);}
	public static final String Replace(String s, String find, String repl)            {return s.replace(find, repl);}
	public static final String NewByBry(byte[] v)                                     {return v == null ? null : new String(v);}
	public static final char[] ToCharAry(String s)                                    {return s.toCharArray();}
	public static final String NewA7(byte[] v) {return v == null ? null : NewA7(v, 0, v.length);}
	public static final String NewA7(byte[] v, int bgn, int end) {
		try {
			return v == null
				? null
				: new String(v, bgn, end - bgn, "ASCII");
		}
		catch (Exception e) {throw ErrLni.NewMsg("unsupported encoding; bgn=" + bgn + " end=" + end);}
	}
	public static final String NewU8ByLen(byte[] v, int bgn, int len) {
		int vLen = v.length;
		if (bgn + len > vLen) len = vLen - bgn;
		return NewU8(v, bgn, bgn + len);
	}
	public static final String NewU8(byte[] v) {return v == null ? null : NewU8(v, 0, v.length);}
	public static final String NewU8(byte[] v, int bgn, int end) {
		try {
			return v == null
				? null
				: new String(v, bgn, end - bgn, "UTF-8");
		}
		catch (Exception e) {throw ErrLni.NewMsg("unsupported encoding; bgn=" + bgn + " end=" + end);}
	}
}
