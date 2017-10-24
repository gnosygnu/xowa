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
public class IoEngine_xrg_xferFil {
	public boolean Type_move() {return move;} private boolean move = false;
	public Io_url Src() {return src;} Io_url src;
	public Io_url Trg() {return trg;} Io_url trg;
	public boolean Overwrite() {return overwrite;} public IoEngine_xrg_xferFil Overwrite_() {return Overwrite_(true);} public IoEngine_xrg_xferFil Overwrite_(boolean v) {overwrite = v; return this;} private boolean overwrite = false;
	public boolean ReadOnlyFails() {return readOnlyFails;} public IoEngine_xrg_xferFil ReadOnlyFails_off() {return ReadOnlyFails_(false);} public IoEngine_xrg_xferFil ReadOnlyFails_(boolean v) {readOnlyFails = v; return this;} private boolean readOnlyFails = true;
	public boolean MissingFails() {return missingFails;} public IoEngine_xrg_xferFil MissingFails_off() {return MissingFails_(false);} public IoEngine_xrg_xferFil MissingFails_(boolean v) {missingFails = v; return this;} private boolean missingFails = true;
	public void Exec() {IoEnginePool.Instance.Get_by(src.Info().EngineKey()).XferFil(this);}
	public static IoEngine_xrg_xferFil move_(Io_url src, Io_url trg) {return new_(src, trg, true);}
	public static IoEngine_xrg_xferFil copy_(Io_url src, Io_url trg) {return new_(src, trg, false);}
	static IoEngine_xrg_xferFil new_(Io_url src, Io_url trg, boolean move) {
		IoEngine_xrg_xferFil rv = new IoEngine_xrg_xferFil();
		rv.src = src; rv.trg = trg; rv.move = move;
		return rv;
	}	IoEngine_xrg_xferFil() {}
}
