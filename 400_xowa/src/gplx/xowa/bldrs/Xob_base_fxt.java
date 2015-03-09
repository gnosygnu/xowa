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
package gplx.xowa.bldrs; import gplx.*; import gplx.xowa.*;
import gplx.ios.*; import gplx.xowa.bldrs.*; 
public class Xob_base_fxt {
	public Xob_base_fxt Clear() {
		if (app == null) {
			app = Xoa_app_fxt.app_();
			wiki = Xoa_app_fxt.wiki_tst_(app);
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
	public GfoInvkAble Bldr_itm() {return bldr_itm;} GfoInvkAble bldr_itm;
	public Xodb_page page_(String ttl) {return page_(ttl, "");}
	public Xodb_page page_(String ttl, String text) {return new Xodb_page().Ttl_(Bry_.new_utf8_(ttl), wiki.Ns_mgr()).Wtxt_(Bry_.new_utf8_(text));}
	public Io_fil_chkr meta_(String url, String data) {return new Io_fil_chkr(Io_url_.mem_fil_(url), data);}
	public void Init_fxts(Xob_bldr bldr, Xowe_wiki wiki, Xob_base_fxt... fxt_ary) {
		int fxt_ary_len = fxt_ary.length;
		for (int i = 0; i < fxt_ary_len; i++)
			fxt_ary[i].Init_(bldr, wiki);
	}
	public Xob_base_fxt Init_fil(String url, String raw) {return Init_fil(Io_url_.new_fil_(url), raw);}
	public Xob_base_fxt Init_fil(Io_url url, String raw) {Io_mgr._.SaveFilStr(url, raw); return this;}
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
		Tfds.Eq_str_lines(expd, Io_mgr._.LoadFilStr(url));
		return this;
	}
	public static void Run_cmd(Xob_bldr bldr, Xob_cmd cmd) {
		cmd.Cmd_bgn(bldr);
		cmd.Cmd_run();
		cmd.Cmd_end();
	}
	public static void Run_wkr(Xob_bldr bldr, Xobd_wkr wkr, Xodb_page[] page_ary) {
		wkr.Wkr_bgn(bldr);
		int page_ary_len = page_ary.length;
		for (int i = 0; i < page_ary_len; i++) {
			Xodb_page page = page_ary[i];
			wkr.Wkr_run(page);
		}
		wkr.Wkr_end();		
	}
}
