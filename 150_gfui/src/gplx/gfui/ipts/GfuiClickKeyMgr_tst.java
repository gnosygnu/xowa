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
package gplx.gfui.ipts; import gplx.*; import gplx.gfui.*;
import org.junit.*; import gplx.gfui.ipts.*; import gplx.gfui.controls.windows.*;
public class GfuiClickKeyMgr_tst {
	@Test  public void ExtractKeyFromText() {
		tst_ExtractKey("&click", IptKey_.C);
		tst_ExtractKey("&", IptKey_.None);
		tst_ExtractKey("trailing &", IptKey_.None);
		tst_ExtractKey("me & you", IptKey_.None);
	}
	void tst_ExtractKey(String text, IptKey expd) {
		IptKey actl = GfuiWinKeyCmdMgr.ExtractKeyFromText(text);
		Tfds.Eq(expd, actl);
	}
}	
