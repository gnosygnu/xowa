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
package gplx.xowa.xtns.wbases.imports.json; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*; import gplx.xowa.xtns.wbases.imports.*;
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wkrs.*;
public class Xowb_json_dump_cmd extends Xob_cmd__base {
	private final    Xowb_json_dump_parser json_dump_parser;
	private Io_url src_fil;
	public Xowb_json_dump_cmd(Xob_bldr bldr, Xowe_wiki wiki) {super(bldr, wiki);
		this.json_dump_parser = new Xowb_json_dump_parser(bldr, wiki);
	}
	@Override public void Cmd_run() {
		json_dump_parser.Parse(src_fil);
	}

	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_src_fil_))		this.src_fil = m.ReadIoUrl("v");
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}	private static final String Invk_src_fil_ = "src_fil_";

	public static final String BLDR_CMD_KEY = "wbase.json_dump";
	@Override public String Cmd_key() {return BLDR_CMD_KEY;}
	public static final    Xob_cmd Prototype = new Xowb_json_dump_cmd(null, null);
	@Override public Xob_cmd Cmd_clone(Xob_bldr bldr, Xowe_wiki wiki) {return new Xowb_json_dump_cmd(bldr, wiki);}
}
