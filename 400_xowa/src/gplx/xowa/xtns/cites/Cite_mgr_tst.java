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
package gplx.xowa.xtns.cites; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*; import gplx.core.tests.*;
import gplx.xowa.langs.msgs.*;
public class Cite_mgr_tst {
	private final    Cite_mgr_fxt fxt = new Cite_mgr_fxt();
	@Test   public void getLinkLabel_lower_alpha() {
		fxt.Test__getLinkLabel("lower-alpha", 1, "a");
		fxt.Test__getLinkLabel("lower-alpha", 2, "b");
		fxt.Test__getLinkLabel("lower-alpha", 3, "c");
	}
	@Test   public void getLinkLabel_upper_roman() {
		fxt.Test__getLinkLabel("upper-roman", 1, "I");
		fxt.Test__getLinkLabel("upper-roman", 2, "II");
		fxt.Test__getLinkLabel("upper-roman", 3, "III");
	}
	@Test   public void getLinkLabel_unknown() {
		fxt.Test__getLinkLabel("unknown", 1, "unknown 1");
	}
	@Test   public void getLinkLabel_err() {
		fxt.Test__getLinkLabel("upper-roman", 4, "<span class=\"error mw-ext-cite-error\" lang=\"en\" dir=\"ltr\">Cite error: Ran out of custom link labels for group \"upper-roman\".\nDefine more in the <nowiki>[[MediaWiki:cite_link_label_group-upper-roman]]</nowiki> message.</span>");
	}
}
class Cite_mgr_fxt {
	private Cite_mgr mgr;
	private Xowe_wiki wiki;
	public Cite_mgr_fxt() {
		// init cite_mgr
		Xoae_app app = Xoa_app_fxt.Make__app__edit();
		this.wiki = Xoa_app_fxt.Make__wiki__edit(app);
		this.mgr = new Cite_mgr(wiki);

		// init common messages
		this.Init__msg__label_grp("lower-alpha", "a b c");
		this.Init__msg__label_grp("upper-roman", "I II III");
		this.Init__msg(Cite_mgr.Msg__cite_error, "Cite error: ~{0}");
		this.Init__msg(Cite_mgr.Msg__cite_error_no_link_label_group, "Ran out of custom link labels for group \"~{0}\".\nDefine more in the <nowiki>[[MediaWiki:~{1}]]</nowiki> message.");
	}
	private void Init__msg__label_grp(String key, String val) {
		this.Init__msg(String_.new_u8(Cite_mgr.Msg__cite_link_label_group) + key, val);
	}
	private void Init__msg(String key, String val) {Init__msg(Bry_.new_u8(key), val);}
	private void Init__msg(byte[] key, String val) {
		Xol_msg_itm msg = wiki.Msg_mgr().Get_or_make(key);
		msg.Atrs_set(Bry_.new_u8(val), false, false);
	}
	public void Test__getLinkLabel(String group, int offset, String expd) {
		Gftest.Eq__str(expd, mgr.getLinkLabel(offset, Bry_.new_u8(group)));
	}
}
