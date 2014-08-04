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
public class IoEngine_dir_basic_system_tst extends IoEngine_dir_basic_base {
	@Override protected void setup_hook() {
		root = Tfds.RscDir.GenSubDir_nest("100_core", "ioEngineTest", "_temp");
		IoEngine_xrg_deleteDir.new_(root).Recur_().ReadOnlyFails_off().Exec();
	}	@Override protected IoEngine engine_() {return IoEngine_system.new_();}
	@Test  @Override public void ScanDir() {
		super.ScanDir();
	}
}
