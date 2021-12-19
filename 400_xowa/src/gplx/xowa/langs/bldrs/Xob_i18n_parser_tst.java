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
package gplx.xowa.langs.bldrs;
import gplx.libs.files.Io_mgr;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.libs.files.Io_url;
import gplx.libs.files.Io_url_;
import gplx.xowa.*;
import org.junit.*;
public class Xob_i18n_parser_tst {
	@Before public void init() {fxt.Clear();} private Xob_i18n_parser_fxt fxt = new Xob_i18n_parser_fxt();
	@Test public void Basic() {
		fxt.Test_xto_gfs(StringUtl.ConcatLinesNlSkipLast
		( "{"
		, "    \"@metadata\": {"
		, "        \"authors\": []"
		, "    },"
		, "\"key_1\": \"val_1\","
		, "\"key_2\": \"val_2\","
		, "\"key_3\": \"val $1\","
		, "}"
		), StringUtl.ConcatLinesNlSkipLast
		( "this.messages.load_text("
		, "<:['"
		, "key_1|val_1"
		, "key_2|val_2"
		, "key_3|val ~{0}"
		, "']:>"
		, ");"
		));
	}
//		@Test public void Load_msgs_validate() {
//			fxt.Test_load_msgs_dir("C:\\xowa\\bin\\any\\xowa\\xtns\\Insider\\i18n\\");
//		}
}
class Xob_i18n_parser_fxt {
	public void Clear() {
	}
	public void Test_xto_gfs(String raw, String expd) {
		byte[] actl = Xob_i18n_parser.Xto_gfs(BryUtl.NewU8(raw));
		GfoTstr.EqLines(expd, StringUtl.NewU8(actl));
	}
	public void Test_load_msgs_dir(String dir_str) {
		Xoae_app app = Xoa_app_fxt.Make__app__edit();
		Xowe_wiki wiki = Xoa_app_fxt.Make__wiki__edit(app);
		Io_url dir_url = Io_url_.new_dir_(dir_str);
		Io_url[] fil_urls = Io_mgr.Instance.QueryDir_fils(dir_url);
		int len = fil_urls.length;
		for (int i = 0; i < len; ++i) {
			Xob_i18n_parser.Load_msgs(false, wiki.Lang(), fil_urls[i]);
		}
	}
}
