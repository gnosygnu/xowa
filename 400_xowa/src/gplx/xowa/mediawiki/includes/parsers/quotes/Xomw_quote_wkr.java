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
package gplx.xowa.mediawiki.includes.parsers.quotes; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*; import gplx.xowa.mediawiki.includes.parsers.*;
import gplx.xowa.parsers.htmls.*;
import gplx.core.primitives.*;
public class Xomw_quote_wkr {// THREAD.UNSAFE: caching for repeated calls
	private Bry_bfr tmp;
	private final    Int_list apos_pos_ary = new Int_list(32);
	public Xomw_quote_wkr(Bry_bfr tmp) {
		this.tmp = tmp;
	}
	public void doAllQuotes(XomwParserCtx pctx, XomwParserBfr pbfr) {
		Bry_bfr src_bfr = pbfr.Src();
		byte[] src = src_bfr.Bfr();
		int src_bgn = 0;
		int src_end = src_bfr.Len();
		Bry_bfr bfr = pbfr.Trg();
		pbfr.Switch();

		int cur = src_bgn;
		int line_bgn = cur;
		while (true) {
			int line_end = Bry_find_.Find_fwd(src, Byte_ascii.Nl, line_bgn, src_end);
			if (line_end == Bry_find_.Not_found) {
				line_end = src_end;
			}
			Do_quotes(bfr, Bool_.Y, src, line_bgn, line_end);
			if (line_end == src_end)
				break;
			else
				line_bgn = line_end + 1;	// 1=\n.length
		}
		// Bry_split_.Split(src, src_bgn, src_end, Byte_ascii.Nl, Bool_.N, this); // PORTED.SPLIT: $lines = StringUtils::explode( "\n", $text );
		if (bfr.Match_end_byt(Byte_ascii.Nl))
			bfr.Del_by_1(); // REF.MW: $outtext = substr( $outtext, 0, -1 );
		apos_pos_ary.Clear();
	}
	public byte[] Do_quotes(Bry_bfr tmp, byte[] src) {
		boolean found = Do_quotes(tmp, Bool_.N, src, 0, src.length);
		return found ? tmp.To_bry_and_clear() : src;
	}
	private boolean Do_quotes(Bry_bfr bfr, boolean all_quotes_mode, byte[] src, int line_bgn, int line_end) {
		byte[][] arr = XophpPreg.split(apos_pos_ary, src, line_bgn, line_end, Wtxt__apos, Bool_.Y);	// PORTED.REGX: arr = preg_split("/(''+)/", text, -1, PREG_SPLIT_DELIM_CAPTURE);
		if (arr == null) {
			if (all_quotes_mode) {
				bfr.Add_mid(src, line_bgn, line_end).Add_byte_nl();
			}
			return false;
		}
		int arr_len = arr.length;

		// First, do some preliminary work. This may shift some apostrophes from
		// being mark-up to being text. It also counts the number of occurrences
		// of bold and italics mark-ups.
		int num_bold = 0;
		int num_italics = 0;
		for (int i = 1; i < arr_len; i += 2) {
			int apos_len = arr[i].length;
			// If there are ever four apostrophes, assume the first is supposed to
			// be text, and the remaining three constitute mark-up for bold text.
			// (bug 13227: ''''foo'''' turns into ' ''' foo ' ''')
			if (apos_len == 4) {
				arr[i - 1] = Bry_.Add(arr[i - 1], Byte_ascii.Apos_bry);
				arr[i] = Bry_.new_a7("'''");
				apos_len = 3;
			}
			else if (apos_len > 5) {
				// If there are more than 5 apostrophes in a row, assume they're all
				// text except for the last 5.
				// (bug 13227: ''''''foo'''''' turns into ' ''''' foo ' ''''')
				arr[i - 1] = Bry_.Add(arr[i - 1], Bry_.Repeat(Byte_ascii.Apos, apos_len - 5));
				arr[i] = Bry_.new_a7("'''''");
				apos_len = 5;
			}
			// Count the number of occurrences of bold and italics mark-ups.
			if (apos_len == 2) {
				num_italics++;
			}
			else if (apos_len == 3) {
				num_bold++;
			}
			else if (apos_len == 5) {
				num_italics++;
				num_bold++;
			}
		}

		// If there is an odd number of both bold and italics, it is likely
		// that one of the bold ones was meant to be an apostrophe followed
		// by italics. Which one we cannot know for certain, but it is more
		// likely to be one that has a single-letter word before it.
		// NOTE: this code primarily handles italicized possessives; EX: The ''[[Main Page]]'''s talk page.
		if ((num_bold % 2 == 1) && (num_italics % 2 == 1)) {
			int prv_ends_w_word_1char = -1;
			int prv_ends_w_word_nchar = -1;
			int prv_ends_w_space = -1;
			for (int i = 1; i < arr_len; i += 2) {
				if (arr[i].length == 3) {
					byte[] prv = arr[i - 1];
					byte prv__last_char = XophpString.substr_byte(prv, -1);
					byte prv__last_minus_1_char = XophpString.substr_byte(prv, -2, 1);
					if (prv__last_char == Byte_ascii.Space) {              // NOTE: prv ends in space; EX: "''prv '''"
						if (prv_ends_w_space == -1) {
							prv_ends_w_space = i;
						}
					}
					else if (prv__last_minus_1_char == Byte_ascii.Space) { // NOTE: prv ends in 1-char word; EX: "''prv a'''"
						prv_ends_w_word_1char = i;
						// if $firstsingleletterword is set, we don't
						// look at the other options, so we can bail early.
						break;
					}
					else {
						if (prv_ends_w_word_nchar == -1) {
							prv_ends_w_word_nchar = i;
						}
					}
				}
			}

			// If there is a single-letter word, use it!
			if (prv_ends_w_word_1char > -1) {
				arr[prv_ends_w_word_1char] = Wtxt__apos;
				arr[prv_ends_w_word_1char - 1] = Bry_.Add(arr[prv_ends_w_word_1char - 1], Byte_ascii.Apos);
			}
			else if (prv_ends_w_word_nchar > -1) {
				// If not, but there's a multi-letter word, use that one.
				arr[prv_ends_w_word_nchar] = Wtxt__apos;
				arr[prv_ends_w_word_nchar - 1] = Bry_.Add(arr[prv_ends_w_word_nchar - 1], Byte_ascii.Apos);
			}
			else if (prv_ends_w_space > -1) {
				// ... otherwise use the first one that has neither.
				// (notice that it is possible for all three to be -1 if, for example,
				// there is only one pentuple-apostrophe in the line)
				arr[prv_ends_w_space] = Wtxt__apos;
				arr[prv_ends_w_space - 1] = Bry_.Add(arr[prv_ends_w_space - 1], Byte_ascii.Apos);
			}
		}

		// Now let's actually convert our apostrophic mush to HTML!
		int state = State__empty;
		for (int j = 0; j < arr_len; j++) {
			if ((j % 2) == 0) {
				if (state == State__both) {
					tmp.Add(arr[j]);
				}
				else {
					bfr.Add(arr[j]);
				}
			}
			else {
				int apos_len = arr[j].length;
				if (apos_len == 2) {
					if (state == State__i) {
						bfr.Add_str_a7("</i>");
						state = State__empty;
					}
					else if (state == State__bi) {
						bfr.Add_str_a7("</i>");
						state = State__b;
					}
					else if (state == State__ib) {
						bfr.Add_str_a7("</b></i><b>");
						state = State__b;
					}
					else if (state == State__both) {
						bfr.Add_str_a7("<b><i>").Add_bfr_and_preserve(tmp).Add_str_a7("</i>");
						state = State__b;
					}
					else { // state can be 'b' or ''
						bfr.Add_str_a7("<i>");
						state = state == State__b ? State__bi : State__i;
					}
				}
				else if (apos_len == 3) {
					if (state == State__b) {
						bfr.Add_str_a7("</b>");
						state = State__empty;
					}
					else if (state == State__bi) {
						bfr.Add_str_a7("</i></b><i>");
						state = State__i;
					}
					else if (state == State__ib) {
						bfr.Add_str_a7("</b>");
						state = State__i;
					}
					else if (state == State__both) {
						bfr.Add_str_a7("<i><b>").Add_bfr_and_preserve(tmp).Add_str_a7("</b>");
						state = State__i;
					}
					else { // state can be 'i' or ''
						bfr.Add_str_a7("<b>");
						state = state == State__i ? State__ib : State__b;
					}
				}
				else if (apos_len == 5) {
					if (state == State__b) {
						bfr.Add_str_a7("</b><i>");
						state = State__i;
					}
					else if (state == State__i) {
						bfr.Add_str_a7("</i><b>");
						state = State__b;
					}
					else if (state == State__bi) {
						bfr.Add_str_a7("</i></b>");
						state = State__empty;
					}
					else if (state == State__ib) {
						bfr.Add_str_a7("</b></i>");
						state = State__empty;
					}
					else if (state == State__both) {
						bfr.Add_str_a7("<i><b>").Add_bfr_and_preserve(tmp).Add_str_a7("</b></i>");
						state = State__empty;
					}
					else { // (state == '')
						tmp.Clear();
						state = State__both;
					}
				}
			}
		}
		// Now close all remaining tags.  Notice that the order is important.
		if (state == State__b || state == State__ib) {
			bfr.Add_str_a7("</b>");
		}
		if (state == State__i || state == State__bi || state == State__ib) {
			bfr.Add_str_a7("</i>");
		}
		if (state == State__bi) {
			bfr.Add_str_a7("</b>");
		}
		// There might be lonely ''''', so make sure we have a buffer
		if (state == State__both && tmp.Len_gt_0()) {
			bfr.Add_str_a7("<b><i>").Add_bfr_and_clear(tmp).Add_str_a7("</i></b>");
		}
		bfr.Add_byte_nl();
		return true;
	}
	private static final int 
	  State__empty = 0
	, State__b = 1
	, State__i = 2
	, State__bi = 3
	, State__ib = 4
	, State__both = 5
	;
	private static final    byte[] Wtxt__apos = Bry_.new_a7("''");
//		/**
//		* Replace single quotes with HTML markup
//		* @private
//		*
//		* @param String $text
//		*
//		* @return String The altered text
//		*/
//		public function doAllQuotes($text) {
//			$outtext = '';
//			$lines = StringUtils::explode("\n", $text);
//			foreach ($lines as $line) {
//				$outtext .= this.doQuotes($line) . "\n";
//			}
//			$outtext = substr($outtext, 0, -1);
//			return $outtext;
//		}
//
//		/**
//		* Helper function for doAllQuotes()
//		*
//		* @param String $text
//		*
//		* @return String
//		*/
//		public function doQuotes($text) {
//			$arr = preg_split("/(''+)/", $text, -1, PREG_SPLIT_DELIM_CAPTURE);
//			$countarr = count($arr);
//			if ($countarr == 1) {
//				return $text;
//			}
//
//			// First, do some preliminary work. This may shift some apostrophes from
//			// being mark-up to being text. It also counts the number of occurrences
//			// of bold and italics mark-ups.
//			$numbold = 0;
//			$numitalics = 0;
//			for ($i = 1; $i < $countarr; $i += 2) {
//				$thislen = strlen($arr[$i]);
//				// If there are ever four apostrophes, assume the first is supposed to
//				// be text, and the remaining three constitute mark-up for bold text.
//				// (T15227: ''''foo'''' turns into ' ''' foo ' ''')
//				if ($thislen == 4) {
//					$arr[$i - 1] .= "'";
//					$arr[$i] = "'''";
//					$thislen = 3;
//				} elseif ($thislen > 5) {
//					// If there are more than 5 apostrophes in a row, assume they're all
//					// text except for the last 5.
//					// (T15227: ''''''foo'''''' turns into ' ''''' foo ' ''''')
//					$arr[$i - 1] .= str_repeat("'", $thislen - 5);
//					$arr[$i] = "'''''";
//					$thislen = 5;
//				}
//				// Count the number of occurrences of bold and italics mark-ups.
//				if ($thislen == 2) {
//					$numitalics++;
//				} elseif ($thislen == 3) {
//					$numbold++;
//				} elseif ($thislen == 5) {
//					$numitalics++;
//					$numbold++;
//				}
//			}
//
//			// If there is an odd number of both bold and italics, it is likely
//			// that one of the bold ones was meant to be an apostrophe followed
//			// by italics. Which one we cannot know for certain, but it is more
//			// likely to be one that has a single-letter word before it.
//			if (($numbold % 2 == 1) && ($numitalics % 2 == 1)) {
//				$firstsingleletterword = -1;
//				$firstmultiletterword = -1;
//				$firstspace = -1;
//				for ($i = 1; $i < $countarr; $i += 2) {
//					if (strlen($arr[$i]) == 3) {
//						$x1 = substr($arr[$i - 1], -1);
//						$x2 = substr($arr[$i - 1], -2, 1);
//						if ($x1 === ' ') {
//							if ($firstspace == -1) {
//								$firstspace = $i;
//							}
//						} elseif ($x2 === ' ') {
//							$firstsingleletterword = $i;
//							// if $firstsingleletterword is set, we don't
//							// look at the other options, so we can bail early.
//							break;
//						} else {
//							if ($firstmultiletterword == -1) {
//								$firstmultiletterword = $i;
//							}
//						}
//					}
//				}
//
//				// If there is a single-letter word, use it!
//				if ($firstsingleletterword > -1) {
//					$arr[$firstsingleletterword] = "''";
//					$arr[$firstsingleletterword - 1] .= "'";
//				} elseif ($firstmultiletterword > -1) {
//					// If not, but there's a multi-letter word, use that one.
//					$arr[$firstmultiletterword] = "''";
//					$arr[$firstmultiletterword - 1] .= "'";
//				} elseif ($firstspace > -1) {
//					// ... otherwise use the first one that has neither.
//					// (notice that it is possible for all three to be -1 if, for example,
//					// there is only one pentuple-apostrophe in the line)
//					$arr[$firstspace] = "''";
//					$arr[$firstspace - 1] .= "'";
//				}
//			}
//
//			// Now let's actually convert our apostrophic mush to HTML!
//			$output = '';
//			$buffer = '';
//			$state = '';
//			$i = 0;
//			foreach ($arr as $r) {
//				if (($i % 2) == 0) {
//					if ($state === 'both') {
//						$buffer .= $r;
//					} else {
//						$output .= $r;
//					}
//				} else {
//					$thislen = strlen($r);
//					if ($thislen == 2) {
//						if ($state === 'i') {
//							$output .= '</i>';
//							$state = '';
//						} elseif ($state === 'bi') {
//							$output .= '</i>';
//							$state = 'b';
//						} elseif ($state === 'ib') {
//							$output .= '</b></i><b>';
//							$state = 'b';
//						} elseif ($state === 'both') {
//							$output .= '<b><i>' . $buffer . '</i>';
//							$state = 'b';
//						} else { // $state can be 'b' or ''
//							$output .= '<i>';
//							$state .= 'i';
//						}
//					} elseif ($thislen == 3) {
//						if ($state === 'b') {
//							$output .= '</b>';
//							$state = '';
//						} elseif ($state === 'bi') {
//							$output .= '</i></b><i>';
//							$state = 'i';
//						} elseif ($state === 'ib') {
//							$output .= '</b>';
//							$state = 'i';
//						} elseif ($state === 'both') {
//							$output .= '<i><b>' . $buffer . '</b>';
//							$state = 'i';
//						} else { // $state can be 'i' or ''
//							$output .= '<b>';
//							$state .= 'b';
//						}
//					} elseif ($thislen == 5) {
//						if ($state === 'b') {
//							$output .= '</b><i>';
//							$state = 'i';
//						} elseif ($state === 'i') {
//							$output .= '</i><b>';
//							$state = 'b';
//						} elseif ($state === 'bi') {
//							$output .= '</i></b>';
//							$state = '';
//						} elseif ($state === 'ib') {
//							$output .= '</b></i>';
//							$state = '';
//						} elseif ($state === 'both') {
//							$output .= '<i><b>' . $buffer . '</b></i>';
//							$state = '';
//						} else { // ($state == '')
//							$buffer = '';
//							$state = 'both';
//						}
//					}
//				}
//				$i++;
//			}
//			// Now close all remaining tags.  Notice that the order is important.
//			if ($state === 'b' || $state === 'ib') {
//				$output .= '</b>';
//			}
//			if ($state === 'i' || $state === 'bi' || $state === 'ib') {
//				$output .= '</i>';
//			}
//			if ($state === 'bi') {
//				$output .= '</b>';
//			}
//			// There might be lonely ''''', so make sure we have a buffer
//			if ($state === 'both' && $buffer) {
//				$output .= '<b><i>' . $buffer . '</i></b>';
//			}
//			return $output;
//		}
}
