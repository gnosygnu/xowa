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
import org.junit.*; import gplx.core.tests.*; import gplx.core.envs.*;
public class Io_url__tst {
	@Before public void init() {fxt.Clear();} private final    Io_url__fxt fxt = new Io_url__fxt();
	@Test   public void Basic__lnx()	{fxt.Test__New__http_or_null(Bool_.N, "file:///C:/a.txt", "C:/a.txt");}
	@Test   public void Basic__wnt()	{fxt.Test__New__http_or_null(Bool_.Y, "file:///C:/a.txt", "C:\\a.txt");}
	@Test   public void Null()			{fxt.Test__New__http_or_null(Bool_.N, "C:/a.txt", null);}
}
class Io_url__fxt {
	public void Clear() {Io_mgr.Instance.InitEngine_mem();}
	public void Test__New__http_or_null(boolean os_is_wnt, String raw, String expd) {
		Op_sys.Cur_(os_is_wnt ? Op_sys.Tid_wnt : Op_sys.Tid_lnx);
		Gftest.Eq__obj_or_null(expd, Io_url_.New__http_or_null(raw));
	}
}
