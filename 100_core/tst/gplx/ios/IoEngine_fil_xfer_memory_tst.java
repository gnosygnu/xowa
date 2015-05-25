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
package gplx.ios; import gplx.*;
import org.junit.*;
public class IoEngine_fil_xfer_memory_tst extends IoEngine_fil_xfer_base {
	@Override protected void setup_hook() {
		root = Io_url_.mem_dir_("mem");
	}	@Override protected IoEngine engine_() {return IoEngine_.Mem_init_();}
	@Override protected Io_url AltRoot() {
		Io_mgr.I.InitEngine_mem_("mem2");
		return Io_url_.mem_dir_("mem2");
	}
}
