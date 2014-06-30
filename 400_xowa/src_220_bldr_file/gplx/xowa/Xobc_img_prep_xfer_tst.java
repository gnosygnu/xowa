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
package gplx.xowa; import gplx.*;
import org.junit.*;
public class Xobc_img_prep_xfer_tst {
	Xobc_img_xfer_bldr_fxt fxt = new Xobc_img_xfer_bldr_fxt();
	@Test  public void Img_download_bldr() {
		fxt.Img_link_
 			(	"A.png|0,100,200,upright=0.8"
 			,	"A.png|1,101,201,upright=1"
 			,	"B.png|1,102,202,upright=1"
		,	""
 			)
		.Name_1_
		(	"A.png|||10000|100|200|8|"
		)
		.Name_0_
		(	"B.png|C.png||10001|101|201|4|"
		)
		.Expd_
		(	"A.png|||10000|100|200|8|1||0,100,200,upright=0.8;1,101,201,upright=1"
		,	"B.png|C.png||10001|101|201|4|0||1,102,202,upright=1"
		)
		.Run()
		;
	}
}
class Xobc_img_xfer_bldr_fxt {
	public Xobc_img_xfer_bldr_fxt Img_link_(String... v) 		{img_link		= String_.Concat_lines_nl_skip_last(v); return this;} private String img_link;
	public Xobc_img_xfer_bldr_fxt Name_1_(String... v) 		{name_wiki_1	= String_.Concat_lines_nl_skip_last(v); return this;} private String name_wiki_1;
	public Xobc_img_xfer_bldr_fxt Name_0_(String... v) 		{name_wiki_0	= String_.Concat_lines_nl_skip_last(v); return this;} private String name_wiki_0;
	public Xobc_img_xfer_bldr_fxt Expd_(String... v) 			{expd			= String_.Concat_lines_nl_skip_last(v); return this;} private String expd;
	public void Run() {
		Io_mgr._.InitEngine_mem();
		Xoa_app app = Xoa_app_fxt.app_();
		Xow_wiki wiki = Xoa_app_fxt.wiki_tst_(app);
		Xob_bldr bldr = Xoa_app_fxt.bldr_(app);
		
		Xobc_img_prep_xfer cmd = new Xobc_img_prep_xfer(bldr, wiki);
		cmd.Commons_url_(Io_url_.mem_dir_("mem/commons"));
		cmd.Cmd_bgn(bldr);
		Io_mgr._.SaveFilStr(cmd.Link_dir().GenSubFil("dump.csv"), img_link);
		Io_mgr._.SaveFilStr(cmd.Wiki_0_dir().GenSubFil("dump.csv"), name_wiki_0);
		Io_mgr._.SaveFilStr(cmd.Wiki_1_dir().GenSubFil("dump.csv"), name_wiki_1);
		
		cmd.Cmd_run();
		
		Io_url[] dump_urls = cmd.Dump_url_gen().Prv_urls();
		Io_url dump_url = dump_urls[0];
		String actl = Io_mgr._.LoadFilStr(dump_url);
		Tfds.Eq_str_lines(expd, actl);
	}
}
