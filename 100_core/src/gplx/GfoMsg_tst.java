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
package gplx;
import org.junit.*; import gplx.langs.gfs.*;
public class GfoMsg_tst {
	@Before public void setup() {
		GfsCore.Instance.AddObj(new Mok(), "Mok");
	}
	@Test  public void Write1() {
		GfoMsg m = GfoMsg_.root_leafArgs_(String_.Ary("a", "b"), Keyval_.new_("int0", 1));
		tst_Msg(m, "a.b:int0='1';");
	}
	@Test  public void Write() {
		Mok mok = new Mok();
		tst_Msg(Gfo_invk_to_str.WriteMsg(mok, Mok.Invk_Cmd0, true, 1, "a"), "Mok.Cmd0:bool0='y' int0='1' str0='a';");
		mok.Int0 = 2;
		mok.Bool0 = true;
		mok.Str0 = "b";
		tst_Msg(Gfo_invk_to_str.ReadMsg(mok, Mok.Invk_Cmd0), "Mok.Cmd0:bool0='y' int0='2' str0='b';");
	}
	void tst_Msg(GfoMsg m, String expd) {Tfds.Eq(expd, m.To_str());}
	class Mok implements Gfo_invk {
		public boolean Bool0;
		public int Int0;
		public String Str0;
		public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
			if	(ctx.Match(k, Invk_Cmd0)) {
				Bool0 = m.ReadBoolOr("bool0", Bool0);
				Int0 = m.ReadIntOr("int0", Int0);
				Str0 = m.ReadStrOr("str0", Str0);
				if (ctx.Deny()) return this;
			}
			return this;
		}	public static final    String Invk_Cmd0 = "Cmd0";
	}
}
