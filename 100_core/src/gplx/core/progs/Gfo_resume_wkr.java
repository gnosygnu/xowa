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
