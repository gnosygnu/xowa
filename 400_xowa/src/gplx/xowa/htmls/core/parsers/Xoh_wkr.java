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
package gplx.xowa.htmls.core.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
public interface Xoh_wkr {
	void On_hdr		(int tag_bgn, int tag_end, int level, byte[] id, byte[] caption, boolean id_caption_related);
	void On_lnke	(int tag_bgn, int tag_end, byte lnke_type, int autonumber_id, byte[] href);
	void On_lnki	(int tag_bgn, int tag_end, byte lnki_type, int site_bgn, int site_end, byte[] page_bry, byte[] capt_bry, byte[] trail_bry);
	void On_escape	(int rng_bgn, int rng_end);
	void On_space	(int rng_bgn, int rng_end);
	void On_txt		(int rng_bgn, int rng_end);
}
