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
package gplx.xowa.parsers.mws.tblws; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*; import gplx.xowa.parsers.mws.*;
public class Xomw_tblw_wkr {
	private final    Bry_bfr bfr = Bry_bfr_.New(), tmp_bfr = Bry_bfr_.New();
	private final    List_adp 
	  td_history = List_adp_.New()
	, last_tag_history = List_adp_.New()
	, tr_history = List_adp_.New()
	, tr_attributes = List_adp_.New()
	, has_opened_tr = List_adp_.New()
	;
	private static final    byte[] 
	  Bry__tblw_end = Bry_.new_a7("|}"), Bry__tr = Bry_.new_a7("|-"), Bry__th = Bry_.new_a7("|+")
	, Bry__special_case = Bry_.new_a7("<table>\n<tr><td></td></tr>\n</table>")
	, Bry__tag__td = Bry_.new_a7("td"), Bry__tag__th = Bry_.new_a7("th"), Bry__tag__caption = Bry_.new_a7("caption")
	, Bry__elem_end__tr = Bry_.new_a7("</tr>")
	;
	private static final    int Len__special_case = Bry__special_case.length;
	public byte[] Do_table_stuff(byte[] src) {
		int src_len = src.length;
		byte[][] lines = Bry_split_.Split(src, 0, src_len, Byte_ascii.Nl_bry); // PORTED: $lines = StringUtils::explode("\n", $text);
		int lines_len = lines.length;

		// PORTED:member variables
		// $td_history = []; // Is currently a td tag open?
		// $last_tag_history = []; // Save history of last lag activated (td, th or caption)
		// $tr_history = []; // Is currently a tr tag open?
		// $tr_attributes = []; // history of tr attributes
		// $has_opened_tr = []; // Did this table open a <tr> element?

		int indent_level = 0; // indent level of the table
		byte[] chars_2 = new byte[2];
		for (int i = 0; i < lines_len; i++) {
			byte[] line_orig = lines[i];
			byte[] line = Bry_.Trim(line_orig);

			int line_len = line.length;
			if (line_len == 0) { // empty line, go to next line
				bfr.Add(line_orig).Add_byte_nl();
				continue;
			}

			byte char_0 = line[0];
			chars_2[0] = line[0];
			if (line_len > 1) chars_2[1] = line[1];

			boolean is_indented_table = false;
			// ":*" , "\s*" , "{|" , ".*"
			if (is_indented_table) {
//				if (preg_match('/^(:*)\s*\{\|(.*)$/', $line, $matches)) {
//					// First check if we are starting a new table
//					$indent_level = strlen(matches[1]);
//
//					$attributes = $this->mStripState->unstripBoth(matches[2]);
//					$attributes = Sanitizer::fixTagAttributes(attributes, 'table');
//
//					line_orig = str_repeat('<dl><dd>', $indent_level) . "<table{$attributes}>";
				td_history.Add(false);
				last_tag_history.Add(Bry_.Empty);
				tr_history.Add(false);
				tr_attributes.Add(Bry_.Empty);
				has_opened_tr.Add(false);
			}
			else if (td_history.Len() == 0) {
				// Don't do any of the following
				bfr.Add(line_orig).Add_byte_nl();
				continue;
			}
			else if (Bry_.Eq(chars_2, Bry__tblw_end)) {
				// We are ending a table
				line = tmp_bfr.Add_str_a7("</table>").Add_mid(line, 2, line.length).To_bry_and_clear();
				byte[] last_tag = (byte[])List_adp_.Pop(last_tag_history);

				if (has_opened_tr.Len() == 0) {
					line = tmp_bfr.Add_str_a7("<tr><td></td></tr>").Add(line).To_bry_and_clear();
				}

				if (tr_history.Len() > 0) {
					List_adp_.Pop(tr_history);
					line = tmp_bfr.Add_str_a7("</tr>").Add(line).To_bry_and_clear();
				}

				if (td_history.Len() > 0) {
					List_adp_.Pop(td_history);
					line = tmp_bfr.Add_str_a7("</").Add(last_tag).Add_str_a7(">").Add(line).To_bry_and_clear();
				}
				List_adp_.Pop(tr_attributes);
				line_orig = tmp_bfr.Add(line).Add(Bry_.Repeat_bry(Bry_.new_a7("</dd></dl>"), indent_level)).To_bry_and_clear();
			}
			else if (Bry_.Eq(chars_2, Bry__tr)) {
//					// Now we have a table row
//					$line = preg_replace('#^\|-+#', '', $line);
//
//					// Whats after the tag is now only attributes
//					$attributes = $this->mStripState->unstripBoth(line);
//					$attributes = Sanitizer::fixTagAttributes(attributes, 'tr');
				List_adp_.Pop(tr_attributes);
//					array_push(tr_attributes, $attributes);

				line = Bry_.Empty;
				byte[] last_tag = (byte[])List_adp_.Pop(last_tag_history);
				List_adp_.Pop(has_opened_tr);
				has_opened_tr.Add(true);

				if ((boolean)List_adp_.Pop(tr_history)) {
					line = Bry__elem_end__tr;
				}

				if ((boolean)List_adp_.Pop(td_history)) {
					line = tmp_bfr.Add_str_a7("</").Add(last_tag).Add_byte(Byte_ascii.Gt).Add(line).To_bry_and_clear();
				}

				line_orig = line;
				tr_history.Add(false);
				td_history.Add(false);
				last_tag_history.Add(Bry_.Empty);
			}
			else if ( char_0 == Byte_ascii.Pipe
					|| char_0 == Byte_ascii.Bang
					|| Bry_.Eq(chars_2, Bry__th)
				) {
				// This might be cell elements, td, th or captions
				if (Bry_.Eq(chars_2, Bry__th)) {
					char_0 = Byte_ascii.Pipe;
					line = Bry_.Mid(line, 2);
				} else {
					line = Bry_.Mid(line, 1);
				}

				// Implies both are valid for table headings.
				if (char_0 == Byte_ascii.Nl) {
					// $line = StringUtils::replaceMarkup('!!', '||', $line);
				}

				// Split up multiple cells on the same line.
				// FIXME : This can result in improper nesting of tags processed
				// by earlier parser steps.
//					$cells = explode('||', $line);

				line_orig = Bry_.Empty;

				byte[] previous = null;
				// Loop through each table cell
//					foreach (cells as $cell) {
					previous = Bry_.Empty;
					if (char_0 != Byte_ascii.Plus) {
						byte[] tr_after = (byte[])List_adp_.Pop(tr_attributes);
						if (!(boolean)List_adp_.Pop(tr_history)) {
							previous = tmp_bfr.Add_str_a7("<tr").Add(tr_after).Add_str_a7(">\n").To_bry_and_clear();
						}
						tr_history.Add(true);
						tr_attributes.Add(Bry_.Empty);
						List_adp_.Pop(has_opened_tr);
						has_opened_tr.Add(true);
					}

					byte[] last_tag = (byte[])List_adp_.Pop(last_tag_history);

					if ((boolean)List_adp_.Pop(td_history)) {
						previous = tmp_bfr.Add_str_a7("</").Add(last_tag).Add_str_a7(">\n").Add(previous).To_bry_and_clear();
					}

					if (char_0 == Byte_ascii.Pipe) {
						last_tag = Bry__tag__td;
					}
					else if (char_0 == Byte_ascii.Bang) {
						last_tag = Bry__tag__th;
					}
					else if (char_0 == Byte_ascii.Plus) {
						last_tag = Bry__tag__caption;
					}
					else {
						last_tag = Bry_.Empty;
					}

					last_tag_history.Add(last_tag);
//
//						// A cell could contain both parameters and data
//						$cell_data = explode('|', $cell, 2);
//
//						// Bug 553: Note that a '|' inside an invalid link should not
//						// be mistaken as delimiting cell parameters
//						if (strpos(cell_data[0], '[[') !== false) {
//							$cell = "{$previous}<{$last_tag}>{$cell}";
//						} else if (count(cell_data) == 1) {
//							$cell = "{$previous}<{$last_tag}>{$cell_data[0]}";
//						} else {
//							$attributes = $this->mStripState->unstripBoth(cell_data[0]);
//							$attributes = Sanitizer::fixTagAttributes(attributes, $last_tag);
//							$cell = "{$previous}<{$last_tag}{$attributes}>{$cell_data[1]}";
//						}
//
//						line_orig = Bry_.Add(line_orig, $cell);
					td_history.Add(true);
//					}
			}
			bfr.Add(line_orig).Add_byte_nl();
		}

		// Closing open td, tr && table
		while (td_history.Len() > 0) {
			if (tr_history.Len() > 0) {
				List_adp_.Del_at_last(tr_history);
				bfr.Add_str_a7("</td>\n");
			}
			if (has_opened_tr.Len() == 0) {
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
			return Bry_.Empty;
		}
		return bfr.To_bry_and_clear();
	}
}
