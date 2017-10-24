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
import gplx.core.brys.*;
class Volume_prep_itm implements Bry_bfr_able {
	public int Prep_id = 0;
	public byte[] Page_ttl = null;
	public long Max_bytes = 0;
	public int Max_depth = 2;
	public int Max_article_count = -1;
	public int Max_link_count_per_page = -1;
	public boolean Skip_navbox = false;
	public int Max_file_count = -1;
	public long Max_file_size = -1;
	public boolean Skip_audio = true;
	public static final    Volume_prep_itm[] Ary_empty = new Volume_prep_itm[0];
	public void To_bfr(Bry_bfr bfr) {
		bfr.Add(Page_ttl);
	}
}
// Earth|2|100|10|100|100MB
