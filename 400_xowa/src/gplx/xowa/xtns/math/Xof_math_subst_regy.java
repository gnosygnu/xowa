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
package gplx.xowa.xtns.math; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.btries.*;
public class Xof_math_subst_regy {
	Bry_bfr bfr = Bry_bfr.new_();
	public byte[] Subst(byte[] src) {
		if (!init) Init();
		int src_len = src.length;
		int dollarSignCount = 0;
		for (int i = 0; i < src_len; i++) {
			byte b = src[i];
			Object o = trie.Match_bgn_w_byte(b, src, i, src_len);
			if (o == null)
				bfr.Add_byte(b);
			else {
				Xof_math_subst_itm itm = (Xof_math_subst_itm)o;
				int itm_src_len = itm.SrcLen();
				int nxt = i + itm_src_len;
				if (nxt < src_len) {
					switch (src[nxt]) {	// NOTE: for now, manually list characters that are viable word end characters; NOTE: my knowledge of TeX is nil
  							case Byte_ascii.Space:
						case Byte_ascii.Curly_end:
						case Byte_ascii.Brack_end:
						case Byte_ascii.Underline:
						case Byte_ascii.Nl: // NOTE: needed for \begin\n
							break;
						default:
							if (itm.WholeWord()) {
								bfr.Add_byte(b);	// itm does not match; ignore; EX: \alpha is itm, but cur text is \alpham
								continue;
							}
							else
								break;
					}
				}
				bfr.Add(itm.Trg());
				if (itm.DollarSign()) ++dollarSignCount;
				i += itm_src_len - 1;
			}
		}
		for (int i = 0; i < dollarSignCount; i++)
			bfr.Add_byte(Byte_ascii.Dollar);
		return bfr.To_bry_and_clear_and_trim();
	}	boolean init = false;
	public Xof_math_subst_regy Init() {
		if (init) return this;
		init = true;
		Reg("\\Alpha", "\\mathrm{A}");
		Reg("\\Beta", "\\mathrm{B}");
		Reg("\\Epsilon", "\\mathrm{E}");
		Reg("\\Zeta", "\\mathrm{Z}");
		Reg("\\Eta", "\\mathrm{H}");
		Reg("\\thetasym", "\\vartheta");
		Reg("\\Iota", "\\mathrm{I}");
		Reg("\\Kappa", "\\mathrm{K}");
		Reg("\\Mu", "\\mathrm{M}");
		Reg("\\Nu", "\\mathrm{N}");
		Reg("\\omicron", "\\mathrm{o}");
		Reg("\\Omicron", "\\mathrm{O}");
		Reg("\\Rho", "\\mathrm{P}");
		Reg("\\Tau", "\\mathrm{T}");
		Reg("\\Chi", "\\mathrm{X}");
		Reg("\\alef", "\\aleph");
		Reg("\\alefsym", "\\aleph");
		Reg("\\larr", "\\leftarrow");
		Reg("\\rarr", "\\rightarrow");
		Reg("\\Larr", "\\Leftarrow");
		Reg("\\lArr", "\\Leftarrow");
		Reg("\\Rarr", "\\Rightarrow");
		Reg("\\rArr", "\\Rightarrow");
		Reg("\\uarr", "\\uparrow");
		Reg("\\uArr", "\\Uparrow");
		Reg("\\Uarr", "\\Uparrow");
		Reg("\\darr", "\\downarrow");
		Reg("\\dArr", "\\Downarrow");
		Reg("\\Darr", "\\Downarrow");
		Reg("\\lrarr", "\\leftrightarrow");
		Reg("\\harr", "\\leftrightarrow");
		Reg("\\Lrarr", "\\Leftrightarrow");
		Reg("\\Harr", "\\Leftrightarrow");
		Reg("\\lrArr", "\\Leftrightarrow");
		Reg("\\hAar", "\\Leftrightarrow");
		Reg("\\bull", "\\bullet");
		Reg("\\Dagger", "\\ddagger");
		Reg("\\weierp", "\\wp");
		Reg("\\and", "\\land");
		Reg("\\or", "\\lor");
		Reg("\\sub", "\\subset");
		Reg("\\supe", "\\supseteq");
		Reg("\\sube", "\\subseteq");
		Reg("\\infin", "\\infty");
		Reg("\\isin", "\\in");
		Reg("\\exist", "\\exists");
		Reg("\\ne", "\\neq");
		Reg("\\real", "\\Re");
		Reg("\\image", "\\Im");
		Reg("\\plusmn", "\\pm");
		Reg("\\sdot", "\\cdot");
		Reg("\\empty", "\\emptyset");
		Reg("\\O", "\\emptyset");
		Reg("\\sect", "\\S");
		Reg("\\ge", "\\geq");
		Reg("\\le", "\\leq");
		Reg("\\ang", "\\angle");
		Reg("\\part", "\\partial", false, true);
		Reg("\\lang", "\\langle");
		Reg("\\rang", "\\rangle");
		Reg("\\lbrack", "[");
		Reg("\\rbrack", "]");
		Reg("\\clubs", "\\clubsuit");
		Reg("\\spades", "\\spadesuit");
		Reg("\\hearts", "\\heartsuit");
		Reg("\\diamonds", "\\diamondsuit");
		Reg("\\euro", "\\mbox{\\euro}");
		Reg("\\geneuro", "\\mbox{\\geneuro}");
		Reg("\\geneuronarrow", "\\mbox{\\geneuronarrow}");
		Reg("\\geneurowide", "\\mbox{\\geneurowide}");
		Reg("\\officialeuro", "\\mbox{\\officialeuro}");
		Reg("\\Coppa", "\\mbox{\\Coppa}");
		Reg("\\coppa", "\\mbox{\\coppa}");
		Reg("\\varcoppa", "\\mbox{\\coppa}");
		Reg("\\Digamma", "\\mbox{\\Digamma}");
		Reg("\\Koppa", "\\mbox{\\Koppa}");
		Reg("\\koppa", "\\mbox{\\koppa}");
		Reg("\\Sampi", "\\mbox{\\Sampi}");
		Reg("\\sampi", "\\mbox{\\sampi}");
		Reg("\\Stigma", "\\mbox{\\Stigma}");
		Reg("\\stigma", "\\mbox{\\stigma}");
		Reg("\\varstigma", "\\mbox{\\varstigma}");
		Reg("\\reals", "\\mathbb{R}");
		Reg("\\Reals", "\\mathbb{R}");
		Reg("\\R", "\\mathbb{R}");
		Reg("\\C", "\\mathbb{C}");
		Reg("\\cnums", "\\mathbb{C}");
		Reg("\\Complex", "\\mathbb{C}");
		Reg("\\Z", "\\mathbb{Z}");
		Reg("\\natnums", "\\mathbb{N}");
		Reg("\\N", "\\mathbb{N}");
		Reg("\\Q", "\\mathbb{Q}");
		Reg("\\H", "\\mathbb{H}");
		Reg("\\restriction", "\\upharpoonright");
		Reg("\\begin{align}", "\n$\\begin{align}", true, true);	// HACK: \begin{align} needs to have preceding blank line else "\begin{align} allowed only in paragraph mode." and no dvi produced
		return this;
	}
	private void Reg(String src_str, String trg_str) {Reg(src_str, trg_str, false, true);}
	private void Reg(String src_str, String trg_str, boolean dollarSign, boolean wholeWord) {
		byte[] src_bry = Bry_.new_a7(src_str);
		Xof_math_subst_itm itm = new Xof_math_subst_itm(src_bry, Bry_.new_a7(trg_str), dollarSign, wholeWord);
		trie.Add_obj(src_bry, itm);
	}
	private Btrie_slim_mgr trie = Btrie_slim_mgr.cs();
}
class Xof_math_subst_itm {
	public int SrcLen() {return src_len;} private int src_len;
	public byte[] Src() {return src;} private byte[] src;
	public byte[] Trg() {return trg;} private byte[] trg;
	public boolean DollarSign() {return dollarSign;} private boolean dollarSign;
	public boolean WholeWord() {return wholeWord;} private boolean wholeWord;
	public Xof_math_subst_itm(byte[] src, byte[] trg, boolean dollarSign, boolean wholeWord) {this.src = src; src_len = src.length; this.trg = trg; this.dollarSign = dollarSign; this.wholeWord = wholeWord;}
}
