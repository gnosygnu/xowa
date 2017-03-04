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
package gplx.xowa.addons.bldrs.files; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*;
import gplx.xowa.bldrs.wkrs.*;
import gplx.xowa.addons.bldrs.files.cmds.*;
import gplx.xowa.addons.bldrs.mass_parses.inits.*; import gplx.xowa.addons.bldrs.mass_parses.parses.*; import gplx.xowa.addons.bldrs.mass_parses.makes.*; import gplx.xowa.addons.bldrs.mass_parses.resumes.*;
import gplx.xowa.addons.bldrs.files.cksums.*; import gplx.xowa.addons.bldrs.files.checks.*;
import gplx.xowa.addons.bldrs.app_cfgs.wm_server_cfgs.*;
import gplx.xowa.addons.bldrs.files.missing_origs.*;
public class Xoax_builds_files_addon implements Xoax_addon_itm, Xoax_addon_itm__bldr {
	public Xob_cmd[] Bldr_cmds() {
		return new Xob_cmd[]
		{ Xobldr__lnki_temp__create.Prototype
		, Xobldr__lnki_regy__create.Prototype
		, Xobldr__page_regy__create.Prototype
		, Xobldr__orig_regy__create.Prototype
		, Xobldr_missing_origs_cmd.Prototype
		, Xobldr__xfer_temp__insert_thm.Prototype
		, Xobldr__xfer_temp__insert_orig.Prototype
		, Xobldr__xfer_regy__create.Prototype
		, Xobldr__xfer_regy__update_downloaded.Prototype

		, Xobldr__fsdb_db__create_data.Prototype
		, Xobldr__fsdb_db__create_orig.Prototype
		, Xobldr__page_file_map__create.Prototype

		, Xobldr__text_db__make_page.Prototype
		, Xobldr__text_db__drop_page.Prototype
		, Xobldr__redirect__create.Prototype
		, Xobldr__image__create.Prototype

		, Xomp_init_cmd.Prototype
		, Xomp_parse_cmd.Prototype
		, Xomp_make_cmd.Prototype
		, Xomp_resume_cmd.Prototype
		, Xocksum_calc_cmd.Prototype
		, Xocheck_cmd.Prototype

		, Xowm_server_cfg_cmd.Prototype
		};
	}

	public String Addon__key() {return "xowa.builds.images";}
}
