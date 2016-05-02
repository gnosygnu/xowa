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
package gplx.xowa.guis.cbks.swts; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*; import gplx.xowa.guis.cbks.*;
import org.junit.*; import gplx.core.tests.*;
import gplx.core.gfobjs.*;
public class Gfobj_wtr__json__swt_tst {
	private final    Gfobj_wtr__json__swt_fxt fxt = new Gfobj_wtr__json__swt_fxt();
	@Test   public void Json_proc() {
		fxt.Test__json_proc 
		( "proc_name"
		, fxt.Make__nde
		(   fxt.Make__fld_str	("k1", "v1")
		,   fxt.Make__fld_long	("k2", 2)
		,   fxt.Make__fld_int	("k3", 3)
		)
		, "return proc_name('{\"k1\":\"v1\",\"k2\":2,\"k3\":3}');"
		);
	}
}
class Gfobj_wtr__json__swt_fxt extends Gfobj_wtr__json_fxt {		public Gfobj_wtr__json__swt_fxt Test__json_proc() {return this;}
	public void Test__json_proc(String proc_name, Gfobj_nde root, String expd) {
		Gfobj_wtr__json__swt wtr = new Gfobj_wtr__json__swt();
		String actl = wtr.Write_as_func(proc_name, root);
		Gftest.Eq__str(expd, actl, "json_write");
	}
}
