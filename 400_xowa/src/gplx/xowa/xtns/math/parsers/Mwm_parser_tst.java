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
import org.junit.*;
public class Mwm_parser_tst {
	private final Mwm_parser_fxt fxt = new Mwm_parser_fxt();
	@Test  public void Text() {
		fxt.Test_parse("abc"
		,	fxt.text(0, 3)
		);
	}
	@Test  public void Ws() {
		fxt.Test_parse("   "
		,	fxt.ws(0, 3)
		);
	}
	@Test  public void Mix() {
		fxt.Test_parse("a b c"
		,	fxt.text(0, 1)
		,	fxt.ws	(1, 2)
		,	fxt.text(2, 3)
		,	fxt.ws	(3, 4)
		,	fxt.text(4, 5)
		);
	}
	@Test  public void Func() {
		fxt.Test_parse("\\abc \\def"
		,	fxt.func(0, 4)
		,	fxt.ws	(4, 5)
		,	fxt.func(5, 9)
		);
	}
//		@Test  public void Arg() {
//			fxt.Test_parse("{a}"
//			,	fxt.arg(0, 3
//			,		fxt.text(1, 2)
//			)
//			);
//		}
}
class Mwm_parser_fxt {
	private final Bry_bfr tmp_bfr = Bry_bfr.reset_(255);
	private final Mwm_parser parser = new Mwm_parser();
	private final Mwm_tkn__root expd_root, actl_root;
	private final Mwm_tkn_mkr tkn_mkr = new Mwm_tkn_mkr();
	public Mwm_parser_fxt() {
		this.expd_root = new Mwm_tkn__root(tkn_mkr);
		this.actl_root = new Mwm_tkn__root(tkn_mkr);
	}
	public void Test_parse(String src_str, Mwm_tkn... expd_tkns) {
		byte[] src_bry = Bry_.new_u8(src_str);
		int src_len = src_bry.length;
		expd_root.Init_as_root(0, src_len);
		actl_root.Init_as_root(0, src_len);
		parser.Parse(actl_root, src_bry);
		int len = actl_root.Subs__len();
		for (int i = 0; i < len; ++i) {
			Mwm_tkn sub = actl_root.Subs__get_at(i);
			sub.To_bry(tmp_bfr, 0);
		}
		String expd = tmp_bfr.Xto_str_and_clear();
		len = expd_tkns.length;
		for (int i = 0; i < len; ++i) {
			Mwm_tkn sub = expd_tkns[i];
			sub.To_bry(tmp_bfr, 0);
		}
		String actl = tmp_bfr.Xto_str_and_clear();
		Tfds.Eq_str_lines(expd, actl, src_str);
	}
	private Mwm_tkn leaf(int tid, int bgn, int end) {
		int uid = expd_root.Regy__add(tid, bgn, end, null);
		return new Mwm_tkn__leaf().Init(expd_root, tid, uid, bgn, end);
	}
	private Mwm_tkn node(int tid, int bgn, int end, Mwm_tkn tkn) {
		int uid = expd_root.Regy__add(tid, bgn, end, tkn);
		return new Mwm_tkn__node().Init(expd_root, tid, uid, bgn, end);
	}
	public Mwm_tkn text	(int bgn, int end) {return leaf(Mwm_tkn_.Tid__text	, bgn, end);}
	public Mwm_tkn ws	(int bgn, int end) {return leaf(Mwm_tkn_.Tid__ws	, bgn, end);}
	public Mwm_tkn func	(int bgn, int end) {return node(Mwm_tkn_.Tid__func	, bgn, end, new Mwm_tkn__node());}
	public Mwm_tkn arg	(int bgn, int end, Mwm_tkn... subs) {
		Mwm_tkn rv = node(Mwm_tkn_.Tid__func	, bgn, end, new Mwm_tkn__node());
		int len = subs.length;
		for (int i = 0; i < len; ++i) {
			Mwm_tkn sub = subs[i];
			expd_root.Regy__move(rv, sub);
		}
		return rv;
	}
}
