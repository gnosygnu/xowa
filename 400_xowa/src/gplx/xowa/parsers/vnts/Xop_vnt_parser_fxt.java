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
import gplx.xowa.langs.*; import gplx.xowa.langs.vnts.*;
public class Xop_vnt_parser_fxt {
	public Xop_fxt Parser_fxt() {return fxt;} private Xop_fxt fxt;
	public Xop_vnt_parser_fxt Clear() {
		Xoae_app app = Xoa_app_fxt.app_();
		Xowe_wiki wiki = Xoa_app_fxt.wiki_(app, "zh.wikipedia.org");
		fxt = new Xop_fxt(app, wiki);
		Xol_lang lang = wiki.Lang();
		lang.Fallback_bry_(Bry_.new_a7("zh-cn,zh-hans,zh-hant"));
		Xop_vnt_parser_fxt.Vnt_mgr__init(lang.Vnt_mgr(), 3, Vnts_chinese);
		Xop_vnt_lxr_.Init(wiki);
		return this;
	}
	public Xop_vnt_parser_fxt Test_parse(String raw, String expd) {
		fxt.Test_parse_page_all_str(raw, expd);
		return this;
	}
	public static void Vnt_mgr__init(Xol_vnt_mgr vnt_mgr, int cur_idx, String[] ary) {
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			Xol_vnt_itm itm = vnt_mgr.Regy__get_or_new(Bry_.new_a7(ary[i]));
			vnt_mgr.Lang().Lang_mgr().Get_by_key_or_load(itm.Key()).Fallback_bry_(Bry_.new_a7("zh-hans,zh-hant"));
		}
		vnt_mgr.Init_end();
		vnt_mgr.Cur_vnt_(Bry_.new_a7(ary[cur_idx]));
	}
	public static final String[] Vnts_chinese = String_.Ary("zh", "zh-hans", "zh-hant", "zh-cn", "zh-hk", "zh-mo", "zh-sg", "zh-tw");
}
