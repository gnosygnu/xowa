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
package gplx.xowa.addons.htmls.tocs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.htmls.*;
class Xoh_toc_wkr__lvl {
	private static final int Toc_lvls_max = 7;
	private final    int[] sub_lvl_count = new int[Toc_lvls_max], lvl_count = new int[Toc_lvls_max]; 
	private int prv_lvl, toc_lvl, prv_toc_lvl;
	private int uid = 0;
	public void Clear() {
		uid = prv_lvl = toc_lvl = prv_toc_lvl = 0;
	}
	public void Calc_level(Xoh_toc_itm rv, int lvl) {	// REF.MW:Parser.php!formatHeadings
		if (lvl > prv_lvl) { // Increase TOC lvl
			toc_lvl++;
			sub_lvl_count[toc_lvl - List_adp_.Base1] = 0;
			if (toc_lvl < Toc_lvls_max) {
				prv_toc_lvl = toc_lvl;
				// $toc .= Linker::tocIndent();
			}
		}
		else if (lvl < prv_lvl && toc_lvl > 1) {// Decrease TOC lvl, find lvl to jump to
			int i = toc_lvl;
			for (; i > 0; i--) {
				int cur_lvl_count = lvl_count[i];
				if (cur_lvl_count == lvl) {	// Found last matching lvl
					toc_lvl = i;
					break;
				}
				else if (cur_lvl_count < lvl) {	// Found first matching lvl below current lvl
					toc_lvl = i + 1;
					break;
				}
			}
			if (i == 0)
				toc_lvl = 1;
			if (toc_lvl < Toc_lvls_max) {
				if (prv_toc_lvl < Toc_lvls_max) {
					// Unindent only if the previous toc lvl was shown :p
					// $toc .= Linker::tocUnindent( $prv_toc_lvl - $toc_lvl );
					prv_toc_lvl = toc_lvl;
				} else {
					// $toc .= Linker::tocLineEnd();
				}
			}
		}
		else {	// No change in lvl, end TOC line
			if (toc_lvl < Toc_lvls_max) {
				// $toc .= Linker::tocLineEnd();
			}
		}            
		lvl_count[toc_lvl] = lvl;
		sub_lvl_count[toc_lvl - List_adp_.Base1] = sub_lvl_count[toc_lvl - List_adp_.Base1] + 1;
		prv_lvl = lvl;	// NOTE: same as "if ( $toclevel ) $prevlevel = $level;" but at end of block

		// Tfds.Write(lvl, prv_lvl, lvl, toc_lvl, Int_.Ary_concat(",", lvl_count), Int_.Ary_concat(",", sub_lvl_count));
		int[] copy = new int[toc_lvl];
		Int_.Ary_copy_to(sub_lvl_count, toc_lvl, copy);
		rv.Set__lvl(++uid, toc_lvl, copy);
	}
}
