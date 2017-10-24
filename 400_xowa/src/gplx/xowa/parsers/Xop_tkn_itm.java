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
package gplx.xowa.parsers; import gplx.*; import gplx.xowa.*;
import gplx.xowa.parsers.tmpls.*;
import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.htmls.*;
public interface Xop_tkn_itm extends Xop_tkn_grp {
	byte Tkn_tid();
	Xop_tkn_itm Tkn_ini_pos(boolean immutable, int bgn, int end);
	Xop_tkn_itm Tkn_clone(Xop_ctx ctx, int bgn, int end);
	boolean Tkn_immutable();
	Xop_tkn_grp Tkn_grp();
	int Src_bgn();
	int Src_end();
	int Src_bgn_grp(Xop_tkn_grp grp, int sub_idx);
	int Src_end_grp(Xop_tkn_grp grp, int sub_idx);
	int Tkn_sub_idx();
	boolean Ignore();
	Xop_tkn_itm Tkn_grp_(Xop_tkn_grp grp, int sub_idx);
	void Src_end_(int v);
	void Src_end_grp_(Xop_ctx ctx, Xop_tkn_grp grp, int sub_idx, int src_end);
	Xop_tkn_itm Ignore_y_();
	void Ignore_y_grp_(Xop_ctx ctx, Xop_tkn_grp grp, int sub_idx);
	void Clear();
	void Tmpl_fmt(Xop_ctx ctx, byte[] src, Xot_fmtr fmtr);
	void Tmpl_compile(Xop_ctx ctx, byte[] src, Xot_compile_data prep_data); // SEE:NOTE_1:Tmpl_compile
	boolean Tmpl_evaluate(Xop_ctx ctx, byte[] src, Xot_invk caller, Bry_bfr bfr);
	void Html__write(Bry_bfr bfr, Xoh_html_wtr wtr, Xowe_wiki wiki, Xoae_page page, Xop_ctx ctx, Xoh_wtr_ctx hctx, Xoh_html_wtr_cfg cfg, Xop_tkn_grp grp, int sub_idx, byte[] src);
}
/*
NOTE_1: Tmpl_compile
- called for tmpl_defn
- identifies tkn as static or dynamic; important for evaluate later; if static, evaluate will simply extract src
- if static, parses prm; EX: {{{1|a}}} will produce member variables of idx=1 and dflt=a
- if static, parses tmpl_name; EX: {{concat|a|b}} will generate name of concat
- if <onlyinclude> mark tmpl accordingly
*/
