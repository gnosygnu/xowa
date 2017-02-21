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
package gplx.xowa.files; import gplx.*; import gplx.xowa.*;
public class Xof_cfg_download implements Gfo_invk {
	public Xof_cfg_download() {
		this.enabled = true;					// changed to true; DATE:2016-12-19; OLD: CFG: set to false b/c some tests only do parsing [[File:A.png]] and repos are not set up
		this.redownload = Redownload_none;		// CFG: set to none to be as conservative as possible
	}
	public boolean Enabled() {return enabled;} public Xof_cfg_download Enabled_(boolean v) {enabled = v; return this;} private boolean enabled;
	public byte Redownload() {return redownload;} public Xof_cfg_download Redownload_(byte v) {redownload = v; return this;} private byte redownload;
	public void Init_by_wiki(Xow_wiki wiki) {
		wiki.App().Cfg().Bind_many_app(this, Cfg__retrieval_enabled);
	}

	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Cfg__retrieval_enabled))	enabled = m.ReadYn("v");

		else if	(ctx.Match(k, Invk_redownload))			return Redownload_to_str_(redownload);
		else if	(ctx.Match(k, Invk_redownload_))		redownload = Redownload_parse_(m.ReadStr("v"));
		else if	(ctx.Match(k, Invk_redownload_toggle))	redownload = redownload == Redownload_none ? Redownload_missing : Redownload_none;
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}	private static final String Invk_redownload = "redownload", Invk_redownload_ = "redownload_", Invk_redownload_toggle = "redownload_toggle";
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
	private static final String Cfg__retrieval_enabled = "xowa.files.retrieval_enabled";
}
