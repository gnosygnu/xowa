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
package gplx.xowa.bldrs; import gplx.*; import gplx.xowa.*;
import gplx.xowa.bldrs.wkrs.*;
public class Xob_cmd_regy {
	private final    Ordered_hash regy = Ordered_hash_.New();
	public Xob_cmd Get_or_null(String key) {return (Xob_cmd)regy.Get_by(key);}
	public void Add_many(Xob_cmd... ary) {
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			Xob_cmd cmd = ary[i];
			regy.Add(cmd.Cmd_key(), cmd);
		}
	}
}
