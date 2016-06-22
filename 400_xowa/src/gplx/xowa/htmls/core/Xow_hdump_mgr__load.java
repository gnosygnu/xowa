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
package gplx.xowa.htmls.core; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import gplx.core.ios.*;
import gplx.xowa.htmls.heads.*; import gplx.xowa.htmls.core.makes.*; import gplx.xowa.htmls.core.hzips.*;
import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.wikis.pages.*; import gplx.xowa.wikis.pages.skins.*; import gplx.xowa.wikis.pages.lnkis.*;
public class Xow_hdump_mgr__load {
	private final    Xow_wiki wiki; private final    Xoh_hzip_mgr hzip_mgr; private final    Io_stream_zip_mgr zip_mgr;
	private final    Xoh_page tmp_hpg; private final    Bry_bfr tmp_bfr; private final    Xowd_page_itm tmp_dbpg = new Xowd_page_itm();		
	private Xow_override_mgr override_mgr__html, override_mgr__page;
	public Xow_hdump_mgr__load(Xow_wiki wiki, Xoh_hzip_mgr hzip_mgr, Io_stream_zip_mgr zip_mgr, Xoh_page tmp_hpg, Bry_bfr tmp_bfr) {
		this.wiki = wiki; this.hzip_mgr = hzip_mgr; this.zip_mgr = zip_mgr; this.tmp_hpg = tmp_hpg; this.tmp_bfr = tmp_bfr;
		this.make_mgr = new Xoh_make_mgr(wiki.App().Usr_dlg(), wiki.App().Fsys_mgr(), gplx.langs.htmls.encoders.Gfo_url_encoder_.Fsys_lnx, wiki.Domain_bry());			
	}
	public Xoh_make_mgr Make_mgr() {return make_mgr;} private final    Xoh_make_mgr make_mgr;
	public void Load_by_edit(Xoae_page wpg) {
		tmp_hpg.Init(wpg.Wiki(), wpg.Url(), wpg.Ttl(), wpg.Revision_data().Id());
		Load(tmp_hpg, wpg.Ttl());
		wpg.Hdump_data().Body_(tmp_hpg.Body());
		wpg.Root_(new gplx.xowa.parsers.Xop_root_tkn());	// HACK: set root, else load page will fail
		Fill_page(wpg, tmp_hpg);
	}
	public boolean Load(Xoh_page hpg, Xoa_ttl ttl) {
		synchronized (tmp_dbpg) {
			if (override_mgr__page == null) {
				Io_url override_root_url = wiki.Fsys_mgr().Root_dir().GenSubDir_nest("data", "wiki");
				this.override_mgr__page = new Xow_override_mgr(override_root_url.GenSubDir_nest("page"));
				this.override_mgr__html = new Xow_override_mgr(override_root_url.GenSubDir_nest("html"));
			}
			boolean loaded = Load__dbpg(wiki, tmp_dbpg.Clear(), hpg, ttl);
			hpg.Init(hpg.Wiki(), hpg.Url(), ttl, tmp_dbpg.Id());
			if (!loaded) {		// nothing in "page" table
				byte[] page_override = override_mgr__page.Get_or_same(ttl.Page_db(), null);
				if (page_override == null) return Load__fail(hpg);
				hpg.Body_(page_override);
				return true;
			}
			Xow_db_file html_db = wiki.Data__core_mgr().Dbs__get_by_id_or_fail(tmp_dbpg.Html_db_id());
			if (!html_db.Tbl__html().Select_by_page(hpg)) return Load__fail(hpg);			// nothing in "html" table
			byte[] src = Parse(hpg, hpg.Body_zip_tid(), hpg.Body_hzip_tid(), hpg.Body());
			hpg.Body_(src);
			return true;
		}
	}
	public byte[] Decode_as_bry(Bry_bfr bfr, Xoh_page hpg, byte[] src, boolean mode_is_diff) {hzip_mgr.Hctx().Mode_is_diff_(mode_is_diff); hzip_mgr.Decode(bfr, wiki, hpg, src); return bfr.To_bry_and_clear();}
	private byte[] Parse(Xoh_page hpg, int zip_tid, int hzip_tid, byte[] src) {
		if (zip_tid > gplx.core.ios.streams.Io_stream_.Tid_raw)
			src = zip_mgr.Unzip((byte)zip_tid, src);
		if (hzip_tid == Xoh_hzip_dict_.Hzip__v1) {
			src = override_mgr__html.Get_or_same(hpg.Ttl().Page_db(), src);
			hpg.Section_mgr().Add(0, 2, Bry_.Empty, Bry_.Empty).Content_bgn_(0);	// +1 to skip \n
			src = Decode_as_bry(tmp_bfr.Clear(), hpg, src, Bool_.N);
			hpg.Section_mgr().Set_content(hpg.Section_mgr().Len() - 1, src, src.length);
		}
		else
			src = make_mgr.Parse(src, hpg, hpg.Wiki());
		return src;
	}
	private void Fill_page(Xoae_page wpg, Xoh_page hpg) {
		Xopg_html_data html_data = wpg.Html_data();
		html_data.Display_ttl_(tmp_hpg.Display_ttl());
		html_data.Content_sub_(tmp_hpg.Content_sub());			
		html_data.Xtn_skin_mgr().Add(new Xopg_xtn_skin_itm_stub(tmp_hpg.Sidebar_div()));
		Xoh_head_mgr wpg_head = html_data.Head_mgr();
		Xopg_module_mgr hpg_head = hpg.Head_mgr();			
		wpg_head.Itm__mathjax().Enabled_		(hpg_head.Math_exists());
		wpg_head.Itm__popups().Bind_hover_area_	(hpg_head.Imap_exists());
		wpg_head.Itm__gallery().Enabled_		(hpg_head.Gallery_packed_exists());
		wpg_head.Itm__hiero().Enabled_			(hpg_head.Hiero_exists());
		wpg_head.Itm__timeline().Enabled_		(true);

		// transfer images from Xoh_page to Xoae_page 
		Xoh_img_mgr src_imgs = hpg.Img_mgr();
		int len = src_imgs.Len();
		for (int i = 0; i < len; ++i) {
			gplx.xowa.files.Xof_fsdb_itm itm = src_imgs.Get_at(i);
			wpg.Hdump_data().Imgs().Add(itm);
			wpg.File_queue().Add(itm);	// add to file_queue for http_server
		}

		// transfer redlinks
		Xopg_lnki_list src_list = hpg.Redlink_list();
		Xopg_lnki_list trg_list = wpg.Redlink_list();
		len = src_list.Len();
		for (int i = 0; i < len; ++i) {
			trg_list.Add_direct(src_list.Get_at(i));
		}
	}
	private static boolean Load__fail(Xoh_page hpg) {hpg.Exists_n_(); return false;}
	private static boolean Load__dbpg(Xow_wiki wiki, Xowd_page_itm dbpg, Xoh_page hpg, Xoa_ttl ttl) {
		wiki.Data__core_mgr().Tbl__page().Select_by_ttl(dbpg, ttl.Ns(), ttl.Page_db());
		if (dbpg.Redirect_id() != -1) Load__dbpg__redirects(wiki, dbpg);
		return dbpg.Html_db_id() != -1;
	}
	private static void Load__dbpg__redirects(Xow_wiki wiki, Xowd_page_itm dbpg) {
		int redirect_count = 0;
		while (++redirect_count < 5) {
			int redirect_id = dbpg.Redirect_id();
			wiki.Data__core_mgr().Tbl__page().Select_by_id(dbpg, redirect_id);
			if (redirect_id == -1) break;
		}
	}
}
class Xow_override_mgr {
	private final    Hash_adp_bry hash = Hash_adp_bry.cs();
	private final    Io_url root_dir;
	private boolean init = true;
	public Xow_override_mgr(Io_url root_dir) {this.root_dir = root_dir;} 
	public void Clear() {hash.Clear();}
	public byte[] Get_or_same(byte[] ttl, byte[] src) {
		if (init) {init = false; Load_from_fsys(hash, root_dir);}
		byte[] rv = (byte[])hash.Get_by_bry(ttl);
		return rv == null ? src : rv;
	}
	private static void Load_from_fsys(Hash_adp_bry hash, Io_url root_dir) {
		Io_url[] urls = Io_mgr.Instance.QueryDir_args(root_dir).Recur_(true).ExecAsUrlAry();
		int urls_len = urls.length;
		for (int i = 0; i < urls_len; ++i) {
			Io_url url = urls[i];
			byte[] raw = Io_mgr.Instance.LoadFilBry(url); int bry_len = raw.length;
			int nl_pos = Bry_find_.Find_fwd(raw, Byte_ascii.Nl, 0, bry_len); if (nl_pos == Bry_find_.Not_found) continue;
			byte[] ttl = Bry_.Mid(raw, 0, nl_pos);
			byte[] src = Bry_.Mid(raw, nl_pos + 1, bry_len);				
			hash.Add_bry_obj(ttl, src);
		}
	}
}
