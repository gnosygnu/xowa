/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx;
public class GfoNde implements GfoInvkAble {
	public GfoFldList Flds() {return flds;} GfoFldList flds;
	public HashAdp EnvVars() {return envVars;} HashAdp envVars = HashAdp_.new_();
	public String Name() {return name;} public GfoNde Name_(String v) {name = v; return this;} private String name;
	public Object ReadAt(int i)					{ChkIdx(i); return ary[i];}
	public void WriteAt(int i, Object val)		{ChkIdx(i); ary[i] = val;}
	public Object Read(String key)				{int i = IndexOfOrFail(key); return ary[i];}
	public void Write(String key, Object val)	{int i = IndexOfOrFail(key); ary[i] = val;}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {return Read(k);}

	public GfoNdeList Subs() {return subs;} GfoNdeList subs = GfoNdeList_.new_();
	public GfoFldList SubFlds() {return subFlds;} GfoFldList subFlds = GfoFldList_.new_();
	public void XtoStr_wtr(DataWtr wtr) {XtoStr_wtr(this, wtr);}// TEST
	public String XtoStr() {
		String_bldr sb = String_bldr_.new_();
		for (int i = 0; i < aryLen; i++) {
			String key = i >= flds.Count()	? "<< NULL " + i + " >>" : flds.FetchAt(i).Key();
			String val = i >= aryLen		? "<< NULL " + i + " >>" : Object_.XtoStr_OrNullStr(ary[i]);
			sb.Add(key).Add("=").Add(val);
		}
		return sb.XtoStr();
	}
	int IndexOfOrFail(String key) {
		int i = flds.IndexOf(key);
		if ((i < 0 || i >= aryLen)) throw Err_.new_("field name not found").Add("name", key).Add("index", i).Add("count", this.Flds().Count());
		return i;
	}
	boolean ChkIdx(int i) {if (i < 0 || i >= aryLen) throw Err_.missing_idx_(i, aryLen); return true;}
	Object[] ary; int type; int aryLen;
	@gplx.Internal protected GfoNde(int type, String name, GfoFldList flds, Object[] ary, GfoFldList subFlds, GfoNde[] subAry) {
		this.type = type; this.name = name; this.flds = flds; this.ary = ary; aryLen = Array_.Len(ary); this.subFlds = subFlds;
		for (GfoNde sub : subAry)
			subs.Add(sub);
	}
	static void XtoStr_wtr(GfoNde nde, DataWtr wtr) {
		if (nde.type == GfoNde_.Type_Leaf) {
			wtr.WriteLeafBgn("flds");
			for (int i = 0; i < nde.ary.length; i++)
				wtr.WriteData(nde.Flds().FetchAt(i).Key(), nde.ReadAt(i));
			wtr.WriteLeafEnd();
		}
		else {
			if (nde.type == GfoNde_.Type_Node)			// never write node info for root
				wtr.WriteTableBgn(nde.Name(), nde.SubFlds());
			for (int i = 0; i < nde.Subs().Count(); i++) {
				GfoNde sub = nde.Subs().FetchAt_asGfoNde(i);
				XtoStr_wtr(sub, wtr);
			}
			if (nde.type == GfoNde_.Type_Node)
				wtr.WriteNodeEnd();
		}
	}
}
