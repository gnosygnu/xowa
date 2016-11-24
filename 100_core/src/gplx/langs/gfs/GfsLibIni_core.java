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
package gplx.langs.gfs; import gplx.*; import gplx.langs.*;
import gplx.core.gfo_regys.*;
public class GfsLibIni_core implements GfsLibIni {
	public void Ini(GfsCore core) {
		core.AddCmd(GfsCoreHelp.new_(core), "help");
		core.AddObj(String_.Gfs, "String_");
		core.AddObj(Int_.Gfs, "Int_");
		core.AddObj(DateAdp_.Gfs, "Date_");
		core.AddObj(RandomAdp_.Gfs, "RandomAdp_");
		core.AddObj(GfoTemplateFactory.Instance, "factory");
		core.AddObj(GfoRegy.Instance, "GfoRegy_");
		core.AddObj(GfsCore.Instance, "GfsCore_");
		core.AddObj(gplx.core.ios.IoUrlInfoRegy.Instance, "IoUrlInfoRegy_");
		core.AddObj(gplx.core.ios.IoUrlTypeRegy.Instance, "IoUrlTypeRegy_");

		GfoRegy.Instance.Parsers().Add("Io_url", Io_url_.Parser);
	}
        public static final GfsLibIni_core Instance = new GfsLibIni_core(); GfsLibIni_core() {}
}
