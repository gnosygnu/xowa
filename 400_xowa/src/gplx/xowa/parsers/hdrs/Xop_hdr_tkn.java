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
package gplx.xowa.parsers.hdrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
public class Xop_hdr_tkn extends Xop_tkn_itm_base {
	public Xop_hdr_tkn(int bgn, int end, int num) {this.Tkn_ini_pos(false, bgn, end); this.num = num;}
	@Override public byte Tkn_tid()			{return Xop_tkn_itm_.Tid_hdr;}
	public int		Num()					{return num;}					private int num = -1;					// EX: 2 for <h2>
	public int		Manual_bgn()			{return manual_bgn;}			private int manual_bgn;					// unbalanced count; EX: === A == -> 1
	public int		Manual_end()			{return manual_end;}			private int manual_end;					// unbalanced count; EX: == A === -> 1
	public boolean  First_in_doc()			{return first_in_doc;}			private boolean first_in_doc;				// true if 1st hdr in doc
	public void		First_in_doc_y_()		{first_in_doc = true;} 
	public byte[]	Section_editable_page()	{return section_editable_page;}	private byte[] section_editable_page;	// EX: Earth as in 'href="/wiki/Earth"'
	public int		Section_editable_idx()	{return section_editable_idx;}	private int section_editable_idx;		// EX: 1 as in "section=1"

	public void Init_by_parse(int num, int manual_bgn, int manual_end) {
		this.num = num;
		this.manual_bgn = manual_bgn;
		this.manual_end = manual_end;
	}
	public void Section_editable_(byte[] section_editable_page, int section_editable_idx) {
		this.section_editable_page = section_editable_page;
		this.section_editable_idx = section_editable_idx;
	}

	public static final    Xop_hdr_tkn[] Ary_empty = new Xop_hdr_tkn[0];
}
