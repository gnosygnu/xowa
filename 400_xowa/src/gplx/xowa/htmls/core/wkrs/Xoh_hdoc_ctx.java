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
package gplx.xowa.htmls.core.wkrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
import gplx.xowa.apps.urls.*;
import gplx.xowa.htmls.core.hzips.*; import gplx.xowa.htmls.core.wkrs.xndes.tags.*; import gplx.xowa.htmls.core.wkrs.xndes.dicts.*;
import gplx.xowa.files.*; import gplx.xowa.apps.fsys.*; import gplx.xowa.files.caches.*;
import gplx.xowa.wikis.domains.*; import gplx.xowa.wikis.ttls.*; import gplx.xowa.apps.metas.*;		
public class Xoh_hdoc_ctx {
	private byte[] fsys__file;
	public byte[]					Fsys__root()		{return fsys__root;} private byte[] fsys__root;
	public byte[]					Fsys__file__comm()	{return fsys__file__comm;} private byte[] fsys__file__comm;
	public byte[]					Fsys__file__wiki()	{return fsys__file__wiki;} private byte[] fsys__file__wiki;
	public Xoa_app					App()				{return app;} private Xoa_app app;
	public byte[]					Wiki__domain_bry()	{return wiki__domain_bry;} private byte[] wiki__domain_bry;
	public Xow_ttl_parser			Wiki__ttl_parser()	{return wiki__ttl_parser;} private Xow_ttl_parser wiki__ttl_parser;
	public Xoa_url_parser			Wiki__url_parser()	{return wiki__url_parser;} private Xoa_url_parser wiki__url_parser;
	public boolean						Xwiki_mgr__missing(byte[] domain){return app.Xwiki_mgr__missing(domain);}
	public Xou_cache_finder			File__mgr()			{return file__mgr;} private Xou_cache_finder file__mgr = Xou_cache_finder_.Noop; 
	public Xof_url_bldr				File__url_bldr()	{return file__url_bldr;} private final Xof_url_bldr file__url_bldr = new Xof_url_bldr();
	public byte[]					Page__url()			{return page__url;} private byte[] page__url;
	public Xoh_pool_mgr__hzip		Pool_mgr__hzip()	{return pool_mgr__hzip;} private final Xoh_pool_mgr__hzip pool_mgr__hzip = new Xoh_pool_mgr__hzip();
	public Xoh_pool_mgr__data		Pool_mgr__data()	{return pool_mgr__data;} private final Xoh_pool_mgr__data pool_mgr__data = new Xoh_pool_mgr__data();
	public Xoh_pool_mgr__wtr		Pool_mgr__wtr()		{return pool_mgr__wtr;} private final Xoh_pool_mgr__wtr pool_mgr__wtr = new Xoh_pool_mgr__wtr();
	public Xoh_stat_itm				Hzip__stat()		{return hzip__stat;} private final Xoh_stat_itm hzip__stat = new Xoh_stat_itm();
	public Xohz_tag_regy			Hzip__xnde__regy()	{return hzip__xnde__regy;} private final Xohz_tag_regy hzip__xnde__regy = Xohz_tag_regy_.New_dflt();
	public Xoh_xnde_dict_reg		Hzip__xnde__dict()	{return hzip__xnde__dict;} private final Xoh_xnde_dict_reg hzip__xnde__dict = new Xoh_xnde_dict_reg();
	public int						Uid__lnki_nxt()		{return ++uid__lnki;} private int uid__lnki;
	public int						Uid__gly__nxt()		{return ++uid__gly;} private int uid__gly;
	public boolean						Mode_is_diff()		{return mode_is_diff;} private boolean mode_is_diff;		public void Mode_is_diff_(boolean v) {mode_is_diff = v;}
	public void Init_by_app(Xoa_app app) {
		Xoa_fsys_mgr fsys_mgr = app.Fsys_mgr();
		this.app = app;
		this.fsys__root = fsys_mgr.Root_dir().To_http_file_bry();
		this.fsys__file = fsys_mgr.File_dir().To_http_file_bry();
		this.fsys__file__comm = Bry_.Add(fsys__file, Xow_domain_itm_.Bry__commons, Byte_ascii.Slash_bry);
		// Xou_cache_mgr cache_mgr = app.User().User_db_mgr().Cache_mgr();
		// if (cache_mgr != null) file__mgr = Xou_cache_finder_.New_db(cache_mgr);	// NOTE: this effectively only loads the cache db in app mode (and not in test mode)
		pool_mgr__hzip.Init();
	}
	public void Init_by_page(Xow_wiki wiki, byte[] page_url) {
		if (fsys__root == null) Init_by_app(wiki.App());	// LAZY INIT
		this.wiki__url_parser = wiki.Utl__url_parser();
		this.wiki__ttl_parser = wiki;
		this.wiki__domain_bry = wiki.Domain_bry();
		this.fsys__file__wiki = Bry_.Add(fsys__file, wiki__domain_bry, Byte_ascii.Slash_bry);
		this.page__url = page_url;
		this.Clear();
	}		
	private void Clear() {
		hzip__stat.Clear();
		this.uid__lnki = 1;	// NOTE: should be 0, but for historical reasons, 1st lnki starts at 2; EX: id='xowa_lnki_2'
		this.uid__gly = -1;
	}
	public static final int Invalid = -1;
}
