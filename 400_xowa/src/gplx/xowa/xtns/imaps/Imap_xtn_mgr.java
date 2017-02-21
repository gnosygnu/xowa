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
package gplx.xowa.xtns.imaps; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.btries.*; import gplx.xowa.htmls.core.wkrs.lnkis.htmls.*;
import gplx.xowa.xtns.imaps.itms.*;
public class Imap_xtn_mgr extends Xox_mgr_base implements Gfo_invk {
	private boolean init;
	@Override public byte[]				Xtn_key()			{return XTN_KEY;}		public static final    byte[] XTN_KEY = Bry_.new_a7("imageMap");
	@Override public boolean				Enabled_default()	{return true;}
	public			Xoh_arg_img_core	Img_core_arg()		{return img_core_arg;}	private final    Xoh_arg_img_core__basic img_core_arg = new Xoh_arg_img_core__basic();
        public			Imap_parser			Parser()			{return parser;}		private Imap_parser parser;
	public			byte[]				Desc_msg()			{return desc_msg;}		private byte[] desc_msg;
	public			byte[]				Desc_icon_url()		{return desc_icon_url;} private byte[] desc_icon_url;
	public Btrie_slim_mgr Desc_trie()	{
		if (desc_trie == null) {
			this.desc_trie = Imap_desc_tid.New_trie(wiki);
			this.desc_msg = wiki.Msg_mgr().Val_by_key_obj("imagemap_description");
			this.desc_icon_url = wiki.Appe().Fsys_mgr().Bin_xtns_dir().GenSubFil_nest("ImageMap", "imgs", "desc-20.png").To_http_file_bry();
		}
		return desc_trie;
	}	private Btrie_slim_mgr desc_trie;
	public Imap_xtn_mgr Xtn_assert() {
		if (init) return this;
		this.init = true;
		this.parser = new Imap_parser(this);
		return this;
	}
	@Override public void		Xtn_init_by_wiki(Xowe_wiki wiki) {this.wiki = wiki;} private Xowe_wiki wiki;
	@Override public Xox_mgr		Xtn_clone_new() {return new Imap_xtn_mgr();}

	public static final    byte[]
	  Bry__usemap__html		= Bry_.new_a7(" usemap=\"#imagemap_1_")
	, Bry__usemap__name		= Bry_.new_a7("usemap")
	, Bry__usemap__prefix	= Bry_.new_a7("#imagemap_1_")
	;
}
