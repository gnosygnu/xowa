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
package gplx.xowa.xtns; import gplx.*; import gplx.xowa.*;
import gplx.core.primitives.*; import gplx.core.btries.*;
import gplx.xowa.xtns.cites.*; import gplx.xowa.xtns.imaps.*; import gplx.xowa.xtns.relatedSites.*; import gplx.xowa.xtns.proofreadPage.*; import gplx.xowa.xtns.wbases.*;
import gplx.xowa.xtns.insiders.*; import gplx.xowa.xtns.indicators.*; import gplx.xowa.xtns.pagebanners.*;
public class Xow_xtn_mgr implements Gfo_invk {
	private Ordered_hash regy = Ordered_hash_.New_bry();
	public int Count() {return regy.Count();}
	public Cite_xtn_mgr Xtn_cite() {return xtn_cite;} private Cite_xtn_mgr xtn_cite;
	public Imap_xtn_mgr Xtn_imap() {return xtn_imap;} private Imap_xtn_mgr xtn_imap;
	public Sites_xtn_mgr Xtn_sites() {return xtn_sites;} private Sites_xtn_mgr xtn_sites;
	public Insider_xtn_mgr Xtn_insider() {return xtn_insider;} private Insider_xtn_mgr xtn_insider;
	public Indicator_xtn_mgr Xtn_indicator() {return xtn_indicator;} private Indicator_xtn_mgr xtn_indicator;
	public Pp_xtn_mgr Xtn_proofread() {return xtn_proofread;} private Pp_xtn_mgr xtn_proofread;
	public Wdata_xtn_mgr Xtn_wikibase() {return xtn_wikibase;} private Wdata_xtn_mgr xtn_wikibase;
	public Pgbnr_xtn_mgr Xtn_pgbnr() {return xtn_pgbnr;} private Pgbnr_xtn_mgr xtn_pgbnr;
	public Xow_xtn_mgr Ctor_by_app(Xoae_app app) {	// NOTE: needed for options
		Add(app, new Cite_xtn_mgr());
		Add(app, new Imap_xtn_mgr());
		Add(app, new Sites_xtn_mgr());
		Add(app, new Insider_xtn_mgr());
		Add(app, new Indicator_xtn_mgr());
		Add(app, new Pp_xtn_mgr());
		Add(app, new Wdata_xtn_mgr());
		Add(app, new Pgbnr_xtn_mgr());
		Add(app, new gplx.xowa.xtns.scribunto.Scrib_xtn_mgr());
		Add(app, new gplx.xowa.xtns.gallery.Gallery_xtn_mgr());
		Add(app, new gplx.xowa.xtns.poems.Poem_xtn_mgr());
		Add(app, new gplx.xowa.xtns.hieros.Hiero_xtn_mgr());
		Add(app, new gplx.xowa.xtns.scores.Score_xtn_mgr());
		Add(app, new gplx.xowa.xtns.listings.Listing_xtn_mgr());
		Add(app, new gplx.xowa.xtns.titleBlacklists.Blacklist_xtn_mgr());
		Add(app, new gplx.xowa.xtns.pfuncs.scribunto.Pfunc_xtn_mgr());
		Add(app, new gplx.xowa.xtns.flaggedRevs.Flagged_revs_xtn_mgr());
		return this;
	}
	public Xow_xtn_mgr Ctor_by_wiki(Xowe_wiki wiki) {
		Xoae_app app = wiki.Appe();
		Xow_xtn_mgr app_xtn_mgr = app.Xtn_mgr();
		int regy_len = app_xtn_mgr.Count();
		for (int i = 0; i < regy_len; i++) {
			Xox_mgr proto = (Xox_mgr)app_xtn_mgr.Get_at(i);
			Xox_mgr mgr = proto.Xtn_clone_new();
			mgr.Xtn_ctor_by_wiki(wiki);
			regy.Add(mgr.Xtn_key(), mgr);
			Set_members(mgr);
		}
		return this;
	}
	public void Init_by_app(Xoae_app app) {
		int regy_len = regy.Count();
		for (int i = 0; i < regy_len; i++) {
			Xox_mgr mgr = (Xox_mgr)regy.Get_at(i);
			mgr.Xtn_init_by_app(app);
		}
	}
	public Xow_xtn_mgr Init_by_wiki(Xowe_wiki wiki) {
		int regy_len = regy.Count();
		for (int i = 0; i < regy_len; i++) {
			Xox_mgr mgr = (Xox_mgr)regy.Get_at(i);
			mgr.Xtn_init_by_wiki(wiki);
		}
		return this;
	}
	public Xox_mgr Get_at(int i) {return (Xox_mgr)regy.Get_at(i);}
	public Xox_mgr Get_or_fail(byte[] key) {Object rv = regy.Get_by(key); if (rv == null) throw Err_.new_wo_type("unknown xtn", "key", String_.new_u8(key)); return (Xox_mgr)rv;}
	private Xox_mgr Add(Xoae_app app, Xox_mgr xtn) {
		xtn.Xtn_ctor_by_app(app);
		regy.Add(xtn.Xtn_key(), xtn);
		Set_members(xtn);
		return xtn;
	}
	private void Set_members(Xox_mgr mgr) {
		byte[] xtn_key = mgr.Xtn_key();
		Object o = xtn_tid_trie.Match_exact(xtn_key, 0, xtn_key.length); if (o == null) return;
		switch (((Byte_obj_val)o).Val()) {
			case Tid_cite:			xtn_cite		= (Cite_xtn_mgr)mgr; break;
			case Tid_sites:			xtn_sites		= (Sites_xtn_mgr)mgr; break;
			case Tid_insider:		xtn_insider		= (Insider_xtn_mgr)mgr; break;
			case Tid_indicator:		xtn_indicator	= (Indicator_xtn_mgr)mgr; break;
			case Tid_imap:			xtn_imap		= (Imap_xtn_mgr)mgr; break;
			case Tid_proofread:		xtn_proofread	= (Pp_xtn_mgr)mgr; break;
			case Tid_wikibase:		xtn_wikibase	= (Wdata_xtn_mgr)mgr; break;
			case Tid_pgbnr:			xtn_pgbnr		= (Pgbnr_xtn_mgr)mgr; break;
		}
	}
	private static final byte Tid_cite = 0, Tid_sites = 1, Tid_insider = 2, Tid_imap = 3, Tid_proofread = 4, Tid_wikibase = 5, Tid_indicator = 6, Tid_pgbnr = 7;
	private static final    Btrie_slim_mgr xtn_tid_trie = Btrie_slim_mgr.cs()
	.Add_bry_byte(Cite_xtn_mgr.XTN_KEY				, Tid_cite)
	.Add_bry_byte(Sites_xtn_mgr.XTN_KEY				, Tid_sites)
	.Add_bry_byte(Insider_xtn_mgr.XTN_KEY			, Tid_insider)
	.Add_bry_byte(Indicator_xtn_mgr.XTN_KEY			, Tid_indicator)
	.Add_bry_byte(Imap_xtn_mgr.XTN_KEY				, Tid_imap)
	.Add_bry_byte(Pp_xtn_mgr.XTN_KEY				, Tid_proofread)
	.Add_bry_byte(Wdata_xtn_mgr.XTN_KEY				, Tid_wikibase)
	.Add_bry_byte(Pgbnr_xtn_mgr.Xtn_key_static		, Tid_pgbnr)
	;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_get))				return Get_or_fail(m.ReadBry("v"));
		else return Gfo_invk_.Rv_unhandled;
	}	private static final String Invk_get = "get";
}
