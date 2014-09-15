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
package gplx.xowa.xtns; import gplx.*; import gplx.xowa.*;
import gplx.core.btries.*;
import gplx.xowa.xtns.cite.*; import gplx.xowa.xtns.imaps.*; import gplx.xowa.xtns.relatedSites.*; import gplx.xowa.xtns.insiders.*; import gplx.xowa.xtns.proofreadPage.*;
public class Xow_xtn_mgr implements GfoInvkAble {
	private OrderedHash regy = OrderedHash_.new_bry_();
	public int Count() {return regy.Count();}
	public Cite_xtn_mgr Xtn_cite() {return xtn_cite;} private Cite_xtn_mgr xtn_cite;
	public Imap_xtn_mgr Xtn_imap() {return xtn_imap;} private Imap_xtn_mgr xtn_imap;
	public Sites_xtn_mgr Xtn_sites() {return xtn_sites;} private Sites_xtn_mgr xtn_sites;
	public Insider_xtn_mgr Xtn_insider() {return xtn_insider;} private Insider_xtn_mgr xtn_insider;
	public Pp_xtn_mgr Xtn_proofread() {return xtn_proofread;} private Pp_xtn_mgr xtn_proofread;
	public Xow_xtn_mgr Ctor_by_app(Xoa_app app) {	// NOTE: needed for options
		Add(app, new Cite_xtn_mgr());
		Add(app, new Imap_xtn_mgr());
		Add(app, new Sites_xtn_mgr());
		Add(app, new Insider_xtn_mgr());
		Add(app, new Pp_xtn_mgr());
		Add(app, new gplx.xowa.xtns.scribunto.Scrib_xtn_mgr());
		Add(app, new gplx.xowa.xtns.gallery.Gallery_xtn_mgr());
		Add(app, new gplx.xowa.xtns.poems.Poem_xtn_mgr());
		Add(app, new gplx.xowa.xtns.hieros.Hiero_xtn_mgr());
		Add(app, new gplx.xowa.xtns.scores.Score_xtn_mgr());
		Add(app, new gplx.xowa.xtns.listings.Listing_xtn_mgr());
		Add(app, new gplx.xowa.xtns.titleBlacklists.Blacklist_xtn_mgr());
		Add(app, new gplx.xowa.xtns.pfuncs.scribunto.Pfunc_xtn_mgr());
		return this;
	}
	public Xow_xtn_mgr Ctor_by_wiki(Xow_wiki wiki) {
		Xoa_app app = wiki.App();
		Xow_xtn_mgr app_xtn_mgr = app.Xtn_mgr();
		int regy_len = app_xtn_mgr.Count();
		for (int i = 0; i < regy_len; i++) {
			Xox_mgr proto = (Xox_mgr)app_xtn_mgr.Get_at(i);
			Xox_mgr mgr = proto.Clone_new();
			mgr.Xtn_ctor_by_wiki(wiki);
			regy.Add(mgr.Xtn_key(), mgr);
			Set_members(mgr);
		}
		return this;
	}
	public void Init_by_app(Xoa_app app) {
		int regy_len = regy.Count();
		for (int i = 0; i < regy_len; i++) {
			Xox_mgr mgr = (Xox_mgr)regy.FetchAt(i);
			mgr.Xtn_init_by_app(app);
		}
	}
	public Xow_xtn_mgr Init_by_wiki(Xow_wiki wiki) {
		int regy_len = regy.Count();
		for (int i = 0; i < regy_len; i++) {
			Xox_mgr mgr = (Xox_mgr)regy.FetchAt(i);
			mgr.Xtn_init_by_wiki(wiki);
		}
		return this;
	}
	public Xox_mgr Get_at(int i) {return (Xox_mgr)regy.FetchAt(i);}
	public Xox_mgr Get_or_fail(byte[] key) {Object rv = regy.Fetch(key); if (rv == null) throw Err_.new_fmt_("unknown xtn: {0}", String_.new_utf8_(key)); return (Xox_mgr)rv;}
	private Xox_mgr Add(Xoa_app app, Xox_mgr xtn) {
		xtn.Xtn_ctor_by_app(app);
		regy.Add(xtn.Xtn_key(), xtn);
		Set_members(xtn);
		return xtn;
	}
	private void Set_members(Xox_mgr mgr) {
		byte[] xtn_key = mgr.Xtn_key();
		Object o = xtn_tid_trie.Match_exact(xtn_key, 0, xtn_key.length); if (o == null) return;
		switch (((Byte_obj_val)o).Val()) {
			case Tid_cite:			xtn_cite = (Cite_xtn_mgr)mgr; break;
			case Tid_sites:			xtn_sites = (Sites_xtn_mgr)mgr; break;
			case Tid_insider:		xtn_insider = (Insider_xtn_mgr)mgr; break;
			case Tid_imap:			xtn_imap = (Imap_xtn_mgr)mgr; break;
			case Tid_proofread:		xtn_proofread = (Pp_xtn_mgr)mgr; break;
		}
	}
	private static final byte Tid_cite = 0, Tid_sites = 1, Tid_insider = 2, Tid_imap = 3, Tid_proofread = 4;
	private static final Btrie_slim_mgr xtn_tid_trie = Btrie_slim_mgr.cs_()
	.Add_bry_bval(Cite_xtn_mgr.XTN_KEY		, Tid_cite)
	.Add_bry_bval(Sites_xtn_mgr.XTN_KEY		, Tid_sites)
	.Add_bry_bval(Insider_xtn_mgr.XTN_KEY	, Tid_insider)
	.Add_bry_bval(Imap_xtn_mgr.XTN_KEY		, Tid_imap)
	.Add_bry_bval(Pp_xtn_mgr.XTN_KEY		, Tid_proofread)
	;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_get))				return Get_or_fail(m.ReadBry("v"));
		else return GfoInvkAble_.Rv_unhandled;
	}	private static final String Invk_get = "get";
}
