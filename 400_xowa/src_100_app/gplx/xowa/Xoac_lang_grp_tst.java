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
import org.junit.*; import gplx.xowa.langs.*;
public class Xoac_lang_grp_tst {
	Xoac_lang_grp_fxt fxt = new Xoac_lang_grp_fxt();
	@Before public void init() {fxt.Clear();}
	@Test  public void Add_itm_new() {
		fxt.Define_bulk(String_.Concat_lines_nl
			(	"+|euro|itm|fr|French"
			,	"+|euro|itm|de|German"
			)
			,	fxt.grp_("euro").Itms_
			(		fxt.itm_("fr").Local_name_("French")
			,		fxt.itm_("de").Local_name_("German")
			));
	}
	@Test  public void Add_itm_nl() {
		fxt.Define_bulk(String_.Concat_lines_nl
			(	""
			,	"+|euro|itm|fr|French"
			,	""
			)
			,	fxt.grp_("euro").Itms_
			(		fxt.itm_("fr").Local_name_("French")
			));
	}
	@Test  public void Add_grp() {
		fxt.Define_bulk(String_.Concat_lines_nl
			(	"+||grp|euro|European~1"
			,	"+|euro|itm|fr|French"
			)
			,	fxt.grp_("euro").Name_("European").Sort_idx_(1).Itms_
			(		fxt.itm_("fr").Local_name_("French")
			));
	}
	@Test  public void Set_grp() {
		fxt.Define_bulk(String_.Concat_lines_nl
			(	"+|euro|itm|fr|French"
			,	"+||grp|euro|European"
			)
			,	fxt.grp_("euro").Name_("European").Itms_
			(		fxt.itm_("fr").Local_name_("French")
			));
	}
	@Test  public void Del() {
		fxt.Define_bulk(String_.Concat_lines_nl
			(	"+|euro|itm|fr|French"
			,	"+|euro|itm|de|German"
			,	"-|euro|fr"
			)
			,	fxt.grp_("euro").Itms_
			(		fxt.itm_("de").Local_name_("German")
			));
	}
	@Test  public void Add_grp_nest() {
		fxt.Define_bulk(String_.Concat_lines_nl
			(	"+||grp|wiki"
			,	"+|wiki|grp|euro|European"
			,	"+|euro|itm|de|German"
			)
			,	fxt.grp_("wiki").Itms_
			(		fxt.grp_("euro").Itms_
			(			fxt.itm_("de").Local_name_("German")
			)));
	}
}
class Xoac_lang_grp_fxt {
	Xoa_lang_mgr lang_mgr; Tst_mgr tst_mgr = new Tst_mgr();
	public void Clear() {
		Xoae_app app = Xoa_app_fxt.app_();
		lang_mgr = app.Lang_mgr();
	}
	public Xoac_lang_itm_chkr itm_(String key) {return new Xoac_lang_itm_chkr(key);}
	public Xoac_lang_grp_chkr grp_(String key) {return new Xoac_lang_grp_chkr(key);}
	public Xoac_lang_grp_fxt Define_bulk(String raw, Xoac_lang_grp_chkr... expd) {
		lang_mgr.Groups().Set_bulk(Bry_.new_utf8_(raw));
		tst_mgr.Tst_ary("", expd, Xto_ary(lang_mgr.Groups()));
		return this;
	}
	Xoac_lang_grp[] Xto_ary(Cfg_nde_root root) {
		int len = root.Root_len();
		Xoac_lang_grp[] rv = new Xoac_lang_grp[len];
		for (int i = 0; i < len; i++) {
			rv[i] = (Xoac_lang_grp)root.Root_get_at(i);	// ASSUME: root only has grps (no itms)
		}
		return rv;
	}
}
abstract class Xoac_lang_chkr_base implements Tst_chkr {
	public abstract Class<?> TypeOf();
	public abstract int Chk(Tst_mgr mgr, String path, Object actl);
}
class Xoac_lang_itm_chkr extends Xoac_lang_chkr_base {
	public Xoac_lang_itm_chkr(String key) {this.key = key;}
	public String Key() {return key;} private String key;
	public Xoac_lang_itm_chkr Local_name_(String v) {local_name = v; return this;} private String local_name;
	@Override public Class<?> TypeOf() {return Xoac_lang_itm.class;}
	@Override public int Chk(Tst_mgr mgr, String path, Object actl_obj) {
		Xoac_lang_itm actl = (Xoac_lang_itm)actl_obj;
		int rv = 0;
		rv += mgr.Tst_val(key == null, path, "key", key, String_.new_utf8_(actl.Key_bry()));
		rv += mgr.Tst_val(local_name == null, path, "local_name", local_name, String_.new_utf8_(actl.Local_name_bry()));
		return rv;
	}
}
class Xoac_lang_grp_chkr extends Xoac_lang_chkr_base {
	public Xoac_lang_grp_chkr(String key) {this.key = key;}
	public String Key() {return key;} private String key;
	public Xoac_lang_grp_chkr Name_(String v) {name = v; return this;} private String name;
	public Xoac_lang_grp_chkr Sort_idx_(int v) {sort_idx = v; return this;} private int sort_idx = -1;
	public Xoac_lang_grp_chkr Itms_(Xoac_lang_chkr_base... v) {this.itms = v; return this;} private Xoac_lang_chkr_base[] itms;
	@Override public Class<?> TypeOf() {return Xoac_lang_grp.class;}
	@Override public int Chk(Tst_mgr mgr, String path, Object actl_obj) {
		Xoac_lang_grp actl = (Xoac_lang_grp)actl_obj;
		int rv = 0;
		rv += mgr.Tst_val(key == null, path, "key", key, String_.new_utf8_(actl.Key_bry()));
		rv += mgr.Tst_val(name == null, path, "name", name, String_.new_utf8_(actl.Name_bry()));
		rv += mgr.Tst_val(sort_idx == -1, path, "sort_idx", sort_idx, actl.Sort_idx());
		rv += mgr.Tst_sub_ary(itms, Xto_ary(actl), path, rv);
		return rv;
	}
	Xoac_lang_obj[] Xto_ary(Xoac_lang_grp grp) {
		int len = grp.Itms_len();
		Xoac_lang_obj[] rv = new Xoac_lang_obj[len];
		for (int i = 0; i < len; i++)
			rv[i] = (Xoac_lang_obj)grp.Itms_get_at(i);	// ASSUME: grp only has itms (no grps)
		return rv;
	}
}
