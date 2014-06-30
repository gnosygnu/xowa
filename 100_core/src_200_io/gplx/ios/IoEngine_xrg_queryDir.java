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
import gplx.criterias.*;
public class IoEngine_xrg_queryDir {
	public Io_url Url() {return url;} public IoEngine_xrg_queryDir Url_(Io_url val) {url = val; return this;} Io_url url;
	public boolean Recur() {return recur;} public IoEngine_xrg_queryDir Recur_() {return Recur_(true);} public IoEngine_xrg_queryDir Recur_(boolean val) {recur = val; return this;} private boolean recur = false;
	public boolean DirInclude() {return dirInclude;} public IoEngine_xrg_queryDir DirInclude_() {return DirInclude_(true);} public IoEngine_xrg_queryDir DirInclude_(boolean val) {dirInclude = val; return this;} private boolean dirInclude = false;
	public Criteria FilCrt() {return filCrt;} public IoEngine_xrg_queryDir FilCrt_(Criteria val) {filCrt = val; return this;} Criteria filCrt;
	public Criteria DirCrt() {return dirCrt;} public IoEngine_xrg_queryDir DirCrt_(Criteria val) {dirCrt = val; return this;} Criteria dirCrt;
	public Criteria SubDirScanCrt() {return subDirScanCrt;} public IoEngine_xrg_queryDir SubDirScanCrt_(Criteria val) {subDirScanCrt = val; return this;} Criteria subDirScanCrt;
	public IoEngine_xrg_queryDir DirOnly_() {
		DirInclude_(true);
		filCrt = Criteria_.None;
		return this;
	}

	public ConsoleDlg UsrDlg() {return usrDlg;} public IoEngine_xrg_queryDir UsrDlg_(ConsoleDlg val) {usrDlg = val; return this;} ConsoleDlg usrDlg = ConsoleDlg_.Null;
	public IoEngine_xrg_queryDir FilPath_(String val) {
		Criteria_ioMatch crt = Criteria_ioMatch.parse_(true, val, url.Info().CaseSensitive());
		filCrt = Criteria_wrapper.new_(IoItm_base_.Prop_Path, crt);
		return this;
	}
	public IoItmDir ExecAsDir() {return IoEnginePool._.Fetch(url.Info().EngineKey()).QueryDirDeep(this);}
	public Io_url[] ExecAsUrlAry() {return ExecAsItmHash().XtoIoUrlAry();}
	public IoItmHash ExecAsItmHash() {
		Criteria crt = dirInclude ? Criteria_.All : Criteria_wrapper.new_(IoItm_base_.Prop_Type, Criteria_.eq_(IoItmFil.Type_Fil));
		IoItmHash list = ExecAsDir().XtoIoItmList(crt);
		list.SortBy(IoItmBase_comparer_nest._);
		return list;
	}
	public static IoEngine_xrg_queryDir new_(Io_url url) {
		IoEngine_xrg_queryDir rv = new IoEngine_xrg_queryDir();
		rv.url = url;
		rv.filCrt = Criteria_wrapper.new_(IoItm_base_.Prop_Path, Criteria_.All);
		rv.dirCrt = Criteria_wrapper.new_(IoItm_base_.Prop_Path, Criteria_.All);
		rv.subDirScanCrt = Criteria_wrapper.new_(IoItm_base_.Prop_Path, Criteria_.All);
		return rv;
	}	IoEngine_xrg_queryDir() {}
}
