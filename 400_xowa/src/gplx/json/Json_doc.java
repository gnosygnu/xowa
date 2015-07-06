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
package gplx.json; import gplx.*;
public class Json_doc {
	public void Ctor(byte[] src, Json_itm_nde root) {this.src = src; this.root = root;}
	public Bry_bfr Bfr() {return bfr;} Bry_bfr bfr = Bry_bfr.new_();
	public Number_parser Utl_num_parser() {return utl_num_parser;} Number_parser utl_num_parser = new Number_parser();
	public byte[] Str_utf8_bry() {return str_utf8_bry;} private byte[] str_utf8_bry = new byte[6];
	public byte[] Src() {return src;} private byte[] src;
	public Json_itm_nde Root() {return root;} Json_itm_nde root;
	public byte[] Get_val_as_bry_or(byte[]   qry_bry, byte[] or) {tmp_qry_bry[0] = qry_bry; return Get_val_as_bry_or(tmp_qry_bry, or);}
	public byte[] Get_val_as_bry_or(byte[][] qry_bry, byte[] or) {
		Json_itm nde = Find_nde(root, qry_bry, qry_bry.length - 1, 0);
		return nde == null || nde.Tid() != Json_itm_.Tid_string ? or : nde.Data_bry();
	}
	public String Get_val_as_str_or(byte[]   qry_bry, String or) {tmp_qry_bry[0] = qry_bry; return Get_val_as_str_or(tmp_qry_bry, or);}
	public String Get_val_as_str_or(byte[][] qry_bry, String or) {
		Json_itm nde = Find_nde(root, qry_bry, qry_bry.length - 1, 0);
		return nde == null || nde.Tid() != Json_itm_.Tid_string ? or : (String)nde.Data();
	}
	public Json_grp Get_grp(byte[] qry_bry) {
		tmp_qry_bry[0] = qry_bry;
		Json_itm rv = Find_nde(root, tmp_qry_bry, 0, 0); if (rv == null) return null;
		return (Json_grp)rv;
	}	private byte[][] tmp_qry_bry = new byte[1][];
	public Json_grp Get_grp(byte[][] qry_bry) {
		Json_itm rv = Find_nde(root, qry_bry, qry_bry.length - 1, 0); if (rv == null) return null;
		return (Json_grp)rv;
	}
	public Json_itm Find_nde(byte[] key) {
		tmp_qry_bry[0] = key;
		return Find_nde(root, tmp_qry_bry, 0, 0);
	}
	private Json_itm Find_nde(Json_itm_nde owner, byte[][] paths, int paths_last, int paths_idx) {
		byte[] path = paths[paths_idx];
		int subs_len = owner.Subs_len();
		for (int i = 0; i < subs_len; i++) {
			Json_itm_kv itm = Json_itm_kv.cast_(owner.Subs_get_at(i)); if (itm == null) continue;	// ignore simple props, arrays, ndes
			if (!itm.Key_eq(path)) continue;
			if (paths_idx == paths_last) return itm.Val();
			Json_itm_nde sub_nde = Json_itm_nde.cast_(itm.Val()); if (sub_nde == null) return null;	// match, but has not a nde; exit
			return Find_nde(sub_nde, paths, paths_last, paths_idx + 1);
		}
		return null;
	}
	public static Json_doc new_apos_concat_nl(String... ary) {return new_apos_(String_.Concat_lines_nl(ary));}
	public static Json_doc new_apos_(String v) {return new_(Bry_.Replace(Bry_.new_u8(v), Byte_ascii.Apos, Byte_ascii.Quote));}
	public static Json_doc new_(String v) {return new_(Bry_.new_u8(v));}
	public static Json_doc new_(byte[] v) {
		synchronized (parser) {return parser.Parse(v);}
	}	private static final Json_parser parser = new Json_parser();
}