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
package gplx.xowa; import gplx.*;
import gplx.ios.*; import gplx.xowa.files.cnvs.*;
class Xobc_img_xfer_base_fxt {
	public Xow_wiki En_wiki() {return en_wiki;} private Xow_wiki en_wiki;
	public Xow_wiki Commons() {return commons;} private Xow_wiki commons;
	Xofw_wiki_wkr_mock mock_wkr = new Xofw_wiki_wkr_mock();
	public void Init_page_create(Xow_wiki wiki, String ttl) {Init_page_create(wiki, ttl, "");}
	public void Init_page_create(Xow_wiki wiki, String ttl, String txt) {
		Xoa_ttl page_ttl = Xoa_ttl.parse_(wiki, Bry_.new_utf8_(ttl));
		byte[] page_raw = Bry_.new_utf8_(txt);
		wiki.Db_mgr().Save_mgr().Data_create(page_ttl, page_raw);
	}
	public void Ini(boolean src_local) {
		Io_mgr._.InitEngine_mem();
		Xoa_app app = Xoa_app_fxt.app_();
		en_wiki = Xoa_app_fxt.wiki_tst_(app);
		commons = Xoa_app_fxt.wiki_(app, Xow_wiki_.Domain_commons_str);
		mock_wkr.Clear_commons();	// assume all files are in repo 0
		en_wiki.File_mgr().Repo_mgr().Page_finder_(mock_wkr);
		en_wiki.Db_mgr().Load_mgr().Clear();
		commons.Db_mgr().Load_mgr().Clear();
		app.Wiki_mgr().Add(commons);
		
		Xof_file_mgr file_mgr = app.File_mgr();
		file_mgr.Img_mgr().Wkr_resize_img_(Xof_img_wkr_resize_img_mok._);
		file_mgr.Img_mgr().Wkr_query_img_size_(new Xof_img_wkr_query_img_size_test());

		byte[] src_commons = Bry_.new_ascii_("src_commons");
		byte[] src_en_wiki = Bry_.new_ascii_("src_en_wiki");
		byte[] trg_commons = Bry_.new_ascii_("trg_commons");
		byte[] trg_en_wiki = Bry_.new_ascii_("trg_en_wiki");
		App_repo_add(file_mgr, src_commons, "mem/src/commons.wikimedia.org/", Xow_wiki_.Domain_commons_str, false);
		App_repo_add(file_mgr, src_en_wiki, "mem/src/en.wikipedia.org/", Xow_wiki_.Domain_enwiki_str, false);
		App_repo_add(file_mgr, trg_commons, "mem/trg/commons.wikimedia.org/", Xow_wiki_.Domain_commons_str, true);
		App_repo_add(file_mgr, trg_en_wiki, "mem/trg/en.wikipedia.org/", Xow_wiki_.Domain_enwiki_str, true);
		Xow_repo_mgr wiki_repo_mgr = en_wiki.File_mgr().Repo_mgr();
		Xof_repo_pair pair = null;
		pair = wiki_repo_mgr.Add_repo(src_commons, trg_commons);
		pair.Src().Fsys_is_wnt_(true).Wmf_fsys_(!src_local);
		pair.Trg().Fsys_is_wnt_(true);

		pair = wiki_repo_mgr.Add_repo(src_en_wiki, trg_en_wiki);
		pair.Src().Fsys_is_wnt_(true).Wmf_fsys_(!src_local);
		pair.Trg().Fsys_is_wnt_(true);

		src_fils = trg_fils = null;
		this.Ini_hook(app, en_wiki);
		html_src = null;
	}
	@gplx.Virtual public void Ini_hook(Xoa_app app, Xow_wiki wiki) {}
	private void App_repo_add(Xof_file_mgr file_mgr, byte[] key, String root, String wiki, boolean trg) {
		Xof_repo_itm repo = file_mgr.Repo_mgr().Set(String_.new_utf8_(key), root, wiki).Ext_rules_(Xoft_rule_grp.Grp_app_default);
		if (trg) {
			byte[][] ary = repo.Mode_names();
			ary[0] = Bry_.new_ascii_("raw");
			ary[1] = Bry_.new_ascii_("fit");
		}
	}
	public Xobc_img_xfer_base_fxt Src_base(Io_fil... v) {src_fils = v; return this;} Io_fil[] src_fils;
	public Xobc_img_xfer_base_fxt Trg_base(Io_fil... v) {trg_fils = v; return this;} Io_fil[] trg_fils;
	public String Html_view_src() {return html_src;} protected Xobc_img_xfer_base_fxt Html_src_base_(String v) {html_src = v; return this;} private String html_src;
	public void ini_src_fils() {
		if (src_fils != null) {
			for (int i = 0; i < src_fils.length; i++) {
				Io_fil src_fil = src_fils[i];
				Io_mgr._.SaveFilStr(src_fil.Url(), src_fil.Data());
			}
		}
	}
	public void tst_trg_fils() {
		for (int i = 0; i < trg_fils.length; i++) {
			Io_fil trg_fil = trg_fils[i];
			String data = Io_mgr._.LoadFilStr(trg_fil.Url());
			Tfds.Eq_str_lines(trg_fil.Data(), data, trg_fil.Url().Raw());
		}		
	}
	public void	 save_(Io_fil v)						{Io_mgr._.SaveFilStr(v.Url(), v.Data());}
	public Io_fil reg_(String url, String... v)	{return new Io_fil(Io_url_.mem_fil_(url), String_.Concat_lines_nl(v));}
	public Io_fil img_(String url_str, int w, int h)	{return file_(url_str, file_img(w, h));}
	public Io_fil svg_(String url_str, int w, int h)	{return file_(url_str, file_svg(w, h));}
	public Io_fil ogg_(String url_str)					{return file_(url_str, "");}
	Io_fil file_(String url_str, String data)			{return new Io_fil(Io_url_.mem_fil_(url_str), data);}
	String file_img(int w, int h) {return String_.Format("{0},{1}", w, h);}
	String file_svg(int w, int h) {return String_.Format("<svg width=\"{0}\" height=\"{1}\" />", w, h);}
}
class Xobc_img_run_xfer_fxt extends Xobc_img_xfer_base_fxt {
	public Xobc_img_run_xfer_fxt Rdr(String... v) {rdr = String_.Concat_lines_nl(v); return this;} private String rdr;
	public Xobc_img_run_xfer_fxt Src(Io_fil... v) {return (Xobc_img_run_xfer_fxt)this.Src_base(v);}
	public Xobc_img_run_xfer_fxt Trg(Io_fil... v) {return (Xobc_img_run_xfer_fxt)this.Trg_base(v);}
	@Override public void Ini_hook(Xoa_app app, Xow_wiki wiki) {
		bldr = Xoa_app_fxt.bldr_(app);
		wkr = new Xobc_img_run_xfer(bldr, wiki);
		GfoInvkAble_.InvkCmd_val(wkr, Xobc_img_run_xfer.Invk_rdr_dir_, "mem/rdr/");
	}	private Xob_bldr bldr; Xobc_img_run_xfer wkr;
	public void tst() {
		ini_src_fils();
		Io_mgr._.SaveFilStr(wkr.Rdr_dir().GenSubFil("0000.csv"), rdr);
		wkr.Cmd_bgn(bldr);
		wkr.Cmd_run();
		wkr.Cmd_end();
		tst_trg_fils();
	}
	public void tst_html_src(String expd) {
		boolean found = wkr.Xfer_itm().Atrs_calc_for_html();
		Tfds.Eq(expd, String_.new_utf8_(wkr.Xfer_itm().Html_view_src()));
		Tfds.Eq(true, found, "img not found");
	}
}
