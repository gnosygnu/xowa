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
package gplx.xowa.apps.apis.xowa.xtns; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.apis.*; import gplx.xowa.apps.apis.xowa.*;
import gplx.xowa.xtns.wbases.*;
public class Xoapi_wikibase implements Gfo_invk, Gfo_evt_mgr_owner {
	public Xoapi_wikibase() {
		evt_mgr = new Gfo_evt_mgr(this);
	}
	public Gfo_evt_mgr Evt_mgr() {return evt_mgr;} private Gfo_evt_mgr evt_mgr;
	public byte[][] Core_langs() {return core_langs;} private byte[][] core_langs = Bry_.Ary("en");
	public byte[][] Sort_langs() {return sort_langs;} private byte[][] sort_langs = Bry_.Ary("en", "de", "es", "fr", "it", "nl", "pl", "ru", "sv");
	public byte[] Link_wikis() {return link_wikis;} private byte[] link_wikis = Bry_.new_a7("enwiki");
	public void Init_by_app(Xoae_app app) {
		app.Cfg().Bind_many_app(this, Cfg__core_langs, Cfg__link_wikis, Cfg__sort_langs);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Cfg__core_langs))	 			{core_langs = m.ReadBryAry("v", Byte_ascii.Semic); Gfo_evt_mgr_.Pub_val(this, Evt_core_langs_changed, core_langs);}
		else if	(ctx.Match(k, Cfg__sort_langs))	 			{sort_langs = m.ReadBryAry("v", Byte_ascii.Semic); Gfo_evt_mgr_.Pub_val(this, Evt_sort_langs_changed, sort_langs);}
		else if	(ctx.Match(k, Cfg__link_wikis))	 			{link_wikis = m.ReadBry("v"); Gfo_evt_mgr_.Pub_val(this, Evt_link_wikis_changed, link_wikis);}
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	public static final String 
	  Evt_core_langs_changed = "core_langs_changed"
	, Evt_link_wikis_changed = "link_wikis_changed"
	, Evt_sort_langs_changed = "sort_langs_changed"
	;
	private static final String
	  Cfg__core_langs = "xowa.addon.wikibase.pages.core_langs"
	, Cfg__link_wikis = "xowa.addon.wikibase.pages.link_wikis"
	, Cfg__sort_langs = "xowa.addon.wikibase.pages.sort_langs"
	;
}
