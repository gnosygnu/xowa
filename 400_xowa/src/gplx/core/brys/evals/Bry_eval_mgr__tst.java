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
package gplx.core.brys.evals; import gplx.*; import gplx.core.*; import gplx.core.brys.*;
import org.junit.*; import gplx.core.tests.*;
public class Bry_eval_mgr__tst {
	private final    Bry_eval_mgr__fxt fxt = new Bry_eval_mgr__fxt();
	@Test 	public void Text()				{fxt.Test__eval("abc"												, "abc");}
	@Test 	public void Args_0()			{fxt.Test__eval("abc~{test}xyz"										, "abctestxyz");}
	@Test 	public void Args_n()			{fxt.Test__eval("abc~{concat|d|e|f}xyz"								, "abcdefxyz");}
	@Test 	public void Recur_1()			{fxt.Test__eval("abc~{~{test}}xyz"									, "abctestxyz");}
	@Test 	public void Recur_2()			{fxt.Test__eval("abc~{t~{concat|e|s}t}xyz"							, "abctestxyz");}
	@Test 	public void Grp_end()			{fxt.Test__eval("a}b"												, "a}b");}
	@Test 	public void Escape()			{fxt.Test__eval("a~~b"												, "a~b");}
	// @Test 	public void Eos()				{fxt.Test__eval("a~"												, "a~");}
}
class Bry_eval_mgr__fxt {
	private final    Bry_eval_mgr mgr = Bry_eval_mgr.Dflt().Add_many(new Bry_eval_wkr__test(), new Bry_eval_wkr__concat());
	public Bry_eval_mgr__fxt Test__eval(String raw, String expd) {
		Gftest.Eq__bry(Bry_.new_u8(expd), mgr.Eval(Bry_.new_u8(raw)));
		return this;
	}
}
class Bry_eval_wkr__test implements Bry_eval_wkr {
	public String Key() {return "test";}
	public void Resolve(Bry_bfr rv, byte[] src, int src_bgn, int src_end) {
		rv.Add_str_a7("test");
	}
}
class Bry_eval_wkr__concat implements Bry_eval_wkr {
	public String Key() {return "concat";}
	public void Resolve(Bry_bfr rv, byte[] src, int src_bgn, int src_end) {
		byte[][] ary = Bry_split_.Split(src, src_bgn, src_end, Byte_ascii.Pipe, false);
		for (byte[] itm : ary) {
			rv.Add(itm);
		}
 		}
}