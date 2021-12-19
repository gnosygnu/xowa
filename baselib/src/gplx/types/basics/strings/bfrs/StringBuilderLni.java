package gplx.types.basics.strings.bfrs;
import java.lang.StringBuilder;
public class StringBuilderLni {
	private StringBuilder sb = new StringBuilder();
	public final int Len() {return sb.length();}
	protected final void LniDel(int bgn, int len)               {sb.delete(bgn, len);}
	protected final void LniAddByte(byte i)                     {sb.append(i);}
	public final void LniAddChar(char c)                        {sb.append(c);}
	protected final void LniAdd(int i)                          {sb.append(i);}
	protected final void LniAddLong(long i)                     {sb.append(i);}
	public final void LniAdd(String s)                          {sb.append(s);}
	protected final void LniAddObj(Object o)                    {sb.append(o);}
	protected final void LniAddMid(String s, int bgn, int len)  {sb.append(s, bgn, len);}
	protected final void LniEnsureCapacity(int capacity)        {sb.ensureCapacity(capacity);}
	public final String ToStr()                                 {return sb.toString();}
	@Override public final String toString()                    {return sb.toString();}
}
