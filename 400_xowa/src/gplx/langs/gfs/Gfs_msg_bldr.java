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
public class Gfs_msg_bldr implements GfoMsgParser {
	private final    Gfs_parser parser = new Gfs_parser();
	public GfoMsg ParseToMsg(String s) {return Bld(s);}
	public GfoMsg Bld(String src) {return Bld(Bry_.new_u8(src));}
	public GfoMsg Bld(byte[] src) {
		Gfs_nde nde = parser.Parse(src);
		return Bld_msg(src, nde);
	}
	private GfoMsg Bld_msg(byte[] src, Gfs_nde nde) {
		boolean op_is_assign = (nde.Op_tid() == Gfs_nde.Op_tid_assign);
		String name = String_.new_u8(nde.Name_bry(src));
		if (op_is_assign) name += Tkn_mutator;
		GfoMsg rv = GfoMsg_.new_parse_(name);
		int len = nde.Atrs_len();
		for (int i = 0; i < len; i++) {
			Gfs_nde atr = nde.Atrs_get_at(i);
			rv.Add("", String_.new_u8(atr.Name_bry(src)));
		}
		len = nde.Subs_len();
		for (int i = 0; i < len; i++) {
			Gfs_nde sub = nde.Subs_get_at(i);
			if (op_is_assign)	// NOTE: for now (a) assignss cannot be nested; EX: "a.b = c;" is okay but "a.b = c.d;" is not
				rv.Add("", Bld_msg(src, sub).Key());
			else
				rv.Subs_add(Bld_msg(src, sub));
		}			
		return rv;
	}
	public static final    Gfs_msg_bldr Instance = new Gfs_msg_bldr(); Gfs_msg_bldr() {}	// TS.static
	public static final String Tkn_mutator = "_";
}
