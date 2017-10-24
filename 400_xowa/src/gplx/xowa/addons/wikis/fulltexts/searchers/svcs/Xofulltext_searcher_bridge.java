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
package gplx.xowa.addons.wikis.fulltexts.searchers.svcs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.fulltexts.*; import gplx.xowa.addons.wikis.fulltexts.searchers.*;
import gplx.langs.jsons.*;
import gplx.xowa.htmls.bridges.*;
public class Xofulltext_searcher_bridge implements Bridge_cmd_itm {
	private Xofulltext_searcher_svc svc;
	public void Init_by_app(Xoa_app app) {
		this.svc = new Xofulltext_searcher_svc(app);
	}
	public String Exec(Json_nde data) {
		byte proc_id = proc_hash.Get_as_byte_or(data.Get_as_bry_or(Bridge_cmd_mgr.Msg__proc, null), Byte_ascii.Max_7_bit);
		Json_nde args = data.Get_kv(Bridge_cmd_mgr.Msg__args).Val_as_nde();
		switch (proc_id) {
			case Proc__search_run:				svc.Search_run(args); break;
			case Proc__search_cxl:				svc.Search_cxl(args); break;
			case Proc__options_save:			svc.Options_save(args); break;
			case Proc__snips_show_all:			svc.Snips_show_all(args); break;
			default: throw Err_.new_unhandled_default(proc_id);
		}
		return "";
	}

	private static final byte Proc__search_run = 0, Proc__search_cxl = 1, Proc__options_save = 2, Proc__snips_show_all = 3;
	private static final    Hash_adp_bry proc_hash = Hash_adp_bry.cs()
	.Add_str_byte("search_run"					, Proc__search_run)
	.Add_str_byte("search_cxl"					, Proc__search_cxl)
	.Add_str_byte("options_save"				, Proc__options_save)
	.Add_str_byte("snips_show_all"				, Proc__snips_show_all)
	;

	public byte[] Key() {return BRIDGE_KEY;} public static final    byte[] BRIDGE_KEY = Bry_.new_a7("xowa.wiki.fulltext.searcher");
        public static final    Xofulltext_searcher_bridge Prototype = new Xofulltext_searcher_bridge(); Xofulltext_searcher_bridge() {}
}
