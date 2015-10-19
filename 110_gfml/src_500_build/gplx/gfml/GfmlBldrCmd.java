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
public interface GfmlBldrCmd {
	String Key();
	void Exec(GfmlBldr bldr, GfmlTkn tkn);
}
class GfmlBldrCmd_ {
	public static final GfmlBldrCmd Null = new GfmlBldrCmd_null();
}
class GfmlBldrCmd_null implements GfmlBldrCmd {
	public String Key() {return "gfml.nullCmd";}
	public void Exec(GfmlBldr bldr, GfmlTkn tkn) {}
}
class GfmlBldrCmdRegy {
	public void Add(GfmlBldrCmd cmd) {hash.Add(cmd.Key(), cmd);}
	public GfmlBldrCmd GetOrFail(String key) {return (GfmlBldrCmd)hash.Get_by_or_fail(key);}
	Hash_adp hash = Hash_adp_.new_();
	public static GfmlBldrCmdRegy new_() {
		GfmlBldrCmdRegy rv = new GfmlBldrCmdRegy();
		rv.Add(GfmlBldrCmd_elemKey_set.Instance);
		rv.Add(GfmlBldrCmd_dataTkn_set.Instance);
		return rv;
	}	GfmlBldrCmdRegy() {}
}
