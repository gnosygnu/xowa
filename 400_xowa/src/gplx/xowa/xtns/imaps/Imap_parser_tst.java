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
package gplx.xowa.xtns.imaps; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*; import gplx.core.primitives.*; import gplx.xowa.parsers.*; import gplx.xowa.xtns.imaps.itms.*;
public class Imap_parser_tst {		
	@Before public void init() {fxt.Reset();} private Imap_parser_fxt fxt = new Imap_parser_fxt();
	@Test  public void Rect_pass()				{fxt.Test_shape("rect 1 2 3 4 [[A]]"								, fxt.itm_rect_("[[A]]", 1, 2, 3, 4));}
	@Test  public void Rect_pass_many()			{fxt.Test_shape("rect 1 2 3 4 5 6[[A]]"								, fxt.itm_rect_("[[A]]", 1, 2, 3, 4));} // PURPOSE: MW allows extra points to be passed; PAGE:en.w:Kilauea DATE:2014-07-28
	@Test  public void Rect_pass_invalid()		{fxt.Test_shape("rect 1 2 3 4 a b [[A]]"							, fxt.itm_rect_("[[A]]", 1, 2, 3, 4));} // PURPOSE: MW only scans first 4 tokens for numbers; PAGE:de.w:Wilhelm_Angele DATE:2014-10-30
	@Test  public void Circle_pass()			{fxt.Test_shape("circle 1 2 3 [[A]]"								, fxt.itm_circle_("[[A]]", 1, 2, 3));}
	@Test  public void Poly_pass()				{fxt.Test_shape("poly 1 2 3 4 5 6 [[A]]"							, fxt.itm_poly_("[[A]]", 1, 2, 3, 4, 5, 6));}
	@Test  public void Poly_pass_chars()		{fxt.Test_shape("poly a b [[A]]"									, fxt.itm_poly_("[[A]]", 0, 0));}		// PURPOSE: non-numeric should be converted to 0; PAGE:uk.w:Стратосфера; DATE:2014-07-26
	@Test  public void Poly_pass_chars_2()		{fxt.Test_shape("poly 1a 2a [[A]]"									, fxt.itm_poly_("[[A]]", 0, 0));}		// PURPOSE: non-numeric should be converted to 0; PAGE:ru.w:Системный_блок; DATE:2014-10-22
	@Test  public void Poly_pass_dots()			{fxt.Test_shape("poly 1.2 3.4 [[A]]"								, fxt.itm_poly_("[[A]]", 1.2d, 3.4d));}	// PURPOSE: make sure decimals are handled correctly
	@Test  public void Poly_pass_commas()		{fxt.Test_shape("poly 1, 2, 3, 4 [[A]]"								, fxt.itm_poly_("[[A]]", 1, 2, 3, 4));}	// PURPOSE: commas should be ignored; PAGE:de.w:Kaimnitz; DATE:2014-08-05
	@Test  public void Poly_pass_commas_2()		{fxt.Test_shape("poly 1,2 3,4 [[A]]"								, fxt.itm_poly_("[[A]]", 1, 3));}		// PURPOSE: commas should be ignored for purpose of parse; PAGE:fr.w:Gouesnou; DATE:2014-08-12
	@Test  public void Poly_pass_commas_3()		{fxt.Test_shape("poly ,1 2 [[A]]"									, fxt.itm_poly_("[[A]]", 1, 2));}		// PURPOSE: do not fail if comma is at start of number; PAGE:en.w:Area_codes_281,_346,_713,_and_832; DATE:2015-07-31
	@Test  public void Rect_fail()				{fxt.Test_shape_err("rect 1 2 3 [[A]]"								, "imagemap_missing_coord");}
	@Test  public void Circle_fail()			{fxt.Test_shape_err("circle 1 2 [[A]]"								, "imagemap_missing_coord");}
	@Test  public void Poly_fail_odd()			{fxt.Test_shape_err("poly 1 2 3 [[A]]"								, "imagemap_poly_odd");}
	@Test  public void Poly_fail_zero()			{fxt.Test_shape_err("poly [[A]]"									, "imagemap_missing_coord");}
	@Test  public void Circle_fail_invalid()	{fxt.Test_shape_err("rect 1 2..3 4 [[A]]"							, "imagemap_invalid_coord");}
}
class Imap_parser_fxt extends Imap_base_fxt {
	private Imap_parser parser;
	private Imap_map imap;
	@Override public void Reset() {
		super.Reset();
		byte[] ttl_bry = Bry_.new_a7("Test_1");
		Imap_xtn_mgr xtn_mgr = new Imap_xtn_mgr();
		Xoae_page page = Xoae_page.New(wiki, wiki.Ttl_parse(ttl_bry));
		parser = new Imap_parser(xtn_mgr);
		parser.Init(wiki, page, Gfo_usr_dlg_.Noop);
		parser.Clear();
		imap = new Imap_map(1);
	}
	public void Test_shape(String raw_str, Imap_part_shape expd) {
		raw_str = "File:A.png\n" + raw_str;
		byte[] raw = Bry_.new_u8(raw_str);			
		parser.Parse(imap, raw, 0, raw.length);
		Imap_part_shape[] actl_ary = imap.Shapes();
		Imap_part_shape actl = actl_ary == null | actl_ary.length != 1 ? null : (Imap_part_shape)actl_ary[0];
		if		(actl == null && expd == null) {}	// noop; test passed
		else if (actl == null && expd != null) {Tfds.Fail("actl should not be null", raw);}
		else if (actl != null && expd == null) {Tfds.Fail("actl should be null", raw);}
		else {
			Tfds.Eq(expd.Part_tid(), actl.Part_tid(), "tid");
			Tfds.Eq_ary(expd.Shape_pts(), actl.Shape_pts(), "pts");
			Tfds.Eq(String_.new_u8(expd.Link_href()), String_.new_u8(actl.Link_href()));
			Tfds.Eq(String_.new_u8(expd.Link_text()), String_.new_u8(actl.Link_text()));
		}
		Tfds.Eq(0, imap.Errs().length, "expd 0 errors");
	}
	public void Test_shape_err(String raw_str, String expd_err) {
		raw_str = "File:A.png\n" + raw_str;
		byte[] raw = Bry_.new_u8(raw_str);
		parser.Parse(imap, raw, 0, raw.length);
		Imap_err[] err_ary = imap.Errs();
		Tfds.Eq(1, err_ary.length, "expd 1 err");
		Tfds.Eq(expd_err, err_ary[0].Err_msg());
	}
}
