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
package gplx.xowa.apis.xowa.envs; import gplx.*; import gplx.xowa.*; import gplx.xowa.apis.*; import gplx.xowa.apis.xowa.*;
public class Xoapi_env implements GfoInvkAble {
	public void Init_by_kit(Xoa_app app) {}
	public String Version_previous() {return version_previous;} private String version_previous = "";
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_version_previous)) 						return version_previous;
		else if	(ctx.Match(k, Invk_version_previous_)) 						version_previous = m.ReadStr("v");
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	private static final String Invk_version_previous = "version_previous", Invk_version_previous_ = "version_previous_";
}
