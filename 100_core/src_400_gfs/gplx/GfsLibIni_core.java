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
public class GfsLibIni_core implements GfsLibIni {
	public void Ini(GfsCore core) {
		core.AddCmd(GfsCoreHelp.new_(core), "help");
		core.AddObj(String_.Gfs, "String_");
		core.AddObj(Int_.Gfs, "Int_");
		core.AddObj(DateAdp_.Gfs, "Date_");
		core.AddObj(RandomAdp_.Gfs, "RandomAdp_");
		core.AddObj(GfoTemplateFactory._, "factory");
		core.AddObj(GfoRegy._, "GfoRegy_");
		core.AddObj(GfsCore._, "GfsCore_");
		core.AddObj(gplx.ios.IoUrlInfoRegy._, "IoUrlInfoRegy_");
		core.AddObj(gplx.ios.IoUrlTypeRegy._, "IoUrlTypeRegy_");

		GfoRegy._.Parsers().Add("Io_url", Io_url_.Parser);
	}
        public static final GfsLibIni_core _ = new GfsLibIni_core(); GfsLibIni_core() {}
}
