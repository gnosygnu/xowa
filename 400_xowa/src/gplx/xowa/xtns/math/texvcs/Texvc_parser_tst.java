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
package gplx.xowa.xtns.math.texvcs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.math.*;
import org.junit.*; import gplx.xowa.xtns.math.texvcs.tkns.*; import gplx.xowa.xtns.math.texvcs.funcs.*;
public class Texvc_parser_tst {
	private final    Texvc_parser_fxt fxt = new Texvc_parser_fxt();
	@Before public void init() {fxt.Clear();}
	@Test  public void Text() {
		fxt.Test_parse("abc"
		,	fxt.Mkr().text(0, 3)
		);
	}
	@Test  public void Ws() {
		fxt.Test_parse("   "
		,	fxt.Mkr().ws(0, 3)
		);
	}
	@Test  public void Mix() {
		fxt.Test_parse("a b c"
		,	fxt.Mkr().text(0, 1)
		,	fxt.Mkr().ws	(1, 2)
		,	fxt.Mkr().text(2, 3)
		,	fxt.Mkr().ws	(3, 4)
		,	fxt.Mkr().text(4, 5)
		);
	}
	@Test  public void Curly() {
		fxt.Test_parse("{a}"
		,	fxt.Mkr().curly(0, 3
		,		fxt.Mkr().text(1, 2)
		)
		);
	}
	@Test  public void Curly_2() {
		fxt.Test_parse("{a{b}c}"
		,	fxt.Mkr().curly(0, 7
			,	fxt.Mkr().text	(1, 2)
			,	fxt.Mkr().curly	(2, 5
				,	fxt.Mkr().text	(3, 4)
				)
			,	fxt.Mkr().text	(5, 6)
		)
		);
	}
	@Test  public void Func() {
		fxt.Test_parse("\\abc \\def"
		,	fxt.Mkr().func(0, 4, Texvc_func_itm_.Id__xowa_unknown)
		,	fxt.Mkr().ws  (4, 5)
		,	fxt.Mkr().func(5, 9, Texvc_func_itm_.Id__xowa_unknown)
		);
	}
	@Test  public void Mathrm() {
		fxt.Test_parse("\\mathrm\\frac{a}{b}"
		,	fxt.Mkr().func	( 0,  7, Texvc_func_itm_.Id__mathrm)
		,	fxt.Mkr().func	( 7, 12, Texvc_func_itm_.Id__frac)
		,	fxt.Mkr().curly	(12, 15
		,		fxt.Mkr().text	(13, 14)
		)
		,	fxt.Mkr().curly	(15, 18
		,		fxt.Mkr().text	(16, 17)
		)
		);
	}
}
class Texvc_tkn_mkr_fxt {
	private final    Texvc_ctx ctx;
	private final    Texvc_root root;
	public Texvc_tkn_mkr_fxt(Texvc_ctx ctx) {
		this.ctx = ctx;
		this.root = new Texvc_root();
	}
	public void Clear() {
		root.Init_as_root(ctx.Tkn_mkr(), Bry_.Empty, 0, 8);
	}
	private Texvc_tkn leaf(int tid, int bgn, int end) {
		int uid = root.Regy__add(tid, tid, bgn, end, null);
		return new Texvc_tkn__leaf_raw().Init(root, tid, uid, bgn, end);
	}
	public Texvc_tkn text	(int bgn, int end) {return leaf(Texvc_tkn_.Tid__text	, bgn, end);}
	public Texvc_tkn ws	(int bgn, int end) {return leaf(Texvc_tkn_.Tid__ws	, bgn, end);}
	public Texvc_tkn func	(int bgn, int end, int func_tid, Texvc_tkn... subs) {
		Texvc_func_itm itm = ctx.Func_regy().Get_at(func_tid);
		Texvc_tkn tkn = itm.Tkn(); if (tkn == null) tkn = new Texvc_tkn__func(Texvc_func_itm_.Itm__unknown);
		node(Texvc_tkn_.Tid__func, itm.Singleton_id(), tkn, bgn, end, subs);
		return tkn;
	}
	public Texvc_tkn curly(int bgn, int end, Texvc_tkn... subs) {
		int tid = Texvc_tkn_.Tid__curly;
		Texvc_tkn tkn = new Texvc_tkn__func(Texvc_func_itm_.Itm__arg);
		node(tid, Texvc_tkn_mkr.Singleton_id__null, tkn, bgn, end, subs);
		return tkn;
	}
	private void node(int tid, int singleton_id, Texvc_tkn tkn, int bgn, int end, Texvc_tkn... subs) {
		root.Regy__add(tid, singleton_id, bgn, end, tkn);
		int len = subs.length;
		for (int i = 0; i < len; ++i) {
			Texvc_tkn sub = subs[i];
			root.Regy__move(sub.Uid(), tkn.Uid());
		}
	}
}
class Texvc_parser_fxt {
	protected final    Bry_bfr tmp_bfr = Bry_bfr_.Reset(255);
	private final    Texvc_parser parser = new Texvc_parser();
	private final    Texvc_root actl_root;
	private final    Texvc_ctx ctx;
	public Texvc_parser_fxt() {
		this.actl_root = new Texvc_root();
		this.ctx = new Texvc_ctx();
		this.mkr_fxt = new Texvc_tkn_mkr_fxt(ctx);
	}
	public Texvc_tkn_mkr_fxt Mkr() {return mkr_fxt;} private final    Texvc_tkn_mkr_fxt mkr_fxt;
	public void Clear() {
		mkr_fxt.Clear();
		actl_root.Init_as_root(ctx.Tkn_mkr(), Bry_.Empty, 0, 8);
	}
	public Texvc_root Exec_parse(byte[] src_bry) {
		parser.Parse(ctx, actl_root, src_bry);
		return actl_root;
	}
	public void Test_parse(String src_str, Texvc_tkn... expd_tkns) {
		byte[] src_bry = Bry_.new_u8(src_str);
		Exec_parse(src_bry);
		Tfds.Eq_str_lines(Texvc_tkn_.Print_dbg_str(tmp_bfr, expd_tkns), actl_root.Print_dbg_str(tmp_bfr), src_str);
	}
}
