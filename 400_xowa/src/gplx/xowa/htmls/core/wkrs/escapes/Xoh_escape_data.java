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
package gplx.xowa.htmls.core.wkrs.escapes; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.docs.*;
import gplx.xowa.htmls.core.hzips.*;
public class Xoh_escape_data implements Gfh_doc_wkr {
	private final Xoh_hdoc_wkr wkr;		
	public Xoh_escape_data(Xoh_hdoc_wkr wkr, byte hook_byte) {this.wkr = wkr; this.hook = Bry_.New_by_byte(hook_byte);}
	public byte[] Hook() {return hook;} private final byte[] hook;	// NOTE: bry with 1 member which is hook to be escaped; EX: "1" or "2" or "27"
	public int Parse(byte[] src, int src_bgn, int src_end, int pos) {
		int rv = pos + 1;
		wkr.On_escape(this);
		return rv;
	}
}
