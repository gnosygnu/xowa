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
package gplx.gfml; import gplx.*;
import org.junit.*;
public class z162_ndeHdrs_err_tst {
	GfmlParse_fxt fx = GfmlParse_fxt.new_();
	@Before public void setup() {
		fx.ini_RootLxr_Add
			(	GfmlDocLxrs.NdeHeader_lxr()
			);
	}
	@Test public void NotNamed() {
		fx.tst_Err(":", UsrMsg_mok.new_(GfmlUsrMsgs.fail_DatTkn_notFound()));
	}
	@Test public void Dangling() {
		fx.tst_Err("a{", UsrMsg_mok.new_(GfmlUsrMsgs.fail_Frame_danglingBgn()));
	}
}
