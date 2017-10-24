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
import gplx.core.criterias.*;
public class IoEngine_xrg_xferDir {
	public boolean Type_move() {return move;} public boolean Type_copy() {return !move;} private boolean move = false;
	public Io_url Src() {return src;} public IoEngine_xrg_xferDir Src_(Io_url val) {src = val; return this;} Io_url src;
	public Io_url Trg() {return trg;} public IoEngine_xrg_xferDir Trg_(Io_url val) {trg = val; return this;} Io_url trg;
	public boolean Recur() {return recur;} public IoEngine_xrg_xferDir Recur_() {recur = true; return this;} private boolean recur = false;
	public boolean Overwrite() {return overwrite;} public IoEngine_xrg_xferDir Overwrite_() {return Overwrite_(true);} public IoEngine_xrg_xferDir Overwrite_(boolean v) {overwrite = v; return this;} private boolean overwrite = false;
	public boolean ReadOnlyFails() {return readOnlyFails;} public IoEngine_xrg_xferDir ReadOnlyFails_() {return ReadOnlyFails_(true);} public IoEngine_xrg_xferDir ReadOnlyFails_(boolean v) {readOnlyFails = v; return this;} private boolean readOnlyFails = false;
	public Criteria MatchCrt() {return matchCrt;} public IoEngine_xrg_xferDir MatchCrt_(Criteria v) {matchCrt = v; return this;} Criteria matchCrt = Criteria_.All;
	public Criteria SubDirScanCrt() {return subDirScanCrt;} public IoEngine_xrg_xferDir SubDirScanCrt_(Criteria v) {subDirScanCrt = v; return this;} Criteria subDirScanCrt = Criteria_.All;
	public void Exec() {IoEnginePool.Instance.Get_by(src.Info().EngineKey()).XferDir(this);}
	public static IoEngine_xrg_xferDir move_(Io_url src, Io_url trg) {return new_(src, trg, true);}
	public static IoEngine_xrg_xferDir copy_(Io_url src, Io_url trg) {return new_(src, trg, false);}
	static IoEngine_xrg_xferDir new_(Io_url src, Io_url trg, boolean move) {
		IoEngine_xrg_xferDir rv = new IoEngine_xrg_xferDir();
		rv.src = src; rv.trg = trg; rv.move = move;
		return rv;
	}	IoEngine_xrg_xferDir() {}
}
