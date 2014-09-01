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
package gplx.xowa.xtns.imaps; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.btries.*; import gplx.xowa.wikis.*; import gplx.xowa.html.*; import gplx.xowa.html.lnkis.*;
public class Imap_xtn_mgr extends Xox_mgr_base implements GfoInvkAble {
	private boolean init;
	@Override public boolean Enabled_default() {return true;}
	@Override public byte[] Xtn_key() {return XTN_KEY;} public static final byte[] XTN_KEY = Bry_.new_ascii_("imageMap");
	public Xow_wiki Wiki() {return wiki;} private Xow_wiki wiki;
        @gplx.Internal protected Imap_parser Parser() {return parser;} private Imap_parser parser;
	public void Desc_assert() {
		if (desc_trie != null) return;
		desc_trie = Imap_desc_tid.trie_(wiki);
		desc_msg = wiki.Msg_mgr().Val_by_key_obj("imagemap_description");
		desc_icon_url = wiki.App().Fsys_mgr().Bin_extensions_dir().GenSubFil_nest("ImageMap", "imgs", "desc-20.png").To_http_file_bry();
	}
	public Btrie_slim_mgr Desc_trie() {return desc_trie;}	private Btrie_slim_mgr desc_trie;
	public byte[] Desc_msg() {return desc_msg;} private byte[] desc_msg;
	public byte[] Desc_icon_url() {return desc_icon_url;} private byte[] desc_icon_url;
	public Xoh_arg_img_core Img_core_fmtr(boolean hctx_is_hdump) {return hctx_is_hdump ? img_core_hdump : img_core_basic;}
	private final Xoh_arg_img_core img_core_basic = new Xoh_arg_img_core__basic(), img_core_hdump = new Xoh_arg_img_core__hdump();
	@Override public Xox_mgr Clone_new() {return new Imap_xtn_mgr();}
	@Override public void Xtn_init_by_wiki(Xow_wiki wiki) {
		this.wiki = wiki;
	}
	public void Xtn_assert() {
		if (init) return;
		parser = new Imap_parser(this);
		init = true;
	}
	public void Clear() {
	}
}
