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
import org.junit.*; import gplx.core.primitives.*; import gplx.gfui.*; import gplx.xowa.files.*; import gplx.xowa.parsers.lnkis.*;
public class Xof_xfer_itm_tst {		
	@Before public void init() {fxt.ini();} Xof_xfer_itm_fxt fxt = new Xof_xfer_itm_fxt();
	@Test  public void Box()						{tst_Calc_view("40,50"	, "40,40"	, "40,40");}	// EX:[[File:Crystal Clear app kedit.svg|50x40px]]
	@Test  public void Long_w()						{tst_Calc_view("128,80"	, "720,194"	, "128,34");}	// EX:[[File:Firma B.Ohiggins.svg|128x80px|alt=|Bernardo O'Higgins's signature]]
	@Test  public void Long_h()						{tst_Calc_view("300,-1"	, "149,408"	, "149,408");}	// EX:[[File:Adhanema Lasva.jpg|thumb|300px|The Firman given to the Bosnian Franciscans]]
	@Test  public void Width_too_long()				{tst_Calc_view("100,-1"	, "40,40"	, "40,40");}	// limit to height;
	@Test  public void Width_missing()				{tst_Calc_view("-1,20"	, "80,40"	, "40,20");}	// calc width based on height and file_size
	@Test  public void Prefer_height_over_width()	{tst_Calc_view("60,20"	, "120,60"	, "40,20");}	// prefer height; if width was preferred, size would be 60,30
	@Test  public void Height_missing()				{tst_Calc_view("50,-1"	, "100,200"	, "50,100");}
	@Test  public void Explicit_ratio_large()		{tst_Calc_view("120,40"	, "200,100"	, "80,40");}		// see NOTE_1:view ratio > file ratio
	@Test  public void Explicit_ratio_small()		{tst_Calc_view("120,80"	, "200,100"	, "120,60");}		// see NOTE_1:view ratio > file ratio
	private void tst_Calc_view(String lnki_str, String file_str, String expd_str) {
		Int_2_ref rv = new Int_2_ref();
		Int_2_val lnki = Int_2_val.parse(lnki_str);
		Int_2_val file = Int_2_val.parse(file_str);
		Int_2_val expd = Int_2_val.parse(expd_str);
		Xof_xfer_itm_.Calc_view(rv, Xop_lnki_type.Id_thumb, lnki.Val_0(), lnki.Val_1(), file.Val_0(), file.Val_1(), true);
		Tfds.Eq(expd.Val_0(), rv.Val_0());
		Tfds.Eq(expd.Val_1(), rv.Val_1());
	}
	@Test 	public void Thumb_lnkY() 						{fxt.Lnki_(300, 200).tst(300, 200);}							// size provided; use
	@Test 	public void Thumb_lnkN() 						{fxt.Lnki_( -1,  -1).tst(220,  -1);}							// w=thumbnail default
	@Test 	public void Thumb_lnkN_sqlY() 					{fxt.Lnki_( -1,  -1).File_(220, 200).tst(220, 200);}			// w=thumbnail default; h=calc from sql
	@Test 	public void Thumb_lnkN_sqlY_adjH() 				{fxt.Lnki_( -1,  -1).File_(440, 500).tst(220, 250);}			// w=thumbnail default; h=calc from sql
	@Test 	public void Thumb_lnkW_sqlY() 					{fxt.Lnki_(200,  -1).File_(400, 500).tst(200, 250);}			// w=lnki; h=calc from sql
	@Test 	public void Thumb_lnkH_sqlY() 					{fxt.Lnki_( -1, 250).File_(400, 500).tst(200, 250);}			// w=calc from sql
	@Test 	public void Thumb_lnkW_sqlY_W_too_large() 		{fxt.Lnki_(600, 750).File_(400, 500).tst(400, 500);}			// w/h: truncate to file
	@Test 	public void Thumb_w_is_wrong() 					{fxt.Lnki_( 20,  20).File_( 80, 100).tst( 16,  20);}
	@Test 	public void Thumb_w_is_wrong_2() 				{fxt.Lnki_( 65,  50).File_(160, 160).tst( 50,  50);}
	@Test 	public void Thumb_size_is_wrong() 				{fxt.Lnki_(128,  80).File_(720, 194).tst(128,  34);}
}
class Xof_xfer_itm_fxt {
	public Xof_xfer_itm_fxt ini() {lnki_img_type = Xop_lnki_type.Id_thumb; lnki_upright = -1; file_w = file_h = lnki_w = lnki_h = -1; return this;}
	public Xof_xfer_itm_fxt Lnki_img_type_(byte v) {lnki_img_type = v; return this;} private byte lnki_img_type;
	public Xof_xfer_itm_fxt Lnki_upright_(double v) {lnki_upright = v; return this;} double lnki_upright;
	public Xof_xfer_itm_fxt File_(int w, int h) {file_w = w; file_h = h; return this;} private int file_w, file_h;
	public Xof_xfer_itm_fxt Lnki_(int w, int h) {lnki_w = w; lnki_h = h; return this;} private int lnki_w, lnki_h;
	public Xof_xfer_itm_fxt tst(int expd_w, int expd_h) {
		boolean wmf_thumbable = Xof_xfer_itm_.Lnki_thumbable_calc(lnki_img_type, lnki_w, lnki_h);
		Int_2_ref calc_size = new Int_2_ref();
		Xof_xfer_itm_.Calc_xfer_size(calc_size, Xop_lnki_type.Id_thumb, Xof_img_size.Thumb_width_img, file_w, file_h, lnki_w, lnki_h, wmf_thumbable, lnki_upright);
		Tfds.Eq(expd_w, calc_size.Val_0());
		Tfds.Eq(expd_h, calc_size.Val_1());
		return this;
	}
}
