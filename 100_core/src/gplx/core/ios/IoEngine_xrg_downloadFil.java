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
package gplx.core.ios; import gplx.*; import gplx.core.*;
import gplx.core.brys.fmtrs.*;
import gplx.core.ios.streams.*;
public class IoEngine_xrg_downloadFil {
	public String Src() {return src;} public IoEngine_xrg_downloadFil Src_(String v) {src = v; return this;} private String src;
	public Io_url Trg() {return trg;} public IoEngine_xrg_downloadFil Trg_(Io_url v) {trg = v; return this;} private Io_url trg;
	public byte Rslt() {return rslt;} public IoEngine_xrg_downloadFil Rslt_(byte v) {rslt = v; return this;} private byte rslt = Rslt_pass;
	public Exception Rslt_err() {return rslt_err;} public IoEngine_xrg_downloadFil Rslt_err_(Exception v) {rslt_err = v; return this;} private Exception rslt_err;
	public String Rslt_err_str() {
		return rslt_err == null ? "none" : Err_.Message_gplx_full(rslt_err);
	}
	public String User_agent() {return user_agent;} public IoEngine_xrg_downloadFil User_agent_(String v) {user_agent = v; return this;} private String user_agent;
	public Gfo_usr_dlg Prog_dlg() {return prog_dlg;} public IoEngine_xrg_downloadFil Prog_dlg_(Gfo_usr_dlg v) {prog_dlg = v; download_fmt.Ctor(prog_dlg); return this;} private Gfo_usr_dlg prog_dlg;
	public Bry_fmtr Prog_fmtr() {return prog_fmtr;} private final    Bry_fmtr prog_fmtr = Bry_fmtr.new_("~{download_header}: ~{download_read} of ~{download_length} kb;", "download_header", "download_url", "download_read", "download_length");
	public String Prog_fmt_hdr() {return prog_fmt_hdr;} public IoEngine_xrg_downloadFil Prog_fmt_hdr_(String v) {prog_fmt_hdr = v; return this;} private String prog_fmt_hdr = "";	// NOTE: must init to "", else null ref when building String
	public boolean Prog_cancel() {return prog_cancel;} public IoEngine_xrg_downloadFil Prog_cancel_y_() {prog_cancel = true; return this;} private volatile boolean prog_cancel;
	public boolean Prog_running() {return prog_running;} public IoEngine_xrg_downloadFil Prog_running_(boolean v) {prog_running = v; return this;} private boolean prog_running;
	public long Src_content_length() {return src_content_length;} public IoEngine_xrg_downloadFil Src_content_length_(long v) {src_content_length = v; return this;} private long src_content_length;
	public DateAdp Src_last_modified() {return src_last_modified;} public IoEngine_xrg_downloadFil Src_last_modified_(DateAdp v) {src_last_modified = v; return this;} private DateAdp src_last_modified;
	public boolean Src_last_modified_query() {return src_last_modified_query;} public IoEngine_xrg_downloadFil Src_last_modified_query_(boolean v) {src_last_modified_query = v; return this;} private boolean src_last_modified_query;
	public String Trg_engine_key() {return trg_engine_key;} public IoEngine_xrg_downloadFil Trg_engine_key_(String v) {trg_engine_key = v; return this;} private String trg_engine_key = IoEngine_.SysKey;
	public Io_download_fmt Download_fmt() {return download_fmt;} private final    Io_download_fmt download_fmt = new Io_download_fmt();
	public boolean Exec() {return IoEnginePool.Instance.Get_by(trg.Info().EngineKey()).DownloadFil(this);}
	public Io_stream_rdr Exec_as_rdr() {return IoEnginePool.Instance.Get_by(IoEngine_.SysKey).DownloadFil_as_rdr(this);}
	public boolean Exec_meta_only() {return exec_meta_only;} private boolean exec_meta_only;
	public byte[] Exec_as_bry(String src) {
		this.Src_(src); this.Trg_(trg_mem);
		download_fmt.Download_init(src, prog_fmt_hdr);	// NOTE: must set src else NULL error
		boolean pass = IoEnginePool.Instance.Get_by(trg_engine_key).DownloadFil(this);
		return pass ? Io_mgr.Instance.LoadFilBry(trg_mem) : null;
	}	private Io_url trg_mem = Io_url_.mem_fil_("mem/download.tmp");
	public boolean Exec_meta(String src) {
		this.Src_(src); this.Trg_(trg_mem);	// NOTE: set Trg_ else error in download proc
		download_fmt.Download_init(src, prog_fmt_hdr);	// NOTE: must set src else NULL error
		exec_meta_only = true;
		boolean rv = IoEnginePool.Instance.Get_by(trg_engine_key).DownloadFil(this);
		exec_meta_only = false;
		return rv;
	}
	public void Init(String src, Io_url trg) {
		this.src = src; this.trg = trg;
		prog_cancel = false;
		rslt_err = null;
		rslt = Rslt_pass;
		prog_running = true;
		download_fmt.Download_init(src, "downloading ~{src_name}: ~{prog_left} left (@ ~{prog_rate}); ~{prog_done} of ~{src_len} (~{prog_pct}%)");
	}
	public static IoEngine_xrg_downloadFil new_(String src, Io_url trg) {
		IoEngine_xrg_downloadFil rv = new IoEngine_xrg_downloadFil();
		rv.src = src; rv.trg = trg;
		return rv;
	}	IoEngine_xrg_downloadFil() {}
	public static final byte Rslt_pass = 0, Rslt_fail_host_not_found = 1, Rslt_fail_file_not_found = 2, Rslt_fail_unknown = 3;
}
