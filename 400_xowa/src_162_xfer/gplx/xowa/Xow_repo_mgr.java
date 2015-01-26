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
import gplx.xowa.wikis.*; import gplx.xowa.files.*;
public class Xow_repo_mgr implements GfoInvkAble {
	public Xow_repo_mgr(Xow_wiki wiki) {
		this.wiki = wiki;
		Xoa_app app = wiki.App();
		xfer_mgr = new Xof_xfer_mgr(app.File_mgr());
		page_finder = new Xofw_wiki_wkr_base(wiki, app.Wiki_mgr());
	}	private Xow_wiki wiki;
	public Xof_xfer_mgr Xfer_mgr() {return xfer_mgr;} private Xof_xfer_mgr xfer_mgr;
	public Xofw_wiki_finder Page_finder() {return page_finder;} public Xow_repo_mgr Page_finder_(Xofw_wiki_finder v) {page_finder = v; return this;} private Xofw_wiki_finder page_finder;
	public int Repos_len() {return repos.Count();} ListAdp repos = ListAdp_.new_();
	public Xof_repo_pair Repos_get_by_wiki(byte[] wiki) {
		int len = repos.Count();
		for (int i = 0; i < len; i++) {
			Xof_repo_pair pair = (Xof_repo_pair)repos.FetchAt(i);
			if (Bry_.Eq(wiki, pair.Wiki_key()))
				return pair;
		}
		return null;
	}
	public Xof_repo_pair Repos_get_at(int i) {return (Xof_repo_pair)repos.FetchAt(i);}
	public Xof_repo_pair Repos_get_by_id(int id) {
		int len = repos.Count();
		for (int i = 0; i < len; i++) {
			Xof_repo_pair pair = (Xof_repo_pair)repos.FetchAt(i);
			if (pair.Repo_id() == id) return pair;
		}
		return null;
	}
	public boolean Xfer_by_meta(Xof_xfer_itm xfer_itm, Xof_xfer_queue queue) {
		byte[] ttl = xfer_itm.Lnki_ttl();
		Xof_meta_itm meta_itm = xfer_itm.Meta_itm();
		boolean chk_all = false;
		byte[] src_wiki_key = wiki.Domain_bry();
		if (meta_itm.State_new()) {
			byte rslt = Xfer_by_meta__find_file(ttl, meta_itm, wiki.Domain_bry());
			switch (rslt) {
				case Xof_meta_itm.Tid_null: xfer_itm.Trg_repo_idx_(0); chk_all = true; break;	// NOTE: src_wiki_key becomes wiki.Key_bry() for sake of simplicity
				case Xof_meta_itm.Tid_main: xfer_itm.Trg_repo_idx_(Xof_meta_itm.Repo_same); break;
				case Xof_meta_itm.Tid_ptr:	src_wiki_key = Xfer_by_meta__find_main_ptr(meta_itm, xfer_itm); break;
				case Xof_meta_itm.Tid_vrtl: src_wiki_key = Xfer_by_meta__find_main_vrtl(meta_itm, xfer_itm); break;
			}
		}
		else {
			Xof_repo_itm src_repo = meta_itm.Repo_itm(wiki);
			src_wiki_key = src_repo.Wiki_key();
			xfer_itm.Trg_repo_idx_(meta_itm.Vrtl_repo());	// NOTE: set trg_repo_idx b/c xfer_mgr will always set meta_itm.Vrtl_repo() with trg_repo_idx
		}
		if (meta_itm.Ptr_ttl_exists())
			xfer_itm.Set__ttl(meta_itm.Ttl(), meta_itm.Ptr_ttl());
		boolean main_exists_unknown = src_wiki_key == null;		// WORKAROUND/HACK: SEE:NOTE_1:reset_main_exists
		boolean rv = Xfer_by_meta__exec(chk_all, xfer_itm, meta_itm, src_wiki_key, queue, false);
		if (!rv && (!chk_all && !main_exists_unknown)) {	// xfer failed even with page found in wiki; try again, but chk all
			rv = Xfer_by_meta__exec(true, xfer_itm, meta_itm, src_wiki_key, queue, true);
		}
		return rv;
	}
	byte[] Xfer_by_meta__find_main_ptr(Xof_meta_itm meta_itm, Xof_xfer_itm xfer_itm) {
		byte[] redirect = meta_itm.Ptr_ttl(); int redirect_tries = 0;
		byte[] md5 = Xof_xfer_itm_.Md5_(redirect);
		while (true) {
			boolean found = page_finder.Locate(tmp_rslt, repos, redirect);
			if (!found) return null;
			Xow_wiki trg_wiki = wiki;
			int repo_idx = tmp_rslt.Repo_idx();
			byte[] trg_wiki_key = Bry_.Empty;
			if (repo_idx != Xof_meta_itm.Repo_unknown) {
				trg_wiki_key = wiki.File_mgr().Repo_mgr().Repos_get_at(repo_idx).Wiki_key();
				trg_wiki = wiki.App().Wiki_mgr().Get_by_key_or_make(trg_wiki_key);
			}
			Xof_meta_itm redirect_meta = trg_wiki.File_mgr().Meta_mgr().Get_itm_or_new(redirect, md5);
			if (tmp_rslt.Redirect() == Xop_redirect_mgr.Redirect_null_bry) {
				if (redirect_meta.State_new()) {
					if (repo_idx == Xof_meta_itm.Repo_unknown) {
//							meta_itm.Vrtl_repo_(Xof_meta_itm.Repo_same);
						xfer_itm.Trg_repo_idx_(Xof_meta_itm.Repo_same);
					}
					else {
						if (!Bry_.Eq(trg_wiki_key, wiki.Domain_bry())) {
//								meta_itm.Vrtl_repo_(tmp_rslt.Repo_idx());
							xfer_itm.Trg_repo_idx_(tmp_rslt.Repo_idx());
						}
						else {
//								meta_itm.Vrtl_repo_(Xof_meta_itm.Repo_same);
							xfer_itm.Trg_repo_idx_(Xof_meta_itm.Repo_same);
						}
					}
				}
				return trg_wiki_key;
			}
			redirect = tmp_rslt.Redirect();
			if (++redirect_tries > Xop_redirect_mgr.Redirect_max_allowed) return null;
		}
	}
	byte[] Xfer_by_meta__find_main_vrtl(Xof_meta_itm meta_itm, Xof_xfer_itm xfer_itm) {
		int repo_idx = meta_itm.Vrtl_repo();
		if (repo_idx == Xof_meta_itm.Repo_unknown) return null;;
		Xof_repo_itm trg_repo = wiki.File_mgr().Repo_mgr().Repos_get_at(repo_idx).Trg();
		xfer_itm.Trg_repo_idx_(repo_idx);
		return trg_repo.Wiki_key();
	}
	boolean Xfer_by_meta__exec(boolean chk_all, Xof_xfer_itm xfer_itm, Xof_meta_itm meta_itm, byte[] main_wiki_key, Xof_xfer_queue queue, boolean second_chance) {
		int repos_len = repos.Count();
		for (int i = 0; i < repos_len; i++) {								// iterate over all repo pairs
			Xof_repo_pair pair = (Xof_repo_pair)repos.FetchAt(i);
			Xof_repo_itm pair_src = pair.Src();
			boolean main_wiki_key_is_pair_src = Bry_.Eq(main_wiki_key, pair_src.Wiki_key());
			if (	(chk_all && !main_wiki_key_is_pair_src)					// only do chk_all if main_wiki is not pair_src; note that chk_all will only be called in two ways (1) with main_wiki_key as null; (2) with main_key_as val
				||	(!chk_all && main_wiki_key_is_pair_src)) {				// pair.Src.Wiki == main.Wiki; note that there can be multiple pairs with same src; EX: have 2 pairs for commons: one for file and another for http					
				xfer_mgr.Atrs_by_itm(xfer_itm, pair_src, pair.Trg());
				boolean make = xfer_mgr.Make_file(wiki);
				if (make) {
					xfer_itm.Trg_repo_(pair.Trg());
					if (second_chance && xfer_itm.Meta_itm().Vrtl_repo() == 0)	// second_chance and item found; change vrtl_repo from commons back to same; EX: tarball and [[Image:Rembrandt De aartsengel verlaat Tobias en zijn gezin. 1637.jpg|120px]]
						xfer_itm.Meta_itm().Vrtl_repo_(Xof_meta_itm.Repo_same);
					return true;												// file was made; return; if not continue looking at other repos					
				}
			}
		}
		return false;
	}
	public Xofw_file_finder_rslt Page_finder_locate(byte[] ttl_bry) {page_finder.Locate(tmp_rslt, repos, ttl_bry); return tmp_rslt;}
	byte Xfer_by_meta__find_file(byte[] ttl_bry, Xof_meta_itm meta_itm, byte[] cur_wiki_key) {
		byte new_tid = Byte_.Max_value_127;
		boolean found = page_finder.Locate(tmp_rslt, repos, ttl_bry);
		if (found) {
			if (Bry_.Eq(cur_wiki_key, tmp_rslt.Repo_wiki_key())) {	// itm is in same repo as cur wiki
				new_tid = Xof_meta_itm.Tid_main;					
				byte[] redirect = tmp_rslt.Redirect();
				if (redirect != Xop_redirect_mgr.Redirect_null_bry) {	// redirect found
					meta_itm.Ptr_ttl_(redirect);
				}
				meta_itm.Vrtl_repo_(Xof_meta_itm.Repo_same);
			}
			else {														// itm is in diff repo
				new_tid = Xof_meta_itm.Tid_vrtl;
				meta_itm.Vrtl_repo_(tmp_rslt.Repo_idx());
				meta_itm.Ptr_ttl_(tmp_rslt.Redirect());
			}
		}
		else {															// itm not found; has to be vrtl, but mark repo as unknown
			meta_itm.Vrtl_repo_(Xof_meta_itm.Repo_unknown);
		}
		return new_tid;
	}	private Xofw_file_finder_rslt tmp_rslt = new Xofw_file_finder_rslt();
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_add))			return Add_repo(m.ReadBry("src"), m.ReadBry("trg"));
		else if	(ctx.Match(k, Invk_clear))			repos.Clear();
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_add = "add", Invk_clear = "clear";
	public Xof_repo_pair Add_repo(byte[] src_repo_key, byte[] trg_repo_key) {
		Xof_meta_mgr trg_meta = wiki.File_mgr().Meta_mgr();
		Xoa_repo_mgr repo_mgr = wiki.App().File_mgr().Repo_mgr();
		Xof_repo_itm src_repo = repo_mgr.Get_by(src_repo_key), trg_repo = repo_mgr.Get_by(trg_repo_key);
		byte[] src_wiki_key = src_repo.Wiki_key(), trg_wiki_key = trg_repo.Wiki_key();
		if (!Bry_.Eq(src_wiki_key, trg_wiki_key) && !Bry_.Eq(src_wiki_key, Xow_wiki_domain_.Key_home_bry)) throw Err_mgr._.fmt_(GRP_KEY, "add_repo", "wiki keys do not match: ~{0} ~{1}", String_.new_utf8_(src_wiki_key), String_.new_utf8_(trg_wiki_key));
		Xof_repo_pair pair = new Xof_repo_pair(repos.Count(), src_repo, trg_repo, trg_meta, src_wiki_key);
		repos.Add(pair);
		return pair;
	}
	public boolean Xfer_file(Xof_xfer_itm file) {
		int repo_idx = file.Trg_repo_idx();
		boolean wiki_is_unknown = repo_idx == Xof_meta_itm.Repo_unknown;
		boolean make_pass = false;
		int len = repos.Count();			
		if (!wiki_is_unknown) make_pass = Xfer_file_exec(file, this.Repos_get_at(repo_idx), repo_idx);
		if (make_pass) return true;

		for (int i = 0; i < len; i++) {
			Xof_repo_pair pair = (Xof_repo_pair)repos.FetchAt(i);
			if (i != repo_idx) {	// try other wikis
				file.Meta_itm().Orig_exists_(Xof_meta_itm.Exists_unknown);	// always reset orig exists; this may have been flagged to missing above and should be cleared
				make_pass = Xfer_file_exec(file, pair, i);
				if (make_pass) break;
			}
		}
		return make_pass;
	}
	private boolean Xfer_file_exec(Xof_xfer_itm file, Xof_repo_pair pair, int repo_idx) {
		xfer_mgr.Atrs_by_itm(file, pair.Src(), pair.Trg());
		Xof_meta_itm meta_itm = xfer_mgr.Meta_itm();
		boolean rv = xfer_mgr.Make_file(wiki);
		if (rv) {
			meta_itm.Vrtl_repo_(repo_idx);	// update repo_idx to whatever is found
		}
		return rv;
	}
	private static final String GRP_KEY = "Xow_repo_mgr";
}
/*
NOTE_1:reset_main_exists
this is primarily for Img_missing_wiki_1 and [[Image:Alcott-L.jpg|thumb|right|Louisa May Alcott]]
first some details:
. file pages exist in both en_wiki and in commons_wiki; w:File:Alcott-L.jpg; c:File:Alcott-L.jpg
. images are slightly different (w: is darker than c:)
. c was recently updated to redirect to File:Louisa May Alcott (1881 illustration).jpg

the actual effect:
. [[Image:Alcott-L.jpg|thumb|right|Louisa May Alcott]] in en.wikipedia should pull the w: one (not the c:) one
. this behavior seems contrary to all other wiki behavior where c: is given primacy over w:
.. EX: [[File:Tanks of WWI.ogg|thumb|thumbtime=12|alt=Primitive]] exists in both en_wiki and in commons, but the file only exists in commons
. moreover this page has a special note about the image deliberately remainining in en_wiki

the workaround/hack (described via intended sequence):
1: commons is defined as first repo_pair
2: image found in commons page (note that en_wiki is not checked for PERF reasons)
3: image is searched for in commons, but not found; note that both thumb and orig are missing
4: image will be searched for in en_wiki and found

note that (4) however reuses the same meta which is marked as "not found" from the commons attempt
. item will then have orig marked falsely as not-found

one approach would be to create a new meta and send that into the chk_all function. this turned out to be problematic

so, the workaround:
	if going into chk_all
	...and orig was previous unknown
	...but is now marked no
	then reset to unknown to give it a "clean" slate for the chk all

in theory, the above statement seems fine, but it does seem hackish...
*/