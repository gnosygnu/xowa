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
package gplx.xowa.wikis.metas; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
public class Xow_sys_cfg implements Gfo_invk {
	public Xow_sys_cfg(Xowe_wiki wiki) {}
	public boolean Xowa_cmd_enabled() {return xowa_cmd_enabled;} public Xow_sys_cfg Xowa_cmd_enabled_(boolean v) {xowa_cmd_enabled = v; return this;} private boolean xowa_cmd_enabled;
	public boolean Xowa_proto_enabled() {return xowa_proto_enabled;} public Xow_sys_cfg Xowa_proto_enabled_(boolean v) {xowa_proto_enabled = v; return this;} private boolean xowa_proto_enabled;
	public void Copy(Xow_sys_cfg src) {
		this.xowa_cmd_enabled = src.xowa_cmd_enabled;
		this.xowa_proto_enabled = src.xowa_proto_enabled;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, "xowa_cmd_enabled_"))                xowa_cmd_enabled = m.ReadYn("v");
		else if	(ctx.Match(k, "xowa_proto_enabled_"))              xowa_proto_enabled = m.ReadYn("v");
		else return Gfo_invk_.Rv_unhandled;
		return this;
	}
}
