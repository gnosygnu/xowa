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
package gplx.langs.gfs; import gplx.*; import gplx.langs.*;
import org.junit.*;
public class GfsCore_tst {
	@Before public void setup() {
		core = GfsCore.new_();
		core.AddObj(String_.Gfs, "String_");
		core.AddObj(Int_.Gfs, "Int_");
	}	GfsCore core;
	@Test  public void Basic() {					// String_.Len('abc') >> 3
		tst_Msg
			(	msg_("String_").Subs_
			(		msg_("Len").Add("v", "abc"))
			,	3);
	}
	@Test  public void PrimitiveConversion() {		// String_.Len('abc').Add(-3) >> 0
		tst_Msg
			(	msg_("String_").Subs_
			(		msg_("Len").Add("v", "abc").Subs_
			(			msg_("Add").Add("operand", -3))
			)
			,	0);
	}
//		@Test  public void Fail_notFound() {			// String_.DoesNotExists
//			tst_Err
//				(	msg_("help").Add("", "String_.DoesNotExist")
//				,	GfsHelp.Err_Unhandled("String_", "DoesNotExist"));
//		}
	@Test  public void Cmd() {						// cmd
		core.AddCmd(new GfsTest_cmd(), "testCmd");
		tst_Msg
			(	msg_("testCmd").Add("s", "pass")
			,	"pass");
	}
	@Test  public void EmptyMsg() {
		tst_Msg
			(	msg_("")
			,	GfoInvkAble_.Rv_unhandled);
	}
//		@Test  public void Fail_argMissing() {			// String_.Len()
//			tst_String__Len_Err(msg_("Len"), GfsCtx.Err_KeyNotFound("v", "<<EMPTY>>"));
//		}
//		@Test  public void Fail_argWrongKey() {			// String_.Len(badKey='abc')
//			tst_String__Len_Err(msg_("Len").Add("badKey", "abc"), GfsCtx.Err_KeyNotFound("v", "badKey;"));
//		}
//		@Test  public void Fail_argExtraKey() {			// String_.Len(v='abc' extraKey=1)
//			tst_String__Len_Err(msg_("Len").Add("v", "abc").Add("extraKey", 1), GfsCtx.Err_KeyNotFound("v", "badKey;"));
//		}
	@Test  public void Add_obj_deep() {			// String_.Len(badKey='abc')
		GfsCore_tst_nest obj1 = GfsCore_tst_nest.new_("1", "val1");
		GfsCore_tst_nest obj1_1 = GfsCore_tst_nest.new_("1_1", "val2");
		core.AddObj(obj1, "1");
		core.AddDeep(obj1_1, "1", "1_1");

		GfoMsg root = GfoMsg_.root_("1", "1_1", GfsCore_tst_nest.Prop2);
		Object actl = core.ExecOne(GfsCtx.Instance, root);
		Tfds.Eq("val2", actl);
	}
	void tst_String__Len_Err(GfoMsg m, Err expd) {
		tst_Err(msg_("String_").Subs_(m), expd);
	}
	void tst_Err(GfoMsg msg, Err expd) {
		GfoMsg root = msg;
		GfsCtx ctx = GfsCtx.new_();
		try {
			core.ExecOne(ctx, root);
			Tfds.Fail_expdError();
		}
		catch (Exception e) {
			Tfds.Eq_err(expd, e);
		}
	}
	GfoMsg msg_(String k) {return GfoMsg_.new_cast_(k);}
	void tst_Msg(GfoMsg msg, Object expd) {
		GfsCtx ctx = GfsCtx.new_();
		Object actl = core.ExecOne(ctx, msg);
		Tfds.Eq(expd, actl);
	}
}
class GfsCore_tst_nest implements GfoInvkAble, GfoInvkCmdMgrOwner {
	public GfoInvkCmdMgr InvkMgr() {return invkMgr;} GfoInvkCmdMgr invkMgr = GfoInvkCmdMgr.new_();
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Prop1)) {return prop1;}
		else if	(ctx.Match(k, Prop2)) {return prop2;}
		else if	(ctx.Match(k, prop1)) {return this;}
		else	return invkMgr.Invk(ctx, ikey, k, m, this);
	}	public static final String Prop1 = "Prop1", Prop2 = "Prop2";
	String prop1, prop2;
        public static GfsCore_tst_nest new_(String prop1, String prop2) {
		GfsCore_tst_nest rv = new GfsCore_tst_nest();
		rv.prop1 = prop1; rv.prop2 = prop2;
		return rv;
	}	GfsCore_tst_nest() {}
}
class GfsTest_cmd implements GfoInvkAble {
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {return m.ReadStr("s");}
}
