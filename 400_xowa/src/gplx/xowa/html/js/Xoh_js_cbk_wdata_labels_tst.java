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
package gplx.xowa.html.js; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*;
import org.junit.*; import gplx.core.json.*; import gplx.xowa.xtns.wdatas.*;
public class Xoh_js_cbk_wdata_labels_tst {
	@Before public void init() {fxt.Init();} private final Wdata_wiki_mgr_fxt fxt = new Wdata_wiki_mgr_fxt();
	@Test   public void Basic() {
		fxt.Init_pages_add(fxt.Wdoc_bldr("q1").Add_label("en", "en_q1").Xto_wdoc());
		fxt.Init_pages_add(fxt.Wdoc_bldr("q2").Add_label("en", "en_q2").Xto_wdoc());
		fxt.Init_pages_add(fxt.Wdoc_bldr("Property:P1").Add_label("en", "en_property_p1").Xto_wdoc());
		Tst_wikidata_label_get(String_.Ary("en", "q1", "q2", "Property:P1"), String_.Ary("en_q1", "en_q2", "en_property_p1"));
	}
	@Test   public void Outliers() {
		fxt.Init_pages_add(fxt.Wdoc_bldr("q1").Add_label("en", "en_q1").Add_label("de", "de_q1").Xto_wdoc());
		Tst_wikidata_label_get(String_.Ary("fr", "q1"), String_.Ary((String)null));
		Tst_wikidata_label_get(String_.Ary("de", "q1"), String_.Ary("de_q1"));
		Tst_wikidata_label_get(String_.Ary("xowa_title", "q1"), String_.Ary("q1"));
		Tst_wikidata_label_get(String_.Ary("xowa_ui_lang", "q1"), String_.Ary("en_q1"));
		Tst_wikidata_label_get(String_.Ary("fr;de", "q1"), String_.Ary("de_q1"));
	}
	@Test   public void Escaped() {	// PURPOSE: \t should be escaped; EX:wd.q:2; DATE:2014-04-23
		Wdata_doc d = doc_("q1", String_.Concat_lines_nl
		(	"{ 'entity':['item',1]"
		,	", 'label':"
		,	"  { 'en':'\\ta'"	// NOTE: json literally has "\t", not (char)8
		,	"  }"
		,	"}"
		));
		fxt.Init_pages_add(d);
		Tst_wikidata_label_get(String_.Ary("en", "q1"), String_.Ary("\ta"));
	}
	private Wdata_doc doc_(String qid, String src) {
		Json_doc jdoc = fxt.Make_json(src);
		Xoae_app app = Xoa_app_fxt.app_();
		Wdata_doc rv = new Wdata_doc(Bry_.new_a7(qid), app.Wiki_mgr().Wdata_mgr(), jdoc);
		return rv;
	}
	private void Tst_wikidata_label_get(String[] args, String[] expd) {
		Xoa_app_fxt.Init_gui(fxt.App(), fxt.Wiki());
		Xoh_js_cbk exec = fxt.App().Gui_mgr().Browser_win().Active_html_itm().Js_cbk();
		GfoMsg msg = GfoMsg_.new_cast_(Xoh_js_cbk.Invk_wikidata_get_label);
		int args_len = args.length;
		for (int i = 0; i < args_len; i++)
			msg.Add("v", args[i]);
		String[] actl = (String[])GfoInvkAble_.InvkCmd_msg(exec, Xoh_js_cbk.Invk_wikidata_get_label, msg);
		Tfds.Eq_ary_str(expd, actl);
	}
}
