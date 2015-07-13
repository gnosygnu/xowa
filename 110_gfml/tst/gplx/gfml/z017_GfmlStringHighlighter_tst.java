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
