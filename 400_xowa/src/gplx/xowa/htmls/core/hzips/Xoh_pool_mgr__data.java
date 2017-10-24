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
package gplx.xowa.htmls.core.hzips; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
import gplx.core.btries.*; import gplx.core.threads.poolables.*;
import gplx.xowa.htmls.core.wkrs.escapes.*;
import gplx.xowa.htmls.core.wkrs.hdrs.*; import gplx.xowa.htmls.core.wkrs.lnkes.*; import gplx.xowa.htmls.core.wkrs.lnkis.*; import gplx.xowa.htmls.core.wkrs.xndes.*;
import gplx.xowa.htmls.core.wkrs.imgs.*; import gplx.xowa.htmls.core.wkrs.thms.*; import gplx.xowa.htmls.core.wkrs.tocs.*;
import gplx.xowa.htmls.core.wkrs.addons.timelines.*; import gplx.xowa.htmls.core.wkrs.addons.gallerys.*; import gplx.xowa.htmls.core.wkrs.addons.medias.*;
public class Xoh_pool_mgr__data {
	private final    Gfo_poolable_mgr pool__hdr, pool__lnke, pool__img, pool__img_bare, pool__toc, pool__timeline, pool__gallery, pool__media;
	public Xoh_pool_mgr__data() {
		pool__hdr		= New_pool(new Xoh_hdr_data());
		pool__lnke		= New_pool(new Xoh_lnke_data());
		pool__img		= New_pool(new Xoh_img_data());
		pool__img_bare	= New_pool(new Xoh_img_bare_data());
		pool__toc		= New_pool(new Xoh_toc_data());
		pool__timeline	= New_pool(new Xoh_timeline_data());
		pool__gallery	= New_pool(new Xoh_gallery_data());
		pool__media		= New_pool(new Xoh_media_data());
	}
	public Xoh_data_itm Get_by_tid(int tid) {
		Gfo_poolable_mgr pool = null;
		switch (tid) {
			case Xoh_hzip_dict_.Tid__hdr:		pool = pool__hdr; break;
			case Xoh_hzip_dict_.Tid__lnke:		pool = pool__lnke; break;
			case Xoh_hzip_dict_.Tid__img:		pool = pool__img; break;
			case Xoh_hzip_dict_.Tid__img_bare:	pool = pool__img_bare; break;
			case Xoh_hzip_dict_.Tid__toc:		pool = pool__toc; break;
			case Xoh_hzip_dict_.Tid__timeline:	pool = pool__timeline; break;
			case Xoh_hzip_dict_.Tid__gallery:	pool = pool__gallery; break;
			case Xoh_hzip_dict_.Tid__media:		pool = pool__media; break;
			default:							return null;
		}
		return (Xoh_data_itm)pool.Get_fast();
	}
	private static Gfo_poolable_mgr New_pool(Gfo_poolable_itm proto) {
		return Gfo_poolable_mgr_.New(1, 32, proto);
	}
}
