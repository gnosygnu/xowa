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
package gplx.xowa.addons.bldrs.mass_parses.parses.mgrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.mass_parses.*; import gplx.xowa.addons.bldrs.mass_parses.parses.*;
import gplx.core.ios.streams.*;
public class Xomp_parse_mgr_cfg implements Gfo_invk {
	public int		Num_wkrs()						{return num_wkrs;}						private int num_wkrs = -1;
	public int		Num_pages_in_pool()				{return num_pages_in_pool;}				private int num_pages_in_pool = -1;
	public int		Num_pages_per_wkr()				{return num_pages_per_wkr;}				private int num_pages_per_wkr = 1000;
	public int		Progress_interval()				{return progress_interval;}				private int progress_interval = 1000;
	public int		Perf_interval()					{return perf_interval;}					private int perf_interval = 10000;
	public int		Commit_interval()				{return commit_interval;}				private int commit_interval = 10000;
	public int		Cleanup_interval()				{return cleanup_interval;}				private int cleanup_interval = 50;	// setting at 1000 uses lots of memory
	public boolean		Hdump_enabled()					{return hdump_enabled;}					private boolean hdump_enabled = true;
	public boolean		Hdump_catboxs()					{return hdump_catboxs;}					private boolean hdump_catboxs = false;
	public boolean		Hzip_enabled()					{return hzip_enabled;}					private boolean hzip_enabled = true;
	public boolean		Hdiff_enabled()					{return hdiff_enabled;}					private boolean hdiff_enabled = true;
	public boolean		Log_file_lnkis()				{return log_file_lnkis;}				private boolean log_file_lnkis = true;
	public boolean		Load_all_templates()			{return load_all_templates;}			private boolean load_all_templates = true;
	public boolean		Load_all_imglinks()				{return load_all_imglinks;}				private boolean load_all_imglinks = true;
	public String	Load_ifexists_ns()				{return load_ifexists_ns;}				private String load_ifexists_ns = null;
	public boolean		Log_math()						{return log_math;}						private boolean log_math = false;
	public byte		Zip_tid()						{return zip_tid;}						private byte zip_tid = Io_stream_tid_.Tid__gzip;
	public Io_url	Mgr_url()						{return mgr_url;}						private Io_url mgr_url;
	public String	Wkr_machine_name()				{return wkr_machine_name;}				private String wkr_machine_name;
	public boolean		Show_msg__fetched_pool()		{return show_msg__fetched_pool;}		private boolean show_msg__fetched_pool;
	public boolean		Indexer_enabled()               {return indexer_enabled;}               private boolean indexer_enabled;
	public String   Indexer_opt()                   {return indexer_opt;}                   private String indexer_opt = gplx.gflucene.indexers.Gflucene_idx_opt.Docs_and_freqs.Key();
	public void Init(Xowe_wiki wiki) {
		if (num_wkrs == -1)				num_wkrs = gplx.core.envs.Runtime_.Cpu_count();
		if (num_pages_in_pool == -1)	num_pages_in_pool = num_wkrs * 1000;
		if (mgr_url == null)			mgr_url = wiki.Fsys_mgr().Root_dir().GenSubDir_nest("tmp", "xomp");
		if (wkr_machine_name == null)	wkr_machine_name = gplx.core.envs.System_.Env__machine_name();
	}

	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk__num_wkrs_))						num_wkrs = m.ReadInt("v");
		else if	(ctx.Match(k, Invk__num_pages_in_pool_))			num_pages_in_pool = m.ReadInt("v");
		else if	(ctx.Match(k, Invk__num_pages_per_wkr_))			num_pages_per_wkr = m.ReadInt("v");
		else if	(ctx.Match(k, Invk__progress_interval_))			progress_interval = m.ReadInt("v");
		else if	(ctx.Match(k, "perf_interval_"))					perf_interval = m.ReadInt("v");
		else if	(ctx.Match(k, Invk__commit_interval_))				commit_interval = m.ReadInt("v");
		else if	(ctx.Match(k, Invk__cleanup_interval_))				cleanup_interval = m.ReadInt("v");
		else if	(ctx.Match(k, Invk__hdump_enabled_))				hdump_enabled = m.ReadYn("v");
		else if	(ctx.Match(k, Invk__hzip_enabled_))					hzip_enabled = m.ReadYn("v");
		else if	(ctx.Match(k, Invk__hdiff_enabled_))				hdiff_enabled = m.ReadYn("v");
		else if	(ctx.Match(k, Invk__zip_tid_))						zip_tid = m.ReadByte("v");
		else if	(ctx.Match(k, Invk__load_all_templates_))			load_all_templates = m.ReadYn("v");
		else if	(ctx.Match(k, Invk__load_all_imglinks_))			load_all_imglinks = m.ReadYn("v");
		else if	(ctx.Match(k, Invk__load_ifexists_ns_))				load_ifexists_ns = m.ReadStr("v");
		else if	(ctx.Match(k, Invk__manual_now_))					Datetime_now.Manual_and_freeze_(m.ReadDate("v"));
		else if	(ctx.Match(k, Invk__mgr_url_))						mgr_url = m.ReadIoUrl("v");
		else if	(ctx.Match(k, Invk__wkr_machine_name_))				wkr_machine_name = m.ReadStr("v");
		else if	(ctx.Match(k, Invk__show_msg__fetched_pool_))		show_msg__fetched_pool = m.ReadYn("v");
		else if	(ctx.Match(k, Invk__hdump_catboxes_))				hdump_catboxs = m.ReadYn("v");
		else if	(ctx.Match(k, Invk__log_math_))						log_math = m.ReadYn("v");
		else if	(ctx.Match(k, "indexer_enabled_"))                  indexer_enabled = m.ReadYn("v");
		else if	(ctx.Match(k, "indexer_opt_"))                      indexer_opt = m.ReadStr("v");
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String
	  Invk__num_wkrs_ = "num_wkrs_", Invk__num_pages_in_pool_ = "num_pages_in_pool_", Invk__num_pages_per_wkr_ = "num_pages_per_wkr_"
	, Invk__progress_interval_ = "progress_interval_", Invk__commit_interval_ = "commit_interval_", Invk__cleanup_interval_ = "cleanup_interval_"
	, Invk__hdump_enabled_ = "hdump_enabled_", Invk__hzip_enabled_ = "hzip_enabled_", Invk__hdiff_enabled_ = "hdiff_enabled_", Invk__zip_tid_ = "zip_tid_"
	, Invk__load_all_templates_ = "load_all_templates_", Invk__load_all_imglinks_ = "load_all_imglinks_", Invk__load_ifexists_ns_ = "load_ifexists_ns_", Invk__manual_now_ = "manual_now_"
	, Invk__hdump_catboxes_ = "hdump_catboxes_"
	, Invk__log_math_ = "log_math_"
	, Invk__mgr_url_ = "mgr_url_", Invk__wkr_machine_name_ = "wkr_machine_name_"
	, Invk__show_msg__fetched_pool_ = "show_msg__fetched_pool_"
	;
}
