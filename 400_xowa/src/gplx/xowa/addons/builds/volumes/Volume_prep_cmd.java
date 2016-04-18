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
package gplx.xowa.addons.builds.volumes; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.builds.*;
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
		Bry_bfr bfr = Bry_bfr.new_();
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
