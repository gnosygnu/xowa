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
package gplx.xowa.parsers.mws.tables; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*; import gplx.xowa.parsers.mws.*;
import gplx.langs.phps.utls.*;
public class Xomw_table_wkr implements gplx.core.brys.Bry_split_wkr {// THREAD.UNSAFE: caching for repeated calls
	private final    Bry_bfr bfr = Bry_bfr_.New(), tmp_bfr = Bry_bfr_.New();
	private final    List_adp 
	  td_history = List_adp_.New()
	, last_tag_history = List_adp_.New()
	, tr_history = List_adp_.New()
	, tr_attributes = List_adp_.New()
	, has_opened_tr = List_adp_.New()
	;
	private int indent_level = 0; // indent level of the table
	private byte[] first_2 = new byte[2];
	public byte[] Do_table_stuff(byte[] src) {
		indent_level = 0;

		// PORTED:member variables
		// $td_history = []; // Is currently a td tag open?
		// $last_tag_history = []; // Save history of last lag activated (td, th or caption)
		// $tr_history = []; // Is currently a tr tag open?
		// $tr_attributes = []; // history of tr attributes
		// $has_opened_tr = []; // Did this table open a <tr> element?

		// PORTED.SPLIT: $lines = StringUtils::explode("\n", $text);
		Bry_split_.Split(src, 0, src.length, Byte_ascii.Nl, Bool_.N, this);

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
		if (	bfr.Len() == Len__special_case
			&&	Bry_.Eq(bfr.Bfr(), 0, Len__special_case, Bry__special_case)) {
			bfr.Clear();
			return Bry_.Empty;
		}
		return bfr.To_bry_and_clear();
	}
	public int Split(byte[] src, int itm_bgn, int itm_end) {
		byte[] out_line = Bry_.Mid(src, itm_bgn, itm_end); // MW: "$outLine"
		byte[] line = Bry_.Trim(out_line);            // MW: "$line"

		int line_temp_len = line.length;
		if (line_temp_len == 0) { // empty line, go to next line
			bfr.Add(out_line).Add_byte_nl();
			return Bry_split_.Rv__ok;
		}

		byte first_char = line[0];
		first_2[0] = line[0];
		if (line_temp_len > 1) first_2[1] = line[1];

		// PORTED: preg_match('/^(:*)\s*\{\|(.*)$/', $line, $matches)
		byte[] tblw_atrs = null;
		boolean tblw_bgn_found = false;
		int colons_end = Bry_find_.Find_fwd_while(src, 0, line_temp_len, Byte_ascii.Colon);
		int tblw_bgn = Bry_find_.Find_fwd_while(line, colons_end, line_temp_len, Byte_ascii.Space);
		int tblw_atrs_bgn = tblw_bgn + 2;
		if (Bry_.Eq(line, tblw_bgn, tblw_atrs_bgn, Bry__tblw_bgn)) {
			tblw_bgn_found = true;
			tblw_atrs = (tblw_atrs_bgn == line_temp_len) ? Bry_.Empty : Bry_.Mid(line, tblw_atrs_bgn, line_temp_len);
		}
		if (tblw_bgn_found) {
			// First check if we are starting a new table
			indent_level = colons_end;

//				atrs = $this->mStripState->unstripBoth(matches[2]);
//				atrs = Sanitizer::fixTagAttributes(attributes, 'table');

			// PORTED: out_line = str_repeat('<dl><dd>', $indent_level) . "<table{atrs}>";
			for (int j = 0; j < indent_level; j++)
				tmp_bfr.Add(Bry__dl_dd);
			out_line = tmp_bfr.Add_str_a7("<table").Add(tblw_atrs).Add_byte(Byte_ascii.Angle_end).To_bry_and_clear();
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
		else if (Bry_.Eq(first_2, Bry__tblw_end)) {
			// We are ending a table
			line = tmp_bfr.Add_str_a7("</table>").Add_mid(line, 2, line.length).To_bry_and_clear();
			byte[] last_tag = Php_ary_.Pop_bry_or_null(last_tag_history);

			if (!Php_ary_.Pop_bool_or_n(has_opened_tr)) {
				line = tmp_bfr.Add_str_a7("<tr><td></td></tr>").Add(line).To_bry_and_clear();
			}

			if (Php_ary_.Pop_bool_or_n(tr_history)) {
				line = tmp_bfr.Add_str_a7("</tr>").Add(line).To_bry_and_clear();
			}

			if (Php_ary_.Pop_bool_or_n(td_history)) {
				line = tmp_bfr.Add_str_a7("</").Add(last_tag).Add_byte(Byte_ascii.Angle_end).Add(line).To_bry_and_clear();
			}
			Php_ary_.Pop_bry_or_null(tr_attributes);
			// PORTED:$outLine = $line . str_repeat( '</dd></dl>', $indent_level );
			tmp_bfr.Add(line);
			for (int j = 0; j < indent_level; j++)
				tmp_bfr.Add(Bry__dl_dd_end);				
			out_line = tmp_bfr.To_bry_and_clear();
		}
		else if (Bry_.Eq(first_2, Bry__tr)) {
			// Now we have a table row
			line = Bry_.Mid(line, 2);        // PORTED: $line = preg_replace('#^\|-+#', '', $line);

			// Whats after the tag is now only attributes
			byte[] atrs = line;
//				atrs = $this->mStripState->unstripBoth(line);
//				atrs = Sanitizer::fixTagAttributes(attributes, 'tr');
			Php_ary_.Pop_bry_or_null(tr_attributes);
			tr_attributes.Add(atrs);

			line = Bry_.Empty;
			byte[] last_tag = Php_ary_.Pop_bry_or_null(last_tag_history);
			Php_ary_.Pop_bool_or_n(has_opened_tr);
			has_opened_tr.Add(true);

			if (Php_ary_.Pop_bool_or_n(tr_history)) {
				line = Bry__elem_end__tr;
			}

			if (Php_ary_.Pop_bool_or_n(td_history)) {
				line = tmp_bfr.Add_str_a7("</").Add(last_tag).Add_byte(Byte_ascii.Gt).Add(line).To_bry_and_clear();
			}

			out_line = line;
			tr_history.Add(false);
			td_history.Add(false);
			last_tag_history.Add(Bry_.Empty);
		}
		else if ( first_char == Byte_ascii.Pipe
				|| first_char == Byte_ascii.Bang
				|| Bry_.Eq(first_2, Bry__th)
			) {
			// This might be cell elements, td, th or captions
			if (Bry_.Eq(first_2, Bry__th)) {
				first_char = Byte_ascii.Pipe;
				line = Bry_.Mid(line, 2);
			} else {
				line = Bry_.Mid(line, 1);
			}

			// Implies both are valid for table headings.
			if (first_char == Byte_ascii.Nl) {
//					$line = StringUtils::replaceMarkup('!!', '||', $line);
			}

			// Split up multiple cells on the same line.
			// FIXME : This can result in improper nesting of tags processed
			// by earlier parser steps.
			byte[][] cells = Bry_split_.Split(line, Bry__td2);

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
						previous = tmp_bfr.Add_str_a7("<tr").Add(tr_after).Add_str_a7(">\n").To_bry_and_clear();
					}
					tr_history.Add(true);
					tr_attributes.Add(Bry_.Empty);
					Php_ary_.Pop_bool_or_n(has_opened_tr);
					has_opened_tr.Add(true);
				}

				byte[] last_tag = Php_ary_.Pop_bry_or_null(last_tag_history);

				if (Php_ary_.Pop_bool_or_n(td_history)) {
					previous = tmp_bfr.Add_str_a7("</").Add(last_tag).Add_str_a7(">\n").Add(previous).To_bry_and_clear();
				}

				if (first_char == Byte_ascii.Pipe) {
					last_tag = Bry__tag__td;
				}
				else if (first_char == Byte_ascii.Bang) {
					last_tag = Bry__tag__th;
				}
				else if (first_char == Byte_ascii.Plus) {
					last_tag = Bry__tag__caption;
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
				if (Bry_find_.Find_fwd(cell_data_0, Bry__lnki) != Bry_find_.Not_found) {
					cell = tmp_bfr.Add(previous).Add_byte(Byte_ascii.Angle_bgn).Add(last_tag).Add_byte(Byte_ascii.Angle_end).Add(cell).To_bry_and_clear();
				}
				else if (cell_data_1 == null) {
					cell = tmp_bfr.Add(previous).Add_byte(Byte_ascii.Angle_bgn).Add(last_tag).Add_byte(Byte_ascii.Angle_end).Add(cell_data_0).To_bry_and_clear();
				}
				else {
//						atrs = $this->mStripState->unstripBoth(cell_data_0);
//						atrs = Sanitizer::fixTagAttributes(attributes, $last_tag);
					cell = tmp_bfr.Add(previous).Add_byte(Byte_ascii.Angle_bgn).Add(last_tag).Add(cell_data_0).Add_byte(Byte_ascii.Angle_end).Add(cell_data_1).To_bry_and_clear();
				}

				out_line = Bry_.Add(out_line, cell);
				td_history.Add(true);
			}
		}
		bfr.Add(out_line).Add_byte_nl();
		return Bry_split_.Rv__ok;
	}
	private static final    byte[] 
	  Bry__tblw_bgn = Bry_.new_a7("{|"), Bry__tblw_end = Bry_.new_a7("|}"), Bry__tr = Bry_.new_a7("|-"), Bry__th = Bry_.new_a7("|+"), Bry__td2 = Bry_.new_a7("||")
	, Bry__lnki = Bry_.new_a7("[[")
	, Bry__special_case = Bry_.new_a7("<table>\n<tr><td></td></tr>\n</table>")
	, Bry__tag__td = Bry_.new_a7("td"), Bry__tag__th = Bry_.new_a7("th"), Bry__tag__caption = Bry_.new_a7("caption")
	, Bry__elem_end__tr = Bry_.new_a7("</tr>")
	, Bry__dl_dd = Bry_.new_a7("<dl><dd>")
	, Bry__dl_dd_end = Bry_.new_a7("</dd></dl>")
	;
	private static final    int Len__special_case = Bry__special_case.length;
}
