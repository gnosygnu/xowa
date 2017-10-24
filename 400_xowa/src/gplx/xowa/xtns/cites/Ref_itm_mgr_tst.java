/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.xtns.cites; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*; import gplx.core.tests.*;
public class Ref_itm_mgr_tst {
	Ref_itm_mgr_fxt fxt = new Ref_itm_mgr_fxt();
	@Before public void init() {fxt.Clear();}
	@Test  public void Basic()	{fxt.run_Add("key_1", "key_2").tst(fxt.itm_("key_1", 0, 0), fxt.itm_("key_2", 1, 1));}
	@Test  public void Repeat() {fxt.run_Add("key_1", "key_2", "key_1").tst(fxt.itm_("key_1", 0).Related_(fxt.itm_uid_(2)), fxt.itm_("key_2", 1, 1));}
	@Test  public void Group()	{fxt.run_Add_grp("grp_1", "key_1").run_Add("key_1").tst_grp("grp_1", fxt.itm_("key_1", 0, 0)).tst(fxt.itm_("key_1", 0, 1));}
	@Test  public void Follow()	{fxt.run_Add("key_1").run_Add_follow("key_1").tst(fxt.itm_("key_1", 0, 0).Related_(fxt.itm_uid_(1).Idx_minor_follow_()));}
}
class Ref_itm_mgr_fxt {
	private Ref_itm_mgr ref_mgr = new Ref_itm_mgr();
	private Tst_mgr tst_mgr = new Tst_mgr();
	public Xtn_ref_nde_chkr itm_uid_(int uid)					{return itm_(null, -1, uid);}
	public Xtn_ref_nde_chkr itm_(String key, int idx)			{return itm_(key, idx, -1);}
	public Xtn_ref_nde_chkr itm_(String key, int idx, int uid)	{return new Xtn_ref_nde_chkr().Key_(key).Idx_major_(idx).Uid_(uid);}
	public Ref_itm_mgr_fxt run_Add_follow(String follow)		{return run_Add_grp(Bry_.Empty, Bry_.new_u8(follow), Bry_.Ary(Bry_.Empty));}
	public Ref_itm_mgr_fxt run_Add(String... name_ary)	{return run_Add_grp(Bry_.Empty, Bry_.Empty, Bry_.Ary(name_ary));}
	public Ref_itm_mgr_fxt run_Add_grp(String grp, String... name_ary) {return run_Add_grp(Bry_.new_u8(grp), Bry_.Empty, Bry_.Ary(name_ary));}
	public void Clear() {ref_mgr.Grps_clear();}
	Ref_itm_mgr_fxt run_Add_grp(byte[] grp_name, byte[] follow, byte[]... name_ary) {
		for (int i = 0; i < name_ary.length; i++) {
			byte[] name = name_ary[i];
			Ref_nde itm = new Ref_nde().Name_(name);
			ref_mgr.Grps_add(grp_name, name, follow, itm);
		}
		return this;
	}
	public Ref_itm_mgr_fxt tst(Xtn_ref_nde_chkr... chkr_ary) {return tst_grp(ref_mgr.Lst_get(Bry_.Empty, 0), chkr_ary);}
	public Ref_itm_mgr_fxt tst_grp(String grp_name, Xtn_ref_nde_chkr... chkr_ary) {return tst_grp(ref_mgr.Lst_get(Bry_.new_a7(grp_name), 0), chkr_ary);}
	public Ref_itm_mgr_fxt tst_grp(Ref_itm_lst lst, Xtn_ref_nde_chkr... chkr_ary) {
		int itms_len = lst.Itms_len();
		Ref_nde[] actl = new Ref_nde[itms_len];
		for (int i = 0; i < itms_len; i++)
			actl[i] = lst.Itms_get_at(i);
		tst_mgr.Tst_ary("", chkr_ary, actl);
		return this;
	}	List_adp actl_list = List_adp_.New();
}
class Xtn_ref_nde_chkr implements Tst_chkr {
	public Class<?> TypeOf() {return Ref_nde.class;}
	public String Key() {return key;} public Xtn_ref_nde_chkr Key_(String v) {key = v; return this;} private String key;
	public int Idx_major() {return idx_major;} public Xtn_ref_nde_chkr Idx_major_(int v) {idx_major = v; return this;} private int idx_major = -1;
	public int Idx_minor() {return idx_minor;} public Xtn_ref_nde_chkr Idx_minor_(int v) {idx_minor = v; return this;} private int idx_minor = -1;
	public Xtn_ref_nde_chkr Idx_minor_follow_() {idx_minor = Ref_nde.Idx_minor_follow; return this;}
	public int Uid() {return uid;} public Xtn_ref_nde_chkr Uid_(int v) {uid = v; return this;} private int uid = -1;
	public Xtn_ref_nde_chkr[] Related() {return related;} public Xtn_ref_nde_chkr Related_(Xtn_ref_nde_chkr... v) {related = v; return this;} Xtn_ref_nde_chkr[] related;
	public int Chk(Tst_mgr mgr, String path, Object actl_obj) {
		Ref_nde actl = (Ref_nde)actl_obj;
		int rv = 0;
		rv += mgr.Tst_val(key == null, path, "key", key, String_.new_u8(actl.Name()));
		rv += mgr.Tst_val(idx_major == -1, path, "idx_major", idx_major, actl.Idx_major());
		rv += mgr.Tst_val(idx_minor == -1, path, "idx_minor", idx_minor, actl.Idx_minor());
		rv += mgr.Tst_val(uid == -1, path, "uid", uid, actl.Uid());
		if (related != null)
			rv += mgr.Tst_sub_ary(related, Xto_bry(actl), "related", rv);
		return rv;
	}
	Ref_nde[] Xto_bry(Ref_nde itm) {
		int len = itm.Related_len();
		Ref_nde[] rv = new Ref_nde[len];
		for (int i = 0; i < len; i++)
			rv[i] = itm.Related_get(i);
		return rv;
	}
}
