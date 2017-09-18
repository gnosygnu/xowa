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
package gplx.langs.mustaches; import gplx.*; import gplx.langs.*;
import org.junit.*; import gplx.core.primitives.*;
public class Mustache_itm_render_tst {
	private final    Mustache_itm_render_fxt fxt = new Mustache_itm_render_fxt();
	@Test  public void Text() {
		fxt.Test__parse("a b c", "a b c");
	}
	@Test  public void Variable() {
		fxt.Init__root(fxt.Make_mock(0).Add_prop("prop1", "1").Add_prop("prop2", "2"));
		fxt.Test__parse("{{prop1}}", "1");
		fxt.Test__parse("a{{prop1}}b{{prop2}}c", "a1b2c");
	}
	@Test  public void Escape() {
		fxt.Init__root(fxt.Make_mock(0).Add_prop("prop1", "<"));
		fxt.Test__parse("{{{prop1}}}", "<");
		fxt.Test__parse("{{prop1}}", "&lt;");
	}
	@Test  public void Section_bool() {
		fxt.Init__root(fxt.Make_mock(0).Add_bool_y("bool_y").Add_bool_n("bool_n"));
		fxt.Test__parse("a{{#bool_y}}b{{/bool_y}}c", "abc");
		fxt.Test__parse("a{{#bool_n}}b{{/bool_n}}c", "ac");
		fxt.Test__parse("a{{#bool_y}}b{{/bool_y}}c{{#bool_n}}d{{/bool_n}}e", "abce");
	}
	@Test  public void Section_not() {
		fxt.Init__root(fxt.Make_mock(0).Add_bool_y("bool_y").Add_bool_n("bool_n"));
		fxt.Test__parse("a{{^bool_y}}b{{/bool_y}}c", "ac");
		fxt.Test__parse("a{{^bool_n}}b{{/bool_n}}c", "abc");
		fxt.Test__parse("a{{^bool_y}}b{{/bool_y}}c{{^bool_n}}d{{/bool_n}}e", "acde");
	}
	@Test   public void Section_ws() {
		fxt.Init__root(fxt.Make_mock(0).Add_bool_y("bool_y"));
		fxt.Test__parse("a\n  {{#bool_y}}   \nb\n  {{/bool_y}}   \nc", "a\nb\nc");
	}
	@Test  public void Section_subs_flat() {
		fxt.Init__root(fxt.Make_mock(0).Add_subs("subs1"
		,	fxt.Make_mock(1).Add_prop("prop1", "1").Add_subs("subs2")
		,	fxt.Make_mock(2).Add_prop("prop1", "2").Add_subs("subs2")
		));
		fxt.Test__parse("a{{#subs1}}({{prop1}}){{/subs1}}d", "a(1)(2)d");
	}
	@Test  public void Section_subs_nest_1() {
		fxt.Init__root
		(	fxt.Make_mock(0).Add_subs("subs1"
		,		fxt.Make_mock(1).Add_prop("prop1", "a").Add_subs("subs2"
		,			fxt.Make_mock(11).Add_prop("prop2", "1")
		,			fxt.Make_mock(12).Add_prop("prop2", "2"))
		));
		fxt.Test__parse
		( "{{#subs1}}{{prop1}}{{#subs2}}{{prop2}}{{/subs2}}{{/subs1}}"
		, "a12"
		);
	}
	@Test  public void Section_subs_nest_2() {
		fxt.Init__root
		(	fxt.Make_mock(0).Add_subs("subs1"
		,		fxt.Make_mock(1).Add_prop("prop1", "a").Add_subs("subs2"
		,			fxt.Make_mock(11).Add_prop("prop2", "1")
		,			fxt.Make_mock(12).Add_prop("prop2", "2")
				)
		,		fxt.Make_mock(2).Add_prop("prop1", "b")
			)
		);
		fxt.Test__parse
		( "{{#subs1}}{{prop1}}{{#subs2}}{{prop2}}{{/subs2}}{{/subs1}}"
		, "a12b"
		);
	}
	@Test  public void Section_subs_nest_3() {
		fxt.Init__root
		(	fxt.Make_mock(0).Add_subs("subs1"
		,		fxt.Make_mock(1).Add_prop("prop1", "a").Add_subs("subs2"
		,			fxt.Make_mock(11).Add_prop("prop2", "1")
		,			fxt.Make_mock(12).Add_prop("prop2", "2")
				)
		,		fxt.Make_mock(2).Add_prop("prop1", "b").Add_subs("subs2"
		,			fxt.Make_mock(21).Add_prop("prop2", "3")
		,			fxt.Make_mock(22).Add_prop("prop2", "4")
				)
			)
		);
		fxt.Test__parse
		( "{{#subs1}}{{prop1}}{{#subs2}}{{prop2}}{{/subs2}}{{prop1}}{{/subs1}}"
		, "a12ab34b"
		);
	}
	@Test  public void Section_bool_subs() {	// handle prop written after boolean; should not pick up inner prop
		fxt.Init__root
		(	fxt.Make_mock(0).Add_bool_y("bool1").Add_prop("prop2", "2").Add_subs("subs1"
		,		fxt.Make_mock(1).Add_prop("prop1", "11")
		,		fxt.Make_mock(2).Add_prop("prop1", "12")
		));
		fxt.Test__parse
		( "a{{#bool1}}b{{#subs1}}c{{prop1}}d{{/subs1}}e{{/bool1}}f{{prop2}}g"
		, "abc11dc12def2g"
		);
	}
	@Test  public void Section_owner() {
		fxt.Init__root
		(	fxt.Make_mock(0).Add_subs("subs1"
		,		fxt.Make_mock(1).Add_prop("prop1", "a").Add_subs("subs2"
		,			fxt.Make_mock(11).Add_prop("prop2", "1")
		)
		));
		fxt.Test__parse
		( "{{#subs1}}{{#subs2}}{{prop1}}{{prop2}}{{/subs2}}{{/subs1}}"	// prop1 is cited in subs2, but value belongs to subs1
		, "a1"
		);
	}
}
class Mustache_itm_render_fxt {
	private final    Mustache_tkn_parser parser = new Mustache_tkn_parser();
	private final    Mustache_render_ctx ctx = new Mustache_render_ctx();
	private final    Mustache_bfr bfr = Mustache_bfr.New();
	private Mustache_doc_itm__mock root;
	public Mustache_doc_itm__mock Make_mock(int id) {return new Mustache_doc_itm__mock(id);}
	public void Init__root(Mustache_doc_itm__mock v) {this.root = v;}
	public void Test__parse(String src_str, String expd) {
		byte[] src_bry = Bry_.new_a7(src_str);
		Mustache_tkn_itm actl_itm = parser.Parse(src_bry, 0, src_bry.length);
		ctx.Init(root);
		actl_itm.Render(bfr, ctx);
		Tfds.Eq_str_lines(expd, bfr.To_str_and_clear());
	}
}
class Mustache_doc_itm__mock implements Mustache_doc_itm {
	private final    Hash_adp hash_prop = Hash_adp_.New(), hash_bool = Hash_adp_.New(), hash_subs = Hash_adp_.New();
	public Mustache_doc_itm__mock(int id) {this.id = id;}
	public int id;
	public Mustache_doc_itm__mock Add_prop(String key, String val)	{hash_prop.Add(key, Bry_.new_u8(val)); return this;}
	public Mustache_doc_itm__mock Add_bool_y(String key)			{hash_bool.Add(key, Bool_obj_ref.y_()); return this;}
	public Mustache_doc_itm__mock Add_bool_n(String key)			{hash_bool.Add(key, Bool_obj_ref.n_()); return this;}
	public Mustache_doc_itm__mock Add_subs(String key, Mustache_doc_itm__mock... ary)	{hash_subs.Add(key, ary); return this;}
	public boolean Mustache__write(String key, Mustache_bfr bfr) {
		byte[] rv = (byte[])hash_prop.Get_by(key);
		if (rv == null) return false;
		bfr.Add_bry(rv);
		return true;
	}
	public Mustache_doc_itm[] Mustache__subs(String key) {
		Object rv = hash_bool.Get_by(key);
		if (rv != null) {
			boolean bool_val = ((Bool_obj_ref)rv).Val();
			return bool_val ? Mustache_doc_itm_.Ary__bool__y : Mustache_doc_itm_.Ary__bool__n;
		}
		return (Mustache_doc_itm__mock[])hash_subs.Get_by(key);
	}
}
