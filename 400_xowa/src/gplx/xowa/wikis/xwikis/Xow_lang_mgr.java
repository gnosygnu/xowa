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
package gplx.xowa.wikis.xwikis; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.xowa.langs.*; import gplx.xowa.wikis.xwikis.*; import gplx.xowa.apis.xowa.html.*; import gplx.xowa.xtns.wdatas.core.*;
import gplx.xowa.html.hrefs.*;
import gplx.xowa.langs.cfgs.*;
public class Xow_lang_mgr {
	Xow_lang_mgr() {
		int len = Xol_lang_itm_.Id__max;
		itms = new Xow_lang_itm[len];
	}
	public Bry_fmtr Html_div() {return html_div;} private final Bry_fmtr html_div = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( "<div id=\"xowa-lang\">"
	, "  <h5>~{toggle_btn} (links: ~{len}) ~{wikidata_link}</h5>"
	, "  <div~{toggle_hdr}>~{grps}"
	, "  </div>"
	, "</div>"
	), "len", "wikidata_link", "toggle_btn", "toggle_hdr", "grps");
	public Bry_fmtr Html_wikidata_link() {return html_wikidata_link;} private final Bry_fmtr html_wikidata_link = Bry_fmtr.new_(" (<a href=\"/site/www.wikidata.org/wiki/~{qid}\">wikidata</a>)", "qid");
	public void Clear() {hash.Clear();}
	public void Itms_reg(Xow_xwiki_itm xwiki, Xoac_lang_itm lang) {
		int lang_id = xwiki.Lang_id();
		Xoac_lang_grp ini_grp = lang.Grp();
		Xow_lang_grp grp = (Xow_lang_grp)hash.Get_by(ini_grp.Key_bry());
		if (grp == null) {
			grp = Grps_get_or_new(ini_grp.Key_bry());
			grp.Name_(ini_grp.Name_bry());
			grp.Sort_idx_(ini_grp.Sort_idx());
		}
		Xow_lang_itm itm = itms[lang_id];
		if (itm == null) {
			itm = new Xow_lang_itm(grp, xwiki, lang);
			itms[lang_id] = itm;
		}
		grp.Itms_add(itm);
	}
	public int Grps_len() {return hash.Count();}
	public Xow_lang_grp Grps_get_at(int i) {return (Xow_lang_grp)hash.Get_at(i);}
	Xow_lang_grp Grps_get_or_new(byte[] key) {
		Xow_lang_grp rv = (Xow_lang_grp)hash.Get_by(key);
		if (rv == null) {
			int id = hash.Count();
			rv = Xow_lang_grp.dflt_(this, id, key);
			rv.Sort_idx_(id);
			hash.Add(key, rv);
			rv.Name_(key);
		}
		return rv;
	}	private Ordered_hash hash = Ordered_hash_.new_bry_();
	public void Grps_sort() {hash.Sort_by(Xow_lang_grp_sorter_sort_idx._);}
	public void Html_bld(Bry_bfr bfr, Xowe_wiki wiki, List_adp slink_list, byte[] qid) {
		int grp_len = hash.Count();
		for (int i = 0; i < grp_len; i++) {
			Xow_lang_grp grp = (Xow_lang_grp)hash.Get_at(i);
			grp.Itms_reset();
		}
		int slink_len = slink_list.Count();
		for (int i = 0; i < slink_len; i++) {
			Wdata_sitelink_itm slink = (Wdata_sitelink_itm)slink_list.Get_at(i);
			Xoa_ttl ttl = slink.Page_ttl();
			Xow_xwiki_itm xwiki = ttl.Wik_itm();
			int lang_id = xwiki.Lang_id();
			Xow_lang_itm itm = itms[lang_id];	// NOTE: handles ttls like [[fr:]] and [[:fr;]] which have an empty Page_txt, but a valued Full_txt_raw
			byte[] ttl_bry = ttl.Page_txt_w_anchor();
			boolean empty_xwiki = false;
			if (Bry_.Len_eq_0(ttl_bry)) {
				ttl_bry = wiki.Parser_mgr().Ctx().Cur_page().Ttl().Page_txt();
				empty_xwiki = true;
			}			
			itm.Atrs_set(ttl_bry, empty_xwiki, slink.Badges());
		}
		html_bldr.Init(this, wiki, slink_list, slink_len, qid);
		html_bldr.XferAry(bfr, -1);
	}	private Xow_lang_itm[] itms = null; Xow_lang_html html_bldr = new Xow_lang_html();
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_get))	return Grps_get_or_new(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_sort))	Grps_sort();
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_get = "get", Invk_sort = "sort";
	public static Xow_lang_mgr dflt_() {return new Xow_lang_mgr();}
}
class Xow_lang_html implements Bry_fmtr_arg {
	private Xow_lang_mgr lang_mgr; Xowe_wiki wiki; List_adp ttl_list; private byte[] qid;
	private Xoapi_toggle_itm toggle_itm;
	private int stage = 0;
	public Xow_lang_html Init(Xow_lang_mgr lang_mgr, Xowe_wiki wiki, List_adp ttl_list, int ttl_list_len, byte[] qid) {
		this.lang_mgr = lang_mgr; this.wiki = wiki; this.ttl_list = ttl_list; this.qid = qid;
		toggle_itm = wiki.Appe().Api_root().Html().Page().Toggle_mgr().Get_or_new("wikidata-langs");
		return this;
	}
	public void XferAry(Bry_bfr bfr, int idx) {
		switch (stage) {
			case 0: {
				stage = 1;
				Bry_bfr tmp_bfr = wiki.Utl__bfr_mkr().Get_b128().Mkr_rls();
				byte[] msg_lang = wiki.Msg_mgr().Val_by_id(Xol_msg_itm_.Id_page_lang_header);
				byte[] wikidata_link = Bry_.Len_eq_0(qid) ? Bry_.Empty : lang_mgr.Html_wikidata_link().Bld_bry_many(tmp_bfr, qid);
				toggle_itm.Init(msg_lang);
				lang_mgr.Html_div().Bld_bfr_many(bfr, ttl_list.Count(), wikidata_link, toggle_itm.Html_toggle_btn(), toggle_itm.Html_toggle_hdr(), this);
				stage = 0;
				break;
			}
			case 1:
				stage = 2;
				int grps_len = lang_mgr.Grps_len();
				for (int i = 0; i < grps_len; i++) {
					grp = lang_mgr.Grps_get_at(i);
					if (grp.Itms_active_len() == 0) continue;	// skip grps with no items
					grp.Html_all().Bld_bfr_many(bfr, grp.Name(), this);
				}
				stage = 1;
				break;
			case 2:
				int itms_len = grp.Itms_len();
				int grp_counter = 0; boolean row_opened = false;
				for (int i = 0; i < itms_len; i++) {
					Xow_lang_itm itm = grp.Itms_get(i);
					if (!itm.Page_name_has()) continue;
					if (grp_counter == 0) {
						bfr.Add(grp.Html_grp_bgn());
						row_opened = true;
					}
					byte[] lang_key = itm.Lang_key();
					byte[] domain = itm.Lang_domain();
					byte[] page_name = itm.Page_name();
					byte[] local_name = itm.Lang_name();
					byte[] badge_cls = Badge_cls(tmp_bfr, itm.Page_badges());
					if (wiki.Appe().Usere().Wiki().Xwiki_mgr().Get_by_key(domain) == null)
						tmp_bfr.Add(Xoh_href_.Bry__https).Add(domain).Add(Xoh_href_.Bry__wiki);
					else
						tmp_bfr.Add(Xoh_href_.Bry__site).Add(domain).Add(Xoh_href_.Bry__wiki);
					if (!itm.Empty_xwiki()) tmp_bfr.Add(page_name);
					grp.Html_itm().Bld_bfr_many(bfr, lang_key, domain, local_name, tmp_bfr.Xto_bry_and_clear(), page_name, badge_cls);
					++grp_counter;
					if (grp_counter == 3) {
						row_opened = false;
						bfr.Add(grp.Html_grp_end());
						grp_counter = 0;
					}
				}
				if (row_opened) {
					bfr.Add(grp.Html_grp_end());
					grp_counter = 0;
				}
				stage = 2;
				break;
		}
	}	private Xow_lang_grp grp; Bry_bfr tmp_bfr = Bry_bfr.reset_(255);
	private static byte[] Badge_cls(Bry_bfr bfr, byte[][] badges) {
		if (badges == null) badges = Bry_.Ary_empty;
		int badges_len = badges.length;
		bfr.Add(Cls_bgn);
		if (badges_len == 0)
			bfr.Add(Badge_none_cls);
		else {
			for (int i = 0; i < badges_len; ++i) {
				if (i != 0) bfr.Add_byte_comma();
				byte[] badge = badges[i];
				byte[] badge_cls = (byte[])badges_hash.Get_by_bry(badge);
				if (badge_cls == null) Gfo_usr_dlg_.I.Warn_many("", "", "unknown badge: badge=~{0}", String_.new_u8(badge));
				else bfr.Add(badge_cls);
			}
		}
		bfr.Add_byte_apos();
		return bfr.Xto_bry_and_clear();
	}
	private static final byte[]
	  Badge_none_cls	= Bry_.new_a7("badge-none")
	, Cls_bgn			= Bry_.new_a7(" class='")
	;
	private static Hash_adp_bry badges_hash = Hash_adp_bry.ci_a7()
	.Add_str_obj("Q17437798", Bry_.new_a7("badge-goodarticle"))
	.Add_str_obj("Q17437796", Bry_.new_a7("badge-featuredarticle"))
	.Add_str_obj("Q17559452", Bry_.new_a7("badge-recommendedarticle"))
	.Add_str_obj("Q17506997", Bry_.new_a7("badge-featuredlist"))
	.Add_str_obj("Q17580674", Bry_.new_a7("badge-featuredportal"))
	;
}