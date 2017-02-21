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
package gplx.langs.gfs; import gplx.*; import gplx.langs.*;
import org.junit.*; import gplx.core.strings.*;
public class Gfs_msg_bldr_tst {
	@Before public void init() {fxt.Clear();} Gfs_msg_bldr_fxt fxt = new Gfs_msg_bldr_fxt();
	@Test  public void Basic() {
		fxt.Test_build("a;", fxt.msg_("a"));
	}
	@Test  public void Dot() {
		fxt.Test_build("a.b.c;"
		, fxt.msg_("a").Subs_
		(	fxt.msg_("b").Subs_
		(		fxt.msg_("c")
		)));
	}
	@Test  public void Args() {
		fxt.Test_build("a('b', 'c');", fxt.msg_("a", fxt.kv_("", "b"), fxt.kv_("", "c")));
	}
	@Test  public void Args_num() {
		fxt.Test_build("a(1);", fxt.msg_("a", fxt.kv_("", "1")));
	}
	@Test   public void Assign() {
		fxt.Test_build("a = 'b';", fxt.msg_("a_", fxt.kv_("", "b")));
	}
	@Test   public void Assign_num() {
		fxt.Test_build("a = 1;", fxt.msg_("a_", fxt.kv_("", "1")));
	}
}
class Gfs_msg_bldr_fxt {
	public void Clear() {} String_bldr sb = String_bldr_.new_(); Gfs_msg_bldr msg_bldr = Gfs_msg_bldr.Instance;
	public Keyval kv_(String key, String val) {return Keyval_.new_(key, val);}
	public GfoMsg msg_(String key, Keyval... args) {
		GfoMsg rv = GfoMsg_.new_parse_(key);
		int len = args.length;
		for (int i = 0; i < len; i++) {
			Keyval kv = args[i];
			rv.Add(kv.Key(), kv.Val());
		}
		return rv;
	}
	public void Test_build(String raw, GfoMsg... expd) {
		GfoMsg root = msg_bldr.Bld(raw);		
		Tfds.Eq_str_lines(Xto_str(expd), Xto_str(To_ary(root)));
	}
	GfoMsg[] To_ary(GfoMsg msg) {
		int len = msg.Subs_count();
		GfoMsg[] rv = new GfoMsg[len];
		for (int i = 0; i < len; i++)
			rv[i] = msg.Subs_getAt(i);
		return rv;
	}
	String Xto_str(GfoMsg[] ary) {
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			if (i != 0) sb.Add_char_crlf();
			sb.Add(ary[i].To_str());
		}
		return sb.To_str_and_clear();
	}
}
