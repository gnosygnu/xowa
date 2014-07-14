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
package gplx.xowa.xtns.imageMap; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
public class Imap_itm_parser_tst {		
	@Before public void init() {fxt.Reset();} private Imap_itm_parser_fxt fxt = new Imap_itm_parser_fxt();
	@Test  public void Dflt_pass()				{fxt.Test_shape("default [[A]]"										, fxt.itm_dflt_("[[A]]"));}
	@Test  public void Dflt_ignore_points()		{fxt.Test_shape("default 1 2 3 [[A]]"								, fxt.itm_dflt_("[[A]]"));}
	@Test  public void Rect_pass()				{fxt.Test_shape("rect 1 2 3 4 [[A]]"								, fxt.itm_rect_("[[A]]", 1, 2, 3, 4));}
	@Test  public void Circle_pass()			{fxt.Test_shape("circle 1 2 3 [[A]]"								, fxt.itm_circle_("[[A]]", 1, 2, 3));}
	@Test  public void Poly_pass()				{fxt.Test_shape("poly 1 2 3 4 5 6 [[A]]"							, fxt.itm_poly_("[[A]]", 1, 2, 3, 4, 5, 6));}
	@Test  public void Rect_fail()				{fxt.Test_shape_err("rect 1 2 3 [[A]]"								, "imagemap_missing_coord");}
	@Test  public void Circle_fail()			{fxt.Test_shape_err("circle 1 2 [[A]]"								, "imagemap_missing_coord");}
	@Test  public void Poly_fail_odd()			{fxt.Test_shape_err("poly 1 2 3 [[A]]"								, "imagemap_poly_odd");}
	@Test  public void Poly_fail_zero()			{fxt.Test_shape_err("poly [[A]]"									, "imagemap_missing_coord");}
	@Test  public void Poly_fail_invalid()		{fxt.Test_shape_err("poly 1 2..3 [[A]]"								, "imagemap_invalid_coord");}
}
class Imap_itm_parser_fxt {
	private Xoa_app app; private Xow_wiki wiki;
	private Imap_itm_parser parser = new Imap_itm_parser();
	public void Reset() {
		app = Xoa_app_fxt.app_();
		wiki = Xoa_app_fxt.wiki_tst_(app);
		Xoa_url url = Xoa_url.new_(wiki.Domain_bry(), Bry_.new_ascii_("Test_1"));
		parser.Init(wiki, url, Gfo_usr_dlg_.Null);
		parser.Clear();
	}
	public Imap_itm_shape itm_dflt_(String link) {return itm_shape_(Imap_itm_.Tid_shape_dflt, link);}
	public Imap_itm_shape itm_rect_(String link, int... pts_ary) {return itm_shape_(Imap_itm_.Tid_shape_rect, link, pts_ary);}
	public Imap_itm_shape itm_circle_(String link, int... pts_ary) {return itm_shape_(Imap_itm_.Tid_shape_circle, link, pts_ary);}
	public Imap_itm_shape itm_poly_(String link, int... pts_ary) {return itm_shape_(Imap_itm_.Tid_shape_poly, link, pts_ary);}
	private Imap_itm_shape itm_shape_(byte tid, String link, int... pts_ary) {
		int pts_len = pts_ary.length;
		Double_obj_val[] pts_doubles = new Double_obj_val[pts_len];
		for (int i = 0; i < pts_len; ++i)
			pts_doubles[i] = Double_obj_val.new_(pts_ary[i]);
		byte[] link_bry = Bry_.new_utf8_(link);
		Xop_tkn_itm link_tkn = app.Tkn_mkr().Bry(-1, -1, link_bry);
		Imap_itm_shape rv = new Imap_itm_shape(0, 0, link_bry.length, tid, pts_doubles);
		rv.Shape_link_(app, wiki, link_bry, link_tkn);
		return rv;
	}
	public void Test_shape(String raw_str, Imap_itm_shape expd) {
		raw_str = "File:A.png\n" + raw_str;
		byte[] raw = Bry_.new_utf8_(raw_str);
		Imap_itm[] actl_ary = parser.Parse(raw, 0, raw.length);
		Imap_itm_shape actl = actl_ary == null | actl_ary.length != 2 ? null : (Imap_itm_shape)actl_ary[1];
		if		(actl == null && expd == null) {}	// noop; test passed
		else if (actl == null && expd != null) {Tfds.Fail("actl should not be null", raw);}
		else if (actl != null && expd == null) {Tfds.Fail("actl should be null", raw);}
		else {
			Tfds.Eq(expd.Itm_tid(), actl.Itm_tid(), "tid");
			Tfds.Eq_ary(expd.Shape_pts(), actl.Shape_pts(), "pts");
			Xop_bry_tkn expd_link_tkn = ((Xop_bry_tkn)expd.Shape_link());				
			String expd_link = String_.new_utf8_(expd_link_tkn.Val());
			String actl_link = String_.new_utf8_(raw, actl.Shape_link().Src_bgn(), actl.Shape_link().Src_end());
			Tfds.Eq(expd_link, actl_link);
		}
		Tfds.Eq(0, parser.Errs().Count(), "expd 0 errors");
	}
	public void Test_shape_err(String raw_str, String expd_err) {
		raw_str = "File:A.png\n" + raw_str;
		byte[] raw = Bry_.new_utf8_(raw_str);
		parser.Parse(raw, 0, raw.length);
		Imap_itm_err[] err_ary = (Imap_itm_err[])parser.Errs().XtoAryAndClear(Imap_itm_err.class);
		Tfds.Eq(1, err_ary.length, "expd 1 err");
		Tfds.Eq(expd_err, err_ary[0].Err_key());
	}
}
