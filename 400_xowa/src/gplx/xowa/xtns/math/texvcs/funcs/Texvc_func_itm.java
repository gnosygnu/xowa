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
package gplx.xowa.xtns.math.texvcs.funcs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.math.*; import gplx.xowa.xtns.math.texvcs.*;
import gplx.core.btries.*;
import gplx.xowa.xtns.math.texvcs.tkns.*;
public class Texvc_func_itm {
	private Texvc_tkn tkn; private boolean tkn_check = true;
	public Texvc_func_itm(int id, byte[] key) {this.id = id; this.key = key;}
	public int Id() {return id;} private final int id;
	public byte[] Key() {return key;} private final byte[] key;
	public Texvc_tkn Tkn() {
		if (tkn_check) {
			tkn_check = false;
			if (literal || delimiter) this.tkn = new Texvc_tkn__func(this);
		}
		return tkn;
	}
	public int Singleton_id() {return id + Texvc_tkn_.Tid_len;}
	public byte[] Manual() {return manual;} public Texvc_func_itm Manual_(String v) {manual = Bry_.new_a7(v); return this;} private byte[] manual;
	public boolean Literal() {return literal;} public Texvc_func_itm Literal_() {this.literal = true; return this;} private boolean literal;
	public boolean Big() {return big;} public Texvc_func_itm Big_() {this.big = true; return this;} private boolean big;
	public boolean Delimiter() {return delimiter;} public Texvc_func_itm Delimiter_() {this.delimiter = true; return this;} private boolean delimiter;
	public boolean Tex_only() {return tex_only;} public Texvc_func_itm Tex_only_() {this.tex_only = true; return this;} private boolean tex_only;
	public boolean Fun_ar1() {return fun_ar1;} public Texvc_func_itm Fun_ar1_() {this.fun_ar1 = true; return this;} private boolean fun_ar1;
	public boolean Fun_ar2() {return fun_ar2;} public Texvc_func_itm Fun_ar2_() {this.fun_ar2 = true; return this;} private boolean fun_ar2;
	public boolean Fun_ar2nb() {return fun_ar2nb;} public Texvc_func_itm Fun_ar2nb_() {this.fun_ar2nb = true; return this;} private boolean fun_ar2nb;
	public boolean Fun_infix() {return fun_infix;} public Texvc_func_itm Fun_infix_() {this.fun_infix = true; return this;} private boolean fun_infix;
	public boolean Declh() {return declh;} public Texvc_func_itm Declh_() {this.declh = true; return this;} private boolean declh;
	public boolean Fontforce_rm() {return fontforce_rm;} public Texvc_func_itm Fontforce_rm_() {this.fontforce_rm = true; return this;} private boolean fontforce_rm;
	public boolean Left() {return left;} public Texvc_func_itm Left_() {this.left = true; return this;} private boolean left;
	public boolean Right() {return right;} public Texvc_func_itm Right_() {this.right = true; return this;} private boolean right;
	public boolean Fail() {return fail;} public Texvc_func_itm Fail_() {this.fail = true; return this;} private boolean fail;
	public boolean Type_latex() {return type_latex;} public Texvc_func_itm Type_latex_() {this.type_latex = true; return this;} private boolean type_latex;
	public boolean Type_mw() {return type_mw;} public Texvc_func_itm Type_mw_() {this.type_mw = true; return this;} private boolean type_mw;
	public int Args() {return args;} public Texvc_func_itm Args_(int v) {args = v; return this;} private int args;
	public String Tag() {return tag;} public Texvc_func_itm Tag_(String v) {tag = v; return this;} private String tag;
}
