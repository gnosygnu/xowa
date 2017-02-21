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
package gplx.xowa.xtns.wbases.hwtrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*;
import gplx.xowa.langs.*;
import gplx.xowa.xtns.wbases.core.*; import gplx.xowa.apps.apis.xowa.xtns.*;
public class Wdata_lbl_wkr_wiki implements Wdata_lbl_wkr {
	private Wdata_wiki_mgr wdata_mgr;
	private Xoapi_wikibase wikibase_api;
	public Wdata_lbl_wkr_wiki(Xoapi_wikibase wikibase_api, Wdata_wiki_mgr wdata_mgr) {this.wikibase_api = wikibase_api; this.wdata_mgr = wdata_mgr;}
	public void Resolve(Wdata_lbl_mgr lbl_mgr, Wdata_lang_sorter sorter) {
		List_adp queue = lbl_mgr.Queue();
		int len = queue.Count();
		for (int i = 0; i < len; ++i) {
			Wdata_lbl_itm itm = (Wdata_lbl_itm)queue.Get_at(i);
			Wdata_doc wdoc = wdata_mgr.Doc_mgr.Get_by_xid_or_null(itm.Ttl());
			if (wdoc == null) {
				Xoa_app_.Usr_dlg().Warn_many("", "", "wbase.lbl_wkr:page does not exists; page=~{0}", itm.Ttl());
				continue; // handle incomplete wikidata dumps; DATE:2015-06-11
			}
			Ordered_hash labels = wdoc.Label_list();
			if (labels.Count() == 0) continue;
			labels.Sort_by(sorter);
			Wdata_langtext_itm label = Wdata_langtext_itm.Get_itm_or_null(wdoc.Label_list(), wikibase_api.Core_langs());
			if (label == null)
				itm.Load_vals(Bry_.Empty, itm.Ttl());	// NOTE: use itm.Ttl() in case no label is found for the core_lang
			else {
				itm.Load_vals(label.Lang(), label.Text());
				if (itm.Text_en_enabled()) {
					Wdata_langtext_itm en_label = (Wdata_langtext_itm)labels.Get_by(Xol_lang_itm_.Key_en);
					itm.Text_en_(en_label == null ? Bry_.Empty : en_label.Text());
				}
			}
		}
	}
}
