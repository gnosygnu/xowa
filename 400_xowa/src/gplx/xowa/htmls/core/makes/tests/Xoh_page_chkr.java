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
package gplx.xowa.htmls.core.makes.tests; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.makes.*;
import gplx.xowa.htmls.sections.*;
import gplx.xowa.files.*;
public class Xoh_page_chkr {
	private final    Xoh_section_mgr expd_section_mgr = new Xoh_section_mgr();
	private final    Xoh_img_mgr expd_img_mgr = new Xoh_img_mgr();
	private final    Bry_bfr bfr = Bry_bfr_.New();
	public void Clear() {
		expd_img_mgr.Clear();
	}
	public Xoh_page_chkr Body_(String v) {this.expd_body = String_.Replace(v, "'", "\""); return this;} private String expd_body;
	public Xoh_page_chkr Sections__add(int uid, int level, String anchor, String display, String content) {
		expd_section_mgr.Add(uid, level, Bry_.new_u8(anchor), Bry_.new_u8(display)).Content_(Bry_.new_u8(content));
		return this;
	}
	public Xoh_page_chkr Imgs__add(String wiki_abrv, String lnki_ttl, byte lnki_type, double lnki_upright, int lnki_w, int lnki_h, double lnki_time, int lnki_page) {
		Xof_fsdb_itm fsdb_itm = expd_img_mgr.Make_img(false);
		fsdb_itm.Init_at_lnki(Xof_exec_tid.Tid_wiki_page, Bry_.new_u8(wiki_abrv), Bry_.new_u8(lnki_ttl), lnki_type, lnki_upright, lnki_w, lnki_h, lnki_time, lnki_page, Xof_patch_upright_tid_.Tid_all);
		return this;
	}
	public void Check(Xoh_page actl) {
		if (expd_body != null) Tfds.Eq_str_lines(expd_body, String_.new_u8(actl.Db().Html().Html_bry()));
		if (expd_section_mgr.Len() > 0)
			Tfds.Eq_str_lines(To_str__section_mgr(expd_section_mgr), To_str__section_mgr(actl.Section_mgr()));
		if (expd_img_mgr.Len() > 0)
			Tfds.Eq_str_lines(To_str__img_mgr(expd_img_mgr), To_str__img_mgr(actl.Img_mgr()));
	}
	private String To_str__section_mgr(Xoh_section_mgr section_mgr) {
		section_mgr.To_bfr(bfr);
		return bfr.To_str_and_clear();
	}
	private String To_str__img_mgr(Xoh_img_mgr img_mgr) {
		img_mgr.To_bfr(bfr);
		return bfr.To_str_and_clear();
	}
}
