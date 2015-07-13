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
public class GfmlDataRdr extends GfmlDataRdr_base {
	public static DataRdr raw_root_(String raw) {
		GfmlDoc gdoc = GfmlDoc_.parse_any_eol_(raw);
		GfmlDataRdr rv = new GfmlDataRdr();
		rv.Parse_set(true);
		rv.SetNode(gdoc.RootNde().SubHnds().Get_at(0));
		return rv;
	}
	public static DataRdr raw_list_(String raw) {
		GfmlDoc gdoc = GfmlDoc_.parse_any_eol_(raw);
		GfmlDataRdr rv = new GfmlDataRdr();
		rv.SetNode(gdoc.RootNde());
		return rv;
	}
	public static GfmlDataRdr nde_(GfmlNde nde) {
		GfmlDataRdr rv = new GfmlDataRdr();
		rv.SetNode(nde);
		return rv;
	}
	public static DataRdr wtr_(DataWtr wtr) {return raw_root_(wtr.XtoStr());}
	@Override public SrlMgr SrlMgr_new(Object o) {return new GfmlDataRdr();}
	@gplx.Internal protected GfmlDataRdr() {
		this.Parse_set(true);
	}
}
class GfmlDataRdrNde extends GfmlDataRdr_base {
	public GfmlDataRdrNde(GfmlNde current) {SetNode(current);}
	@Override public SrlMgr SrlMgr_new(Object o) {return new GfmlDataRdrNde(null);}
}
