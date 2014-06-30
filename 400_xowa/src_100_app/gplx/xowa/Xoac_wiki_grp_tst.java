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
import org.junit.*;
import gplx.xowa.wikis.*;
public class Xoac_wiki_grp_tst {
	Xoac_wiki_grp_fxt fxt = new Xoac_wiki_grp_fxt();
	@Before public void init() {fxt.Clear();}
	@Test  public void Add_itm_new() {
		fxt.Define_bulk(String_.Concat_lines_nl
			(	"+|core|itm|commons|commons"
			,	"+|core|itm|meta|meta;m"
			,	"+|peer|itm|wiktionary|wikt;wiktionary"
			,	"+|peer|itm|wikisource|s"
			)
			,	fxt.grp_("core").Itms_
			(		fxt.itm_("commons").Aliases_("commons")
			,		fxt.itm_("meta").Aliases_("meta", "m")
			)
			,	fxt.grp_("peer").Itms_
			(		fxt.itm_("wiktionary").Aliases_("wikt", "wiktionary")
			,		fxt.itm_("wikisource").Aliases_("s")
			)				
			);
	}
}
class Xoac_wiki_grp_fxt {
	Xoa_wiki_mgr wiki_mgr; Tst_mgr tst_mgr = new Tst_mgr();
	public void Clear() {
		Xoa_app app = Xoa_app_fxt.app_();
		wiki_mgr = app.Wiki_mgr();			
	}
	public Xoac_wiki_itm_chkr itm_(String key) {return new Xoac_wiki_itm_chkr(key);}
	public Xoac_wiki_grp_chkr grp_(String key) {return new Xoac_wiki_grp_chkr(key);}
	public Xoac_wiki_grp_fxt Define_bulk(String raw, Xoac_wiki_grp_chkr... expd) {
		wiki_mgr.Groups().Set_bulk(Bry_.new_utf8_(raw));
		tst_mgr.Tst_ary("", expd, Xto_ary(wiki_mgr.Groups()));
		return this;
	}
	Xoac_wiki_grp[] Xto_ary(Cfg_nde_root root) {
		int len = root.Root_len();
		Xoac_wiki_grp[] rv = new Xoac_wiki_grp[len];
		for (int i = 0; i < len; i++) {
			rv[i] = (Xoac_wiki_grp)root.Root_get_at(i);	// ASSUME: root only has grps (no itms)
		}
		return rv;
	}
}
interface Xoac_wiki_chkr_obj extends Tst_chkr {}
class Xoac_wiki_itm_chkr implements Xoac_wiki_chkr_obj {
	public Xoac_wiki_itm_chkr(String key) {this.key = key;}
	public String Key() {return key;} private String key;
	public Xoac_wiki_itm_chkr Aliases_(String... v) {aliases = v; return this;} private String[] aliases;
	public Class<?> TypeOf() {return Xoac_wiki_itm.class;}
	public int Chk(Tst_mgr mgr, String path, Object actl_obj) {
		Xoac_wiki_itm actl = (Xoac_wiki_itm)actl_obj;
		int rv = 0;
		rv += mgr.Tst_val(key == null, path, "key", key, String_.new_utf8_(actl.Key_bry()));
		rv += mgr.Tst_val(aliases == null, path, "aliases", String_.AryXtoStr(aliases), String_.AryXtoStr(String_.Ary(actl.Aliases())));
		return rv;
	}
}
class Xoac_wiki_grp_chkr implements Xoac_wiki_chkr_obj {
	public Xoac_wiki_grp_chkr(String key) {this.key = key;}
	public String Key() {return key;} private String key;
	public Xoac_wiki_grp_chkr Name_(String v) {name = v; return this;} private String name;
	public Xoac_wiki_grp_chkr Itms_(Xoac_wiki_chkr_obj... v) {this.itms = v; return this;} private Xoac_wiki_chkr_obj[] itms;
	public Class<?> TypeOf() {return Xoac_wiki_grp.class;}
	public int Chk(Tst_mgr mgr, String path, Object actl_obj) {
		Xoac_wiki_grp actl = (Xoac_wiki_grp)actl_obj;
		int rv = 0;
		rv += mgr.Tst_val(key == null, path, "key", key, String_.new_utf8_(actl.Key_bry()));
		rv += mgr.Tst_val(name == null, path, "name", name, String_.new_utf8_(actl.Name_bry()));
		rv += mgr.Tst_sub_ary(itms, Xto_ary(actl), path, rv);
		return rv;
	}
	Xoac_wiki_obj[] Xto_ary(Xoac_wiki_grp grp) {
		int len = grp.Itms_len();
		Xoac_wiki_obj[] rv = new Xoac_wiki_obj[len];
		for (int i = 0; i < len; i++)
			rv[i] = (Xoac_wiki_obj)grp.Itms_get_at(i);	// ASSUME: grp only has itms (no grps)
		return rv;
	}
}
