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
package gplx.xowa.files.origs; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.xowa.files.repos.*; import gplx.xowa.files.fsdb.*; import gplx.xowa.apps.wms.apis.*; import gplx.xowa.files.downloads.*;
import gplx.xowa.apps.wms.apis.origs.*;
public class Xof_orig_wkr__wmf_api implements Xof_orig_wkr {
	private final    Xoapi_orig_base orig_api; private final    Xof_download_wkr download_wkr; private final    Xow_repo_mgr repo_mgr; private final    byte[] wiki_domain;
	private final    Xoapi_orig_rslts api_rv = new Xoapi_orig_rslts();		
	public Xof_orig_wkr__wmf_api(Xoapi_orig_base orig_api, Xof_download_wkr download_wkr, Xow_repo_mgr repo_mgr, byte[] wiki_domain) {
		this.orig_api = orig_api; this.download_wkr = download_wkr; this.repo_mgr = repo_mgr; this.wiki_domain = wiki_domain;
	}
	public byte Tid() {return Xof_orig_wkr_.Tid_wmf_api;}
	public void			Find_by_list(Ordered_hash rv, List_adp itms) {Xof_orig_wkr_.Find_by_list(this, rv, itms);}
	public Xof_orig_itm Find_as_itm(byte[] ttl, int list_idx, int list_len) {
		if (!gplx.core.ios.IoEngine_system.Web_access_enabled) return Xof_orig_itm.Null;	// don't check api if download disabled, else prog messages; DATE:2015-06-17

		// make call to api
		Xoa_app_.Usr_dlg().Prog_none("", "", Prog_msg(list_idx, list_len, ttl));
		boolean found = orig_api.Api_query_size(api_rv, download_wkr, repo_mgr, ttl, Xof_img_size.Null, Xof_img_size.Null);	// pass in null size to look for orig; DATE:2015-02-10
		if (!found) return Xof_orig_itm.Null;	// ttl not found by api; return

		// deserialize values and return
		byte api_repo = Bry_.Eq(api_rv.Orig_wiki(), wiki_domain) ? Xof_orig_itm.Repo_wiki : Xof_orig_itm.Repo_comm;
		byte[] api_page = api_rv.Orig_page();
		int api_w = api_rv.Orig_w(), api_h = api_rv.Orig_h();
		Xof_ext api_ext = Xof_ext_.new_by_ttl_(api_page); api_ext = Ext__handle_ogg(api_ext, api_w, api_h);
		byte[] api_redirect = Bry_.Eq(api_page, ttl) ? Bry_.Empty : api_page;	// ttl is different; must be redirect
		Xof_orig_itm rv = new Xof_orig_itm(api_repo, api_page, api_ext.Id(), api_w, api_h, api_redirect);
		rv.Insert_new_y_();
		return rv;
	}
	public boolean Add_orig(byte repo, byte[] page, int ext_id, int w, int h, byte[] redirect) {return false;}
	public void				Db_txn_save() {}
	public void				Db_rls() {}
	public static Xof_ext Ext__handle_ogg(Xof_ext ext, int w, int h) {
		if (!ext.Id_is_ogg()) return ext;
		boolean is_audio = w == 0 && h == 0; // wmf returns back w/h of 0 if audio; non-0 if video; DATE:2013-11-11
		int actl_ext_id = is_audio ? Xof_ext_.Id_oga : Xof_ext_.Id_ogv;
		return Xof_ext_.new_by_id_(actl_ext_id);
	}
	private static String Prog_msg(int idx, int len, byte[] ttl) {
		return String_.Format("downloading file {0} of {1}: {2}", idx + List_adp_.Base1, len, ttl);
	}
}
