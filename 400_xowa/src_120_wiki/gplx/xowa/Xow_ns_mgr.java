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
package gplx.xowa; import gplx.*;
import gplx.core.primitives.*; import gplx.core.btries.*; import gplx.xowa.langs.cases.*;
import gplx.xowa.bldrs.installs.*;
import gplx.xowa.xtns.scribunto.*;
public class Xow_ns_mgr implements GfoInvkAble, gplx.lists.ComparerAble {
	private Ordered_hash id_hash = Ordered_hash_.new_();		// hash for retrieval by id
	private Hash_adp_bry name_hash;								// hash for retrieval by name; note that ns names are case-insensitive "File:" == "fILe:"
	private Hash_adp_bry tmpl_hash;								// hash for retrieval by name; PERF for templates
	private Ordered_hash aliases = Ordered_hash_.new_();		// hash to store aliases; used to populate name_hash;
	public Xow_ns_mgr(Xol_case_mgr case_mgr) {
		name_hash = Hash_adp_bry.ci_u8(case_mgr);
		tmpl_hash = Hash_adp_bry.ci_u8(case_mgr);
	}
	public Xow_ns_mgr Clear() {
		name_hash.Clear();
		id_hash.Clear();
		tmpl_hash.Clear();
		for (int i = 0; i < ords_len; i++)
			ords[i] = null;
		ords_len = 0;
		ns_count = 0;
		ns_file = null;
		return this;
	}
	public Btrie_slim_mgr Category_trie() {return category_trie;}		private Btrie_slim_mgr category_trie;
	public Xow_ns		Ns_main()				{return ns_main;}		private Xow_ns ns_main;
	public Xow_ns		Ns_template()			{return ns_template;}	private Xow_ns ns_template;
	public Xow_ns		Ns_file()				{return ns_file;}		private Xow_ns ns_file;
	public Xow_ns		Ns_category()			{return ns_category;}	private Xow_ns ns_category;
	public Xow_ns		Ns_portal()				{return ns_portal;}		private Xow_ns ns_portal;
	public Xow_ns		Ns_project()			{return ns_project;}	private Xow_ns ns_project;
	public Xow_ns		Ns_module()				{return ns_module;}		private Xow_ns ns_module;
	public Xow_ns		Ns_mediawiki()			{return ns_mediawiki;}	private Xow_ns ns_mediawiki;
	public int			Ns_page_id()			{return ns_page_id;}	public void Ns_page_id_(int v) {ns_page_id = v;} private int ns_page_id = Int_.Min_value;
	public int			Count() {return ns_count;} private int ns_count = 0;
	public Xow_ns[]		Ords_ary() {return ords;} private Xow_ns[] ords = new Xow_ns[Xow_ns_mgr_.Ordinal_max];
	public int			Ords_len() {return ords_len;} private int ords_len;
	public Xow_ns		Ords_get_at(int ord)	{return ords[ord];}
	public int			Ids_len()				{return id_hash.Count();}
	public Xow_ns		Ids_get_at(int idx)		{return (Xow_ns)id_hash.Get_at(idx);}
	public Xow_ns		Ids_get_or_null(int id) {return (Xow_ns)id_hash.Get_by(ns_hash_lkp.Val_(id));} private Int_obj_ref ns_hash_lkp = Int_obj_ref.zero_();
	private Xow_ns		Ids_get_or_empty(int id) {
		Xow_ns rv = Ids_get_or_null(id);
		return rv == null ? Ns__empty : rv;
	}	private static final Xow_ns Ns__empty = new Xow_ns(Int_.Max_value, Byte_.Zero, Bry_.Empty, false);
	public Xow_ns		Names_get_or_null(byte[] name_bry) {return this.Names_get_or_null(name_bry, 0, name_bry.length);}
	public Xow_ns		Names_get_or_null(byte[] src, int bgn, int end) {
		Object rv = name_hash.Get_by_mid(src, bgn, end);
		return rv == null ? null : ((Xow_ns_mgr_name_itm)rv).Ns();
	}
	public Xow_ns		Names_get_or_main(byte[] name_bry) {
		Xow_ns rv = this.Names_get_or_null(name_bry, 0, name_bry.length);
		return rv == null ? this.Ns_main() : rv;
	}
	public Object		Names_get_w_colon(byte[] src, int bgn, int end) {	// NOTE: get ns for a name with a ":"; EX: "Template:A" should return "Template" ns
		int colon_pos = Bry_finder.Find_fwd(src, Byte_ascii.Colon, bgn, end);
		if (colon_pos == Bry_.NotFound) return null;	// name does not have ":"; return;
		Object rv = name_hash.Get_by_mid(src, bgn, colon_pos);
		return rv == null ? null : ((Xow_ns_mgr_name_itm)rv).Ns();
	}
	public int			Tmpls_get_w_colon(byte[] src, int bgn, int end)  {	// NOTE: get length of template name with a ":"; EX: "Template:A" returns 10; PERF
		int colon_pos = Bry_finder.Find_fwd(src, Byte_ascii.Colon, bgn, end); 
		if (colon_pos == Bry_.NotFound) return Bry_.NotFound;
		Object o = tmpl_hash.Get_by_mid(src, bgn, colon_pos + 1);	// +1 to include colon_pos
		return o == null ? Bry_.NotFound : ((byte[])o).length;
	}
	public void			Aliases_clear() {aliases.Clear();}		
	public Xow_ns_mgr	Aliases_add(int ns_id, String name) {
		KeyVal kv = KeyVal_.new_(name, Int_obj_val.new_(ns_id));
		aliases.Add_if_dupe_use_nth(name, kv);
		return this;
	}
	public Xow_ns_mgr Init() {
		this.Ords_sort();
		this.Rebuild_hashes();
		return this;
	}
	public void Init_w_defaults() {
		this.Add_defaults();
		this.Init();
	}
	private void Rebuild_hashes() {
		name_hash.Clear(); tmpl_hash.Clear();
		for (int i = 0; i < ords_len; i++) {
			Xow_ns ns = ords[i];
			if (ns == null) continue;	// TEST: allow gaps in ns numbers; see Talk_skip test and related
			if (ns.Id() == Xow_ns_.Id_project_talk) Fix_project_talk(ns);	// NOTE: handle $1 talk as per Language.php!fixVariableInNamespace; placement is important as it must go before key registration but after ord sort
			Rebuild_hashes__add(name_hash, ns, ns.Name_bry());
			if (ns.Id_tmpl()) tmpl_hash.Add(ns.Name_db_w_colon(), ns.Name_db_w_colon());
		}
		int aliases_len = aliases.Count();
		for (int i = 0; i < aliases_len; i++) {
			KeyVal kv = (KeyVal)aliases.Get_at(i);
			int ns_id = ((Int_obj_val)kv.Val()).Val();
			Xow_ns ns = Ids_get_or_null(ns_id); if (ns == null) continue; // happens when alias exists, but not ns; EX: test has Image alias, but not File alias; should not happen "live" but don't want to fail
			ns.Aliases_add(kv.Key());	// register alias with official ns; EX: "Image" will be placed in "File"'s .Aliases
			byte[] alias_bry = Bry_.new_u8(kv.Key());
			Rebuild_hashes__add(name_hash, ns, alias_bry);
			if (ns.Id_tmpl()) {
				byte[] alias_name = Bry_.new_u8(kv.Key());
				alias_name = Bry_.Add(alias_name, Byte_ascii.Colon);
				tmpl_hash.Add_if_dupe_use_nth(alias_name, alias_name);
			}
		}
	}
	private void Fix_project_talk(Xow_ns ns) {
		byte[] ns_name = ns.Name_bry();
		if (Bry_finder.Find_fwd(ns.Name_bry(), Project_talk_fmt_arg)== Bry_.NotFound) return; // no $1 found; exit
		Xow_ns project_ns = ords[ns.Ord_subj_id()];
		if (project_ns == null) return;	// should warn or throw error; for now just exit
		ns.Name_bry_(Bry_.Replace(ns_name, Project_talk_fmt_arg, project_ns.Name_bry()));
	}	private static final byte[] Project_talk_fmt_arg = Bry_.new_a7("$1");
	private void Rebuild_hashes__add(Hash_adp_bry hash, Xow_ns ns, byte[] key) {
		Xow_ns_mgr_name_itm ns_itm = new Xow_ns_mgr_name_itm(key, ns);
		hash.Add_if_dupe_use_nth(key, ns_itm);
		if (Bry_finder.Find_fwd(key, Byte_ascii.Underline) != Bry_.NotFound)	// ns has _; add another entry for space; EX: Help_talk -> Help talk
			hash.Add_if_dupe_use_nth(Bry_.Replace(key, Byte_ascii.Underline, Byte_ascii.Space), ns_itm);
	}
	public Xow_ns_mgr Add_defaults() { // NOTE: needs to happen after File ns is added; i.e.: cannot be put in Xow_ns_mgr() {} ctor
		Aliases_add(Xow_ns_.Id_file		, "Image");			// REF.MW: Setup.php; add "Image", "Image talk" for backward compatibility; note that MW hardcodes Image ns as well
		Aliases_add(Xow_ns_.Id_file_talk, "Image_talk");
		Aliases_add(Xow_ns_.Id_project	, "Project");		// always add "Project" ns (EX: Wikipedia is name for en.wikipedia.org; not sure if MW hardcodes, but it is in messages
		Aliases_add(gplx.xowa.xtns.scribunto.Scrib_xtn_mgr.Ns_id_module, "Module");		// always add "Module" ns; de.wikipedia.org has "Modul" defined in siteinfo.xml, but also uses Module
		return this;
	}
	public Xow_ns_mgr Add_new(int nsId, String name) {return Add_new(nsId, Bry_.new_u8(name), Xow_ns_case_.Id_1st, false);}	// for tst_ constructor
	public Xow_ns_mgr Add_new(int ns_id, byte[] name, byte caseMatchId, boolean alias) {
		Bry_.Replace_all_direct(name, Byte_ascii.Space, Byte_ascii.Underline);	// standardize on _; EX: User talk -> User_talk; DATE:2013-04-21
		Xow_ns ns = new Xow_ns(ns_id, caseMatchId, name, alias);
		switch (ns_id) {
			case Xow_ns_.Id_main:					ns_main = ns; break;
			case Xow_ns_.Id_template:				ns_template = ns; break;
			case Xow_ns_.Id_portal:					ns_portal = ns; break;
			case Xow_ns_.Id_project:				ns_project = ns; break;
			case Xow_ns_.Id_mediawiki:				ns_mediawiki = ns; break;
			case Scrib_xtn_mgr.Ns_id_module:		ns_module = ns; break;
			case Xow_ns_.Id_file:					if (ns_file == null) ns_file = ns; break;	// NOTE: if needed, else Image will become the official ns_file
			case Xow_ns_.Id_category:
				ns_category = ns;
				if (category_trie == null)
					category_trie = Btrie_slim_mgr.new_(ns.Case_match() == Xow_ns_case_.Id_all);
				category_trie.Add_obj(ns.Name_bry(), this);
				break;
		}
		++ns_count;
		if (!id_hash.Has(ns_hash_lkp.Val_(ns_id)))		// NOTE: do not add if already exists; avoids alias
			id_hash.Add(Int_obj_ref.new_(ns.Id()), ns);
		name_hash.Add_if_dupe_use_nth(ns.Name_bry(), new Xow_ns_mgr_name_itm(ns.Name_bry(), ns));
		return this;
	}
	public int compare(Object lhsObj, Object rhsObj) {
		Xow_ns lhs = (Xow_ns)lhsObj;
		Xow_ns rhs = (Xow_ns)rhsObj;
		return Int_.Compare(lhs.Id(), rhs.Id());
	}
	private void Ords_sort() {
		int ords_cur = 0;
		int ns_len = id_hash.Count();
		id_hash.Sort_by(this);
		// assert that all items are grouped in pairs of subj, talk; note that subj is even and talk is odd
		int nxt_ns_id = Int_.Min_value;
		int prv_ns_id = Int_.Min_value;
		for (int i = 0; i < ns_len; i++) {
			Xow_ns ns = (Xow_ns)id_hash.Get_at(i);
			int ns_id = ns.Id();
			if (ns_id < 0						// ignore negative ns (which don't have subj/talk pairing) 			
				|| ns.Is_alias()				// ignore alias
				) continue;			
			if (nxt_ns_id != Int_.Min_value) {	// nxt_ns_id is set
				if (nxt_ns_id != ns_id)			// prv was subj, but cur does not match expected talk_id; create talk for prv subj
					Ords_sort_add(nxt_ns_id);
				nxt_ns_id = Int_.Min_value;		// always reset value
			}
			if (ns_id % 2 == 0)					// subj
				nxt_ns_id = ns_id + 1;			// anticipate nxt_ns_id
			else {								// talk
				if (prv_ns_id != ns_id - 1)		// prv was not subj for cur; create subj for current talk
					Ords_sort_add(ns_id - 1);
			}
			prv_ns_id = ns_id;
		}
		if (nxt_ns_id != Int_.Min_value)			// handle trailing ns_id; EX: 0, 1, 2; need to make 3
			Ords_sort_add(nxt_ns_id);
		
		// sort again b/c new ns may have been added
		id_hash.Sort_by(this);
		ns_len = id_hash.Count();
		// assign ords; assert that subj has even ordinal index
		ords_len = 0;
		for (int i = 0; i < ns_len; i++) {
			Xow_ns ns = (Xow_ns)id_hash.Get_at(i);
			int ns_id = ns.Id();
			if (ns.Is_alias()) continue;		// ignore alias
			if (ns_id < 0) {}
			else {
				if (ns_id % 2 == 0) {			// subj
					if (ords_cur % 2 != 0) {	// current ordinal is not even; skip 
						++ords_len;
						++ords_cur;
					}
				}
			}
			ns.Ord_(ords_cur);
			ords[ords_cur++] = ns;
			++ords_len;
		}
	}
	private void Ords_sort_add(int ns_id) {
		this.Add_new(ns_id, Bry_.new_by_int(ns_id), Xow_ns_case_.Id_1st, false);	// NOTE: name and case_match are mostly useless defaults; note that in theory this proc should not be called (all siteInfos should be well-formed) but just in case, create items now so that Get_by_ord() does not fail
	}
	public byte[] Bld_ttl_w_ns(Bry_bfr bfr, boolean text_form, boolean literalize, int ns_id, byte[] ttl) {
		if (ns_id == Xow_ns_.Id_main) return ttl;
		Xow_ns ns = Ids_get_or_null(ns_id); if (ns == null) {Xoa_app_.Usr_dlg().Warn_many("", "", "ns_mgr:uknown ns_id; ns_id=~{0} ttl=~{1}", ns_id, ttl); return ttl;}
		if (literalize) bfr.Add_byte(Byte_ascii.Colon);	// NOTE: add : to literalize ns; EX: [[Category:A]] will get thrown into category list; [[:Category:A]] will print
		bfr.Add(text_form ? ns.Name_txt_w_colon() : ns.Name_db_w_colon());
		bfr.Add(ttl);
		return bfr.Xto_bry_and_clear();
	}
	class Xow_ns_mgr_name_itm {
		public Xow_ns_mgr_name_itm(byte[] name, Xow_ns ns) {this.name = name; this.name_len = name.length; this.ns = ns;}
		public byte[] Name() {return name;} private byte[] name;
		public int Name_len() {return name_len;} private int name_len;
		public Xow_ns Ns() {return ns;} private Xow_ns ns;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_load))				Xow_cfg_wiki_core.Load_ns_(this, m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_clear))				this.Clear();
		else if	(ctx.Match(k, Invk_add_alias_bulk))		Exec_add_alias_bulk(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_get_by_id_or_new))	return this.Ids_get_or_empty(m.ReadInt("v"));	// NOTE: called by #cfg files for setting Subpages_enabled; if ns doesn't exist, returning empty is fine; DATE:2014-02-15
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_add_alias_bulk = "add_alias_bulk", Invk_get_by_id_or_new = "get_by_id_or_new";
	public static final String Invk_load = "load", Invk_clear = "clear";
	private void Exec_add_alias_bulk(byte[] raw) {
		byte[][] lines = Bry_.Split(raw, Byte_ascii.Nl);
		int lines_len = lines.length;
		for (int i = 0; i < lines_len; i++) {
			byte[] line = lines[i];
			if (line.length == 0) continue;
			byte[][] flds = Bry_.Split(line, Byte_ascii.Pipe);
			int cur_id = Bry_.To_int_or(flds[0], Int_.Min_value);
			this.Aliases_add(cur_id, String_.new_u8(flds[1]));
		}
		Ords_sort();
	}
}
