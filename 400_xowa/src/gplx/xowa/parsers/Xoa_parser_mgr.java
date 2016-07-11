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
import gplx.xowa.parsers.xndes.*; import gplx.xowa.parsers.htmls.*; import gplx.xowa.parsers.uniqs.*;
public class Xoa_parser_mgr {		
	private final    Mwh_doc_wkr__atr_bldr atr_bldr = new Mwh_doc_wkr__atr_bldr();
	public Xop_tkn_mkr			Tkn_mkr()		{return tkn_mkr;}		private final    Xop_tkn_mkr tkn_mkr = new Xop_tkn_mkr();
	public Xop_uniq_mgr			Core__uniq_mgr() {return core__uniq_mgr;} private final    Xop_uniq_mgr core__uniq_mgr = new Xop_uniq_mgr();
	public Mwh_atr_parser		Xnde__atr_parser() {return atr_parser;} private final    Mwh_atr_parser atr_parser = new Mwh_atr_parser();
	public Mwh_atr_itm[]		Xnde__parse_atrs(byte[] src, int src_bgn, int src_end) {
		synchronized (atr_bldr) {// LOCK:app-level; DATE:2016-07-06
			//if (src_bgn < src_end) {	// CHART
			//	src = Bry_.Mid(src, src_bgn, src_end);
			//	src = gplx.xowa.parsers.xndes.Xop_xnde_tkn.uniq_mgr.Parse(src);
			//	src_bgn = 0;
			//	src_end = src.length;
			//}
			atr_parser.Parse(atr_bldr, -1, -1, src, src_bgn, src_end);
			return atr_bldr.To_atr_ary();
		}
	}
	public Mwh_atr_itm[]		Xnde__parse_atrs_for_tblw(byte[] src, int src_bgn, int src_end) {
		synchronized (atr_bldr) { // LOCK:app-level; DATE:2016-07-06
			//int angle_bgn_pos = Bry_find_.Find_fwd(src, Byte_ascii.Angle_bgn, src_bgn, src_end);
			//if (angle_bgn_pos != Bry_find_.Not_found) {
			//	src = Bry_.Mid(src, src_bgn, src_end);
			//	src = Bry_.Replace(src, Byte_ascii.Angle_bgn_bry, gplx.langs.htmls.Gfh_entity_.Lt_bry);
			//	src_bgn = 0;
			//	src_end = src.length;
			//}
			atr_parser.Parse(atr_bldr, -1, -1, src, src_bgn, src_end);
			return atr_bldr.To_atr_ary();
		}
	}
}
