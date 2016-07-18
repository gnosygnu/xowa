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
package gplx.xowa.htmls.core.wkrs.imgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import org.junit.*; import gplx.core.envs.*; import gplx.xowa.htmls.core.hzips.*;
public class Xoh_img_bare_hzip__tst {
	private final    Xoh_hzip_fxt fxt = new Xoh_hzip_fxt().Init_mode_diff_y_();
	private int prv_os_tid = -1;
	@Before public void init() {
		fxt.Clear();
		prv_os_tid = Op_sys.Cur().Tid();
		Op_sys.Cur_(Op_sys.Drd.Tid());		// force drd mode; needed for img_bare
	}
	@After  public void term() {
		Op_sys.Cur_(prv_os_tid);			// revert back to previous mode; otherwise global Op_sys is set to Drd which will cause other tests to fail (notably tidy)
	}
	@Test   public void Hiero() {
		fxt.Test__bicode
		( "~(!<img style=\"margin: 1px; height: 11px;\" src=\"~X1.png\" title=\"X1 [t]\" alt=\"t\">~"
		, "<img style='margin: 1px; height: 11px;' src='file:///android_asset/xowa/bin/any/xowa/xtns/Wikihiero/img/hiero_X1.png' title='X1 [t]' alt='t'>"
		);
	}
}
