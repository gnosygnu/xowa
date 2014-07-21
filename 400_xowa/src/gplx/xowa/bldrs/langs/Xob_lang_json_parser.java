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
package gplx.xowa.bldrs.langs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.json.*; import gplx.gfs.*;
public class Xob_lang_json_parser {
	private Json_parser json_parser = new Json_parser();
	private Gfs_bldr gfs_bldr = new Gfs_bldr();
	private Xol_csv_parser csv_parser = Xol_csv_parser._;
	public Xob_lang_json_parser() {}
	public byte[] Parse(byte[] raw) {
		Json_doc jdoc = json_parser.Parse(raw);
		Json_itm_nde jnde = jdoc.Root();
		gfs_bldr.Add_proc_init_many("this", "messages", "load_text").Add_paren_bgn().Add_nl();
		gfs_bldr.Add_quote_xtn_bgn();
		int subs_len = jnde.Subs_len();
		Bry_bfr bfr = gfs_bldr.Bfr();
		for (int i = 0; i < subs_len; ++i)
			Parse_sub(bfr, raw, jnde.Subs_get_at(i));
		gfs_bldr.Add_quote_xtn_end().Add_paren_end().Add_term_nl();
		return gfs_bldr.Xto_bry();
	}
	private void Parse_sub(Bry_bfr bfr, byte[] raw, Json_itm itm) {
		switch (itm.Tid()) {
			case Json_itm_.Tid_kv:
				Json_itm_kv kv = (Json_itm_kv)itm;
				if (kv.Key().Data_eq(Name_metadata)) return;	// ignore @metadata node
				byte[] key_bry = kv.Key().Data_bry();
				byte[] val_bry = kv.Val().Data_bry();
				csv_parser.Save(bfr, key_bry);					// key
				bfr.Add_byte_pipe();							// |
				csv_parser.Save(bfr, val_bry);					// val
				bfr.Add_byte_nl();								// \n
				break;
		}
	}
	private static final byte[] Name_metadata = Bry_.new_ascii_("@metadata");
}
