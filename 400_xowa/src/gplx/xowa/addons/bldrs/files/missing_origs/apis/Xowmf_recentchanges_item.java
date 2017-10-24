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
package gplx.xowa.addons.bldrs.files.missing_origs.apis; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.files.*; import gplx.xowa.addons.bldrs.files.missing_origs.*;
public class Xowmf_recentchanges_item {
	public int Ns() {return ns;} private int ns;
	public byte[] Title() {return title;} private byte[] title;
	public int Pageid() {return pageid;} private int pageid;
	public int Revid() {return revid;} private int revid;
	public int Old_revid() {return old_revid;} private int old_revid;
	public int Rcid() {return rcid;} private int rcid;
	public int Oldlen() {return oldlen;} private int oldlen;
	public int Newlen() {return newlen;} private int newlen;
	public int Logid() {return logid;} private int logid;
	public String Logtype() {return logtype;} private String logtype;
	public String Logaction() {return logaction;} private String logaction; // upload, move, delete

	public byte[] Img_timestamp() {return img_timestamp;} private byte[] img_timestamp;

	public int Target_ns() {return target_ns;} private int target_ns;
	public byte[] Target_title() {return target_title;} private byte[] target_title;

	public List_adp Logtypes() {return logtypes;} private List_adp logtypes;
	public void Init_base(int ns, byte[] title, int pageid, int revid, int old_revid, int rcid, int oldlen, int newlen, int logid, String logtype, String logaction) {
		this.ns = ns;
		this.title = title;
		this.pageid = pageid;
		this.revid = revid;
		this.old_revid = old_revid;
		this.rcid = rcid;
		this.oldlen = oldlen;
		this.newlen = newlen;
		this.logid = logid;
		this.logtype = logtype;
		this.logaction = logaction;
	}
	public void Init_upload(byte[] img_timestamp) {
		this.img_timestamp = img_timestamp;
	}
	public void Init_move(int target_ns, byte[] target_title) {
		this.target_ns = target_ns;
		this.target_title = target_title;
	}
	public void Logtype_push() {
		if (logtypes == null) {
			logtypes = List_adp_.New();
		}
		logtypes.Add(logtype);
	}
}
