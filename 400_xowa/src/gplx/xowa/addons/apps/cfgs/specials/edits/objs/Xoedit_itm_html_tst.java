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
package gplx.xowa.addons.apps.cfgs.specials.edits.objs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.cfgs.*; import gplx.xowa.addons.apps.cfgs.specials.*; import gplx.xowa.addons.apps.cfgs.specials.edits.*;
import org.junit.*; import gplx.core.tests.*;
import gplx.xowa.addons.apps.cfgs.mgrs.types.*;
public class Xoedit_itm_html_tst {
	private final    Xoedit_itm_html_fxt fxt = new Xoedit_itm_html_fxt();
	@Test   public void Build_html__memo() {
		fxt.Type_("memo").Key_("key1").Name_("name1").Html_cls_("html_cls1").Html_atrs_("key1=val1");

		// normal
		fxt.Val_("data1")
			.Test__Build_html("\n<textarea id=\"key1\" data-xocfg-key=\"key1\" data-xocfg-type=\"memo\" accesskey=\"d\" class=\"xocfg_data__memo html_cls1\" key1=val1>" 
			+ "data1"
			+ "</textarea>");

		// xml-tags
		fxt.Val_("<pre>~{page_text}</pre>")
			.Test__Build_html("\n<textarea id=\"key1\" data-xocfg-key=\"key1\" data-xocfg-type=\"memo\" accesskey=\"d\" class=\"xocfg_data__memo html_cls1\" key1=val1>" 
			+ "&lt;pre&gt;~{page_text}&lt;/pre&gt;"
			+ "</textarea>");
	}
}
class Xoedit_itm_html_fxt {
	private final    Xocfg_type_mgr type_mgr = new Xocfg_type_mgr();
	private final    Bry_bfr bry = Bry_bfr_.New();
	
	public Xoedit_itm_html_fxt Type_(String v) {this.type = v; return this;} private String type;
	public Xoedit_itm_html_fxt Key_(String v) {this.key = v; return this;} private String key;
	public Xoedit_itm_html_fxt Name_(String v) {this.name = v; return this;} private String name;
	public Xoedit_itm_html_fxt Html_cls_(String v) {this.html_cls = v; return this;} private String html_cls;
	public Xoedit_itm_html_fxt Html_atrs_(String v) {this.html_atrs = v; return this;} private String html_atrs;
	public Xoedit_itm_html_fxt Val_(String v) {this.val = v; return this;} private String val;

	public void Test__Build_html(String expd) {
		Xoedit_itm_html.Build_html(bry, type_mgr, key, name, type, html_atrs, html_cls, Bry_.new_u8(val));
		Gftest.Eq__str(expd, bry.To_str_and_clear());
	}
}