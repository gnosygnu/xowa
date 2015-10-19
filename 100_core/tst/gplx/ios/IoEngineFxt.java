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
package gplx.ios; import gplx.*;
public class IoEngineFxt {
	IoEngine EngineOf(Io_url url) {return IoEnginePool.Instance.Get_by(url.Info().EngineKey());}
	public void tst_ExistsPaths(boolean expd, Io_url... ary) {			
		for (Io_url fil : ary) {
			if (fil.Type_dir())
				Tfds.Eq(expd, EngineOf(fil).ExistsDir(fil), "ExistsDir failed; dir={0}", fil);
			else
				Tfds.Eq(expd, EngineOf(fil).ExistsFil_api(fil), "ExistsFil failed; fil={0}", fil);
		}
	}
	public void tst_LoadFilStr(Io_url fil, String expd) {Tfds.Eq(expd, EngineOf(fil).LoadFilStr(IoEngine_xrg_loadFilStr.new_(fil)));}
	public void run_SaveFilText(Io_url fil, String expd) {EngineOf(fil).SaveFilText_api(IoEngine_xrg_saveFilStr.new_(fil, expd));}
	public void run_UpdateFilModifiedTime(Io_url fil, DateAdp modifiedTime) {EngineOf(fil).UpdateFilModifiedTime(fil, modifiedTime);}
	public void tst_QueryFilReadOnly(Io_url fil, boolean expd) {Tfds.Eq(expd, EngineOf(fil).QueryFil(fil).ReadOnly());}
	public IoEngineFxt tst_QueryFil_size(Io_url fil, long expd) {Tfds.Eq(expd, EngineOf(fil).QueryFil(fil).Size()); return this;}
	public IoEngineFxt tst_QueryFil_modifiedTime(Io_url fil, DateAdp expd) {Tfds.Eq_date(expd, EngineOf(fil).QueryFil(fil).ModifiedTime()); return this;}
	public IoItmDir tst_ScanDir(Io_url dir, Io_url... expd) {
		IoItmDir dirItem = EngineOf(dir).QueryDir(dir);
		Io_url[] actl = new Io_url[dirItem.SubDirs().Count() + dirItem.SubFils().Count()];
		for (int i = 0; i < dirItem.SubDirs().Count(); i++) {
			IoItmDir subDir = IoItmDir_.as_(dirItem.SubDirs().Get_at(i));
			actl[i] = subDir.Url();
		}
		for (int i = 0; i < dirItem.SubFils().Count(); i++) {
			IoItmFil subFil = IoItmFil_.as_(dirItem.SubFils().Get_at(i));
			actl[i + dirItem.SubDirs().Count()] = subFil.Url();
		}
		Tfds.Eq_ary_str(expd, actl);
		return dirItem;
	}
	public static IoEngineFxt new_() {
		IoEngineFxt rv = new IoEngineFxt();
		return rv;
	}
	public IoEngineFxt() {}
}
