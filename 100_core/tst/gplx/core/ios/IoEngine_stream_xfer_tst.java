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
public class IoEngine_stream_xfer_tst {
	@Before public void setup() {
		srcEngine = IoEngine_memory.new_("mock1");
		trgEngine = IoEngine_memory.new_("mock2");
		IoEnginePool.Instance.Add_if_dupe_use_nth(srcEngine); IoEnginePool.Instance.Add_if_dupe_use_nth(trgEngine);
		IoUrlInfoRegy.Instance.Reg(IoUrlInfo_.mem_("mem1/", srcEngine.Key()));
		IoUrlInfoRegy.Instance.Reg(IoUrlInfo_.mem_("mem2/", trgEngine.Key()));
		srcDir = Io_url_.mem_dir_("mem1/dir"); trgDir = Io_url_.mem_dir_("mem2/dir");
	}
	@Test  public void TransferBetween() {
		Io_url srcPath = srcDir.GenSubFil("fil.txt");
		Io_url trgPath = trgDir.GenSubFil("fil.txt");
		tst_TransferStreams(srcEngine, srcPath, trgEngine, trgPath);
	}
	void tst_TransferStreams(IoEngine srcEngine, Io_url srcPath, IoEngine trgEngine, Io_url trgPath) {
		srcEngine.SaveFilText_api(IoEngine_xrg_saveFilStr.new_(srcPath, "test1"));
		trgEngine.DeleteFil_api(IoEngine_xrg_deleteFil.new_(trgPath));	// make sure file is deleted
		fx.tst_ExistsPaths(true, srcPath);
		fx.tst_ExistsPaths(false, trgPath);

		IoEngineUtl utl = IoEngineUtl.new_();
		utl.BufferLength_set(4);
		utl.XferFil(srcEngine, IoEngine_xrg_xferFil.copy_(srcPath, trgPath));
		fx.tst_ExistsPaths(true, srcPath, trgPath);
		fx.tst_LoadFilStr(trgPath, "test1");
	}
	IoEngineFxt fx = IoEngineFxt.new_();
	Io_url srcDir, trgDir;
	IoEngine srcEngine, trgEngine;
}
