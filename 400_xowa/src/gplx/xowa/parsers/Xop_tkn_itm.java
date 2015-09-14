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
package gplx.xowa.parsers; import gplx.*; import gplx.xowa.*;
import gplx.xowa.parsers.tmpls.*;
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
}
/*
NOTE_1: Tmpl_compile
- called for tmpl_defn
- identifies tkn as static or dynamic; important for evaluate later; if static, evaluate will simply extract src
- if static, parses prm; EX: {{{1|a}}} will produce member variables of idx=1 and dflt=a
- if static, parses tmpl_name; EX: {{concat|a|b}} will generate name of concat
- if <onlyinclude> mark tmpl accordingly
*/
