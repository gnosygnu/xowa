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
package gplx.xowa.bldrs; import gplx.*; import gplx.xowa.*;
import gplx.core.ios.*;
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wkrs.*; import gplx.xowa.wikis.data.tbls.*;
public class Xob_base_fxt {
	public Xob_base_fxt Clear() {
		if (app == null) {
			app = Xoa_app_fxt.Make__app__edit();
			wiki = Xoa_app_fxt.Make__wiki__edit(app);
			bldr = Xoa_app_fxt.bldr_(app);
		}
		this.Init_(bldr, wiki);
		Clear_hook();
		return this;
	}
	@gplx.Virtual public void Clear_hook() {}
	public Xob_base_fxt Init_(Xob_bldr bldr, Xowe_wiki wiki) {this.bldr = bldr; this.wiki = wiki; return this;}
	public Xoae_app App() {return app;} private Xoae_app app;
	public Xob_bldr Bldr() {return bldr;} private Xob_bldr bldr;
	public Xowe_wiki Wiki() {return wiki;} private Xowe_wiki wiki;
	public Gfo_invk Bldr_itm() {return bldr_itm;} Gfo_invk bldr_itm;
	public Xowd_page_itm page_(String ttl) {return page_(ttl, "");}
	public Xowd_page_itm page_(String ttl, String text) {return new Xowd_page_itm().Ttl_(Bry_.new_u8(ttl), wiki.Ns_mgr()).Text_(Bry_.new_u8(text));}
	public Io_fil_chkr meta_(String url, String data) {return new Io_fil_chkr(Io_url_.mem_fil_(url), data);}
	public void Init_fxts(Xob_bldr bldr, Xowe_wiki wiki, Xob_base_fxt... fxt_ary) {
		int fxt_ary_len = fxt_ary.length;
		for (int i = 0; i < fxt_ary_len; i++)
			fxt_ary[i].Init_(bldr, wiki);
	}
	public Xob_base_fxt Init_fil(String url, String raw) {return Init_fil(Io_url_.new_fil_(url), raw);}
	public Xob_base_fxt Init_fil(Io_url url, String raw) {Io_mgr.Instance.SaveFilStr(url, raw); return this;}
	public Xob_base_fxt Exec_cmd(String cmd_key, GfoMsg... msgs) {
		Xob_cmd cmd = (Xob_cmd)bldr.Cmd_mgr().Add_cmd(wiki, cmd_key);
		this.bldr_itm = cmd;
		int len = msgs.length;
		GfsCtx ctx = GfsCtx.new_();
		for (int i = 0; i < len; i++) {
			GfoMsg msg = msgs[i];
			cmd.Invk(ctx, GfsCtx.Ikey_null, msg.Key(), msg);
		}
		Run_cmd(bldr, cmd);
		return this;
	}
	public Xob_base_fxt Test_fil(String url, String expd) {return Test_fil(Io_url_.new_fil_(url), expd);}
	public Xob_base_fxt Test_fil(Io_url url, String expd) {
		Tfds.Eq_str_lines(expd, Io_mgr.Instance.LoadFilStr(url));
		return this;
	}
	public static void Run_cmd(Xob_bldr bldr, Xob_cmd cmd) {
		cmd.Cmd_bgn(bldr);
		cmd.Cmd_run();
		cmd.Cmd_end();
	}
	public static void Run_wkr(Xob_bldr bldr, Xob_page_wkr wkr, Xowd_page_itm[] page_ary) {
		wkr.Page_wkr__bgn();
		int page_ary_len = page_ary.length;
		for (int i = 0; i < page_ary_len; i++) {
			Xowd_page_itm page = page_ary[i];
			wkr.Page_wkr__run(page);
		}
		wkr.Page_wkr__end();		
	}
}
