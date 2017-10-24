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

	public Console_adp UsrDlg() {return usrDlg;} public IoEngine_xrg_queryDir UsrDlg_(Console_adp val) {usrDlg = val; return this;} Console_adp usrDlg = Console_adp_.Noop;
	public IoEngine_xrg_queryDir FilPath_(String val) {
		Criteria_ioMatch crt = Criteria_ioMatch.parse(true, val, url.Info().CaseSensitive());
		filCrt = Criteria_fld.new_(IoItm_base_.Prop_Path, crt);
		return this;
	}
	public IoItmDir ExecAsDir() {return IoEnginePool.Instance.Get_by(url.Info().EngineKey()).QueryDirDeep(this);}
	public Io_url[] ExecAsUrlAry() {return ExecAsItmHash().XtoIoUrlAry();}
	public IoItmHash ExecAsItmHash() {
		Criteria crt = dirInclude ? Criteria_.All : Criteria_fld.new_(IoItm_base_.Prop_Type, Criteria_.eq_(IoItmFil.Type_Fil));
		IoItmHash list = ExecAsDir().XtoIoItmList(crt);
		list.Sort_by(IoItmBase_comparer_nest.Instance);
		return list;
	}
	public static IoEngine_xrg_queryDir new_(Io_url url) {
		IoEngine_xrg_queryDir rv = new IoEngine_xrg_queryDir();
		rv.url = url;
		rv.filCrt = Criteria_fld.new_(IoItm_base_.Prop_Path, Criteria_.All);
		rv.dirCrt = Criteria_fld.new_(IoItm_base_.Prop_Path, Criteria_.All);
		rv.subDirScanCrt = Criteria_fld.new_(IoItm_base_.Prop_Path, Criteria_.All);
		return rv;
	}	IoEngine_xrg_queryDir() {}
}
