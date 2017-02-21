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
package gplx.xowa.htmls.core.wkrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
import gplx.xowa.apps.urls.*;
import gplx.langs.htmls.encoders.*; import gplx.xowa.htmls.core.hzips.*; import gplx.xowa.htmls.core.wkrs.xndes.tags.*; import gplx.xowa.htmls.core.wkrs.xndes.dicts.*;
import gplx.xowa.files.*; import gplx.xowa.files.repos.*; import gplx.xowa.files.caches.*; import gplx.xowa.apps.fsys.*;
import gplx.xowa.wikis.domains.*; import gplx.xowa.wikis.ttls.*; import gplx.xowa.apps.metas.*;		
public class Xoh_hdoc_ctx {
	private byte[] fsys__file, abrv__commons, abrv__wiki;
	public byte[]					Fsys__res()			{return fsys__res;} private byte[] fsys__res;
	public byte[]					Fsys__root()		{return fsys__root;} private byte[] fsys__root;
	public byte[]					Fsys__file__comm()	{return fsys__file__comm;} private byte[] fsys__file__comm;
	public byte[]					Fsys__file__wiki()	{return fsys__file__wiki;} private byte[] fsys__file__wiki;
	public byte[]					Fsys__file__math()	{return fsys__file__math;} private byte[] fsys__file__math;
	public Xof_repo_itm				Fsys__repo(boolean local)	{return local ? repo_local : repo_remote;} private Xof_repo_itm repo_remote, repo_local;
	public boolean						Fsys__is_wnt()		{return fsys__is_wnt;} private boolean fsys__is_wnt;
	public Xoa_app					App()				{return app;} private Xoa_app app;
	public byte[]					Wiki__domain_bry()	{return wiki__domain_bry;} private byte[] wiki__domain_bry;
	public Xow_domain_itm			Wiki__domain_itm()	{return wiki__domain_itm;} private Xow_domain_itm wiki__domain_itm;
	public Xow_ttl_parser			Wiki__ttl_parser()	{return wiki__ttl_parser;} private Xow_ttl_parser wiki__ttl_parser;
	public Xow_url_parser			Wiki__url_parser()	{return wiki__url_parser;} private Xow_url_parser wiki__url_parser;
	public boolean					Xwiki_mgr__missing(byte[] domain){return app.Xwiki_mgr__missing(domain);}
	public Xou_cache_finder			Cache_mgr()			{return cache_mgr;} private Xou_cache_finder cache_mgr = Xou_cache_finder_.Noop; 
	public byte[]					Cache__wiki_abrv(boolean repo_is_commons) {return repo_is_commons ? abrv__commons : abrv__wiki;}
	public Xof_url_bldr				File__url_bldr()	{return file__url_bldr;} private final    Xof_url_bldr file__url_bldr = Xof_url_bldr.new_v2();
	public Xoa_page					Page()				{return page;} private Xoa_page page;
	public byte[]					Page__url()			{return page__url;} private byte[] page__url;
	public Xoh_pool_mgr__hzip		Pool_mgr__hzip()	{return pool_mgr__hzip;} private final    Xoh_pool_mgr__hzip pool_mgr__hzip = new Xoh_pool_mgr__hzip();
	public Xoh_pool_mgr__data		Pool_mgr__data()	{return pool_mgr__data;} private final    Xoh_pool_mgr__data pool_mgr__data = new Xoh_pool_mgr__data();
	public Xoh_pool_mgr__wtr		Pool_mgr__wtr()		{return pool_mgr__wtr;} private final    Xoh_pool_mgr__wtr pool_mgr__wtr = new Xoh_pool_mgr__wtr();
	public Xoh_stat_itm				Hzip__stat()		{return hzip__stat;} private final    Xoh_stat_itm hzip__stat = new Xoh_stat_itm();
	public Xohz_tag_regy			Hzip__xnde__regy()	{return hzip__xnde__regy;} private final    Xohz_tag_regy hzip__xnde__regy = Xohz_tag_regy_.New_dflt();
	public Xoh_xnde_dict_reg		Hzip__xnde__dict()	{return hzip__xnde__dict;} private final    Xoh_xnde_dict_reg hzip__xnde__dict = new Xoh_xnde_dict_reg();
	public int						Uid__gly__nxt()		{return ++uid__gly;} private int uid__gly;
	public boolean						Mode_is_diff()		{return mode_is_diff;} private boolean mode_is_diff;		public void Mode_is_diff_(boolean v) {mode_is_diff = v;}
	public void Init_by_app(Xoa_app app) {
		Xoa_fsys_mgr fsys_mgr = app.Fsys_mgr();
		this.app = app;
		this.fsys__root = fsys_mgr.Root_dir().To_http_file_bry();
		this.fsys__file = fsys_mgr.File_dir().To_http_file_bry();
		this.fsys__file__comm = Bry_.Add(fsys__file, Xow_domain_itm_.Bry__commons, Byte_ascii.Slash_bry);
		this.fsys__file__math = Bry_.Add(fsys__file, Bry_.new_a7("math"), Byte_ascii.Slash_bry);
		this.fsys__res = gplx.core.envs.Op_sys.Cur().Tid_is_drd() ? Fsys__res__drd : fsys__root;
		this.fsys__is_wnt = gplx.core.envs.Op_sys.Cur().Tid_is_wnt();
		Xou_cache_mgr usr_cache_mgr = app.User().User_db_mgr().Cache_mgr();
		if (usr_cache_mgr != null && usr_cache_mgr.Enabled()) cache_mgr = Xou_cache_finder_.New_db(usr_cache_mgr);	// NOTE: this effectively only loads the cache db in app mode (and not in test mode)
		this.abrv__commons = Xow_domain_itm_.parse(Xow_domain_itm_.Bry__commons).Abrv_xo();
		pool_mgr__hzip.Init();
	}
	public void Init_by_page(Xow_wiki wiki, Xoa_page page) {
		if (fsys__root == null) Init_by_app(wiki.App());	// LAZY INIT
		this.wiki__url_parser = wiki.Utl__url_parser();
		this.wiki__ttl_parser = wiki;
		this.wiki__domain_bry = wiki.Domain_bry();
		this.wiki__domain_itm = wiki.Domain_itm();

		// init repos
		Xof_repo_pair[] pairs = wiki.File__repo_mgr().Repos_ary();
		int pairs_len = pairs.length;
		for (int i = 0; i < pairs_len; ++i) {
			Xof_repo_pair pair = pairs[i];
			Xof_repo_itm itm = pair.Trg();
			byte[] itm_domain = itm.Wiki_domain();
			if		(Bry_.Eq(itm_domain, wiki__domain_bry))
				repo_local = itm;
			else if (Bry_.Eq(itm_domain, Xow_domain_itm_.Bry__commons))
				repo_remote = pair.Trg();
		}

		this.abrv__wiki = wiki.Domain_itm().Abrv_xo();
		this.fsys__file__wiki = Bry_.Add(fsys__file, wiki__domain_bry, Byte_ascii.Slash_bry);
		this.page = page;
		this.page__url = page.Url_bry_safe();
		this.Clear();
	}		
	private void Clear() {
		hzip__stat.Clear();
		this.uid__gly = -1;
	}

	public void Test__cache__mgr_(Xou_cache_finder v) {this.cache_mgr = v;}
	public static final int Invalid = -1;
	private static final    byte[] Fsys__res__drd = Bry_.new_a7("file:///android_asset/xowa/");
}
