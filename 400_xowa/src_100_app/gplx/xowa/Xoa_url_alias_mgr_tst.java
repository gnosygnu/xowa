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
//namespace gplx.xowa {
//	import org.junit.*;
//	public class Xoa_url_alias_mgr_tst {
//		@Before public void init() {}
//		@Test  public void Basic() {
//			Add_bulk_tst(String_.Concat_lines_nl
//			(	"w|en.wikipedia.org/wiki/~{0}"
//			,	"d|en.wiktionary.org/wiki/~{0}"
//			), 	KeyVal_.new_("w:Earth", "en.wikipedia.org/wiki/Earth"), KeyVal_.new_("d:Earth", "en.wiktionary.org/wiki/Earth"), KeyVal_.new_("x:Earth", null));
//		}
//		private void Add_bulk_tst(String raw_str, params KeyVal[] expd_ary) {
//			Xoae_app app = Xoa_app_fxt.app_();
//			Xoa_url_alias_mgr mgr = new Xoa_url_alias_mgr(app);
//			byte[] raw_bry = Bry_.new_ascii_(raw_str);
//			mgr.Add_bulk(raw_bry);
//			int expd_ary_len = expd_ary.length;
//			for (int i = 0; i < expd_ary_len; i++) {
//				KeyVal kv = expd_ary[i];
//				byte[] ttl 	= Bry_.new_ascii_safe_null_(kv.Key()); 
//				byte[] expd = Bry_.new_ascii_safe_null_((String)kv.Val());
//				byte[] actl = mgr.Fmt_or_null(ttl);
//				Tfds.Eq(String_.new_utf8_null_safe_(expd), String_.new_utf8_null_safe_(actl));
//			}
//		}
//	}
//}
