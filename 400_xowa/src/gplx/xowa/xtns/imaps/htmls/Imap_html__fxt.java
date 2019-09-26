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
package gplx.xowa.xtns.imaps.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.imaps.*;
import gplx.xowa.files.*; import gplx.xowa.files.caches.*;
import gplx.xowa.parsers.lnkis.*;
import gplx.xowa.htmls.core.htmls.*; import gplx.xowa.htmls.core.makes.tests.*;
import gplx.xowa.langs.msgs.*;
class Imap_html__fxt {
	private final    Xoh_make_fxt make_fxt = new Xoh_make_fxt();
	private final    Xop_fxt parser_fxt;
	private boolean hdump;
	public Imap_html__fxt() {
		this.parser_fxt = new Xop_fxt();
		Xol_msg_itm msg = parser_fxt.Wiki().Msg_mgr().Get_or_make(Bry_.new_a7("imagemap_description"));
		msg.Atrs_set(Bry_.new_a7("click here"), false, false);
	}
	public Imap_html__fxt Hdump_n_() {return Hdump_(Bool_.N);}
	public Imap_html__fxt Hdump_y_() {return Hdump_(Bool_.Y);}
	private Imap_html__fxt Hdump_(boolean v) {
		this.hdump = v;
		return this;
	}
	public Xop_fxt Fxt() {return parser_fxt;}
	public void Test_html_full_str(String raw, String expd) {parser_fxt.Test_html_full_str(raw, expd);}
	public void Test_html_full_frag(String raw, String expd) {parser_fxt.Test_html_full_frag(raw, expd);}
	public String Frag_html_full() {
		return String_.Concat_lines_nl_skip_last
		( "<div id=\"imap_div_0\" class=\"noresize\">"
		, "      <map name=\"imageMap_1_1\">"
		, "      </map>"
		, "      <img id=\"xoimg_0\" alt=\"\" src=\"file:///mem/wiki/repo/trg/orig/7/0/A.png\" width=\"0\" height=\"0\" usemap=\"#imageMap_1_1\"/>"
		, "    </div>"
		);
	}
	public void Test__hview(String wtxt, String expd) {
		parser_fxt.Hctx_(Xoh_wtr_ctx.Basic);
		parser_fxt.Test_html_full_str(wtxt, expd);
	}
	public void Test__hdump(String wtxt, String save, String load, Xof_fsdb_itm[] fsdb_itms) {
		parser_fxt.Hctx_(Xoh_wtr_ctx.Hdump);
		parser_fxt.Test_html_full_str(wtxt, save);

		make_fxt.Init__usr_cache(fsdb_itms);
		make_fxt.Page_chkr().Clear();
		for (Xof_fsdb_itm itm : fsdb_itms) {
			make_fxt.Page_chkr().Imgs__add(String_.new_u8(itm.Lnki_wiki_abrv()), String_.new_u8(itm.Lnki_ttl()), itm.Lnki_type(), itm.Lnki_upright(), itm.Lnki_w(), itm.Lnki_h(), itm.Lnki_time(), itm.Lnki_page());
		}
		make_fxt.Test__make(save, make_fxt.Page_chkr().Body_(load));
	}
	public Xof_fsdb_itm[] Basic__fsdb() {
		return new Xof_fsdb_itm[] {make_fxt.Init__fsdb_itm("en.w", "A.png", 123, 0, 123, 100, "mem/wiki/repo/trg/thumb/7/0/A.png/123px.png")};
	}
	public String Basic__wtxt() {
		return String_.Concat_lines_nl_skip_last
		( "<imagemap>"
		, "File:A.png|thumb|123px|a1"
		, "circle 0 0 5 [[B|b1]]"
		, "rect 0 0 4 8 [[C|c1]]"
		, "desc none"
		, "</imagemap>"
		);
	}
	public String Basic__html(boolean dir_has_value) {
		int mode = -1;
		if (hdump) {
			if (dir_has_value) {
				mode = Mode__hload;
			}
			else {
				mode = Mode__hsave;
			}
		}
		else {
			mode = Mode__hview;
		}

		String div_id = "", div_width = "";
		String img_id = "", img_data = "", img_src = "", img_width = "", img_height = "";
		String magnify_id = "";

		switch (mode) {
			case Mode__hview:
				div_id = " id=\"xowa_file_div_0\"";
				div_width = "220"; // NOTE:220px is default w for "non-found" thumb; DATE:2014-09-24
				img_id = " id=\"xoimg_0\"";
				img_data = "";
				img_src = "file:///mem/wiki/repo/trg/thumb/7/0/A.png/123px.png";
				img_width = "123";
				img_height = "0";
				magnify_id = "";
				break;
			case Mode__hsave:
				div_id = "";
				div_width = "123";
				img_id = "";
				img_data = " data-xowa-title=\"A.png\" data-xoimg=\"1|123|0|-1|-1|-1\"";
				img_src = "";
				img_width = "0";
				img_height = "0";
				magnify_id = "";
				break;
			case Mode__hload:
				div_id = "";
				div_width = "123";
				img_id = " id=\"xoimg_0\"";
				img_data = " data-xowa-title=\"A.png\" data-xoimg=\"1|123|0|-1|-1|-1\"";
				img_src = "file:///mem/wiki/repo/trg/thumb/7/0/A.png/123px.png";
				img_width = "123";
				img_height = "100";
				magnify_id = " id=\"xolnki_2\"";
				break;
		}
		return String_.Concat_lines_nl_skip_last
		( "<div class=\"thumb tright\">"
		, "  <div" + div_id + " class=\"thumbinner\" style=\"width:" + div_width + "px;\">"
		, "    <div id=\"imap_div_0\" class=\"noresize\">"
		, "      <map name=\"imageMap_1_1\">"
		, "        <area href=\"/wiki/B\" shape=\"circle\" coords=\"0,0,5\" alt=\"b1\" title=\"b1\"/>"
		, "        <area href=\"/wiki/C\" shape=\"rect\" coords=\"0,0,4,8\" alt=\"c1\" title=\"c1\"/>"
		, "      </map>"
		, "      <img" + img_id + img_data + " alt=\"\" src=\"" + img_src + "\" width=\"" + img_width + "\" height=\"" + img_height + "\" usemap=\"#imageMap_1_1\"/>"
		, "    </div>"
		, "    <div class=\"thumbcaption\">"
		,       "<div class=\"magnify\"><a" + magnify_id + " href=\"/wiki/File:A.png\" class=\"internal\" title=\"Enlarge\"></a></div>a1"
		, "    </div>"
		, "  </div>"
		, "</div>"
		);
	}
	public String Desc__wtxt() {
		make_fxt.Init__usr_cache(make_fxt.Init__fsdb_itm("en.w", "A.png", 123, 0, 123, 100, "mem/wiki/repo/trg/thumb/7/0/A.png/123px.png"));
		make_fxt.Page_chkr().Imgs__add("en.w", "A.png", Xop_lnki_type.Id_none, -1, 123, 0, -1, -1);
		return String_.Concat_lines_nl_skip_last
		( "<imagemap>"
		, "File:A.png|123px|a1"
		, "desc top-left"
		, "</imagemap>"
		);
	}
	public String Desc__html(boolean dir_has_value) {
		int mode = -1;
		if (hdump) {
			if (dir_has_value) {
				mode = Mode__hload;
			}
			else {
				mode = Mode__hsave;
			}
		}
		else {
			mode = Mode__hview;
		}

		String img_id = "", img_data = "", img_src = "", img_width = "", img_height = "";
		String desc_src = "", desc_hdump = "";

		switch (mode) {
			case Mode__hview:
				img_id = " id=\"xoimg_0\"";
				img_data = "";
				img_src = "file:///mem/wiki/repo/trg/thumb/7/0/A.png/123px.png";
				img_width = "123";
				img_height = "0";
				desc_src = "file:///mem/xowa/bin/any/xowa/xtns/ImageMap/imgs/desc-20.png";
				desc_hdump = " ";
				break;
			case Mode__hsave:
				img_id = "";
				img_data = " data-xowa-title=\"A.png\" data-xoimg=\"1|123|0|-1|-1|-1\"";
				img_src = "";
				img_width = "0";
				img_height = "0";
				desc_src = "";
				desc_hdump = " data-xowa-hdump='imap-desc-icon'";
				break;
			case Mode__hload:
				img_id = " id=\"xoimg_0\"";
				img_data = " data-xowa-title=\"A.png\" data-xoimg=\"1|123|0|-1|-1|-1\"";
				img_src = "file:///mem/wiki/repo/trg/thumb/7/0/A.png/123px.png";
				img_width = "123";
				img_height = "100";
				desc_src = "file:///mem/xowa/bin/any/xowa/xtns/ImageMap/imgs/desc-20.png";
				desc_hdump = " data-xowa-hdump='imap-desc-icon'";
				break;
		}

		return String_.Concat_lines_nl_skip_last
		( "<div id=\"imap_div_0\" class=\"noresize\" style=\"height:0px; width: 123px;\">"
		, "      <map name=\"imageMap_1_1\">"
		, "      </map>"
		, "      <img" + img_id + img_data + " alt=\"a1\" src=\"" + img_src + "\" width=\"" + img_width + "\" height=\"" + img_height + "\" usemap=\"#imageMap_1_1\"/>"
		, "      <div style=\"margin-left:0px; margin-top:1px; text-align:left;\">"
		, "        <a href=\"/wiki/File:A.png\" title=\"click here\">"
		, "          <img alt=\"click here\" src=\"" + desc_src + "\" style=\"border: none;\"" + desc_hdump + "/>"
		, "        </a>"
		, "      </div>"
		, "    </div>"
		);
	}
	private static final int Mode__hview = 0, Mode__hsave = 1, Mode__hload = 2;
}
