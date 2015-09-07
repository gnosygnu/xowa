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
package gplx.xowa.xtns.math.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.math.*;
import gplx.core.btries.*;
class Mwm_tkn__func {
	public Mwm_tkn__func(byte[] key) {
		this.key = key;
	}
	public byte[] Key() {return key;} private final byte[] key;
	public byte[] Manual() {return manual;} public Mwm_tkn__func Manual_(String v) {manual = Bry_.new_a7(v); return this;} private byte[] manual;
	public boolean Literal() {return literal;} public Mwm_tkn__func Literal_() {this.literal = true; return this;} private boolean literal;
	public boolean Big() {return big;} public Mwm_tkn__func Big_() {this.big = true; return this;} private boolean big;
	public boolean Delimiter() {return delimiter;} public Mwm_tkn__func Delimiter_() {this.delimiter = true; return this;} private boolean delimiter;
	public boolean Tex_only() {return tex_only;} public Mwm_tkn__func Tex_only_() {this.tex_only = true; return this;} private boolean tex_only;
	public boolean Fun_ar1() {return fun_ar1;} public Mwm_tkn__func Fun_ar1_() {this.fun_ar1 = true; return this;} private boolean fun_ar1;
	public boolean Fun_ar2() {return fun_ar2;} public Mwm_tkn__func Fun_ar2_() {this.fun_ar2 = true; return this;} private boolean fun_ar2;
	public boolean Fun_ar2nb() {return fun_ar2nb;} public Mwm_tkn__func Fun_ar2nb_() {this.fun_ar2nb = true; return this;} private boolean fun_ar2nb;
	public boolean Fun_infix() {return fun_infix;} public Mwm_tkn__func Fun_infix_() {this.fun_infix = true; return this;} private boolean fun_infix;
	public boolean Declh() {return declh;} public Mwm_tkn__func Declh_() {this.declh = true; return this;} private boolean declh;
	public boolean Fontforce_rm() {return fontforce_rm;} public Mwm_tkn__func Fontforce_rm_() {this.fontforce_rm = true; return this;} private boolean fontforce_rm;
	public boolean Left() {return left;} public Mwm_tkn__func Left_() {this.left = true; return this;} private boolean left;
	public boolean Right() {return right;} public Mwm_tkn__func Right_() {this.right = true; return this;} private boolean right;
	public boolean Fail() {return fail;} public Mwm_tkn__func Fail_() {this.fail = true; return this;} private boolean fail;
	public boolean Type_latex() {return type_latex;} public Mwm_tkn__func Type_latex_() {this.type_latex = true; return this;} private boolean type_latex;
	public boolean Type_mw() {return type_mw;} public Mwm_tkn__func Type_mw_() {this.type_mw = true; return this;} private boolean type_mw;
}
