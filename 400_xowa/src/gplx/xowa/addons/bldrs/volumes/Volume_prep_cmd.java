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
package gplx.xowa.addons.bldrs.volumes; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*;
import gplx.core.brys.*;
import gplx.dbs.*;
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wkrs.*;	
public class Volume_prep_cmd extends Xob_cmd__base {
	private Io_url prep_url, make_url;
	public Volume_prep_cmd(Xob_bldr bldr, Xowe_wiki wiki) {super(bldr, wiki);}
	@Override public void Cmd_run() {
		Volume_prep_itm[] page_itms = new Volume_prep_rdr().Parse(prep_url);
		Volume_prep_mgr prep_mgr = new Volume_prep_mgr(new Volume_page_loader__wiki(wiki));
		Volume_make_itm[] make_itms = prep_mgr.Calc_makes(page_itms);
		Bry_bfr bfr = Bry_bfr_.New();
		for (Volume_make_itm make_itm : make_itms) {
			make_itm.To_bfr(bfr);
			bfr.Add_byte_nl();
		}
		Io_mgr.Instance.SaveFilBfr(make_url, bfr);
	}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk__prep_url_))				prep_url = m.ReadIoUrl("v");
		else if	(ctx.Match(k, Invk__make_url_))				make_url = m.ReadIoUrl("v");
		else	return super.Invk(ctx, ikey, k, m);
		return this;
	}
	private static final String Invk__prep_url_ = "prep_url_", Invk__make_url_ = "make_url_";

	public static final String BLDR_CMD_KEY = "volume.prep";
	@Override public String Cmd_key() {return BLDR_CMD_KEY;}
	public static final    Xob_cmd Prototype = new Volume_prep_cmd(null, null);
	@Override public Xob_cmd Cmd_clone(Xob_bldr bldr, Xowe_wiki wiki) {return new Volume_prep_cmd(bldr, wiki);}
}
