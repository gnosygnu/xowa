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
package gplx.xowa.files.origs; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.xowa.files.repos.*; import gplx.xowa.files.fsdb.*; import gplx.xowa.wmfs.apis.*;
public class Xof_orig_wkr__wmf_api implements Xof_orig_wkr {
	private final Xoapi_orig_base orig_api; private final Xof_download_wkr download_wkr; private final Xowe_repo_mgr repo_mgr; private final byte[] wiki_domain;
	private final Xoapi_orig_rslts api_rv = new Xoapi_orig_rslts();		
	public Xof_orig_wkr__wmf_api(Xoapi_orig_base orig_api, Xof_download_wkr download_wkr, Xowe_repo_mgr repo_mgr, byte[] wiki_domain) {
		this.orig_api = orig_api; this.download_wkr = download_wkr; this.repo_mgr = repo_mgr; this.wiki_domain = wiki_domain;
	}
	public byte Tid() {return Xof_orig_wkr_.Tid_wmf_api;}
	public boolean			Find_by_list(OrderedHash rv, ListAdp itms) {throw Err_.not_implemented_();}
	public Xof_orig_itm Find_as_itm(byte[] ttl) {
		boolean found = orig_api.Api_query_size(api_rv, download_wkr, repo_mgr, ttl, Xof_img_size.Null, Xof_img_size.Null);	// pass in null size to look for orig; DATE:2015-02-10
		if (!found) return Xof_orig_itm.Null;	// ttl not found by api; return
		byte api_repo = Bry_.Eq(api_rv.Orig_wiki(), wiki_domain) ? Xof_orig_itm.Repo_wiki : Xof_orig_itm.Repo_comm;
		byte[] api_page = api_rv.Orig_page();
		int api_w = api_rv.Orig_w(), api_h = api_rv.Orig_h();
		Xof_ext api_ext = Xof_ext_.new_by_ttl_(api_page); api_ext = Ext__handle_ogg(api_ext, api_w, api_h);
		byte[] api_redirect = Bry_.Eq(api_page, ttl) ? null : api_page;	// ttl is different; must be redirect
		Xof_orig_itm rv = new Xof_orig_itm();
		rv.Init(api_repo, api_page, api_ext.Id(), api_w, api_h, api_redirect);
		return rv;
	}
	public boolean Add_orig(byte repo, byte[] page, int ext_id, int w, int h, byte[] redirect) {return false;}
	public static Xof_ext Ext__handle_ogg(Xof_ext ext, int w, int h) {
		if (!ext.Id_is_ogg()) return ext;
		boolean is_audio = w == 0 && h == 0; // wmf returns back w/h of 0 if audio; non-0 if video; DATE:2013-11-11
		int actl_ext_id = is_audio ? Xof_ext_.Id_oga : Xof_ext_.Id_ogv;
		return Xof_ext_.new_by_id_(actl_ext_id);
	}
}
