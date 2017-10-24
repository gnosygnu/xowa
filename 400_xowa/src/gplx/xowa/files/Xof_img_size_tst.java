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
package gplx.xowa.files; import gplx.*; import gplx.xowa.*;
import org.junit.*; import gplx.core.bits.*; import gplx.xowa.files.*; import gplx.xowa.parsers.lnkis.*;
public class Xof_img_size_tst {
	private final Xof_img_size_fxt fxt = new Xof_img_size_fxt();
	@Before public void init() {
		fxt.Reset();
		fxt.Orig_(400, 200);
	}
	@Test  	public void Lnki_lt_orig_null() 		{fxt.Lnki_type_(Xop_lnki_type.Id_null)		.Lnki_(200, 100).Test_html(200, 100, Bool_.N);}	// [[File:A.png|200px]]           -> 200,100; File_is_orig = n
	@Test  	public void Lnki_lt_orig_thumb() 		{fxt.Lnki_type_(Xop_lnki_type.Id_thumb)		.Lnki_(200, 100).Test_html(200, 100, Bool_.N);}	// [[File:A.png|thumb|200px]]     -> 200,100; File_is_orig = n
	@Test  	public void Lnki_lt_orig_frameless() 	{fxt.Lnki_type_(Xop_lnki_type.Id_frameless)	.Lnki_(200, 100).Test_html(200, 100, Bool_.N);}	// [[File:A.png|frameless|200px]] -> 200,100; File_is_orig = n
	@Test  	public void Lnki_lt_orig_frame() 		{fxt.Lnki_type_(Xop_lnki_type.Id_frame)		.Lnki_(200,  -1).Test_html(400, 200, Bool_.Y);}	// [[File:A.png|frame|200px]]     -> 400,200; File_is_orig = y; frame always ignores parameters and returns orig
	@Test  	public void Lnki_gt_orig_null() 		{fxt.Lnki_type_(Xop_lnki_type.Id_null)		.Lnki_(800, 400).Test_html(800, 400, Bool_.Y);}	// [[File:A.png|800px]]           -> 800,400; File_is_orig = n
	@Test  	public void Lnki_gt_orig_thumb() 		{fxt.Lnki_type_(Xop_lnki_type.Id_thumb)		.Lnki_(800, 400).Test_html(400, 200, Bool_.Y);}	// [[File:A.png|thumb|800px]]     -> 400,200; File_is_orig = n
	@Test  	public void Lnki_gt_orig_frameless() 	{fxt.Lnki_type_(Xop_lnki_type.Id_frameless)	.Lnki_(800, 400).Test_html(400, 200, Bool_.Y);}	// [[File:A.png|frameless|800px]] -> 400,200; File_is_orig = n
	@Test  	public void Lnki_gt_orig_frame() 		{fxt.Lnki_type_(Xop_lnki_type.Id_frame)		.Lnki_(800,  -1).Test_html(400, 200, Bool_.Y);}	// [[File:A.png|frame|800px]]     -> 400,200; File_is_orig = y; frame always ignores parameters and returns orig
	@Test  	public void Lnki_eq_orig_null() 		{fxt.Lnki_type_(Xop_lnki_type.Id_null)		.Lnki_(400, 200).Test_html(400, 200, Bool_.Y);}	// make sure File_is_orig = y
	@Test  	public void Lnki_gt_orig_null_svg() 	{fxt.Lnki_ext_(Xof_ext_.Id_svg).Lnki_type_(Xop_lnki_type.Id_null)		.Lnki_(800, 400).Test_html(800, 400, Bool_.N);}	// [[File:A.svg|800px]]           -> 800,400; File_is_orig = n
	@Test  	public void Lnki_gt_orig_thumb_svg() 	{fxt.Lnki_ext_(Xof_ext_.Id_svg).Lnki_type_(Xop_lnki_type.Id_thumb)		.Lnki_(800, 400).Test_html(800, 400, Bool_.N);}	// [[File:A.svg|thumb|800px]]     -> 800,400; File_is_orig = n
	@Test  	public void Lnki_gt_orig_frameless_svg(){fxt.Lnki_ext_(Xof_ext_.Id_svg).Lnki_type_(Xop_lnki_type.Id_frameless)	.Lnki_(800, 400).Test_html(800, 400, Bool_.N);}	// [[File:A.svg|frameless|800px]] -> 800,400; File_is_orig = n
	@Test  	public void Lnki_gt_orig_frame_svg() 	{fxt.Lnki_ext_(Xof_ext_.Id_svg).Lnki_type_(Xop_lnki_type.Id_frame)		.Lnki_(800,  -1).Test_html(400, 200, Bool_.N);}	// [[File:A.svg|frame|800px]]     -> 400,200; File_is_orig = n; frame always ignores parameters and returns orig
	@Test   public void Width_missing()				{fxt.Lnki_( -1, 100).Test_html(200, 100);}	// calc width based on height and orig
	@Test   public void Height_missing()			{fxt.Lnki_(200,  -1).Test_html(200, 100);}
	@Test  	public void Orig_missing() 				{fxt.Lnki_(400, 200).Orig_( -1,  -1).Test_html(400, 200);}	// no orig_size; use lnki_w and lnki_h
	@Test  	public void Lnki_missing() 				{fxt.Lnki_( -1,  -1).Test_html(220, 110);}					// w=thumbnail default; h=calc from orig
	@Test   public void Lnki_missing_frameless()    {fxt.Lnki_( -1,  -1).Lnki_type_(Xop_lnki_type.Id_frameless) .Test_html(220, 110, Bool_.N);}	// default to thumb width
	@Test   public void Lnki_missing_null()         {fxt.Lnki_( -1,  -1).Lnki_type_(Xop_lnki_type.Id_null)		.Test_html(400, 200, Bool_.Y);}	// default to orig width
	@Test  	public void Lnki_missing__orig_missing(){fxt.Lnki_( -1,  -1).Orig_( -1,  -1).Test_html(220,  -1);}	// no lnki or orig size; default to 220 with unknown height
	@Test   public void Prefer_height_over_width()	{fxt.Lnki_(200, 100).Test_html(200, 100);}					// prefer height; if width were preferred, size would be 200,134
	@Test  	public void Upright() 					{fxt.Lnki_upright_(1).Lnki_(-1, -1).Orig_(440, 400).Test_html(220, 200);}	
	@Test  	public void Upright_w_thumb() 			{fxt.Lnki_type_(Xop_lnki_type.Id_thumb).Lnki_upright_(2).Lnki_(-1, -1).Orig_(1500, 1125).Test_html(440, 330);}
	@Test  	public void Upright_ignored_by_w() 		{fxt.Lnki_type_(Xop_lnki_type.Id_thumb).Lnki_upright_(3.2).Lnki_(900, -1).Orig_(4653, 854).Test_html(900, 165);}// PAGE: fr.w:Bogota; DATE:2014-05-22
	@Test   public void Explicit_ratio_large()		{fxt.Lnki_(120,  40).Test_html( 80,  40);}	// see NOTE_2: lnki_ratio > orig_ratio
	@Test   public void Explicit_ratio_small()		{fxt.Lnki_(120,  80).Test_html(120,  60);}	// see NOTE_2: lnki_ratio > orig_ratio
	@Test   public void Lnki_gt_orig_null_svg_example() {	// EX:[[File:Crystal Clear app kedit.svg|50x40px]]
		fxt.Lnki_ext_(Xof_ext_.Id_svg).Lnki_type_(Xop_lnki_type.Id_null).Lnki_( 50,  40).Orig_( 40,  40).Test_html( 40,  40);
	}	
	@Test   public void Prefer_height_over_width_example() {// EX:[[File:Firma B.Ohiggins.svg|128x80px|alt=|Bernardo O'Higgins's signature]]
		fxt.Lnki_ext_(Xof_ext_.Id_svg).Lnki_type_(Xop_lnki_type.Id_null).Lnki_(128,  80).Orig_(720, 194).Test_html(128,  34);
	}	
	@Test   public void Lnki_gt_orig_thumb_example() {// EX:[[File:Adhanema Lasva.jpg|thumb|300px|The Firman given to the Bosnian Franciscans]]
		fxt.Lnki_type_(Xop_lnki_type.Id_thumb).Lnki_(300,  -1).Orig_(149, 408).Test_html(149, 408, Bool_.Y);
	}
	@Test  	public void Upright_and_null_width_fails() {// PURPOSE: if width = -1, but upright is specified, ignore upright (was calculating 0 for width); DATE:2013-11-23
		fxt.Lnki_type_(Xop_lnki_type.Id_null).Lnki_(-1, 110).Orig_(440, 220).Lnki_upright_(.50f).Test_html(220, 110, Bool_.N);
	}
	@Test  	public void Svg_null_width() {	// PURPOSE: if svg and only height is specified, default width to 2048 (and recalc); DATE: 2013-11-26
		fxt.Lnki_ext_(Xof_ext_.Id_svg).Lnki_(-1, 40).Orig_(1, 1).Test_html(40, 40, Bool_.N);	// NOTE: used to be 1,1
	}
	@Test  	public void Svg_max_width() {	// PURPOSE: large width causes int overflow; vi.w:Danh_sách_quốc_kỳ DATE:2014-04-26
		fxt.Lnki_ext_(Xof_ext_.Id_svg).Lnki_(Int_.Max_value, 90).Orig_(900, 600).Test_html(135, 90, Bool_.N);	// NOTE: used to be Int_.Max_value,90
	}
	@Test  	public void Pdf_none_defaults_to_thumb() {	// PURPOSE: if no width is specified, pdf uses thumb width default, not orig width); DATE: 2013-11-27
		fxt.Lnki_type_(Xop_lnki_type.Id_none).Lnki_ext_(Xof_ext_.Id_pdf).Lnki_(-1, -1).Orig_(440, 220).Test_html(220, 110, Bool_.N);	// NOTE: used to be 1,1
	}
	@Test  	public void Frame() {	// PURPOSE: frame incorrectly defaulted to file_is_orig; [[File:MESSENGER.jpg|200x200px|framed]]; DATE:2013-12-22
		fxt.Lnki_type_(Xop_lnki_type.Id_frame).Lnki_ext_(Xof_ext_.Id_png).Lnki_(200, 200).Orig_(2038, 1529).Test_html(200, 150, Bool_.N);
	}
	@Test  	public void Frame_and_thumb(){ // PURPOSE: frame and thumb should be treated as frame; Enm.Has(val, Id_frame) vs val == Id_frame; PAGE:en.w:History_of_Western_Civilization; DATE:2015-04-16
		fxt.Lnki_type_(Bitmask_.Add_byte(Xop_lnki_type.Id_frame, Xop_lnki_type.Id_thumb)).Lnki_(200,  -1).Test_html(400, 200, Bool_.Y);	// mut return same as Lnki_lt_orig_frame above
	}
	@Test  	public void Video__use_orig_w(){ // PURPOSE: video should use orig_w; DATE:2015-08-07
		fxt.Lnki_type_(Xop_lnki_type.Id_none).Lnki_ext_(Xof_ext_.Id_ogv).Lnki_(-1,  -1).Orig_(500, 250).Test_html(500, 250, Bool_.N);
	}
	@Test  	public void Video__use_thumb(){ // PURPOSE: video should use thumb_w, not orig_w; PAGE:en.w:Edward_Snowden DATE:2015-08-17
		fxt.Lnki_type_(Xop_lnki_type.Id_thumb).Lnki_ext_(Xof_ext_.Id_ogv).Lnki_(-1,  -1).Orig_(440, 220).Test_html(220, 110, Bool_.N);
	}
}
class Xof_img_size_fxt {
	private Xof_img_size img_size = new Xof_img_size();
	public Xof_img_size_fxt Reset() {
		lnki_type = Xop_lnki_type.Id_thumb;
		lnki_ext = Xof_ext_.Id_jpg;
		lnki_upright = Xof_img_size.Upright_null;
		orig_w = orig_h = lnki_w = lnki_h = Xof_img_size.Null;
		return this;
	}
	public Xof_img_size_fxt Lnki_type_(byte v) {lnki_type = v; return this;} private byte lnki_type;
	public Xof_img_size_fxt Lnki_ext_(int v) {lnki_ext = v; return this;} private int lnki_ext;
	public Xof_img_size_fxt Lnki_upright_(double v) {lnki_upright = v; return this;} private double lnki_upright;
	public Xof_img_size_fxt Orig_(int w, int h) {orig_w = w; orig_h = h; return this;} private int orig_w, orig_h;
	public Xof_img_size_fxt Lnki_(int w, int h) {lnki_w = w; lnki_h = h; return this;} private int lnki_w, lnki_h;
	public void Test_html(int expd_w, int expd_h) {Test_html(expd_w, expd_h, false);}
	public void Test_html(int expd_html_w, int expd_html_h, boolean expd_file_is_orig) {
		img_size.Html_size_calc(Xof_exec_tid.Tid_wiki_page, lnki_w, lnki_h, lnki_type, Xof_patch_upright_tid_.Tid_all, lnki_upright, lnki_ext, orig_w, orig_h, Xof_img_size.Thumb_width_img);
		Tfds.Eq(expd_html_w, img_size.Html_w(), "html_w");
		Tfds.Eq(expd_html_h, img_size.Html_h(), "html_h");
		Tfds.Eq(expd_file_is_orig, img_size.File_is_orig(), "file_is_orig");
	}
}
