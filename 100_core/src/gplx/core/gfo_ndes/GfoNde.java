/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.gfo_ndes; import gplx.*; import gplx.core.*;
import gplx.core.strings.*; import gplx.core.stores.*;
public class GfoNde implements Gfo_invk {
	public GfoFldList Flds() {return flds;} GfoFldList flds;
	public Hash_adp EnvVars() {return envVars;} Hash_adp envVars = Hash_adp_.New();
	public String Name() {return name;} public GfoNde Name_(String v) {name = v; return this;} private String name;
	public Object ReadAt(int i)					{ChkIdx(i); return ary[i];}
	public void WriteAt(int i, Object val)		{ChkIdx(i); ary[i] = val;}
	public Object Read(String key)				{int i = IndexOfOrFail(key); return ary[i];}
	public void Write(String key, Object val)	{int i = IndexOfOrFail(key); ary[i] = val;}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {return Read(k);}

	public GfoNdeList Subs() {return subs;} GfoNdeList subs = GfoNdeList_.new_();
	public GfoFldList SubFlds() {return subFlds;} GfoFldList subFlds = GfoFldList_.new_();
	public void XtoStr_wtr(DataWtr wtr) {XtoStr_wtr(this, wtr);}// TEST
	public String To_str() {
		String_bldr sb = String_bldr_.new_();
		for (int i = 0; i < aryLen; i++) {
			String key = i >= flds.Count()	? "<< NULL " + i + " >>" : flds.Get_at(i).Key();
			String val = i >= aryLen		? "<< NULL " + i + " >>" : Object_.Xto_str_strict_or_null_mark(ary[i]);
			sb.Add(key).Add("=").Add(val);
		}
		return sb.To_str();
	}
	int IndexOfOrFail(String key) {
		int i = flds.Idx_of(key);
		if ((i < 0 || i >= aryLen)) throw Err_.new_wo_type("field name not found", "name", key, "index", i, "count", this.Flds().Count());
		return i;
	}
	boolean ChkIdx(int i) {if (i < 0 || i >= aryLen) throw Err_.new_missing_idx(i, aryLen); return true;}
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
				wtr.WriteData(nde.Flds().Get_at(i).Key(), nde.ReadAt(i));
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
