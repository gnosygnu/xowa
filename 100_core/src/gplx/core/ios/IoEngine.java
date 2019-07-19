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
import gplx.core.consoles.*; import gplx.core.criterias.*;
import gplx.core.ios.streams.*; import gplx.core.ios.atrs.*;
public interface IoEngine {
	String		Key();
	boolean		ExistsFil_api(Io_url url);
	void		SaveFilText_api(IoEngine_xrg_saveFilStr args);
	String		LoadFilStr(IoEngine_xrg_loadFilStr args);
	void		DeleteFil_api(IoEngine_xrg_deleteFil args);
	void		CopyFil(IoEngine_xrg_xferFil args);
	void		MoveFil(IoEngine_xrg_xferFil args);
	IoItmFil	QueryFil(Io_url url);
	void		UpdateFilAttrib(Io_url url, IoItmAttrib atr); // will fail if file does not exists
	void		UpdateFilModifiedTime(Io_url url, DateAdp modified);
	IoStream	OpenStreamRead(Io_url url);
	IoStream	OpenStreamWrite(IoEngine_xrg_openWrite args);
	void		XferFil(IoEngine_xrg_xferFil args);
	void		RecycleFil(IoEngine_xrg_recycleFil xrg);
	boolean		Truncate_fil(Io_url url, long size);
	Io_itm_atr_req Query_itm_atrs(Io_url url, Io_itm_atr_req req);

	boolean		ExistsDir(Io_url url);
	void		CreateDir(Io_url url); // creates all folder levels (EX: C:\a\b\c\ will create C:\a\ and C:\a\b\). will not fail if called on already existing folders.
	void		DeleteDir(Io_url url);
	void		MoveDir(Io_url src, Io_url trg);		// will fail if trg exists
	void		CopyDir(Io_url src, Io_url trg);
	IoItmDir	QueryDir(Io_url url);

	void		DeleteDirDeep(IoEngine_xrg_deleteDir args);
	void		MoveDirDeep(IoEngine_xrg_xferDir args);		// will fail if trg exists
	IoItmDir	QueryDirDeep(IoEngine_xrg_queryDir args);
	void		XferDir(IoEngine_xrg_xferDir args);
	boolean		DownloadFil(IoEngine_xrg_downloadFil xrg);
	Io_stream_rdr DownloadFil_as_rdr(IoEngine_xrg_downloadFil xrg);
}
