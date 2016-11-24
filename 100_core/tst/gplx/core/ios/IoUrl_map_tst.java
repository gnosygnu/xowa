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
public class IoUrl_map_tst {
	IoUrlFxt fx = IoUrlFxt.new_();
	@Test  public void Xto_api() {
		IoUrlInfo inf = IoUrlInfo_.alias_("tst:\\", "C:\\tst\\", IoEngine_.SysKey);
		fx.tst_Xto_api(Io_url_.new_inf_("tst:\\dir\\fil.txt", inf), "C:\\tst\\dir\\fil.txt");
		fx.tst_Xto_api(Io_url_.new_inf_("tst:\\dir\\", inf), "C:\\tst\\dir");	// no trailing \
	}		
	@Test  public void Xto_api_wce() {			
		IoUrlInfo inf = IoUrlInfo_.alias_("wce:\\", "\\SD Card\\", IoEngine_.SysKey); 
		fx.tst_Xto_api(Io_url_.new_inf_("wce:\\dir\\", inf), "\\SD Card\\dir");
	}
}
