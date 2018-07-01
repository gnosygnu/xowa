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
package gplx.gfml; import gplx.*;
import org.junit.*;
public class z017_GfmlStringHighlighter_tst {
	@Test  public void Short() {
		tst_Err(sh_().Raw_("a=").Mark_(1, '=', "key tkn").Mark_(2, '?', "EOS:missing data")
			,   "<  >"
			,   " a= "
			,	"  =?"
			,	""
			,	"[1] = key tkn"
			,	"[2] ? EOS:missing data"
			);
	}
	@Test  public void Whitespace() {
		tst_Err(sh_().Raw_("a\t\nb").Mark_(0, ' ', "")
			,   "< tn >"
			,   " a  b "
			,   "      "
			);
	}
	@Test  public void Long() {
		tst_Err(sh_().Raw_("abcdefghijklmnopqrstuvwxyzyxwvutsrqponmlkjihgfedcba").Mark_(0, '{', "bgn").Mark_(50, '}', "end")
			,   "<                                                   >"
			,   " abcdefghijklmnopqrstuvwxyzyxwvutsrqponmlkjihgfedcba "
			,   " {                                                 } "
			,	""
			,	"[00] { bgn"
			,	"[50] } end"
			);
	}
	GfmlStringHighlighter sh_() {return GfmlStringHighlighter.new_();}
	void tst_Err(GfmlStringHighlighter sh, String... expdLines) {
		String[] actlLines = sh.Gen();
		Tfds.Eq_ary_str(expdLines, actlLines);
	}
}
