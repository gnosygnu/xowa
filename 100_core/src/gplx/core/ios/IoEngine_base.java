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
package gplx.core.ios; import gplx.*; import gplx.core.*;
import gplx.core.ios.streams.*;
public abstract class IoEngine_base implements IoEngine {
	public abstract String		Key();
	public abstract boolean		ExistsFil_api(Io_url url);
	public abstract void		SaveFilText_api(IoEngine_xrg_saveFilStr args);
	public abstract String		LoadFilStr(IoEngine_xrg_loadFilStr args);
	public abstract void		DeleteFil_api(IoEngine_xrg_deleteFil args);
	public abstract void		CopyFil(IoEngine_xrg_xferFil args);
	public abstract void		MoveFil(IoEngine_xrg_xferFil args);
	public abstract IoItmFil	QueryFil(Io_url url);
	public abstract void		UpdateFilAttrib(Io_url url, IoItmAttrib atr); // will fail if file does not exists
	public abstract void		UpdateFilModifiedTime(Io_url url, DateAdp modified);
	public abstract IoStream	OpenStreamRead(Io_url url);
	public abstract IoStream	OpenStreamWrite(IoEngine_xrg_openWrite args);
	public abstract void		XferFil(IoEngine_xrg_xferFil args);
	public abstract boolean		Truncate_fil(Io_url url, long size);

	public abstract boolean		ExistsDir(Io_url url);
	public abstract void		CreateDir(Io_url url); // creates all folder levels (EX: C:\a\b\c\ will create C:\a\ and C:\a\b\). will not fail if called on already existing folders.
	public abstract void		DeleteDir(Io_url url);
	public abstract void		MoveDir(Io_url src, Io_url trg);		// will fail if trg exists
	public abstract void		CopyDir(Io_url src, Io_url trg);
	public abstract IoItmDir	QueryDir(Io_url url);

	public abstract void		DeleteDirDeep(IoEngine_xrg_deleteDir args);
	public abstract void		MoveDirDeep(IoEngine_xrg_xferDir args);		// will fail if trg exists
	public abstract IoItmDir	QueryDirDeep(IoEngine_xrg_queryDir args);
	public abstract void		XferDir(IoEngine_xrg_xferDir args);
	public abstract boolean		DownloadFil(IoEngine_xrg_downloadFil xrg);
	public abstract Io_stream_rdr DownloadFil_as_rdr(IoEngine_xrg_downloadFil xrg);

	public void RecycleFil(IoEngine_xrg_recycleFil xrg) {
		Io_url recycleUrl = xrg.RecycleUrl();
		if (recycleUrl.Type_fil()) {
			this.MoveFil(IoEngine_xrg_xferFil.move_(xrg.Url(), recycleUrl).Overwrite_(false).ReadOnlyFails_(true).MissingFails_(xrg.MissingFails()));
			IoRecycleBin.Instance.Regy_add(xrg);
		}
		else
			this.MoveDirDeep(IoEngine_xrg_xferDir.move_(xrg.Url(), recycleUrl).Overwrite_(false).ReadOnlyFails_(true));
	}
}
