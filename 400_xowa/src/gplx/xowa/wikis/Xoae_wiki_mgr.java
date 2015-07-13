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
package gplx.xowa.wikis; import gplx.*; import gplx.xowa.*;
import gplx.xowa.langs.*; import gplx.xowa.xtns.wdatas.*; import gplx.xowa.wikis.domains.crts.*;
public class Xoae_wiki_mgr implements Xoa_wiki_mgr, GfoInvkAble {
	private Xoae_app app;
	private List_adp list = List_adp_.new_(); private Hash_adp_bry hash = Hash_adp_bry.ci_ascii_();	// ASCII:url_domain; EX:en.wikipedia.org
	public Xoae_wiki_mgr(Xoae_app app) {
		this.app = app;
		wiki_regy = new Xoa_wiki_regy(app);
		wdata_mgr = new Wdata_wiki_mgr(app);
	}
	public Xoa_wiki_regy Wiki_regy() {return wiki_regy;} private Xoa_wiki_regy wiki_regy;
	public Cfg_nde_root Groups() {return groups;} Cfg_nde_root groups = new Cfg_nde_root().Root_(new Xoac_wiki_grp(Bry_.Empty), Xoac_lang_grp.Make_grp, Bry_.Ary_empty);
	public Xow_script_mgr Scripts() {return scripts;} private Xow_script_mgr scripts = new Xow_script_mgr();
	public Wdata_wiki_mgr Wdata_mgr() {return wdata_mgr;} Wdata_wiki_mgr wdata_mgr;
	public void Init_by_app() {
		wdata_mgr.Init_by_app();
	}
	public int Count() {return hash.Count();}
	public void Del(byte[] key) {hash.Del(key);}
	public Xowe_wiki Get_at(int i) {return Int_.Between(i, 0, this.Count() - 1) ? (Xowe_wiki)list.Get_at(i) : null;}
	public Xowe_wiki Get_by_key_or_null(byte[] key) {return Bry_.Len_eq_0(key) ? null : (Xowe_wiki)hash.Get_by(key);}
	public Xowe_wiki Get_by_key_or_null(byte[] src, int bgn, int end) {return (Xowe_wiki)hash.Get_by_mid(src, bgn, end);}
	public Xow_wiki Get_by_key_or_make_init_y(byte[] key) {
		Xowe_wiki rv = this.Get_by_key_or_null(key);
		if (rv == null) rv = New_wiki(key);
		rv.Init_assert();
		return rv;
	}
	public Xow_wiki Get_by_key_or_make_init_n(byte[] key) {return Get_by_key_or_make(key);}
	public Xowe_wiki Get_by_key_or_make(byte[] key) {
		Xowe_wiki rv = this.Get_by_key_or_null(key);
		if (rv == null) rv = New_wiki(key);
		return rv;
	}
	public Xowe_wiki Wiki_commons() {
		Xowe_wiki rv = this.Get_by_key_or_null(Xow_domain_.Domain_bry_commons);
		if (rv != null) rv.Init_assert();
		return rv;
	}
	public Xowe_wiki Add(Xowe_wiki wiki) {
		Xowe_wiki rv = (Xowe_wiki)hash.Get_by_bry(wiki.Domain_bry());
		if (rv == null) {
			hash.Add(wiki.Domain_bry(), wiki);
			list.Add(wiki);
			rv = wiki;
		}
		return rv;
	}
	public void Free_mem(boolean clear_ctx) {
		int list_len = list.Count();
		for (int i = 0; i < list_len; i++) {
			Xowe_wiki wiki = (Xowe_wiki)list.Get_at(i);
//				wiki.Defn_cache().ReduceCache();
			if (clear_ctx) wiki.Ctx().Clear();	// NOTE: clear_ctx will reset toc and refs
			wiki.Cache_mgr().Page_cache().Free_mem_all();
			wiki.Cache_mgr().Tmpl_result_cache().Clear();
		}
	}
	public void Clear() {hash.Clear(); list.Clear();}
	public void Rls() {
		int len = list.Count();
		for (int i = 0; i < len; i++) {
			Xowe_wiki wiki = (Xowe_wiki)list.Get_at(i);
			wiki.Rls();
		}
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_get))					return Get_by_key_or_make(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_groups))					return groups;
		else if	(ctx.Match(k, Invk_scripts))				return scripts;
		else if	(ctx.Match(k, Invk_wdata))					return wdata_mgr;
		else if	(ctx.Match(k, Invk_len))					return this.Count();
		else if	(ctx.Match(k, Invk_get_at))					return this.Get_at(m.ReadInt("v"));
		else	return GfoInvkAble_.Rv_unhandled;
	}	private static final String Invk_get = "get", Invk_groups = "groups", Invk_scripts = "scripts", Invk_wdata = "wdata";
	private static final String Invk_len = "len", Invk_get_at = "get_at";
	private Xowe_wiki New_wiki(byte[] key) {
		Xow_domain domain_itm = Xow_domain_.parse(key);
		byte[] lang_key = domain_itm.Lang_key();
		if (lang_key == Xol_lang_itm_.Key__unknown) lang_key = Xol_lang_.Key_en;	// unknown langs default to english; note that this makes nonwmf english by default
		Xol_lang lang = 
			domain_itm.Domain_tid() == Xow_domain_type_.Tid_other
			?  new Xol_lang(app.Lang_mgr(), Xol_lang_.Key_en).Kwd_mgr__strx_(true)
			: app.Lang_mgr().Get_by_key_or_new(lang_key);			
		Xow_ns_mgr ns_mgr = Xow_ns_mgr_.default_(lang.Case_mgr());
		Io_url wiki_dir = app.Fsys_mgr().Wiki_dir().GenSubDir(domain_itm.Domain_str());
		Xowe_wiki rv = new Xowe_wiki(app, lang, ns_mgr, domain_itm, wiki_dir);
		Add(rv);
		return rv;
	}
}
