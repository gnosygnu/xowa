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
package gplx.xowa.xtns.imaps; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*; import gplx.core.primitives.*;
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
	@Test  public void Rect_fail()				{fxt.Test_shape_err("rect 1 2 3 [[A]]"								, "imagemap_missing_coord");}
	@Test  public void Circle_fail()			{fxt.Test_shape_err("circle 1 2 [[A]]"								, "imagemap_missing_coord");}
	@Test  public void Poly_fail_odd()			{fxt.Test_shape_err("poly 1 2 3 [[A]]"								, "imagemap_poly_odd");}
	@Test  public void Poly_fail_zero()			{fxt.Test_shape_err("poly [[A]]"									, "imagemap_missing_coord");}
	@Test  public void Circle_fail_invalid()	{fxt.Test_shape_err("rect 1 2..3 4 [[A]]"							, "imagemap_invalid_coord");}
}
class Imap_fxt_base {
	protected Xoae_app app; protected Xowe_wiki wiki;
	@gplx.Virtual public void Reset() {
		app = Xoa_app_fxt.app_();
		wiki = Xoa_app_fxt.wiki_tst_(app);
		wiki.Ctx().Para().Enabled_n_();
	}
	public Imap_itm_shape itm_rect_(String link, double... pts_ary) {return itm_shape_(Imap_itm_.Tid_shape_rect, link, pts_ary);}
	public Imap_itm_shape itm_circle_(String link, double... pts_ary) {return itm_shape_(Imap_itm_.Tid_shape_circle, link, pts_ary);}
	public Imap_itm_shape itm_poly_(String link, double... pts_ary) {return itm_shape_(Imap_itm_.Tid_shape_poly, link, pts_ary);}
	private Imap_itm_shape itm_shape_(byte tid, String link, double... pts_ary) {
		int pts_len = pts_ary.length;
		Double_obj_val[] pts_doubles = new Double_obj_val[pts_len];
		for (int i = 0; i < pts_len; ++i)
			pts_doubles[i] = Double_obj_val.new_(pts_ary[i]);
		byte[] link_bry = Bry_.new_utf8_(link);
		Imap_itm_shape rv = new Imap_itm_shape(tid, pts_doubles);
		Imap_link_owner_.Init(rv, app, wiki, link_bry, Make_link_tkn(link_bry));
		return rv;
	}
	private Xop_tkn_itm Make_link_tkn(byte[] src) {
		Xop_root_tkn root_tkn = new Xop_root_tkn();			
		wiki.Parser().Parse_text_to_wdom(root_tkn, wiki.Ctx(), app.Tkn_mkr(), src, Xop_parser_.Doc_bgn_bos);
		return root_tkn.Subs_get(0);
	}
}
class Imap_parser_fxt extends Imap_fxt_base {
	private Imap_parser parser;
	private Imap_map imap;
	@Override public void Reset() {
		super.Reset();
		Xoa_url url = Xoa_url.new_(wiki.Domain_bry(), Bry_.new_ascii_("Test_1"));
		Imap_xtn_mgr xtn_mgr = new Imap_xtn_mgr();
		parser = new Imap_parser(xtn_mgr);
		parser.Init(wiki, url, Gfo_usr_dlg_.Noop);
		parser.Clear();
		imap = new Imap_map(1);
	}
	public void Test_shape(String raw_str, Imap_itm_shape expd) {
		raw_str = "File:A.png\n" + raw_str;
		byte[] raw = Bry_.new_utf8_(raw_str);			
		parser.Parse(imap, raw, 0, raw.length);
		Imap_itm_shape[] actl_ary = imap.Shapes();
		Imap_itm_shape actl = actl_ary == null | actl_ary.length != 1 ? null : (Imap_itm_shape)actl_ary[0];
		if		(actl == null && expd == null) {}	// noop; test passed
		else if (actl == null && expd != null) {Tfds.Fail("actl should not be null", raw);}
		else if (actl != null && expd == null) {Tfds.Fail("actl should be null", raw);}
		else {
			Tfds.Eq(expd.Itm_tid(), actl.Itm_tid(), "tid");
			Tfds.Eq_ary(expd.Shape_pts(), actl.Shape_pts(), "pts");
			Tfds.Eq(String_.new_utf8_(expd.Link_href()), String_.new_utf8_(actl.Link_href()));
			Tfds.Eq(String_.new_utf8_(expd.Link_text()), String_.new_utf8_(actl.Link_text()));
		}
		Tfds.Eq(0, imap.Errs().length, "expd 0 errors");
	}
	public void Test_shape_err(String raw_str, String expd_err) {
		raw_str = "File:A.png\n" + raw_str;
		byte[] raw = Bry_.new_utf8_(raw_str);
		parser.Parse(imap, raw, 0, raw.length);
		Imap_err[] err_ary = imap.Errs();
		Tfds.Eq(1, err_ary.length, "expd 1 err");
		Tfds.Eq(expd_err, err_ary[0].Err_key());
	}
}
