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
package gplx.xowa.addons.bldrs.volumes; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*;
class Volume_prep_mgr {
	private final    Volume_page_loader loader;
	private final    Volume_page_itm tmp_page = new Volume_page_itm();
	private final    List_adp list = List_adp_.New();
	public Volume_prep_mgr(Volume_page_loader loader) {this.loader = loader;}
	public Volume_make_itm[] Calc_makes(Volume_prep_itm[] ary) {
		Volume_prep_ctx ctx = new Volume_prep_ctx();
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			Volume_prep_itm itm = ary[i];
			ctx.Init(ctx, itm);
			Calc_make(ctx, itm.Page_ttl);
		}
		return (Volume_make_itm[])list.To_ary_and_clear(Volume_make_itm.class);
	}
	private void Calc_make(Volume_prep_ctx ctx, byte[] page_ttl) {
		if (!loader.Load(tmp_page, page_ttl)) return;
		if (ctx.Bytes_max != -1 && ctx.Bytes_count > ctx.Bytes_max) return;
		if (ctx.Depth_count > ctx.Depth_max) return;
		List_adp files_list = tmp_page.File_list();
		int files_len = files_list.Len();
		for (int i = 0; i < files_len; ++i) {
			Volume_make_itm file_itm = (Volume_make_itm)files_list.Get_at(i);
			list.Add(file_itm);
		}
		List_adp links_list = tmp_page.Link_list();
		int links_len = links_list.Len();
		for (int i = 0; i < links_len; ++i) {
			if (ctx.Page_max != -1 && ctx.Page_count++ > ctx.Page_max) return;
			Volume_make_itm link_itm = (Volume_make_itm)links_list.Get_at(i);
			list.Add(link_itm);
			ctx.Depth_count++;
			Calc_make(ctx, link_itm.Item_ttl);
			ctx.Depth_count--;
		}
	}
}
class Volume_prep_ctx {
	public long Bytes_count;
	public long Bytes_max;
	public int Page_count;
	public int Page_max;
	public int Depth_count;
	public int Depth_max;
	public void Init(Volume_prep_ctx prv_ctx, Volume_prep_itm itm) {
		this.Bytes_count = prv_ctx.Bytes_count;
		this.Bytes_max = itm.Max_bytes;
		this.Page_count = 1;
		this.Page_max = itm.Max_article_count;
		this.Depth_count = 0;
		this.Depth_max = itm.Max_depth;
	}
//		public int Max_link_count_per_page = -1;
//		public int Max_file_count = -1;
//		public long Max_file_size = -1;
//		public boolean Skip_navbox = false;
//		public boolean Skip_audio = true;
}
