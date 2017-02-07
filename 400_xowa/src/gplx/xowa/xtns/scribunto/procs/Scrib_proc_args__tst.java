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
package gplx.xowa.xtns.scribunto.procs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*;
import org.junit.*; import gplx.core.tests.*; 
public class Scrib_proc_args__tst {
	private final    Scrib_proc_args__fxt fxt = new Scrib_proc_args__fxt();
	@Test   public void Pull_kv_ary__basic() {	// PURPOSE.assert:
		fxt.Init(Keyval_.int_(1, Keyval_.Ary(Keyval_.int_(1, "a"), Keyval_.int_(2, "b"), Keyval_.int_(3, "c"))));
		fxt.Test__pull_kv_ary(0, Keyval_.int_(1, "a"), Keyval_.int_(2, "b"), Keyval_.int_(3, "c"));
	}
	@Test   public void Pull_kv_ary__gaps() {	// PURPOSE.fix: gaps cause null-ref; PAGE:en.w:Shalkar_District DATE:2016-09-12
		fxt.Init(Keyval_.int_(1, Keyval_.Ary(Keyval_.int_(1, "a"), Keyval_.int_(3, "c"), Keyval_.int_(5, "e"))));
		fxt.Test__pull_kv_ary(0, Keyval_.int_(1, "a"), Keyval_.int_(2, null), Keyval_.int_(3, "c"), Keyval_.int_(4, null), Keyval_.int_(5, "e"));
	}
	@Test   public void Pull_kv_ary__gaps__many() {	// PURPOSE.assert:
		fxt.Init(Keyval_.int_(1, Keyval_.Ary(Keyval_.int_(1, "a"), Keyval_.int_(4, "d"), Keyval_.int_(5, "e"), Keyval_.int_(8, "h"))));
		fxt.Test__pull_kv_ary(0
		, Keyval_.int_(1, "a"), Keyval_.int_(2, null), Keyval_.int_(3, null), Keyval_.int_(4, "d"), Keyval_.int_(5, "e")
		, Keyval_.int_(6, null), Keyval_.int_(7, null), Keyval_.int_(8, "h")
		);
	}
}
class Scrib_proc_args__fxt {
	private Scrib_proc_args args;
	public void Init(Keyval... ary) {this.args = new Scrib_proc_args(ary);}
	public void Test__pull_kv_ary(int idx, Keyval... expd) {
		Keyval[] actl = args.Pull_kv_ary_safe(idx);			
		Gftest.Eq__str(Keyval_.Ary_to_str(expd), Keyval_.Ary_to_str(actl));
	}
}
