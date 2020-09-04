/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.wikis;

import gplx.Bry_;
import gplx.GfoMsg;
import gplx.Gfo_invk;
import gplx.Gfo_invk_;
import gplx.GfsCtx;
import gplx.Hash_adp_bry;
import gplx.Int_;
import gplx.Io_url;
import gplx.List_adp;
import gplx.List_adp_;
import gplx.String_;
import gplx.xowa.Xoae_app;
import gplx.xowa.Xow_wiki;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.addons.wikis.directorys.dbs.Xowdir_db_mgr;
import gplx.xowa.addons.wikis.directorys.dbs.Xowdir_wiki_itm;
import gplx.xowa.langs.Xol_lang_itm;
import gplx.xowa.langs.Xol_lang_itm_;
import gplx.xowa.langs.Xol_lang_stub_;
import gplx.xowa.wikis.domains.Xow_domain_itm;
import gplx.xowa.wikis.domains.Xow_domain_itm_;
import gplx.xowa.wikis.domains.Xow_domain_tid_;
import gplx.xowa.wikis.metas.Xow_script_mgr;
import gplx.xowa.wikis.nss.Xow_ns_mgr;
import gplx.xowa.wikis.nss.Xow_ns_mgr_;
import gplx.xowa.xtns.wbases.Wdata_wiki_mgr;

public class Xoae_wiki_mgr implements Xoa_wiki_mgr, Gfo_invk {
	private final    Xoae_app app;
	private final    List_adp list = List_adp_.New(); private final    Hash_adp_bry hash = Hash_adp_bry.ci_a7();	// ASCII:url_domain; EX:en.wikipedia.org
	private Xowdir_db_mgr db_mgr;
	public Xoae_wiki_mgr(Xoae_app app) {
		this.app = app;
		this.wiki_regy = new Xoa_wiki_regy(app);
		this.wdata_mgr = new Wdata_wiki_mgr(app);
	}
	public Xoa_wiki_regy Wiki_regy() {return wiki_regy;} private final    Xoa_wiki_regy wiki_regy;
	public Xow_script_mgr Scripts() {return scripts;} private final    Xow_script_mgr scripts = new Xow_script_mgr();
	public Wdata_wiki_mgr Wdata_mgr() {return wdata_mgr;} private final    Wdata_wiki_mgr wdata_mgr;
	public Xowe_wiki Wiki_commons() {
		synchronized (this) {	// LOCK:app-level; DATE:2016-07-06
			Xowe_wiki rv = (Xowe_wiki)this.Get_by_or_null(Xow_domain_itm_.Bry__commons);
			if (rv != null) rv.Init_assert();
			return rv;
		}
	}
	public void Init_by_app() {
		this.db_mgr = new Xowdir_db_mgr(app.User().User_db_mgr().Conn());
		wdata_mgr.Init_by_app();
	}
	public int			Count()							{return list.Count();}
	public boolean			Has(byte[] key)					{return hash.Has(key);}
	public Xow_wiki		Get_at(int idx)					{return (Xow_wiki)list.Get_at(idx);}
	public Xowe_wiki	Get_at_or_null(int i)			{return Int_.Between(i, 0, this.Count() - 1) ? (Xowe_wiki)list.Get_at(i) : null;}
	public Xow_wiki		Get_by_or_null(byte[] key)		{return Bry_.Len_eq_0(key) ? null : (Xowe_wiki)hash.Get_by(key);}
	public Xow_wiki		Get_by_or_make_init_y(byte[] key) {
		synchronized (this) {	// LOCK:app-level; DATE:2016-07-06
			Xowe_wiki rv = (Xowe_wiki)this.Get_by_or_null(key); if (rv == null) rv = Make_and_add(key);
			rv.Init_assert();
			return rv;
		}
	}
	public Xow_wiki		Get_by_or_make_init_n(byte[] key) {return Get_by_or_make(key);}
	public Xowe_wiki	Get_by_or_make(byte[] key) {
		Xowe_wiki rv = (Xowe_wiki)this.Get_by_or_null(key); if (rv == null) rv = Make_and_add(key);
		return rv;
	}
	public void Add(Xow_wiki wiki) {
		if (hash.Get_by_bry(wiki.Domain_bry()) != null) return;	// if already there, don't add again; basically, Add_if_dupe_use_1st
		hash.Add(wiki.Domain_bry(), wiki);
		list.Add(wiki);
	}
	public Xowe_wiki Make_and_add(byte[] domain_bry) {
		// get wiki_root_url from either user_wiki or /xowa/wiki/
		Xowdir_wiki_itm user_wiki_itm = db_mgr == null 
			? null	// TEST:
			: db_mgr.Tbl__wiki().Select_by_key_or_null(String_.new_u8(domain_bry));

		Xowe_wiki rv = null;
		if (user_wiki_itm == null) {
			rv = (Xowe_wiki)Make(domain_bry, app.Fsys_mgr().Wiki_dir().GenSubDir(String_.new_a7(domain_bry)));
			Add(rv);
		}
		else {
			rv = gplx.xowa.addons.wikis.directorys.specials.items.bldrs.Xow_wiki_factory.Load_personal(app, domain_bry, user_wiki_itm.Url().OwnerDir());
		}
		return rv;
	}
	public Xow_wiki Make(byte[] domain_bry, Io_url wiki_root_dir) {
		// init domain
		Xow_domain_itm domain_itm = Xow_domain_itm_.parse(domain_bry);

		// get lang from domain; if not wmf, default to en
		byte[] lang_key = domain_itm.Lang_actl_key();
		if (lang_key == Xol_lang_stub_.Key__unknown) lang_key = Xol_lang_itm_.Key_en;	// unknown langs default to english; note that this makes nonwmf english by default
		Xol_lang_itm lang = app.Lang_mgr().Get_by_or_new(lang_key);			
		if (domain_itm.Domain_type_id() == Xow_domain_tid_.Tid__other) {
			lang = Xol_lang_itm.New(app.Lang_mgr(), Xol_lang_itm_.Key_en).Kwd_mgr__strx_(true);	// create a new english lang, but enable strx functions; DATE:2015-08-23
			Xol_lang_itm_.Lang_init(lang);
		}

		// load ns from site_meta
		Xow_ns_mgr ns_mgr = app.Dbmeta_mgr().Ns__get_or_load(domain_bry);
		if (ns_mgr.Ids_len() == 0) ns_mgr = Xow_ns_mgr_.default_(lang.Case_mgr());	// non-wmf wikis will use default ns_mgr

		return new Xowe_wiki(app, lang, ns_mgr, domain_itm, wiki_root_dir);
	}
	public Xow_wiki		Import_by_url(Io_url url) {return Xoa_wiki_mgr_.Import_by_url(app, this, url);}
	public void Del(byte[] key) {hash.Del(key);}
	public void Clear() {hash.Clear(); list.Clear();}
	public void Free_mem(boolean clear_ctx) {
		int list_len = list.Count();
		for (int i = 0; i < list_len; i++) {
			Xowe_wiki wiki = (Xowe_wiki)list.Get_at(i);
//				wiki.Defn_cache().ReduceCache();
			if (clear_ctx) wiki.Parser_mgr().Ctx().Clear_all();	// NOTE: clear_ctx will reset toc and refs
			wiki.Cache_mgr().Page_cache().Free_mem(true);
		}
	}
	public void Rls() {
		int len = list.Count();
		for (int i = 0; i < len; i++) {
			Xowe_wiki wiki = (Xowe_wiki)list.Get_at(i);
			wiki.Rls();
		}
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_get))							return Get_by_or_make(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_scripts))						return scripts;
		else if	(ctx.Match(k, Invk_wdata))							return wdata_mgr;
		else if	(ctx.Match(k, Invk_len))							return this.Count();
		else if	(ctx.Match(k, Invk_get_at))							return this.Get_at_or_null(m.ReadInt("v"));
		else if	(ctx.Match(k, Xoa_wiki_mgr_.Invk__import_by_url))	return this.Import_by_url(m.ReadIoUrl("v"));
		else	return Gfo_invk_.Rv_unhandled;
	}	private static final String Invk_get = "get", Invk_scripts = "scripts", Invk_wdata = "wdata";
	private static final    String Invk_len = "len", Invk_get_at = "get_at";
}
