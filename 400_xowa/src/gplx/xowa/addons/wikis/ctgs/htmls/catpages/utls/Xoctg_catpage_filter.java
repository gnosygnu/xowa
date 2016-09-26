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
package gplx.xowa.addons.wikis.ctgs.htmls.catpages.utls; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*; import gplx.xowa.addons.wikis.ctgs.htmls.*; import gplx.xowa.addons.wikis.ctgs.htmls.catpages.*;
import gplx.core.lists.binary_searches.*;
import gplx.xowa.addons.wikis.ctgs.htmls.catpages.doms.*; import gplx.xowa.addons.wikis.ctgs.htmls.catpages.urls.*;
public class Xoctg_catpage_filter {
	public static void Filter(int limit, Xoctg_catpage_url cat_url, Xoctg_catpage_ctg ctg) {
		int len = Xoa_ctg_mgr.Tid___max;
		for (byte i = 0; i < len; ++i) {
			Filter_by_grp(limit, cat_url, ctg.Grp_by_tid(i));
		}
	}
	private static void Filter_by_grp(int grp_len, Xoctg_catpage_url cat_url, Xoctg_catpage_grp grp) {
		byte grp_tid = grp.Tid();
		byte[] grp_key = cat_url.Grp_keys()[grp_tid];

		// dflt to bos; EX: grp_bgn=0 grp_end=200
		int grp_bgn = 0; 
		int grp_end = grp_bgn + grp_len;

		// key specified; calc new grp_bgn, grp_end
		if (grp_key != null) {
			// get idx of key
			int key_idx = Binary_search_.Search(grp.Itms(), Xoctg_catpage_itm_sorter__sort_key.Sorter, grp_key);

			// if fwd, set grp_bgn to key_idx, and add grp_len
			if (cat_url.Grp_fwds()[grp_tid]) {
				grp_bgn = key_idx;
				grp_end = grp_bgn + grp_len;
			}
			// if bwd, set grp_end to key_idx, and subtract grp_len
			else {
				grp_end = key_idx;
				grp_bgn = grp_end - grp_len;

				// assert new grp_bgn is not negative
				if (grp_bgn < 0) grp_bgn = 0;
			}
		}

		// assert new grp_end is not > grp_max; note that this needs to be specified for when grp_key is null and not null
		int grp_max = grp.Itms__len();
		if (grp_end > grp_max)
			grp_end = grp_max;
		grp.Rng_(grp_bgn, grp_end);
	}
}
