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
public class JarAdp_ {
	public static DateAdp ModifiedTime_type(Class<?> type) {if (type == null) throw Err_.null_("type");
		Io_url url = Url_type(type);
		return Io_mgr.I.QueryFil(url).ModifiedTime();
	}
	public static Io_url Url_type(Class<?> type) {if (type == null) throw Err_.null_("type");
				String codeBase = type.getProtectionDomain().getCodeSource().getLocation().getPath();
		if (Op_sys.Cur().Tid_is_wnt())
			codeBase = String_.Mid(codeBase, 1);	// codebase always starts with /; remove for wnt 
		codeBase = String_.Replace(codeBase, "/", Op_sys.Cur().Fsys_dir_spr_str());	// java always returns DirSpr as /; change to Env_.DirSpr to handle windows
		try   {codeBase = java.net.URLDecoder.decode(codeBase, "UTF-8");}
		catch (java.io.UnsupportedEncodingException e) {Err_.Noop(e);}
				return Io_url_.new_fil_(codeBase);
	}
}
