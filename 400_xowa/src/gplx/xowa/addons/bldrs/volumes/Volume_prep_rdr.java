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
class Volume_prep_rdr {
	public Volume_prep_itm[] Parse(Io_url url) {return Parse(Io_mgr.Instance.LoadFilBryOr(url, null));}
	public Volume_prep_itm[] Parse(byte[] src) {
		if (src == null) return Volume_prep_itm.Ary_empty;
		List_adp rv = List_adp_.New();
		byte[][] lines = Bry_split_.Split_lines(src);
		int lines_len = lines.length;
		for (int i = 0; i < lines_len; ++i) {
			Volume_prep_itm itm = Parse_line_or_null(lines[i]);
			if (itm != null) rv.Add(itm);
		}
		return (Volume_prep_itm[])rv.To_ary_and_clear(Volume_prep_itm.class);
	}
	private Volume_prep_itm Parse_line_or_null(byte[] line) {
		byte[][] flds = Bry_split_.Split(line, Byte_ascii.Pipe);
		int flds_len = flds.length; if (flds_len == 0) return null;
		Volume_prep_itm rv = new Volume_prep_itm();
		for (int i = 0; i < flds_len; ++i) {
			byte[] fld = flds[i];
			switch (i) {
				case 0: rv.Page_ttl = fld; break;
				default: throw Err_.new_unhandled_default(i);
			}
		}
		return rv;
	}
}
