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
package gplx.xowa.specials.xowa.file_browsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.specials.*; import gplx.xowa.specials.xowa.*;
import gplx.xowa.specials.*; import gplx.ios.*;
public class Xosp_fbrow_special implements Xows_page {
	private static final Xoa_url_arg_mgr url_args = new Xoa_url_arg_mgr(null);
	public void Special_gen(Xowe_wiki wiki, Xoae_page page, Xoa_url url, Xoa_ttl ttl) {
		Xosp_fbrow_rslt rslt = Gen(url.Args(), GfoInvkAble_.Null);
		page.Html_data().Html_restricted_n_();
		page.Html_data().Custom_head_end_concat(rslt.Html_head());
		page.Data_raw_(rslt.Html_body());
	}
	public static Xosp_fbrow_rslt Gen(Gfo_url_arg[] args, GfoInvkAble select_invkable) {
		url_args.Init(args);
		byte[] cmd_bry = url_args.Read_bry_or_empty(Arg_cmd);
		Xosp_fbrow_cmd cmd = (Xosp_fbrow_cmd)cmd_regy.Get_by_bry(cmd_bry); if (cmd == null) cmd = Xosp_fbrow_cmd__err.I;
		return cmd.Make_new().Write_html(url_args, select_invkable);
	}
	private static final byte[] Arg_cmd = Bry_.new_ascii_("cmd");
	private static final Hash_adp_bry cmd_regy = Hash_adp_bry.cs_()
	.Add_bry_obj(Xosp_fbrow_cmd__wiki_add.Regy_key, Xosp_fbrow_cmd__wiki_add.I)
	.Add_bry_obj(Xosp_fbrow_cmd__root_set.Regy_key, Xosp_fbrow_cmd__root_set.I)
	;
	public static final byte[] Ttl_name_bry = Bry_.new_ascii_("XowaFileBrowser");
}
