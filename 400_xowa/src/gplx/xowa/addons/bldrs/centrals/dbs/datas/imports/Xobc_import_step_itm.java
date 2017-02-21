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
package gplx.xowa.addons.bldrs.centrals.dbs.datas.imports; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.centrals.*; import gplx.xowa.addons.bldrs.centrals.dbs.*; import gplx.xowa.addons.bldrs.centrals.dbs.datas.*;
import gplx.dbs.*;
public class Xobc_import_step_itm {
	public Xobc_import_step_itm(int step_id, int host_id, byte[] wiki_abrv, String wiki_date
		, String import_name, int import_type, byte import_zip_type, long import_size_zip, long import_size_raw, String import_md5
		, long import_prog_data_max, int import_prog_row_max
		) {
		this.Step_id = step_id;
		this.Host_id = host_id;
		this.wiki_abrv = wiki_abrv;
		this.Wiki_date = wiki_date;
		this.Import_name = import_name;
		this.Import_type = import_type;
		this.Import_zip_type = import_zip_type;
		this.Import_size_zip = import_size_zip;
		this.Import_size_raw = import_size_raw;
		this.Import_md5 = import_md5;
		this.Import_prog_data_max = import_prog_data_max;
		this.Import_prog_row_max = import_prog_row_max;
	}
	public final    int Step_id;
	public final    int Host_id;
	public byte[] Wiki_abrv() {return wiki_abrv;} private final    byte[] wiki_abrv;
	public final    String Wiki_date;
	public final    String Import_name;
	public final    int Import_type;
	public final    byte Import_zip_type;
	public final    long Import_size_zip;
	public final    long Import_size_raw;
	public final    String Import_md5;
	public final    long Import_prog_data_max;
	public final    int Import_prog_row_max;

	public static final    Xobc_import_step_itm Null = null;
}
