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
package gplx.xowa.users.prefs; import gplx.*; import gplx.xowa.*; import gplx.xowa.users.*;
import org.junit.*;
public class Prefs_converter_tst {
	@Before public void init() {} Prefs_converter_fxt fxt = new Prefs_converter_fxt();
	@Test   public void Basic() {
		fxt.Test_convert(String_.Concat_lines_nl
		(	"app.scripts.txns.get('user.prefs.general').version_('0.7.2.0').bgn();"
		,	"a.b.c_('d');"
		,	"e.f.g_h_(1);"
		,	"i.j('k').l_(2);"
		), String_.Concat_lines_nl
		(	"app.cfgs.get('app.sys_cfg.options_version', 'app').val = '2';"
		,	"app.cfgs.get('a.b.c', 'app').val = 'd';"
		,	"app.cfgs.get('e.f.g_h', 'app').val = '1';"
		,	"app.cfgs.get('i.j(\"k\").l', 'app').val = '2';"
		));
	}
}
class Prefs_converter_fxt {
	Prefs_converter converter = Prefs_converter._;
	public void Test_convert(String raw, String expd) {
		String actl = converter.Convert(raw);
		Tfds.Eq_str_lines(expd, actl);
	}
	public void Parse(String raw_str) {
		byte[] raw_bry = Bry_.new_utf8_(raw_str);
		int bgn_pos = Bry_finder.Find_fwd(raw_bry, Byte_ascii.Paren_bgn);
		if (bgn_pos == Bry_.NotFound) throw Err_.new_fmt_("unable to find paren_bgn: {0}", raw_str);
		int end_pos = Bry_finder.Find_fwd(raw_bry, Byte_ascii.Paren_end, bgn_pos);
		if (end_pos == Bry_.NotFound) throw Err_.new_fmt_("unable to find paren_end: {0}", raw_str);
		raw_bry = Bry_.Mid(raw_bry, bgn_pos, end_pos);
		int len = raw_bry.length;
		for (int i = 0; i < len; i++) {
			byte[] c = gplx.intl.Utf8_.Get_char_at_pos_as_bry(raw_bry, i);
			if (c.length == 1) {
				switch (c[0]) {
					case Byte_ascii.Dash:
						break;
					case Byte_ascii.Pipe:
						break;
				}				
			}
		} 
	}
}
