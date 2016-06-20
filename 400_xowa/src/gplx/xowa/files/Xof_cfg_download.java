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
package gplx.xowa.files; import gplx.*; import gplx.xowa.*;
public class Xof_cfg_download implements Gfo_invk {
	public Xof_cfg_download() {
		this.enabled = true;					// CFG: set to false b/c some tests only do parsing [[File:A.png]] and repos are not set up
		this.redownload = Redownload_none;		// CFG: set to none to be as conservative as possible
	}
	public boolean Enabled() {return enabled;} public Xof_cfg_download Enabled_(boolean v) {enabled = v; return this;} private boolean enabled;
	public byte Redownload() {return redownload;} public Xof_cfg_download Redownload_(byte v) {redownload = v; return this;} private byte redownload;

	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_enabled_))			enabled = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_redownload))			return Redownload_to_str_(redownload);
		else if	(ctx.Match(k, Invk_redownload_))		redownload = Redownload_parse_(m.ReadStr("v"));
		else if	(ctx.Match(k, Invk_redownload_toggle))	redownload = redownload == Redownload_none ? Redownload_missing : Redownload_none;
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}	private static final String Invk_enabled_ = "enabled_", Invk_redownload = "redownload", Invk_redownload_ = "redownload_", Invk_redownload_toggle = "redownload_toggle";
	byte Redownload_parse_(String s) {
		if		(String_.Eq(s, "none"))		return Redownload_none;
		else if	(String_.Eq(s, "missing"))	return Redownload_missing;
		else if	(String_.Eq(s, "all"))		return Redownload_all;
		else								throw Err_.new_unhandled(s);
	}
	public static final byte Redownload_none = 0, Redownload_missing = 1, Redownload_all = 2; 
	String Redownload_to_str_(byte v) {
		switch (v) {
			case Redownload_none:		return "none";
			case Redownload_missing:	return "missing";
			case Redownload_all:		return "all";
			default:					throw Err_.new_unhandled(v);
		}
	}
}
