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
package gplx.xowa.html.skins; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*;
public class Xoh_skin_itm implements GfoInvkAble {
	private final Bry_fmtr fmtr = Bry_fmtr.new_();
	public Xoh_skin_itm(String key, String fmt) {this.key = key; fmtr.Fmt_(fmt);}
	public String Key() {return key;} private final String key;
	public void Fmt_(String v) {fmtr.Fmt_(v);}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_fmt))		return String_.new_u8(fmtr.Fmt());
		else if	(ctx.Match(k, Invk_fmt_))		fmtr.Fmt_(m.ReadStr("v"));
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	private static final String
	  Invk_fmt = "fmt", Invk_fmt_ = "fmt_"
	;
}
