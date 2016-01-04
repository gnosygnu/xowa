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
package gplx.xowa.bldrs.cmds.diffs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*;
public class Xob_diff_build_cmd implements Xob_cmd {
	private final Xob_bldr bldr; private final Xowe_wiki wiki;
	private String prev_url, curr_url, diff_url; private int commit_interval;
	public Xob_diff_build_cmd(Xob_bldr bldr, Xowe_wiki wiki) {this.bldr = bldr; this.wiki = wiki;}
	public String Cmd_key()		{return Xob_cmd_keys.Key_diff_build;}
	public void Cmd_run() {
		new Xob_diff_build_wkr(bldr, wiki, prev_url, curr_url, diff_url, commit_interval).Exec();
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk__prev_url_))				prev_url = m.ReadStr("v");
		else if	(ctx.Match(k, Invk__curr_url_))				curr_url = m.ReadStr("v");
		else if	(ctx.Match(k, Invk__diff_url_))				diff_url = m.ReadStr("v");
		else if	(ctx.Match(k, Invk__commit_interval_))		commit_interval = m.ReadInt("v");
		else												return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	public void Cmd_init(Xob_bldr bldr) {}
	public void Cmd_bgn(Xob_bldr bldr) {}
	public void Cmd_end() {}
	public void Cmd_term() {}
	private static final String Invk__prev_url_ = "prev_url_", Invk__curr_url_ = "curr_url_", Invk__diff_url_ = "diff_url_", Invk__commit_interval_ = "commit_interval_";
}
