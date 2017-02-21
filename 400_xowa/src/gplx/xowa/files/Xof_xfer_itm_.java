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
import gplx.core.primitives.*;
import gplx.gfui.*; import gplx.xowa.parsers.lnkis.*;
public class Xof_xfer_itm_ {
	public static void Calc_xfer_size(Int_2_ref rv, byte lnki_type, int thumb_default_w, int file_w, int file_h, int lnki_w, int lnki_h, boolean lnki_thumb, double lnki_upright, Xof_ext ext, int exec_tid) {
		boolean ext_is_svg = ext.Id_is_svg();
		boolean limit_size = !ext_is_svg || (ext_is_svg && exec_tid == Xof_exec_tid.Tid_wiki_file);
		Calc_xfer_size(rv, lnki_type, thumb_default_w, file_w, file_h, lnki_w, lnki_h, lnki_thumb, lnki_upright, limit_size);
	}
	public static void Calc_xfer_size(Int_2_ref rv, byte lnki_type, int thumb_default_w, int file_w, int file_h, int lnki_w, int lnki_h, boolean lnki_thumb, double lnki_upright) {Calc_xfer_size(rv, lnki_type, thumb_default_w, file_w, file_h, lnki_w, lnki_h, lnki_thumb, lnki_upright, true);}
	public static void Calc_xfer_size(Int_2_ref rv, byte lnki_type, int thumb_default_w, int file_w, int file_h, int lnki_w, int lnki_h, boolean lnki_thumb, double lnki_upright, boolean thumb_width_must_be_lt_file_width) {
		int rv_w = lnki_w, rv_h = lnki_h;
		if (lnki_w < 1 && lnki_h < 1) {
			if (lnki_thumb)		rv_w = thumb_default_w;		// do not default to thumb if only height is set; EX: x900px should have w=0 h=900
			else				rv_w = file_w;
		}
		rv_w = Xof_img_size.Upright_calc(Xof_patch_upright_tid_.Tid_all, lnki_upright, rv_w, lnki_w, lnki_h, lnki_type);	// only v1 calls Calc_xfer_size
		if (file_w < 1)				rv.Val_all_(rv_w, rv_h);
		else						Xof_xfer_itm_.Calc_view(rv, lnki_type, rv_w, rv_h, file_w, file_h, thumb_width_must_be_lt_file_width);
	}
	public static void Calc_view(Int_2_ref rv, byte lnki_type, int lnki_w, int lnki_h, int file_w, int file_h, boolean thumb_width_must_be_lt_file_width) {// SEE:NOTE_1 for proc source/layout
		if (lnki_w == -1) lnki_w = file_w;						// lnki_w missing >>> use file_w; REF.MW:Linker.php|makeImageLink2|$hp['width'] = $file->getWidth( $page );				
		if (lnki_h != -1) {										// height exists; REF.MW:Generic.php|normaliseParams|if ( isset( $params['height'] ) && $params['height'] != -1 ) {
			if (lnki_w * file_h > lnki_h * file_w)  {			// lnki ratio > file ratio; SEE:NOTE_2;
				lnki_w = Calc_w(file_w, file_h, lnki_h);
			}
		}
		lnki_h = Scale_h(file_w, file_h, lnki_w);
		if (	Xop_lnki_type.Id_limits_large_size(lnki_type)	// added on DATE:2014-04-09
			&&	lnki_w > file_w && thumb_width_must_be_lt_file_width) {	// do not allow lnki_w > file_w; REF.MW:Generic.php|normaliseParams
			lnki_w = file_w; lnki_h = file_h;
		}
		rv.Val_all_(lnki_w, lnki_h);
	}
	public static int Calc_w(int file_w, int file_h, int lnki_h) {
		double ideal_w = (double)file_w * (double)lnki_h / (double)file_h;
		double ideal_w_ceil = Math_.Ceil(ideal_w);
		return Math_.Round(ideal_w_ceil * file_h / file_w, 0) > lnki_h
			? (int)Math_.Floor(ideal_w)
			: (int)ideal_w_ceil;
	}
	public static int Scale_h(int file_w, int file_h, int lnki_w) {
		return file_w == 0												// REF.MW:File.php|scaleHeight
			? 0
			: (int)Math_.Round(((double)lnki_w * file_h) / file_w, 0);	// NOTE: (double) needed else result will be int and decimal will be automatically truncated
	}
	public static boolean Lnki_thumbable_calc(byte lnki_type, int lnki_w, int lnki_h) {
		return 
			(	lnki_type == Xop_lnki_type.Id_frame && lnki_w != -1 && lnki_h != -1)
			||	(Xop_lnki_type.Id_defaults_to_thumb(lnki_type) || lnki_w != -1 || lnki_h != -1)
			;
	}	// SEE:NOTE_3
}
/*
NOTE_1:proc source/layout
MW calls the falling procs
. Linker.php|makeImageLink2
. Linker.php|makeThumbLink2
. File.php|transform
. Bitmap.php|normaliseParams
. Generic.php|normaliseParams
. File.php|scaleHeight
Note that this proc is a selective culling of the w,h setting code in the above (the procs do a lot of other checks/building)
also, MW's if branching can be combined. for now, emulating MW and not enforcing matching if/else 

NOTE_2: view ratio > file ratio
REF.MW:ImageFunctions.php|wfFitBoxWidth
don't know why this logic exists; for now, articulating example

consider file of 200,100 (2:1)
EX_1: view is 120,40 (3:1)
- (a) 120,80 or (b) 80,40
- (b) 80,40

EX_2: view is 120,80 (1.5:1)
- (a) 120,60 or (b) 160,80
- (a) 120,60
*/