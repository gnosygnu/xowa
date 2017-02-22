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
package gplx.xowa.wikis.nss; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import org.junit.*;
import gplx.xowa.langs.cases.*;
public class Xow_ns_mgr_tst {		
	@Before public void init() {fxt.Clear();} private Xow_ns_mgr_fxt fxt = new Xow_ns_mgr_fxt();
	@Test  public void Basic() 				{fxt.ini_ns_(-2, 0, 1).run_Ords_sort().tst_Ords(-2, -100, 0, 1);}
	@Test  public void Talk_skip() 			{fxt.ini_ns_(-2, 0, 2, 3).run_Ords_sort().tst_Ords(-2, -100, 0, 1, 2, 3);}
	@Test  public void Subj_skip() 			{fxt.ini_ns_(-2, 1, 2, 3).run_Ords_sort().tst_Ords(-2, -100, 0, 1, 2, 3);}
	@Test  public void Out_of_order() 		{fxt.ini_ns_(3, 1, 2, -2).run_Ords_sort().tst_Ords(-2, -100, 0, 1, 2, 3);}
	@Test  public void Skip_odd() 			{fxt.ini_ns_(-2, 1, 3).run_Ords_sort().tst_Ords(-2, -100, 0, 1, 2, 3);}
	@Test  public void Skip_even() 			{fxt.ini_ns_(-2, 2, 4).run_Ords_sort().tst_Ords(-2, -100, 2, 3, 4, 5);}
	@Test  public void Ns_alias() {
		fxt.Ns_mgr().Aliases_clear();
		fxt.Ns_mgr().Add_new(Xow_ns_.Tid__template, "Template");
		fxt.Ns_mgr().Aliases_add(Xow_ns_.Tid__template, "Templatex");
		fxt.Ns_mgr().Init();
		byte[] name = Bry_.new_a7("Templatex:Abc");
		Tfds.Eq(10, fxt.Ns_mgr().Tmpls_get_w_colon(name, 0, name.length));
	}
	@Test  public void Utf8() {// PURPOSE: handle different casings for ns_names; PAGE:ru.w:Портрет_итальянского_Ренессанса DATE:2014-07-04
		Xow_ns_mgr ns_mgr = new Xow_ns_mgr(Xol_case_mgr_.U8());
		ns_mgr.Add_new(1234, "Test");
		ns_mgr.Add_new(1235, "файл");
		fxt.Ns_mgr_(ns_mgr);
		fxt.Test_ns_name(1234, "Test", "test", "TEST", "tesT");
		fxt.Test_ns_name(1235, "файл", "Файл");
	}
}
class Xow_ns_mgr_fxt {
	private Xow_ns_mgr ns_mgr = new Xow_ns_mgr(Xol_case_mgr_.A7());
	public Xow_ns_mgr Ns_mgr() {return ns_mgr;}
	public void Ns_mgr_(Xow_ns_mgr v) {this.ns_mgr = v;}
	public void Clear() {ns_mgr.Clear();}
	public Xow_ns_mgr_fxt ini_ns_(int... ids) {
		int ids_len = ids.length;
		for (int i = 0; i < ids_len; i++) {
			int id = ids[i];
			ns_mgr.Add_new(id, Int_.To_str(id));
		}
		return this;
	}
	public Xow_ns_mgr_fxt run_Ords_sort() {ns_mgr.Init(); return this;}
	public Xow_ns_mgr_fxt tst_Ords(int... expd) {
		int actl_len = ns_mgr.Ords_len();
		int[] actl = new int[actl_len];
		for (int i = 0; i < actl_len; i++) {
			Xow_ns ns_itm = ns_mgr.Ords_ary()[i]; 
			actl[i] = ns_itm == null ? -100 : ns_itm.Id();
		}
		Tfds.Eq_ary(expd, actl);
		return this;
	}
	public void Test_ns_name(int expd_id, String... ns_names) {
		int ns_names_len = ns_names.length;
		for (int i = 0; i < ns_names_len; ++i) {
			String ns_name = ns_names[i];
			Xow_ns actl_ns = ns_mgr.Names_get_or_null(Bry_.new_u8(ns_name));
			int actl_id = actl_ns == null ? Int_.Min_value : actl_ns.Id();
			Tfds.Eq(expd_id, actl_id, ns_name);
		}
	}
}
