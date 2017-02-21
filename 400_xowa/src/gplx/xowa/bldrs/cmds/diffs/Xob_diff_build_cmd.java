/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.bldrs.cmds.diffs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*;
import gplx.xowa.bldrs.wkrs.*;
public class Xob_diff_build_cmd implements Xob_cmd {
	private final    Xob_bldr bldr; private final    Xowe_wiki wiki;
	private String prev_url, curr_url, diff_url; private int commit_interval;
	private int[] db_ids = Int_.Ary_empty; private String bld_name = "all";
	public Xob_diff_build_cmd(Xob_bldr bldr, Xowe_wiki wiki) {this.bldr = bldr; this.wiki = wiki;}
	public String Cmd_key()		{return Xob_cmd_keys.Key_diff_build;}
	public Xob_cmd Cmd_clone(Xob_bldr bldr, Xowe_wiki wiki) {return null;}
	public void Cmd_run() {
		new Xob_diff_build_wkr(bldr, wiki, prev_url, curr_url, diff_url, commit_interval, new Xowd_tbl_mapr(bld_name, db_ids)).Exec();
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk__prev_url_))				prev_url = m.ReadStr("v");
		else if	(ctx.Match(k, Invk__curr_url_))				curr_url = m.ReadStr("v");
		else if	(ctx.Match(k, Invk__diff_url_))				diff_url = m.ReadStr("v");
		else if	(ctx.Match(k, Invk__commit_interval_))		commit_interval = m.ReadInt("v");
		else if	(ctx.Match(k, Invk__db_ids_))				db_ids = Int_.Ary_parse(m.ReadStr("v"), "|");
		else if	(ctx.Match(k, Invk__bld_name_))				bld_name = m.ReadStr("v");
		else												return Gfo_invk_.Rv_unhandled;
		return this;
	}
	public void Cmd_init(Xob_bldr bldr) {}
	public void Cmd_bgn(Xob_bldr bldr) {}
	public void Cmd_end() {}
	public void Cmd_term() {}
	private static final String Invk__prev_url_ = "prev_url_", Invk__curr_url_ = "curr_url_", Invk__diff_url_ = "diff_url_"
	, Invk__commit_interval_ = "commit_interval_", Invk__db_ids_ = "db_ids_", Invk__bld_name_ = "bld_name_";
}
