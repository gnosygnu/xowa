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
package gplx.langs.jsons; import gplx.*; import gplx.langs.*;
public class Json_ary extends Json_itm_base implements Json_grp {
	public Json_ary(int src_bgn, int src_end) {this.Ctor(src_bgn, src_end);}
	@Override public byte Tid() {return Json_itm_.Tid__ary;}
	public void Src_end_(int v) {this.src_end = v;}
	@Override public Object Data() {return null;}
	@Override public byte[] Data_bry() {return null;}
	public int Len() {return subs_len;} private int subs_len = 0, subs_max = 0;
	public Json_nde Get_at_as_nde(int i) {
		Json_itm rv = subs[i]; if (rv.Tid() != Json_itm_.Tid__nde) throw Err_.new_("json", "itm is not nde", "type", rv.Tid(), "i", i);
		return (Json_nde)rv;
	}
	public Json_itm Get_at(int i) {return subs[i];}
	public Json_nde Get_as_nde(int i) {return Json_nde.cast(subs[i]);}
	public Json_ary Add_many(Json_itm... ary) {
		int len = ary.length;
		for (int i = 0; i < len; i++)
			Add(ary[i]);
		return this;
	}
	public void Add(Json_itm itm) {
		int new_len = subs_len + 1;
		if (new_len > subs_max) {	// ary too small >>> expand
			subs_max = new_len * 2;
			Json_itm[] new_subs = new Json_itm[subs_max];
			Array_.Copy_to(subs, 0, new_subs, 0, subs_len);
			subs = new_subs;
		}
		subs[subs_len] = itm;
		subs_len = new_len;
	}
	@Override public void Print_as_json(Bry_bfr bfr, int depth) {
		if (subs_len == 0) {	// empty grp; print on one line (rather than printing across 3)
			bfr.Add_byte(Byte_ascii.Brack_bgn).Add_byte(Byte_ascii.Brack_end);
			return;
		}
		bfr.Add_byte_nl();
		Json_grp_.Print_indent(bfr, depth);
		bfr.Add_byte(Byte_ascii.Brack_bgn).Add_byte(Byte_ascii.Space);
		for (int i = 0; i < subs_len; i++) {
			if (i != 0) {
				Json_grp_.Print_nl(bfr); Json_grp_.Print_indent(bfr, depth);
				bfr.Add_byte(Byte_ascii.Comma).Add_byte(Byte_ascii.Space);
			}
			subs[i].Print_as_json(bfr, depth + 1);
		}
		Json_grp_.Print_nl(bfr); Json_grp_.Print_indent(bfr, depth);
		bfr.Add_byte(Byte_ascii.Brack_end).Add_byte_nl();
	}
	public byte[][] Xto_bry_ary() {
		if (subs_len == 0) return Bry_.Ary_empty;
		byte[][] rv = new byte[subs_len][];
		for (int i = 0; i < subs_len; ++i)
			rv[i] = subs[i].Data_bry();
		return rv;
	}
	private Json_itm[] subs = Json_itm_.Ary_empty;
	public static Json_ary cast_or_null(Json_itm v) {return v == null || v.Tid() != Json_itm_.Tid__ary ? null : (Json_ary)v;}
	public static Json_ary cast(Json_itm v) {
		if (v == null || v.Tid() != Json_itm_.Tid__ary) throw Err_.new_("json", "itm is not array");
		return (Json_ary)v;
	}
}
