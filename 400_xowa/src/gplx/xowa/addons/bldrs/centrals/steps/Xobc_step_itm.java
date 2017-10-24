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
package gplx.xowa.addons.bldrs.centrals.steps; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.centrals.*;
import gplx.core.gfobjs.*;
import gplx.xowa.addons.bldrs.centrals.cmds.*;
public class Xobc_step_itm {
	private int cmd_idx = 0;
	private final    Xobc_cmd_itm[] cmds;
	public Xobc_step_itm(int step_id, int step_seqn, Xobc_cmd_itm[] cmds) {
		this.step_id = step_id; this.step_seqn = step_seqn; this.cmds = cmds;
	}
	public int					Step_id() {return step_id;} private final    int step_id;
	public int					Step_seqn() {return step_seqn;} private final    int step_seqn;
	public String				Step_name() {return step_name;} private String step_name;
	public Xobc_cmd_itm			Cmd() {return cmds[cmd_idx];}
	public Xobc_step_itm		Cmd_idx_(int v) {cmd_idx = v; return this;}
	public void					Cmd_idx_next_() {++cmd_idx;} 
	public boolean					Cmd_is_last() {return cmd_idx == cmds.length - 1;}
	public Xobc_cmd_itm			Step_fallback_to(String fallback_id) {
		int fallback_idx = 0;
		int len = cmds.length;
		for (int i = 0; i < len; ++i) {
			Xobc_cmd_itm cmd = cmds[i];
			if (String_.Eq(cmd.Cmd_type(), fallback_id)) {
				fallback_idx = cmd.Cmd_id();
				break;
			}
		}
		cmd_idx = fallback_idx;
		return Cmd();
	}
	public void Step_name_(String v) {this.step_name = v;}
	public void Step_cleanup() {
		int len = cmds.length;
		for (int i = 0; i < len; ++i) {
			cmds[i].Cmd_cleanup();
		}
	}
	public Gfobj_nde Save_to(Gfobj_nde nde) {
		nde.Add_int	("step_id", step_id);
		nde.Add_str	("step_name", step_name);
		this.Cmd().Save_to(nde.New_nde("cmd"));
		return nde;	// fluent
	}		
	
	public static final int Type__wiki_import = 1;
	public static final int Seqn__0 = 0;
}
