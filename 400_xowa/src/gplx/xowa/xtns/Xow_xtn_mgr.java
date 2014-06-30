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
import gplx.xowa.xtns.gallery.*; import gplx.xowa.xtns.scribunto.*; import gplx.xowa.xtns.poems.*; import gplx.xowa.xtns.hiero.*;
import gplx.xowa.xtns.scores.*; import gplx.xowa.xtns.listings.*; import gplx.xowa.xtns.titleBlacklists.*;
public class Xow_xtn_mgr implements GfoInvkAble {
	private OrderedHash regy = OrderedHash_.new_bry_();
	public int Count() {return regy.Count();}
	public Xow_xtn_mgr Ctor_by_app(Xoa_app app) {	// NOTE: needed for options
		Add(app, new Scrib_xtn_mgr());
		Add(app, new Gallery_xtn_mgr());
		Add(app, new Poem_xtn_mgr());
		Add(app, new Hiero_xtn_mgr());
		Add(app, new Score_xtn_mgr());
		Add(app, new Listing_xtn_mgr());
		Add(app, new Blacklist_xtn_mgr());
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
		return xtn;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_get))				return Get_or_fail(m.ReadBry("v"));
		else return GfoInvkAble_.Rv_unhandled;
	}	private static final String Invk_get = "get";
}
