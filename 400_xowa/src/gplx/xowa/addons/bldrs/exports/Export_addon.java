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
package gplx.xowa.addons.bldrs.exports; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*;
import gplx.xowa.bldrs.wkrs.*;
public class Export_addon implements Xoax_addon_itm, Xoax_addon_itm__bldr {
	public Xob_cmd[] Bldr_cmds() {
		return new Xob_cmd[] 
		{ gplx.xowa.addons.bldrs.exports.splits.Split_bldr_cmd.Prototype
		, gplx.xowa.addons.bldrs.exports.merges.Merge_bldr_cmd.Prototype
		, gplx.xowa.addons.bldrs.exports.packs.splits.Pack_split_bldr_cmd.Prototype
		, gplx.xowa.addons.bldrs.exports.packs.files.Pack_file_bldr_cmd.Prototype
		};
	}

	public String Addon__key() {return "xowa.builds.repacks";}
}
