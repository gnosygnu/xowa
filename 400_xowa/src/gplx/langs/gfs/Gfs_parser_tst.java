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
package gplx.langs.gfs;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.errs.ErrUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.StringUtl;
import org.junit.*;
public class Gfs_parser_tst {		
	@Before public void init() {fxt.Clear();} Gfs_parser_fxt fxt = new Gfs_parser_fxt();
	@Test public void Semicolon() {
		fxt	.Test_parse("a;", fxt.nde_("a"));
		fxt	.Test_parse("a;b;c;", fxt.nde_("a"), fxt.nde_("b"), fxt.nde_("c"));
		fxt	.Test_parse("a_0;", fxt.nde_("a_0"));
	}
	@Test public void Dot() {
		fxt	.Test_parse("a.b;", fxt.nde_("a").Subs_add(fxt.nde_("b")));
		fxt	.Test_parse("a.b;c.d;", fxt.nde_("a").Subs_add(fxt.nde_("b")), fxt.nde_("c").Subs_add(fxt.nde_("d")));
	}
	@Test public void Parens() {
		fxt	.Test_parse("a();b();", fxt.nde_("a"), fxt.nde_("b"));
		fxt	.Test_parse("a().b();c().d();", fxt.nde_("a").Subs_add(fxt.nde_("b")), fxt.nde_("c").Subs_add(fxt.nde_("d")));
	}
	@Test public void Num() {
		fxt	.Test_parse("a(1,2);", fxt.nde_("a").Atrs_add_many(fxt.val_("1"), fxt.val_("2")));
	}
	@Test public void Quote() {
		fxt	.Test_parse("a('b');", fxt.nde_("a").Atrs_add(fxt.val_("b")));
	}
	@Test public void Quote_escaped() {
		fxt	.Test_parse("a('b''c''d');", fxt.nde_("a").Atrs_add(fxt.val_("b'c'd")));
	}
	@Test public void Quote_escaped_2() {
		fxt	.Test_parse("a('a''''b');", fxt.nde_("a").Atrs_add(fxt.val_("a''b")));
	}
	@Test public void Quote_mixed() {
		fxt	.Test_parse("a('b\"c');", fxt.nde_("a").Atrs_add(fxt.val_("b\"c")));
	}
	@Test public void Comma() {
		fxt	.Test_parse("a('b','c','d');", fxt.nde_("a").Atrs_add_many(fxt.val_("b"), fxt.val_("c"), fxt.val_("d")));
	}
	@Test public void Ws() {
		fxt	.Test_parse(" a ( 'b' , 'c' ) ; ", fxt.nde_("a").Atrs_add_many(fxt.val_("b"), fxt.val_("c")));
	}
	@Test public void Comment_slash_slash() {
		fxt	.Test_parse("//z\na;//y\n", fxt.nde_("a"));
	}
	@Test public void Comment_slash_star() {
		fxt	.Test_parse("/*z*/a;/*y*/", fxt.nde_("a"));
	}
	@Test public void Curly() {
		fxt	.Test_parse("a{b;}", fxt.nde_("a").Subs_add(fxt.nde_("b")));
	}
	@Test public void Curly_nest() {
		fxt	.Test_parse("a{b{c{d;}}}"
		, fxt.nde_("a").Subs_add
		(	fxt.nde_("b").Subs_add
		(		fxt.nde_("c").Subs_add
		(			fxt.nde_("d")
		))));
	}
	@Test public void Curly_nest_peers() {
		fxt	.Test_parse(StringUtl.ConcatLinesNl
		(	"a{"
		,	"  a0{"
		,	"    a00{"
		,	"      a000;"
		,	"    }"
		,	"    a01;"
		,	"  }"
		,	"  a1;"
		,	"}"
		)
		, fxt.nde_("a").Subs_add_many
		(	fxt.nde_("a0").Subs_add_many
		(		fxt.nde_("a00").Subs_add
		(			fxt.nde_("a000")
		)
		,		fxt.nde_("a01")
		)
		,	fxt.nde_("a1")
		));
	}
	@Test public void Curly_dot() {
		fxt	.Test_parse("a{a0.a00;a1.a10;}"
		, fxt.nde_("a").Subs_add_many
		(	fxt.nde_("a0").Subs_add_many(fxt.nde_("a00"))
		,	fxt.nde_("a1").Subs_add_many(fxt.nde_("a10"))
		));
	}
	@Test public void Eq() {
		fxt	.Test_parse("a='b';", fxt.nde_("a").Atrs_add(fxt.val_("b")));
		fxt	.Test_parse("a.b.c='d';"
		,	fxt.nde_("a").Subs_add
		(		fxt.nde_("b").Subs_add_many
		(			fxt.nde_("c").Atrs_add(fxt.val_("d"))
		)));
		fxt	.Test_parse("a.b{c='d'; e='f'}"
		,	fxt.nde_("a").Subs_add
		(		fxt.nde_("b").Subs_add_many
		(			fxt.nde_("c").Atrs_add(fxt.val_("d"))
		,			fxt.nde_("e").Atrs_add(fxt.val_("f"))
		)));
	}
	@Test public void Curly_nest_peers2() {
		fxt	.Test_parse(StringUtl.ConcatLinesNl
		(	"a() {"
		,	"  k1 = 'v1';"
		,	"}"
		)
		, fxt.nde_("a").Subs_add_many
		(	fxt.nde_("k1").Atrs_add(fxt.val_("v1"))
		)
		);
	}
	@Test public void Fail() {
		fxt	.Test_parse_fail("a(.);", Gfs_err_mgr.Fail_msg_invalid_lxr);	// (.)
		fxt	.Test_parse_fail("a..b;", Gfs_err_mgr.Fail_msg_invalid_lxr);	// ..
		fxt	.Test_parse_fail("a.;", Gfs_err_mgr.Fail_msg_invalid_lxr);		// .;
		fxt	.Test_parse_fail("a", Gfs_err_mgr.Fail_msg_eos);				// eos
		fxt	.Test_parse_fail("a;~;", Gfs_err_mgr.Fail_msg_unknown_char);	// ~
	}
}
class Gfs_parser_fxt {
	public void Clear() {}
	public Gfs_nde nde_(String v) {return new Gfs_nde().Name_(BryUtl.NewA7(v));}
	public Gfs_nde val_(String v) {return new Gfs_nde().Name_(BryUtl.NewA7(v));}
	public void Test_parse(String src_str, Gfs_nde... expd) {
		byte[] src_bry = BryUtl.NewU8(src_str);
		Gfs_nde root = parser.Parse(src_bry);
		GfoTstr.EqLines(To_str(null, expd), To_str(src_bry, root.Subs_to_ary()));
	}	private BryWtr tmp_bfr = BryWtr.New(), path_bfr = BryWtr.New(); Gfs_parser parser = new Gfs_parser();
	public void Test_parse_fail(String src_str, String expd_err) {
		byte[] src_bry = BryUtl.NewU8(src_str);
		try {parser.Parse(src_bry);}
		catch (Exception e) {
			String actl_err = ErrUtl.ToStrFull(e);
			actl_err = StringUtl.GetStrBefore(actl_err, ":");
			boolean match = StringUtl.Has(actl_err, expd_err);
			if (!match) GfoTstr.Fail("expecting '" + expd_err + "' got '" + actl_err + "'");
			return;
		}
		GfoTstr.Fail("expected to fail with " + expd_err);
	}
	public String To_str(byte[] src, Gfs_nde[] expd) {
		int subs_len = expd.length;
		for (int i = 0; i < subs_len; i++) {
			path_bfr.Clear().AddIntVariable(i);
			To_str(tmp_bfr, path_bfr, src, expd[i]);
		}	
		return tmp_bfr.ToStrAndClear();
	}
	public void To_str(BryWtr bfr, BryWtr path, byte[] src, Gfs_nde nde) {
		To_str_atr(bfr, path, src, Atr_name, nde.Name(), nde.Name_bgn(), nde.Name_end());
		int atrs_len = nde.Atrs_len();
		for (int i = 0; i < atrs_len; i++) {
			Gfs_nde atr = nde.Atrs_get_at(i);
			int path_len_old = path.Len();
			path.AddByte(AsciiByte.Dot).AddByte((byte)(AsciiByte.Ltr_a + i));
			int path_len_new = path.Len();
			To_str(bfr, path, src, atr);
			path.DelBy(path_len_new - path_len_old);
		}
		int subs_len = nde.Subs_len();
		for (int i = 0; i < subs_len; i++) {
			Gfs_nde sub = nde.Subs_get_at(i);
			int path_len_old = path.Len();
			path.AddByte(AsciiByte.Dot).AddIntVariable(i);
			int path_len_new = path.Len();
			To_str(bfr, path, src, sub);
			path.DelBy(path_len_new - path_len_old);
		}
	}
	private void To_str_atr(BryWtr bfr, BryWtr path_bfr, byte[] src, byte[] name, byte[] val, int val_bgn, int val_end) {
		if (val == null && val_bgn == -1 && val_end == -1) return;
		bfr.AddBfrAndPreserve(path_bfr).AddByte(AsciiByte.Colon);
		bfr.Add(name);
		if (val == null)
			bfr.AddMid(src, val_bgn, val_end);
		else
			bfr.Add(val);
		bfr.AddByteNl();
	}
	private static final byte[] Atr_name = BryUtl.NewA7("name=");
}
