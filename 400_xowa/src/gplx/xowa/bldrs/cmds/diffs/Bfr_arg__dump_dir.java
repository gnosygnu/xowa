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
package gplx.xowa.bldrs.cmds.diffs;
import gplx.types.custom.brys.wtrs.args.BryBfrArg;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.xowa.*;
class Bfr_arg__dump_dir implements BryBfrArg {	// .dump_dir = "/xowa/wiki/en.wikipedia.org/"
	private final Xow_wiki wiki;
	public Bfr_arg__dump_dir(Xow_wiki wiki) {this.wiki = wiki;}
	public void AddToBfr(BryWtr bfr) {
		bfr.Add(wiki.Fsys_mgr().Root_dir().RawBry());
	}
}
class Bfr_arg__dump_core implements BryBfrArg {// .dump_core = "en.wikipedia.org-core.xowa"
	private final Xow_wiki wiki;
	public Bfr_arg__dump_core(Xow_wiki wiki) {this.wiki = wiki;}
	public void AddToBfr(BryWtr bfr) {
		bfr.AddStrU8(wiki.Data__core_mgr().Db__core().Url().NameAndExt());
	}
}
class Bfr_arg__dump_domain implements BryBfrArg {// .dump_domain = en.wikipedia.org
	private final Xow_wiki wiki;
	public Bfr_arg__dump_domain(Xow_wiki wiki) {this.wiki = wiki;}
	public void AddToBfr(BryWtr bfr) {
		bfr.Add(wiki.Domain_bry());
	}
}
class Bfr_arg__dir_spr implements BryBfrArg {// .dir_spr = "/"
	public void AddToBfr(BryWtr bfr) {
		bfr.AddByte(gplx.core.envs.Op_sys.Cur().Fsys_dir_spr_byte());
	}
}
