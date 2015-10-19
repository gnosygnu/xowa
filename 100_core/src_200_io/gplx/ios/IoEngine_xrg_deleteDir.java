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
import gplx.core.consoles.*; import gplx.core.criterias.*;
public class IoEngine_xrg_deleteDir {
	public Io_url Url() {return url;} public IoEngine_xrg_deleteDir Url_(Io_url val) {url = val; return this;} Io_url url;
	public boolean Recur() {return recur;} public IoEngine_xrg_deleteDir Recur_() {return Recur_(true);} public IoEngine_xrg_deleteDir Recur_(boolean v) {recur = v; return this;} private boolean recur = false;
	public boolean ReadOnlyFails() {return readOnlyFails;} public IoEngine_xrg_deleteDir ReadOnlyFails_off() {return ReadOnlyFails_(false);} public IoEngine_xrg_deleteDir ReadOnlyFails_(boolean v) {readOnlyFails = v; return this;} private boolean readOnlyFails = true;
	public boolean MissingIgnored() {return missingIgnored;} public IoEngine_xrg_deleteDir MissingIgnored_() {return MissingIgnored_(true);} public IoEngine_xrg_deleteDir MissingIgnored_(boolean v) {missingIgnored = v; return this;} private boolean missingIgnored = true;
	public Criteria MatchCrt() {return matchCrt;} public IoEngine_xrg_deleteDir MatchCrt_(Criteria v) {matchCrt = v; return this;} Criteria matchCrt = Criteria_.All;
	public Criteria SubDirScanCrt() {return subDirScanCrt;} public IoEngine_xrg_deleteDir SubDirScanCrt_(Criteria v) {subDirScanCrt = v; return this;} Criteria subDirScanCrt = Criteria_.All;
	public Console_adp UsrDlg() {return usrDlg;} public IoEngine_xrg_deleteDir UsrDlg_(Console_adp v) {usrDlg = v; return this;} Console_adp usrDlg = Console_adp_.Noop;
	public void Exec() {IoEnginePool.Instance.Get_by(url.Info().EngineKey()).DeleteDirDeep(this);}
	public static IoEngine_xrg_deleteDir new_(Io_url url) {
		IoEngine_xrg_deleteDir rv = new IoEngine_xrg_deleteDir();
		rv.url = url;
		return rv;
	}	IoEngine_xrg_deleteDir() {}
}
