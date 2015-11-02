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
package gplx.core.ios; import gplx.*; import gplx.core.*;
import org.junit.*;
public class IoEngine_xrg_recycleFil_tst {
	@Before public void setup() {
		IoEngine_.Mem_init_();
	}
	@Test  public void GenRecycleUrl() {
		tst_GenRecycleUrl(recycle_(), Io_url_.mem_fil_("mem/z_trash/20100102/gplx.images;115559123;;fil.txt"));
		tst_GenRecycleUrl(recycle_().Uuid_include_(), Io_url_.mem_fil_("mem/z_trash/20100102/gplx.images;115559123;467ffb41-cdfe-402f-b22b-be855425784b;fil.txt"));
	}
	IoEngine_xrg_recycleFil recycle_() {return IoEngine_xrg_recycleFil.gplx_(Io_url_.mem_fil_("mem/dir/fil.txt")).AppName_("gplx.images").Uuid_(Guid_adp_.parse("467ffb41-cdfe-402f-b22b-be855425784b")).Time_(DateAdp_.parse_gplx("20100102_115559123"));}
	void tst_GenRecycleUrl(IoEngine_xrg_recycleFil xrg, Io_url expd) {
		Tfds.Eq(expd, xrg.RecycleUrl());
	}
}
