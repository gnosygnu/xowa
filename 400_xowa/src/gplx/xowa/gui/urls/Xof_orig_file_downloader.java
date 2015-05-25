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
package gplx.xowa.gui.urls; import gplx.*; import gplx.xowa.*; import gplx.xowa.gui.*;
import gplx.xowa.files.*; import gplx.xowa.files.repos.*; import gplx.xowa.files.origs.*;
public class Xof_orig_file_downloader {
	public static Xof_fsdb_itm Make_fsdb(Xowe_wiki wiki, byte[] lnki_ttl, Xof_img_size img_size, Xof_url_bldr url_bldr) {
		Xof_fsdb_itm fsdb = new Xof_fsdb_itm();
		lnki_ttl = Xoa_ttl.Replace_spaces(Xoa_app_.Utl__encoder_mgr().Http_url().Decode(lnki_ttl));
		fsdb.Init_at_lnki(Xof_exec_tid.Tid_viewer_app, wiki.Domain_itm().Abrv_xo(), lnki_ttl, Xop_lnki_type.Id_none, Xop_lnki_tkn.Upright_null, Xof_img_size.Size_null_deprecated, Xof_img_size.Size_null_deprecated, Xof_lnki_time.Null, Xof_lnki_page.Null, Xof_patch_upright_tid_.Tid_all);
		fsdb.Init_at_hdoc(Int_.MaxValue, Xof_html_elem.Tid_img);// NOTE: set elem_id to "impossible" number, otherwise it will auto-update an image on the page with a super-large size; [[File:Alfred Sisley 062.jpg]]
		Xof_orig_itm orig = wiki.File__orig_mgr().Find_by_ttl_or_null(lnki_ttl); if (orig == Xof_orig_itm.Null) return null;	// orig not found; need orig in order to get repo
		Xof_repo_itm repo = wiki.File__repo_mgr().Get_trg_by_id_or_null(orig.Repo(), lnki_ttl, Bry_.Empty); if (repo == null) return null; // repo not found
		fsdb.Init_at_orig(orig.Repo(), repo.Wiki_domain(), orig.Ttl(), orig.Ext(), orig.W(), orig.H(), orig.Redirect());
		fsdb.Init_at_html(Xof_exec_tid.Tid_viewer_app, img_size, repo, url_bldr);
		fsdb.File_is_orig_(true);
		return fsdb;
	}
}
