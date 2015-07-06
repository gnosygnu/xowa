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
import gplx.xowa.langs.*;
import gplx.xowa.html.hrefs.*;
public class Xow_xwiki_mgr implements GfoInvkAble {
	private Xowe_wiki wiki; private Xow_xwiki_mgr_srl srl;
	public Xow_xwiki_mgr() {}	// FIXME: current placeholder for viewer
	public Xow_xwiki_mgr(Xowe_wiki wiki, Gfo_url_parser url_parser) {
		this.wiki = wiki;
		srl = new Xow_xwiki_mgr_srl(this, url_parser);
	}		
	public Xow_lang_mgr Lang_mgr() {return lang_mgr;} private final Xow_lang_mgr lang_mgr = Xow_lang_mgr.dflt_();
	public int Len() {return list.Count();} private Ordered_hash list = Ordered_hash_.new_bry_(); private Hash_adp_bry hash = Hash_adp_bry.ci_ascii_();	// ASCII:lang_code
	public void Clear() {hash.Clear(); list.Clear();}
	public Xow_xwiki_itm Get_at(int i)								{return (Xow_xwiki_itm)list.Get_at(i);}
	public Xow_xwiki_itm Get_by_key(byte[] key)						{return (Xow_xwiki_itm)hash.Get_by_bry(key);}
	public Xow_xwiki_itm Get_by_mid(byte[] src, int bgn, int end)	{return (Xow_xwiki_itm)hash.Get_by_mid(src, bgn, end);}
	public Xow_xwiki_itm Add_full(String alias, String domain)		{return Add_full(Bry_.new_a7(alias), Bry_.new_a7(domain), null);}
	public Xow_xwiki_itm Add_full(byte[] alias, byte[] domain)		{return Add_full(alias, domain, null);}
	public Xow_xwiki_itm Add_full(byte[] alias, byte[] domain_bry, byte[] url_fmt) {
		int domain_tid = Byte_.Zero;
		int lang_id = -1;
		Xow_domain wiki_type = Xow_domain_.parse(domain_bry);
		domain_tid = wiki_type.Domain_tid();
		if (Bry_.Len_gt_0(wiki_type.Lang_key())) {	// domain_bry has lang (EX: "en.")
			Xol_lang_itm lang_itm = Xol_lang_itm_.Get_by_key(wiki_type.Lang_key());
			if (lang_itm == null) return null;	// unknown lang: do not add to wiki collection; EX: en1.wikipedia.org
			lang_id = lang_itm.Id();
		}
		Xow_xwiki_itm itm = Xow_xwiki_itm.new_(alias, url_fmt, lang_id, domain_tid, domain_bry);
		Add_itm(itm, null);
		return itm;
	}
	public void Sort_by_key() {
		list.Sort();
	}
	public Xow_domain[] Get_by_crt(Xow_domain cur, gplx.xowa.wikis.domains.crts.Xow_domain_crt_itm crt) {
		List_adp rv = List_adp_.new_();
		int len = this.Len();
		for (int i = 0; i < len; ++i) {
			Xow_xwiki_itm wiki = this.Get_at(i);
			if (!wiki.Offline()) continue;
			Xow_domain domain_itm = Xow_domain_.parse(wiki.Domain_bry());
			if (crt.Matches(cur, domain_itm)) rv.Add(domain_itm);
		}
		return (Xow_domain[])rv.To_ary_and_clear(Xow_domain.class);
	}
	public void Add_bulk(byte[] raw) {
		byte[][] rows = Bry_.Split(raw, Byte_ascii.Nl);
		int rows_len = rows.length;
		Hash_adp_bry lang_regy = Xol_lang_itm_.Regy();
		for (int i = 0; i < rows_len; i++) {
			byte[] row = rows[i]; if (Bry_.Len_eq_0(row)) continue;	// ignore blank rows
			Xow_xwiki_itm itm = Add_bulk_row(lang_regy, row);
			Add_itm(itm, null);
		}
	}
	public Xow_xwiki_itm Add_bulk_row(Hash_adp_bry lang_regy, byte[] row) {
		byte[][] flds = Bry_.Split(row, Byte_ascii.Pipe); int flds_len = flds.length;
		byte[] alias = Bry_.Empty, domain_bry = Bry_.Empty;
		for (int j = 0; j < flds_len; j++) {
			byte[] fld = flds[j];
			switch (j) {
				case 0:		alias			= fld; break;
				case 1:		domain_bry	= fld; break;
				case 2:		break;	// reserved for 0,1 (0=custom; 1=wmf)
				default:	throw Err_mgr._.unhandled_(j);
			}
		}
		Xow_domain domain = Xow_domain_.parse(domain_bry);
		int lang_id = Xol_lang_itm_.Id__unknown;
		if (Bry_.Len_gt_0(domain.Lang_key())) {
			Xol_lang_itm lang_itm = Xol_lang_itm_.Get_by_key(domain.Lang_key());
			if (lang_itm != null						// lang exists
				&& Bry_.Eq(alias, lang_itm.Key()))	// alias == lang.key; only assign langs to aliases that have lang key; EX: w|en.wikipedia.org; "w" alias should not be registered for "en"; DATE:2013-07-25
				lang_id = lang_itm.Id();
		}
		byte[] url_fmt = Bry_.Add(Xoh_href_parser.Href_https_bry, domain_bry, Xoh_href_parser.Href_wiki_bry, Arg_0);
		return Xow_xwiki_itm.new_(alias, url_fmt, lang_id, domain.Domain_tid(), domain_bry);
	}	static final byte[] Arg_0 = Bry_.new_a7("~{0}");
	String Exec_itms_print(byte[] raw) {
		Bry_fmtr fmtr = Bry_fmtr.new_bry_(raw, "wiki_key");//, "wiki_type_url", "wiki_lang", "wiki_name", "wiki_logo_url");
		Bry_bfr tmp_bfr = Xoa_app_.Utl__bfr_mkr().Get_k004();
		Hash_adp_bry seen = Hash_adp_bry.ci_ascii_();	// ASCII:url_domain; EX:en.wikipedia.org
		int wikis_len = list.Count();
		for (int i = 0; i < wikis_len; i++) {
			Xow_xwiki_itm itm = (Xow_xwiki_itm)list.Get_at(i);
			byte[] key = itm.Key_bry();
			if (Bry_.Eq(key, Xow_domain_type_.Key_bry_home)) continue;	// skip home
			byte[] domain = itm.Domain_bry();
			if (seen.Has(domain)) continue;
			seen.Add_as_key_and_val(domain);
			fmtr.Bld_bfr_many(tmp_bfr, key);
		}
		return tmp_bfr.To_str_and_rls();
	}
	public void Add_bulk_peers(byte[] raw) {
		byte[][] keys = Bry_.Split(raw, Byte_ascii.Tilde);
		int len = keys.length;
		Ordered_hash peers = Ordered_hash_.new_();
		Cfg_nde_root peer_root = wiki.Appe().Wiki_mgr().Groups();
		for (int i = 0; i < len; i++) {
			byte[] key = keys[i];
			Cfg_nde_obj peer_grp = peer_root.Grps_get(key);
			if (peer_grp == null)
				throw Err_mgr._.fmt_(GRP_KEY, "invalid_peer", "unknown peer group: ~{0}", String_.new_u8(key));
			else
				Cfg_nde_obj_.Fill_recurse(peers, peer_grp);
		}

		len = peers.Count();
		byte[] lang_key_bry = wiki.Lang().Key_bry();
		if (lang_key_bry == Xol_lang_itm_.Key__unknown) lang_key_bry = Xol_lang_.Key_en;	// default non-lang wikis to english
		String lang_key_str = String_.new_u8(lang_key_bry);
		int lang_id = Xol_lang_itm_.Get_by_key(lang_key_bry).Id();
		for (int i = 0; i < len; i++) {
			Xoac_wiki_itm wiki_itm = (Xoac_wiki_itm)peers.Get_at(i);
			byte[] wiki_name_bry = wiki_itm.Key_bry();
			String wiki_name = String_.new_u8(wiki_name_bry);
			String domain_str = null;
			int domain_tid = Xow_domain_type_.Get_type_as_tid(wiki_name_bry);
			switch (domain_tid) {
				case Xow_domain_type_.Tid_commons:
				case Xow_domain_type_.Tid_species:
				case Xow_domain_type_.Tid_meta:
				case Xow_domain_type_.Tid_incubator:			domain_str = String_.Format("{0}.wikimedia.org", wiki_name); break;			// EX: commons.wikimedia.org
				case Xow_domain_type_.Tid_wikidata:				domain_str = String_.Format("www.wikidata.org", wiki_name); break;			// EX: www.wikidata.org
				case Xow_domain_type_.Tid_mediawiki:			domain_str = String_.Format("www.mediawiki.org", wiki_name); break;
				case Xow_domain_type_.Tid_wmforg:	domain_str = String_.Format("wikimediafoundation.org", wiki_name); break;
				default:										domain_str = String_.Format("{0}.{1}.org", lang_key_str, wiki_name); break;	// EX: en.wiktionary.org
			}
			byte[] domain_bry = Bry_.new_u8(domain_str);
			Xowe_wiki lang_wiki = wiki.Appe().Wiki_mgr().Get_by_key_or_null(domain_bry);
			boolean offline_exists = lang_wiki != null;
			String fmt = String_.Format("http://" + domain_str + "/wiki/~{0}");
			int aliases_len = wiki_itm.Aliases().length;
			for (int j = 0; j < aliases_len; j++) {
				byte[] alias = wiki_itm.Aliases()[j];
				if (wiki.Ns_mgr().Names_get_or_null(alias, 0, alias.length) != null) continue;	// NOTE: do not add xwiki if alias matches namespace; EX: en.wiktionary.org has ns of "Wiktionary"; do not add alias of "wiktionary"; note that wikipedia does have an alias to wiktionary
				Xow_xwiki_itm xwiki = Xow_xwiki_itm.new_(alias, Bry_.new_u8(fmt), lang_id, domain_tid, domain_bry).Offline_(offline_exists);	// NOTE: domain_tid must be used, not wiki.Domain_tid; DATE:2014-09-14
				Add_itm(xwiki, null);
			}
		}
	}
	public void Add_bulk_langs(GfoMsg m) {
		byte[] grp_key = m.ReadBry("grp_key");
		byte[] wiki_type_name = m.ReadBryOr("wiki_type_name", null);
		int wiki_tid = wiki_type_name == null ? wiki.Domain_tid() : Xow_domain_type_.Get_type_as_tid(wiki_type_name);
		Add_bulk_langs(grp_key, wiki_tid);
	}
	public void Add_bulk_langs(byte[] grp_key) {Add_bulk_langs(grp_key, wiki.Domain_tid());}
	public void Add_bulk_langs(byte[] grp_key, int domain_tid) {
		Ordered_hash langs = wiki.Appe().Lang_mgr().Xto_hash(grp_key);
		int len = langs.Count();
		byte[] wiki_tid_name = Xow_domain_type_.Get_type_as_bry(domain_tid);
		String wiki_tid_name_str = String_.new_u8(wiki_tid_name);
		for (int i = 0; i < len; i++) {
			Xoac_lang_itm lang = (Xoac_lang_itm)langs.Get_at(i);
			String domain_str = String_.Format("{0}.{1}.org", String_.new_u8(lang.Key_bry()), wiki_tid_name_str); // EX: fr.wikipedia.org
			byte[] domain_bry = Bry_.new_u8(domain_str);
			Xowe_wiki lang_wiki = wiki.Appe().Wiki_mgr().Get_by_key_or_null(domain_bry);
			boolean offline_exists = lang_wiki != null;
			String url_fmt = String_.Format("http://" + domain_str + "/wiki/~{0}");
			int lang_id = Xol_lang_itm_.Get_by_key(lang.Key_bry()).Id();
			Xow_xwiki_itm xwiki = Xow_xwiki_itm.new_(lang.Key_bry(), Bry_.new_u8(url_fmt), lang_id, domain_tid, domain_bry).Offline_(offline_exists);
			Add_itm(xwiki, lang);
		}
		lang_mgr.Grps_sort();
	}	private static final String GRP_KEY = "xowa.wiki.xwikis";
	public void Add_itm(Xow_xwiki_itm itm) {Add_itm(itm, null);}
	private void Add_itm(Xow_xwiki_itm xwiki, Xoac_lang_itm lang) {
		byte[] xwiki_key = xwiki.Key_bry();
		if (	!hash.Has(xwiki.Key_bry())		// only register xwiki / lang pair once
			&&	lang != null)					// null lang should not be registered
			lang_mgr.Itms_reg(xwiki, lang);
		byte[] xwiki_domain = xwiki.Domain_bry();
		if (!domain_hash.Has(xwiki_domain)) {	// domain is new
			domain_hash.Add(xwiki_domain, xwiki_key);
			list.Add_if_dupe_use_nth(xwiki_key, xwiki);	// only add to list if domain is new; some wikis like commons will be added multiple times under different aliases (commons, c, commons.wikimedia.org); need to check domain and add only once DATE:2014-11-07
		}
		hash.Add_if_dupe_use_nth(xwiki_key, xwiki);
	}	private final Hash_adp_bry domain_hash = Hash_adp_bry.ci_ascii_(); 
	public void Add_many(byte[] v) {srl.Load_by_bry(v);}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_count))						return list.Count();
		else if	(ctx.Match(k, Invk_add_bulk))					Add_bulk(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_add_bulk_langs))				Add_bulk_langs(m);
		else if	(ctx.Match(k, Invk_add_bulk_peers))				Add_bulk_peers(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_add_many))					Add_many(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_itms_print))					return Exec_itms_print(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_clear))						this.Clear();
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	private static final String 
	  Invk_add_bulk = "add_bulk", Invk_add_bulk_langs = "add_bulk_langs", Invk_add_bulk_peers = "add_bulk_peers", Invk_add_many = "add_many"
	, Invk_itms_print = "itms_print", Invk_count = "count", Invk_clear = "clear"
	;
}
