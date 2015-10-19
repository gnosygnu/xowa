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
package gplx.xowa.langs.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
import org.junit.*;
import gplx.core.intls.*;
public class Xob_i18n_parser_tst {
	@Before public void init() {fxt.Clear();} private Xob_i18n_parser_fxt fxt = new Xob_i18n_parser_fxt();
	@Test  public void Basic() {
		fxt.Test_xto_gfs(String_.Concat_lines_nl_skip_last
		( "{"
		, "    \"@metadata\": {"
		, "        \"authors\": []"
		, "    },"
		, "\"key_1\": \"val_1\","
		, "\"key_2\": \"val_2\","
		, "\"key_3\": \"val $1\","
		, "}"
		), String_.Concat_lines_nl_skip_last
		( "this.messages.load_text("
		, "<:['"
		, "key_1|val_1"
		, "key_2|val_2"
		, "key_3|val ~{0}"
		, "']:>"
		, ");"
		));
	}
//		@Test  public void Load_msgs_validate() {
//			fxt.Test_load_msgs_dir("C:\\xowa\\bin\\any\\xowa\\xtns\\Insider\\i18n\\");
//		}
}
class Xob_i18n_parser_fxt {
	public void Clear() {
	}
	public void Test_xto_gfs(String raw, String expd) {
		byte[] actl = Xob_i18n_parser.Xto_gfs(Bry_.new_u8(raw));
		Tfds.Eq_str_lines(expd, String_.new_u8(actl));
	}
	public void Test_load_msgs_dir(String dir_str) {
		Xoae_app app = Xoa_app_fxt.app_();
		Xowe_wiki wiki = Xoa_app_fxt.wiki_tst_(app);
		Io_url dir_url = Io_url_.new_dir_(dir_str);
		Io_url[] fil_urls = Io_mgr.Instance.QueryDir_fils(dir_url);
		int len = fil_urls.length;
		for (int i = 0; i < len; ++i) {
			Xob_i18n_parser.Load_msgs(false, wiki.Lang(), fil_urls[i]);
		}
	}
}
