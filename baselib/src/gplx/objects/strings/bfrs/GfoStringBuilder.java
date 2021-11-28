package gplx.objects.strings.bfrs;

import gplx.objects.strings.String_;

public class GfoStringBuilder {
    private final StringBuilder sb = new StringBuilder();
    public int Len() {return sb.length();}
    public GfoStringBuilder Clear() {
        sb.delete(0, sb.length());
        return this;
    }
    public GfoStringBuilder AddNl() {return Add("\n");}
    public GfoStringBuilder Add(int v)       {sb.append(v); return this;}
    public GfoStringBuilder Add(String v)    {sb.append(v); return this;}
    public GfoStringBuilder Add(char v)      {sb.append(v); return this;}
    public GfoStringBuilder AddObj(Object v) {sb.append(v); return this;}
	public GfoStringBuilder AddMid(String s, int bgn)          {sb.append(s, bgn, s.length()); return this;}
	public GfoStringBuilder AddMid(String s, int bgn, int end) {sb.append(s, bgn, end); return this;}
    public GfoStringBuilder AddFmt(String fmt, Object... args) {
        sb.append(String_.Format(fmt, args));
        return this;
    }
	public GfoStringBuilder AddRepeat(char c, int repeat) {
		sb.ensureCapacity(this.Len() + repeat);
		for (int i = 0; i < repeat; i++)
			sb.append(c);
		return this;
	}
    public String ToStr() {return sb.toString();}

    @Override
    public String toString() {
        return sb.toString();
    }
}
