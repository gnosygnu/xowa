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
package gplx.xowa.addons.searchs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*;
import gplx.xowa.addons.searchs.dbs.*; import gplx.xowa.addons.searchs.searchers.*; import gplx.xowa.addons.searchs.parsers.*; import gplx.xowa.addons.searchs.searchers.rslts.*; import gplx.xowa.addons.searchs.searchers.cbks.*;
import gplx.xowa.langs.cases.*;
public class Srch_search_addon implements Xoax_addon_itm {
	private final    Srch_search_mgr search_mgr;
	public Srch_search_addon(Xow_wiki wiki) {
		this.wiki_domain = wiki.Domain_bry();
		this.db_mgr = new Srch_db_mgr(wiki).Init();
		this.ttl_parser = new Srch_text_parser().Init_for_ttl(wiki.Case_mgr());
		this.search_mgr = new Srch_search_mgr(this, wiki, ttl_parser);
	}
	public byte[]				Addon__key()	{return Key_const;}		public static final    byte[] Key_const = Bry_.new_a7("xowa.search");
	public byte[]				Wiki_domain()	{return wiki_domain;}	private final    byte[] wiki_domain;
	public Srch_db_mgr			Db_mgr()		{return db_mgr;}		private final    Srch_db_mgr db_mgr;
	public Srch_text_parser		Ttl_parser()	{return ttl_parser;}	private final    Srch_text_parser ttl_parser;

	public void Search_cancel() {search_mgr.Search_cancel();}
	public void Search(Srch_search_qry qry, Srch_rslt_cbk cbk) {search_mgr.Search(qry, cbk);}
	public void Clear_rslts_cache() {search_mgr.Clear_rslts_cache();}

	public static final int Score_max = 1000000;
	public static final byte Wildcard__star = Byte_ascii.Star;
	public static Srch_search_addon Get(Xow_wiki wiki) {
		synchronized (wiki.App()) {	// THREAD: must synchronized on app, else 2 wikis with simultaneous search commands will get same addon
			Srch_search_addon rv = (Srch_search_addon)wiki.Addon_mgr().Itms__get_or_null(Key_const);
			if (rv == null) {
				Xoa_app_.Usr_dlg().Log_many("", "", "addons.init: ~{0}", Key_const);
				rv = new Srch_search_addon(wiki);
				wiki.Addon_mgr().Itms__add(rv);
			}
			return rv;
		}
	}
}
