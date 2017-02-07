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
package gplx.xowa.addons.apps.scripts; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*;
import gplx.core.envs.*;
import gplx.core.scripts.*;
public class Xoscript_env {
	private final    Gfo_script_engine engine;
	public Xoscript_env(Gfo_script_engine engine, Io_url root_dir) {
		this.root_dir = root_dir;
		this.engine = engine;
	}
	public Io_url Root_dir() {return root_dir;} private final    Io_url root_dir;
	public void load_script(String file) {
		engine.Load_script(Io_url_.new_fil_(Xoscript_env.Resolve_file(Bool_.N, root_dir, file)));
	}
	public static String Resolve_file(boolean use_file_protocol, Io_url root_dir, String file) {
		String rv = file;

		// resolve relative urls; EX: "./a.js" -> "/xowa/wiki/simple.wikipedia.org/bin/script/a.js"
		if (String_.Has_at_bgn(rv, "./")) {
			// remove "./"
			rv = String_.Mid(rv, 2);

			if (use_file_protocol)
				rv = root_dir.To_http_file_str() + rv;
			else {
				// if fsys_url && wnt, replace "\" with "/"
				if (Op_sys.Cur().Tid_is_wnt())
					rv = String_.Replace(rv, Op_sys.Lnx.Fsys_dir_spr_str(), Op_sys.Wnt.Fsys_dir_spr_str());
				rv = root_dir.Xto_api() + Op_sys.Cur().Fsys_dir_spr_str() + rv;
			}
		}
		return rv;
	}
}
