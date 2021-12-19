/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.ios.atrs;
import gplx.libs.dlgs.Gfo_usr_dlg_;
import gplx.libs.files.Io_url;
import gplx.types.errs.Err;
import gplx.types.errs.ErrUtl;
import java.io.File;
import java.nio.file.Path;
import java.util.Set;
public abstract class Io_itm_atr_wkr {
		private final Path path;
	public Io_itm_atr_wkr(Path path) {
		this.path = path;
	}
		public Io_itm_atr_req Process(Io_itm_atr_req req) {
				try {
			if (req.Check_read_only())
				req.Is_read_only_(this.Is_read_only());
		}
		catch (Exception e) {
			Err err = ErrUtl.NewArgs("query_itm_atrs failed", "url", path.toString(), "atrs", req.To_str(), "e", ErrUtl.ToStrLog(e));
			if (req.Ignore_errors()) { // https://stackoverflow.com/questions/25163174/get-generic-folder-permissions-like-generic-all-using-javas-aclfileattributev
				Gfo_usr_dlg_.Instance.Warn_many("", "", err.ToStrLog());
			}
			else {
				throw err;
			}
		}
				return req;
	}
	public abstract boolean Is_read_only();
	public static Io_itm_atr_wkr New(Io_url url) {
				File fil = new File(url.Xto_api());
		Path path = fil.toPath();
		Set<String> supported_views = path.getFileSystem().supportedFileAttributeViews();
		if (supported_views.contains("posix")) {
			return new Io_itm_atr_wkr__psx(path);
		}
		// WNT
		else if (supported_views.contains("acl")) {
			return new Io_itm_atr_wkr__acl(path);
		}
		else {
			String set_string = "";
			for (String view : supported_views) {
				set_string += view + ";";
			}
			throw ErrUtl.NewUnhandled(set_string);
		}
			}
}
