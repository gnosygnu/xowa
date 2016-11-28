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
