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
import gplx.core.bits.*;
import gplx.xowa.parsers.lnkis.*;
public class Xof_img_size {
	public int Html_w() {return html_w;} private int html_w;
	public int Html_h() {return html_h;} private int html_h;
	public int File_w() {return file_w;} private int file_w;	// NOTE: file_w will always equal html_w, unless rounding is implemented; EX: html_w=150,151,152 -> file_w=150
	public int File_h() {return file_h;} private int file_h;
	public boolean File_is_orig() {return file_is_orig;} private boolean file_is_orig;
	private void Clear() {
		html_w = html_h = file_w = file_h = 0;
		file_is_orig = false;
	}
	public void Html_size_calc(int exec_tid, int lnki_w, int lnki_h, byte lnki_type, int upright_patch, double lnki_upright, int orig_ext, int orig_w, int orig_h, int thm_dflt_w) {
		synchronized (this) {
			this.Clear();											// always clear before calc; caller should be responsible, but just to be safe.
			if (	Xof_ext_.Id_supports_time(orig_ext)				// ext is video
				&&	lnki_w == Xof_img_size.Null						// no size specified
				&&	!Xop_lnki_type.Id_is_thumbable(lnki_type)		// not thumb which is implicitly 220; PAGE:en.w:Edward_Snowden; DATE:2015-08-17
				)
				lnki_w = orig_w;									// use original size; EX:[[File:A.ogv]] -> [[File:A.ogv|550px]] where 550px is orig_w; DATE:2015-08-07
			if (Bitmask_.Has_int(lnki_type, Xop_lnki_type.Id_frame)	// frame: always return orig size; Linker.php!makeThumbLink2; // Use image dimensions, don't scale
				&& lnki_h == Null) {								// unless lnki_h specified; DATE:2013-12-22
				html_w = file_w = orig_w;
				html_h = file_h = orig_h;
				file_is_orig = Xof_ext_.Orig_file_is_img(orig_ext);	// file_is_orig = true, unless svg, ogv, pdf
				if (file_is_orig)
					file_w = file_h = Size__same_as_orig;
				return;
			}
			html_w = lnki_w; html_h = lnki_h;						// set html vals to lnki vals
			file_is_orig = false;
			if (html_w == Null && html_h == Null) {					// no size set; NOTE: do not default to thumb if only height is set; EX: x900px should have w=0 h=900
				if (Xop_lnki_type.Id_defaults_to_thumb(lnki_type))
					html_w = thm_dflt_w;
				else if (	orig_ext == Xof_ext_.Id_pdf				// pdf and viewing on page; default to 220
						&&	exec_tid == Xof_exec_tid.Tid_wiki_page)
					html_w = thm_dflt_w;
				else
					html_w = orig_w;
			}
			html_w = Upright_calc(upright_patch, lnki_upright, html_w, lnki_w, lnki_h, lnki_type);
			if (orig_w == Null) return;								// no orig_w; just use html_w and html_h (html_h will likely be -1 and wrong)
			boolean ext_is_svg = orig_ext == Xof_ext_.Id_svg;
			if (html_w == Xof_img_size.Null) {
				if	(	ext_is_svg									// following strange MW logic; REF.MW:Linker.php|makeImageLink|If its a vector image, and user only specifies height, we don't want it to be limited by its "normal" width; DATE: 2013-11-26
					&&	html_h != Xof_img_size.Null)
					html_w = Svg_max_width;
				else
					html_w = orig_w;								// html_w missing >>> use orig_w; REF.MW:Linker.php|makeImageLink2|$hp['width'] = $file->getWidth( $page );				
			}
			if (html_h != Xof_img_size.Null) {						// html_h exists; REF.MW:ImageHandler.php|normaliseParams|if ( isset( $params['height'] ) && $params['height'] != -1 ) {
				if (	(long)html_w * (long)orig_h 
					>	(long)html_h * (long)orig_w)				// html ratio > orig ratio; recalc html_w; SEE:NOTE_2; NOTE: casting to long to prevent int overflow; [[File:A.png|9999999999x90px]]; DATE:2014-04-26
					html_w = Calc_w(orig_w, orig_h, html_h);
			}
			html_h = Scale_h(orig_w, orig_h, html_w);				// calc html_h
			if (	html_w >= orig_w								// html >= orig
				&&	(	Xof_ext_.Orig_file_is_img(orig_ext)			// orig is img (ignore for svg, ogv, pdf, etc)
					||	ext_is_svg && exec_tid == Xof_exec_tid.Tid_wiki_file	// limit to size if svg and [[File]] page
					)
				) {
				file_is_orig = true;								// use orig img (don't create thumb)
				file_w = file_h = Size__same_as_orig;
				if (Xop_lnki_type.Id_limits_large_size(lnki_type)) {// do not allow html_w > orig_w; REF.MW:Generic.php|normaliseParams
					html_w = orig_w;
					html_h = orig_h;
				}
			}
			else {													// html < orig
				file_w = html_w;
				file_h = html_h;
			}
		}
	}
	public static int Calc_w(int file_w, int file_h, int lnki_h) {		// REF.MW:media/MediaHandler.php|fitBoxWidth
		double ideal_w = (double)file_w * (double)lnki_h / (double)file_h;
		double ideal_w_ceil = Math_.Ceil(ideal_w);
		return Math_.Round(ideal_w_ceil * file_h / file_w, 0) > lnki_h
			? (int)Math_.Floor(ideal_w)
			: (int)ideal_w_ceil;
	}
	public static int Scale_h(int file_w, int file_h, int lnki_w) {
		return file_w == 0												// REF.MW:File.php|scaleHeight
			? 0
			: (int)Math_.Round(((double)lnki_w * file_h) / file_w, 0);	// NOTE: (double) needed else result will be int and fraction will be truncated
	}
	public static int Upright_calc(int upright_patch_tid, double upright, int cur_w, int lnki_w, int lnki_h, byte lnki_type) {
		boolean upright_patch_use_thumb_w = Xof_patch_upright_tid_.Split_use_thumb_w(upright_patch_tid);
		boolean upright_patch_fix_default = Xof_patch_upright_tid_.Split_fix_default(upright_patch_tid);
		double upright_default_val = upright_patch_fix_default ? .75f : 1f;
		if (upright_patch_use_thumb_w) {
			if	(upright != Upright_null							// upright set
				&& lnki_w == Null									// w is null; EX: ( -1, 220); must exit early or will become 0; DATE:2013-11-23
				&& lnki_h == Null									// h is null; EX: (220,  -1); REF:Linker.php|makeImageLink|"if (... !$hp['width'] ); 
				&& Xop_lnki_type.Id_supports_upright(lnki_type)
				) {
				if	(upright == Upright_default_marker) upright = upright_default_val;	// upright is default; set val to .75; EX: [[File:A.png|upright]]						
				int rv = (int)(Thumb_width_img * upright);
				return Round_10p2(rv);
			}
			else
				return cur_w;										// upright doesn't apply; return self;
		}
		else {														// support old broken calc
			if 		(upright == Upright_null)		return cur_w;	// upright is null; return width
			else if (upright == Upright_default_marker)upright = upright_default_val;	// upright is default; set val to .75; NOTE: wrong b/c [[File:A.png|upright=1]] -> .75
			if		(cur_w == Null)					return Null;	// width is null (-1); must exit early or will become 0; DATE:2013-11-23
			int rv = (int)(cur_w * upright);						// NOTE: wrong b/c should be Thumb_width_img, not cur_w
			return Round_10p2(rv);
		}
	}
	private static int Round_10p2(int v) {
		int mod = v % 10;
		if (mod > 4) 	v += 10 - mod;
		else			v -= mod;
		return v;
	}
	public static final int Null = -1;
	public static final int Thumb_width_img = 220, Thumb_width_ogv = 220;
	public static final    double Upright_null = -1, Upright_default_marker = 0; // REF:MW: if ( isset( $fp['upright'] ) && $fp['upright'] == 0 )
	public static final int Size__neg1 = -1, Size_null = 0;	// Size_null = 0, b/c either imageMagick / inkscape fails when -1 is passed
	public static final int Size__same_as_orig = -1;
	private static final int Svg_max_width = 2048;
}
/*
NOTE_1:proc source/layout
MW calls the falling procs
. Linker.php|makeImageLink2
. Linker.php|makeThumbLink2
. File.php|transform
. Bitmap.php|normaliseParams
. media/MediaHandler.php|fitBoxWidth
. File.php|scaleHeight
Note that this proc is a selective culling of the w,h setting code in the above (the procs do a lot of other checks/building)
also, MW's if branching can be combined. for now, emulating MW and not enforcing matching if/else 
NOTE_2: lnki_ratio > orig_ratio
REF.MW:media/MediaHandler.php|fitBoxWidth
COMMENT:"Height is the relative smaller dimension, so scale width accordingly"
consider file of 200,100 (2:1)
EX_1: view is 120,40 (3:1)
- dimensions are either (a) 120,80 or (b) 80,40
- use (b) 80,40
EX_2: view is 120,80 (1.5:1)
- dimensions are either (a) 120,60 or (b) 160,80
- use (a) 120,60
*/
