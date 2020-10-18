/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.langs.jsons;

import gplx.Bry_;
import gplx.Bry_bfr;
import gplx.Bry_bfr_;
import gplx.Err_;
import gplx.String_;
import gplx.core.primitives.Gfo_number_parser;

public class Json_doc {
	private final byte[][] tmp_qry_bry = new byte[1][];
	public void Ctor(byte[] src, Json_grp new_root) {
		this.src = src;
		this.root_grp = new_root;
		switch (root_grp.Tid()) {
			case Json_itm_.Tid__nde:	this.root_ary = null; this.root_nde = (Json_nde)root_grp; break;
			case Json_itm_.Tid__ary:	this.root_nde = null; this.root_ary = (Json_ary)root_grp; break;
			default:					throw Err_.new_unhandled(root_grp.Tid());
		}
	}
	public byte[] Src() {return src;} private byte[] src;
	public Json_grp Root_grp() {return root_grp;} private Json_grp root_grp;
	public Json_nde Root_nde() {return root_nde;} private Json_nde root_nde;
	public Json_ary Root_ary() {return root_ary;} private Json_ary root_ary;
	public Bry_bfr Bfr() {return bfr;} private final Bry_bfr bfr = Bry_bfr_.New();
	public Gfo_number_parser Utl_num_parser() {return utl_num_parser;} private final Gfo_number_parser utl_num_parser = new Gfo_number_parser();
	public byte[] Tmp_u8_bry() {return tmp_u8_bry;} private final byte[] tmp_u8_bry = new byte[6];	// tmp bry[] for decoding sequences like \u0008
	public byte[] Get_val_as_bry_or(byte[]   qry_bry, byte[] or) {tmp_qry_bry[0] = qry_bry; return Get_val_as_bry_or(tmp_qry_bry, or);}
	public byte[] Get_val_as_bry_or(byte[][] qry_bry, byte[] or) {
		Json_itm nde = Find_nde(root_nde, qry_bry, qry_bry.length - 1, 0);
		return nde == null || nde.Tid() != Json_itm_.Tid__str ? or : nde.Data_bry();
	}
	public String Get_val_as_str_or(byte[]   qry_bry, String or) {tmp_qry_bry[0] = qry_bry; return Get_val_as_str_or(tmp_qry_bry, or);}
	public String Get_val_as_str_or(byte[][] qry_bry, String or) {
		Json_itm nde = Find_nde(root_nde, qry_bry, qry_bry.length - 1, 0);
		return nde == null || nde.Tid() != Json_itm_.Tid__str ? or : (String)nde.Data();
	}
	public int Get_val_as_int_or(byte[]   qry_bry, int or) {tmp_qry_bry[0] = qry_bry; return Get_val_as_int_or(tmp_qry_bry, or);}
	public int Get_val_as_int_or(byte[][] qry_bry, int or) {
		Json_itm nde = Find_nde(root_nde, qry_bry, qry_bry.length - 1, 0);
		return nde == null || nde.Tid() != Json_itm_.Tid__int ? or : Bry_.To_int(nde.Data_bry());
	}
	public Json_grp Get_grp(byte[] qry_bry) {
		tmp_qry_bry[0] = qry_bry;
		Json_itm rv = Find_nde(root_nde, tmp_qry_bry, 0, 0); if (rv == null) return null;
		return (Json_grp)rv;
	}
	public Json_grp Get_grp_many(String... qry_ary) {return Get_grp_many(Bry_.Ary(qry_ary));}
	public Json_grp Get_grp_many(byte[]... qry_bry) {
		Json_itm rv = Find_nde(root_nde, qry_bry, qry_bry.length - 1, 0); if (rv == null) return null;
		return (Json_grp)rv;
	}
	public Json_itm Find_nde(byte[] key) {
		tmp_qry_bry[0] = key;
		return Find_nde(root_nde, tmp_qry_bry, 0, 0);
	}
	private Json_itm Find_nde(Json_nde owner, byte[][] paths, int paths_last, int paths_idx) {
		byte[] path = paths[paths_idx];
		int subs_len = owner.Len();
		for (int i = 0; i < subs_len; i++) {
			Json_kv itm = Json_kv.Cast(owner.Get_at(i)); if (itm == null) continue;	// ignore simple props, arrays, ndes
			if (!itm.Key_eq(path)) continue;
			if (paths_idx == paths_last) return itm.Val();
			Json_nde sub_nde = Json_nde.Cast(itm.Val()); if (sub_nde == null) return null;	// match, but has not a nde; exit
			return Find_nde(sub_nde, paths, paths_last, paths_idx + 1);
		}
		return null;
	}
	public static String Make_str_by_apos(String... ary) {return String_.Replace(String_.Concat_lines_nl_skip_last(ary), "'", "\"");}
	public static String[] Make_str_ary_by_apos(String... ary) {
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			String itm = ary[i];
			if (String_.Has(itm, "'"))
				ary[i] = String_.Replace(itm, "'", "\"");
		}
		return ary;
	}
}
