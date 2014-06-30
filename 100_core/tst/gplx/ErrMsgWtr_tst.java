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
public class ErrMsgWtr_tst {
	@Test  public void Rethrow() {
		tst(new RethrowExample()
						,  "0	failed run <gplx.app>"
			,  "	gplx.ErrMsgWtr_tst$RethrowExample.Run"
			,  "1	failed proc2 <gplx.app>"
			,  "	gplx.ErrMsgWtr_tst$RethrowExample.Proc1"
			,  "2	failed proc1; key=key val=123:0 <gplx.parse>"
			,  "	gplx.ErrMsgWtr_tst$RethrowExample.Proc2"
			,  "-----------------------------------"
			,  "	gplx.ErrMsgWtr_tst.Rethrow(ErrMsgWtr_tst.java:5)"
			,  "	gplx.ErrMsgWtr_tst.tst(ErrMsgWtr_tst.java:24)"
			,  "0	gplx.ErrMsgWtr_tst$RethrowExample.Run(ErrMsgWtr_tst.java:35)"
			,  "1	gplx.ErrMsgWtr_tst$RethrowExample.Proc1(ErrMsgWtr_tst.java:36)"
			,  "2	gplx.ErrMsgWtr_tst$RethrowExample.Proc2(ErrMsgWtr_tst.java:37)"
			,  ""
						);
	}
	void tst(ErrExample o, String... expdAry) {
//			try {o.Run();}
//			catch (Exception exc) {
//				String actlMsg = ErrMsgWtr._.Message_gplx(exc);
//				String[] actlAry = String_.Split(actlMsg, String_.CrLf);
//				Tfds.Eq_ary_str(expdAry, actlAry); //Tfds.Write(String_.CrLf + actlMsg);
//				return;
//			}
//			Tfds.Fail_expdError();
	}
	interface ErrExample {void Run();}
	class RethrowExample implements ErrExample {
		public void Run()	{try {Proc1();} catch(Exception exc) {throw Err_.err_(exc, "failed run");} }
		public void Proc1() {try {Proc2();} catch(Exception exc) {throw Err_.err_(exc, "failed proc2");} }
		public void Proc2() {throw Err_.new_key_("gplx.parse", "failed proc1");} 
	}
	@Test  public void Deep() {
		tst(new DeepExample()
						,  "0	failed proc1; key=key val=123:0 <gplx.parse>"
			,  "	gplx.ErrMsgWtr_tst$DeepExample.Proc2"
			,  "-----------------------------------"
			,  "	gplx.ErrMsgWtr_tst.Deep(ErrMsgWtr_tst.java:40)"
			,  "	gplx.ErrMsgWtr_tst.tst(ErrMsgWtr_tst.java:24)"
			,  "	gplx.ErrMsgWtr_tst$DeepExample.Run(ErrMsgWtr_tst.java:55)"
			,  "	gplx.ErrMsgWtr_tst$DeepExample.Proc1(ErrMsgWtr_tst.java:56)"
			,  "0	gplx.ErrMsgWtr_tst$DeepExample.Proc2(ErrMsgWtr_tst.java:57)"
			,  ""
						);
	}
	class DeepExample implements ErrExample {
		public void Run()	{Proc1();}
		public void Proc1() {Proc2();}
		public void Proc2() {throw Err_.new_key_("gplx.parse", "failed proc1");} 
	}
}
