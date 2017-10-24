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
package gplx.xowa.xtns.math; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.btries.*;
class Xomath_subst_mgr {
	private final    Btrie_slim_mgr trie = Btrie_slim_mgr.cs();
	private final    Bry_bfr tmp = Bry_bfr_.New();
	private boolean init = false;
	public byte[] Subst(byte[] src) {
		if (!init) Init();
		int src_len = src.length;
		int inserted_dollars = 0;

		// loop each byte
		for (int i = 0; i < src_len; i++) {
			byte b = src[i];
			Object o = trie.Match_bgn_w_byte(b, src, i, src_len);
			if (o == null)	// regular char; add to bfr
				tmp.Add_byte(b);
			else {			// subst itm's trg for src
				Xomath_subst_itm itm = (Xomath_subst_itm)o;
				int itm_src_len = itm.Src_len();

				// if whole_word, check last_char for exact match; exit if not
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
							if (itm.Whole_word()) {
								tmp.Add_byte(b);	// itm does not match; ignore; EX: \alpha is itm, but cur text is \alpham
								continue;
							}
							else
								break;
					}
				}

				// add trg; increment dollar_sign; update i
				tmp.Add(itm.Trg());
				if (itm.Dollar_sign()) ++inserted_dollars;	// if .Dollar_sign() is true, then tkn has inserted a dollar sign; will need to add matching closing item below
				i += itm_src_len - 1;
			}
		}

		// add closing dollar-sign tokens for inserted_dollars
		for (int i = 0; i < inserted_dollars; i++)
			tmp.Add_byte(Byte_ascii.Dollar);

		return tmp.To_bry_and_clear_and_trim();
	}
	private Xomath_subst_mgr Init() {
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
	private void Reg(String src_str, String trg_str, boolean dollar_sign, boolean whole_word) {
		byte[] src_bry = Bry_.new_a7(src_str);
		Xomath_subst_itm itm = new Xomath_subst_itm(src_bry, Bry_.new_a7(trg_str), dollar_sign, whole_word);
		trie.Add_obj(src_bry, itm);
	}
}
class Xomath_subst_itm {
	public Xomath_subst_itm(byte[] src, byte[] trg, boolean dollar_sign, boolean whole_word) {
		this.src = src; src_len = src.length; this.trg = trg; this.dollar_sign = dollar_sign; this.whole_word = whole_word;
	}
	public int Src_len() {return src_len;} private final    int src_len;
	public byte[] Src() {return src;} private final    byte[] src;
	public byte[] Trg() {return trg;} private final    byte[] trg;
	public boolean Dollar_sign() {return dollar_sign;} private final    boolean dollar_sign;
	public boolean Whole_word() {return whole_word;} private final    boolean whole_word;
}
