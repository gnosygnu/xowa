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
package gplx.core.progs; import gplx.*; import gplx.core.*;
public interface Gfo_resume_wkr {
	boolean		Resuming();
	long		Get_long(byte tid);
	void		Set_long(byte tid, long v);
}
class Gfo_resume_wkr__download {
	public boolean		Resuming()					{return resuming;} private boolean resuming = true;
	public long		Get_long(byte tid)			{return val;} private long val;
	public void		Set_long(byte tid, long v)	{val = v;}
}
class Gfo_resume_wkr__unzip {
	public boolean		Resuming()					{return resuming;} private boolean resuming = true;
	public long		Get_long(byte tid)			{return val;} private long val;
	public void		Set_long(byte tid, long v)	{val = v;}
	public String	Get_str(byte tid) {return name;} private String name;
	public void		Set_str(byte tid, String v) {this.name = v;}
}
/*
[
	{ "job_uid":
	, "subs": 
	[
		{ "download"
		, "done": 0
		, "resume_bytes":123
		}
	,	{ "unzip"
		, "done": 0
		, "resume_file":abc
		, "resume_bytes":123
		}
	]
	}
]
*/
