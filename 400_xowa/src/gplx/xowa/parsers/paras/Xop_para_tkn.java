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
package gplx.xowa.parsers.paras;
import gplx.langs.htmls.Gfh_tag_;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.errs.ErrUtl;
import gplx.xowa.Xoae_page;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.htmls.core.htmls.Xoh_html_wtr;
import gplx.xowa.htmls.core.htmls.Xoh_html_wtr_;
import gplx.xowa.htmls.core.htmls.Xoh_html_wtr_cfg;
import gplx.xowa.htmls.core.htmls.Xoh_wtr_ctx;
import gplx.xowa.parsers.Xop_ctx;
import gplx.xowa.parsers.Xop_tkn_grp;
import gplx.xowa.parsers.Xop_tkn_itm_;
import gplx.xowa.parsers.Xop_tkn_itm_base;
public class Xop_para_tkn extends Xop_tkn_itm_base {
	public Xop_para_tkn(int pos) {this.Tkn_ini_pos(false, pos, pos);}
	@Override public byte Tkn_tid() {return Xop_tkn_itm_.Tid_para;}
	public byte Para_end() {return para_end;} public Xop_para_tkn Para_end_(byte v) {para_end = v; return this;} private byte para_end = Tid_none;
	public byte Para_bgn() {return para_bgn;} public Xop_para_tkn Para_bgn_(byte v) {para_bgn = v; return this;} private byte para_bgn = Tid_none;
	public int Space_bgn() {return space_bgn;} public Xop_para_tkn Space_bgn_(int v) {space_bgn = v; return this;} private int space_bgn = 0;
	public boolean Nl_bgn() {return nl_bgn;} public Xop_para_tkn Nl_bgn_y_() {nl_bgn = true; return this;} private boolean nl_bgn;
	public static final byte 
	  Tid_none = 0		//
	, Tid_para = 1		// </p>
	, Tid_pre  = 2		// </pre>
	;
	@Override public void Html__write(BryWtr bfr, Xoh_html_wtr wtr, Xowe_wiki wiki, Xoae_page page, Xop_ctx ctx, Xoh_wtr_ctx hctx, Xoh_html_wtr_cfg cfg, Xop_tkn_grp grp, int sub_idx, byte[] src) {
		if (nl_bgn && bfr.Len() > 0) {
			if (hctx.Mode_is_alt())							// write called during alt=''
				bfr.AddByteSpace();						// write '\s', not '\n'
			else
				bfr.AddByteIfNotLast(AsciiByte.Nl);	// write '\n'
		}
		switch (para_end) {
			case Xop_para_tkn.Tid_none:		break;
			case Xop_para_tkn.Tid_para:		bfr.Add(Gfh_tag_.P_rhs).AddByteNl(); break;		// '<p>'
			case Xop_para_tkn.Tid_pre:		bfr.Add(Gfh_tag_.Pre_rhs).AddByteNl(); break;	// '<pre>'
			default:						throw ErrUtl.NewUnhandled(para_end);
		}
		switch (para_bgn) {
			case Xop_para_tkn.Tid_none:		break;
			case Xop_para_tkn.Tid_para:		Xoh_html_wtr_.Para__assert_tag_starts_on_nl(bfr, this.Src_bgn()); bfr.Add(Gfh_tag_.P_lhs); break;		// '</p>'
			case Xop_para_tkn.Tid_pre:		Xoh_html_wtr_.Para__assert_tag_starts_on_nl(bfr, this.Src_bgn()); bfr.Add(Gfh_tag_.Pre_lhs); break;	// '</pre>'
			default:						throw ErrUtl.NewUnhandled(para_bgn);
		}
		if (space_bgn > 0)
			bfr.AddByteRepeat(AsciiByte.Space, space_bgn);
	}
}
