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
package gplx.xowa.xtns.wbases.imports.json; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*; import gplx.xowa.xtns.wbases.imports.*;
import gplx.core.ios.*; import gplx.core.ios.streams.*; import gplx.core.criterias.*; import gplx.core.envs.*;
class Io_stream_rdr_mgr {
	public static Io_stream_rdr Get_rdr_or_null(Io_url src_fil, Io_url src_dir, Io_stream_unzip_mgr unzip_mgr, String... filter_ary) {			
		IoItmFil src_itm = null;
		if (src_fil != null) src_itm = Io_mgr.Instance.QueryFil(src_fil);

		// specified file doesn't exist; try to find similar file based on filter
		if (src_itm == null || !src_itm.Exists()) {
			src_itm = Get_itm_by_filters(src_dir, filter_ary);
			if (src_itm == null) return null;
		}

		// return rdr
		Io_url src_itm_url = src_itm.Url();
		Io_stream_rdr rv = unzip_mgr.Handles(src_itm_url)
			? unzip_mgr.New_rdr(src_itm_url)
			: Io_stream_rdr_.New__raw(src_itm_url);
		rv.Len_(src_itm.Size());
		return rv;
	}
	private static IoItmFil Get_itm_by_filters(Io_url dir, String... filter_ary) {
		// create array of matches based on filters
		int match_ary_len = filter_ary.length;
		Criteria_ioMatch[] match_ary = new Criteria_ioMatch[match_ary_len];
		for (int i = 0; i < match_ary_len; ++i)
			match_ary[i] = Criteria_ioMatch.parse(true, filter_ary[i], dir.Info().CaseSensitive());

		// get files and check each file for match
		IoItmFil rv = null;
		IoItmHash itm_hash = Io_mgr.Instance.QueryDir_args(dir).ExecAsItmHash();
		int len = itm_hash.Count();
		for (int i = 0; i < len; ++i) {
			IoItm_base itm = itm_hash.Get_at(i);
			for (int j = 0; j < match_ary_len; ++j) {
				if (itm.Type_fil() && match_ary[j].Matches(itm.Url()))
					rv = (IoItmFil)itm;	// NOTE: this will return the last match; useful for getting latest dump when multiple dumps are in one dir; (assuming latest should alphabetize last)
			}
		}
		return rv;
	}
}
class Io_stream_unzip_mgr {		
	private final    String[] zip_exts;
	private final    boolean stdout_enabled; private final    Process_adp stdout_process;
	public Io_stream_unzip_mgr(boolean stdout_enabled, Process_adp stdout_process, String[] zip_exts) {
		this.stdout_enabled = stdout_enabled; this.stdout_process = stdout_process; this.zip_exts = zip_exts;
	}
	public boolean Handles(Io_url url) {return String_.In(url.Ext(), zip_exts);}
	public Io_stream_rdr New_rdr(Io_url url) {
		return stdout_enabled
			? Io_stream_rdr_process.new_(stdout_process.Exe_url(), url, stdout_process.Xto_process_bldr_args(url.Raw()))
			: Io_stream_rdr_.New__bzip2(url)
			;
	}
}
