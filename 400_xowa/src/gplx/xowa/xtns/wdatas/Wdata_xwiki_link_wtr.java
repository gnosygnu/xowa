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
package gplx.xowa.xtns.wdatas; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.json.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.xwikis.*; import gplx.xowa.xtns.wdatas.*; import gplx.xowa.xtns.wdatas.core.*; import gplx.xowa.xtns.wdatas.pfuncs.*;
public class Wdata_xwiki_link_wtr implements Bry_fmtr_arg {
	public Wdata_xwiki_link_wtr Page_(Xoae_page page) {this.page = page; return this;} private Xoae_page page;
	public void XferAry(Bry_bfr bfr, int idx) {
		List_adp slink_list = page.Slink_list();
		byte[] qid = Write_wdata_links(slink_list, page.Wikie(), page.Ttl(), page.Wdata_external_lang_links());
		if (slink_list.Count() > 0)
			page.Wikie().Xwiki_mgr().Lang_mgr().Html_bld(bfr, page.Wikie(), slink_list, qid);
	}
	public static byte[] Write_wdata_links(List_adp slink_list, Xowe_wiki wiki, Xoa_ttl ttl, Wdata_external_lang_links_data external_links_mgr) {
		try {
			switch (wiki.Domain_tid()) {
				case Xow_domain_type_.Tid_home:		// home will never be in wikidata
				case Xow_domain_type_.Tid_wikidata:	// wikidata will never be in wikidata
					return Qid_null;
			}
			Wdata_wiki_mgr wdata_mgr = wiki.Appe().Wiki_mgr().Wdata_mgr();
			Wdata_doc doc = wdata_mgr.Pages_get(wiki, ttl); if (doc == null) return Qid_null;	// no links
			boolean external_links_mgr_enabled = external_links_mgr.Enabled();
			Ordered_hash links = doc.Slink_list();
			Bry_bfr tmp_bfr = wiki.Appe().Utl__bfr_mkr().Get_k004();
			Xow_wiki_abrv wiki_abrv = new Xow_wiki_abrv();
			int len = links.Count();
			for (int i = 0; i < len; i++) {
				Wdata_sitelink_itm slink = (Wdata_sitelink_itm)links.Get_at(i);
				byte[] xwiki_key = slink.Site();
				Xow_wiki_abrv_.parse_(wiki_abrv, xwiki_key, 0, xwiki_key.length);
				if (wiki_abrv.Domain_tid() == Xow_wiki_abrv_.Tid_null) {
					wiki.Appe().Usr_dlg().Warn_many("", "", "unknown wiki in wikidata: ttl=~{0} wiki=~{1}", ttl.Page_db_as_str(), String_.new_u8(xwiki_key));
					continue;
				}
				if (wiki_abrv.Domain_tid() != wiki.Domain_tid()) continue;	// ignore wikis in a different domain; EX: looking at enwiki:Earth, and wikidata has dewikiquote; ignore dewikiquote; DATE:2014-06-21
				byte[] lang_key = wiki_abrv.Lang_itm().Key();
				if (external_links_mgr_enabled && external_links_mgr.Langs_hide(lang_key, 0, lang_key.length)) continue;
				tmp_bfr.Add(lang_key);
				tmp_bfr.Add_byte(Byte_ascii.Colon);
				tmp_bfr.Add(slink.Name());
				Xoa_ttl slink_ttl = Xoa_ttl.parse_(wiki, tmp_bfr.Xto_bry_and_clear());
				if (slink_ttl == null) continue;								// invalid ttl
				Xow_xwiki_itm xwiki_itm = slink_ttl.Wik_itm();
				if (	xwiki_itm == null									// not a known xwiki; EX: [[zzz:abc]]
					||	Bry_.Eq(xwiki_itm.Domain_bry(), wiki.Domain_bry())	// skip if same as self; i.e.: do not include links to enwiki if already in enwiki
					) continue;
				slink.Page_ttl_(slink_ttl);
				slink_list.Add(slink);
			}
			tmp_bfr.Mkr_rls();
			if (external_links_mgr_enabled && external_links_mgr.Sort())
				slink_list.Sort_by(Xoa_ttl_sorter._);
			return doc.Qid();
		} catch (Exception e) {Err_.Noop(e); return Qid_null;}
	}
	public static final byte[] Qid_null = Bry_.Empty;	// NOTE: return Empty, not null else Bry_fmtr will fail
}
class Xoa_ttl_sorter implements gplx.lists.ComparerAble {
	public int compare(Object lhsObj, Object rhsObj) {
		Xoa_ttl lhs = (Xoa_ttl)lhsObj, rhs = (Xoa_ttl)rhsObj;
		return Bry_.Compare(lhs.Raw(), rhs.Raw());
	}
	public static final Xoa_ttl_sorter _ = new Xoa_ttl_sorter(); Xoa_ttl_sorter() {}
}
