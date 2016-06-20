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
package gplx.xowa.apps.cfgs.gui; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.cfgs.*;
public class Xocfg_html implements Gfo_invk {
	public Xocfg_html() {
		this.content_editable = false;		// CFG: default to false for general user
	}
	public boolean Content_editable() {return content_editable;} public Xocfg_html Content_editable_(boolean v) {content_editable = v; return this;} private boolean content_editable;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_content_editable))		return Yn.To_str(content_editable);
		else if	(ctx.Match(k, Invk_content_editable_))		content_editable = m.ReadYn_toggle("v", content_editable);
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String Invk_content_editable = "content_editable", Invk_content_editable_ = "content_editable_";
}
