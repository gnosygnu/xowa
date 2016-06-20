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
package gplx.core.net; import gplx.*; import gplx.core.*;
import gplx.core.net.qargs.*;
class Gfo_url_parser_fxt {
	private final    Gfo_url_parser parser = new Gfo_url_parser();
	private Gfo_url actl;
	public Gfo_url_parser_fxt Chk_protocol_tid(byte v) 			{Tfds.Eq_byte(v, actl.Protocol_tid(), "protocol_tid"); return this;}
	public Gfo_url_parser_fxt Chk_protocol_bry(String v) 		{Tfds.Eq_str(v, actl.Protocol_bry(), "protocol_bry"); return this;}
	public Gfo_url_parser_fxt Chk_site(String v) 				{Tfds.Eq_str(v, actl.Segs__get_at_1st(), "site"); return this;}
	public Gfo_url_parser_fxt Chk_page(String v) 				{Tfds.Eq_str(v, actl.Segs__get_at_nth(), "page"); return this;}
	public Gfo_url_parser_fxt Chk_anch(String v) 				{Tfds.Eq_str(v, actl.Anch(), "anch"); return this;}
	public Gfo_url_parser_fxt Chk_segs(String... ary) 	{Tfds.Eq_int(ary.length, actl.Segs().length, "segs_len"); Tfds.Eq_str_lines(String_.Concat_lines_nl(ary), String_.Concat_lines_nl(String_.Ary(actl.Segs())), "segs"); return this;}
	public Gfo_url_parser_fxt Chk_qargs(String... ary)	{Tfds.Eq_str_lines(String_.To_str__as_kv_ary(ary), Gfo_qarg_itm.To_str(actl.Qargs()), "qargs"); return this;}
	public Gfo_url_parser_fxt Run_parse(String v) {
		this.actl = parser.Parse(Bry_.new_u8(v)); 
		return this;
	}
	public void Test_Parse_site_fast(String raw, String expd) {
		byte[] raw_bry = Bry_.new_u8(raw);
		parser.Parse_site_fast(site_data, raw_bry, 0, raw_bry.length);
		String actl = String_.new_u8(raw_bry, site_data.Site_bgn(), site_data.Site_end());
		Tfds.Eq(expd, actl);
	}	private final    Gfo_url_site_data site_data = new Gfo_url_site_data();
}
