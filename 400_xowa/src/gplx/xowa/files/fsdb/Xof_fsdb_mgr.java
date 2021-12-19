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
package gplx.xowa.files.fsdb;
import gplx.types.basics.lists.List_adp;
import gplx.xowa.*;
public interface Xof_fsdb_mgr {
	String                              Key();
	gplx.xowa.files.bins.Xof_bin_mgr    Bin_mgr();
	gplx.fsdb.meta.Fsm_mnt_mgr          Mnt_mgr();
	void                                Init_by_wiki(Xow_wiki wiki);
	void                                Fsdb_search_by_list(List_adp itms, Xow_wiki wiki, Xoa_page page, gplx.xowa.guis.cbks.js.Xog_js_wkr js_wkr);
	void                                Rls();
}
