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
package gplx.xowa.mediawiki.includes.parsers.tables;
import gplx.types.basics.utls.BryLni;
import gplx.types.custom.brys.BrySplitWkr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.BryFind;
import gplx.types.custom.brys.BrySplit;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.xowa.mediawiki.XophpArray;
import gplx.xowa.mediawiki.includes.XomwSanitizer;
import gplx.xowa.mediawiki.includes.libs.XomwStringUtils;
import gplx.xowa.mediawiki.includes.parsers.XomwParserBfr;
import gplx.xowa.mediawiki.includes.parsers.XomwParserCtx;
import gplx.xowa.mediawiki.includes.parsers.XomwStripState;
public class Xomw_table_wkr implements BrySplitWkr {// THREAD.UNSAFE: caching for repeated calls
	private final BryWtr tmp;
	private BryWtr bfr;
	private final XomwSanitizer sanitizer; private final XomwStripState strip_state;
	private final List_adp
	  td_history       = List_adp_.New() // Is currently a td tag open?
	, last_tag_history = List_adp_.New() // Save history of last lag activated (td, th or caption)
	, tr_history       = List_adp_.New() // Is currently a tr tag open?
	, tr_attributes    = List_adp_.New() // history of tr attributes
	, has_opened_tr    = List_adp_.New() // Did this table open a <tr> element?
	;
	private int indent_level = 0; // indent level of the table
	private byte[] first_2 = new byte[2];
	public Xomw_table_wkr(BryWtr tmp, XomwSanitizer sanitizer, XomwStripState stripState) {
		this.tmp = tmp;
		this.sanitizer = sanitizer;
		this.strip_state = stripState;
	}
	public void doTableStuff(XomwParserCtx pctx, XomwParserBfr pbfr) {
		BryWtr src_bfr = pbfr.Src();
		byte[] src = src_bfr.Bry();
		int src_bgn = 0;
		int src_end = src_bfr.Len();
		this.bfr = pbfr.Trg();
		pbfr.Switch();

		indent_level = 0;
		
		BrySplit.Split(src, src_bgn, src_end, AsciiByte.Nl, BoolUtl.N, this); // PORTED.SPLIT: $lines = StringUtils::explode("\n", $text);

		// Closing open td, tr && table
		while (td_history.Len() > 0) {
			if (XophpArray.popBoolOrN(td_history)) {
				bfr.AddStrA7("</td>\n");
			}
			if (XophpArray.popBoolOrN(tr_history)) {
				bfr.AddStrA7("</tr>\n");
			}
			if (!XophpArray.popBoolOrN(has_opened_tr)) {
				bfr.AddStrA7("<tr><td></td></tr>\n");
			}
			bfr.AddStrA7("</table>\n");
		}

		// Remove trailing line-ending (b/c)
		if (bfr.GetAtLastOrNilIfEmpty() == AsciiByte.Nl) {
			bfr.DelBy1();
		}

		// special case: don't return empty table
		if (	bfr.Len() == Len__tb__empty
			&&	BryLni.Eq(bfr.Bry(), 0, Len__tb__empty, Html__tb__empty)) {
			bfr.Clear();
			return;
		}
	}
	public int Split(byte[] src, int itm_bgn, int itm_end) {
		byte[] out_line = BryLni.Mid(src, itm_bgn, itm_end); // MW: "$outLine"
		byte[] line = BryUtl.Trim(out_line);                 // MW: "$line"

		int line_len = line.length;
		if (line_len == 0) { // empty line, go to next line
			bfr.Add(out_line).AddByteNl();
			return BrySplit.Rv__ok;
		}

		byte first_char = line[0];
		first_2[0] = line[0];
		first_2[1] = line_len == 1 ? AsciiByte.Null : line[1];

		// PORTED: preg_match('/^(:*)\s*\{\|(.*)$/', $line, $matches)
		byte[] tblw_atrs = null;
		boolean tblw_bgn_found = false;
		int colons_end = BryFind.FindFwdWhile(src, 0, line_len, AsciiByte.Colon);
		int tblw_bgn = BryFind.FindFwdWhile(line, colons_end, line_len, AsciiByte.Space);
		int tblw_atrs_bgn = tblw_bgn + 2;
		if (BryLni.Eq(line, tblw_bgn, tblw_atrs_bgn, Wtxt__tb__bgn)) {
			tblw_bgn_found = true;
			tblw_atrs = (tblw_atrs_bgn == line_len) ? BryUtl.Empty : BryLni.Mid(line, tblw_atrs_bgn, line_len);
		}
		if (tblw_bgn_found) {
			// First check if we are starting a new table
			indent_level = colons_end;

			tblw_atrs = strip_state.unstripBoth(tblw_atrs);

			// PORTED: out_line = str_repeat('<dl><dd>', $indent_level) . "<table{atrs}>";
			for (int j = 0; j < indent_level; j++)
				tmp.Add(Html__dl__bgn);
			tmp.AddStrA7("<table");
			sanitizer.fixTagAttributes(tmp, Name__table, tblw_atrs);
			tmp.AddByte(AsciiByte.AngleEnd);
			out_line = tmp.ToBryAndClear();
			td_history.Add(false);
			last_tag_history.Add(BryUtl.Empty);
			tr_history.Add(false);
			tr_attributes.Add(BryUtl.Empty);
			has_opened_tr.Add(false);
		}
		else if (td_history.Len() == 0) {
			// Don't do any of the following
			bfr.Add(out_line).AddByteNl();
			return BrySplit.Rv__ok;
		}
		else if (BryLni.Eq(first_2, Wtxt__tb__end)) {
			// We are ending a table
			line = tmp.AddStrA7("</table>").AddMid(line, 2, line.length).ToBryAndClear();
			byte[] last_tag = XophpArray.popBryOrNull(last_tag_history);

			if (!XophpArray.popBoolOrN(has_opened_tr)) {
				line = tmp.AddStrA7("<tr><td></td></tr>").Add(line).ToBryAndClear();
			}

			if (XophpArray.popBoolOrN(tr_history)) {
				line = tmp.AddStrA7("</tr>").Add(line).ToBryAndClear();
			}

			if (XophpArray.popBoolOrN(td_history)) {
				line = tmp.AddStrA7("</").Add(last_tag).AddByte(AsciiByte.AngleEnd).Add(line).ToBryAndClear();
			}
			XophpArray.popBryOrNull(tr_attributes);
			// PORTED:$outLine = $line . str_repeat( '</dd></dl>', $indent_level );
			tmp.Add(line);
			for (int j = 0; j < indent_level; j++)
				tmp.Add(Html__dl__end);				
			out_line = tmp.ToBryAndClear();
		}
		else if (BryLni.Eq(first_2, Wtxt__tr)) {
			// Now we have a table row
			line = BryLni.Mid(line, 2);        // PORTED: $line = preg_replace('#^\|-+#', '', $line);

			// Whats after the tag is now only attributes
			byte[] atrs = strip_state.unstripBoth(line);
			sanitizer.fixTagAttributes(tmp, Name__tr, atrs);
			atrs = tmp.ToBryAndClear();

			XophpArray.popBryOrNull(tr_attributes);
			tr_attributes.Add(atrs);

			line = BryUtl.Empty;
			byte[] last_tag = XophpArray.popBryOrNull(last_tag_history);
			XophpArray.popBoolOrN(has_opened_tr);
			has_opened_tr.Add(true);

			if (XophpArray.popBoolOrN(tr_history)) {
				line = Html__tr__end;
			}

			if (XophpArray.popBoolOrN(td_history)) {
				line = tmp.AddStrA7("</").Add(last_tag).AddByte(AsciiByte.Gt).Add(line).ToBryAndClear();
			}

			out_line = line;
			tr_history.Add(false);
			td_history.Add(false);
			last_tag_history.Add(BryUtl.Empty);
		}
		else if ( first_char == AsciiByte.Pipe
				|| first_char == AsciiByte.Bang
				|| BryLni.Eq(first_2, Wtxt__caption)
			) {
			// This might be cell elements, td, th or captions
			if (BryLni.Eq(first_2, Wtxt__caption)) {
				first_char = AsciiByte.Plus;
				line = BryLni.Mid(line, 2);
			} else {
				line = BryLni.Mid(line, 1);
			}

			// Implies both are valid for table headings.
			if (first_char == AsciiByte.Bang) {
				XomwStringUtils.replaceMarkup(line, 0, line.length, Wtxt__th2, Wtxt__td2); // $line = StringUtils::replaceMarkup('!!', '||', $line);
			}

			// Split up multiple cells on the same line.
			// FIXME : This can result in improper nesting of tags processed
			// by earlier parser steps.
			byte[][] cells = BrySplit.Split(line, Wtxt__td2);
			if (cells.length == 0) cells = Cells__empty;	// handle "\n|\n" which should still generate "<tr><td></td></tr>", not ""; see TEST

			out_line = BryUtl.Empty;

			byte[] previous = null;
			// Loop through each table cell
			int cells_len = cells.length;
			for (int j = 0; j < cells_len; j++) {
				byte[] cell = cells[j];
				previous = BryUtl.Empty;
				if (first_char != AsciiByte.Plus) {
					byte[] tr_after = XophpArray.popBryOrNull(tr_attributes);
					if (!XophpArray.popBoolOrN(tr_history)) {
						previous = tmp.AddStrA7("<tr").Add(tr_after).AddStrA7(">\n").ToBryAndClear();
					}
					tr_history.Add(true);
					tr_attributes.Add(BryUtl.Empty);
					XophpArray.popBoolOrN(has_opened_tr);
					has_opened_tr.Add(true);
				}

				byte[] last_tag = XophpArray.popBryOrNull(last_tag_history);

				if (XophpArray.popBoolOrN(td_history)) {
					previous = tmp.AddStrA7("</").Add(last_tag).AddStrA7(">\n").Add(previous).ToBryAndClear();
				}

				if (first_char == AsciiByte.Pipe) {
					last_tag = Name__td;
				}
				else if (first_char == AsciiByte.Bang) {
					last_tag = Name__th;
				}
				else if (first_char == AsciiByte.Plus) {
					last_tag = Name__caption;
				}
				else {
					last_tag = BryUtl.Empty;
				}

				last_tag_history.Add(last_tag);

				// A cell could contain both parameters and data
				byte[][] cell_data = BrySplit.SplitWithmax(cell, AsciiByte.Pipe, 2);

				// Bug 553: Note that a '|' inside an invalid link should not
				// be mistaken as delimiting cell parameters
				byte[] cell_data_0 = cell_data[0];
				byte[] cell_data_1 = cell_data[1];
				if (BryFind.FindFwd(cell_data_0, Wtxt__lnki__bgn) != BryFind.NotFound) {
					cell = tmp.Add(previous).AddByte(AsciiByte.AngleBgn).Add(last_tag).AddByte(AsciiByte.AngleEnd).Add(cell).ToBryAndClear();
				}
				else if (cell_data_1 == null) {
					cell = tmp.Add(previous).AddByte(AsciiByte.AngleBgn).Add(last_tag).AddByte(AsciiByte.AngleEnd).Add(cell_data_0).ToBryAndClear();
				}
				else {
					byte[] atrs = strip_state.unstripBoth(cell_data_0);
					tmp.Add(previous).AddByte(AsciiByte.AngleBgn).Add(last_tag);
					sanitizer.fixTagAttributes(tmp, last_tag, atrs);
					tmp.AddByte(AsciiByte.AngleEnd).Add(cell_data_1);
					cell = tmp.ToBryAndClear();
				}

				out_line = BryUtl.Add(out_line, cell);
				td_history.Add(true);
			}
		}
		bfr.Add(out_line).AddByteNl();
		return BrySplit.Rv__ok;
	}
//		public function doTableStuff($text) {
//
//			$lines = StringUtils::explode("\n", $text);
//			$out = '';
//			$td_history = []; # Is currently a td tag open?
//			$last_tag_history = []; # Save history of last lag activated (td, th or caption)
//			$tr_history = []; # Is currently a tr tag open?
//			$tr_attributes = []; # history of tr attributes
//			$has_opened_tr = []; # Did this table open a <tr> element?
//			$indent_level = 0; # indent level of the table
//
//			foreach ($lines as $outLine) {
//				$line = trim($outLine);
//
//				if ($line === '') { # empty line, go to next line
//					$out .= $outLine . "\n";
//					continue;
//				}
//
//				$first_character = $line[0];
//				$first_two = substr($line, 0, 2);
//				$matches = [];
//
//				if (preg_match('/^(:*)\s*\{\|(.*)$/', $line, $matches)) {
//					# First check if we are starting a new table
//					$indent_level = strlen($matches[1]);
//
//					$attributes = this.mStripState->unstripBoth($matches[2]);
//					$attributes = Sanitizer::fixTagAttributes($attributes, 'table');
//
//					$outLine = str_repeat('<dl><dd>', $indent_level) . "<table{$attributes}>";
//					array_push($td_history, false);
//					array_push($last_tag_history, '');
//					array_push($tr_history, false);
//					array_push($tr_attributes, '');
//					array_push($has_opened_tr, false);
//				} elseif (count($td_history) == 0) {
//					# Don't do any of the following
//					$out .= $outLine . "\n";
//					continue;
//				} elseif ($first_two === '|}') {
//					# We are ending a table
//					$line = '</table>' . substr($line, 2);
//					$last_tag = array_pop($last_tag_history);
//
//					if (!array_pop($has_opened_tr)) {
//						$line = "<tr><td></td></tr>{$line}";
//					}
//
//					if (array_pop($tr_history)) {
//						$line = "</tr>{$line}";
//					}
//
//					if (array_pop($td_history)) {
//						$line = "</{$last_tag}>{$line}";
//					}
//					array_pop($tr_attributes);
//					$outLine = $line . str_repeat('</dd></dl>', $indent_level);
//				} elseif ($first_two === '|-') {
//					# Now we have a table row
//					$line = preg_replace('#^\|-+#', '', $line);
//
//					# Whats after the tag is now only attributes
//					$attributes = this.mStripState->unstripBoth($line);
//					$attributes = Sanitizer::fixTagAttributes($attributes, 'tr');
//					array_pop($tr_attributes);
//					array_push($tr_attributes, $attributes);
//
//					$line = '';
//					$last_tag = array_pop($last_tag_history);
//					array_pop($has_opened_tr);
//					array_push($has_opened_tr, true);
//
//					if (array_pop($tr_history)) {
//						$line = '</tr>';
//					}
//
//					if (array_pop($td_history)) {
//						$line = "</{$last_tag}>{$line}";
//					}
//
//					$outLine = $line;
//					array_push($tr_history, false);
//					array_push($td_history, false);
//					array_push($last_tag_history, '');
//				} elseif ($first_character === '|'
//					|| $first_character === '!'
//					|| $first_two === '|+'
//				) {
//					# This might be cell elements, td, th or captions
//					if ($first_two === '|+') {
//						$first_character = '+';
//						$line = substr($line, 2);
//					} else {
//						$line = substr($line, 1);
//					}
//
//					// Implies both are valid for table headings.
//					if ($first_character === '!') {
//						$line = StringUtils::replaceMarkup('!!', '||', $line);
//					}
//
//					# Split up multiple cells on the same line.
//					# FIXME : This can result in improper nesting of tags processed
//					# by earlier parser steps.
//					$cells = explode('||', $line);
//
//					$outLine = '';
//
//					# Loop through each table cell
//					foreach ($cells as $cell) {
//						$previous = '';
//						if ($first_character !== '+') {
//							$tr_after = array_pop($tr_attributes);
//							if (!array_pop($tr_history)) {
//								$previous = "<tr{$tr_after}>\n";
//							}
//							array_push($tr_history, true);
//							array_push($tr_attributes, '');
//							array_pop($has_opened_tr);
//							array_push($has_opened_tr, true);
//						}
//
//						$last_tag = array_pop($last_tag_history);
//
//						if (array_pop($td_history)) {
//							$previous = "</{$last_tag}>\n{$previous}";
//						}
//
//						if ($first_character === '|') {
//							$last_tag = 'td';
//						} elseif ($first_character === '!') {
//							$last_tag = 'th';
//						} elseif ($first_character === '+') {
//							$last_tag = 'caption';
//						} else {
//							$last_tag = '';
//						}
//
//						array_push($last_tag_history, $last_tag);
//
//						# A cell could contain both parameters and data
//						$cell_data = explode('|', $cell, 2);
//
//						# T2553: Note that a '|' inside an invalid link should not
//						# be mistaken as delimiting cell parameters
//						# Bug T153140: Neither should language converter markup.
//						if (preg_match('/\[\[|-\{/', $cell_data[0]) === 1) {
//							$cell = "{$previous}<{$last_tag}>{$cell}";
//						} elseif (count($cell_data) == 1) {
//							$cell = "{$previous}<{$last_tag}>{$cell_data[0]}";
//						} else {
//							$attributes = this.mStripState->unstripBoth($cell_data[0]);
//							$attributes = Sanitizer::fixTagAttributes($attributes, $last_tag);
//							$cell = "{$previous}<{$last_tag}{$attributes}>{$cell_data[1]}";
//						}
//
//						$outLine .= $cell;
//						array_push($td_history, true);
//					}
//				}
//				$out .= $outLine . "\n";
//			}
//
//			# Closing open td, tr && table
//			while (count($td_history) > 0) {
//				if (array_pop($td_history)) {
//					$out .= "</td>\n";
//				}
//				if (array_pop($tr_history)) {
//					$out .= "</tr>\n";
//				}
//				if (!array_pop($has_opened_tr)) {
//					$out .= "<tr><td></td></tr>\n";
//				}
//
//				$out .= "</table>\n";
//			}
//
//			# Remove trailing line-ending (b/c)
//			if (substr($out, -1) === "\n") {
//				$out = substr($out, 0, -1);
//			}
//
//			# special case: don't return empty table
//			if ($out === "<table>\n<tr><td></td></tr>\n</table>") {
//				$out = '';
//			}
//
//			return $out;
//		}
	private static final byte[]
	  Wtxt__tb__bgn     = BryUtl.NewA7("{|")
	, Wtxt__tb__end     = BryUtl.NewA7("|}")
	, Wtxt__tr          = BryUtl.NewA7("|-")
	, Wtxt__caption     = BryUtl.NewA7("|+")
	, Wtxt__th2         = BryUtl.NewA7("!!")
	, Wtxt__td2         = BryUtl.NewA7("||")
	, Wtxt__lnki__bgn   = BryUtl.NewA7("[[")

	, Name__table       = BryUtl.NewA7("table")
	, Name__tr          = BryUtl.NewA7("tr")
	, Name__td          = BryUtl.NewA7("td")
	, Name__th          = BryUtl.NewA7("th")
	, Name__caption     = BryUtl.NewA7("caption")

	, Html__tr__end     = BryUtl.NewA7("</tr>")
	, Html__dl__bgn     = BryUtl.NewA7("<dl><dd>")
	, Html__dl__end     = BryUtl.NewA7("</dd></dl>")
	, Html__tb__empty   = BryUtl.NewA7("<table>\n<tr><td></td></tr>\n</table>")
	;
	private static final int Len__tb__empty = Html__tb__empty.length;
	private static final byte[][] Cells__empty = new byte[][] {BryUtl.Empty};
}
