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
package gplx.xowa.users; import gplx.*; import gplx.xowa.*;
public class Xou_log_mgr implements GfoInvkAble {
	public boolean Log_redlinks() {return log_redlinks;} private boolean log_redlinks;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_log_redlinks))			return Yn.Xto_str(log_redlinks);
		else if	(ctx.Match(k, Invk_log_redlinks_))			log_redlinks = m.ReadYn("v");
		return this;
	}
	public static final String 
	  Invk_log_redlinks = "log_redlinks", Invk_log_redlinks_ = "log_redlinks_"
	;
}
