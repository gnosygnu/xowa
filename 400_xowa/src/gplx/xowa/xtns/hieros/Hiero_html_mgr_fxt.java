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
package gplx.xowa.xtns.hieros; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
public class Hiero_html_mgr_fxt {
	private Hiero_xtn_mgr xtn_mgr;
	public Hiero_html_mgr_fxt(Xop_fxt fxt) {this.fxt = fxt;}
	public Xop_fxt Fxt() {return fxt;} private Xop_fxt fxt;
	public void Reset() {
		fxt.Reset();
		xtn_mgr = new Hiero_xtn_mgr();
		xtn_mgr.Xtn_init_by_app(fxt.App());
		xtn_mgr.Clear();
	}
	public Hiero_html_mgr_fxt Init_hiero_A1_B1() {
		this.Init_file("A1", 29, 38);
		this.Init_file("B1", 23, 38);
		return this;
	}
	public Hiero_html_mgr_fxt Init_hiero_cartouche() {
		this.Init_phoneme("<", "Ca1");
		this.Init_phoneme(">", "Ca2");
		return this;
	}
	public Hiero_html_mgr_fxt Init_hiero_p_t() {
		this.Init_phoneme("p", "Q3");
		this.Init_phoneme("t", "X1");
		this.Init_file("Q3", 12, 15);
		this.Init_file("X1", 20, 11);
		return this;
	}
	public Hiero_html_mgr_fxt Init_hiero_a_A1() {
		this.Init_prefab("a&A1");
		this.Init_file("a&A1", 37, 38);
		return this;
	}
	public Hiero_html_mgr_fxt Init_prefab(String prefab)					{xtn_mgr.Prefab_mgr().Add(Bry_.new_u8(prefab)); return this;}
	public Hiero_html_mgr_fxt Init_file(String s, int w, int h)				{xtn_mgr.File_mgr().Add(Bry_.new_u8(s), w, h); return this;}
	public Hiero_html_mgr_fxt Init_phoneme(String phoneme, String code)		{xtn_mgr.Phoneme_mgr().Add(Bry_.new_u8(phoneme), Bry_.new_u8(code)); return this;}
	public void Test_html_full_str(String raw, String expd)					{fxt.Test_html_full_str(raw, expd);}
	public void Test_html_full_frag(String raw, String expd)				{fxt.Test_html_full_frag(raw, expd);}
}	
