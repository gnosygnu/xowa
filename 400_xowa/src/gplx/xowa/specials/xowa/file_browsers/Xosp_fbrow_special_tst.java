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
import org.junit.*; import gplx.core.net.*; import gplx.ios.*;
public class Xosp_fbrow_special_tst {
	@Before public void init() {fxt.Clear();} private Xosp_fbrow_special_fxt fxt = new Xosp_fbrow_special_fxt();
	@Test    public void Basic() {
		fxt.Init_dir(fxt.Make_dir());
		fxt.Test_nav("mem/root/dir/", String_.Concat_lines_nl_skip_last
		( "<table>"
		, "  <tr>"
		, "    <td>"
		, "      <table width='100%'>"
		, "        <tr>"
		, "          <td align='left'><a href='javascript:get_selected_chk(\"/wiki/Special:XowaFileBrowser?cmd=xowa.wiki.add&amp;path=mem/root/dir/&amp;selected=\", \"chk_\");'>import</a>"
		, "      	 </td>"
		, "        </tr>"
		, "      </table>"
		, "    </td>"
		, "  </tr>"
		, "  <tr>"
		, "    <td style='width:100%;border:1px solid #AAAAAA;'>mem/root/dir/"
		, "    </td>"
		, "  </tr>"
		, "  <tr>"
		, "    <td>"
		, "      <table class='sortable fsys_tb'>"
		, "        <tr>"
		, "          <th><!--selected--></th>"
		, "          <th>name</th>"
		, "          <th>size</th>"
		, "          <th>modified</th>"
		, "        </tr>"
		, "        <tr>"
		, "          <td></td>"
		, "          <td><a href='/wiki/Special:XowaFileBrowser?cmd=xowa.wiki.add&amp;path=mem/root/'>..</a></td>"
		, "          <td></td>"
		, "          <td></td>"
		, "        </tr>"
		, "        <tr>"
		, "          <td>&nbsp;&nbsp;&nbsp;</td>"
		, "          <td><a href='/wiki/Special:XowaFileBrowser?cmd=xowa.wiki.add&amp;path=mem/root/dir/a/'>a</a></td>"
		, "          <td></td>"
		, "          <td></td>"
		, "        </tr>"
		, "        <tr>"
		, "          <td>&nbsp;&nbsp;&nbsp;</td>"
		, "          <td><a href='/wiki/Special:XowaFileBrowser?cmd=xowa.wiki.add&amp;path=mem/root/dir/b/'>b</a></td>"
		, "          <td></td>"
		, "          <td></td>"
		, "        </tr>"
		, "        <tr>"
		, "          <td>&nbsp;&nbsp;&nbsp;</td>"
		, "          <td>1.txt</td>"
		, "          <td class='fsys_td_size'>1 KB</td>"
		, "          <td>2001-01-01 00:00:00</td>"
		, "        </tr>"
		, "        <tr>"
		, "          <td>&nbsp;&nbsp;&nbsp;</td>"
		, "          <td>2.txt</td>"
		, "          <td class='fsys_td_size'>1 KB</td>"
		, "          <td>2004-01-02 00:00:00</td>"
		, "        </tr>"
		, "        <tr>"
		, "          <td><input type='checkbox' id='chk_3.xowa'/></td>"
		, "          <td>3.xowa</td>"
		, "          <td class='fsys_td_size'>1 KB</td>"
		, "          <td>2004-01-03 00:00:00</td>"
		, "        </tr>"
		, "      </table>"
		, "    </td>"
		, "  </tr>"
		, "</table>"
		));
	}
}
class Io_itm_bldr {
	public IoItmDir Root(Io_url url, IoItm_base... subs) {
		IoItmDir rv = IoItmDir_.top_(url);
		Init_dir(rv, url.NameAndExt(), subs);
		return rv;
	}
	public IoItmDir Dir(String name, IoItm_base... subs) {
		IoItmDir rv = IoItmDir_.sub_(name);
		Init_dir(rv, name, subs);
		return rv;
	}
	public IoItmFil Fil(String name, int size, String date) {return IoItmFil_.sub_(name, size, DateAdp_.parse_iso8561(date));}
	private void Init_dir(IoItmDir rv, String name, IoItm_base... subs) {
		int len = subs.length;
		for (int i = 0; i < len; ++i) {
			IoItm_base sub = subs[i];
			IoItmList sub_list = sub.Type_fil() ? rv.SubFils() : rv.SubDirs();
			sub.OwnerDir_set(rv);
			sub_list.Add(sub);
		}
	}
}
class Xosp_fbrow_special_fxt {
	private Io_itm_bldr bldr = new Io_itm_bldr();
	public void Clear() {}
	public IoItmDir Make_dir() {
		return bldr.Root(Io_url_.mem_dir_("mem/root/dir/")
		,   bldr.Dir("a"
		,     bldr.Fil("a1.txt", 11, "2011-01-01")
		,     bldr.Fil("a2.txt", 12, "2011-01-02")
		,     bldr.Fil("a3.txt", 13, "2011-01-03")
		)
		,   bldr.Dir("b"
		,     bldr.Fil("b1.txt", 21, "2011-02-01")
		,     bldr.Fil("b2.txt", 22, "2011-02-02")
		,     bldr.Fil("b3.txt", 23, "2011-02-03")
		)
		,   bldr.Fil("1.txt", 1, "2001-01-01")
		,   bldr.Fil("2.txt", 2, "2004-01-02")
		,   bldr.Fil("3.xowa", 3, "2004-01-03")
		);
	}
	public void Init_dir(IoItmDir dir) {
		IoItmDir_.Make(dir);
	}
	public void Test_nav(String path, String expd) {
		Xoa_url_arg_mgr args_mgr = new Xoa_url_arg_mgr(null);
		Xoa_url url = Xoa_url.new_(Bry_.Empty, Bry_.Empty).Qargs_ary_(Gfo_qarg_itm.Ary("cmd", "xowa.wiki.add", "mode", "view", "path", path));
		args_mgr.Init(url.Qargs_ary());
		Xosp_fbrow_cmd__wiki_add cmd = new Xosp_fbrow_cmd__wiki_add();
		byte[] actl = cmd.Write_html(args_mgr, GfoInvkAble_.Null).Html_body();
		Tfds.Eq_str_lines(expd, String_.new_u8(actl));
	}
}
