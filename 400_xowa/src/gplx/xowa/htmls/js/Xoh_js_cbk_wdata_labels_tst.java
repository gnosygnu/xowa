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
package gplx.xowa.htmls.js; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import org.junit.*; import gplx.langs.jsons.*; import gplx.xowa.xtns.wbases.*;
public class Xoh_js_cbk_wdata_labels_tst {
	@Before public void init() {fxt.Init();} private final    Wdata_wiki_mgr_fxt fxt = new Wdata_wiki_mgr_fxt();
	@Test   public void Basic() {
		fxt.Init__docs__add(fxt.Wdoc_bldr("q1").Add_label("en", "en_q1").Xto_wdoc());
		fxt.Init__docs__add(fxt.Wdoc_bldr("q2").Add_label("en", "en_q2").Xto_wdoc());
		fxt.Init__docs__add(fxt.Wdoc_bldr("Property:P1").Add_label("en", "en_property_p1").Xto_wdoc());
		Tst_wikidata_label_get(String_.Ary("en", "q1", "q2", "Property:P1"), String_.Ary("en_q1", "en_q2", "en_property_p1"));
	}
	@Test   public void Outliers() {
		fxt.Init__docs__add(fxt.Wdoc_bldr("q1").Add_label("en", "en_q1").Add_label("de", "de_q1").Xto_wdoc());
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
		fxt.Init__docs__add(d);
		Tst_wikidata_label_get(String_.Ary("en", "q1"), String_.Ary("\ta"));
	}
	private Wdata_doc doc_(String qid, String src) {
		Json_doc jdoc = fxt.Make_json(src);
		Xoae_app app = Xoa_app_fxt.Make__app__edit();
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
		String[] actl = (String[])Gfo_invk_.Invk_by_msg(exec, Xoh_js_cbk.Invk_wikidata_get_label, msg);
		Tfds.Eq_ary_str(expd, actl);
	}
}
