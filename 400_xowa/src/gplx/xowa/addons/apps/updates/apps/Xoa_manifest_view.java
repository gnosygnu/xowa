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
package gplx.xowa.addons.apps.updates.apps; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.updates.*;
public class Xoa_manifest_view {
	private final    Xoa_manifest_wkr wkr;
	public Xoa_manifest_view(String manifest_url) {
					this.wkr = new Xoa_manifest_wkr(this);
		wkr.Init(manifest_url);
	}
			public void Append(String s) {
					}

	public static void Run(String manifest_url) {
		if (!gplx.core.envs.Op_sys.Cur().Tid_is_osx())
			new Xoa_manifest_view(manifest_url);
	}
}
