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
import gplx.core.brys.*; import gplx.xowa.wikis.*;
class Bfr_arg__dump_dir implements Bfr_arg {	// .dump_dir = "/xowa/wiki/en.wikipedia.org/"
	private final Xow_wiki wiki;
	public Bfr_arg__dump_dir(Xow_wiki wiki) {this.wiki = wiki;}
	public void Bfr_arg__add(Bry_bfr bfr) {
		bfr.Add(wiki.Fsys_mgr().Root_dir().RawBry());
	}
}
class Bfr_arg__dump_core implements Bfr_arg {// .dump_core = "en.wikipedia.org-core.xowa"
	private final Xow_wiki wiki;
	public Bfr_arg__dump_core(Xow_wiki wiki) {this.wiki = wiki;}
	public void Bfr_arg__add(Bry_bfr bfr) {
		bfr.Add_str_u8(wiki.Data__core_mgr().Db__core().Url().NameAndExt());
	}
}
class Bfr_arg__dump_domain implements Bfr_arg {// .dump_domain = en.wikipedia.org
	private final Xow_wiki wiki;
	public Bfr_arg__dump_domain(Xow_wiki wiki) {this.wiki = wiki;}
	public void Bfr_arg__add(Bry_bfr bfr) {
		bfr.Add(wiki.Domain_bry());
	}
}
class Bfr_arg__dir_spr implements Bfr_arg {// .dir_spr = "/"
	public void Bfr_arg__add(Bry_bfr bfr) {
		bfr.Add_byte(gplx.core.envs.Op_sys.Cur().Fsys_dir_spr_byte());
	}
}
