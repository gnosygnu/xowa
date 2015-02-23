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
public class Xow_lang_grp implements GfoInvkAble {
	public int Id() {return id;} private int id;
	public byte[] Key() {return key;} private byte[] key;
	public byte[] Name() {return name;} public Xow_lang_grp Name_(byte[] v) {name = v; return this;} private byte[] name;
	public int Sort_idx() {return sort_idx;} public Xow_lang_grp Sort_idx_(int v) {sort_idx = v; return this;} private int sort_idx = 0;
	public byte Sort_mode() {return sort_mode;} public Xow_lang_grp Sort_mode_(byte v) {sort_mode = v; return this;} private byte sort_mode = Sort_mode_page_name;
	public Xow_lang_itm[] Itms() {if (itms == null) itms = (Xow_lang_itm[])itm_list.Xto_ary(Xow_lang_itm.class); return itms;} private Xow_lang_itm[] itms;
	public int Itms_len() {return this.Itms().length;}
	public Xow_lang_itm Itms_get(int i) {return this.Itms()[i];}
	public void Itms_add(Xow_lang_itm itm) {itms = null; itm_list.Add(itm);} ListAdp itm_list = ListAdp_.new_();
	public void Itms_active_len_add_one_() {++itms_active_len;}
	public int Itms_active_len() {return itms_active_len;} private int itms_active_len;
	public void Itms_reset() {
		Xow_lang_itm[] itms_ary = this.Itms();
		int itms_len = itms_ary.length;
		for (int i = 0; i < itms_len; i++)
			itms_ary[i].Atrs_set(null, false, null);	// clear out pre-existing page names; needed b/c this struct is a singleton for entire wiki
		itms_active_len = 0;
	}
	public Bry_fmtr Html_all() {return html_all;} Bry_fmtr html_all; public Xow_lang_grp Html_all_(String s) {html_all = Bry_fmtr.new_(s, "all_name", "grps"); return this;}
	public byte[] Html_grp_bgn() {return html_grp_bgn;} private byte[] html_grp_bgn = Bry_.new_ascii_("\n    <tr>");
	public byte[] Html_grp_end() {return html_grp_end;} private byte[] html_grp_end = Bry_.new_ascii_("\n    </tr>");
	public Bry_fmtr Html_itm() {return html_itm;} Bry_fmtr html_itm; public Xow_lang_grp Html_itm_(String s) {html_itm = Bry_fmtr.new_(s, "lang_code", "lang_domain", "lang_name", "lang_href", "pagename_translation", "page_badge"); return this;}
	public void Html_bld(Bry_bfr bfr, Xowe_wiki wiki) {
		Xow_lang_itm[] itms_ary = this.Itms();
		if (sort_mode == Xow_lang_grp.Sort_mode_page_name)
			Array_.Sort(itms_ary, Xow_lang_itm_sorter_page_name._);
		int itms_ary_len = itms_ary.length;
		for (int i = 0; i < itms_ary_len; i++)
			itms_ary[i].Html_bld(bfr, wiki);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_owner))				return lang_mgr;
		else if	(ctx.Match(k, Invk_name_))				name = m.ReadBry("v");
		else if	(ctx.Match(k, Invk_sort_idx_))			sort_idx = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_sort_mode_))			sort_mode = (byte)m.ReadInt("v");
		else if	(ctx.Match(k, Invk_html_all_))			Html_all_(m.ReadStr("v"));
		else if	(ctx.Match(k, Invk_html_itm_))			Html_itm_(m.ReadStr("v"));
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_owner = "owner", Invk_name_ = "name_", Invk_sort_mode_ = "sort_mode_", Invk_sort_idx_ = "sort_idx_", Invk_html_all_ = "html_all_", Invk_html_itm_ = "html_itm_";
	public static final byte Sort_mode_lang_idx = 0, Sort_mode_lang_name = 1, Sort_mode_page_name = 2;
	public static Xow_lang_grp dflt_(Xow_lang_mgr lang_mgr, int id, byte[] key) {
		Xow_lang_grp rv = new Xow_lang_grp();
		rv.lang_mgr = lang_mgr; rv.id = id; rv.key = key; rv.name = key;
		rv.Html_all_(String_.Concat_lines_nl_skip_last
		( ""
		, "  <h4>~{all_name}</h4>"
		, "  <table style='width: 100%;'>~{grps}"
		, "  </table>"
		));
		rv.Html_itm_(String_.Concat_lines_nl_skip_last
		( ""
		, "      <td style='width: 10%; padding-bottom: 5px;'>~{lang_name}</td><td style='width: 20%; padding-bottom: 5px;'><li~{page_badge}><a hreflang=\"~{lang_code}\" title=\"~{pagename_translation}\" href=\"~{lang_href}\">~{pagename_translation}</a></li></td><td style='width: 3%; padding-bottom: 5px;'></td>"
		));
		return rv;
	}	private Xow_lang_grp() {}
	Xow_lang_mgr lang_mgr;
}
class Xow_lang_itm_sorter_page_name implements gplx.lists.ComparerAble {
	public int compare(Object lhsObj, Object rhsObj) {return Bry_.Compare(((Xow_lang_itm)lhsObj).Page_name(), ((Xow_lang_itm)rhsObj).Page_name());}
	public static final Xow_lang_itm_sorter_page_name _ = new Xow_lang_itm_sorter_page_name(); Xow_lang_itm_sorter_page_name() {}
}
class Xow_lang_grp_sorter_sort_idx implements gplx.lists.ComparerAble {
	public int compare(Object lhsObj, Object rhsObj) {return Int_.Compare(((Xow_lang_grp)lhsObj).Sort_idx(), ((Xow_lang_grp)rhsObj).Sort_idx());}
	public static final Xow_lang_grp_sorter_sort_idx _ = new Xow_lang_grp_sorter_sort_idx(); Xow_lang_grp_sorter_sort_idx() {}
}
