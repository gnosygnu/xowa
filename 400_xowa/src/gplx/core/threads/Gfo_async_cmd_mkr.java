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
package gplx.core.threads; import gplx.*; import gplx.core.*;
class Gfo_async_cmd_mkr {
//		private Gfo_async_cmd_itm[] free = Gfo_async_cmd_itm.Ary_empty, used = Gfo_async_cmd_itm.Ary_empty;
//		private int free_bgn = 0, free_end = 0, ary_len = 0;
//		public void Resize(int v) {
//			free = (Gfo_async_cmd_itm[])Array_.Resize(free, v);
//			used = (Gfo_async_cmd_itm[])Array_.Resize(used, v);
//			ary_len = v;
//		}
	public Gfo_async_cmd_itm Get(Gfo_invk invk, String invk_key, Object... args) {
		Gfo_async_cmd_itm rv = new Gfo_async_cmd_itm();
		rv.Init(invk, invk_key, args);
		return rv;
	}
	public void Rls(Gfo_async_cmd_itm cmd) {
	}
}
