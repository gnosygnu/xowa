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
package gplx.xowa.mws.parsers.tables; import gplx.*; import gplx.xowa.*; import gplx.xowa.mws.*; import gplx.xowa.mws.parsers.*;
import gplx.langs.phps.utls.*;
import gplx.xowa.parsers.htmls.*;
import gplx.xowa.mws.utls.*; import gplx.xowa.parsers.uniqs.*;
public class Xomw_table_wkr implements gplx.core.brys.Bry_split_wkr {// THREAD.UNSAFE: caching for repeated calls
	private final    Bry_bfr tmp;
	private Bry_bfr bfr;
	private final    Xomw_sanitizer sanitizer; private final    Xomw_strip_state strip_state;
	private final    List_adp 
	  td_history       = List_adp_.New() // Is currently a td tag open?
	, last_tag_history = List_adp_.New() // Save history of last lag activated (td, th or caption)
	, tr_history       = List_adp_.New() // Is currently a tr tag open?
	, tr_attributes    = List_adp_.New() // history of tr attributes
	, has_opened_tr    = List_adp_.New() // Did this table open a <tr> element?
	;
	private int indent_level = 0; // indent level of the table
	private byte[] first_2 = new byte[2];
	public Xomw_table_wkr(Xomw_parser parser) {
		this.tmp = parser.Tmp();
		this.sanitizer = parser.Sanitizer();
		this.strip_state = parser.Strip_state();
	}
	public void Do_table_stuff(Xomw_parser_ctx pctx, Xomw_parser_bfr pbfr) {
		Bry_bfr src_bfr = pbfr.Src();
		byte[] src = src_bfr.Bfr();
		int src_bgn = 0;
		int src_end = src_bfr.Len();
		this.bfr = pbfr.Trg();
		pbfr.Switch();

		indent_level = 0;
		
		Bry_split_.Split(src, src_bgn, src_end, Byte_ascii.Nl, Bool_.N, this); // PORTED.SPLIT: $lines = StringUtils::explode("\n", $text);

		// Closing open td, tr && table
		while (td_history.Len() > 0) {
			if (Php_ary_.Pop_bool_or_n(td_history)) {
				bfr.Add_str_a7("</td>\n");
			}
			if (Php_ary_.Pop_bool_or_n(tr_history)) {
				bfr.Add_str_a7("</tr>\n");
			}
			if (!Php_ary_.Pop_bool_or_n(has_opened_tr)) {
				bfr.Add_str_a7("<tr><td></td></tr>\n");
			}
			bfr.Add_str_a7("</table>\n");
		}

		// Remove trailing line-ending (b/c)
		if (bfr.Get_at_last_or_nil_if_empty() == Byte_ascii.Nl) {
			bfr.Del_by_1();
		}

		// special case: don't return empty table
		if (	bfr.Len() == Len__tb__empty
			&&	Bry_.Eq(bfr.Bfr(), 0, Len__tb__empty, Html__tb__empty)) {
			bfr.Clear();
			return;
		}
	}
	public int Split(byte[] src, int itm_bgn, int itm_end) {
		byte[] out_line = Bry_.Mid(src, itm_bgn, itm_end); // MW: "$outLine"
		byte[] line = Bry_.Trim(out_line);                 // MW: "$line"

		int line_len = line.length;
		if (line_len == 0) { // empty line, go to next line
			bfr.Add(out_line).Add_byte_nl();
			return Bry_split_.Rv__ok;
		}

		byte first_char = line[0];
		first_2[0] = line[0];
		first_2[1] = line_len == 1 ? Byte_ascii.Null : line[1];

		// PORTED: preg_match('/^(:*)\s*\{\|(.*)$/', $line, $matches)
		byte[] tblw_atrs = null;
		boolean tblw_bgn_found = false;
		int colons_end = Bry_find_.Find_fwd_while(src, 0, line_len, Byte_ascii.Colon);
		int tblw_bgn = Bry_find_.Find_fwd_while(line, colons_end, line_len, Byte_ascii.Space);
		int tblw_atrs_bgn = tblw_bgn + 2;
		if (Bry_.Eq(line, tblw_bgn, tblw_atrs_bgn, Wtxt__tb__bgn)) {
			tblw_bgn_found = true;
			tblw_atrs = (tblw_atrs_bgn == line_len) ? Bry_.Empty : Bry_.Mid(line, tblw_atrs_bgn, line_len);
		}
		if (tblw_bgn_found) {
			// First check if we are starting a new table
			indent_level = colons_end;

			tblw_atrs = strip_state.Unstrip_both(tblw_atrs);

			// PORTED: out_line = str_repeat('<dl><dd>', $indent_level) . "<table{atrs}>";
			for (int j = 0; j < indent_level; j++)
				tmp.Add(Html__dl__bgn);
			tmp.Add_str_a7("<table");
			sanitizer.Fix_tag_attributes(tmp, Name__table, tblw_atrs);
			tmp.Add_byte(Byte_ascii.Angle_end);
			out_line = tmp.To_bry_and_clear();
			td_history.Add(false);
			last_tag_history.Add(Bry_.Empty);
			tr_history.Add(false);
			tr_attributes.Add(Bry_.Empty);
			has_opened_tr.Add(false);
		}
		else if (td_history.Len() == 0) {
			// Don't do any of the following
			bfr.Add(out_line).Add_byte_nl();
			return Bry_split_.Rv__ok;
		}
		else if (Bry_.Eq(first_2, Wtxt__tb__end)) {
			// We are ending a table
			line = tmp.Add_str_a7("</table>").Add_mid(line, 2, line.length).To_bry_and_clear();
			byte[] last_tag = Php_ary_.Pop_bry_or_null(last_tag_history);

			if (!Php_ary_.Pop_bool_or_n(has_opened_tr)) {
				line = tmp.Add_str_a7("<tr><td></td></tr>").Add(line).To_bry_and_clear();
			}

			if (Php_ary_.Pop_bool_or_n(tr_history)) {
				line = tmp.Add_str_a7("</tr>").Add(line).To_bry_and_clear();
			}

			if (Php_ary_.Pop_bool_or_n(td_history)) {
				line = tmp.Add_str_a7("</").Add(last_tag).Add_byte(Byte_ascii.Angle_end).Add(line).To_bry_and_clear();
			}
			Php_ary_.Pop_bry_or_null(tr_attributes);
			// PORTED:$outLine = $line . str_repeat( '</dd></dl>', $indent_level );
			tmp.Add(line);
			for (int j = 0; j < indent_level; j++)
				tmp.Add(Html__dl__end);				
			out_line = tmp.To_bry_and_clear();
		}
		else if (Bry_.Eq(first_2, Wtxt__tr)) {
			// Now we have a table row
			line = Bry_.Mid(line, 2);        // PORTED: $line = preg_replace('#^\|-+#', '', $line);

			// Whats after the tag is now only attributes
			byte[] atrs = strip_state.Unstrip_both(line);
			sanitizer.Fix_tag_attributes(tmp, Name__tr, atrs);
			atrs = tmp.To_bry_and_clear();

			Php_ary_.Pop_bry_or_null(tr_attributes);
			tr_attributes.Add(atrs);

			line = Bry_.Empty;
			byte[] last_tag = Php_ary_.Pop_bry_or_null(last_tag_history);
			Php_ary_.Pop_bool_or_n(has_opened_tr);
			has_opened_tr.Add(true);

			if (Php_ary_.Pop_bool_or_n(tr_history)) {
				line = Html__tr__end;
			}

			if (Php_ary_.Pop_bool_or_n(td_history)) {
				line = tmp.Add_str_a7("</").Add(last_tag).Add_byte(Byte_ascii.Gt).Add(line).To_bry_and_clear();
			}

			out_line = line;
			tr_history.Add(false);
			td_history.Add(false);
			last_tag_history.Add(Bry_.Empty);
		}
		else if ( first_char == Byte_ascii.Pipe
				|| first_char == Byte_ascii.Bang
				|| Bry_.Eq(first_2, Wtxt__caption)
			) {
			// This might be cell elements, td, th or captions
			if (Bry_.Eq(first_2, Wtxt__caption)) {
				first_char = Byte_ascii.Plus;
				line = Bry_.Mid(line, 2);
			} else {
				line = Bry_.Mid(line, 1);
			}

			// Implies both are valid for table headings.
			if (first_char == Byte_ascii.Bang) {
				Xomw_string_utl.Replace_markup(line, 0, line.length, Wtxt__th2, Wtxt__td2); // $line = StringUtils::replaceMarkup('!!', '||', $line);
			}

			// Split up multiple cells on the same line.
			// FIXME : This can result in improper nesting of tags processed
			// by earlier parser steps.
			byte[][] cells = Bry_split_.Split(line, Wtxt__td2);
			if (cells.length == 0) cells = Cells__empty;	// handle "\n|\n" which should still generate "<tr><td></td></tr>", not ""; see TEST

			out_line = Bry_.Empty;

			byte[] previous = null;
			// Loop through each table cell
			int cells_len = cells.length;
			for (int j = 0; j < cells_len; j++) {
				byte[] cell = cells[j];
				previous = Bry_.Empty;
				if (first_char != Byte_ascii.Plus) {
					byte[] tr_after = Php_ary_.Pop_bry_or_null(tr_attributes);
					if (!Php_ary_.Pop_bool_or_n(tr_history)) {
						previous = tmp.Add_str_a7("<tr").Add(tr_after).Add_str_a7(">\n").To_bry_and_clear();
					}
					tr_history.Add(true);
					tr_attributes.Add(Bry_.Empty);
					Php_ary_.Pop_bool_or_n(has_opened_tr);
					has_opened_tr.Add(true);
				}

				byte[] last_tag = Php_ary_.Pop_bry_or_null(last_tag_history);

				if (Php_ary_.Pop_bool_or_n(td_history)) {
					previous = tmp.Add_str_a7("</").Add(last_tag).Add_str_a7(">\n").Add(previous).To_bry_and_clear();
				}

				if (first_char == Byte_ascii.Pipe) {
					last_tag = Name__td;
				}
				else if (first_char == Byte_ascii.Bang) {
					last_tag = Name__th;
				}
				else if (first_char == Byte_ascii.Plus) {
					last_tag = Name__caption;
				}
				else {
					last_tag = Bry_.Empty;
				}

				last_tag_history.Add(last_tag);

				// A cell could contain both parameters and data
				byte[][] cell_data = Bry_split_.Split_w_max(cell, Byte_ascii.Pipe, 2);

				// Bug 553: Note that a '|' inside an invalid link should not
				// be mistaken as delimiting cell parameters
				byte[] cell_data_0 = cell_data[0];
				byte[] cell_data_1 = cell_data[1];
				if (Bry_find_.Find_fwd(cell_data_0, Wtxt__lnki__bgn) != Bry_find_.Not_found) {
					cell = tmp.Add(previous).Add_byte(Byte_ascii.Angle_bgn).Add(last_tag).Add_byte(Byte_ascii.Angle_end).Add(cell).To_bry_and_clear();
				}
				else if (cell_data_1 == null) {
					cell = tmp.Add(previous).Add_byte(Byte_ascii.Angle_bgn).Add(last_tag).Add_byte(Byte_ascii.Angle_end).Add(cell_data_0).To_bry_and_clear();
				}
				else {
					byte[] atrs = strip_state.Unstrip_both(cell_data_0);
					tmp.Add(previous).Add_byte(Byte_ascii.Angle_bgn).Add(last_tag);
					sanitizer.Fix_tag_attributes(tmp, last_tag, atrs);
					tmp.Add_byte(Byte_ascii.Angle_end).Add(cell_data_1);
					cell = tmp.To_bry_and_clear();
				}

				out_line = Bry_.Add(out_line, cell);
				td_history.Add(true);
			}
		}
		bfr.Add(out_line).Add_byte_nl();
		return Bry_split_.Rv__ok;
	}
	private static final    byte[] 
	  Wtxt__tb__bgn     = Bry_.new_a7("{|")
	, Wtxt__tb__end     = Bry_.new_a7("|}")
	, Wtxt__tr          = Bry_.new_a7("|-")
	, Wtxt__caption     = Bry_.new_a7("|+")
	, Wtxt__th2         = Bry_.new_a7("!!")
	, Wtxt__td2         = Bry_.new_a7("||")
	, Wtxt__lnki__bgn   = Bry_.new_a7("[[")

	, Name__table       = Bry_.new_a7("table")
	, Name__tr          = Bry_.new_a7("tr")
	, Name__td          = Bry_.new_a7("td")
	, Name__th          = Bry_.new_a7("th")
	, Name__caption     = Bry_.new_a7("caption")

	, Html__tr__end     = Bry_.new_a7("</tr>")
	, Html__dl__bgn     = Bry_.new_a7("<dl><dd>")
	, Html__dl__end     = Bry_.new_a7("</dd></dl>")
	, Html__tb__empty   = Bry_.new_a7("<table>\n<tr><td></td></tr>\n</table>")
	;
	private static final    int Len__tb__empty = Html__tb__empty.length;
	private static final    byte[][] Cells__empty = new byte[][] {Bry_.Empty};
}
