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
package gplx.xowa.apps.apis.xowa.wikis; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.apis.*; import gplx.xowa.apps.apis.xowa.*;
public class Xoapi_hdump implements Gfo_invk {		
	public boolean				Read_preferred()		{return read_preferred;}	private boolean read_preferred = true;
	public Hdump_html_mode	Html_mode()				{return html_mode;}			private Hdump_html_mode html_mode = Hdump_html_mode.Shown;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk__read_preferred))	 	return Yn.To_str(read_preferred);
		else if	(ctx.Match(k, Invk__read_preferred_))		read_preferred = m.ReadYn("v");
		else if	(ctx.Match(k, Invk__html_mode))				return html_mode.Key();
		else if	(ctx.Match(k, Invk__html_mode_))			html_mode = Hdump_html_mode.Parse(m.ReadStr("v"));
		else if	(ctx.Match(k, Invk__html_mode_list)) 		return Hdump_html_mode.Opt_list();
		return this;
	}
	private static final String
	  Invk__read_preferred	= "read_preferred"	, Invk__read_preferred_ = "read_preferred_"
	, Invk__html_mode		= "html_mode"		, Invk__html_mode_ = "html_mode_", Invk__html_mode_list = "html_mode_list"
	;
}
