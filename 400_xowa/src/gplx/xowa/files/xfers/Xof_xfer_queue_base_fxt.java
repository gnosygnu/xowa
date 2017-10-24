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
package gplx.xowa.files.xfers; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.core.ios.*;
import gplx.xowa.wikis.domains.*;
import gplx.xowa.files.cnvs.*; import gplx.xowa.files.repos.*; import gplx.xowa.files.exts.*; import gplx.xowa.files.imgs.*;
import gplx.xowa.apps.wms.apis.*; import gplx.xowa.apps.wms.apis.origs.*;	
public class Xof_xfer_queue_base_fxt {
	public Xoapi_orig_mok Api_size() {return api_size;} private Xoapi_orig_mok api_size = Xoapi_orig_mok.Instance;
	public Xof_repo_itm Src_commons_repo() {return src_commons_repo;} private Xof_repo_itm src_commons_repo;
	public Xof_repo_itm Src_en_wiki_repo() {return src_en_wiki_repo;} private Xof_repo_itm src_en_wiki_repo;
	@gplx.Virtual public void Clear(boolean src_repo_is_wmf) {
		Io_mgr.Instance.InitEngine_mem();
		if (app == null) {
			app = Xoa_app_fxt.Make__app__edit();
			en_wiki = Xoa_app_fxt.Make__wiki__edit(app, Xow_domain_itm_.Str__enwiki);
			commons = Xoa_app_fxt.Make__wiki__edit(app, Xow_domain_itm_.Str__commons);
			app.Wiki_mgr().Add(commons);
			app.Wiki_mgr().Add(en_wiki);
			
			Xof_file_mgr file_mgr = app.File_mgr();
			file_mgr.Img_mgr().Wkr_resize_img_(Xof_img_wkr_resize_img_mok.Instance);
			file_mgr.Img_mgr().Wkr_query_img_size_(new Xof_img_wkr_query_img_size_test());
			app.Wmf_mgr().Api_mgr().Api_orig_(api_size);

			byte[] src_commons = Bry_.new_a7("src_commons");
			byte[] src_en_wiki = Bry_.new_a7("src_en_wiki");
			byte[] trg_commons = Bry_.new_a7("trg_commons");
			byte[] trg_en_wiki = Bry_.new_a7("trg_en_wiki");
			src_commons_repo = Ini_repo_add(file_mgr, src_commons, "mem/src/commons.wikimedia.org/", Xow_domain_itm_.Str__commons, false);
			src_en_wiki_repo = Ini_repo_add(file_mgr, src_en_wiki, "mem/src/en.wikipedia.org/"		, Xow_domain_itm_.Str__enwiki, false);
			Ini_repo_add(file_mgr, trg_commons, "mem/trg/commons.wikimedia.org/", Xow_domain_itm_.Str__commons, true).Primary_(true);
			Ini_repo_add(file_mgr, trg_en_wiki, "mem/trg/en.wikipedia.org/"		, Xow_domain_itm_.Str__enwiki, true).Primary_(true);
			Xowe_repo_mgr wiki_repo_mgr = en_wiki.File_mgr().Repo_mgr();
			Xof_repo_pair pair = null;
			pair = wiki_repo_mgr.Add_repo(src_commons, trg_commons);
			pair.Src().Fsys_is_wnt_(true).Wmf_fsys_(src_repo_is_wmf).Tarball_(!src_repo_is_wmf);
			pair.Trg().Fsys_is_wnt_(true);

			pair = wiki_repo_mgr.Add_repo(src_en_wiki, trg_en_wiki);
			pair.Src().Fsys_is_wnt_(true).Wmf_fsys_(src_repo_is_wmf);
			pair.Trg().Fsys_is_wnt_(true);
		}
		en_wiki.Clear_for_tests();
		commons.Clear_for_tests();
		src_fils = trg_fils = Io_fil.Ary_empty;
		html_view_str = null;
		html_w = html_h = -1;
	}
	public Xoae_app App() {return app;} private Xoae_app app;
	public Xowe_wiki En_wiki() {return en_wiki;} private Xowe_wiki en_wiki;
	public Xowe_wiki Commons() {return commons;} private Xowe_wiki commons;
	public void ini_page_create_commons(String ttl)								{Init_page_create(commons, ttl, "");}
	public void ini_page_create_commons_redirect(String ttl, String redirect)	{Init_page_create(commons, ttl, "#REDIRECT [[" + redirect + "]]");}
	public void ini_page_create_en_wiki(String ttl)								{Init_page_create(en_wiki, ttl, "");}
	public void ini_page_create_en_wiki_redirect(String ttl, String redirect)	{Init_page_create(en_wiki, ttl, "#REDIRECT [[" + redirect + "]]");}
	public void Init_page_create(Xowe_wiki wiki, String ttl, String txt) {
		Xoa_ttl page_ttl = Xoa_ttl.Parse(wiki, Bry_.new_u8(ttl));
		byte[] page_raw = Bry_.new_u8(txt);
		wiki.Db_mgr().Save_mgr().Data_create(page_ttl, page_raw);
	}
	Xof_repo_itm Ini_repo_add(Xof_file_mgr file_mgr, byte[] key, String root, String wiki, boolean trg) {
		Xof_repo_itm repo = file_mgr.Repo_mgr().Set(String_.new_u8(key), root, wiki).Ext_rules_(Xof_rule_grp.Grp_app_default).Dir_depth_(2);
		if (trg) {
			byte[][] ary = repo.Mode_names();
			ary[0] = Bry_.new_a7("raw");
			ary[1] = Bry_.new_a7("fit");
		}
		return repo;
	}
	public Xof_xfer_queue_base_fxt Src_base(Io_fil... v) {src_fils = v; return this;} Io_fil[] src_fils = Io_fil.Ary_empty;
	public Xof_xfer_queue_base_fxt Trg_base(Io_fil... v) {trg_fils = v; return this;} Io_fil[] trg_fils = Io_fil.Ary_empty;
	public String Html_view_src() {return html_view_str;} protected Xof_xfer_queue_base_fxt Html_src_base_(String v) {html_view_str = v; return this;} private String html_view_str;
	public int Html_w() {return html_w;} public Xof_xfer_queue_base_fxt Html_w_(int v) {html_w = v; return this;} private int html_w = -1;
	public int Html_h() {return html_h;} public Xof_xfer_queue_base_fxt Html_h_(int v) {html_h = v; return this;} private int html_h = -1;
	public void ini_src_fils() {
		if (src_fils != null) {
			for (int i = 0; i < src_fils.length; i++) {
				Io_fil src_fil = src_fils[i];
				Io_mgr.Instance.SaveFilStr(src_fil.Url(), src_fil.Data());
			}
		}
	}
	public void tst_trg_fils() {
		for (int i = 0; i < trg_fils.length; i++) {
			Io_fil trg_fil = trg_fils[i];
			String data = Io_mgr.Instance.LoadFilStr(trg_fil.Url());
			Tfds.Eq_str_lines(trg_fil.Data(), data, trg_fil.Url().Raw());
		}		
	}
	public void	 save_(Io_fil v)						{Io_mgr.Instance.SaveFilStr(v.Url(), v.Data());}
	public Io_fil reg_(String url, String... v)	{return new Io_fil(Io_url_.mem_fil_(url), String_.Concat_lines_nl(v));}
	public Io_fil img_(String url_str, int w, int h)	{return file_(url_str, file_img(w, h));}
	public Io_fil svg_(String url_str, int w, int h)	{return file_(url_str, file_svg(w, h));}
	public Io_fil ogg_(String url_str)					{return file_(url_str, "");}
	public void fil_absent(String url)					{Tfds.Eq_false(Io_mgr.Instance.ExistsFil(Io_url_.mem_fil_(url)), "fil should not exist: {0}", url);}
	Io_fil file_(String url_str, String data)			{return new Io_fil(Io_url_.mem_fil_(url_str), data);}
	String file_img(int w, int h) {return String_.Format("{0},{1}", w, h);}
	String file_svg(int w, int h) {return String_.Format("<svg width=\"{0}\" height=\"{1}\" />", w, h);}
}
