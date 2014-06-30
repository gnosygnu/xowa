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
package gplx;
import org.junit.*;
public class ErrMock_tst {	// NOTE: ErrMock_tst name important b/c gplx java code will ignore all stacks with gplx.Err_
	@Before public void setup() {} Err err = null;
	@Test  public void Basic() {
		err = Err_.new_key_("gplx.MockFail", "mock fail");
		try	{throw err;} catch (Exception e) {err = Err.convert_(e);}
		tst_Key("gplx.MockFail").tst_Msg("mock fail").tst_ProcName("gplx.ErrMock_tst.Basic");		
	}
	@Test  public void Args() {
		err = Err_.new_key_("gplx.MockFail", "mock fail").Add("a", 1).Add("b", 2);
		try	{throw err;} catch (Exception e) {err = Err.convert_(e);}
		tst_Arg(0, "a", 1).tst_Arg(1, "b", 2);
	}
//		@Test  public void PrintAll() {
//			String actl = "";
//			try {FailedMethod();}
//			catch (Exception e) {
//				actl = Err_.Message_gplx(e);
//			}
//			Tfds.Eq_ary_str(String_.Split(String_.Concat_lines_crlf(
//				//				"	mock fail <gplx.MockFail>"
//				,"		@a       1"
//				,"		@b       2"
//				,"	gplx.ErrMock_tst.FailedMethod()"
//				,"-----------------------------------"
//				,"	gplx.ErrMock_tst.PrintAll()"
//				,"		c:\\000\\200.dev\\100.gplx\\100.framework\\100.core\\gplx\\tst\\gplx\\errmock_tst.cs(18,0)"
//				,"	gplx.ErrMock_tst.FailedMethod()"
//				,"		c:\\000\\200.dev\\100.gplx\\100.framework\\100.core\\gplx\\tst\\gplx\\errmock_tst.cs(37,0)"), String_.NewLine),
//				//				String_.Split(actl, String_.CrLf));
//		}
	void FailedMethod() {
		throw Err_.new_key_("gplx.MockFail", "mock fail").Add("a", 1).Add("b", 2);
	}

	ErrMock_tst tst_Key(String expd) {Tfds.Eq(expd, err.Key()); return this;}
	ErrMock_tst tst_Msg(String expd) {Tfds.Eq(expd, err.Hdr()); return this;}
	ErrMock_tst tst_ProcName(String expd) {Tfds.Eq(expd, err.Proc().SignatureRaw()); return this;}
	ErrMock_tst tst_Arg(int i, String expdKey, Object expdVal) {
		KeyVal actl = (KeyVal)err.Args().FetchAt(i);
		KeyVal expd = KeyVal_.new_(expdKey, expdVal);
		Tfds.Eq(expd.XtoStr(), actl.XtoStr()); return this;
	}
}
