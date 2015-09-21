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
public class Vnt_language_converter {
	private final Bry_bfr bfr = Bry_bfr.new_();
	private int max_depth = 32; 
	private byte[] src; private int src_len;
	private boolean convert_needed;
	private int pos;
	public byte[] Parse(byte[] vnt, byte[] src) {// REF.MW:/languages/LanguageConverter.php!recursiveConvertTopLevel
		synchronized (bfr) {
			int markup_count = 0;
			this.pos = 0;
			this.convert_needed = false; // for sr lang; SEE:LanguageSr.php !$this->guessVariant(src, vnt);
			this.src = src; this.src_len = src.length;
			while (pos < src_len) {
				int curly_bgn = Bry_find_.Find_fwd(src, Bry__curly_bgn, pos, src_len);
				if (curly_bgn == Bry_find_.Not_found) {		// No more markup, append final segment
					if (markup_count == 0) return src;			// no markups found; just return original
					Add_output(vnt, convert_needed, src, pos, src_len);
					return bfr.Xto_bry_and_clear();
				}
				bfr.Add_mid(src, pos, curly_bgn);				// Markup found; append segment
				Add_output(vnt, convert_needed, src, pos, src_len);
				pos = curly_bgn;								// Advance position
				++markup_count;
				Parse_recursive(vnt, pos, 1);		// Do recursive conversion
			}
			return bfr.Xto_bry_and_clear();
		}
	}
	private void Parse_recursive(byte[] vnt, int pos, int depth) {
		pos += 2;	// skip "-{"
		boolean warning_done = false;
		// $inner = '';
		while (pos < src_len) {
			byte b = src[pos];
			Object o = trie.Match_bgn_w_byte(b, src,pos, src_len);
			if (o == null) {	// char;
				++pos;
				continue;
			}
			int new_pos = trie.Match_pos();					// Markup found; Append initial segment
			bfr.Add_mid(src, pos, new_pos);
			pos = new_pos;									// Advance position
			switch (((Byte_obj_val)o).Val()) {
				case Tid__curly_bgn:
					if (depth >= max_depth) {
						bfr.Add(Bry__curly_bgn);
						if (!warning_done) {
							bfr.Add_str("<span class=\"error\">");
							// wfMessage('language-converter-depth-warning')->numParams($this->mMaxDepth)->inContentLanguage()->text()
							bfr.Add_str("</span>");
							warning_done = true;
						}
						pos += 2;	// skip "-{"
						continue;
					}
					Parse_recursive(vnt, pos, depth + 1);	// Recursively parse another rule
					break;
				case Tid__curly_end:
					pos += 2;
					/*
					// Apply the rule
					$rule = new ConverterRule($inner, $this);
					$rule->parse($variant);
					$this->applyManualConv($rule);
					return $rule->getDisplay();
					*/
					return;
				default: throw Err_.new_unhandled(-1);		// never happens
			}
		}
		if (pos < src_len) { // Unclosed rule
			byte[] frag = Auto_convert(vnt, src, pos, src_len);
			bfr.Add(Bry__curly_bgn).Add(frag);
		}
		pos = src_len;
	}
	private void Add_output(byte[] vnt, boolean convert_needed, byte[] src, int pos, int src_len) {
		if (convert_needed) {
			byte[] frag = Auto_convert(vnt, src, pos, src_len);
			bfr.Add(frag);
		}
		else
			bfr.Add_mid(src, pos, src_len);
	}
	private byte[] Auto_convert(byte[] vnt, byte[] src, int bgn, int end) {return src;}
	private static final byte Tid__curly_bgn = 1, Tid__curly_end = 2;
	private static final byte[] Bry__curly_bgn = Bry_.new_a7("-{"), Bry__curly_end = Bry_.new_a7("}-");
	private static final Btrie_fast_mgr trie = Btrie_fast_mgr.cs()
	.Add_bry_byte(Bry__curly_bgn, Tid__curly_bgn)
	.Add_bry_byte(Bry__curly_end, Tid__curly_end);
}
