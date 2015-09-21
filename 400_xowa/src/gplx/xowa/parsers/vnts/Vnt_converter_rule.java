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
package gplx.xowa.parsers.vnts; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.btries.*; import gplx.core.primitives.*;
class Vnt_converter_rule {	// REF.MW: /languages/LanguageConverter.php|ConverterRule
	private final byte[] src;
	private final int src_bgn, src_end;
	private int pipe_pos = -1;
	public Vnt_converter_rule(byte[] src, int src_bgn, int src_end) {
		this.src = src; this.src_bgn = src_bgn; this.src_end = src_end;
	}
	public void Parse() {
	}
	public void Parse_flags(Vnt_flag_parser parser) {
		this.pipe_pos = Bry_find_.Find_fwd(src, Byte_ascii.Pipe, src_bgn, src_end);
		if (pipe_pos != Bry_find_.Not_found)			// "|" found; EX: -{A|}-
			parser.Parse(src, src_bgn, pipe_pos);
		int flag_count = parser.Count();
		if		(flag_count == 0)											parser.Set_y(Vnt_flag_itm_.Tid_show);
		else if (parser.Limit_if_exists(Vnt_flag_itm_.Tid_raw))				{}
		else if (parser.Limit_if_exists(Vnt_flag_itm_.Tid_name))			{}
		else if (parser.Limit_if_exists(Vnt_flag_itm_.Tid_del))				{}
		else if (flag_count == 1 && parser.Get(Vnt_flag_itm_.Tid_title))	parser.Set_y(Vnt_flag_itm_.Tid_macro);
		else if (parser.Get(Vnt_flag_itm_.Tid_macro)) {
			boolean exists_d = parser.Get(Vnt_flag_itm_.Tid_descrip);
			boolean exists_t = parser.Get(Vnt_flag_itm_.Tid_title);
			parser.Clear();
			parser.Set_y_many(Vnt_flag_itm_.Tid_all, Vnt_flag_itm_.Tid_macro);
			if (exists_d) parser.Set_y(Vnt_flag_itm_.Tid_descrip);
			if (exists_t) parser.Set_y(Vnt_flag_itm_.Tid_title);
		}
		else {
			if (parser.Get(Vnt_flag_itm_.Tid_add))
				parser.Set_y_many(Vnt_flag_itm_.Tid_all, Vnt_flag_itm_.Tid_show);
			if (parser.Get(Vnt_flag_itm_.Tid_descrip))
				parser.Set_n(Vnt_flag_itm_.Tid_show);
			parser.Limit_if_exists_vnts();	// try to find flags like "zh-hans", "zh-hant"; allow syntaxes like "-{zh-hans;zh-hant|XXXX}-"
		}
	}
	public void Parse_rules(Vnt_rule_parser parser) {
		parser.Parse(src, src_bgn, src_end);
	}
}
