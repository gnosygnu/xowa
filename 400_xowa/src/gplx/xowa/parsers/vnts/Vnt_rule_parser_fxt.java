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
package gplx.xowa.parsers.vnts; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.xowa.langs.vnts.*;
class Vnt_rule_parser_fxt {
	private final Vnt_rule_parser parser = new Vnt_rule_parser(); private final Vnt_rule_undi_mgr undis = new Vnt_rule_undi_mgr(); private final Vnt_rule_bidi_mgr bidis = new Vnt_rule_bidi_mgr();
	private final Bry_bfr bfr = Bry_bfr.new_(255);
	public Vnt_rule_parser_fxt() {
		Xol_vnt_regy vnt_regy = new Xol_vnt_regy();
		vnt_regy.Add(Bry_.new_a7("x1"), Bry_.new_a7("lang1"));
		vnt_regy.Add(Bry_.new_a7("x2"), Bry_.new_a7("lang2"));
		vnt_regy.Add(Bry_.new_a7("x3"), Bry_.new_a7("lang3"));
		parser.Init(null, vnt_regy);
	}
	public void Test_parse(String raw, String... expd_ary) {
		byte[] src = Bry_.new_u8(raw);
		parser.Clear(undis, bidis, src);
		parser.Parse(src, 0, src.length);
		parser.To_bry__dbg(bfr);
		Tfds.Eq_str_lines(String_.Concat_lines_nl_skip_last(expd_ary), bfr.To_str_and_clear());
	}
}
