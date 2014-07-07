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
package gplx.xowa.cfgs.gui; import gplx.*; import gplx.xowa.*; import gplx.xowa.cfgs.*;
class Test_api implements GfoInvkAble {
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_meta))	return meta_mgr.Get_or_null(m.ReadStr("v"));
		else	return GfoInvkAble_.Rv_unhandled;
//			return this;
	}	private static final String Invk_meta = "meta";
	private static final String Invk_prop_1 = "prop_1", Invk_prop_2 = "prop_2", Invk_prop_3 = "prop_3", Invk_prop_bry = "bry", Invk_prop_enm = "enm";
	private static final boolean Dflt_prop_1 = false;
	private static final int Dflt_prop_2 = 2;
	private static final String Dflt_prop_3 = "3"; 
	private static final byte[] Dflt_prop_bry = Bry_.new_ascii_("bry"); 
	private static final int Dflt_prop_enm = 5;
	private static final Xocfg_meta_mgr meta_mgr = new Xocfg_meta_mgr().Add
	( Xocfg_meta_itm_.bool_	(Invk_prop_1, Dflt_prop_1)
	, Xocfg_meta_itm_.int_	(Invk_prop_2, Dflt_prop_2).Rng_bgn_(-1)
	, Xocfg_meta_itm_.str_	(Invk_prop_3, Dflt_prop_3)
	, Xocfg_meta_itm_.bry_	(Invk_prop_bry, Dflt_prop_bry)
	, Xocfg_meta_itm_.enm_	(Invk_prop_enm, Dflt_prop_enm).Itms_list_("a", "b", "c")
	, Xocfg_meta_itm_.str_	(Invk_prop_3, Dflt_prop_3).Gui_itm_(Xocfg_gui_itm_.memo_(40, 60))
//		, Xocfg_meta_itm_.enum_	(Invk_prop_3, Dflt_prop_3).Add("a", "b").Add("b", "c").Data(Xocfg_gui.list("a"))
	)
	;
}
interface Xocfg_gui_itm {
}
class Xocfg_gui_itm_ {
	public static Xocfg_gui_itm_memo memo_(int box_w, int box_h) {return new Xocfg_gui_itm_memo(box_w, box_h);}
}
class Xocfg_gui_itm_fld {
}
class Xocfg_gui_itm_memo implements Xocfg_gui_itm {
	public Xocfg_gui_itm_memo(int box_w, int box_h) {this.box_w = box_w; this.box_h = box_h;}
	public int Box_w() {return box_w;} private int box_w;
	public int Box_h() {return box_h;} private int box_h;
}
class Xocfg_meta_mgr {
	private HashAdp hash = HashAdp_.new_();
	public Xocfg_meta_mgr Add(Xocfg_meta_itm_base... ary) {
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			Xocfg_meta_itm_base itm = (Xocfg_meta_itm_base)ary[i];
			hash.Add(itm.Prop_key(), itm);
		}
		return this;
	}
	public Xocfg_meta_itm_base Get_or_null(String key) {return (Xocfg_meta_itm_base)hash.Fetch(key);}
}
abstract class Xocfg_meta_itm_base {
	public void Set(int prop_type, String prop_key, Object prop_dflt) {
		this.prop_key = prop_key; this.prop_type = prop_type; this.prop_dflt = prop_dflt;
	}
	public String Prop_key() {return prop_key;} private String prop_key;
	public Object Prop_dflt() {return prop_dflt;} private Object prop_dflt;
	public int Prop_type() {return prop_type;} private int prop_type;
}
class Xocfg_meta_itm_ {
	public static Xocfg_meta_itm_bool	bool_(String key, boolean dflt) {return new Xocfg_meta_itm_bool(key, dflt);}
	public static Xocfg_meta_itm_int	int_(String key, int dflt) {return new Xocfg_meta_itm_int(key, dflt);}
	public static Xocfg_meta_itm_str	str_(String key, String dflt) {return new Xocfg_meta_itm_str(key, dflt);}
	public static Xocfg_meta_itm_bry	bry_(String key, byte[] dflt) {return new Xocfg_meta_itm_bry(key, dflt);}
	public static Xocfg_meta_itm_enm	enm_(String key, int dflt) {return new Xocfg_meta_itm_enm(key, dflt);}
}
class Xodfg_pref_itm_type_ {
	public static final int Tid_bool = 1, Tid_int = 2, Tid_str = 3, Tid_bry = 4, Tid_enm = 5;
}
class Xocfg_meta_itm_bool extends Xocfg_meta_itm_base {
	public Xocfg_meta_itm_bool(String prop_key, boolean prop_dflt) {this.Set(Xodfg_pref_itm_type_.Tid_bool, prop_key, prop_dflt);}	
}
class Xocfg_meta_itm_int extends Xocfg_meta_itm_base {
	public Xocfg_meta_itm_int(String prop_key, int prop_dflt) {this.Set(Xodfg_pref_itm_type_.Tid_int, prop_key, prop_dflt);}
	public Xocfg_meta_itm_int Rng_bgn_(int bgn) {return Rng_(bgn, Int_.MaxValue);}
	public Xocfg_meta_itm_int Rng_(int bgn, int end) {
		return this;
	}
}
class Xocfg_meta_itm_str extends Xocfg_meta_itm_base {
	public Xocfg_meta_itm_str(String prop_key, String prop_dflt) {this.Set(Xodfg_pref_itm_type_.Tid_str, prop_key, prop_dflt);}
	public Xocfg_gui_itm Gui_itm() {return gui_itm;} public Xocfg_meta_itm_base Gui_itm_(Xocfg_gui_itm v) {gui_itm = v; return this;} private Xocfg_gui_itm gui_itm;
}
class Xocfg_meta_itm_bry extends Xocfg_meta_itm_base {
	public Xocfg_meta_itm_bry(String prop_key, byte[] prop_dflt) {this.Set(Xodfg_pref_itm_type_.Tid_bry, prop_key, prop_dflt);}
}
class Xocfg_meta_itm_enm extends Xocfg_meta_itm_base {
	public Xocfg_meta_itm_enm(String prop_key, int prop_dflt) {this.Set(Xodfg_pref_itm_type_.Tid_enm, prop_key, prop_dflt);}
	public Xocfg_meta_itm_enm Itms_list_(String... ary) {
		return this;
	}
}
